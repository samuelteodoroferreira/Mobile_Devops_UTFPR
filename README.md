# Conversor de Moedas - Projeto DevOps

## Sobre o Projeto
App mobile em Kotlin Multiplatform pra converter Real pra Dólar (e vice-versa). Fiz isso como projeto final da pós em Mobile, tentando aplicar umas práticas DevOps que aprendi durante o curso.

## Autor
**Samuel Teodoro**
- Pós Graduação em Desenvolvimento Mobile - UTFPR

## O que já tá funcionando

### App em si
- Conversão BRL ↔ USD usando API pública
- Interface bem bonita com Material Design 3
- Cache local das taxas (30 min pra não ficar chamando API toda hora)
- Tratamento de erro quando a internet falha
- Validação dos campos de entrada
- Animações suaves pra dar feedback visual

### Arquitetura
- MVVM com Koin pra injeção de dependência
- Kotlin Flow pra gerenciar estado
- Testes unitários cobrindo a lógica principal
- Testes de UI pra garantir que a interface funciona
- Separação clara entre camadas (repository, viewmodel, ui)

### DevOps (parcialmente implementado)
- CI/CD básico com GitHub Actions
- Build automatizado pra Android e iOS
- Testes rodando automaticamente

## O que ainda falta fazer

### Infraestrutura
- [ ] Docker pra containerizar tudo
- [ ] Redis pra cache distribuído
- [ ] Prometheus + Grafana pra monitoramento
- [ ] Scripts de automação

### Qualidade e Segurança
- [ ] Análise estática com Detekt
- [ ] Verificação de vulnerabilidades OWASP
- [ ] Cobertura de testes mais alta
- [ ] Code review automatizado

### Deploy
- [ ] Pipeline completo de CD
- [ ] Deploy automático nas lojas
- [ ] Rollback automático em caso de problema

## Como rodar

### Setup básico
```bash
git clone <repo>
./gradlew build
./gradlew test
```

### Pra desenvolvimento
```bash
./gradlew assembleDebug    # Build APK
./gradlew clean           # Limpar build
```

## Tecnologias que usei
- **Frontend**: Kotlin Multiplatform + Compose (Android) + SwiftUI (iOS)
- **Backend**: API pública de câmbio
- **Arquitetura**: MVVM + Clean Architecture
- **DevOps**: GitHub Actions (por enquanto)

## Próximos passos
1. Implementar Docker e containerização
2. Adicionar monitoramento com Prometheus/Grafana
3. Configurar cache distribuído com Redis
4. Implementar análise de segurança
5. Melhorar a cobertura de testes

## Bugs conhecidos
- iOS ainda tem uns problemas de layout (preciso dar uma olhada)
- Às vezes a API demora pra responder (por isso implementei cache)

## Observações
Esse projeto foi feito pra aprender DevOps na prática. Ainda tem bastante coisa pra implementar, mas o app em si já tá funcionando bem. O objetivo é ter um pipeline completo de CI/CD com monitoramento, segurança e automação.

Se alguém quiser contribuir ou dar sugestões, é só falar! 😊

---
*Projeto em desenvolvimento - Pós Graduação Mobile UTFPR*