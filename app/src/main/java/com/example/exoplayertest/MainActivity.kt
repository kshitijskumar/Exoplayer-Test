package com.example.exoplayertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.exoplayertest.databinding.ActivityMainBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.Util

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var player: SimpleExoPlayer

    private var playWhenReady = false
    private var playBackPosition = 0L
    private var currentWindow = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    private fun initializePlayer(){
        player = SimpleExoPlayer
                .Builder(this)
                .build()
        binding.playerView.player = player

//        val mediaItem = MediaItem.fromUri("https://storage.googleapis.com/exoplayer-test-media-0/play.mp3")
//        val mediaItem = MediaItem.Builder()
//            .setMediaId("Do it all for you")
//            .setUri("https://drive.google.com/file/d/1z1uiN8tALbt_AHokuoUv5Zq8eAYqoZCT/view")
//            .build()
        val doItForYou = MediaItem.fromUri("https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/Alan-Walker-Trevor-Guthrie-Do-It-All-For-You.mp3?alt=media&token=bd2900cc-f89d-45b1-bac9-cf831dc8548b")
        val karasunoFly = MediaItem.fromUri("https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/HAIKYUU_RAP_SONG_Fly_RUSTAGE_ft_CG5%5BGetVideo.watch%5D.mp3?alt=media&token=9843ee81-4a98-4617-b4d4-9b9e989f2fa2")
        val allTimeLow = MediaItem.fromUri("https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/Jon%20Bellion%20-%20All%20Time%20Low%20(Official%20Music%20Video).mp3?alt=media&token=00d83a17-81c4-4935-84c4-a54afa858dec")

        player.setMediaItem(doItForYou)
        player.addMediaItem(karasunoFly)
        player.addMediaItem(allTimeLow)
        player.shuffleModeEnabled = true

        player.playWhenReady = playWhenReady
        player.seekTo(currentWindow, playBackPosition)

        player.prepare()
    }

    private fun releasePlayer(){
        if (this::player.isInitialized){
            playWhenReady = player.playWhenReady
            currentWindow = player.currentWindowIndex
            playBackPosition = player.currentPosition
            player.release()
        }
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT>=24){
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT<24 || !this::player.isInitialized){
            initializePlayer()
        }

        Log.d("Player", "Player is: $player")
        Log.d("Player", "Play when ready is: $playWhenReady")
        Log.d("Player", "current window is: $currentWindow")
        Log.d("Player", "Play back position is: $playBackPosition")
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24){
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24){
            releasePlayer()
        }
    }
}