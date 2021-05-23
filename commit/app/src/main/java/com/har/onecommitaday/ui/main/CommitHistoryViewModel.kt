package com.har.onecommitaday.ui.main

import android.view.View
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.har.onecommitaday.api.http.HttpConnectionHelper
import com.har.onecommitaday.api.model.CommitHistory
import com.har.onecommitaday.api.model.CommitSummary
import com.har.onecommitaday.extensions.getTreeEmoji
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.time.LocalDate

class CommitHistoryViewModel : ViewModel() {

    val contributions = MutableLiveData<List<CommitHistory>>(mutableListOf())

    private val _state = MutableStateFlow<ViewState>(ViewState.INIT)

    private val _summaryData = MutableLiveData(CommitSummary())

    private val _isExpanded = MutableLiveData(false)
    private val _requestCollapse = MutableLiveData(false)

    private var _appearanceType = MutableLiveData("")

    private var _selectedCommitHistory = MutableLiveData<CommitHistory>(null)

    val githubNickName = MutableLiveData("")

    fun setGithubNickname(nickname: String) {
        githubNickName.postValue(nickname)
    }

    val state: LiveData<ViewState>
        get() = _state.asLiveData()

    val summaryData: LiveData<CommitSummary>
        get() = _summaryData

    val isExpanded: LiveData<Boolean>
        get() = _isExpanded

    val requestCollapse: LiveData<Boolean>
        get() = _requestCollapse


    val appearanceType: LiveData<String>
        get() = _appearanceType

    fun setAppearacneType(type: String) {
        _appearanceType.postValue(type)
    }

    val selectedCommitHistory: LiveData<CommitHistory>
        get() = _selectedCommitHistory

    fun setSelectedCommitHistory(selected: CommitHistory) {
        _selectedCommitHistory.postValue(selected)
    }

    fun setConsecutiveDays(cDays: Int) {
        val summaryData = _summaryData.value ?: CommitSummary()
        summaryData.emoji = cDays.getTreeEmoji()
        summaryData.consecutiveDays = cDays

        _summaryData.postValue(summaryData)
    }

    fun setState(state: ViewState) {
        _state.value = state
    }

    fun setExpanded() {
        _isExpanded.postValue(true)
    }

    fun setCollapsed() {
        _isExpanded.postValue(false)
    }

    fun setRequestCollapse() {
        _requestCollapse.postValue(true)
    }

    fun clearGithubNickName() {
        githubNickName.postValue("")
    }

    fun setContributions(commitList: List<CommitHistory>) {
        contributions.postValue(commitList)
    }

    fun onBtnRefreshClicked(view: View) {
        setRequestCollapse()
        setContributions(listOf())
        getContributions()
        setState(ViewState.LOADING)
    }

    fun onBtnSettingsClicked(view: View) {
        setRequestCollapse()
        Navigation.findNavController(view).navigate(CommitHistoryFragmentDirections.actionSplashFragmentToLoginFragment())
    }

    @JvmOverloads
    fun getContributions(githubNickname: String? = null) {
        val nickname = githubNickname ?: githubNickName.value ?: return
        val githubUrl = "https://github.com/users/$nickname/contributions"
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            val contributionString = HttpConnectionHelper.requestHttpGet(githubUrl)
            if (contributionString.isNotEmpty()) {
                val doc = Jsoup.parse(contributionString)
                val rects = doc.select("rect")

                val originalCommitList = rects.filter {
                    val date = it.attr("data-date")
                    val count = it.attr("data-count")
                    date.isNotEmpty() && count.isNotEmpty()
                }.map { element ->
                    val date = element.attr("data-date")
                    val count = element.attr("data-count")
                    val dateDate = LocalDate.parse(date)
                    CommitHistory(dateDate, count.toInt())
                }.reversed()
                val consecutiveDays = originalCommitList.indexOfFirst { it.count == 0 }
                val commitList = when (appearanceType.value) {
                    "jandi" -> {
                        originalCommitList.take(14)
                    }
                    "flower" -> {
                        val additionalDays = originalCommitList[0].date.dayOfWeek.value
                        originalCommitList.take(70 + additionalDays).reversed()

                    }
                    else -> mutableListOf()
                }

                setSelectedCommitHistory(commitList.last())
                setConsecutiveDays(consecutiveDays)
                setContributions(commitList)
            }
        }
    }

    sealed class ViewState {
        object INIT : ViewState()
        object LOADING : ViewState()
        object RESULT : ViewState()
        data class Error(val message: String) : ViewState()
    }
}