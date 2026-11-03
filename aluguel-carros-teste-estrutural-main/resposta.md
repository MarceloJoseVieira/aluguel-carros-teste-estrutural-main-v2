# Respostas — Trabalho Prático da Segunda Unidade

> Preencha os campos indicados e acrescente linhas às tabelas conforme necessário. Mantenha o código dos testes em `src/test/java` e os relatórios originais e logs em `evidencias/`, referenciados por links relativos neste arquivo. Consulte os requisitos completos no [enunciado](ENUNCIADO.md).

## Identificação dos alunos

| Integrante | Nome completo | Matrícula |
|---|---|---|
| Aluno(a) 1 | Marcelo José Vieira da Silva | 20210052017 |
| Aluno(a) 2 | Matheus Rodrigues do Rego | 20220005440 |

- Turma: DIM0507 - TESTE DE SOFTWARE - T01.
- Data: 3/11/2026.
- `artifactId`: MarceloVieira_MatheusRodrigues.

## 5.1. Cobertura de arestas

Classe: `CoberturaArestasTest.java`. Registre os resultados de sua execução isolada.

### Grafo de fluxo de controle

<!-- Insira o grafo e descreva as convenções de entrada, saída, curto-circuito e saídas por exceção. -->

Validação dos argumentos:
```mermaid
flowchart TD
    IN([Entrada]) --> B1

    B1{"1<br/>categoria == null OU<br/>nivelCliente == null OU<br/>seguro == null?"}
    B1 -- V --> B2["2<br/>Lançar NullPointerException"]
    B1 -- F --> B3{"3<br/>numeroDiarias <= 0?"}

    B3 -- V --> B4["4<br/>Lançar IllegalArgumentException"]
    B3 -- F --> B5{"5<br/>quilometrosRodados < 0?"}

    B5 -- V --> B6["6<br/>Lançar IllegalArgumentException"]
    B5 -- F --> B7{"7<br/>horasAtraso < 0?"}

    B7 -- V --> B8["8<br/>Lançar IllegalArgumentException"]
    B7 -- F --> 9

```
\
Valor da diária:
```mermaid
flowchart TD
    B9{"9<br/>categoria == ECONOMICO?"}
    B9 -- V --> B10["10<br/>valorDiaria = 120,00"]
    B9 -- F --> B11{"11<br/>categoria == INTERMEDIARIO?"}

    B11 -- V --> B12["12<br/>valorDiaria = 180,00"]
    B11 -- F --> B13["13<br/>valorDiaria = 280,00"]

    B10 --> 14
    B12 --> 14
    B13 --> 14
```
\
Desconto por duração:
```mermaid
flowchart TD
    B14{"14<br/>numeroDiarias <= 2?"}
    B14 -- V --> B15["15<br/>taxaDescontoDuracao = 0"]
    B14 -- F --> B16{"16<br/>numeroDiarias <= 6?"}

    B16 -- V --> B17["17<br/>taxaDescontoDuracao = 0,05"]
    B16 -- F --> B18{"18<br/>numeroDiarias <= 14?"}

    B18 -- V --> B19["19<br/>taxaDescontoDuracao = 0,10"]
    B18 -- F --> B20["20<br/>taxaDescontoDuracao = 0,15"]

    B15 --> 21
    B17 --> 21
    B19 --> 21
    B20 --> 21
```
\
Desconto por fidelidade:
```mermaid
flowchart TD
    B21["21<br/>Calcular valorBrutoDiarias,<br/>descontoDuracao e<br/>valorDiariasComDesconto"]
    B21 --> B22

    B22{"22<br/>nivelCliente == PRATA E<br/>numeroDiarias >= 7 E<br/>!possuiAtrasoAnterior?"}
    B22 -- V --> B23["23<br/>taxaDescontoFidelidade = 0,05"]
    B22 -- F --> B24{"24<br/>nivelCliente == OURO E<br/>numeroDiarias >= 5 E<br/>!possuiAtrasoAnterior?"}

    B24 -- V --> B25["25<br/>taxaDescontoFidelidade = 0,10"]
    B24 -- F --> B26["26<br/>taxaDescontoFidelidade = 0"]

    B23 --> 27
    B25 --> 27
    B26 --> 27
```
\
Custo da quilometragem:
```mermaid
flowchart TD
    B27["27<br/>Calcular descontoFidelidade,<br/>valorFinalDiarias,<br/>franquiaQuilometros e<br/>quilometrosExcedentes"]
    B27 --> B28

    B28{"28<br/>quilometrosExcedentes <= 0?"}
    B28 -- V --> B29["29<br/>custoQuilometragem = 0"]
    B28 -- F --> B30{"30<br/>quilometrosExcedentes <= 100?"}

    B30 -- V --> B31["31<br/>tarifaQuilometrica = 0,80"]
    B30 -- F --> B32{"32<br/>quilometrosExcedentes <= 300?"}

    B32 -- V --> B33["33<br/>tarifaQuilometrica = 1,00"]
    B32 -- F --> B34["34<br/>tarifaQuilometrica = 1,50"]

    B31 --> 35
    B33 --> 35
    B34 --> 35
```
\
Custo do seguro:
```mermaid
flowchart TD
    B35["35<br/>custoQuilometragem =<br/>quilometrosExcedentes × tarifaQuilometrica"]

    29 --> B36
    B35 --> B36

    B36{"36<br/>seguro == SEM_SEGURO?"}
    B36 -- V --> B37["37<br/>valorSeguroDiaria = 0"]
    B36 -- F --> B38{"38<br/>seguro == BASICO?"}

    B38 -- V --> B39["39<br/>valorSeguroDiaria = 25,00"]
    B38 -- F --> B40["40<br/>valorSeguroDiaria = 45,00"]

    B37 --> 41
    B39 --> 41
    B40 --> 41
```
\
Custo do atraso:
```mermaid
flowchart TD
    B41["41<br/>custoSeguro =<br/>valorSeguroDiaria × numeroDiarias"]
    B41 --> B42

    B42{"42<br/>horasAtraso <= 1?"}
    B42 -- V --> B43["43<br/>custoAtraso = 0"]
    B42 -- F --> B44{"44<br/>horasAtraso <= 3?"}

    B44 -- V --> B45["45<br/>custoAtraso =<br/>valorDiaria × 0,20 × horasAtraso"]
    B44 -- F --> B46["46<br/>custoAtraso = valorDiaria"]

    B47["47<br/>total = valorFinalDiarias +<br/>custoQuilometragem +<br/>custoSeguro + custoAtraso<br/><br/>Retornar total.setScale<br/>(2, RoundingMode.HALF_UP)"]
    B43 --> B47
    B45 --> B47
    B46 --> B47

    B47 --> OUT([Saída])
```

### Identificação dos nós e das arestas

