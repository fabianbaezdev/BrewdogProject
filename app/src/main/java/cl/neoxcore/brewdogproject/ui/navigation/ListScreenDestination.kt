package cl.neoxcore.brewdogproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cl.neoxcore.brewdogproject.presentation.list.ListMVI
import cl.neoxcore.brewdogproject.presentation.list.ListViewModel
import cl.neoxcore.brewdogproject.ui.list.ListScreen

@Composable
fun ListScreenDestination(navController: NavController) {
    val viewModel = hiltViewModel<ListViewModel>()
    ListScreen(
        viewModel = viewModel,
    ) { navigationEffect ->
        if (navigationEffect is ListMVI.Effect.Navigation.BeerDetail) {
            navController.navigateToBeerDetail(navigationEffect.id)
        }
    }
}