package com.example.mobilecw_two.AddLeaguesToDB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version=1, exportSchema = false)
//It is abstract because Room will generate the implementation.
abstract class AppDatabase: RoomDatabase() {
    // This is an abstract method that returns an instance of UserDao.
    abstract fun userDao(): UserDao
}