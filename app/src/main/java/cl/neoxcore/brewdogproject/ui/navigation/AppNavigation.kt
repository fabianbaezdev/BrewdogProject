package cl.neoxcore.brewdogproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.neoxcore.brewdogproject.ui.navigation.Navigation.Args.BEER_ID

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Navigation.Routes.LIST
    ) {
        composable(
            route = Navigation.Routes.LIST
        ) {
            ListScreenDestination(navController)
        }

        composable(
            route = Navigation.Routes.BEER
        ) {
            DetailScreenDestination(navController)
        }
    }
}

object Navigation {

    object Args {
        const val BEER_ID = "beer_id"
    }

    object Routes {
        const val LIST = "list"
        const val BEER = "$LIST/{$BEER_ID}"
    }

}

fun NavController.navigateToBeerDetail(id: String) {
    navigate(route = "${Navigation.Routes.LIST}/$id")
}