| Nó | Trecho ou linhas do código | Descrição |
|---|---|---|
| IN | Linha 8 | Entrada do método `calcularCustoLocacao` |
| N1 | Linha 10 | Verificação conjunta: `categoria == null OU nivelCliente == null OU seguro == null` |
| N2 | Linhas 12–13 | Lançamento de `NullPointerException` |
| N3 | Linha 15 | Verificação: `numeroDiarias <= 0` |
| N4 | Linhas 17–18 | Lançamento de `IllegalArgumentException` para número de diárias |
| N5 | Linha 20 | Verificação: `quilometrosRodados < 0` |
| N6 | Linhas 22–23 | Lançamento de `IllegalArgumentException` para quilometragem |
| N7 | Linha 25 | Verificação: `horasAtraso < 0` |
| N8 | Linhas 27–28 | Lançamento de `IllegalArgumentException` para horas de atraso |
| N9 | Linha 31 | Verificação: `categoria == ECONOMICO` |
| N10 | Linha 33 | Define `valorDiaria = 120,00` |
| N11 | Linha 35 | Verificação: `categoria == INTERMEDIARIO` |
| N12 | Linha 37 | Define `valorDiaria = 180,00` |
| N13 | Linhas 39–41 | Bloco `else`; define `valorDiaria = 280,00` para SUV |
| N14 | Linha 45 | Verificação: `numeroDiarias <= 2` |
| N15 | Linhas 47–48 | Define desconto por duração igual a `0%` |
| N16 | Linha 49 | Verificação: `numeroDiarias <= 6` |
| N17 | Linhas 51–52 | Define desconto por duração igual a `5%` |
| N18 | Linha 53 | Verificação: `numeroDiarias <= 14` |
| N19 | Linhas 55–56 | Define desconto por duração igual a `10%` |
| N20 | Linhas 57–60 | Bloco `else`; define desconto por duração igual a `15%` |
| N21 | Linhas 62–64 | Calcula `valorBrutoDiarias`, `descontoDuracao` e `valorDiariasComDesconto` |
| N22 | Linhas 66 e 68 | Verificação conjunta: `nivelCliente == PRATA E numeroDiarias >= 7 E !possuiAtrasoAnterior` |
| N23 | Linhas 70–71 | Define desconto de fidelidade igual a `5%` |
| N24 | Linha 72 | Verificação conjunta: `nivelCliente == OURO E numeroDiarias >= 5 E !possuiAtrasoAnterior` |
| N25 | Linhas 74–75 | Define desconto de fidelidade igual a `10%` |
| N26 | Linhas 76–79 | Bloco `else`; define desconto de fidelidade igual a `0%` |
| N27 | Linhas 81–86 | Calcula `descontoFidelidade`, `valorFinalDiarias`, `franquiaQuilometros` e `quilometrosExcedentes` |
| N28 | Linha 88 | Verificação: `quilometrosExcedentes <= 0` |
| N29 | Linhas 90–91 | Define `custoQuilometragem = 0` |
| N30 | Linha 95 | Verificação: `quilometrosExcedentes <= 100` |
| N31 | Linhas 97–98 | Define `tarifaQuilometrica = 0,80` |
| N32 | Linha 99 | Verificação: `quilometrosExcedentes <= 300` |
| N33 | Linhas 101–102 | Define `tarifaQuilometrica = 1,00` |
| N34 | Linhas 103–106 | Bloco `else`; define `tarifaQuilometrica = 1,50` |
| N35 | Linha 107 | Calcula `custoQuilometragem` |
| N36 | Linha 111 | Verificação: `seguro == SEM_SEGURO` |
| N37 | Linhas 113–114 | Define `valorSeguroDiaria = 0` |
| N38 | Linha 115 | Verificação: `seguro == BASICO` |
| N39 | Linhas 117–118 | Define `valorSeguroDiaria = 25,00` |
| N40 | Linhas 119–122 | Bloco `else`; define `valorSeguroDiaria = 45,00` para seguro completo |
| N41 | Linha 123 | Calcula `custoSeguro` |
| N42 | Linha 126 | Verificação: `horasAtraso <= 1` |
| N43 | Linhas 128–129 | Define `custoAtraso = 0` |
| N44 | Linha 131 | Verificação: `horasAtraso <= 3` |
| N45 | Linhas 132–133 | Calcula `custoAtraso` proporcional |
| N46 | Linhas 135–137 | Bloco `else`; define `custoAtraso = valorDiaria` |
| N47 | Linhas 139–140 | Calcula o total, arredonda e retorna `total.setScale(2, RoundingMode.HALF_UP)` |
| OUT | — | Saída do grafo |

| Aresta | Origem | Destino | Condição ou fluxo |
|---|---|---|---|
| A1 | IN | N1 | Entrada do método |
| A2 | N1 | N2 | Validação nula verdadeira |
| A3 | N1 | N3 | Validação nula falsa |
| A4 | N3 | N4 | `numeroDiarias <= 0` verdadeiro |
| A5 | N3 | N5 | `numeroDiarias <= 0` falso |
| A6 | N5 | N6 | `quilometrosRodados < 0` verdadeiro |
| A7 | N5 | N7 | `quilometrosRodados < 0` falso |
| A8 | N7 | N8 | `horasAtraso < 0` verdadeiro |
| A9 | N7 | N9 | `horasAtraso < 0` falso |
| A10 | N9 | N10 | Categoria é `ECONOMICO` |
| A11 | N9 | N11 | Categoria não é `ECONOMICO` |
| A12 | N11 | N12 | Categoria é `INTERMEDIARIO` |
| A13 | N11 | N13 | Categoria não é `INTERMEDIARIO`; utiliza SUV |
| A14 | N10 | N14 | Continua após definir diária econômica |
| A15 | N12 | N14 | Continua após definir diária intermediária |
| A16 | N13 | N14 | Continua após definir diária SUV |
| A17 | N14 | N15 | `numeroDiarias <= 2` verdadeiro |
| A18 | N14 | N16 | `numeroDiarias <= 2` falso |
| A19 | N16 | N17 | `numeroDiarias <= 6` verdadeiro |
| A20 | N16 | N18 | `numeroDiarias <= 6` falso |
| A21 | N18 | N19 | `numeroDiarias <= 14` verdadeiro |
| A22 | N18 | N20 | `numeroDiarias <= 14` falso |
| A23 | N15 | N21 | Continua após desconto de duração de `0%` |
| A24 | N17 | N21 | Continua após desconto de duração de `5%` |
| A25 | N19 | N21 | Continua após desconto de duração de `10%` |
| A26 | N20 | N21 | Continua após desconto de duração de `15%` |
| A27 | N21 | N22 | Entra na decisão de fidelidade |
| A28 | N22 | N23 | Decisão de fidelidade PRATA verdadeira |
| A29 | N22 | N24 | Decisão de fidelidade PRATA falsa |
| A30 | N24 | N25 | Decisão de fidelidade OURO verdadeira |
| A31 | N24 | N26 | Decisão de fidelidade OURO falsa |
| A32 | N23 | N27 | Continua após desconto de fidelidade PRATA |
| A33 | N25 | N27 | Continua após desconto de fidelidade OURO |
| A34 | N26 | N27 | Continua sem desconto de fidelidade |
| A35 | N27 | N28 | Entra na decisão de quilometragem |
| A36 | N28 | N29 | `quilometrosExcedentes <= 0` verdadeiro |
| A37 | N28 | N30 | `quilometrosExcedentes <= 0` falso |
| A38 | N30 | N31 | `quilometrosExcedentes <= 100` verdadeiro |
| A39 | N30 | N32 | `quilometrosExcedentes <= 100` falso |
| A40 | N32 | N33 | `quilometrosExcedentes <= 300` verdadeiro |
| A41 | N32 | N34 | `quilometrosExcedentes <= 300` falso |
| A42 | N29 | N36 | Continua sem custo de quilometragem |
| A43 | N31 | N35 | Utiliza tarifa de `0,80` |
| A44 | N33 | N35 | Utiliza tarifa de `1,00` |
| A45 | N34 | N35 | Utiliza tarifa de `1,50` |
| A46 | N35 | N36 | Após calcular o custo da quilometragem |
| A47 | N36 | N37 | Seguro é `SEM_SEGURO` |
| A48 | N36 | N38 | Seguro diferente de `SEM_SEGURO` |
| A49 | N38 | N39 | Seguro é `BASICO` |
| A50 | N38 | N40 | Seguro diferente de `BASICO`; utiliza seguro completo |
| A51 | N37 | N41 | Continua após seguro sem cobrança |
| A52 | N39 | N41 | Continua após seguro básico |
| A53 | N40 | N41 | Continua após seguro completo |
| A54 | N41 | N42 | Entra na decisão de atraso |
| A55 | N42 | N43 | `horasAtraso <= 1` verdadeiro |
| A56 | N42 | N44 | `horasAtraso <= 1` falso |
| A57 | N44 | N45 | `horasAtraso <= 3` verdadeiro |
| A58 | N44 | N46 | `horasAtraso <= 3` falso |
| A59 | N43 | N47 | Continua após atraso sem cobrança |
| A60 | N45 | N47 | Continua após atraso proporcional |
| A61 | N46 | N47 | Continua após cobrança de uma diária |
| A62 | N47 | OUT | Saída normal |

