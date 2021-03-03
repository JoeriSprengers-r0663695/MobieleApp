package com.example.mobieleapp.data.database.dorm

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DormViewModel(private val repository: DormRepository) : ViewModel() {
    val allDorms: MutableLiveData<List<Dorm>> = repository.allDorms.asLiveData() as MutableLiveData<List<Dorm>>


    fun getDormFromUser(id:Int) = runBlocking {
        repository.getDormsForUser(id)
    }


    fun insert(dorm: Dorm) = viewModelScope.launch {
        repository.insert(dorm)
    }
    fun delete() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun deleteSpecific(dorm: Dorm) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteSpecific(dorm)
    }

    fun updateDorm(dorm: Dorm) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateDorm(dorm)
    }
}

class DormViewModelFactory(private val repository: DormRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DormViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DormViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}