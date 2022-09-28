package com.example.risingtest.src.splash

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.risingtest.config.ApplicationClass.Companion.sSharedPreferences
import com.example.risingtest.config.BaseActivity
import com.example.risingtest.databinding.ActivitySplashBinding
import com.example.risingtest.src.login.LoginActivity
import com.example.risingtest.src.main.MainActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({
            loginCheck()
            //startActivity(Intent(this, MainActivity::class.java))
            //finish()
        }, 1500)



    }

    private fun loginCheck(){

        if(sSharedPreferences.getString("X-ACCESS-TOKEN", null) != null){


            Log.d("splashActivity", sSharedPreferences.getString("X-ACCESS-TOKEN", null)!!)

            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            finish()
        }else{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}