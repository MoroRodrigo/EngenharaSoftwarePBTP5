# Sistema Integrado de Gestao de Produtos - TP5

## 1. Descricao do Projeto
Este projeto consiste em um sistema CRUD (Create, Read, Update, Delete) completo, desenvolvido como parte do Teste de Performance 5 do curso de Engenharia de Software. A aplicacao evoluiu de um sistema de linha de comando para uma plataforma web integrada com persistencia de dados, validacoes robustas e um pipeline de entrega continua (CI/CD) totalmente automatizado.

O sistema aplica conceitos avancados de Clean Code, Imutabilidade, Polimorfismo e a separacao de responsabilidades atraves do padrao Command Query Separation (CQS).

## 2. Tecnologias Utilizadas
* Linguagem: Java 21
* Framework Base: Spring Boot 3.2.4
* Gerenciador de Dependencias: Maven
* Motor de Template: Thymeleaf (Interface Web)
* Testes Unitarios e Integracao: JUnit 5 e AssertJ
* Testes Baseados em Propriedades: Jqwik
* Testes de Interface (E2E): Selenium WebDriver
* CI/CD: GitHub Actions
* Relatorios de Cobertura: JaCoCo

## 3. Arquitetura e Boas Praticas
O projeto foi refatorado seguindo as diretrizes do TP5 para garantir manutenibilidade e escalabilidade:

* Imutabilidade: Utilizacao de Java Records para as classes de dominio, garantindo que o estado dos objetos nao seja alterado apos a criacao.
* Polimorfismo: Substituicao de estruturas condicionais (if/else) por Enums com metodos abstratos para calculo de descontos por categoria.
* Fail Early: Validacoes de negocio implementadas diretamente no construtor dos Records e Service.
* CQS (Command Query Separation): Divisao clara entre servicos que realizam alteracoes no sistema (Commands) e servicos que apenas consultam dados (Queries).

## 4. Estrutura de Testes
A suite de testes cobre mais de 90% do codigo, dividida em:

* Testes de Dominio: Validacao de regras de negocio e imutabilidade.
* Testes de Propriedade: Uso do Jqwik para testar limites e valores extremos (Fuzz Testing).
* Testes de Interface: Selenium automatizado para validar o fluxo do usuario na interface web.
* Testes Pos-Deploy: Scripts executados automaticamente pelo pipeline apos a publicacao em ambiente de producao para verificar a saude do sistema.

## 5. Pipeline de CI/CD (GitHub Actions)
O fluxo de automacao esta definido no arquivo .github/workflows/ci-cd-pipeline.yml e segue as seguintes etapas:

1. Build e Test: Compilacao do projeto e execucao de todos os testes unitarios e de propriedade.
2. Analise de Seguranca (SAST): Verificacao estatica do codigo em busca de vulnerabilidades.
3. Deploy em Homologacao: Deploy automatico em ambiente de teste para validacao interna.
4. Deploy em Producao: Deploy condicional que exige aprovacao manual de um revisor (Required Reviewers).
5. Health Check: Execucao de testes Selenium contra a URL de producao para confirmar a integridade do deploy.

## 6. Instrucoes de Execucao

### Pre-requisitos
* Java 21 instalado.
* Maven 3.8 ou superior instalado.
* Google Chrome instalado (para execucao local dos testes Selenium).

### Como rodar a aplicacao
1. Clone o repositorio.
2. No terminal, execute: mvn spring-boot:run
3. Acesse no navegador: http://localhost:8080/produtos

### Como rodar os testes
* Para rodar todos os testes (Unitarios + Jqwik): mvn test
* Para gerar o relatorio de cobertura JaCoCo: mvn verify

## 7. Configuracao de Ambientes e Seguranca
Para o funcionamento correto do pipeline no GitHub:
1. Va em Settings > Environments e crie os ambientes: homologacao e producao.
2. No ambiente producao, ative a opcao Required Reviewers.
3. Cadastre as Secrets necessarias (PROD_DB_PASSWORD, etc.) em cada ambiente para garantir a seguranca das credenciais.

## 8. Monitoramento e Logs
O pipeline utiliza Logs Personalizados do GitHub Actions para facilitar a depuracao. Avisos e erros criticos sao destacados na interface do console atraves de annotations (::notice e ::error).
