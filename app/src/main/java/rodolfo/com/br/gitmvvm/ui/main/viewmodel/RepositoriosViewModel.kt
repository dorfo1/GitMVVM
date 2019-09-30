package rodolfo.com.br.gitmvvm.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import rodolfo.com.br.gitmvvm.data.local.entity.Repositorio
import rodolfo.com.br.gitmvvm.data.remote.getGitHubAPI

class RepositoriosViewModel : ViewModel(){

    var repositorios = MutableLiveData<List<Repositorio>>()
    var loading = MutableLiveData<Boolean>()
    var error = MutableLiveData<Boolean>()


    init {
        loading.value = false
        error.value = false
    }

    fun fetchRepositorios(username:String){
        loading.value = true
        error.value = false
        getGitHubAPI().getRepositorios(username)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<List<Repositorio>>(){
                override fun onSuccess(t: List<Repositorio>) {
                    loading.value = false
                    repositorios.value = t
                }

                override fun onError(e: Throwable) {
                    loading.value = false
                    error.value = true
                }

            })
    }


}