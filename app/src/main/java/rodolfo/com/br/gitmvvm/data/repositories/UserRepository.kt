package rodolfo.com.br.gitmvvm.data.repositories

import android.content.Context
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import rodolfo.com.br.gitmvvm.data.local.UserDatabase
import rodolfo.com.br.gitmvvm.data.local.entity.User
import rodolfo.com.br.gitmvvm.data.local.entity.validaData
import rodolfo.com.br.gitmvvm.data.remote.getGitHubAPI
import rodolfo.com.br.gitmvvm.utils.Resource
import java.lang.Exception

interface UserRepository {

    fun fetchUser(login: String)

    fun clearJobs()
}


class UserRepositoryImpl(private val context: Context) : UserRepository {

    private val _user = MutableLiveData<Resource<User>>()
    val user get() = _user

    private var job = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun fetchUser(login: String) {
        _user.postValue(Resource.Loading())
        uiScope.launch {
            val usuarioBanco =
                UserDatabase.getInstance(context)?.userDao()?.buscarUsuarioPorLogin(login)
            if (validaUsuario(usuarioBanco)) {
                _user.postValue(Resource.Success(usuarioBanco!!))
            } else {
                fetchUserRemote(login)
            }
        }
    }

    private fun fetchUserRemote(login: String) {
        uiScope.launch {
            try {
                val user = getGitHubAPI().getUser(login)
                _user.postValue(Resource.Success(user))
                salvaUserNoBanco(user)
            } catch (error: Exception) {
                _user.postValue(Resource.Error("Falha ao consultar usuário."))
            }
        }
    }

    private fun validaUsuario(user: User?): Boolean {
        return (user != null && user.validaData())
    }

    private fun salvaUserNoBanco(userResponse: User) {
        userResponse.dataUpdate = System.currentTimeMillis() + 5 * 60 * 1000
        uiScope.launch {
            UserDatabase.getInstance(context)?.userDao()?.inserir(userResponse)
        }
    }

    override fun clearJobs() {
        job.cancel()
    }


}