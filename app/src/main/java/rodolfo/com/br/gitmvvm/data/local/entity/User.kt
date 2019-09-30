package rodolfo.com.br.gitmvvm.data.local.entity

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    val id: Int,
    val login : String,
    @ColumnInfo(name = "avatar_url")
    @SerializedName("avatar_url")
    val avatarURL: String,
    @SerializedName("name")
    val nome: String,
    val bio: String?,
    @SerializedName("followers")
    val seguidores: String,
    @SerializedName("public_repos")
    val numeroRepositorios: Int
){
    var dataUpdate : Long = 0
}


fun User.validaData():Boolean {
    return this.dataUpdate > System.currentTimeMillis()
}