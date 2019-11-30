package rodolfo.com.br.gitmvvm.utils

sealed class Resource<T>(
    val data : T? = null,
    val msg : String? = null
){
   class Success<T>(data:T) : Resource<T>(data)
   class Loading<T> : Resource<T>()
   class Error<T>(msg: String?) : Resource<T>(null,msg)
}