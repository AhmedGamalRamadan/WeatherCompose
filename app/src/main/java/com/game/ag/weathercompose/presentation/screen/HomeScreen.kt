package com.game.ag.weathercompose.presentation.screen


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.rounded.Downloading
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.game.ag.weathercompose.util.WeatherAttributeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    val viewModel = hiltViewModel<WeatherViewModel>()
    val weatherAttributeState by viewModel.weatherAttributeState.collectAsState()


    var txtSearchState by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val keyboardController = LocalSoftwareKeyboardController.current
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {

            OutlinedTextField(
                value = txtSearchState,
                onValueChange = { newValue ->
                    txtSearchState = newValue
                    if (txtSearchState.trim().isNotEmpty()) {
//                        viewModel.getWeather(newValue)
                        viewModel.updateSearchQuery(newValue)
                    }
                },
                placeholder = { Text(text = "Search ....") },

                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = { keyboardController?.hide() }
                ),
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search")
                },
                maxLines = 1,
                shape = RoundedCornerShape(23.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            )

        }

        when (val state = weatherAttributeState) {

            is WeatherAttributeState.Success -> {
                val response = state.response
                val current = response.current
                val location = response.location

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = current.temp_c.toString(), fontSize = 34.sp)
                    Spacer(modifier = Modifier.width(23.dp))

                    val imageState = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(current.condition.icon)
                            .size(Size.ORIGINAL)
                            .build()
                    )

                    when (imageState.state) {
                        is AsyncImagePainter.State.Error -> {
                            Icon(
                                imageVector = Icons.Rounded.ImageNotSupported,
                                contentDescription = "error",
                                modifier = Modifier.size(80.dp)
                            )
                        }
                        is AsyncImagePainter.State.Loading -> {
                            Icon(
                                imageVector = Icons.Rounded.Downloading,
                                contentDescription = "loading",
                                modifier = Modifier.size(80.dp)
                            )
                        }
                        is AsyncImagePainter.State.Success -> {
                            Image(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .size(80.dp),
                                painter = imageState,
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }
                        else -> {}
                    }
                }

                // Display additional weather details
                Text(text = location.region)
                Text(text = location.localtime)
                Text(text = current.condition.text)
            }
            is WeatherAttributeState.Error -> {
                Text(text = "Error: ${state.message}", color = Color.Red, fontSize = 20.sp)
            }
            null -> {
                // Initial or loading state
            }
        }
    }
}






/*
 LazyColumn {
                    items(15) {
                        ShimmerListItem(
                            isLoading = true
                        )
                    }
                }
 */

//            val cittty = viewModel.weatherAttributeState.value!!.location.country

//            viewModel.weatherAttributeState.value?.let {
//                WeatherCardCurrent(it)
//            }



