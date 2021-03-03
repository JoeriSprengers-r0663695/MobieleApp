package com.example.mobieleapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mobieleapp.data.database.dorm.Dorm
import com.example.mobieleapp.data.database.dorm.DormDao
import com.example.mobieleapp.data.database.user.User
import com.example.mobieleapp.data.database.user.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [User::class, Dorm::class], version = 1, exportSchema = false)
    public abstract class RoomDatabase : RoomDatabase() {

        abstract fun userDao(): UserDao
        abstract fun dormDao(): DormDao


        private class databaseCallBack(
            private val scope: CoroutineScope
        ) : Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        var userDao = database.userDao()
                        var dormDao = database.dormDao()
                        var user0 = User(0,"a","a","Renter", "dieter@email.com", "23455699")
                        var user1 = User(1,"b","b","Owner", "dieter@email.com", "12234556")

                        var kot = Dorm(0,"groot huis","Distelstraat",70,"tienen",3300,50.50,"bladibla",0)

                        userDao.insert(user0)
                        userDao.insert(user1)
                        dormDao.insert(kot)


                    }
                }
            }
        }

        companion object {
            @Volatile
            private var INSTANCE: com.example.mobieleapp.data.database.RoomDatabase? = null

            fun getDatabase(
                context: Context,
                scope: CoroutineScope
            ): com.example.mobieleapp.data.database.RoomDatabase {
                // if the INSTANCE is not null, then return it,
                // if it is, then create the database
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        com.example.mobieleapp.data.database.RoomDatabase::class.java,
                        "roomDatabase"
                    )
                        .addCallback(databaseCallBack(scope))
                        .build()
                    INSTANCE = instance
                    // return instance
                    instance
                } as com.example.mobieleapp.data.database.RoomDatabase
            }
        }
    }
