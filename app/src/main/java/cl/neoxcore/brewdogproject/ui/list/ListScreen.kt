package cl.neoxcore.brewdogproject.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cl.neoxcore.brewdogproject.domain.model.Beer
import cl.neoxcore.brewdogproject.presentation.list.ListMVI.Effect.Navigation
import cl.neoxcore.brewdogproject.presentation.list.ListMVI.MainUiState.DefaultUiState
import cl.neoxcore.brewdogproject.presentation.list.ListMVI.MainUiState.DisplayListUiState
import cl.neoxcore.brewdogproject.presentation.list.ListMVI.MainUiState.ErrorUiState
import cl.neoxcore.brewdogproject.presentation.list.ListMVI.MainUiState.LoadingUiState
import cl.neoxcore.brewdogproject.presentation.list.ListViewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    viewModel: ListViewModel,
    onNavigationRequested: (navigationEffect: Navigation) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Brewdog Beers")
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            when (state) {
                is DisplayListUiState -> {
                    LazyColumn {
                        items((state as DisplayListUiState).beers) { beer ->
                            CardBeer(beer, onNavigationRequested)
                        }
                    }
                }

                is ErrorUiState -> {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "Error"
                    )
                }

                LoadingUiState -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }

                DefaultUiState -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardBeer(
    beer: Beer,
    onNavigationRequested: (navigationEffect: Navigation) -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth(),
        onClick = {
            onNavigationRequested(Navigation.BeerDetail(id = beer.id.toString()))
        }

    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = beer.image,
                contentDescription = "Beer image",
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.White)
                    .padding(6.dp)
            )
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = beer.name,
                    modifier = Modifier
                        .padding(6.dp),
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
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
                        .padding(6.dp, 2.dp),
                    textAlign = TextAlign.Left,
                    fontSize = 12.sp
                )
            }

        }

    }
}
