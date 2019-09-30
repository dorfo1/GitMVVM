package rodolfo.com.br.gitmvvm.ui.main.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import rodolfo.com.br.gitmvvm.data.local.UserDatabase
import rodolfo.com.br.gitmvvm.data.local.entity.User
import rodolfo.com.br.gitmvvm.data.local.entity.validaData
import rodolfo.com.br.gitmvvm.data.remote.getGitHubAPI

class UserViewModel(application: Application) : AndroidViewModel(application) {

    val context = application
    var user = MutableLiveData<User>()
    var loading = MutableLiveData<Boolean>()
    var error = MutableLiveData<Boolean>()

    private var job = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main + job)


    init {
        loading.value = false
        error.value = false
    }

    fun fetchUser(username: String) {
        loading.value = true
        error.value = false
        user.value = null
        uiScope.launch {
            var userRetornado = UserDatabase.getInstance(context)?.userDao()?.buscarUsuarioPorLogin(username)
            if (validaUsuario(userRetornado)) {
                user.value = userRetornado
                loading.value = false
                context.Toast("Usuário do Banco")
            } else {
                fetchUserRemote(username)
                context.Toast("Usuário da internet")
            }
        }
    }

    private fun validaUsuario(userRetornado: User?): Boolean {
        return (userRetornado != null && userRetornado.validaData())
    }

    private fun fetchUserRemote(username: String) {
        getGitHubAPI().getUser(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<User>() {
                override fun onSuccess(userResponse: User) {
                    user.value = userResponse
                    loading.value = false
                    salvaUserNoBanco(userResponse)
                }

                override fun onError(e: Throwable) {
                    error.value = true
                    loading.value = false
                }
            })
    }

    private fun salvaUserNoBanco(userResponse: User) {
        userResponse.dataUpdate = System.currentTimeMillis() + 5 * 60 * 1000
        uiScope.launch {
            UserDatabase.getInstance(context)?.userDao()?.inserir(userResponse)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
        UserDatabase.destroyInstance()
    }
}

fun Context.Toast(msg:String){
    android.widget.Toast.makeText(this,msg, android.widget.Toast.LENGTH_SHORT).show()
}



