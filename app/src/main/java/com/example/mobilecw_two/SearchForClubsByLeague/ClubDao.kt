package com.example.mobilecw_two.SearchForClubsByLeague

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

//mark the interface as a Room Data Access Object (DAO).
@Dao
interface ClubDao {

    //mark the method as a database insert method.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    // This method inserts a Club into the Club table.
    suspend fun insertClub(club: Club)

    //mark the method as a SQL query method.
    // This method searches the Club table for clubs that match the query and returns them as a List of Club.
    @Query("SELECT * FROM Club WHERE LOWER(Name) LIKE '%' || LOWER(:query) || '%' OR LOWER(strLeague) LIKE '%' || LOWER(:query) || '%'")
    suspend fun searchClubs(query: String): List<Club>


}