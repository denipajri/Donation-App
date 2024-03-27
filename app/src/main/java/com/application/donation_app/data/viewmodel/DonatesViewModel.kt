package com.application.donation_app.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.application.donation_app.data.database.Donate
import com.application.donation_app.data.database.DonateDao
import com.application.donation_app.data.database.DonateDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DonatesViewModel(application: Application) : AndroidViewModel(application) {

    private val dao: DonateDao
    private val _allDonates = MutableLiveData<List<Donate>>()
    private val _getDonate = MutableLiveData<Donate>()

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    val allDonates: LiveData<List<Donate>> = _allDonates
    val getDonate: LiveData<Donate> = _getDonate

    init {
        dao = DonateDatabase.getInstance(application).donatesDao()
    }

    fun getDonates() {
        viewModelScope.launch(Dispatchers.IO) {
            _allDonates.postValue(dao.getAllDonates())
        }
    }

    fun getDonate(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _getDonate.postValue(dao.getDonate(id))
        }
    }

    fun addDonate(donate: Donate) {
        viewModelScope.launch(Dispatchers.IO) {
            executorService.execute {dao.insertDonate(donate)}
        }
        getDonates()
    }

    fun deleteDonate(donate: Donate) {
        viewModelScope.launch(Dispatchers.IO) {
            executorService.execute {dao.deleteDonate(donate)}
        }
        getDonates()
    }

    fun updateDonate(id: Int, donaturName: String, tglDonasi: String, category: String, amount: String) {
        viewModelScope.launch(Dispatchers.IO) {
            executorService.execute {
                dao.updateDonate(id, donaturName, tglDonasi, category, amount)
            }
        }
        getDonates()
    }
}