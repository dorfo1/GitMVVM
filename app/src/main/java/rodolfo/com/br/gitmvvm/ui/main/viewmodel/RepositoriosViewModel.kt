package rodolfo.com.br.gitmvvm.ui.main.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rodolfo.com.br.gitmvvm.data.local.entity.Repositorio
import rodolfo.com.br.gitmvvm.data.repositories.ListRepositoryImpl
import rodolfo.com.br.gitmvvm.utils.Resource

class RepositoriosViewModel(val listRepository: ListRepositoryImpl) : ViewModel() {

    private val listResource: LiveData<Resource<List<Repositorio>>> = listRepository.repositorios

    val repositorios: LiveData<List<Repositorio>> = Transformations.map(listResource) { resource ->
        if (resource is Resource.Success) resource.data else null
    }

    val loading: LiveData<Boolean> = Transformations.map(listResource) { resource ->
        resource is Resource.Loading
    }

    val error: LiveData<Boolean> = Transformations.map(listResource) { resource ->
        resource is Resource.Error
    }

    fun fetchRepositorios(login:String){
        listRepository.fetchRepositories(login)
    }

    @Suppress("UNCHECKED_CAST")
    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RepositoriosViewModel(ListRepositoryImpl()) as T
        }

    }

}