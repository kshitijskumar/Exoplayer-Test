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

    private var playWhenReady = true
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
        val mediaItem = MediaItem.fromUri("https://firebasestorage.googleapis.com/v0/b/learning-firebase-f7960.appspot.com/o/Alan-Walker-Trevor-Guthrie-Do-It-All-For-You.mp3?alt=media&token=bd2900cc-f89d-45b1-bac9-cf831dc8548b")
        player.setMediaItem(mediaItem)

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