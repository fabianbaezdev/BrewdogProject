package cl.neoxcore.brewdogproject.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
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
                modifier = Modifier
                    .padding(8.dp)
                    .testTag("ErrorText"),
                text = "Error"
            )
        }

        DetailMVI.MainUiState.LoadingUiState -> {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .testTag("LoadingText"),
                text = "Loading"
            )
        }

        DetailMVI.MainUiState.DefaultUiState -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("DefaultBox")
            ) {
                CircularProgressIndicator()
            }
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
                            Text(
                                text = beer.name,
                                modifier = Modifier
                                    .testTag("BeerNameText")
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { onNavigationRequested(DetailMVI.Effect.Navigation.BackNavigation) }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    )
                },
                modifier = Modifier
                    .testTag("BeerDetail")
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
                                .padding(6.dp, 0.dp)
                                .testTag("ABVText"),
                            textAlign = TextAlign.Left,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "IBU: ${beer.ibu}",
                            modifier = Modifier
                                .padding(6.dp, 0.dp)
                                .testTag("IBUText"),
                            textAlign = TextAlign.Left,
                            fontSize = 12.sp
                        )
                        Text(
                            text = beer.tagLine,
                            modifier = Modifier
                                .padding(6.dp, 0.dp)
                                .testTag("tagLineText"),
                            textAlign = TextAlign.Left,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }


}