### Casos de teste e rastreabilidade

<!-- Informe todos os argumentos do método em cada caso. Para testes parametrizados, identifique também a invocação correspondente. Explique a obtenção do resultado esperado a partir das regras de negócio. -->

| Caso | Entradas | Resultado ou exceção esperados | Justificativa do resultado esperado | Arestas percorridas | Teste JUnit / invocação |
|---|---|---|---|---|---|
| B1 | `ECONOMICO, COMUM, 1, 0, 0, SEM_SEGURO, false` | `R$ 120,00` | Uma diária sem descontos ou cobranças adicionais | `A1, A3, A5, A7, A9, A10, A14, A17, A23, A27, A29, A31, A34, A35, A37, A42, A47, A51, A54, A55, A59, A62` | `deveCalcularLocacaoBasica[1]` |
| B2 | `ECONOMICO, COMUM, 2, 0, 0, SEM_SEGURO, false` | `R$ 240,00` | Duas diárias sem descontos ou cobranças adicionais | `A1, A3, A5, A7, A9, A10, A14, A17, A23, A27, A29, A31, A34, A35, A37, A42, A47, A51, A54, A55, A59, A62` | `deveCalcularLocacaoBasica[2]` |
| C1 | `INTERMEDIARIO, COMUM, 1, 0, 0, SEM_SEGURO, false` | `R$ 180,00` | Categoria intermediária com diária de R$ 180,00 | `A1, A3, A5, A7, A9, A11, A12, A15, A17, A23, A27, A29, A31, A34, A35, A37, A42, A47, A51, A54, A55, A59, A62` | `casosAdicionaisDeSucesso[C1]` |
| C2 | `SUV, COMUM, 1, 0, 0, SEM_SEGURO, false` | `R$ 280,00` | Categoria SUV com diária de R$ 280,00 | `A1, A3, A5, A7, A9, A11, A13, A16, A17, A23, A27, A29, A31, A34, A35, A37, A42, A47, A51, A54, A55, A59, A62` | `casosAdicionaisDeSucesso[C2]` |
| D1 | `ECONOMICO, COMUM, 3, 0, 0, SEM_SEGURO, false` | `R$ 342,00` | Três diárias com desconto de duração de 5% | `A1, A3, A5, A7, A9, A10, A14, A18, A19, A24, A27, A29, A31, A34, A35, A37, A42, A47, A51, A54, A55, A59, A62` | `casosAdicionaisDeSucesso[D1]` |
| D2 | `ECONOMICO, COMUM, 7, 0, 0, SEM_SEGURO, false` | `R$ 756,00` | Sete diárias com desconto de duração de 10% | `A1, A3, A5, A7, A9, A10, A14, A18, A20, A21, A25, A27, A29, A31, A34, A35, A37, A42, A47, A51, A54, A55, A59, A62` | `casosAdicionaisDeSucesso[D2]` |
| D3 | `ECONOMICO, COMUM, 15, 0, 0, SEM_SEGURO, false` | `R$ 1.530,00` | Quinze diárias com desconto de duração de 15% | `A1, A3, A5, A7, A9, A10, A14, A18, A20, A22, A26, A27, A29, A31, A34, A35, A37, A42, A47, A51, A54, A55, A59, A62` | `casosAdicionaisDeSucesso[D3]` |
| P1 | `ECONOMICO, PRATA, 7, 0, 0, SEM_SEGURO, false` | `R$ 718,20` | Cliente PRATA elegível ao desconto de fidelidade de 5% | `A1, A3, A5, A7, A9, A10, A14, A18, A20, A21, A25, A27, A28, A32, A35, A37, A42, A47, A51, A54, A55, A59, A62` | `casosAdicionaisDeSucesso[P1]` |
| P2 | `ECONOMICO, PRATA, 6, 0, 0, SEM_SEGURO, false` | `R$ 684,00` | Cliente PRATA com menos de 7 diárias | `A1, A3, A5, A7, A9, A10, A14, A18, A19, A24, A27, A29, A31, A34, A35, A37, A42, A47, A51, A54, A55, A59, A62` | `casosAdicionaisDeSucesso[P2]` |
| P3 | `ECONOMICO, PRATA, 7, 0, 0, SEM_SEGURO, true` | `R$ 756,00` | Atraso anterior impede o benefício | `A1, A3, A5, A7, A9, A10, A14, A18, A20, A21, A25, A27, A28, A32, A35, A37, A42, A47, A51, A54, A55, A59, A62` | `casosAdicionaisDeSucesso[P3]` |
| P4 | `ECONOMICO, COMUM, 7, 0, 0, SEM_SEGURO, false` | `R$ 756,00` | Cliente COMUM não recebe fidelidade | `A1, A3, A5, A7, A9, A10, A14, A18, A20, A21, A25, A27, A29, A31, A34, A35, A37, A42, A47, A51, A54, A55, A59, A62` | `casosAdicionaisDeSucesso[P4]` |
| O1 | `ECONOMICO, OURO, 5, 0, 0, SEM_SEGURO, false` | `R$ 513,00` | Cliente OURO elegível ao desconto de fidelidade de 10% | `A1, A3, A5, A7, A9, A10, A14, A18, A19, A24, A27, A29, A30, A33, A35, A37, A42, A47, A51, A54, A55, A59, A62` | `casosAdicionaisDeSucesso[O1]` |
| O2 | `ECONOMICO, OURO, 4, 0, 0, SEM_SEGURO, false` | `R$ 456,00` | Cliente OURO com menos de 5 diárias | `A1, A3, A5, A7, A9, A10, A14, A18, A19, A24, A27, A29, A30, A33, A35, A37, A42, A47, A51, A54, A55, A59, A62` | `casosAdicionaisDeSucesso[O2]` |
| O3 | `ECONOMICO, OURO, 5, 0, 0, SEM_SEGURO, true` | `R$ 570,00` | Atraso anterior impede o benefício | `A1, A3, A5, A7, A9, A10, A14, A18, A19, A24, A27, A29, A30, A33, A35, A37, A42, A47, A51, A54, A55, A59, A62` | `casosAdicionaisDeSucesso[O3]` |
| O4 | `ECONOMICO, COMUM, 5, 0, 0, SEM_SEGURO, false` | `R$ 570,00` | Cliente COMUM não recebe fidelidade | `A1, A3, A5, A7, A9, A10, A14, A18, A19, A24, A27, A29, A31, A34, A35, A37, A42, A47, A51, A54, A55, A59, A62` | `casosAdicionaisDeSucesso[O4]` |
| K1 | `ECONOMICO, COMUM, 1, 100, 0, SEM_SEGURO, false` | `R$ 120,00` | Quilometragem igual à franquia | `A1, A3, A5, A7, A9, A10, A14, A17, A23, A27, A29, A31, A34, A35, A36, A42, A47, A51, A54, A55, A59, A62` | `casosAdicionaisDeSucesso[K1]` |
| K2 | `ECONOMICO, COMUM, 1, 200, 0, SEM_SEGURO, false` | `R$ 200,00` | 100 km excedentes a R$ 0,80 por km | `A1, A3, A5, A7, A9, A10, A14, A17, A23, A27, A29, A31, A34, A35, A37, A38, A43, A46, A47, A51, A54, A55, A59, A62` | `casosAdicionaisDeSucesso[K2]` |
| K3 | `ECONOMICO, COMUM, 1, 400, 0, SEM_SEGURO, false` | `R$ 420,00` | 300 km excedentes a R$ 1,00 por km | `A1, A3, A5, A7, A9, A10, A14, A17, A23, A27, A29, A31, A34, A35, A37, A39, A40, A44, A46, A47, A51, A54, A55, A59, A62` | `casosAdicionaisDeSucesso[K3]` |
| K4 | `ECONOMICO, COMUM, 1, 500, 0, SEM_SEGURO, false` | `R$ 720,00` | 400 km excedentes a R$ 1,50 por km | `A1, A3, A5, A7, A9, A10, A14, A17, A23, A27, A29, A31, A34, A35, A37, A39, A41, A45, A46, A47, A51, A54, A55, A59, A62` | `casosAdicionaisDeSucesso[K4]` |
| S1 | `ECONOMICO, COMUM, 1, 0, 0, BASICO, false` | `R$ 145,00` | Diária mais seguro básico | `A1, A3, A5, A7, A9, A10, A14, A17, A23, A27, A29, A31, A34, A35, A37, A42, A48, A49, A52, A54, A55, A59, A62` | `casosAdicionaisDeSucesso[S1]` |
| S2 | `ECONOMICO, COMUM, 1, 0, 0, COMPLETO, false` | `R$ 165,00` | Diária mais seguro completo | `A1, A3, A5, A7, A9, A10, A14, A17, A23, A27, A29, A31, A34, A35, A37, A42, A48, A50, A53, A54, A55, A59, A62` | `casosAdicionaisDeSucesso[S2]` |
| A1 | `ECONOMICO, COMUM, 1, 0, 1, SEM_SEGURO, false` | `R$ 120,00` | Até 1 hora de atraso não gera cobrança | `A1, A3, A5, A7, A9, A10, A14, A17, A23, A27, A29, A31, A34, A35, A37, A42, A47, A51, A54, A55, A59, A62` | `casosAdicionaisDeSucesso[A1]` |
| A2 | `ECONOMICO, COMUM, 1, 0, 2, SEM_SEGURO, false` | `R$ 168,00` | Duas horas geram cobrança proporcional | `A1, A3, A5, A7, A9, A10, A14, A17, A23, A27, A29, A31, A34, A35, A37, A42, A47, A51, A54, A56, A57, A60, A62` | `casosAdicionaisDeSucesso[A2]` |
| A3 | `ECONOMICO, COMUM, 1, 0, 4, SEM_SEGURO, false` | `R$ 240,00` | Quatro horas geram cobrança de uma diária adicional | `A1, A3, A5, A7, A9, A10, A14, A17, A23, A27, A29, A31, A34, A35, A37, A42, A47, A51, A54, A56, A58, A61, A62` | `casosAdicionaisDeSucesso[A3]` |
| E1 | `null, COMUM, 1, 0, 0, SEM_SEGURO, false` | `NullPointerException` | Categoria nula | `A1, A2` | `deveLancarExcecaoQuandoCategoriaForNula` |
| E2 | `ECONOMICO, null, 1, 0, 0, SEM_SEGURO, false` | `NullPointerException` | Nível do cliente nulo | `A1, A2` | `deveLancarExcecaoQuandoNivelClienteForNulo` |
| E3 | `ECONOMICO, COMUM, 1, 0, 0, null, false` | `NullPointerException` | Seguro nulo | `A1, A2` | `deveLancarExcecaoQuandoSeguroForNulo` |
| E4 | `ECONOMICO, COMUM, 0, 0, 0, SEM_SEGURO, false` | `IllegalArgumentException` | Número de diárias inválido | `A1, A3, A4` | `deveLancarExcecaoQuandoNumeroDeDiariasForInvalido` |
| E5 | `ECONOMICO, COMUM, 1, -1, 0, SEM_SEGURO, false` | `IllegalArgumentException` | Quilometragem negativa | `A1, A3, A5, A6` | `deveLancarExcecaoQuandoQuilometragemForNegativa` |
| E6 | `ECONOMICO, COMUM, 1, 0, -1, SEM_SEGURO, false` | `IllegalArgumentException` | Horas de atraso negativas | `A1, A3, A5, A7, A8` | `deveLancarExcecaoQuandoHorasDeAtrasoForemNegativas` |
| E7 | `null, COMUM, 0, 0, 0, SEM_SEGURO, false` | `NullPointerException` | Exceção nula prevalece sobre diárias inválidas | `A1, A2` | `devePriorizarNuloQuandoTambemHouverDiariasInvalidas` |


