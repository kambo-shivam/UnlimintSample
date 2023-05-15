package com.app.unlimint.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.unlimint.R
import com.app.unlimint.data.model.JokesModel
import com.app.unlimint.data.repository.UnlimintRepository
import com.app.unlimint.ui.main.adapter.UnlimintAdapter
import com.app.unlimint.ui.main.viewmodel.UnlimintViewModel
import com.app.unlimint.utils.Status
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class UnlimintActivity : AppCompatActivity() {

    private val repository: UnlimintRepository by inject()
    private val mainViewModel: UnlimintViewModel by inject()
    private lateinit var adapter: UnlimintAdapter
    private var onLaunch = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.visibility = View.VISIBLE
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UnlimintAdapter()
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        mainViewModel.users.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })

        mainViewModel.jokesLiveData.observe(this) {
            if (onLaunch) {
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                for (item in it) {
                    renderList(item)
                }
                onLaunch = false
            }
        }

    }

    private fun renderList(users: JokesModel) {
        adapter.updateData(users)
    }
}
