package com.raghi.acronymsearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raghi.acronymsearch.model.AcronymResponse
import com.raghi.acronymsearch.network.ApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AcronymViewModel : ViewModel() {

    lateinit var acro_response: MutableLiveData<List<AcronymResponse>>
    var loading = MutableLiveData<Boolean>()
    var error = MutableLiveData<Boolean>()

    fun searchAcronym(text: String, apiClient: ApiInterface) {
        acro_response = MutableLiveData<List<AcronymResponse>>()
        loading.postValue(true)
        error.postValue(false)
        viewModelScope.launch(Dispatchers.IO) {
            val response = apiClient.acroList(text)
            if (response.isSuccessful && response.body() != null) {
                acro_response.postValue(response.body())
            } else {
                onError()
            }
        }
    }

    private fun onError() {
        error.postValue(true)
        loading.postValue(false)
    }

}