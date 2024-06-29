package com.game.ag.weathercompose.presentation.screen.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Downloading
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.game.ag.weathercompose.presentation.screen.WeatherViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    backStackEntry: NavBackStackEntry,
    navController: NavHostController
) {
    val city = backStackEntry.arguments?.getString("city")

    val viewModel = hiltViewModel<WeatherViewModel>()
    val weatherAttributeState by viewModel.weatherAttributeState.collectAsState()

    if (city != null) {
        viewModel.getWeather(city)
    }

    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https:" + weatherAttributeState?.current?.condition?.icon)
            .size(Size.ORIGINAL)
            .build()
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Weather",
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            Column(
                Modifier
                    .padding(15.dp)
                    .align(Alignment.TopStart),
                verticalArrangement = Arrangement.spacedBy(25.dp),
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (imageState.state) {

                        AsyncImagePainter.State.Empty -> {
                            Box(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(100.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.ImageNotSupported,
                                    contentDescription = "empty",
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                        }

                        is AsyncImagePainter.State.Error -> {
                            Box(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(100.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.ImageNotSupported,
                                    contentDescription = "error",
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }

                        is AsyncImagePainter.State.Loading -> {
                            Box(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(100.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Downloading,
                                    contentDescription = "error",
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }

                        is AsyncImagePainter.State.Success -> {
                            Image(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .width(80.dp)
                                    .height(80.dp),
                                painter = imageState,
                                contentDescription = "Weather Icon",
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }

                weatherAttributeState?.current?.temp_c.let {
                    Text(text = "Temperature :" + it.toString() + "°", fontSize = 16.sp)
                }

                weatherAttributeState?.location?.country.let {
                    Text(text = "Country :" + it.toString(), fontSize = 16.sp)

                }

                weatherAttributeState?.location?.lat.let {
                    Text(text = "latitude :" + it.toString(), fontSize = 16.sp)

                }

                weatherAttributeState?.location?.lon.let {
                    Text(text = "longitude :" + it.toString(), fontSize = 16.sp)

                }

                weatherAttributeState?.location?.localtime.let {
                    Text(text = "Local Time :" + it.toString(), fontSize = 16.sp)

                }

                weatherAttributeState?.current?.condition?.text.let {
                    Text(text = "Condition :" + it.toString(), fontSize = 16.sp)

                }

                weatherAttributeState?.current?.humidity.let {
                    Text(text = "Humidity :" + it.toString(), fontSize = 16.sp)

                }
            }
        }
    }
}
