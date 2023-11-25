# EpicSys

# Project Overview

### First Screen (HomeFragment):

The main activity hosts the navigation fragment, facilitating transitions between three main fragments: HomeFragment, DetailsFragment, and FavoriteFragment. The project employs two repositories, one serving as an interface (domain layer) and the other as RepoImp (data layer). RepoImp manages API and Local Database methods. The HomeFragment incorporates a ViewModel injected by Hilt, eliminating the need for a ViewModelFactory. The repository is utilized to invoke API methods, retrieve all airlines, and convey the data to the view through StateFlow. The Resource class is employed to handle responses, managing errors, success, and loading states.

### Second Screen (DetailsFragment):

To obtain information about the selected airline in the DetailsFragment, the airline model is made parcelable for navigation between fragments. A floating button is implemented to save the airline in the FavoriteFragment using Room Database. Additionally, implicit intent is employed to initiate a pre-filled phone call or open the website.

### Third Screen (FavoriteFragment):

A ViewModel is implemented to manage logic in the FavoriteFragment, overseeing the display and deletion of airlines. The fragment showcases the list of favorite airlines retrieved from the local database. The swipe-to-delete functionality allows users to unfavorite airlines.

## Libraries and Technologies Used

- **Clean Architecture**: Divides components into three layers (data, domain, presentation).
  
- **Repository Pattern**: Isolates the data layer from the rest of the application.

- **Room**: Stores favorite airlines in the local database.

- **Retrofit**: Establishes an HTTP connection with the REST API and converts airline JSON files to objects.

- **Hilt**: Facilitates dependency injection, ensuring clean and modular code.

- **Coroutines**: Manages API requests and database operations in background threads.

- **MVVM and StateFlow**: Segregates logic code from views and preserves state during screen configuration changes.

- **Navigation Component**: Employs a single activity containing multiple fragments instead of creating multiple activities.

- **View Binding**: Automates the process of inflating views.

- **Glide**: Loads images into ImageView components.
