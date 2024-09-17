// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
    }
    dependencies {
        //For Navigation Component
        val navVersion = "2.7.3"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.8.0")


        //For Firebase Notification
        // Add the Google services classpath
        classpath("com.google.gms:google-services:4.4.2")

    }
}


plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.google.gms.google.services) apply false

}