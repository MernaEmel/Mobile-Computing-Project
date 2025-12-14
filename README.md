
# ☕ BrewBuddy

**BrewBuddy** is a modern Android coffee ordering application built with Kotlin, featuring a clean architecture and intuitive user interface for coffee enthusiasts.

## 📱 Features

### Core Features
- **Coffee Menu**: Browse hot and cold coffee varieties with detailed descriptions
- **Best Sellers**: Discover weekly best-selling coffees
- **Recommendations**: Get personalized weekly coffee recommendations
- **Favorites**: Save and manage your favorite coffee drinks
- **Order History**: Track recent and past orders
- **User Profile**: Personalized experience with user name management

### Navigation
- **Home**: Weekly best sellers and recommendations
- **Drink Menu**: Browse all available coffee options
- **Favorites**: Your saved coffee preferences
- **Orders**: Order history and tracking

## 🏗️ Architecture

BrewBuddy follows **Clean Architecture** principles with clear separation of concerns:

```
app/
├── data/              # Data layer
│   ├── local/         # Room database & preferences
│   ├── remote/        # API services & DTOs
│   └── repository/    # Repository implementations
├── domain/            # Business logic layer
│   ├── model/         # Domain models
│   ├── repository/    # Repository interfaces
│   └── usecase/       # Use cases
└── presentation/      # UI layer
    ├── screens/       # Fragments
    ├── viewmodel/     # ViewModels
    └── components/    # UI components
```

## 🛠️ Tech Stack

### Core Technologies
- **Language**: Kotlin
- **UI**: Android Views with ViewBinding & DataBinding
- **Architecture**: MVVM + Clean Architecture
- **Dependency Injection**: Hilt

### Libraries & Frameworks
- **Database**: Room with KTX extensions
- **Networking**: Retrofit + OkHttp + Gson
- **Async**: Kotlin Coroutines
- **Navigation**: Navigation Component with Safe Args
- **Image Loading**: Glide & Coil
- **Data Storage**: DataStore Preferences

### Development Tools
- **Build System**: Gradle with Kotlin DSL
- **Code Generation**: KSP (Kotlin Symbol Processing)




## 🚀 Getting Started

### Prerequisites
- Android Studio (latest stable version)
- JDK 11 or higher
- Android SDK with API level 36

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/BrewBuddy.git
   cd BrewBuddy
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory and select it

3. **Sync dependencies**
   ```bash
   ./gradlew build
   ```

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click the "Run" button in Android Studio

## 🗄️ Database Schema

The app uses Room database with the following main entities:

- **CoffeeEntity**: Stores coffee information (id, title, description, price, category)
- **FavEntity**: Manages user favorites
- **OrderHistory**: Tracks user order history

## 🌐 API Integration

BrewBuddy integrates with a coffee API to fetch:
- Hot coffee varieties
- Cold coffee varieties
- Coffee details and pricing

Fallback mechanism provides sample data when API is unavailable.

## 📁 Project Structure

```
com.example.brewbuddy/
├── data/
│   ├── local/database/     # Room database setup
│   ├── remote/api/         # API services
│   └── repository/impl/    # Repository implementations
├── di/                     # Dependency injection modules
├── domain/
│   ├── model/             # Domain models
│   ├── repository/        # Repository interfaces
│   └── usecase/           # Business logic use cases
└── presentation/
    ├── screens/           # UI fragments
    ├── viewmodel/         # ViewModels
    └── components/        # Reusable UI components
```

## 🎨 UI/UX Features

- **Material Design**: Modern UI following Material Design guidelines
- **Custom Fonts**: Raleway and Inter font families
- **Gradient Backgrounds**: Beautiful coffee-themed gradients
- **Responsive Design**: Optimized for various screen sizes
- **Loading States**: Smooth loading indicators and progress bars

## 🧪 Testing

This project includes **unit tests** and uses **Mockito** to mock dependencies.
- **Unit Tests** are written using JUnit to verify the core UseCases work correctly.
- **Mockito** is used to mock repository dependencies, isolating business logic from database or network layers.

Run tests using:

```bash
# Unit tests
./gradlew test

# Instrumented tests
./gradlew connectedAndroidTest
```

## 📝 Code Style

The project follows:
- Kotlin coding conventions
- Clean Architecture principles
- MVVM pattern
- Repository pattern

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Contact

Project Link: [https://github.com/yourusername/BrewBuddy](https://github.com/yourusername/BrewBuddy)

---

**Enjoy your coffee journey with BrewBuddy! ☕**
=======
# Mobile-Computing-Project
>>>>>>> af046afd20a2efad14744d42bcf544685e34e611
