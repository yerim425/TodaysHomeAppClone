package com.example.risingtest.src.splash

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
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
        /*val loginStatus = sSharedPreferences.getString("loginStatus", "false")
        if(loginStatus == "false"){
            sSharedPreferences.edit().putString("loginStatus", "false")
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }else{ //"true"
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }*/

        if(sSharedPreferences.getString("X-ACCESS-TOKEN", null) != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}