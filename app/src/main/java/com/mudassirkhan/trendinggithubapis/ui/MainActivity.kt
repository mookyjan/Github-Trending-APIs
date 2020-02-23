package com.mudassirkhan.trendinggithubapis.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.github.ajalt.timberkt.Timber
import com.mudassirkhan.trendinggithubapis.R
import com.mudassirkhan.trendinggithubapis.databinding.ActivityMainBinding
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory
    lateinit var viewModel : TrendingRepositoriesViewModel
    lateinit var mBinding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        AndroidInjection.inject(this)
        viewModel=   ViewModelProviders.of(this, this.viewModelFactory).get(TrendingRepositoriesViewModel::class.java)
        mBinding.viewModel =viewModel

        toolbarSetup()
        viewModel.loadTrendingRepositories()
        observeEvents()

    }

    fun observeEvents(){

        viewModel.empty.observe(this, Observer {

            when(it){
                true -> ly_offline.visibility = View.VISIBLE
                false ->ly_offline.visibility = View.GONE
            }
        })
    }


    fun toolbarSetup(){
        setSupportActionBar(mBinding.trToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
            R.id.action_sort_by_stars ->
                //TODO sort the list
                Toast.makeText(this,"To be implemented",Toast.LENGTH_SHORT).show()
            R.id.action_sort_by_name ->Toast.makeText(this,"To be implemented",Toast.LENGTH_SHORT).show()

        }
        return super.onOptionsItemSelected(item)
    }


}
