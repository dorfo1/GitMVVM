package rodolfo.com.br.gitmvvm.data.repositories

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import rodolfo.com.br.gitmvvm.data.local.entity.Repositorio
import rodolfo.com.br.gitmvvm.data.remote.getGitHubAPI
import rodolfo.com.br.gitmvvm.utils.Resource
import java.lang.Exception

interface ListRepository {

    fun fetchRepositories(login: String)

    fun clearJobs()
}

class ListRepositoryImpl : ListRepository {

    private var job = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main + job)

    private val _repositorios = MutableLiveData<Resource<List<Repositorio>>>()
    val repositorios get() = _repositorios


    override fun fetchRepositories(login: String) {
        _repositorios.postValue(Resource.Loading())
        uiScope.launch {
            try {
                val repositorios = getGitHubAPI().getRepositorios(login)
                _repositorios.postValue(Resource.Success(repositorios))
            }catch (error : Exception){
                _repositorios.postValue(Resource.Error(msg = "Falha ao buscar repositórios."))
            }
        }
    }

    override fun clearJobs() {
        job.cancel()
    }


}