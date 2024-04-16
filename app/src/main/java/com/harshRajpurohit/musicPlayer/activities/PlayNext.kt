package com.harshRajpurohit.musicPlayer.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.harshRajpurohit.musicPlayer.adapters.FavouriteAdapter
import com.harshRajpurohit.musicPlayer.databinding.ActivityPlayNextBinding
import com.harshRajpurohit.musicPlayer.models.Music

class PlayNext : AppCompatActivity() {

    companion object {
        var playNextList: ArrayList<Music> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(MainActivity.currentTheme[MainActivity.themeIndex])
        val binding = ActivityPlayNextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playNextRV.setHasFixedSize(true)
        binding.playNextRV.setItemViewCacheSize(13)
        binding.playNextRV.layoutManager = GridLayoutManager(this, 3)
        binding.playNextRV.adapter = FavouriteAdapter(this, playNextList, playNext = true)

        if (playNextList.isNotEmpty())
            binding.instructionPN.visibility = View.GONE

        binding.backBtnPN.setOnClickListener {
            finish()
        }
    }
}