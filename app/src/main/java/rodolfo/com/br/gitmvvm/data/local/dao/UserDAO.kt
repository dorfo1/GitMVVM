package rodolfo.com.br.gitmvvm.data.local.dao

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import rodolfo.com.br.gitmvvm.data.local.entity.User

@Dao
interface UserDAO{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(vararg user:User)

    @Query("SELECT * FROM user WHERE login=:login LIMIT 1")
    suspend fun buscarUsuarioPorLogin(login:String) : User

}