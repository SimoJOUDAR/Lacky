package fr.mjoudar.lackey.presentation.mainActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import fr.mjoudar.lackey.R

/***********************************************************************************************
 * MainActivity class
 ***********************************************************************************************/
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}