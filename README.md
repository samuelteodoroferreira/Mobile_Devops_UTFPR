# Conversor de Moedas

## Sobre o Projeto
Este é um aplicativo mobile desenvolvido em Kotlin Multiplatform (KMP) que permite a conversão de valores entre Real (BRL) e Dólar (USD) utilizando uma API pública de câmbio.

## Autor

**Samuel Teodoro**
- Estudante de Pós Graduação em Desenvolvimento Mobile - Universidade Tecnologica Federal do Paraná 

## Implementações Realizadas

### Backend e Lógica de Negócio
- Implementação de cache local para taxas de câmbio com duração de 30 minutos
- Sistema de tratamento de erros robusto para falhas de rede com caching
- Validação de entrada de dados para valores monetários
- Implementação de testes unitários abrangentes
- Integração com API de câmbio para obtenção de taxas em tempo real

### Interface do Usuário
- Design moderno utilizando Material Design 3
- Animações suaves para feedback visual
- Campos de entrada com validação em tempo real
- Exibição formatada de valores monetários
- Indicadores de carregamento durante operações
- Mensagens de erro claras e informativas

### Arquitetura e Estrutura
- Implementação do padrão MVVM
- Separação clara de responsabilidades
- Injeção de dependência com Koin
- Gerenciamento de estado com Kotlin Flow
- Cache local para melhor performance
- Testes de interface automatizados

### Testes
- Testes unitários para lógica de negócio
- Testes de interface para componentes UI
- Testes de integração para fluxos completos
- Cobertura de testes para principais funcionalidades
- Testes automatizados para Android e iOS

### CI/CD
- Pipeline de integração contínua com GitHub Actions
- Builds automatizados para Android e iOS
- Execução automática de testes
- Verificação de qualidade de código

## Funcionalidades
- Interface animada com ícones de moeda (Implementado com ajuda do COPILOT)
- Conversão de Real para Dólar
- Conversão de Dólar para Real
- Interface moderna e intuitiva
- Suporte para Android e iOS

## Tecnologias Utilizadas
- Kotlin Multiplatform (KMP)
- Kotlin/Jetpack Compose (Android) - Tive problemas com versoes - Corrigir
- Swift/SwiftUI (iOS) - Com bugs - Corrigir
- GitHub Actions para CI/CD - Tentei utilizar outras ferramentas, mas sem sucesso
- API pública de câmbio

## Arquitetura
- Clean Architecture 
- Testes Unitários
- Testes de Interface
- MVVM Pattern (implementado com ajuda do COPILOT - Ainda estutando sobre)


## Como Executar
1. Clone o repositório
2. Abra o projeto no Android Studio ou Xcode
3. Execute o aplicativo na plataforma desejada

## Testes
- Execute os testes unitários: `./gradlew test`
- Execute os testes de interface: `./gradlew connectedAndroidTest` 

## Features que Implementarei 
- Adicionarei novas moedas, Dólar Canadense, Libras Etc.
- resolverei bugs na versao IoS, pois nao esta dentro do meu conhecimento.


