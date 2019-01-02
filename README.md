![alt text](https://images.unsplash.com/photo-1533749047139-189de3cf06d3?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=676&q=80 "Logo")

# Air Date Time Picker Library 

An android library written in Kotlin which makes fetching constrained data time selection from user a piece of cake!

[![](https://jitpack.io/v/mumayank/AirDateTimePicker.svg)](https://jitpack.io/#mumayank/AirDateTimePicker)

## Usage
```kotlin
AirDateTimePicker.pickTime(this, textViewTime.tag.toString().toLong(), null, System.currentTimeMillis(), false, false, object: AirDateTimePicker.Callback {
    override fun onSuccess(time: Long) {
        // do something
    }

    override fun onFailure() {
        // can optionally do something
    }
})

AirDateTimePicker.pickDate(this, textViewDate.tag.toString().toLong(), null, System.currentTimeMillis(), object: AirDateTimePicker.Callback {
    override fun onSuccess(time: Long) {
        // do something
    }

    override fun onFailure() {
        // can optionally do something
    }
})

AirDateTimePicker.pickDateTime(this, textViewDateTime.tag.toString().toLong(), null, System.currentTimeMillis(), object: AirDateTimePicker.Callback {
    override fun onSuccess(time: Long) {
        // do something
    }

    override fun onFailure() {
        // can optionally do something
    }
})
```

## Setup
Add this line in your root build.gradle at the end of repositories:

```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' } // this line
  }
}
  ```
Add this line in your app build.gradle:
```gradle
dependencies {
  implementation 'com.github.mumayank:AirDateTimePicker:LATEST_VERSION' // this line
}
```
where LATEST_VERSION is [![](https://jitpack.io/v/mumayank/AirDateTimePicker.svg)](https://jitpack.io/#mumayank/AirDateTimePicker)

That's all!
