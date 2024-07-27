package com.example.mobilecw_two.AddLeaguesToDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

//mark the interface as a Room Data Access Object (DAO
@Dao
interface UserDao {
    //mark the method as a SQL query method.
    // This method retrieves all users and returns them as a Flow of List of User.
    @Query("SELECT * FROM user")
    fun getAllUsers(): Flow<List<User>>

    //mark the method as a database insert method.
    // This method inserts a User into the User table.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    // This method deletes all users from the User table.
    @Query("DELETE FROM user")
    suspend fun deleteAllUsers()

}