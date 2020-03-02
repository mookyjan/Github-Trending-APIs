package com.mudassirkhan.trendinggithubapis.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableList
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.github.ajalt.timberkt.Timber
import com.google.android.material.snackbar.Snackbar
import com.mudassirkhan.githubtrendingapis.ui.adapter.RepositorisListAdapter
import com.mudassirkhan.githubtrendingapis.ui.model.TrendRepositoryModel
import com.mudassirkhan.trendinggithubapis.R
import com.mudassirkhan.trendinggithubapis.databinding.ActivityMainBinding
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: TrendingRepositoriesViewModel
    lateinit var mBinding: ActivityMainBinding
    private lateinit var mAdapter: RepositorisListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        AndroidInjection.inject(this)
//        viewModel =ViewModelProviders.of(this).get(TrendingRepositoriesViewModel::class.java)
        viewModel = ViewModelProviders.of(this, this.viewModelFactory)
            .get(TrendingRepositoriesViewModel::class.java)
        mBinding.viewModel = viewModel
        mBinding.lifecycleOwner =this
        mAdapter = RepositorisListAdapter(viewModel.sortedResult.value!!,null)
        mBinding.rvTrendingRepoList.adapter =mAdapter
        toolbarSetup()
        viewModel.loadTrendingRepositories()
        observeEvents()

    }

    private fun observeEvents() {

        viewModel.empty.observe(this, Observer {

            when (it) {
                true -> ly_offline.visibility = View.VISIBLE
                false -> ly_offline.visibility = View.GONE
            }
        })

        viewModel.sortedResult.observe(this, Observer {
            Timber.d { "sorted list changed $it" }
            mAdapter.setData(it)
            mAdapter.notifyDataSetChanged()
        })

    }


    fun toolbarSetup() {
        setSupportActionBar(mBinding.trToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.action_sort_by_stars ->{
                //TODO sort the list
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "To be implemented",
                    Snackbar.LENGTH_SHORT
                ).show()
                viewModel.sortByStars()
            }
            R.id.action_sort_by_name -> {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "To be implemented",
                    Snackbar.LENGTH_SHORT
                ).show()
                viewModel.sortByNames()
            }

        }
        return super.onOptionsItemSelected(item)
    }


}
