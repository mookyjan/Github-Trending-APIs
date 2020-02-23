package com.mudassirkhan.trendinggithubapis.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mudassirkhan.trendinggithubapis.R
import com.mudassirkhan.trendinggithubapis.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        toolbarSetup()

    }


    fun toolbarSetup() {
        setSupportActionBar(mBinding.trToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }


}
