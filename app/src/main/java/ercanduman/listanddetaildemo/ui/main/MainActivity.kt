package ercanduman.listanddetaildemo.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ercanduman.listanddetaildemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }
}