### Resultado da cobertura

| Medida | Resultado |
|---|---|
| Total de arestas do grafo | 62 |
| Arestas percorridas | 62 |
| Cobertura de arestas (%) | 100% |
| Ramos cobertos do método no JaCoCo | 48 |
| Ramos não cobertos do método no JaCoCo | 0 |
| Cobertura de ramos (%) | 100% |

### Análise da correspondência entre grafo, testes e JaCoCo, incluindo exceções:
O GFC possui 47 nós numerados. A tabela de arestas possui 62
arestas.

Com base na rastreabilidade dos casos de teste, as 62 arestas identificadas
no GFC foram percorridas pelo conjunto de testes, resultando em:

Cobertura de arestas = 62 / 62 × 100 = 100%.

Na execução pelo JaCoCo, foram registrados 48 ramos cobertos
e nenhum ramo não coberto na classe analisada, correspondendo a 100% de
cobertura de ramos.

Evidências HTML, XML e log (links relativos):\
[Relátorio HTML Cobertura de Arestas](evidencias/site/jacoco/arestas/index.html)\
[Relátorio XML Cobertura de Arestas](evidencias/site/jacoco/arestas/jacoco.xml)\
[Log de execução Cobertura de Arestas](evidencias/site/jacoco/arestas/log/arestas.log)


## 5.2. Critério de McCabe

Classe: `CaminhosBasicosTest.java`. Registre os resultados de sua execução isolada.

### Complexidade ciclomática

| Elemento | Contagem |
|---|---|
| Nós (N) | 55 |
| Arestas (E) | 78 |
| Decisões binárias | 24 |

- Cálculo por `E - N + 2`: 78 - 55 + 2 = 25.
- Cálculo por decisões binárias + 1: 24 + 1 = 25.
- Convenção de saída única e tratamento do curto-circuito:  O grafo possui uma entrada IN e uma saída OUT únicas. As saídas por exceção dos nós B2, B4, B6 e B8 também apontam para OUT. As decisões compostas foram expandidas em condições atômicas: B1a/B1b/B1c representam categoria == null, nivelCliente == null e seguro == null; B22a/B22b/B22c representam nivelCliente == PRATA, numeroDiarias >= 7 e !possuiAtrasoAnterior; B24a/B24b/B24c representam nivelCliente == OURO, numeroDiarias >= 5 e !possuiAtrasoAnterior. Assim, o curto-circuito de || e && aparece como arestas separadas no grafo.
- Comparação com a complexidade do método no JaCoCo e explicação de divergências: o relatório target/site/jacoco/arestas/jacoco.xml informa complexidade 25 para calcularCustoLocacao; o total da classe é 26 porque inclui o construtor implícito. O valor coincide com E - N + 2 = 25 e com 24 + 1 = 25. Se as decisões compostas fossem contadas como um único nó, teríamos 18 decisões e V(G) = 19; a diferença de 6 corresponde exatamente às duas condições adicionais de cada uma das três decisões com curto-circuito (B1, B22 e B24).

### Caminhos independentes e testes

- Ordem das entradas: categoria/nível/diárias/km/horas/seguro/atraso anterior.
- Abreviações: ECO = ECONOMICO, INT = INTERMEDIARIO, COM = COMUM, PRA = PRATA, OUR = OURO, SEM = SEM_SEGURO, BAS = BASICO, CMP = COMPLETO; N = sem atraso anterior, S = com atraso anterior.
- Para economizar espaço na tabela, foram definidos os seguintes trechos comuns:
PREFIXO = IN -> B1a -> B1b -> B1c -> B3 -> B5 -> B7
SUFIXO  = B27 -> B28 -> B29 -> B36 -> B37 -> B41 -> B42 -> B43 -> B47 -> OUT

