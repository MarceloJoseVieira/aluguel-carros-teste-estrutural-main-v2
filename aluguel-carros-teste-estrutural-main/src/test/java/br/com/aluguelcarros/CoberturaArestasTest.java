package br.com.aluguelcarros;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class CoberturaArestasTest
{
    private final CalculadoraCustoLocacao calculadora = new CalculadoraCustoLocacao();

    // Exemplo inicial: acrescente os testes necessários para cobertura de arestas.
    @ParameterizedTest
    @CsvSource({"1, 120.00", "2, 240.00"})
    void deveCalcularLocacaoBasica(int numeroDiarias, BigDecimal valorEsperado)
    {
        BigDecimal resultado = calcular(
            CategoriaVeiculo.ECONOMICO,
            NivelCliente.COMUM,
            numeroDiarias,
            0,
            0,
            TipoSeguro.SEM_SEGURO,
            false
        );

        assertThat(resultado).isEqualByComparingTo(valorEsperado);
    }

    static Stream<Arguments> casosAdicionaisDeSucesso()
    {
        return Stream.of(
            // Categorias
            Arguments.of("C1", CategoriaVeiculo.INTERMEDIARIO, NivelCliente.COMUM, 1, 0, 0,
                    TipoSeguro.SEM_SEGURO, false, "180.00"),
            Arguments.of("C2", CategoriaVeiculo.SUV, NivelCliente.COMUM, 1, 0, 0,
                    TipoSeguro.SEM_SEGURO, false, "280.00"),

            // Desconto por duração
            Arguments.of("D1", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 3, 0, 0,
                    TipoSeguro.SEM_SEGURO, false, "342.00"),
            Arguments.of("D2", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 7, 0, 0,
                    TipoSeguro.SEM_SEGURO, false, "756.00"),
            Arguments.of("D3", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 15, 0, 0,
                    TipoSeguro.SEM_SEGURO, false, "1530.00"),

            Arguments.of("D4", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 14, 0, 0,
                    TipoSeguro.SEM_SEGURO, false, "1512.00"),

            // Fidelidade PRATA
            Arguments.of("P1", CategoriaVeiculo.ECONOMICO, NivelCliente.PRATA, 7, 0, 0,
                    TipoSeguro.SEM_SEGURO, false, "718.20"),
            Arguments.of("P2", CategoriaVeiculo.ECONOMICO, NivelCliente.PRATA, 6, 0, 0,
                    TipoSeguro.SEM_SEGURO, false, "684.00"),
            Arguments.of("P3", CategoriaVeiculo.ECONOMICO, NivelCliente.PRATA, 7, 0, 0,
                    TipoSeguro.SEM_SEGURO, true, "756.00"),
            Arguments.of("P4", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 7, 0, 0,
                    TipoSeguro.SEM_SEGURO, false, "756.00"),

            // Fidelidade OURO
            Arguments.of("O1", CategoriaVeiculo.ECONOMICO, NivelCliente.OURO, 5, 0, 0,
                    TipoSeguro.SEM_SEGURO, false, "513.00"),
            Arguments.of("O2", CategoriaVeiculo.ECONOMICO, NivelCliente.OURO, 4, 0, 0,
                    TipoSeguro.SEM_SEGURO, false, "456.00"),
            Arguments.of("O3", CategoriaVeiculo.ECONOMICO, NivelCliente.OURO, 5, 0, 0,
                    TipoSeguro.SEM_SEGURO, true, "570.00"),
            Arguments.of("O4", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 5, 0, 0,
                    TipoSeguro.SEM_SEGURO, false, "570.00"),

            // Quilometragem
            Arguments.of("K1", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 1, 100, 0,
                    TipoSeguro.SEM_SEGURO, false, "120.00"),
            Arguments.of("K2", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 1, 200, 0,
                    TipoSeguro.SEM_SEGURO, false, "200.00"),
            Arguments.of("K3", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 1, 400, 0,
                    TipoSeguro.SEM_SEGURO, false, "420.00"),
            Arguments.of("K4", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 1, 500, 0,
                    TipoSeguro.SEM_SEGURO, false, "720.00"),

            Arguments.of("K5", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 2, 300, 0,
                    TipoSeguro.SEM_SEGURO, false, "320.00"),


                // Seguro
            Arguments.of("S1", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 1, 0, 0,
                    TipoSeguro.BASICO, false, "145.00"),
            Arguments.of("S2", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 1, 0, 0,
                    TipoSeguro.COMPLETO, false, "165.00"),

            // Atraso
            Arguments.of("A1", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 1, 0, 1,
                    TipoSeguro.SEM_SEGURO, false, "120.00"),
            Arguments.of("A2", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 1, 0, 2,
                    TipoSeguro.SEM_SEGURO, false, "168.00"),
            Arguments.of("A3", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 1, 0, 4,
                    TipoSeguro.SEM_SEGURO, false, "240.00"),

            Arguments.of("A4", CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 1, 0, 3,
                    TipoSeguro.SEM_SEGURO, false, "192.00")
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("casosAdicionaisDeSucesso")
    void deveCobrirCasosAdicionaisDeSucesso(
            String caso,
            CategoriaVeiculo categoria,
            NivelCliente nivelCliente,
            int numeroDiarias,
            int quilometrosRodados,
            int horasAtraso,
            TipoSeguro seguro,
            boolean possuiAtrasoAnterior,
            String valorEsperado)
    {
        BigDecimal resultado = calcular(
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
                "Resultado incorreto no caso " + caso
        );
    }

    @Test
    void deveLancarExcecaoQuandoCategoriaForNula() {
        assertThrows(NullPointerException.class, () -> calcular(
                null, NivelCliente.COMUM, 1, 0, 0, TipoSeguro.SEM_SEGURO, false
        ));
    }

    @Test
    void deveLancarExcecaoQuandoNivelClienteForNulo() {
        assertThrows(NullPointerException.class, () -> calcular(
                CategoriaVeiculo.ECONOMICO, null, 1, 0, 0, TipoSeguro.SEM_SEGURO, false
        ));
    }

    @Test
    void deveLancarExcecaoQuandoSeguroForNulo() {
        assertThrows(NullPointerException.class, () -> calcular(
                CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 1, 0, 0, null, false
        ));
    }

    @Test
    void deveLancarExcecaoQuandoNumeroDeDiariasForInvalido() {
        assertThrows(IllegalArgumentException.class, () -> calcular(
                CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 0, 0, 0,
                TipoSeguro.SEM_SEGURO, false
        ));
    }

    @Test
    void deveLancarExcecaoQuandoQuilometragemForNegativa() {
        assertThrows(IllegalArgumentException.class, () -> calcular(
                CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 1, -1, 0,
                TipoSeguro.SEM_SEGURO, false
        ));
    }

    @Test
    void deveLancarExcecaoQuandoHorasDeAtrasoForemNegativas() {
        assertThrows(IllegalArgumentException.class, () -> calcular(
                CategoriaVeiculo.ECONOMICO, NivelCliente.COMUM, 1, 0, -1,
                TipoSeguro.SEM_SEGURO, false
        ));
    }

    @Test
    void devePriorizarNuloQuandoTambemHouverDiariasInvalidas() {
        assertThrows(NullPointerException.class, () -> calcular(
                null, NivelCliente.COMUM, 0, 0, 0, TipoSeguro.SEM_SEGURO, false
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
