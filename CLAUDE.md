# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

All commands use Gradle wrapper from the project root:

```bash
# Build debug APK
./gradlew assembleDebug

# Run unit tests
./gradlew test

# Run a single unit test class
./gradlew test --tests "com.example.sneakershop.ExampleUnitTest"

# Run instrumented (Android) tests (requires connected device/emulator)
./gradlew connectedAndroidTest

# Lint check
./gradlew lint

# Clean build
./gradlew clean assembleDebug
```

On Windows use `gradlew.bat` instead of `./gradlew`.

## Architecture Overview

This is an Android app using **Jetpack Compose** with a simple layered architecture:

### Navigation
All navigation is defined in `MainActivity.kt`. The app starts with a `SplashScreen` that navigates to `home`. The `NavHost` manages these routes: `splash`, `home`, `details/{id}`, `cart`, `checkout`, `wishlist`, `profile`. The bottom navigation bar is shown only on `home`, `wishlist`, `cart`, and `profile` routes.

### State Management
- **`HomeViewModel`** (`viewmodel/`) — the only ViewModel. Holds `HomeUiState` (sneaker list, selected category, search query) as a `StateFlow`. Category filtering uses `sneaker.name.startsWith(category)`, so category names in `SneakerData` must match the prefix exactly.
- **`CartManager`** and **`WishlistManager`** (`model/`) — in-memory singleton objects using Compose `mutableStateListOf` / `mutableListOf`. These are not persisted across app restarts.

### Data Layer
- **`Sneaker`** — Room `@Entity` used both as the database model and as the UI data model throughout the app.
- **`SneakerDatabase`** / **`SneakerDao`** (`data/`) — Room database used **only for favorites/wishlist persistence**. The DAO's `getFavorite()` returns all rows; `isFavorite(id)` checks existence. Accessed via `SneakerApp.database` (lazy singleton in the `Application` class).
- **`SneakerData`** (`model/`) — hardcoded static list of 4 sneakers (Nike, Adidas, Puma). This is the source of truth for the product catalog; `DetailsScreen` looks up sneakers from this list by `id`.

### Key Inconsistency to Be Aware Of
`WishlistManager` (in-memory) and `SneakerDatabase`/`SneakerDao` (Room) both exist for favorites/wishlist but are not synchronized. The Room DB is wired up but the UI screens (`WishlistScreen`, `DetailsScreen`) use `WishlistManager` directly.

## Стандарты кода

- Язык комментариев: русский
- Не использовать LiveData — только StateFlow
- Не писать бизнес-логику в Composable
- Не использовать `!!` — только безопасные операторы (`?.`, `?:`, `let`)

## Известные проблемы (Tech Debt)

- `WishlistManager` (in-memory) и Room DB не синхронизированы — приоритет для рефакторинга
- `SneakerData` — хардкод 4 товаров, нужна загрузка из сети

## Планируемые улучшения

- Добавить Hilt для DI
- Добавить сетевой слой (Retrofit)
- Персистентность `CartManager` через Room

## Tech Stack

- Kotlin, minSdk 26, compileSdk 36
- Jetpack Compose + Material3
- Navigation Compose 2.8.0
- Room 2.6.1 with KSP
- Coil 2.5.0 for image loading
- ViewModel + StateFlow (no Hilt/DI — ViewModels created via `viewModel()`)
