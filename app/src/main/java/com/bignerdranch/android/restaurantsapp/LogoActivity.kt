package com.bignerdranch.android.restaurantsapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import com.bignerdranch.android.restaurantsapp.databinding.ActivityLogoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityLogoBinding = DataBindingUtil.setContentView(this, R.layout.activity_logo)

        binding.piece1.animate().apply {
            duration = 1000
            translationY(500F)
        }.start()

        binding.piece2.animate().apply {
            duration = 1000
            translationY(150F)
            translationX(230F)
        }.start()

        binding.piece3.animate().apply {
            duration = 1000
            translationYBy(-590F)
        }.withEndAction {
            binding.piece4.animate().apply {
                duration = 800
                alpha(1F)
            }.start()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this,MainActivity::class.java)
            finish()
            startActivity(intent)
        }, 2000)




    }
}