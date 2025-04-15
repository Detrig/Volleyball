package com.example.disputer.training.presentation.training_parent

import androidx.lifecycle.ViewModel
import com.example.disputer.authentication.data.AuthUser
import com.example.disputer.authentication.domain.utils.CurrentUserLiveDataWrapper
import com.example.disputer.children.domain.usecases.GetChildrenByIdUseCase
import com.example.disputer.children.domain.usecases.GetChildrenTrainings
import com.example.disputer.core.Navigation
import com.example.disputer.parent.data.Parent
import com.example.disputer.shop.domain.repo.ShopRepository
import com.example.disputer.shop.domain.utils.ShopsLiveDataWrapper
import com.example.disputer.training.data.Training
import com.example.disputer.training.domain.repository.utils.ClickedTrainingLiveDataWrapper
import com.example.disputer.training.domain.repository.utils.YourChildrenFutureTrainingLiveLiveDataWrapper
import com.example.disputer.training.presentation.all_my_training_parent_list.MyAllTrainingParentScreen
import com.example.disputer.training.presentation.training_sign_off.TrainingSignOffScreen
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

open class TrainingParentMainViewModel(
    private val navigation: Navigation,
    private val shopRepository: ShopRepository,
    private val getChildrenByIdUseCase: GetChildrenByIdUseCase,
    private val getChildrenTrainings: GetChildrenTrainings,
    private val currentUserLiveDataWrapper: CurrentUserLiveDataWrapper,
    private val yourChildrenFutureTrainingLiveLiveDataWrapper: YourChildrenFutureTrainingLiveLiveDataWrapper,
    private val shopsLiveDataWrapper: ShopsLiveDataWrapper,
    private val clickedTrainingLiveDataWrapper: ClickedTrainingLiveDataWrapper,
    private val viewModelScope: CoroutineScope,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main,
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val currentDate = sdf.format(Date())

    init {
        viewModelScope.launch(dispatcherIO) {
            val shopsList = shopRepository.getShops()

            withContext(dispatcherMain) {
                if (shopsList.data != null)
                    shopsLiveDataWrapper.update(shopsList.data)
            }
        }
        //getYourChildrenTrainings()
        observeShops()
    }

    fun getYourChildrenFutureTrainings() {
        viewModelScope.launch(dispatcherIO) {
            val currentParent = getCurrentParent() ?: Parent()
            val allTrainings = mutableSetOf<Training>()
            currentParent.childIds.forEach { childId ->
                val children = getChildrenByIdUseCase.invoke(childId).data
                children?.let {
                    val trainings = getChildrenTrainings.invoke(it).data ?: emptyList()
                    allTrainings.addAll(trainings.filter { it.date >= currentDate })
                }
            }
            withContext(dispatcherMain) {
                yourChildrenFutureTrainingLiveLiveDataWrapper.update(allTrainings.toList())
            }
        }
    }


    fun getCurrentParent(): Parent? {
        val user = currentUserLiveDataWrapper.liveData().value
        if (user is AuthUser.ParentUser) {
            return user.parent
        }
        return null
    }

    fun yourChildrenFutureTrainingsLiveData() = yourChildrenFutureTrainingLiveLiveDataWrapper.liveData()
    fun shopsLiveData() = shopsLiveDataWrapper.liveData()

    fun trainingDetailsScreen(training: Training) {
        clickedTrainingLiveDataWrapper.update(training)
        navigation.update(TrainingSignOffScreen)
    }


    fun observeShops() {
        shopRepository.observeShopsLiveData().observeForever {
            it.data?.let {
                shopsLiveDataWrapper.update(it)
            }
        }
    }
}
