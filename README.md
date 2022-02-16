# Test-app
This app lists albums from a static Api

## Architecture
It's a MVVM-single activity app + clean architecture

### Layers
UI: Activity - Screen - ViewModel
Domain: UseCase
Data: Mapper - Repository

## Libraries choice
### UI
ViewBinding + Coil.

### Threading
Coroutineswith flows for multi emitting functions in the repo

### Dependency injection
Koin is used because of it's simplcity to set up

### Network
Retrofit and Moshi for easy to use, simple and efficient json parsing

### ORM
Room for it's efficiency, testability and ease of use

Database 
Room for 
