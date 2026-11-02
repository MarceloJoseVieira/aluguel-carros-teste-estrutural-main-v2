package br.com.aluguelcarros;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CaminhosBasicosTest
{
    private final CalculadoraCustoLocacao calculadora = new CalculadoraCustoLocacao();

    /*
     * Base de caminhos independentes de McCabe para calcularCustoLocacao.
     *
     * V(G) = E - N + 2 = 25, confirmado por 24 decisões binárias + 1.
     * As condições de curto-circuito de || e && são contadas separadamente,
     * conforme o grafo de fluxo de controle documentado em resposta.md.
     *
     * P1  caminho base: ECONOMICO, COMUM, 1 diária, 0 km, 0 h, SEM_SEGURO, sem atraso anterior.
     * P2  exceção: categoria nula.
     * P3  exceção: nível de cliente nulo.
     * P4  exceção: seguro nulo.
     * P5  exceção: número de diárias inválido.
     * P6  exceção: quilometragem negativa.
     * P7  exceção: horas de atraso negativas.
     * P8  categoria INTERMEDIARIO.
     * P9  categoria SUV.
     * P10 faixa de duração de 3 a 6 diárias.
     * P11 faixa de duração de 7 a 14 diárias.
     * P12 faixa de duração de 15 diárias ou mais.
     * P13 fidelidade PRATA concedida.
     * P14 fidelidade PRATA negada por menos de 7 diárias.
     * P15 fidelidade PRATA negada por atraso anterior.
     * P16 fidelidade OURO concedida.
     * P17 fidelidade OURO negada por menos de 5 diárias.
     * P18 fidelidade OURO negada por atraso anterior.
     * P19 quilometragem excedente de até 100 km.
     * P20 quilometragem excedente de mais de 100 até 300 km.
     * P21 quilometragem excedente de mais de 300 km.
     * P22 seguro BASICO.
     * P23 seguro COMPLETO.
     * P24 atraso de 2 a 3 horas.
     * P25 atraso de 4 horas ou mais.
     */

    static Stream<Arguments> caminhosDeSucesso()
    {
        return Stream.of(
            // P1: caminho base.
            Arguments.of("P1", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 1, 0, 0,
                    TipoSeguro.SEM_SEGURO, false, "120.00"),

            // P8 e P9: demais categorias.
            Arguments.of("P8", CategoriaVeiculo.INTERMEDIARIO, NivelCliente.COMUM, 1, 0, 0,
                    TipoSeguro.SEM_SEGURO, false, "180.00"),
            Arguments.of("P9", CategoriaVeiculo.SUV, NivelCliente.COMUM, 1, 0, 0,
                    TipoSeguro.SEM_SEGURO, false, "280.00"),

            // P10 a P12: faixas de desconto por duração.
            Arguments.of("P10", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 3, 0, 0,
                    TipoSeguro.SEM_SEGURO, false, "342.00"),
            Arguments.of("P11", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 7, 0, 0,
                    TipoSeguro.SEM_SEGURO, false, "756.00"),
            Arguments.of("P12", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 15, 0, 0,
                    TipoSeguro.SEM_SEGURO, false, "1530.00"),

            // P13 a P15: decisão de fidelidade PRATA.
            Arguments.of("P13", CategoriaVeiculo.ECONOMICO, NivelCliente.PRATA, 7, 0, 0,
                    TipoSeguro.SEM_SEGURO, false, "718.20"),
            Arguments.of("P14", CategoriaVeiculo.ECONOMICO, NivelCliente.PRATA, 6, 0, 0,
                    TipoSeguro.SEM_SEGURO, false, "684.00"),
            Arguments.of("P15", CategoriaVeiculo.ECONOMICO, NivelCliente.PRATA, 7, 0, 0,
                    TipoSeguro.SEM_SEGURO, true, "756.00"),

            // P16 a P18: decisão de fidelidade OURO.
            Arguments.of("P16", CategoriaVeiculo.ECONOMICO, NivelCliente.OURO, 5, 0, 0,
                    TipoSeguro.SEM_SEGURO, false, "513.00"),
            Arguments.of("P17", CategoriaVeiculo.ECONOMICO, NivelCliente.OURO, 4, 0, 0,
                    TipoSeguro.SEM_SEGURO, false, "456.00"),
            Arguments.of("P18", CategoriaVeiculo.ECONOMICO, NivelCliente.OURO, 5, 0, 0,
                    TipoSeguro.SEM_SEGURO, true, "570.00"),

            // P19 a P21: faixas de quilometragem excedente.
            Arguments.of("P19", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 1, 200, 0,
                    TipoSeguro.SEM_SEGURO, false, "200.00"),
            Arguments.of("P20", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 1, 400, 0,
                    TipoSeguro.SEM_SEGURO, false, "420.00"),
            Arguments.of("P21", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 1, 500, 0,
                    TipoSeguro.SEM_SEGURO, false, "720.00"),

            // P22 e P23: demais seguros.
            Arguments.of("P22", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 1, 0, 0,
                    TipoSeguro.BASICO, false, "145.00"),
            Arguments.of("P23", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 1, 0, 0,
                    TipoSeguro.COMPLETO, false, "165.00"),

            // P24 e P25: faixas de atraso.
            Arguments.of("P24", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 1, 0, 2,
                    TipoSeguro.SEM_SEGURO, false, "168.00"),
            Arguments.of("P25", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 1, 0, 4,
                    TipoSeguro.SEM_SEGURO, false, "240.00")
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("caminhosDeSucesso")
    void deveExecutarCaminhoDeSucesso(
            String caminho,
            CategoriaVeiculo categoria,
            NivelCliente nivelCliente,
            int numeroDiarias,
            int quilometrosRodados,
            int horasAtraso,
            TipoSeguro seguro,
            boolean possuiAtrasoAnterior,
            String valorEsperado)
    {
        BigDecimal resultado = calculadora.calcularCustoLocacao(
                categoria,
                nivelCliente,
                numeroDiarias,
                quilometrosRodados,
                horasAtraso,
                seguro,
                possuiAtrasoAnterior
        );

        assertEquals(
                new BigDecimal(valorEsperado),
                resultado,
                "Resultado incorreto no caminho " + caminho
        );
    }

    @Test
    void caminhoP2DeveLancarExcecaoQuandoCategoriaForNula()
    {
        assertThrows(NullPointerException.class, () -> calcular(
                null, NivelCliente.COMUM, 1, 0, 0, TipoSeguro.SEM_SEGURO, false
        ));
    }

    @Test
    void caminhoP3DeveLancarExcecaoQuandoNivelClienteForNulo()
    {
        assertThrows(NullPointerException.class, () -> calcular(
                CategoriaVeiculo.ECONOMICO, null, 1, 0, 0, TipoSeguro.SEM_SEGURO, false
        ));
    }

    @Test
    void caminhoP4DeveLancarExcecaoQuandoSeguroForNulo()
    {
        assertThrows(NullPointerException.class, () -> calcular(
                CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 1, 0, 0, null, false
        ));
    }

    @Test
    void caminhoP5DeveLancarExcecaoQuandoNumeroDeDiariasForInvalido()
    {
        assertThrows(IllegalArgumentException.class, () -> calcular(
                CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 0, 0, 0,
                TipoSeguro.SEM_SEGURO, false
        ));
    }

    @Test
    void caminhoP6DeveLancarExcecaoQuandoQuilometragemForNegativa()
    {
        assertThrows(IllegalArgumentException.class, () -> calcular(
                CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 1, -1, 0,
                TipoSeguro.SEM_SEGURO, false
        ));
    }

    @Test
    void caminhoP7DeveLancarExcecaoQuandoHorasDeAtrasoForemNegativas()
    {
        assertThrows(IllegalArgumentException.class, () -> calcular(
                CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 1, 0, -1,
                TipoSeguro.SEM_SEGURO, false
        ));
    }

    private BigDecimal calcular(
            CategoriaVeiculo categoria,
            NivelCliente nivelCliente,
            int numeroDiarias,
            int quilometrosRodados,
            int horasAtraso,
            TipoSeguro seguro,
            boolean possuiAtrasoAnterior)
    {
        return calculadora.calcularCustoLocacao(
                categoria,
                nivelCliente,
                numeroDiarias,
                quilometrosRodados,
                horasAtraso,
                seguro,
                possuiAtrasoAnterior
        );
    }
}
