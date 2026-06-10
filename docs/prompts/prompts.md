# Prompts usados con Cursor

Registro de prompts que fuimos usando en Cursor (Agent mode) durante el desarrollo de la app. Los revisamos, compilamos y probamos en emulador después de cada cambio importante.

---

## Arranque del proyecto

> **Prompt 1**  
> Estoy en la materia Desarrollo de Aplicaciones Móviles. Necesito armar la Tarea 2 con Kotlin y Jetpack Compose. Elegimos el escenario 3 (Buscador de Recetas con TheMealDB). ¿Podés crear la estructura base del proyecto Android con Gradle, Material 3 y packages organizados por capas?

> **Prompt 2**  
> Revisá la consigna de la tarea y decime qué componentes Android son obligatorios además de la Activity. Queremos cumplir Activity, Service, BroadcastReceiver, ContentProvider e Intents en esta app.

---

## API y capa de datos

> **Prompt 3**  
> Integrá TheMealDB con Retrofit. Necesito endpoints para buscar por nombre, filtrar por categoría e ingrediente, lookup por id, random y listado de categorías. Usá DTOs separados del dominio, un `RecipeRepository` que devuelva `Result`, y Coil para las imágenes.

> **Prompt 4**  
> Mirá `MealApiModels.kt` y agregá el mapeo a modelos de dominio (`MealSummary`, `MealDetail`). Que los ingredientes del detalle salgan de strIngredient1..20 como hace la API.

---

## UI y navegación

> **Prompt 5**  
> Armá la navegación con Navigation Compose: home, results/{filterType}/{filterValue}, detail/{mealId}, favorites y categories. Que los parámetros de búsqueda se pasen por la ruta y se decodifiquen bien si tienen espacios o acentos.

> **Prompt 6**  
> Diseñá `HomeScreen` en Material 3: campo de búsqueda, chips de categorías populares, filtro por ingrediente y botón "Sorpresa" para receta aleatoria. Tema claro/oscuro siguiendo el sistema.

> **Prompt 7**  
> En `DetailScreen` mostrá imagen, ingredientes, instrucciones y botones para favorito, compartir y abrir el video de YouTube con Intent. Reutilizá componentes en `RecipeComponents.kt` si conviene.

> **Prompt 8**  
> La pantalla de resultados y la de categorías tienen que mostrar loading, error y lista vacía con mensajes distintos. Usá el patrón `UiState` que ya tenemos en el proyecto.

---

## Persistencia y componentes Android

> **Prompt 9**  
> Implementá favoritos con Room: entidad, DAO y observe con Flow. Que el repositorio exponga toggle y observeFavorites para la pantalla de favoritos.

> **Prompt 10**  
> Necesito un `ContentProvider` para los favoritos, un `RecipeCacheService` foreground que precargue categorías al iniciar, y un `ConnectivityReceiver` para cambios de red. Registralos en el Manifest y agregá `IntentUtils` para compartir recetas y abrir YouTube.

---

## Tests y calidad

> **Prompt 11**  
> Agregá tests unitarios con JUnit para `RecipeRepository` usando MockWebServer, y un test de Espresso/Compose para HomeScreen. Que corran con `gradlew testDebugUnitTest`.

> **Prompt 12**  
> Configurá Detekt en el módulo app con reporte HTML en `docs/security/`. También armá Fastlane con lanes build, test y beta para generar el APK debug.

---

## Iteraciones típicas

> **Prompt 13**  
> `@RecipeNavGraph.kt` al volver de favoritos no refresca el corazón en detalle. ¿Cómo sincronizo el estado del favorito entre pantallas?

> **Prompt 14**  
> `@DetailViewModel.kt` tira error cuando el mealId viene mal encodeado en la ruta. Arreglalo sin romper la navegación desde categorías.

---

## Notas del equipo

- Usamos **Cursor Agent** con el repo abierto para que tenga contexto de `@archivos`.
- Después de cada prompt grande, compilamos con `gradlew assembleDebug` y probamos en emulador.
- Los prompts de "arreglalo" fueron los que más repetimos; el diseño visual de las pantallas lo fuimos puliendo nosotros a ojo.
