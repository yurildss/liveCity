# AGENTS.md - LiveCity Codebase Guide

## Project Overview
**LiveCity** is an Android civic engagement app (Kotlin + Jetpack Compose) that lets users report real-time city incidents (crashes, tree falls, infrastructure issues) on an interactive Google Map.

**Tech Stack**: Jetpack Compose, Material 3, Firebase (Auth + Firestore), Google Maps Compose, Hilt DI, Retrofit, Kotlin Coroutines

---

## Architecture & Layering

### MVVM + Clean Architecture Pattern
- **UI Layer** (`screens/`): Composable functions + ViewModels
- **Service Layer** (`service/`): Interfaces defining business logic contracts
- **Implementation Layer** (`service/impl/`): Firebase-backed implementations
- **DI Layer** (`service/module/`): Hilt provider modules for singletons
- **Model Layer** (`model/`): Data classes with Firebase serialization

### Data Flow Example
```kotlin
// ScreenUI (composable) → ViewModel (MutableStateFlow) → Service
//   ↓
// AccountService (interface) → AccountServiceImpl (Firebase) ↓ Firestore
```

---

## Critical Development Patterns

### 1. ViewModel + StateFlow State Management
Every screen has a dedicated ViewModel with immutable UI state:

```kotlin
@HiltViewModel
class MapScreenViewModel @Inject constructor(
    private val storageService: StorageService
) : ViewModel() {
    private val _uiState = MutableStateFlow(MapScreenUIState())
    val uiState = _uiState.asStateFlow()
    
    fun onNavItemClicked(navItem: NavItem) {
        _uiState.value = _uiState.value.copy(selectedNavItem = navItem)  // Immutability via copy()
    }
}
```

**Pattern**: Always use `copy()` to mutate state, expose private mutable flows as public immutable StateFlow.

### 2. Service Interfaces → Implementation Binding
Services defined abstractly; Hilt binds implementations:

`service/AccountService.kt` (interface):
```kotlin
interface AccountService {
    val currentUserId: String
    suspend fun authenticate(email: String, password: String): String
}
```

`service/module/ServiceModule.kt` (Hilt binding):
```kotlin
@Module @InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
}
```

**Why**: Decouples UI from Firebase complexity; enables testing without Firebase.

### 3. Compose State Collection in UI
```kotlin
@Composable
fun LoginScreen(viewModel: LoginScreenViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    // state updates trigger recomposition
}
```

**Key**: Always use `collectAsStateWithLifecycle()` (not `collect()`) for lifecycle safety.

### 4. Cluster Items for Map Rendering
Custom implementation of Google Maps `ClusterItem`:

```kotlin
data class MyClusterItem(private val position: LatLng) : ClusterItem {
    override fun getPosition(): LatLng = position
    override fun getTitle(): String = title
    override fun getSnippet(): String = snippet
    override fun getZIndex(): Float = 0F
}
```

Used with `ClusterManager` for intelligent marker grouping. See `MapScreenViewModel.toClusterItem()`.

---

## Build System & Dependencies

### Gradle Structure
- **Version Catalog**: `gradle/libs.versions.toml` (centralized version management)
- **Multi-module**: `settings.gradle.kts` includes `:app` only (single-module project)
- **Build KTS**: Modern Gradle with type-safe plugin management

### Key Plugins
- `com.android.application` (AGP 8.9.2)
- `kotlin-compose` (Kotlin 2.2.0) - Compose compiler plugin
- `com.google.gms.google-services` (Firebase integration)
- `com.google.devtools.ksp` (Annotation processing for Hilt)
- `com.google.dagger.hilt.android` (Dependency injection)
- `com.google.android.libraries.mapsplatform.secrets-gradle-plugin` (API key management)

### Build Commands (Android Studio/Gradle wrapper)
```bash
# Build debug APK
./gradlew assembleDebug

# Run tests
./gradlew test

# Check for lint errors
./gradlew lint

# Sync dependencies (if build.gradle changed)
./gradlew syncDependencies
```

---

## Firebase Setup & Configuration

### Required Setup
1. **`app/google-services.json`**: Downloaded from Firebase Console (not committed)
2. **Google Maps API Key**: 
   - Stored in `secrets.properties` (not committed)
   - Accessed via `secrets-gradle-plugin`
   - Injected into `AndroidManifest.xml`: `${GOOGLE_MAPS_API_KEY}`

### Firebase Services
- **Authentication**: `FirebaseAuth.getInstance()` (provided by Hilt)
- **Firestore**: `FirebaseFirestore.getInstance()` (provided by Hilt)
- **Data Model**: `Evaluation` with `@DocumentId` annotation

---

## Project Structure & Key Files

