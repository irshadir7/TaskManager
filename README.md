# TaskManager

A modern task management application built with Jetpack Compose, Room, and Kotlin, using modern Android development practices.

---

## ✨ Features

- Create, update, and delete tasks with intuitive UI
- Categorize tasks with custom categories
- Set task priorities (Low, Medium, High)
- Schedule tasks with due dates using a calendar interface
- Mark tasks as complete with swipe gestures
- Reorder tasks with drag and drop functionality
- Filter and sort tasks by various criteria
- Clean and responsive Material 3 UI
- Optimized performance for large task lists (100+ tasks)
- Smooth animations and transitions

---

## 🛠 Tech Stack

- **Language:** Kotlin
- **Minimum SDK:** 29 (Android 10)
- **Target SDK:** 35 (Android 15)
- **UI Framework:** Jetpack Compose 1.7.x with Material 3
- **Navigation:** Jetpack Compose Navigation 2.8.x
- **Database:** Room 2.6.x
- **Architecture:** Clean Architecture principles
- **Dependency Injection:** Hilt
- **Asynchronous Programming:** Kotlin Coroutines & Flow
- **State Management:** ViewModel with LiveData and State
- **Data Persistence:** Room Database & DataStore Preferences
- **UI Components:** Custom Compose widgets and animations

---

## ⚙️ Setup Instructions

### Prerequisites

- Android Studio Arctic Fox (2020.3.1) or newer
- JDK 17 or higher
- Android SDK 35
- Gradle 8.0+

---

## 🎨 UI/UX Design

The UI follows Material 3 design principles with:

- **Bottom Navigation**: Intuitive navigation between main sections
- **FAB (Floating Action Button)**: Quick task creation
- **Swipe Actions**: Efficient task completion and deletion
- **Drag-and-Drop**: Intuitive task reordering
- **Dynamic Theming**: Material 3 color system for a cohesive look

---

## 🚀 Performance Optimizations

To ensure smooth performance even with large task lists:

- **LazyColumn with Key Derivation**: Efficient list rendering
- **Optimized Recomposition**: Using `remember`, `derivedStateOf`, and `key`
- **Shimmer Loading Effect**: Visual feedback during loading
- **Background Processing**: Heavy tasks moved to background threads
- **State Hoisting**: Reduces unnecessary recompositions
- **Rememberized Callbacks**: Avoids performance issues
- **Custom SwipeToDismiss**: Faster and more efficient

---

## 👈 Swipe-to-Dismiss Implementation

- **Bi-directional swiping**:
    - Left for deletion
    - Right for completion
- **Visual feedback**: Icon and color changes
- **Haptic feedback**: Vibration on action complete
- **Undo functionality**: Snackbar with Undo option
- **Smooth animation**: For all swipe states

---

## 🟰 Drag-and-Drop Reordering

- **Long-press activation**: Starts reordering
- **Visual feedback**: Elevation and scaling
- **Haptic feedback**: On pick-up and drop
- **Persistent ordering**: Saved automatically to database

---

## 📚 Library Choices

- **Hilt**: Clean DI setup
- **Room**: Robust local DB with coroutine support
- **Jetpack Compose**: Modern, declarative UI toolkit
- **Coroutines & Flow**: For async/reactive operations
- **Compose Navigation**: Type-safe, argument-passing
- **DataStore**: Flow-based preferences replacement
- **Feather Icons**: Aesthetic icon set
- **ComposeReorderable**: Smooth drag-and-drop
- **Calendar Compose**: Flexible calendar picker
- **Toasty**: Stylish toast messages

---

## 🔮 Future Improvements

- Cloud sync with Firebase or custom backend
- Dark/light theme toggle with dynamic color
- Home screen widget
- Task reminders & push notifications
- Shareable tasks
- Stats & productivity tracking
- Recurring task patterns
- Import/export support  
