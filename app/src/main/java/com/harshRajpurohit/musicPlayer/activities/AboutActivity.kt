package com.harshRajpurohit.musicPlayer.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.harshRajpurohit.musicPlayer.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(MainActivity.currentThemeNav[MainActivity.themeIndex])
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.aboutToolbar)
        binding.aboutToolbar.setNavigationOnClickListener { finish() }
        binding.aboutText.text = aboutText()
    }

    private fun aboutText(): String {
        return "Developed By: Harsh H. Rajpurohit" +
                "\n\nIf you want to provide feedback, I will love to hear that."
    }
}