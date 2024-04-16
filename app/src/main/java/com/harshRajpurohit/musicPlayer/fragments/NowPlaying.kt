package com.harshRajpurohit.musicPlayer.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.harshRajpurohit.musicPlayer.R
import com.harshRajpurohit.musicPlayer.activities.MainActivity
import com.harshRajpurohit.musicPlayer.activities.PlayerActivity
import com.harshRajpurohit.musicPlayer.databinding.FragmentNowPlayingBinding
import com.harshRajpurohit.musicPlayer.models.formatDuration
import com.harshRajpurohit.musicPlayer.models.setSongPosition

class NowPlaying : Fragment() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var binding: FragmentNowPlayingBinding
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireContext().theme.applyStyle(MainActivity.currentTheme[MainActivity.themeIndex], true)
        val view = inflater.inflate(R.layout.fragment_now_playing, container, false)
        binding = FragmentNowPlayingBinding.bind(view)
        binding.root.visibility = View.INVISIBLE
        binding.playPauseBtnNP.setOnClickListener {
            if (PlayerActivity.isPlaying) pauseMusic() else playMusic()
        }
        binding.nextBtnNP.setOnClickListener {
            setSongPosition(increment = true)
            PlayerActivity.musicService!!.createMediaPlayer()
            Glide.with(requireContext())
                .load(PlayerActivity.musicListPA[PlayerActivity.songPosition].artUri)
                .apply(
                    RequestOptions().placeholder(R.drawable.music_player_icon_slash_screen)
                        .centerCrop()
                )
                .into(binding.songImgNP)
            binding.tvSeekBarStart.text =
                formatDuration(PlayerActivity.musicService!!.mediaPlayer!!.currentPosition.toLong())
            binding.tvSeekBarEnd.text =
                formatDuration(PlayerActivity.musicService!!.mediaPlayer!!.duration.toLong())
            binding.songNameNP.text = PlayerActivity.musicListPA[PlayerActivity.songPosition].title
            binding.songAlbumNP.text = PlayerActivity.musicListPA[PlayerActivity.songPosition].album
            binding.seekBarPA.progress =
                PlayerActivity.musicService!!.mediaPlayer!!.currentPosition
            binding.seekBarPA.max =
                PlayerActivity.musicService!!.mediaPlayer!!.duration
            PlayerActivity.musicService!!.showNotification(R.drawable.pause_icon)
            playMusic()
        }
        binding.root.setOnClickListener {
            val intent = Intent(requireContext(), PlayerActivity::class.java)
            intent.putExtra("index", PlayerActivity.songPosition)
            intent.putExtra("class", "NowPlaying")
            ContextCompat.startActivity(requireContext(), intent, null)
        }

        binding.seekBarPA.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    PlayerActivity.musicService!!.mediaPlayer!!.seekTo(progress)
                    PlayerActivity.musicService!!.showNotification(if (PlayerActivity.isPlaying) R.drawable.pause_icon else R.drawable.play_icon)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })

        return view
    }

    override fun onResume() {
        super.onResume()
        if (PlayerActivity.musicService != null) {
            binding.root.visibility = View.VISIBLE
            binding.songNameNP.isSelected = true
            Glide.with(requireContext())
                .load(PlayerActivity.musicListPA[PlayerActivity.songPosition].artUri)
                .apply(
                    RequestOptions().placeholder(R.drawable.music_player_icon_slash_screen)
                        .centerCrop()
                )
                .into(binding.songImgNP)
            binding.tvSeekBarStart.text =
                formatDuration(PlayerActivity.musicService!!.mediaPlayer!!.currentPosition.toLong())
            binding.tvSeekBarEnd.text =
                formatDuration(PlayerActivity.musicService!!.mediaPlayer!!.duration.toLong())
            binding.songNameNP.text = PlayerActivity.musicListPA[PlayerActivity.songPosition].title
            binding.songAlbumNP.text = PlayerActivity.musicListPA[PlayerActivity.songPosition].album
            binding.seekBarPA.progress =
                PlayerActivity.musicService!!.mediaPlayer!!.currentPosition
            binding.seekBarPA.max =
                PlayerActivity.musicService!!.mediaPlayer!!.duration
            if (PlayerActivity.isPlaying) binding.playPauseBtnNP.setIconResource(R.drawable.pause_icon)
            else binding.playPauseBtnNP.setIconResource(R.drawable.play_icon)
            PlayerActivity.musicService!!.seekBarMiniPlayerSetup()
        }
    }

    private fun playMusic() {
        PlayerActivity.isPlaying = true
        PlayerActivity.musicService!!.mediaPlayer!!.start()
        binding.playPauseBtnNP.setIconResource(R.drawable.pause_icon)
        PlayerActivity.musicService!!.showNotification(R.drawable.pause_icon)
    }

    private fun pauseMusic() {
        PlayerActivity.isPlaying = false
        PlayerActivity.musicService!!.mediaPlayer!!.pause()
        binding.playPauseBtnNP.setIconResource(R.drawable.play_icon)
        PlayerActivity.musicService!!.showNotification(R.drawable.play_icon)
    }
}