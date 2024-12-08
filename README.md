# OpenWeather #
OpenWeather is a tutorial Android app to showcase the best practices and recommended Model-View-ViewModel (MVVM) architecture with Repository pattern using Jetpack Compose Application. It is based on [OpenWeather](https://openweathermap.org/).
It has two screens - A city input screen and a weather result screen.

This app highligts the various layers in the architecture - UI, data, business logic using the below libraries to ensure the code is scalable and maintainable:

Framework  | Library
------------- | -------------
Networking  | Retrofit
Concurrency  | Kotlin Coroutines
Dependency Injection  | Hilt
Navigation  | Navigation Compose

## To Use ##
Replace apiKey in build.gradle(app) module with the actual API key obtained from [OpenWeather API](https://home.openweathermap.org/api_keys).

`buildConfigField("String", "OPEN_WEATHER_API_KEY", "\"apiKey\"")`

