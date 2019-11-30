package rodolfo.com.br.gitmvvm.data.remote



import retrofit2.http.GET
import retrofit2.http.Path
import rodolfo.com.br.gitmvvm.data.local.entity.Repositorio
import rodolfo.com.br.gitmvvm.data.local.entity.User

interface GithubAPI{

    @GET("{username}")
    suspend fun getUser(@Path("username") username:String) : User

    @GET("{username}/repos")
    suspend fun getRepositorios(@Path("username") username: String) : List<Repositorio>

}