```
app/src/main/java/com/example/livecity/
├── LiveCityApplication.kt           # Hilt @HiltAndroidApp entry
├── MainActivity.kt                   # Single Activity (Compose-only)
├── screens/                          # UI + ViewModels
│   ├── LiveCityApp.kt               # NavHost root
│   ├── navigation/Destination.kt    # Enum routing
│   ├── login/LoginScreenViewModel.kt
│   ├── feed/MapScreenViewModel.kt   # Main map clustering
│   └── alert/AlertScreenViewModel.kt
├── service/                          # Business logic interfaces
│   ├── AccountService.kt
│   ├── module/
│   │   ├── ServiceModule.kt         # Hilt provider bindings
│   │   ├── FirebaseModule.kt        # Firebase singleton providers
│   │   └── StorageService.kt
│   └── impl/                         # Firebase implementations
├── model/                            # Data classes
│   ├── Evaluation.kt                # Main alert model, GeoPoint
│   ├── Type.kt                      # Alert category
│   └── User.kt
├── network/                          # Retrofit + API
│   ├── GeocodingService.kt          # Google Geocoding API calls
│   └── APIKeyInterceptor.kt
└── util/
    └── IconByName.kt               # Resource mapping by alert type
```

---

## Important Patterns & Conventions

### 1. Immutable Data Classes
All model classes use `data class` with `val` properties. Firestore serialization via `@DocumentId`:

```kotlin
data class Evaluation(
    @DocumentId val id: String = "",
    val position: GeoPoint? = null,  // Firestore GeoPoint
    val date: com.google.firebase.Timestamp? = null
)
```

### 2. Error Handling in Services
Services return `String` from auth methods (not exceptions):
```kotlin
val result = accountService.authenticate(email, password)
if (result != "Success") {
    // Handle error message
}
```

### 3. Screen Navigation
- Define routes in `screens/navigation/Destination.kt` (enum)
- Pass callbacks from parent to handle navigation (not ViewModels doing navigation)
- Example: `onSuccessfulLogin: () -> Unit` passed to `LoginScreen`

### 4. Permissions Handling
Uses `accompanist-permissions` library:
```kotlin
@OptIn(ExperimentalPermissionsApi::class)
val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
```

See `MapScreen.kt` for location permission patterns.

---

## Common Workflows

### Adding a New Screen
1. Create `screens/myFeature/MyFeatureScreen.kt` (Composable)
2. Create `screens/myFeature/MyFeatureViewModel.kt` (@HiltViewModel)
3. Add to `Destination.kt` enum
4. Add composable route in `LiveCityApp.kt` NavHost
5. Inject services if needed (auto-resolved by Hilt constructor injection)

### Adding a Firebase Collection Query
1. Define interface method in `service/module/StorageService.kt`
2. Implement in `service/impl/StorageServiceImpl.kt` using `FirebaseFirestore`
3. Inject `StorageService` into ViewModel
4. Call from viewModelScope with `launch { }`

### Adding a New Alert Type
1. Add icon asset to `res/drawable-nodpi/`
2. Map in `util/IconByName.kt` getIconResByName()
3. Add entry to `Type.kt` enum
4. Update Firestore Evaluation documents with new type

---

## Testing Setup

### Instrumented Tests (`androidTest/`)
- Location: `com/example/livecity/ExampleInstrumentedTest.kt`
- Uses AndroidJUnitRunner
- Run via: `./gradlew connectedAndroidTest`

### Unit Tests (`test/`)
- Location: `com/example/livecity/ExampleUnitTest.kt`
- Run via: `./gradlew test`

**Note**: Firebase mocking/stubbing typically needed for service tests.

---

## Critical Dependencies & Versions

| Library | Version | Purpose |
|---------|---------|---------|
| Kotlin | 2.2.0 | Language |
| Compose BOM | 2024.09.00 | UI framework |
| Firebase BOM | 34.1.0 | Auth + Firestore |
| Hilt | 2.56.2 | DI framework |
| Google Maps Compose | 6.7.1 | Map rendering |
| Retrofit | 2.9.0 | HTTP client |
| Accompanist Permissions | 0.36.0 | Permission handling |

---

## Performance & Optimization Tips

1. **Map Clustering**: Use `ClusterManager` for 100+ markers (already implemented)
2. **Firebase**: Use clustering queries (add to `StorageService`)
3. **Recomposition**: Avoid passing entire objects to composables; decompose state
4. **Coroutines**: Always use `viewModelScope` (cancelled with ViewModel)

---

## Common Gotchas

1. **StateFlow collection**: Must use `collectAsStateWithLifecycle()` in Compose, not `collect()`
2. **Hilt ViewModels**: Use `hiltViewModel()` from `androidx.hilt:hilt-navigation-compose`
3. **Location permissions**: Requires `ACCESS_FINE_LOCATION` + runtime request on Android 6+
4. **Firebase Auth**: `currentUser` can be null after logout; always check
5. **Secrets**: Never commit `secrets.properties` or `google-services.json`

---

## Useful References

- **Compose State Management**: See `MapScreenViewModel` (MutableStateFlow → asStateFlow pattern)
- **Service Layer Example**: `AccountServiceImpl` (Firebase Auth integration)
- **Map Integration**: `MapScreen.kt` + `MapScreenViewModel` (clustering, markers)
- **Navigation Flow**: `LiveCityApp.kt` (NavHost with destination enum)
- **Theme**: `ui/theme` (Material 3 theming setup)