| Caminho | Sequência de nós ou arestas | Caso / entradas | Resultado esperado | Teste JUnit / invocação |
|---|---|---|---|---|
| P1 | PREFIXO → B9 → B10 → B14 → B15 → B21 → B22a → B24a → B26 → SUFIXO | ECO/COM/1/0/0/SEM/N | 120,00 | deveExecutarCaminhoDeSucesso[P1] |
| P2 | IN → B1a → B2 → OUT | null/COM/1/0/0/SEM/N | NullPointerException | caminhoP2DeveLancarExcecaoQuandoCategoriaForNula|
| P3 |IN → B1a → B1b → B2 → OUT |	ECO/null/1/0/0/SEM/N |	NullPointerException |	caminhoP3DeveLancarExcecaoQuandoNivelClienteForNulo |
|P4 |	IN → B1a → B1b → B1c → B2 → OUT |	ECO/COM/1/0/0/null/N |	NullPointerException |	caminhoP4DeveLancarExcecaoQuandoSeguroForNulo |
|P5 |	IN → B1a → B1b → B1c → B3 → B4 → OUT |	ECO/COM/0/0/0/SEM/N |	IllegalArgumentException |	caminhoP5DeveLancarExcecaoQuandoNumeroDeDiariasForInvalido |
|P6 |	IN → B1a → B1b → B1c → B3 → B5 → B6 → OUT |	ECO/COM/1/-1/0/SEM/N |	IllegalArgumentException |	caminhoP6DeveLancarExcecaoQuandoQuilometragemForNegativa |
|P7 |	IN → B1a → B1b → B1c → B3 → B5 → B7 → B8 → OUT |	ECO/COM/1/0/-1/SEM/N |	IllegalArgumentException |	caminhoP7DeveLancarExcecaoQuandoHorasDeAtrasoForemNegativas |
|P8 |	PREFIXO → B9 → B11 → B12 → B14 → B15 → B21 → B22a → B24a → B26 → SUFIXO |	INT/COM/1/0/0/SEM/N |	180,00 |	deveExecutarCaminhoDeSucesso[P8] |
|P9 |	PREFIXO → B9 → B11 → B13 → B14 → B15 → B21 → B22a → B24a → B26 → SUFIXO |	SUV/COM/1/0/0/SEM/N |	280,00 |	deveExecutarCaminhoDeSucesso[P9] |
|P10 |	PREFIXO → B9 → B10 → B14 → B16 → B17 → B21 → B22a → B24a → B26 → SUFIXO |	ECO/COM/3/0/0/SEM/N |	342,00 |	deveExecutarCaminhoDeSucesso[P10] |
|P11 |	PREFIXO → B9 → B10 → B14 → B16 → B18 → B19 → B21 → B22a → B24a → B26 → SUFIXO |	ECO/COM/7/0/0/SEM/N |	756,00 |	deveExecutarCaminhoDeSucesso[P11] |
|P12 |	PREFIXO → B9 → B10 → B14 → B16 → B18 → B20 → B21 → B22a → B24a → B26 → SUFIXO |	ECO/COM/15/0/0/SEM/N |	1530,00 |	deveExecutarCaminhoDeSucesso[P12] |
|P13 |	PREFIXO → B9 → B10 → B14 → B15 → B21 → B22a → B22b → B22c → B23 → SUFIXO |	ECO/PRA/7/0/0/SEM/N |	718,20 |	deveExecutarCaminhoDeSucesso[P13] |
|P14 |	PREFIXO → B9 → B10 → B14 → B15 → B21 → B22a → B22b → B24a → B26 → SUFIXO	|	ECO/PRA/6/0/0/SEM/N	|	684,00	|	deveExecutarCaminhoDeSucesso[P14] |
|P15 |	PREFIXO → B9 → B10 → B14 → B15 → B21 → B22a → B22b → B22c → B24a → B26 → SUFIXO|	ECO/PRA/7/0/0/SEM/S|	756,00	|deveExecutarCaminhoDeSucesso[P15]|
|P16 |	PREFIXO → B9 → B10 → B14 → B15 → B21 → B22a → B24a → B24b → B24c → B25 → SUFIXO|	ECO/OUR/5/0/0/SEM/N|	513,00	|deveExecutarCaminhoDeSucesso[P16]|
|P17 |	PREFIXO → B9 → B10 → B14 → B15 → B21 → B22a → B24a → B24b → B26 → SUFIXO|	ECO/OUR/4/0/0/SEM/N	456,00|	deveExecutarCaminhoDeSucesso[P17]|
|P18 |	PREFIXO → B9 → B10 → B14 → B15 → B21 → B22a → B24a → B24b → B24c → B26 → SUFIXO|	ECO/OUR/5/0/0/SEM/S|	570,00	|deveExecutarCaminhoDeSucesso[P18]|
|P19 |	PREFIXO → B9 → B10 → B14 → B15 → B21 → B22a → B24a → B26 → B27 → B28 → B30 → B31 → B35 → B36 → B37 → B41 → B42 → B43 → B47 → OUT|	ECO/COM/1/200/0/SEM/N|	200,00	|deveExecutarCaminhoDeSucesso[P19]|
|P20 |	PREFIXO → B9 → B10 → B14 → B15 → B21 → B22a → B24a → B26 → B27 → B28 → B30 → B32 → B33 → B35 → B36 → B37 → B41 → B42 → B43 → B47 → OUT|	ECO/COM/1/400/0/SEM/N|	420,00	|deveExecutarCaminhoDeSucesso[P20]|
|P21 |	PREFIXO → B9 → B10 → B14 → B15 → B21 → B22a → B24a → B26 → B27 → B28 → B30 → B32 → B34 → B35 → B36 → B37 → B41 → B42 → B43 → B47 → OUT|	ECO/COM/1/500/0/SEM/N|	720,00	|deveExecutarCaminhoDeSucesso[P21]|
|P22 |	PREFIXO → B9 → B10 → B14 → B15 → B21 → B22a → B24a → B26 → B27 → B28 → B29 → B36 → B38 → B39 → B41 → B42 → B43 → B47 → OUT|	ECO/COM/1/0/0/BAS/N|	145,00	|deveExecutarCaminhoDeSucesso[P22]|
|P23 |	PREFIXO → B9 → B10 → B14 → B15 → B21 → B22a → B24a → B26 → B27 → B28 → B29 → B36 → B38 → B40 → B41 → B42 → B43 → B47 → OUT|	ECO/COM/1/0/0/CMP/N|	165,00	|deveExecutarCaminhoDeSucesso[P23]|
|P24 |	PREFIXO → B9 → B10 → B14 → B15 → B21 → B22a → B24a → B26 → B27 → B28 → B29 → B36 → B37 → B41 → B42 → B44 → B45 → B47 → OUT|	ECO/COM/1/0/2/SEM/N|	168,00	|deveExecutarCaminhoDeSucesso[P24]|
|P25 |	PREFIXO → B9 → B10 → B14 → B15 → B21 → B22a → B24a → B26 → B27 → B28 → B29 → B36 → B37 → B41 → B42 → B44 → B46 → B47 → OUT|	ECO/COM/1/0/4/SEM/N|	240,00	|deveExecutarCaminhoDeSucesso[P25]|

### Demonstração de independência

Foi utilizada a construção incremental: P1 é o caminho base e cada caminho seguinte introduz pelo menos uma aresta que não aparece em nenhum caminho anterior. A tabela abaixo identifica essas arestas.
<!-- Inclua a matriz caminho × aresta e sua análise ou a demonstração incremental exigida pelo enunciado. -->

