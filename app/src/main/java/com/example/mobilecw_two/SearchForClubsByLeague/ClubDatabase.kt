package com.example.mobilecw_two.SearchForClubsByLeague

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Club::class], version=1, exportSchema = false)
abstract class ClubDatabase: RoomDatabase() {
    abstract fun clubDao(): ClubDao

    // This is a companion object,
    //all its members belong to the class itself,
    companion object {

        // mark the INSTANCE variable as volatile,
        @Volatile
        private var INSTANCE: ClubDatabase? = null

        fun getInstance(context: Context): ClubDatabase {
            // If the INSTANCE is not null, it returns it.
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ClubDatabase::class.java,
                    "club_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}