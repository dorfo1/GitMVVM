package rodolfo.com.br.gitmvvm.data.remote

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientAPI<T> {

    private val url: String = "https://api.github.com/users/"

    fun getClient(c: Class<T>): T {

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(getOkHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(c)
    }


    private fun getOkHttpClient(): OkHttpClient.Builder {
        return OkHttpClient.Builder().addNetworkInterceptor(StethoInterceptor())
    }
}

fun getGitHubAPI(): GithubAPI {
    return ClientAPI<GithubAPI>().getClient(GithubAPI::class.java)
}