| Caminho	| Aresta(s) nova(s) em relação aos caminhos anteriores |
|---|---|
|P1	|Define a base do conjunto|
|P2 |	B1a→B2, B2→OUT|
|P3 |	B1b→B2|
|P4 |	B1c→B2|
|P5 |	B3→B4, B4→OUT|
|P6 |	B5→B6, B6→OUT|
|P7 |	B7→B8, B8→OUT|
|P8 |	B9→B11, B11→B12, B12→B14|
|P9 |	B11→B13, B13→B14|
|P10|	B14→B16, B16→B17, B17→B21|
|P11|	B16→B18, B18→B19, B19→B21|
|P12|	B18→B20, B20→B21|
|P13|	B22a→B22b, B22b→B22c, B22c→B23, B23→B27|
|P14|	B22b→B24a|
|P15|	B22c→B24a|
|P16|	B24a→B24b, B24b→B24c, B24c→B25, B25→B27|
|P17|	B24b→B26|
|P18|	B24c→B26|
|P19|	B28→B30, B30→B31, B31→B35, B35→B36|
|P20|	B30→B32, B32→B33, B33→B35|
|P21|	B32→B34, B34→B35|
|P22|	B36→B38, B38→B39, B39→B41|
|P23|	B38→B40, B40→B41|
|P24|	B42→B44, B44→B45, B45→B47|
|P25|	B44→B46, B46→B47|

Como cada caminho a partir de P2 acrescenta pelo menos uma aresta nova, os 25 caminhos são linearmente independentes. O conjunto percorre as 78 arestas do grafo.

### Caminhos inviáveis e limitações

<!-- Quando houver, apresente as restrições contraditórias, as alternativas buscadas e a justificativa para o tamanho do conjunto viável obtido. Caso não haja, registre isso após a análise. -->
Não há caminhos inviáveis. As 24 decisões atômicas são alcançáveis e os 25 caminhos escolhidos são executáveis. As dependências impostas pelo curto-circuito foram consideradas na 
construção:por exemplo, quando nivelCliente != PRATA, as condições numeroDiarias >= 7 e !possuiAtrasoAnterior da decisão PRATA não são avaliadas, mas o caminho que segue para 
a decisão OURO é viável e independente.

#### Resultado da execução

|Medida	|Resultado|
|---|---|
|Testes executados|	25|
|Falhas|	0|
|Erros|	0|
|Ignorados|	0|
|Ramos cobertos no JaCoCo|	48 de 48|
|Linhas cobertas no JaCoCo|	53 de 53|
|Complexidade do método no JaCoCo|	25|

Evidências:\
[Relátorio HTML do McCABE](evidencias/site/jacoco/caminhos/index.html)\
[Relátorio XML do McCABE](evidencias/site/jacoco/caminhos/jacoco.xml)\
[Log de execução do McCABE](evidencias/site/jacoco/caminhos/log/caminhos.log)



## 5.3. MC/DC em duas decisões

Classe: `McdcTest.java`. Registre os resultados de sua execução isolada.

### Decisão 1

- Expressão e localização no código: linha 68 de `CalculadoraCustoLocacao.java`.
- Condição A: nivelCliente == NivelCliente.PRATA.
- Condição B: numeroDiarias >= 7.
- Condição C: !possuiAtrasoAnterior.

| Caso / entradas | A (valor lógico) | B (valor lógico) | C (valor lógico) | Condições efetivamente avaliadas | Resultado da decisão | Resultado esperado do método | Teste JUnit / invocação |
|---|---|---|---|---|---|---|---|
| PRATA-BASE: ECONOMICO, PRATA, 7, 0, 0, SEM_SEGURO, false | V | V | V | A, B e C | V | 718,20 | deveAtenderMcdcDaDecisaoPrata |
| PRATA-A: ECONOMICO, COMUM, 7, 0, 0, SEM_SEGURO, false | F | V | V | A | F | 756,00 | deveAtenderMcdcDaDecisaoPrata |
| PRATA-B: ECONOMICO, PRATA, 6, 0, 0, SEM_SEGURO, false | V | F | V | A e B | F | 684,00 | deveAtenderMcdcDaDecisaoPrata |
| PRATA-C: ECONOMICO, PRATA, 7, 0, 0, SEM_SEGURO, true | V | V | F | A, B e C | F | 756,00 | deveAtenderMcdcDaDecisaoPrata |

| Condição | Par de casos | Demonstração da influência independente |
|---|---|---|
| A | PRATA-BASE × PRATA-A | B e C permanecem verdadeiras. A muda de V para F e a decisão muda de V para F. |
| B | PRATA-BASE × PRATA-B | A e C permanecem verdadeiras. B muda de V para F e a decisão muda de V para F. |
| C | PRATA-BASE × PRATA-C | A e B permanecem verdadeiras. C muda de V para F e a decisão muda de V para F. |

Justificativa de alcance da decisão, curto-circuito e atendimento ao MC/DC de causa única:

O caso PRATA-BASE é V porque o cliente é PRATA,
possui pelo menos sete diárias e não possui atraso anterior.
No caso PRATA-A, somente A assume valor F. Como o operador utilizado
é &&, a avaliação é interrompida após A e B e C não são
avaliadas durante a execução.\
No caso PRATA-B, A é V e B é F. Por isso, C não é avaliada devido ao curto-circuito.\
No caso PRATA-C, as três condições são avaliadas, mas C é F porque
existe atraso anterior.\
Os pares PRATA-BASE × PRATA-A, PRATA-BASE × PRATA-B e
PRATA-BASE × PRATA-C demonstram que cada condição pode alterar
independentemente o resultado da decisão.\
Portanto, a decisão PRATA atende
ao MC/DC de causa única.

### Decisão 2

- Expressão e localização no código: linha 72 de CalculadoraCustoLocacao.java.
- Condição A: nivelCliente == NivelCliente.OURO.
- Condição B: numeroDiarias >= 5.
- Condição C: !possuiAtrasoAnterior.

| Caso / entradas | A (valor lógico) | B (valor lógico) | C (valor lógico) | Condições efetivamente avaliadas | Resultado da decisão | Resultado esperado do método | Teste JUnit / invocação |
|---|---|---|---|---|---|---|---|
| OURO-BASE: ECONOMICO, OURO, 5, 0, 0, SEM_SEGURO, false |  V |  V |  V | A, B e C | V | R$ 513,00 | deveAtenderMcdcDaDecisaoOuro |
| OURO-A: ECONOMICO, COMUM, 5, 0, 0, SEM_SEGURO, false   |  F |  V |  V | A | F | R$ 570,00 | deveAtenderMcdcDaDecisaoOuro |
| OURO-B: ECONOMICO, OURO, 4, 0, 0, SEM_SEGURO, false    |  V |  F |  V | A e B | F | R$ 456,00 | deveAtenderMcdcDaDecisaoOuro |
| OURO-C: ECONOMICO, OURO, 5, 0, 0, SEM_SEGURO, true     |  V |  V |  F | A, B e C | F | R$ 570,00 | deveAtenderMcdcDaDecisaoOuro |

| Condição | Par de casos | Demonstração da influência independente |
|---|---|---|
| A | OURO-BASE × OURO-A | B e C permanecem verdadeiras. A muda de V para F e a decisão muda de V para F. |
| B | OURO-BASE × OURO-B | A e C permanecem verdadeiras. B muda de V para F e a decisão muda de V para F. |
| C | OURO-BASE × OURO-C | A e B permanecem verdadeiras. C muda de V para F e a decisão muda de V para F. |

