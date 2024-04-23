package cl.neoxcore.brewdogproject.ui.list

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import cl.neoxcore.brewdogproject.factory.BeerFactory.makeBeer
import cl.neoxcore.brewdogproject.presentation.list.ListMVI
import cl.neoxcore.brewdogproject.presentation.list.ListViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class ListScreenKtTest {

    @get:Rule(order = 1)
    var composeTestRule = createComposeRule()

    private val viewModel = mockk<ListViewModel>()

    @Test
    fun displayListUiState() {
        val beers = listOf(makeBeer())

        every { viewModel.uiState } returns MutableStateFlow(
            ListMVI.MainUiState.DisplayListUiState(
                beers
            )
        )

        composeTestRule.setContent {
            ListScreen(viewModel = viewModel) {}
        }
        composeTestRule.onNodeWithTag("BeersList", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("ErrorText", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("LoadingBox", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("DefaultBox", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun errorUiState() {
        every { viewModel.uiState } returns MutableStateFlow(
            ListMVI.MainUiState.ErrorUiState(
                error = Throwable(
                    ""
                )
            )
        )

        composeTestRule.setContent {
            ListScreen(viewModel = viewModel) {}
        }
        composeTestRule.onNodeWithTag("BeersList", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("ErrorText", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("LoadingBox", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("DefaultBox", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun loadingUiState() {
        every { viewModel.uiState } returns MutableStateFlow(
            ListMVI.MainUiState.LoadingUiState
        )

        composeTestRule.setContent {
            ListScreen(viewModel = viewModel) {}
        }
        composeTestRule.onNodeWithTag("BeersList", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("ErrorText", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("LoadingBox", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("DefaultBox", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun defaultUiState() {
        every { viewModel.uiState } returns MutableStateFlow(
            ListMVI.MainUiState.DefaultUiState
        )

        composeTestRule.setContent {
            ListScreen(viewModel = viewModel) {}
        }
        composeTestRule.onNodeWithTag("BeersList", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("ErrorText", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("LoadingBox", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("DefaultBox", useUnmergedTree = true).assertExists()
    }

    @Test
    fun cardBeerComponentExists() {
        val beer = makeBeer()
        composeTestRule.setContent {
            CardBeer(beer = beer) {}
        }
        composeTestRule.onNodeWithContentDescription("Beer image").assertExists()
        composeTestRule.onNodeWithTag("BeerNameText", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("ABVText", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("IBUText", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("tagLineText", useUnmergedTree = true).assertExists()

    }

    @Test
    fun cardBeerCorrectTexts() {
        val beer = makeBeer()
        composeTestRule.setContent {
            CardBeer(beer = beer) {}
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