# GitHub-Trending-App

### Structure of the code ###
Simple Android Application written in Kotlin.
This project follows Clean Architecture with MVVM with Clean Architecture Design


# Main libraries used

* Data Binding
* Dagger2
* RxJava2
* Retrofit2
* Timber
* Room
* Junit
* Espresso
* Circle ImageView
* Glide
* sdp library (for universal screen resolution)
* shimmer effect lib
* Android Architecture component Jetpack


# Modules


* `data/` : contains the code to access to the data (repository pattern)
* `domain/` : contains the business logic and the usecases
* `app` : Presentation layer, contains the UI 

this project consist of one screen. on the first screen showing the list of trending repositories and on clicking the item
it is expanding and by clicking again item collapse.
Swipe to Refresh can be used to refresh data by force
normally the new data can be fetched from server after 2 hours
and for the simplicity of this project many things have been kept simple
like 
* ErrorHandling, 
* Internet connectivity and 
* Design of the app is also kept sample and can be improved much more

comments are written with the function that what it will do.

also TODO are given in the area which we can improve more.


##TODO
Sorting the list by names and stars and add menu
improving the design