Justificativa de alcance da decisão, curto-circuito e atendimento ao MC/DC de causa única:\
A decisão OURO é alcançada nos casos em que a decisão PRATA anterior é
F.\
No caso OURO-BASE, o cliente é OURO, possui pelo menos cinco
diárias e não possui atraso anterior. Portanto, as três condições são
verdadeiras e o desconto de 10% é aplicado.\
No caso OURO-A, A é F porque o cliente é COMUM. Assim, B e C não são
avaliadas pela expressão OURO.\
No caso OURO-B, A é verdadeira e B é falsa porque há somente quatro
diárias. Dessa forma, C não é avaliada.\
No caso OURO-C, A e B são verdadeiras, mas C é falsa devido à existência
de atraso anterior. As três condições são avaliadas.\
Os pares OURO-BASE × OURO-A, OURO-BASE × OURO-B e
OURO-BASE × OURO-C demonstram a influência independente de A, B e C.
Portanto, a decisão OURO atende ao MC/DC de causa única.

## 5.4. Teste de mutação com PIT

Escopo: suíte completa das três classes de teste.
- `CoberturaArestasTest`;
- `CaminhosBasicosTest`;
- `McdcTest`.

### Configuração e resultados

- Escopo e operadores utilizados: `br.com.aluguelcarros.CalculadoraCustoLocacao`.
- Comando executado e resultado da execução Maven: `mvn -Ppitest verify`.
- Total de mutantes: 40.
- Percentual de mutantes com status `KILLED`: 98%.

| Status no PIT | Quantidade |
|---|---|
| KILLED | 39 |
| SURVIVED | 1 |
| NO_COVERAGE | 0 |
| TIMED_OUT | 0 |
| NON_VIABLE | 0 |
| MEMORY_ERROR | 0 |
| NOT_STARTED | 0 |
| STARTED | 0 |
| RUN_ERROR | 0 |
| **Total** | **40** |

### Testes acrescentados ou aprimorados após a análise:
Foram adicionados três casos de fronteira em `CoberturaArestasTest.java`:\
`D4`, com 14 diárias e resultado esperado de `R$ 1.512,00`, para a condição
`numeroDiarias <= 14`;\
`K5`, com 2 diárias e 300 km, com resultado esperado de `R$ 320,00`, para validar a franquia calculada por
`100L * numeroDiarias`;\
`A4`, com 3 horas de atraso e resultado esperado de `R$ 192,00`, para a condição `horasAtraso <= 3`. Os três casos mataram
três mutantes que haviam sobrevivido na execução inicial.\
O mutante restante, na linha 88, foi documentado individualmente como possível
mutante equivalente.

Evidências HTML, XML e log (links relativos):\
[Relátorio HTML do PIT](evidencias/pit-reports/index.html)\
[Relátorio XML do PIT](evidencias/pit-reports/mutations.xml)\
[Log da execução do PIT](evidencias/pit-reports/log/pitest.log)

### Análise individual de mutantes não mortos

<!-- Repita os campos abaixo para cada mutante não morto. Se todos foram mortos, registre esse resultado e a evidência correspondente. -->

- Identificação, linha e operador:\
  Mutante localizado na classe `br.com.aluguelcarros.CalculadoraCustoLocacao`, método
  `calcularCustoLocacao`, linha 88, utilizando o operador
  `ConditionalsBoundaryMutator`.
- Alteração realizada pelo mutante: alteração da fronteira da condição.
- Status no PIT: SURVIVED.
- Tentativas de teste e resultados: ---
- Justificativa técnica da impossibilidade, quando aplicável: Quando quilometrosExcedentes é negativo, o código não cobra quilometragem. Quando o excedente é igual a zero, o
  código atribui diretamente BigDecimal.ZERO.
- Demonstração de equivalência para todo o domínio previsto, quando alegada: ---
- Eventual flexibilização do bloqueio Maven e justificativa: ---

## 5.5. Test smells com tsDetect

Escopo: suíte completa das três classes de teste.

### Configuração e abrangência

- Versão/commit e adaptações utilizadas: e89ce1a28d56db314d159e8957591aef6002173d.
- Comando executado, requisitos e limiares: junit5-magic-zero-v1.
- Quantidade e identificação dos arquivos e métodos esperados: 3 arquivos e 19 métodos de teste, conforme a suíte entregue:
  - `CoberturaArestasTest.java` (9 métodos): `deveCalcularLocacaoBasica`, `deveCobrirCasosAdicionaisDeSucesso`, `deveLancarExcecaoQuandoCategoriaForNula`, `deveLancarExcecaoQuandoNivelClienteForNulo`, `deveLancarExcecaoQuandoSeguroForNulo`, `deveLancarExcecaoQuandoNumeroDeDiariasForInvalido`, `deveLancarExcecaoQuandoQuilometragemForNegativa`, `deveLancarExcecaoQuandoHorasDeAtrasoForemNegativas`, `devePriorizarNuloQuandoTambemHouverDiariasInvalidas`;
  - `CaminhosBasicosTest.java` (7 métodos): `deveExecutarCaminhoDeSucesso`, `caminhoP2DeveLancarExcecaoQuandoCategoriaForNula`, `caminhoP3DeveLancarExcecaoQuandoNivelClienteForNulo`, `caminhoP4DeveLancarExcecaoQuandoSeguroForNulo`, `caminhoP5DeveLancarExcecaoQuandoNumeroDeDiariasForInvalido`, `caminhoP6DeveLancarExcecaoQuandoQuilometragemForNegativa`, `caminhoP7DeveLancarExcecaoQuandoHorasDeAtrasoForemNegativas`;
  - `McdcTest.java` (3 métodos): `deveCalcularLocacaoBasica`, `deveAtenderMcdcDaDecisaoPrata`, `deveAtenderMcdcDaDecisaoOuro`.

- Quantidade e identificação dos arquivos e métodos analisados:  3 arquivos e 19 métodos, os mesmos relacionados acima. O `summary.json` registra `"arquivos": 3`, `"metodos": 19` e `"metodos_por_classe"` com `CaminhosBasicosTest = 7`, `CoberturaArestasTest = 9` e `McdcTest = 3`. O `input.csv` contém 3 linhas de arquivos e o `test-smells.csv` contém 3 linhas de resultados. O Surefire registrou 69 execuções de teste, pois os métodos parametrizados são executados várias vezes; todas as 69 execuções terminam sem falhas, erros ou testes ignorados.
- Conferência de reconhecimento dos recursos JUnit utilizados: foram reconhecidos 13 métodos anotados com `@Test` e 6 com `@ParameterizedTest`, totalizando os 19 métodos distintos. Os provedores `@CsvSource` e `@MethodSource` foram reconhecidos, inclusive nos métodos com nome de exibição customizado (`name = "{0}"`). Não há uso de `@RepeatedTest`, `@Disabled` ou teste ignorado. A contagem por classe do tsDetect coincide com a quantidade de métodos distintos registrada pelo Surefire (`CoberturaArestasTest = 9`, `CaminhosBasicosTest = 7`, `McdcTest = 3`), e as asserções utilizadas (`assertThrows`, `assertThat` e `assertEquals`) foram reconhecidas, sem ocorrência de `Unknown Test`.

### Ocorrências e refatorações

<!-- Registre todos os tipos de smell analisados, inclusive aqueles com zero ocorrências. -->

| Tipo de smell | Ocorrências iniciais | Refatoração realizada, se necessária | Ocorrências finais |
|---|---|---|---|
| Lazy Test | 5 (2 em CoberturaArestasTest; 3 em McdcTest) | Extração/uso do helper calcular(...), sem alterar asserções ou casos| 0 |

