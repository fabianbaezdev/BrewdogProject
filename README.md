![top](/imgs/brewdogApp.jpg)

### **Purpose**
To show a demo Android App using Kotlin features, a small MVI architecture and latest Android 
libraries from Jetpack in 2023.

### **Description**
Application connects to [PUNK API V2](https://punkapi.com/) to download a catalogue of beers based on 
Brewdog's DIY Dog (Thanks to [@sammdec](https://github.com/sammdec)).

Data is always saved to the device (using data persistence with Room) and is consumed from there when 
it exists.

### **Libraries/concepts used**

* Clean Architecture with MVI pattern in presentation layer
* Jetpack Compose with Material3 design - for UI layer
* Kotlin Coroutines & Kotlin Flow - for concurrency & reactive approach
* Kotlin Serialization converter - for JSON parsing
* Retrofit - for networking
* Hilt - for Dependency Injection pattern implementation
* Room - for local database
* Coil - for image loading
* Navigation Compose - for navigation between screens
* Unit Testing - for data and domain layers
* Instrumental Testing - For ui layer with Jetpack Compose

### **Recommendations for bigger projects** 

This demo uses an MVI pattern only for small or simple projects. For bigger or more complex projects, 
I recommend making the following changes:

* Introduce the Action concept to allow different Intents to converge into a single Action.
* Separate the ViewModel and create Processor and Reducer classes.
* Incorporate Models into the UI Layer.

### **TODO**

* Improve Beer Detail Screen
* Improve Server Error handling to use local data