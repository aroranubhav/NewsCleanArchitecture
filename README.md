# 📰 NewsCleanArchitecture

A sample Android app demonstrating **Clean Architecture**, **MVVM**, **Hilt Dependency Injection**,
**Coroutines/Flow**, and **Room Database**.  
The project showcases how to structure a modern Android app with a **single source of truth (Room
DB)** — network data is never shown directly to the UI.

---

## ✨ Features

- Fetches latest news articles from a remote API using **Retrofit**
- Persists data in **Room Database** (source of truth)
- UI renders articles **only from the database**, ensuring consistency
- Offline support: previously cached news available without internet
- **WorkManager** integration for periodic background syncing
- **Coroutines + Flow** for async, reactive data streams
- Modularized into clear **Domain, Data, and UI layers**
- **Hilt** for dependency injection
- **Repository pattern** abstracts data sources

---

## 🏛️ Architecture Flow

The app strictly follows the **Single Source of Truth** principle:

1. **Network Layer (Retrofit)**
    - Makes API requests for news
    - Maps DTOs to entities

2. **Database Layer (Room)**
    - Stores news entities
    - Provides reactive flows to the Repository

3. **Repository**
    - Fetches from **Room** for UI consumption
    - On refresh/sync: calls **Retrofit**, saves to DB, then updates UI automatically

4. **UI Layer (ViewModel + RecyclerView)**
    - Collects data **only from DB (Flow)**
    - Never directly depends on Retrofit
    - Renders consistent state even if offline

5. **WorkManager**
    - Periodically syncs news in the background
    - Ensures the DB is always kept fresh

---

## 📂 Project Structure

com.maxi.newscleanarchitecture
│
├── common/ # Common utilities, helpers, Resource wrapper
│
├── data/ # Data layer
│ ├── local/ # Room database, entities, DAO, utilities
│ ├── remote/ # Retrofit API, DTOs, Interceptors, utilities
│ └── repository/ # DefaultNewsRepository implementation
│
├── domain/ # Domain layer
│ ├── model/ # Business/domain models
│ ├── repository/ # Repository interface (abstraction)
│ └── usecase/ # Use cases (business logic)
│
├── ui/ # Presentation layer
│ └── news/ # News screen, ViewModel, Adapter
│
├── di/ # Hilt modules
└── worker/ # NewsSyncWorker for WorkManager


---

## 🚀 Tech Stack

- **Kotlin**
- **Coroutines & Flow** — asynchronous programming
- **Hilt (Dagger)** — dependency injection
- **Retrofit** — network client
- **Room** — local database
- **WorkManager** — background work scheduling
- **ViewModel + Flow** — lifecycle-aware state management

---

## 🛠️ Setup & Run

1. Clone the repo:
   ```bash
   git clone https://github.com/aroranubhav/NewsCleanArchitecture.git

2. Open in Android Studio (Arctic Fox or newer)
3. Sync Gradle & run the app

📄 License

This project is licensed under the MIT License.