package cl.neoxcore.brewdogproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cl.neoxcore.brewdogproject.presentation.detail.DetailMVI.Effect.Navigation
import cl.neoxcore.brewdogproject.presentation.detail.DetailViewModel
import cl.neoxcore.brewdogproject.ui.detail.DetailScreen

@Composable
fun DetailScreenDestination(navController: NavController) {
    val viewModel = hiltViewModel<DetailViewModel>()
    DetailScreen(
        viewModel = viewModel,
    ) { navigationEffect ->
        if (navigationEffect is Navigation.BackNavigation) {
            navController.popBackStack()
        }
    }
}