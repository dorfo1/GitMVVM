package rodolfo.com.br.gitmvvm.data.local.entity

import androidx.room.PrimaryKey

data class Repositorio (
    @PrimaryKey
    val id:Int,
    val name:String,
    val language:String,
    val userId:Int
)