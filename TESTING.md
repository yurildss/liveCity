# 🧪 LiveCity - Testes Unitários

## Visão Geral

Este documento descreve a suite completa de testes unitários criada para o projeto LiveCity. Os testes cobrem ViewModels, Services, Utilitários e Modelos de dados.

---

## 📋 Estrutura de Testes

### Organização dos Testes

```
app/src/test/java/com/example/livecity/
├── screens/
│   ├── login/
│   │   └── LoginScreenViewModelTest.kt          # Testes de autenticação
│   ├── feed/
│   │   └── MapScreenViewModelTest.kt            # Testes de mapa e clustering
│   └── alert/
│       └── AlertScreenViewModelTest.kt          # Testes de criação de alerts
├── service/
│   └── impl/
│       ├── AccountServiceImplTest.kt            # Testes de autenticação Firebase
│       └── StorageServiceImplTest.kt            # Testes de armazenamento Firestore
├── model/
│   ├── EvaluationModelTest.kt                   # Testes do modelo Evaluation
│   ├── TypeModelTest.kt                         # Testes do modelo Type
│   └── UserModelTest.kt                         # Testes do modelo User
└── util/
    └── IconByNameTest.kt                        # Testes de mapeamento de ícones
```

---

## 🧬 Cobertura de Testes

### 1. **LoginScreenViewModelTest** (10 testes)
- ✅ Estado inicial correto
- ✅ Atualização de email
- ✅ Atualização de senha
- ✅ Limpeza de mensagens
- ✅ Validação de email vazio
- ✅ Validação de senha vazia
- ✅ Login com credenciais válidas
- ✅ Login com credenciais inválidas

**Padrão Testado**: MVVM + StateFlow com validação

---

### 2. **MapScreenViewModelTest** (7 testes)
- ✅ Estado inicial correto
- ✅ Alternância de itens de navegação
- ✅ Alternância do bottom sheet
- ✅ Recuperação de alerts com sucesso
- ✅ Recuperação de lista vazia de alerts
- ✅ Clique em cluster item
- ✅ Implementação correta da interface ClusterItem

**Padrão Testado**: Clustering de marcadores, gerenciamento de estado do mapa

---

### 3. **AlertScreenViewModelTest** (13 testes)
- ✅ Estado inicial correto com tipos de alerts padrão
- ✅ Atualização de título
- ✅ Atualização de descrição
- ✅ Atualização de tipo
- ✅ Atualização de posição (GeoPoint)
- ✅ Alternância de expanded
- ✅ Carregamento do mapa
- ✅ Definição de localização atual
- ✅ Definição de posição do marcador
- ✅ Alternância de useMyLocation
- ✅ Alternância de useSetLocation
- ✅ Validações de salvamento (título, descrição, tipo, localização)

**Padrão Testado**: Validação de formulário, conversão GeoPoint

---

### 4. **AccountServiceImplTest** (6 testes)
- ✅ Obtenção de userId do usuário
- ✅ Retorno vazio de userId quando não há usuário
- ✅ Obtenção de nome de exibição
- ✅ Retorno vazio de nome quando não há displayName
- ✅ Retorno vazio de nome quando não há usuário
- ✅ Autenticação com credenciais válidas
- ✅ Autenticação com credenciais inválidas
- ✅ Logout

**Padrão Testado**: Mocking de Firebase Auth, testes assíncronos

---

### 5. **StorageServiceImplTest** (5 testes)
- ✅ Salvamento de alert (verifica chamada Firestore)
- ✅ Recuperação de lista de alerts
- ✅ Recuperação de lista vazia de alerts
- ✅ Recuperação de alerts por userId
- ✅ Recuperação vazia de alerts de usuário inexistente

**Padrão Testado**: Mocking de Firestore, queries

---

### 6. **IconByNameTest** (7 testes)
- ✅ Mapeamento de "ic_danger"
- ✅ Mapeamento de "ic_building"
- ✅ Mapeamento de "ic_car_crash"
- ✅ Mapeamento de "ic_rain_snow_storm"
- ✅ Lançamento de exceção para ícone inválido
- ✅ Lançamento de exceção para string vazia
- ✅ Lançamento de exceção para ícone desconhecido

**Padrão Testado**: Testes de utilitários com validação de erros

---

### 7. **EvaluationModelTest** (7 testes)
- ✅ Criação com todos os parâmetros
- ✅ Criação com valores padrão
- ✅ Formatação de data válida
- ✅ Formatação de data com timestamp nulo
- ✅ Data class mutável (copy)
- ✅ Equals e hashCode
- ✅ Timestamp em formato dd/MM/yyyy

**Padrão Testado**: Data classes, formatação de datas

---

### 8. **TypeModelTest** (6 testes)
- ✅ Criação com parâmetros
- ✅ Criação com valores padrão
- ✅ Data class mutável (copy)
- ✅ Equals e hashCode
- ✅ toString()
- ✅ Construtor com parâmetros nomeados

