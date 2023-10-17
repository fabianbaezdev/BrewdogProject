package cl.neoxcore.brewdogproject.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch

abstract class BaseViewModel<UiState : ViewState, Effect : ViewSideEffect, TResult : MviResult, Intent : ViewIntent>(
    initialState: UiState,
) : ViewModel() {
    private val intentsFlowListenerStarted = CompletableDeferred<Unit>()
    private val resultsFlowListenerStarted = CompletableDeferred<Unit>()

    private val intentsFlow = MutableSharedFlow<Intent>()
    private val resultsFlow = MutableSharedFlow<TResult>()

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<UiState> = _uiState

    private val effectsChannel = Channel<Effect>(Channel.BUFFERED)
    val effects = effectsChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            merge(
                userIntents(),
                nonUserChanges(),
            )
                .scan(uiState.value, ::reduceUiState)
                .catch { Log.e("BaseViewModel", it.stackTraceToString()) }
                .collect {
                    _uiState.value = it
                }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun userIntents(): Flow<TResult> =
        intentsFlow
            .onSubscription { intentsFlowListenerStarted.complete(Unit) }
            .flatMapMerge { mapIntents(it) }


    private fun nonUserChanges(): Flow<TResult> =
        resultsFlow
            .onSubscription { resultsFlowListenerStarted.complete(Unit) }

    fun acceptIntent(intent: Intent) {
        viewModelScope.launch {
            intentsFlowListenerStarted.await()
            intentsFlow.emit(intent)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    protected fun acceptChanges(vararg nonUserChangesFlows: Flow<TResult>) {
        viewModelScope.launch {
            resultsFlowListenerStarted.await()
            resultsFlow.emitAll(
                // to flatten Flow with queue behaviour like in userIntents() Flow but without ::mapIntents
                nonUserChangesFlows.asFlow().flatMapMerge { it },
            )
        }
    }

    protected fun publishEffect(effect: Effect) {
        viewModelScope.launch {
            effectsChannel.send(effect)
        }
    }

    protected abstract fun mapIntents(intent: Intent): Flow<TResult>

    protected abstract fun reduceUiState(
        previousState: UiState,
        result: TResult,
    ): UiState
}

class UnsupportedReduceException(state: ViewState, result: MviResult) :
    RuntimeException("Cannot reduce state: $state with result: $result")

interface ViewIntent
interface ViewState
interface ViewSideEffect
interface MviResult