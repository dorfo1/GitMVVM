package rodolfo.com.br.gitmvvm.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import rodolfo.com.br.gitmvvm.data.local.dao.UserDAO
import rodolfo.com.br.gitmvvm.data.local.entity.User

@Database(entities = [User::class],exportSchema = false,version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDAO

    companion object {
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase? {
            if (INSTANCE == null) {
                synchronized(UserDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, UserDatabase::class.java, "user.db")
                            .build()
                }
            }
            return INSTANCE
        }


        fun destroyInstance(){
            INSTANCE = null
        }
    }
}