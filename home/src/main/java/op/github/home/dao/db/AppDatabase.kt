package op.github.home.dao.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import op.github.home.bean.ArticleData
import op.github.home.bean.RemoteKey
import op.github.home.bean.RemoteKeyDao
import op.github.home.dao.ArticleDao

/**
 * Author: pk
 * Time: 2022/2/8  4:56 下午
 * Description:
 */
@Database(
    entities = [ArticleData::class, RemoteKey::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun remoteKeyDao(): RemoteKeyDao

    companion object{
        private const val DB_NAME = "app.db"

        @Volatile
        private var instance: AppDatabase? = null

        fun get(context: Context): AppDatabase{
            return instance ?: Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build().also {
                instance = it
            }
        }
    }
}