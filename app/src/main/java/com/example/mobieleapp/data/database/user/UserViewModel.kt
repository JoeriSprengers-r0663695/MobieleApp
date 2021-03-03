package com.example.mobieleapp.data.database.user

import androidx.lifecycle.*
import com.example.mobieleapp.data.database.dorm.Dorm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    val allUsers: MutableLiveData<List<User>> = repository.allUsers.asLiveData() as MutableLiveData<List<User>>
    /*
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun getById(id : Int) = runBlocking {
        repository.userByid(id)
    }

    fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)

    }fun delete() = viewModelScope.launch {
        repository.deleteAll()

    }

    fun updateUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateUser(user)
    }

}

class UserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}