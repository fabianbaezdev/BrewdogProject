package cl.neoxcore.brewdogproject.presentation.detail

import cl.neoxcore.brewdogproject.domain.model.Beer
import cl.neoxcore.brewdogproject.presentation.MviResult
import cl.neoxcore.brewdogproject.presentation.ViewIntent
import cl.neoxcore.brewdogproject.presentation.ViewSideEffect
import cl.neoxcore.brewdogproject.presentation.ViewState

class DetailMVI {
    sealed class Intent : ViewIntent {
        object BackClicked : Intent()
    }


    sealed class MainUiState : ViewState {
        object DefaultUiState : MainUiState()
        object LoadingUiState : MainUiState()
        data class DisplayBeerUiState(val beer: Beer) : MainUiState()
        data class ErrorUiState(val error: Throwable) : MainUiState()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object BackNavigation : Navigation()
        }
    }

    sealed class MainResult : MviResult {
        object InProgress : MainResult()
        data class Success(val beer: Beer) : MainResult()
        data class Error(val error: Throwable) : MainResult()

    }

}