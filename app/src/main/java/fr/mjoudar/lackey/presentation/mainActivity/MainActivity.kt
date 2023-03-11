package fr.mjoudar.lackey.presentation.mainActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.mjoudar.lackey.R
import fr.mjoudar.lackey.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}