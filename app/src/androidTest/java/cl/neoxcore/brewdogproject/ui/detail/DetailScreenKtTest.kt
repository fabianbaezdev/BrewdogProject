package cl.neoxcore.brewdogproject.ui.detail

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import cl.neoxcore.brewdogproject.factory.BeerFactory
import cl.neoxcore.brewdogproject.presentation.detail.DetailMVI
import cl.neoxcore.brewdogproject.presentation.detail.DetailViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class DetailScreenKtTest {
    @get:Rule(order = 1)
    var composeTestRule = createComposeRule()

    private val viewModel = mockk<DetailViewModel>()

    @Test
    fun errorUiState() {
        every { viewModel.uiState } returns MutableStateFlow(
            DetailMVI.MainUiState.ErrorUiState(
                error = Throwable(
                    ""
                )
            )
        )

        composeTestRule.setContent {
            DetailScreen(viewModel = viewModel) {}
        }
        composeTestRule.onNodeWithTag("BeerDetail", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("ErrorText", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("LoadingText", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("DefaultBox", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun loadingUiState() {
        every { viewModel.uiState } returns MutableStateFlow(
            DetailMVI.MainUiState.LoadingUiState
        )

        composeTestRule.setContent {
            DetailScreen(viewModel = viewModel) {}
        }
        composeTestRule.onNodeWithTag("BeerDetail", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("ErrorText", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("LoadingText", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("DefaultBox", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun defaultUiState() {
        every { viewModel.uiState } returns MutableStateFlow(
            DetailMVI.MainUiState.DefaultUiState
        )

        composeTestRule.setContent {
            DetailScreen(viewModel = viewModel) {}
        }
        composeTestRule.onNodeWithTag("BeerDetail", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("ErrorText", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("LoadingText", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("DefaultBox", useUnmergedTree = true).assertExists()
    }

    @Test
    fun displayBeerUiState() {
        val beer = BeerFactory.makeBeer()
        every { viewModel.uiState } returns MutableStateFlow(
            DetailMVI.MainUiState.DisplayBeerUiState(beer)
        )

        composeTestRule.setContent {
            DetailScreen(viewModel = viewModel) {}
        }
        composeTestRule.onNodeWithTag("BeerDetail", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("ErrorText", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("LoadingText", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("DefaultBox", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun beerDetailComponentExists() {
        val beer = BeerFactory.makeBeer()
        every { viewModel.uiState } returns MutableStateFlow(
            DetailMVI.MainUiState.DisplayBeerUiState(beer)
        )

        composeTestRule.setContent {
            DetailScreen(viewModel = viewModel) {}
        }
        composeTestRule.onNodeWithContentDescription("Beer Image").assertExists()
        composeTestRule.onNodeWithTag("BeerNameText", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("ABVText", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("IBUText", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("tagLineText", useUnmergedTree = true).assertExists()

    }

    @Test
    fun beerDetailCorrectTexts() {
        val beer = BeerFactory.makeBeer()
        every { viewModel.uiState } returns MutableStateFlow(
            DetailMVI.MainUiState.DisplayBeerUiState(beer)
        )

        composeTestRule.setContent {
            DetailScreen(viewModel = viewModel) {}
        }

        composeTestRule.onNodeWithTag("BeerNameText", useUnmergedTree = true)
            .assertTextEquals(beer.name)
        composeTestRule.onNodeWithTag("ABVText", useUnmergedTree = true)
            .assertTextEquals("ABV: ${beer.abv}")
        composeTestRule.onNodeWithTag("IBUText", useUnmergedTree = true)
            .assertTextEquals("IBU: ${beer.ibu}")
        composeTestRule.onNodeWithTag("tagLineText", useUnmergedTree = true)
            .assertTextEquals(beer.tagLine)
    }

}