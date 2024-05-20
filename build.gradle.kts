buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.44.2")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.0-beta02")
        classpath("com.google.gms:google-services:4.4.1")
    }

    repositories {
        mavenCentral()
        maven {
            url = uri("http://maven.google.com/")
            isAllowInsecureProtocol = true
        }
        maven { url = uri("https://www.jitpack.io" ) }
    }
}


// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id ("com.android.application") version "8.3.0" apply false
    id ("com.android.library") version "8.3.0" apply false
    id ("org.jetbrains.kotlin.android") version "1.8.20" apply false
    id ("com.google.gms.google-services") version "4.3.15" apply false
}