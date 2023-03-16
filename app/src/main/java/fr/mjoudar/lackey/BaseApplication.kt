package fr.mjoudar.lackey

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/***********************************************************************************************
 * BaseApplication class - the root access point of our application
 ***********************************************************************************************/

@HiltAndroidApp
class BaseApplication : Application() {
}