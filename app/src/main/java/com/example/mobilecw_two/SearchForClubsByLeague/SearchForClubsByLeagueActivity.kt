package com.example.mobilecw_two.SearchForClubsByLeague

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.mobilecw_two.SearchForClubsByLeague.ui.theme.MobileCW_TwoTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SearchForClubsByLeagueActivity : ComponentActivity() {

    //variable for the ClubDatabase instance.
    lateinit var db2 : ClubDatabase
    // variable for the ClubDao instance.
    lateinit var clubDao : ClubDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Building the Room database instance.
        db2 = Room.databaseBuilder(this, ClubDatabase::class.java, "search-club-database").build()
        // Getting the ClubDao instance from the database.
        clubDao = db2.clubDao()

        setContent {
            MobileCW_TwoTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    DatabaseUI()
                }
            }
        }
    }

    @Composable
    fun DatabaseUI() {


        //variables for the list of clubs and the search query.
        var clubs by rememberSaveable { mutableStateOf(listOf<Club>()) }
        var league by rememberSaveable { mutableStateOf("") }
        val scope = rememberCoroutineScope()
        var isLoading by rememberSaveable { mutableStateOf(false) }


        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            // TextField Composable for the search query input.
            TextField(
                value = league,
                onValueChange = { league = it },
                placeholder = { Text("Type Here") }
            )

            Spacer(modifier = Modifier.height(20.dp))

            //"Retrieve Clubs" Button
            Button(
                onClick = {
                    scope.launch {
                        isLoading = true
                        // Fetching the clubs from the API.
                        clubs = fetchClubs(league)
                        isLoading = false
                    }
                }
            ) {
                Text(text = "Retrieve Clubs")
            }

            Spacer(modifier = Modifier.width(10.dp))

            // "Save clubs to Database" Button
            Button(
                onClick = {
                    scope.launch {
                        for (i in clubs){
                            clubDao.insertClub(i)
                        }
                    }
                }
            ) {
                Text(text = "Save clubs to Database")
            }

            Spacer(modifier = Modifier.height(20.dp))

            //displays the fetched clubs using a lazy column.
            LazyColumn (
                modifier = Modifier.padding(16.dp)
            ){
                //club details.
                items(clubs) { club ->
                    Text(text = "idTeam: ${club.idTeam},")
                    Text(text = "Name: ${club.Name},")
                    Text(text = "strTeamShort: ${club.strTeamShort},")
                    Text(text = "strAlternate: ${club.strAlternate},")
                    Text(text = "intFormedYear: ${club.intFormedYear},")
                    Text(text = "strLeague: ${club.strLeague},")
                    Text(text = "idLeague: ${club.idLeague},")
                    Text(text = "strStadium: ${club.strStadium},")
                    Text(text = "strKeywords: ${club.strKeywords},")
                    Text(text = "strStadiumThumb: ${club.strStadiumThumb},")
                    Text(text = "strStadiumLocation: ${club.strStadiumLocation},")
                    Text(text = "intStadiumCapacity: ${club.intStadiumCapacity},")
                    Text(text = "strWebsite: ${club.strWebsite},")
                    Text(text = "strTeamJersey: ${club.strTeamJersey},")
                    Text(text = "strTeamLogo: ${club.strTeamLogo},")
                    Spacer(modifier = Modifier.height(20.dp)) // Add some space between each club
                }
            }
        }
    }
}

//fetches the clubs from the API.
suspend fun fetchClubs(league: String): List<Club> = withContext(Dispatchers.IO) {
    //URL for the API request.
    val url = "https://www.thesportsdb.com/api/v1/json/3/search_all_teams.php?l=$league"
    val connection = URL(url).openConnection() as HttpURLConnection
    // Set the request method
    connection.requestMethod = "GET"
    // Get the response
    val responseCode = connection.responseCode
    if (responseCode == HttpURLConnection.HTTP_OK) {
        val inputStream = connection.inputStream
        //read the response
        val reader = BufferedReader(InputStreamReader(inputStream))
        val line = reader.readLine()
        val clubsJson = JSONObject(line)
        val clubsArray = clubsJson.getJSONArray("teams")
        // Parse the JSONArray into a List of Club
        val clubList = parseClubsFromJson(clubsArray)
        clubList
    } else {
        emptyList()
    }
}

// This is a function that parses the JSON response from the API
fun parseClubsFromJson(jsonArray: JSONArray): List<Club> {
    //empty mutable list to hold the Club objects.
    val clubList = mutableListOf<Club>()
    // Loop through each item in the JSON array.
    for (i in 0 until jsonArray.length()) {
        val club = jsonArray.getJSONObject(i)
        // Create a Club object from the JSON object.
        val clubDetails = Club(
            idTeam = club.getInt("idTeam"),
            Name = club.getString("strTeam"),
            strTeamShort = club.getString("strTeamShort"),
            strAlternate = club.getString("strAlternate"),
            intFormedYear = club.getString("intFormedYear"),
            strLeague = club.getString("strLeague"),
            idLeague = club.getString("idLeague"),
            strStadium = club.getString("strStadium"),
            strKeywords = club.getString("strKeywords"),
            strStadiumThumb = club.getString("strStadiumThumb"),
            strStadiumLocation = club.getString("strStadiumLocation"),
            intStadiumCapacity = club.getString("intStadiumCapacity"),
            strWebsite = club.getString("strWebsite"),
            strTeamJersey = club.getString("strTeamJersey"),
            strTeamLogo = club.getString("strTeamLogo")
        )
        // Add the Club object to the list.
        clubList.add(clubDetails)
    }
    //returns a list of Club objects.
    return clubList
}
