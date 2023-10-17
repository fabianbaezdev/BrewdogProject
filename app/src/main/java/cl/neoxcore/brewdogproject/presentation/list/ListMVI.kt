package cl.neoxcore.brewdogproject.presentation.list

import cl.neoxcore.brewdogproject.domain.model.Beer
import cl.neoxcore.brewdogproject.presentation.MviResult
import cl.neoxcore.brewdogproject.presentation.ViewIntent
import cl.neoxcore.brewdogproject.presentation.ViewSideEffect
import cl.neoxcore.brewdogproject.presentation.ViewState

class ListMVI {
    sealed class Intent : ViewIntent {
        data class BeerClicked(val id:String) : Intent()
    }


    sealed class MainUiState : ViewState {
        object DefaultUiState : MainUiState()
        object LoadingUiState : MainUiState()
        data class DisplayListUiState(val beers: List<Beer>) : MainUiState()
        data class ErrorUiState(val error: Throwable) : MainUiState()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class BeerDetail(val id:String) : Navigation()
        }
    }

    sealed class MainResult : MviResult {
        object InProgress : MainResult()
        data class Success(val beers: List<Beer>) : MainResult()
        data class Error(val error: Throwable) : MainResult()

    }
}