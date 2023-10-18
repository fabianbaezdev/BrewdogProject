package cl.neoxcore.brewdogproject.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cl.neoxcore.brewdogproject.presentation.detail.DetailMVI
import cl.neoxcore.brewdogproject.presentation.detail.DetailViewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    onNavigationRequested: (navigationEffect: DetailMVI.Effect.Navigation) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when (state) {

        is DetailMVI.MainUiState.ErrorUiState -> {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Error"
            )
        }

        DetailMVI.MainUiState.LoadingUiState -> {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Loading"
            )
        }

        DetailMVI.MainUiState.DefaultUiState -> {

        }

        is DetailMVI.MainUiState.DisplayBeerUiState -> {
            val beer = (state as DetailMVI.MainUiState.DisplayBeerUiState).beer
            Scaffold(
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = {
                            Text(beer.name)
                        },
                        navigationIcon = {
                            IconButton(onClick = { onNavigationRequested(DetailMVI.Effect.Navigation.BackNavigation) }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    AsyncImage(
                        model = beer.image,
                        contentDescription = "Beer Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.White)
                            .padding(6.dp)
                    )
                    Column {
                        Text(
                            text = "ABV: ${beer.abv}",
                            modifier = Modifier
                                .padding(6.dp, 0.dp),
                            textAlign = TextAlign.Left,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "IBU: ${beer.ibu}",
                            modifier = Modifier
                                .padding(6.dp, 0.dp),
                            textAlign = TextAlign.Left,
                            fontSize = 12.sp
                        )
                        Text(
                            text = beer.tagLine,
                            modifier = Modifier
                                .padding(6.dp, 0.dp),
                            textAlign = TextAlign.Left,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }


}