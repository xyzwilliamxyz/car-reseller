package com.carreseller.ui.splashscreen

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.carreseller.R
import com.carreseller.ui.search.manufacturer.ManufacturerSearchActivity
import com.carreseller.utils.Constants.SPLASH_TIME
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            startActivity(ManufacturerSearchActivity.getIntent(this))
            finish()
        }, SPLASH_TIME)
    }
}
