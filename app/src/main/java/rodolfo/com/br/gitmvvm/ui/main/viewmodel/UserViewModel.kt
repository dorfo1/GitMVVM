package rodolfo.com.br.gitmvvm.ui.main.viewmodel


import android.content.Context
import androidx.lifecycle.*
import rodolfo.com.br.gitmvvm.data.local.entity.User
import rodolfo.com.br.gitmvvm.data.repositories.UserRepositoryImpl
import rodolfo.com.br.gitmvvm.utils.Resource

class UserViewModel(private val userRepository: UserRepositoryImpl) : ViewModel() {


    private val userResource : LiveData<Resource<User>> = userRepository.user

    val user : LiveData<User> = Transformations.map(userResource){ resource ->
        if(resource is Resource.Success) resource.data else null
    }

    val loading : LiveData<Boolean> = Transformations.map(userResource){ resource ->
        resource is Resource.Loading
    }

    val error : LiveData<Boolean> = Transformations.map(userResource){resource ->
        resource is Resource.Error
    }

    fun fetchUser(login:String){
        userRepository.fetchUser(login)
    }

    override fun onCleared() {
        super.onCleared()
        userRepository.clearJobs()
    }


    @Suppress("UNCHECKED_CAST")
    class Factory(val context: Context) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UserViewModel(UserRepositoryImpl(context)) as T
        }

    }
}