**Padrão Testado**: Data classes simples

---

### 9. **UserModelTest** (7 testes)
- ✅ Criação com todos os parâmetros
- ✅ Data class mutável (copy)
- ✅ Equals e hashCode
- ✅ toString()
- ✅ Inequality com emails diferentes
- ✅ Inequality com senhas diferentes

**Padrão Testado**: Data classes, validação de igualdade

---

## 🚀 Como Executar os Testes

### Executar todos os testes unitários
```bash
./gradlew test
```

### Executar testes de um pacote específico
```bash
./gradlew test --tests "com.example.livecity.screens.login.*"
```

### Executar um teste específico
```bash
./gradlew test --tests "com.example.livecity.screens.login.LoginScreenViewModelTest"
```

### Executar testes com relatório detalhado
```bash
./gradlew test --info
```

### Gerar relatório de cobertura de código (com plugin Jacoco)
```bash
./gradlew test jacocoTestReport
```

---

## 📦 Dependências de Teste

As seguintes dependências foram adicionadas ao `build.gradle.kts`:

```kotlin
// Unit Testing
testImplementation("io.mockk:mockk:1.13.7")                              // Mocking library
testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3") // Coroutines testing
testImplementation("app.cash.turbine:turbine:1.0.0")                     // StateFlow testing

// Instrumented Testing (Android Device/Emulator)
androidTestImplementation("com.google.dagger:hilt-android-testing:2.56.2")
kspAndroidTest("com.google.dagger:hilt-android-compiler:2.56.2")
```

---

## 🧪 Padrões de Teste Utilizados

### 1. **StateFlow Testing**
```kotlin
@Test
fun someTest() = runBlocking {
    val state = viewModel.state.first()
    assertEquals(expectedValue, state.value)
}
```

### 2. **Mock com MockK**
```kotlin
val mockService = mockk<SomeService>()
coEvery { mockService.someFunction() } returns expectedResult
```

### 3. **Teste de Async/Coroutines**
```kotlin
@OptIn(ExperimentalCoroutinesApi::class)
class MyTest {
    private val testDispatcher = UnconfinedTestDispatcher()
    
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }
}
```

### 4. **Teste de Validação**
```kotlin
@Test
fun validationTest() {
    // Setup
    // Act
    // Assert
}
```

---

## ✅ Checklist de Qualidade

| Componente | Testes | Status |
|-----------|--------|--------|
| LoginScreenViewModel | 10 | ✅ |
| MapScreenViewModel | 7 | ✅ |
| AlertScreenViewModel | 13 | ✅ |
| AccountServiceImpl | 8 | ✅ |
| StorageServiceImpl | 5 | ✅ |
| IconByName | 7 | ✅ |
| EvaluationModel | 7 | ✅ |
| TypeModel | 6 | ✅ |
| UserModel | 7 | ✅ |
| **TOTAL** | **70** | ✅ |

---

## 🔍 Melhores Práticas Seguidas

1. ✅ **Nomeação Clara**: Testes descrevem o que está sendo testado
2. ✅ **Padrão AAA**: Arrange, Act, Assert
3. ✅ **Isolamento**: Cada teste é independente
4. ✅ **Mocking**: Dependências externas são mockadas
5. ✅ **Dados Reais**: Testes usam dados representativos
6. ✅ **Cobertura**: Casos de sucesso, erro e edge cases
7. ✅ **Coroutines Seguras**: Uso de TestDispatcher
8. ✅ **Sem Side Effects**: Testes não afetam uns aos outros

---

## 📝 Próximos Passos Sugeridos

### Testes Adicionais Recomendados

1. **Testes de Integração**
   - Testes de navegação entre telas
   - Testes E2E com Firebase emulator
   
2. **Testes de UI**
   - Testes com Jetpack Compose
   - Snapshot testing para composables

3. **Testes de Performance**
   - Benchmarking de queries Firestore
   - Testes de memória

4. **Testes de Segurança**
   - Validação de token Firebase
   - Testes de permissões de Android

---

## 🐛 Troubleshooting

### Erro: "UnConfinedTestDispatcher not found"
**Solução**: Ensure `kotlinx-coroutines-test` está adicionado ao `build.gradle.kts`

### Erro: "MockK cannot mock final class"
**Solução**: Use classes abertas ou interfaces para mockar

### Erro: "FirebaseAuth initialization failed"
**Solução**: É esperado em testes unitários; use mocks em vez de instâncias reais

---

## 📚 Referências

- [Kotlin Testing Best Practices](https://kotlinlang.org/docs/reference/testing.html)
- [MockK Documentation](https://mockk.io/)
- [Android Testing Guide](https://developer.android.com/training/testing)
- [Jetpack Compose Testing](https://developer.android.com/jetpack/compose/testing)

---

**Criado em**: 2026-06-30
**Versão**: 1.0

