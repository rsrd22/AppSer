package com.example.appser.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.appser.core.Resource
import com.example.appser.data.model.CicloVitalEntity
import com.example.appser.data.model.RolEntity
import com.example.appser.repository.CicloVitalRepository
import kotlinx.coroutines.Dispatchers

class CicloVitalViewModel(private val repo: CicloVitalRepository): ViewModel() {
    fun fetchSaveCicloVital(ciclovital: CicloVitalEntity) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.saveCicloVital(ciclovital)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
    fun fetchAllCicloVital()= liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.getAllCicloVital()))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
    fun fetchCicloVitalbyId(id: Long)= liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.getCicloVitalById(id)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun fetchCicloVitalByEdad(edad: Int)= liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.getCicloVitalByEdad(edad)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

}

class CicloVitalViewModelFactory(private val repo: CicloVitalRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CicloVitalRepository::class.java).newInstance(repo)
    }
}