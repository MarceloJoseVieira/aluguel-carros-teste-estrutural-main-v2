package br.com.aluguelcarros;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class McdcTest
{
    private final CalculadoraCustoLocacao calculadora = new CalculadoraCustoLocacao();

    // Exemplo inicial: acrescente os testes necessários para MC/DC.
    @ParameterizedTest
    @CsvSource({"1, 120.00", "2, 240.00"})
    void deveCalcularLocacaoBasica(int numeroDiarias, BigDecimal valorEsperado)
    {
        BigDecimal resultado = calculadora.calcularCustoLocacao(
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

    @ParameterizedTest(name = "{0}")
    @CsvSource({
        "PRATA-BASE, ECONOMICO, PRATA, 7, 0, 0, SEM_SEGURO, false, 718.20",
        "PRATA-A,    ECONOMICO, COMUM,  7, 0, 0, SEM_SEGURO, false, 756.00",
        "PRATA-B,    ECONOMICO, PRATA, 6, 0, 0, SEM_SEGURO, false, 684.00",
        "PRATA-C,    ECONOMICO, PRATA, 7, 0, 0, SEM_SEGURO, true,  756.00"
    })
    void deveAtenderMcdcDaDecisaoPrata(
        String caso,
        CategoriaVeiculo categoria,
        NivelCliente nivelCliente,
        int numeroDiarias,
        int quilometrosRodados,
        int horasAtraso,
        TipoSeguro seguro,
        boolean possuiAtrasoAnterior,
        BigDecimal valorEsperado)
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

        assertThat(resultado).as("Resultado incorreto no caso %s", caso).isEqualByComparingTo(valorEsperado);
    }

    @ParameterizedTest(name = "{0}")
    @CsvSource({
        "OURO-BASE, ECONOMICO, OURO,  5, 0, 0, SEM_SEGURO, false, 513.00",
        "OURO-A,    ECONOMICO, COMUM,  5, 0, 0, SEM_SEGURO, false, 570.00",
        "OURO-B,    ECONOMICO, OURO,  4, 0, 0, SEM_SEGURO, false, 456.00",
        "OURO-C,    ECONOMICO, OURO,  5, 0, 0, SEM_SEGURO, true,  570.00"
    })
    void deveAtenderMcdcDaDecisaoOuro(
        String caso,
        CategoriaVeiculo categoria,
        NivelCliente nivelCliente,
        int numeroDiarias,
        int quilometrosRodados,
        int horasAtraso,
        TipoSeguro seguro,
        boolean possuiAtrasoAnterior,
        BigDecimal valorEsperado)
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

        assertThat(resultado).as("Resultado incorreto no caso %s", caso).isEqualByComparingTo(valorEsperado);
    }

}
