package com.game.ag.weathercompose.presentation.screen.home


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavHostController
import com.game.ag.weathercompose.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController:NavHostController
) {

    var txtSearchState by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    val keyboardController = LocalSoftwareKeyboardController.current


    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement =Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {

            OutlinedTextField(
                value = txtSearchState,
                onValueChange = { newValue ->
                    txtSearchState = newValue
                },
                placeholder = { Text(text = "Search ....") },

                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = { keyboardController?.hide() }
                ),
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Search,
                        contentDescription = "Search",
                        )
                },
                maxLines = 1,
                shape = RoundedCornerShape(23.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            )

        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                if (txtSearchState.trim().isNotEmpty()){
                    navController.navigate(Screen.WeatherScreen.rout +"/$txtSearchState")

                }else{
                    Toast.makeText(context, "Write a city", Toast.LENGTH_SHORT).show()
                }
            },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor =  Color.White,
                containerColor = Color.Blue
            ),
            elevation = ButtonDefaults.buttonElevation(
                pressedElevation = 13.dp,
                defaultElevation = 2.dp
            )
        ) {

            Icon(imageVector = Icons.Default.Search, contentDescription = "search")
            Text(text = "Search")
        }

    }
}




