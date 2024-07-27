//https://drive.google.com/drive/folders/1DCXkYY3QMd0HzpNWXTpeHt4pycbxEm-Z?usp=sharing


package com.example.mobilecw_two

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.mobilecw_two.AddLeaguesToDB.AppDatabase
import com.example.mobilecw_two.AddLeaguesToDB.UserDao
import com.example.mobilecw_two.AddLeaguesToDB.leagues
import com.example.mobilecw_two.AdditionalButton.Additional_Activity
import com.example.mobilecw_two.SearchForClubs.SearchForClubs
import com.example.mobilecw_two.SearchForClubsByLeague.SearchForClubsByLeagueActivity
import com.example.mobilecw_two.ui.theme.MobileCW_TwoTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    // Declares a private lateinit property named db of type AppDatabase.
    private lateinit var db : AppDatabase
    // Declares a private lateinit property named userDao of type UserDao.
    private lateinit var userDao : UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initializes the db property
        db = Room.databaseBuilder(this, AppDatabase::class.java, "club-database").build()
        userDao = db.userDao()

        setContent {
            MobileCW_TwoTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeUI()
                }
            }
        }
    }

    @Composable
    fun HomeUI() {
        // Gets the current Context.
        val context = LocalContext.current
        // Remembers a CoroutineScope.
        val scope = rememberCoroutineScope()

        // Creates a new Column and sets the verticalArrangement to SpaceEvenly, the horizontalAlignment to CenterHorizontally, and the modifier.
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {

            // Creates a new Button "Add Leagues to DB"
            Button(
                modifier = Modifier
                    .width(250.dp)
                    .height(150.dp),
                onClick = {

                    // Iterates over the leagues list and Inserts each league into the database..
                    scope.launch {
                        for (i in leagues){
                            userDao.insertUser(i)
                        }
                    }

                    // Sets the text of the Toast to "Done".
                    Toast.makeText(context,
                        "Done",
                        Toast.LENGTH_SHORT).show()
                }
            ) {
                Text(text = "Add Leagues to DB")
            }

            // Creates a new Button "Search for Clubs By League".
            Button(
                modifier = Modifier
                    .width(250.dp)
                    .height(150.dp),
                onClick = {
                    //Navigate to the SearchForClubsByLeagueActivity.
                    val intent = Intent(context, SearchForClubsByLeagueActivity::class.java)
                    context.startActivity(intent)
                }
            ) {
                Text(text = "Search for Clubs By League")
            }

            // Creates a new Button "Search for Clubs".
            Button(
                modifier = Modifier
                    .width(250.dp)
                    .height(150.dp),
                onClick = {
                    //Navigate  to the SearchForClubs.
                   val intent = Intent(context, SearchForClubs::class.java)
                   context.startActivity(intent)
                }) {
                Text(text = "Search for Clubs ")
            }

            // Creates a new Button "Web service".
            Button(
                modifier = Modifier
                    .width(250.dp)
                    .height(150.dp),
                onClick = {
                    //Navigate to the Additional_Activity.
                    val intent = Intent(context, Additional_Activity::class.java)
                    context.startActivity(intent)
                }) {
                Text(text = "Web service ")
            }
        }
    }
}