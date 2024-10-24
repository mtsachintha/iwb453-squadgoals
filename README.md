# **Memento: A Collectors' Marketplace**

## Project Overview
Memento is an Android application designed to connect collectors of rare and vintage items, such as action figures, cards, stamps, and coins. The app allows users to list, buy, and sell collectible items in a trusted marketplace environment. Built with modern Android development tools, Memento is crafted to provide an intuitive user experience for collectors to find and trade valuable items.

## Features
- User registration and login
- Browse and search for collectible items
- List items for sale with detailed descriptions and images
- Direct communication between buyers and sellers (Under development)
- Secure transaction handling (Under development)

## Technologies Used
- **Android Jetpack Compose** for building the UI
- **Kotlin** as the primary programming language for Android development
- **Ktor** for handling network requests
- **Choreo** for backend infrastructure and scalability
- **Ballerina** for API development and service integration
- **MongoDB** as the database for storing user and product information

## Setup Instructions

### Prerequisites
- Android Studio installed (latest version)
- Kotlin plugin for Android Studio
- A MongoDB instance for backend database

### How to Run the Project
1. Clone this repository:
    ```bash
    git clone <repository-url>
    ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Run the project on an Android emulator or physical device.

## Backend & API
The backend of this project is powered by Ballerina, handling all API calls and service integrations efficiently. You can find the Ballerina API code and related information by following the steps below.

### How to Run the Backend

1. Clone the repository:
    ```bash
    git clone https://github.com/mtsachintha/Ballerina
    ```
2. Open the project in VS Code.
3. Sync the project with dependencies using Ballerina's package management.
4. Run the Ballerina services with the following command:
    ```bash
    bal run
    ```

***Note: The backend is deployed on Choreo, but since the free plan only allows up to 3 hours of inactivity, the service may be down at times. In such cases, you will need to run the backend locally and update the API URL in the Kotlin services.***

## Contributing
We welcome contributions! Please feel free to submit issues or pull requests if you'd like to contribute to this project.
