package cl.neoxcore.brewdogproject.presentation.list

import cl.neoxcore.brewdogproject.domain.GetBeersUseCase
import cl.neoxcore.brewdogproject.presentation.BaseViewModel
import cl.neoxcore.brewdogproject.presentation.UnsupportedReduceException
import cl.neoxcore.brewdogproject.presentation.list.ListMVI.Effect.Navigation
import cl.neoxcore.brewdogproject.presentation.list.ListMVI.Intent.BeerClicked
import cl.neoxcore.brewdogproject.presentation.list.ListMVI.MainResult
import cl.neoxcore.brewdogproject.presentation.list.ListMVI.MainResult.Error
import cl.neoxcore.brewdogproject.presentation.list.ListMVI.MainResult.InProgress
import cl.neoxcore.brewdogproject.presentation.list.ListMVI.MainResult.Success
import cl.neoxcore.brewdogproject.presentation.list.ListMVI.MainUiState
import cl.neoxcore.brewdogproject.presentation.list.ListMVI.MainUiState.DefaultUiState
import cl.neoxcore.brewdogproject.presentation.list.ListMVI.MainUiState.DisplayListUiState
import cl.neoxcore.brewdogproject.presentation.list.ListMVI.MainUiState.ErrorUiState
import cl.neoxcore.brewdogproject.presentation.list.ListMVI.MainUiState.LoadingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getBeersUseCase: GetBeersUseCase
) :
    BaseViewModel<MainUiState, ListMVI.Effect, MainResult, ListMVI.Intent>(
        initialState = DefaultUiState
    ) {

    init {
        acceptChanges(getBeers())
    }

    private fun getBeers(): Flow<MainResult> =
        getBeersUseCase.execute().map { transactions ->
            Success(transactions) as MainResult
        }.onStart {
            emit(InProgress)
        }.catch { cause ->
            emit(Error(cause))
        }


    override fun mapIntents(intent: ListMVI.Intent): Flow<MainResult> = when (intent) {
        is BeerClicked -> beerClicked(intent.id)
    }

    override fun reduceUiState(
        previousState: MainUiState,
        result: MainResult
    ): MainUiState = when (previousState) {
        is DefaultUiState -> previousState reduceWith result
        is LoadingUiState -> previousState reduceWith result
        is DisplayListUiState -> previousState reduceWith result
        is ErrorUiState -> previousState reduceWith result
    }

    private infix fun LoadingUiState.reduceWith(result: MainResult): MainUiState {
        return when (result) {
            is Error -> ErrorUiState(result.error)
            is Success -> DisplayListUiState(result.beers)
            else -> throw UnsupportedReduceException(this, result)
        }
    }

    private infix fun DefaultUiState.reduceWith(result: MainResult): MainUiState {
        return when (result) {
            InProgress -> LoadingUiState
            else -> throw UnsupportedReduceException(this, result)
        }
    }

    private infix fun DisplayListUiState.reduceWith(result: MainResult): MainUiState {
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

    private fun beerClicked(id: String): Flow<MainResult> {
        publishEffect(Navigation.BeerDetail(id = id))
        return emptyFlow()
    }

}