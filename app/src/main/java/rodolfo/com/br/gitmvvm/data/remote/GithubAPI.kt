package rodolfo.com.br.gitmvvm.data.remote



import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import rodolfo.com.br.gitmvvm.data.local.entity.Repositorio
import rodolfo.com.br.gitmvvm.data.local.entity.User

interface GithubAPI{

    @GET("{username}")
    fun getUser(@Path("username") username:String) : Single<User>

    @GET("{username}/repos")
    fun getRepositorios(@Path("username") username: String) : Single<List<Repositorio>>

}