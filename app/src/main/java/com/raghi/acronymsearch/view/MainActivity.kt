package com.raghi.acronymsearch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.raghi.acronymsearch.adapter.AcronymListAdapter
import com.raghi.acronymsearch.network.ApiClient
import com.raghi.acronymsearch.network.ApiInterface
import com.raghi.acronymsearch.network.NetworkUtil
import com.raghi.acronymsearch.viewmodel.AcronymViewModel
import com.raghi.acronymsearch.R
import com.raghi.acronymsearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: AcronymViewModel
    var acronymListAdapter: AcronymListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        acronymListAdapter = AcronymListAdapter(arrayListOf(), this)
        viewModel = ViewModelProvider(this).get(AcronymViewModel::class.java)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = acronymListAdapter
        }

        binding.etSearchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    binding.isShown = false
                }
                try {
                    if (newText?.length!! > 2) {
                        acroSearch(newText)
                    }
                } catch (e: Exception) {
                    Log.d("", e.toString())
                }
                return false
            }
        })

    }

    //calling api call from viewModel
    private fun acroSearch(searchText: String?) {
        if (NetworkUtil.checkConnectionType(this@MainActivity)) {
            var apiClient = ApiClient.apiClient.create(ApiInterface::class.java)
            viewModel.searchAcronym(searchText.toString(), apiClient)
            observeData()
        } else {
            binding.loading = false
            binding.showError = true
        }
    }

    //observing the response,error from viewModel
    private fun observeData() {
        viewModel.acro_response.observe(this@MainActivity, Observer {
            binding.loading = false
            if (it.isNotEmpty()) {
                acronymListAdapter?.updateAcroList(it[0].lfs)
                binding.isShown = true
                binding.showError = false
            } else {
                binding.isShown = false
                binding.showError = true
            }
        })
        viewModel.error.observe(this@MainActivity) {
            binding.showError = it
        }
        viewModel.loading.observe(this@MainActivity, Observer {
            binding.loading = it
        })
    }
}
