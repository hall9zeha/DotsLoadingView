# DotsLoadingView
Open source library that displays a loading view with a custom drawable

## How to use
* This library requires api level 22 as a minimum.
* First add the jitpack source in settings.gradle. In gradle 7 or latest
```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' } //here
    }
}
```
* In earlier versions of gradle 7, gradle project level
```gradle
allprojects {
    repositories {
        google()
        mavenCentral()
              
        maven { url "https://jitpack.io" }//here
        
    }
}
```

* Then add the dependency at application gradle level.
 
```gradle
dependencies{
    ...
    implementation 'com.github.hall9zeha:DotsLoadingView:1.0.1'
}
```
* Xml layout
```xml
...
  <com.barryzeha.dotsloadingview.components.DotsLoadingComponent
        android:id="@+id/myDotLoading"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:dotColor="?attr/colorAccent"
        app:dotSize="20"
        app:numberOfDots="4"
        app:dotType="dot"
        app:startAnimation="true"
        />
...
```
