package com.example.brewbuddy.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brewbuddy.domain.usecase.DeleteUserNameUseCase
import com.example.brewbuddy.domain.usecase.GetUserNameUseCase
import com.example.brewbuddy.domain.usecase.SaveUserNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnterNameViewModel @Inject constructor(
    private val saveUserNameUseCase: SaveUserNameUseCase,
    private  val getUserNameUseCase: GetUserNameUseCase,
    private val deleteUserNameUseCase: DeleteUserNameUseCase
) : ViewModel(){

    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> get() = _userName



    init {
        viewModelScope.launch {
            getUserNameUseCase().collect { name ->
                _userName.value=  name
            }
        }
    }
    fun saveName(name: String){
        viewModelScope.launch {
            saveUserNameUseCase(name)
            _userName.value = name
        }
    }

//    fun getName() {
//        viewModelScope.launch {
//            getUserNameUseCase().collect { name ->
//                _userName.value = name
//            }
//        }
//
//    }
    fun logout(){
        viewModelScope.launch {
            deleteUserNameUseCase()
            _userName.value=null
        }
    }

}