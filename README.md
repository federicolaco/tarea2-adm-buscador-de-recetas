# Buscador de Recetas — Tarea 2 ADM

**Escenario #3** · API [TheMealDB](https://www.themealdb.com/api.php)  
**Stack:** Kotlin, Jetpack Compose, Material 3, Retrofit, Room

## Integrantes

- Maria Nazarena Valiero
- Federico Laco
- Simón Corvo

## Funcionalidades

- Buscar recetas por nombre
- Filtrar por ingrediente y categoría
- Ver detalle (ingredientes, instrucciones, video)
- Favoritos locales (Room)
- Receta aleatoria
- Compartir receta y abrir video (Intents)

## Arquitectura

```
UI (Compose) → ViewModels → RecipeRepository → MealApiService (Retrofit)
                                              → FavoriteDao (Room)
```

| Componente Android | Implementación                     |
| ------------------ | ---------------------------------- |
| Activity           | `MainActivity`                     |
| Service            | `RecipeCacheService`               |
| BroadcastReceiver  | `ConnectivityReceiver`             |
| ContentProvider    | `FavoriteRecipesProvider`          |
| Intents            | `IntentUtils` (compartir, YouTube) |

## Navegación (5 rutas)

| Ruta                                 | Parámetros                 |
| ------------------------------------ | -------------------------- |
| `home`                               | —                          |
| `results/{filterType}/{filterValue}` | NAME, CATEGORY, INGREDIENT |
| `detail/{mealId}`                    | id de receta               |
| `favorites`                          | —                          |
| `categories`                         | —                          |

## Compilar y ejecutar

Requisitos: JDK 17, Android SDK 35, Android Studio reciente.

```bash
gradlew.bat assembleDebug
gradlew.bat installDebug
```

APK: `app/build/outputs/apk/debug/app-debug.apk`

## Tests

```bash
gradlew.bat testDebugUnitTest
gradlew.bat connectedDebugAndroidTest
```

## Fastlane

```bash
cd fastlane
bundle exec fastlane beta
```

Lanes: `build`, `test`, `beta`.

## Seguridad y calidad

```bash
gradlew.bat :app:detekt
```

Informe en `docs/security/detekt-report.html`. Guía: [docs/security/README.md](docs/security/README.md).

## Entregables del curso

| Entregable                       | Ubicación            |
| -------------------------------- | -------------------- |
| Código fuente                    | `app/`               |
| Mockups Light/Dark               | `docs/mockups/`      |
| Informe SAST (Detekt)            | `docs/security/`     |
| PPT (≤ 5 slides) y video 30–60 s | `docs/presentacion/` |
| Este README                      | raíz del repo        |

## Manual de usuario

1. En **Inicio**, escribí un nombre y tocá **Buscar**.
2. Filtrá por ingrediente o usá chips de categoría.
3. **Sorpresa** muestra una receta aleatoria.
4. En **Detalle**: favorito, **Compartir** o **Video**.
5. Ícono de corazón → **Favoritos**.

## API

Base URL: `https://www.themealdb.com/api/json/v1/1/`
