package cl.neoxcore.brewdogproject.presentation.detail

import androidx.lifecycle.SavedStateHandle
import cl.neoxcore.brewdogproject.domain.GetBeerByIdUseCase
import cl.neoxcore.brewdogproject.presentation.BaseViewModel
import cl.neoxcore.brewdogproject.presentation.UnsupportedReduceException
import cl.neoxcore.brewdogproject.presentation.detail.DetailMVI.Effect.Navigation.BackNavigation
import cl.neoxcore.brewdogproject.presentation.detail.DetailMVI.Intent
import cl.neoxcore.brewdogproject.presentation.detail.DetailMVI.Intent.BackClicked
import cl.neoxcore.brewdogproject.presentation.detail.DetailMVI.MainResult
import cl.neoxcore.brewdogproject.presentation.detail.DetailMVI.MainResult.Error
import cl.neoxcore.brewdogproject.presentation.detail.DetailMVI.MainResult.InProgress
import cl.neoxcore.brewdogproject.presentation.detail.DetailMVI.MainResult.Success
import cl.neoxcore.brewdogproject.presentation.detail.DetailMVI.MainUiState
import cl.neoxcore.brewdogproject.presentation.detail.DetailMVI.MainUiState.DefaultUiState
import cl.neoxcore.brewdogproject.presentation.detail.DetailMVI.MainUiState.DisplayBeerUiState
import cl.neoxcore.brewdogproject.presentation.detail.DetailMVI.MainUiState.ErrorUiState
import cl.neoxcore.brewdogproject.presentation.detail.DetailMVI.MainUiState.LoadingUiState
import cl.neoxcore.brewdogproject.ui.navigation.Navigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getBeerByIdUseCase: GetBeerByIdUseCase,
    savedStateHandle: SavedStateHandle
) :
    BaseViewModel<MainUiState, DetailMVI.Effect, MainResult, Intent>(
        initialState = DefaultUiState
    ) {

    init {
        val beerId = savedStateHandle.get<String>(Navigation.Args.BEER_ID).orEmpty()
        acceptChanges(getBeer(beerId))
    }

    private fun getBeer(beerId: String): Flow<MainResult> =
        getBeerByIdUseCase.execute(id = beerId).map { transactions ->
            Success(transactions) as MainResult
        }.onStart {
            emit(InProgress)
        }.catch { cause ->
            emit(Error(cause))
        }

    override fun mapIntents(intent: Intent): Flow<MainResult> = when (intent) {
        BackClicked -> backClicked()
    }

    override fun reduceUiState(
        previousState: MainUiState,
        result: MainResult
    ): MainUiState = when (previousState) {
        is DefaultUiState -> previousState reduceWith result
        is DisplayBeerUiState -> previousState reduceWith result
        is ErrorUiState -> previousState reduceWith result
        is LoadingUiState -> previousState reduceWith result
    }

    private infix fun LoadingUiState.reduceWith(result: MainResult): MainUiState {
        return when (result) {
            is Error -> ErrorUiState(result.error)
            is Success -> DisplayBeerUiState(result.beer)
            else -> throw UnsupportedReduceException(this, result)
        }
    }

    private infix fun DefaultUiState.reduceWith(result: MainResult): MainUiState {
        return when (result) {
            InProgress -> LoadingUiState
            else -> throw UnsupportedReduceException(this, result)
        }
    }

    private infix fun DisplayBeerUiState.reduceWith(result: MainResult): MainUiState {
        return when (result) {
            InProgress -> LoadingUiState
            else -> throw UnsupportedReduceException(this, result)
        }
    }

    private infix fun ErrorUiState.reduceWith(result: MainResult): MainUiState {
        return when (result) {
            InProgress -> LoadingUiState
            else -> throw UnsupportedReduceException(this, result)
        }
    }

    private fun backClicked(): Flow<MainResult> {
        publishEffect(BackNavigation)
        return emptyFlow()
    }
}