
# DotsLoadingView

<p align="center">
<a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
<a href="https://github.com/hall9zeha/DotsLoadingView/actions"><img alt="Build Status" src="https://github.com/hall9zeha/DotsLoadingView/workflows/DotsLoadingView/badge.svg"/></a> 
<a href="https://jitpack.io/#hall9zeha/DotsLoadingView"><img alt="License" src="https://jitpack.io/v/hall9zeha/DotsLoadingView.svg"/></a>
</p>

Open source library that displays a loading view with a custom drawable

## How to use
* This library requires api level 21 as a minimum.
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
    implementation 'com.github.hall9zeha:DotsLoadingView:2.0.0'
}
```
* Xml layout
```xml
...
  <com.barryzeha.dotsloadingview.components.DotsLoadingComponent
        android:id="@+id/myDotLoading"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:dotColor="?attr/colorAccent"
        app:dotSize="20"
        app:animationDuration="1000"                                                        
        app:numberOfDots="4"
        app:dotMargin="4"                                                         
        app:dotType="dot"
        app:startAnimation="true"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintTop_toTopOf="parent"                                                          
        />
...
```
* When using the dotSrc field it must be placed in the dotType resource property

```xml
  <com.barryzeha.dotsloadingview.components.DotsLoadingComponent
       
        app:dotSrc="@drawable/ic_my_drawable"
        app:dotType="resource"
        app:startAnimation="true"
        />

```
## Screenshots
||||
|--|--|--|
|Dot Type(for default) |Square Type|Resource Type (drawable custom)|
|<img src="https://github.com/hall9zeha/DotsLoadingView/blob/main/screenshots/captura1.gif" width=80% height=80% />|<img src="https://github.com/hall9zeha/DotsLoadingView/blob/main/screenshots/captura2.gif" width=80% height=80% />|<img src="https://github.com/hall9zeha/DotsLoadingView/blob/main/screenshots/captura3.gif" width=80% height=80% />|

# License
```xml
Designed and developed by 2023 hall9zeha (Barry Zea H.)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