Verificação de preservação das asserções, cobertura, caminhos de McCabe, MC/DC e resultado de mutação após as refatorações: após a refatoração, a suíte foi integralmente reexecutada e as asserções e os casos de teste foram preservados: 69 execuções, 0 falhas, 0 erros e 0 testes ignorados. A cobertura de arestas permaneceu em 62/62 arestas do grafo (100%) e 48/48 ramos no JaCoCo. O critério de McCabe manteve V(G) = 25, os 25 caminhos independentes e os 25 testes correspondentes, com 78/78 arestas, 48/48 ramos e 53/53 linhas cobertas. O MC/DC das duas decisões (PRATA e OURO) foi preservado com os mesmos pares de independência e os 10 casos da classe `McdcTest`. No PIT, permaneceram 40 mutantes: 39 com status `KILLED` e 1 com status `SURVIVED`, o mutante equivalente da linha 88 já justificado individualmente na seção 5.4; não houve `NO_COVERAGE`. No tsDetect, a reanálise resultou em 0 ocorrências nos 21 detectores.

Evidências do relatório original, resumo de abrangência e logs (links relativos): Evidências da preservação: [JaCoCo arestas](evidencias/site/jacoco/arestas/jacoco.xml), [JaCoCo caminhos](evidencias/site/jacoco/caminhos/jacoco.xml), [JaCoCo MC/DC](evidencias/site/jacoco/mcdc/jacoco.xml), [PIT mutations.xml](evidencias/pit-reports/mutations.xml), [Surefire suite](evidencias/surefire-reports/suite/) e [tsDetect final](evidencias/tsdetect/final/summary.json).

## 5.6. Automação e relatório consolidado

### Ambiente e versões

| Componente | Versão utilizada |
|---|---|
| Java | OpenJDK 21.0.11 (Corretto 21.0.11.10.1) |
| Maven | Apache Maven 3.9.16  |
| JUnit | JUnit Jupiter 5.11.4 (com AssertJ 3.27.6) |
| JaCoCo | 0.8.12  |
| PIT e plugin JUnit 5 | PIT 1.17.3; pitest-junit5-plugin 1.2.1 |
| tsDetect e adaptação | commit `e89ce1a28d56db314d159e8957591aef6002173d`; adaptação `junit5-magic-zero-v1`; JAR `TestSmellDetector-0.1-jar-with-dependencies.jar` |
| Python e Git | Python 3.14.4; Git 2.53.0 |

### Execuções finais

<!-- Registre os comandos efetivamente executados na versão final da entrega. -->

| Análise | Comando e perfil | Data da execução | Resultado Maven | Relatório / log (link relativo) |
|---|---|---|---|---|
| JUnit |`mvn test` (perfil padrão; `test.group=suite`, `tests.include=**/*Test.java`) | 03/11/2026 | BUILD SUCCESS; 69 testes, 0 falhas, 0 erros, 0 ignorados | [Relatórios Surefire](evidencias/surefire-reports/suite/); [log JUnit](evidencias/surefire-reports/suite/junit.log)  |
| JaCoCo / arestas isoladas |  `mvn -Pjacoco,arestas clean verify` | 03/11/2026 | BUILD SUCCESS; 34 execuções; 62/62 arestas (100%); 48/48 ramos | [HTML](evidencias/site/jacoco/arestas/index.html); [XML](evidencias/site/jacoco/arestas/jacoco.xml); [log](evidencias/site/jacoco/arestas/log/arestas.log) |
| JaCoCo / caminhos isolados | `mvn -Pjacoco,caminhos verify` | 03/11/2026 | BUILD SUCCESS; 25 execuções; V(G) = 25; 48/48 ramos | [HTML](evidencias/site/jacoco/caminhos/index.html); [XML](evidencias/site/jacoco/caminhos/jacoco.xml); [log](evidencias/site/jacoco/caminhos/log/caminhos.log) |
| JaCoCo / MC/DC isolado |  `mvn -Pjacoco,mcdc verify` | 03/11/2026 | BUILD SUCCESS; 10 execuções; duas decisões MC/DC | [HTML](evidencias/site/jacoco/mcdc/index.html); [XML](evidencias/site/jacoco/mcdc/jacoco.xml); [log](evidencias/site/jacoco/mcdc/log/mcdc.log) |
| PIT | `mvn -Ppitest -Dpit.threshold=0 -Dquality.skip=true verify` | 03/11/2026 | BUILD SUCCESS na execução diagnóstica; 40 mutantes: 39 `KILLED` e 1 `SURVIVED` | [HTML](evidencias/pit-reports/index.html); [XML](evidencias/pit-reports/mutations.xml); [log](evidencias/pit-reports/log/pitest.log) |
| tsDetect | `mvn -Ptsdetect verify` | 03/11/2026 | BUILD SUCCESS; 3 arquivos, 19 métodos, 0 ocorrências | [Resumo](evidencias/tsdetect/final/summary.json); [CSV](evidencias/tsdetect/final/test-smells.csv); [log](evidencias/tsdetect/final/tsdetect.log); [evidência inicial](evidencias/tsdetect/initial/summary.json) |

- Testes executados: 69.
- Falhas: 0.
- Erros: 0.
- Testes ignorados: 0.
- Alterações na automação ou opções de execução, se houver: não houve alteração no `pom.xml`, nos scripts de apoio, nos limiares ou nos detectores do tsDetect. A refatoração dos testes está documentada na seção 5.5. A única opção não padrão foi a execução diagnóstica do PIT com `-Dpit.threshold=0 -Dquality.skip=true`, necessária para gerar o relatório com o mutante equivalente da linha 88, já justificado individualmente na seção 5.4. Fora isso, os comandos são os documentados no `README.md`.
- Identificação da versão final analisada (commit, se disponível): A preencher.

### Conclusão por requisito

| Requisito | Resultado alcançado | Evidência / seção | Limitações ou justificativas |
|---|---|---|---|
| 100% de cobertura de arestas | Atendido: 62/62 arestas do grafo (100%) e 48/48 ramos no JaCoCo | Seção 5.1; [JaCoCo arestas](evidencias/site/jacoco/arestas/index.html) | Nenhuma  |
| Caminhos básicos de McCabe | Atendido: V(G) = 25; 25 caminhos independentes; 25 testes; 78/78 arestas | Seção 5.2; [JaCoCo caminhos](evidencias/site/jacoco/caminhos/index.html) | Nenhuma |
| MC/DC nas duas decisões | Atendido: decisões PRATA e OURO com pares de independência; 10 casos em `McdcTest` | Seção 5.3; [JaCoCo MC/DC](evidencias/site/jacoco/mcdc/index.html) | Nenhuma |
| Todos os mutantes mortos ou impossibilidades justificadas |  39/40 mutantes `KILLED` (97,5%); 1 `SURVIVED` equivalente justificado; 0 `NO_COVERAGE` | Seção 5.4; [PIT](evidencias/pit-reports/index.html) | 1 mutante equivalente na linha 88; exceção justificada individualmente na seção 5.4 |
| Zero ocorrências no tsDetect |  Atendido: 3 arquivos, 19 métodos e 0 ocorrências nos 21 detectores | Seção 5.5; [resumo final](evidencias/tsdetect/final/summary.json) | Resultado válido para os detectores e limiares utilizados; não representa ausência absoluta de qualquer problema de qualidade |
| Execução reproduzível pelo Maven | Atendido: perfis `jacoco,arestas`, `jacoco,caminhos`, `jacoco,mcdc`, `pitest` e `tsdetect`, com comandos no `README.md` | `README.md`; seção 5.6 | A primeira execução do tsDetect exige internet para baixar e compilar o commit fixo; o PIT usa execução diagnóstica por causa do mutante equivalente |

## 6. Conferência da entrega

- [ ] Integrantes e `artifactId` identificados.
- [ ] Todas as respostas preenchidas neste arquivo.
- [X] Código de produção fornecido preservado.
- [X] Código dos testes nas três classes exigidas em `src/test/java`, com cada critério demonstrado isoladamente.
- [ ] Instruções do README conferidas e atualizadas, se necessário.
- [ ] Relatórios e logs da versão final copiados para `evidencias/` e referenciados neste arquivo.
- [ ] Recursos necessários à abertura dos relatórios HTML preservados.
- [ ] Projeto compactado no formato e com o nome exigidos, sem `target`.
