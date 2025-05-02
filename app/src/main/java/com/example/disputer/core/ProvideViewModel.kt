package com.example.disputer.core

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.disputer.authentication.presentation.login.LoginViewModel
import com.example.disputer.authentication.data.AuthRepository
import com.example.disputer.authentication.data.CurrentUserRepositoryImpl
import com.example.disputer.authentication.data.FirebaseCurrentDataSource
import com.example.disputer.authentication.data.PasswordRepository
import com.example.disputer.authentication.data.UserRepository
import com.example.disputer.authentication.domain.usecase.ForgotPasswordUseCase
import com.example.disputer.authentication.domain.usecase.GetCurrentUserRoleUseCase
import com.example.disputer.authentication.domain.usecase.IsLoggedInUseCase
import com.example.disputer.authentication.domain.usecase.LoginUseCase
import com.example.disputer.authentication.domain.usecase.LogoutUseCase
import com.example.disputer.authentication.domain.usecase.RegistrationUseCase
import com.example.disputer.authentication.domain.utils.CurrentUserLiveDataWrapper
import com.example.disputer.authentication.presentation.forgotpassword.ForgotPasswordUiStateLiveDataWrapper
import com.example.disputer.authentication.presentation.login.LoginUiStateLiveDataWrapper
import com.example.disputer.authentication.presentation.register.RegisterUiStateLiveDataWrapper
import com.example.disputer.authentication.presentation.forgotpassword.ForgotPasswordViewModel
import com.example.disputer.authentication.presentation.main.MainViewModel
import com.example.disputer.authentication.presentation.register.RegisterViewModel
import com.example.disputer.children.data.ChildrenRepositoryImpl
import com.example.disputer.children.data.FirebaseChildrenDataSource
import com.example.disputer.children.domain.usecases.AddChildrenUseCase
import com.example.disputer.children.domain.usecases.DeleteChildrenUseCase
import com.example.disputer.children.domain.usecases.GetChildrenByIdUseCase
import com.example.disputer.children.domain.usecases.GetChildrenTrainings
import com.example.disputer.children.domain.utils.AddChildrenUiStateLiveDataWrapper
import com.example.disputer.children.domain.utils.ClickedChildrenLiveDataWrapper
import com.example.disputer.children.domain.utils.CurrentParentChildrenListLiveDataWrapper
import com.example.disputer.children.presentation.add.AddChildrenViewModel
import com.example.disputer.children.presentation.list.ChildrenViewModel
import com.example.disputer.coach.data.CoachRepositoryImpl
import com.example.disputer.coach.data.FirebaseCoachDataSource
import com.example.disputer.coach.domain.usecase.GetCoachListUseCase
import com.example.disputer.coach.domain.usecase.UpdateCoachUseCase
import com.example.disputer.coach.domain.utils.ClickedCoachLiveDataWrapper
import com.example.disputer.coach.domain.utils.CoachListLiveDataWrapper
import com.example.disputer.coach.presentation.edit_profile.EditCoachProfileViewModel
import com.example.disputer.coach.presentation.list.CoachListViewModel
import com.example.disputer.coach.presentation.profile.CoachInfoViewModel
import com.example.disputer.info.InfoViewModel
import com.example.disputer.notification.data.FirebaseNotificationRepository
import com.example.disputer.parent.data.FirebaseParentDataSource
import com.example.disputer.parent.data.ParentRepositoryImpl
import com.example.disputer.parent.domain.usecase.DeleteChildrenFromParentUseCase
import com.example.disputer.parent.domain.usecase.GetParentChildrensUseCase
import com.example.disputer.parent.domain.usecase.UpdateParentUseCase
import com.example.disputer.parent.domain.utils.ParentChildsListLiveDataWrapper
import com.example.disputer.parent.presentation.edit_profile.EditParentProfileViewModel
import com.example.disputer.schedule.domain.ClickedTrainingToSignUpLiveDataWrapper
import com.example.disputer.training.presentation.training_parent.TrainingParentMainViewModel
import com.example.disputer.training.domain.repository.utils.TrainingsLiveDataWrapper
import com.example.disputer.schedule.presentation.ScheduleViewModel
import com.example.disputer.shop.data.FirebaseShopDataSource
import com.example.disputer.shop.data.ShopRepositoryImpl
import com.example.disputer.shop.domain.usecase.AddShopUseCase
import com.example.disputer.shop.domain.usecase.DeleteShopUseCase
import com.example.disputer.shop.domain.usecase.GetShopsUseCase
import com.example.disputer.shop.domain.utils.AddShopUiStateLiveDataWrapper
import com.example.disputer.shop.presentation.ShopViewModel
import com.example.disputer.training.data.FirebaseTrainingDataSource
import com.example.disputer.training.data.TrainingRepositoryImpl
import com.example.disputer.training.domain.repository.utils.AddTrainingUiStateLiveDataWrapper
import com.example.disputer.shop.domain.utils.ShopsLiveDataWrapper
import com.example.disputer.training.domain.repository.utils.ClickedTrainingLiveDataWrapper
import com.example.disputer.training.domain.repository.utils.FutureTrainingListLiveDataWrapper
import com.example.disputer.training.domain.repository.utils.SignedUpForTrainingChildrensByParentLiveDataWrapper
import com.example.disputer.training.domain.repository.utils.YourChildrenFutureTrainingLiveLiveDataWrapper
import com.example.disputer.training.domain.repository.utils.YourChildrenTrainingLiveLiveDataWrapper
import com.example.disputer.training.presentation.all_my_training_parent_list.MyAllTrainingParentViewModel
import com.example.disputer.training.presentation.training_coach.TrainingCoachViewModel
import com.example.disputer.training.presentation.training_sign_up.TrainingSignUpViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

interface ProvideViewModel {

    fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T

    class Base(
        private val clear: ClearViewModel
    ) : ProvideViewModel {
        private val fireBaseAuth = Firebase.auth
        private val fireBaseFirestore = Firebase.firestore
        private val firebaseDatabase = Firebase.database
        private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        private val navigation = Navigation.Base()

        //Auth
        private val authRepository = AuthRepository.Base(fireBaseAuth, fireBaseFirestore)
        private val passwordRepository = PasswordRepository.Base(fireBaseAuth)
        private val userRepository = UserRepository.Base(fireBaseAuth, fireBaseFirestore)

        //Login
        private val loginUiStateLiveDataWrapper = LoginUiStateLiveDataWrapper.Base()
        private val loginUseCase = LoginUseCase(authRepository)
        private val isLoggedInUseCase = IsLoggedInUseCase(userRepository)

        //Logout
        private val logoutUseCase = LogoutUseCase(authRepository)

        //Register
        private val registerUiStateLiveDataWrapper = RegisterUiStateLiveDataWrapper.Base()
        private val registerUseCase = RegistrationUseCase(authRepository)

        //Reset password
        private val forgotPasswordUiStateLiveDataWrapper =
            ForgotPasswordUiStateLiveDataWrapper.Base()
        private val forgotPasswordUseCase = ForgotPasswordUseCase(passwordRepository)

        //Coach
        private val coachDataSource = FirebaseCoachDataSource(fireBaseFirestore)
        private val coachRepository = CoachRepositoryImpl(coachDataSource)
        private val coachListLiveDataWrapper = CoachListLiveDataWrapper.Base()
        private val clickedCoachLiveDataWrapper = ClickedCoachLiveDataWrapper.Base()
        private val updateCoachUseCase = UpdateCoachUseCase(coachRepository)
        private val getCoachListUseCase = GetCoachListUseCase(coachRepository)

        //Parent
        private val parentDataSource = FirebaseParentDataSource(fireBaseFirestore)
        private val parentRepository = ParentRepositoryImpl(parentDataSource)
        private val updateParentUseCase = UpdateParentUseCase(parentRepository)
        private val getParentChildrensUseCase = GetParentChildrensUseCase(parentRepository)
        private val deleteChildrenFromParentUseCase =
            DeleteChildrenFromParentUseCase(parentRepository)
        private val parentChildsListLiveDataWrapper = ParentChildsListLiveDataWrapper.Base()

        //Get role of current user
        private val currentUserDataSource =
            FirebaseCurrentDataSource(fireBaseAuth, coachDataSource, parentDataSource)
        private val currentUserRepository = CurrentUserRepositoryImpl(currentUserDataSource)
        private val currentUserLiveDataWrapper = CurrentUserLiveDataWrapper.Base()
        private val getCurrentUserRoleUseCase = GetCurrentUserRoleUseCase(currentUserRepository)

        //Training
        private val trainingDataSource = FirebaseTrainingDataSource(fireBaseFirestore)
        private val trainingsRepository = TrainingRepositoryImpl(trainingDataSource)
        private val trainingsLiveDataWrapper = TrainingsLiveDataWrapper.Base()
        private val clickedTrainingLiveDataWrapper = ClickedTrainingLiveDataWrapper.Base()
        private val futureTrainingListLiveDataWrapper = FutureTrainingListLiveDataWrapper.Base()
        private val addTrainingUiStateLiveDataWrapper = AddTrainingUiStateLiveDataWrapper.Base()
        private val yourChildrenTrainingsLiveDataWrapper =
            YourChildrenTrainingLiveLiveDataWrapper.Base()
        private val yourChildrenFutureTrainingsLiveDataWrapper =
            YourChildrenFutureTrainingLiveLiveDataWrapper.Base()
        private val clickedTrainingToSignUpLiveDataWrapper =
            ClickedTrainingToSignUpLiveDataWrapper.Base()

        //Shop
        private val shopDataSource = FirebaseShopDataSource(fireBaseFirestore)
        private val shopRepository = ShopRepositoryImpl(shopDataSource)

        private val addShopUseCase = AddShopUseCase(shopRepository)
        private val deleteShopUseCase = DeleteShopUseCase(shopRepository)
        private val getShopsUseCase = GetShopsUseCase(shopRepository)

        private val addShopUiStateLiveDataWrapper = AddShopUiStateLiveDataWrapper.Base()
        private val shopsLiveDataWrapper = ShopsLiveDataWrapper.Base()

        //Children
        private val childrenDataSource = FirebaseChildrenDataSource(fireBaseFirestore)
        private val childrenRepository = ChildrenRepositoryImpl(childrenDataSource)
        private val getChildrenByIdUseCase = GetChildrenByIdUseCase(childrenRepository)
        private val addChildrenUseCase = AddChildrenUseCase(childrenRepository)
        private val deleteChildrenUseCase = DeleteChildrenUseCase(childrenRepository)
        private val getChildrenTrainings = GetChildrenTrainings(childrenRepository)
        private val clickedChildrenLiveDataWrapper = ClickedChildrenLiveDataWrapper.Base()
        private val currentParentChildrenListLiveDataWrapper =
            CurrentParentChildrenListLiveDataWrapper.Base()
        private val addChildrenUiStateLiveDataWrapper = AddChildrenUiStateLiveDataWrapper.Base()
        private val signedUpForTrainingChildrensByParentLiveDataWrapper =
            SignedUpForTrainingChildrensByParentLiveDataWrapper.Base()

        //Notification
        private val notificationRepository = FirebaseNotificationRepository(firebaseDatabase)

        override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
            return when (viewModelClass) {

                LoginViewModel::class.java -> LoginViewModel(
                    navigation,
                    loginUiStateLiveDataWrapper,
                    currentUserLiveDataWrapper,
                    loginUseCase,
                    getCurrentUserRoleUseCase,
                    viewModelScope
                )

                MainViewModel::class.java -> MainViewModel(
                    navigation,
                    currentUserLiveDataWrapper,
                    getCurrentUserRoleUseCase,
                    viewModelScope
                )

                RegisterViewModel::class.java -> RegisterViewModel(
                    navigation,
                    registerUiStateLiveDataWrapper,
                    registerUseCase,
                    viewModelScope
                )

                ForgotPasswordViewModel::class.java -> ForgotPasswordViewModel(
                    navigation,
                    clear,
                    forgotPasswordUiStateLiveDataWrapper,
                    forgotPasswordUseCase,
                    viewModelScope
                )

                TrainingParentMainViewModel::class.java -> TrainingParentMainViewModel(
                    navigation,
                    shopRepository,
                    getChildrenByIdUseCase,
                    getChildrenTrainings,
                    currentUserLiveDataWrapper,
                    yourChildrenFutureTrainingsLiveDataWrapper,
                    shopsLiveDataWrapper,
                    clickedTrainingLiveDataWrapper,
                    viewModelScope
                )

                ScheduleViewModel::class.java -> ScheduleViewModel(
                    navigation,
                    trainingsRepository,
                    currentUserLiveDataWrapper,
                    futureTrainingListLiveDataWrapper,
                    clickedTrainingLiveDataWrapper,
                    //clickedTrainingToSignUpLiveDataWrapper,
                    viewModelScope
                )

                TrainingCoachViewModel::class.java -> TrainingCoachViewModel(
                    navigation,
                    trainingsRepository,
                    shopRepository,
                    notificationRepository,
                    trainingsLiveDataWrapper,
                    shopsLiveDataWrapper,
                    currentUserLiveDataWrapper,
                    addTrainingUiStateLiveDataWrapper,
                    clickedTrainingLiveDataWrapper,
                    signedUpForTrainingChildrensByParentLiveDataWrapper,
                    viewModelScope
                )

                ShopViewModel::class.java -> ShopViewModel(
                    addShopUseCase,
                    deleteShopUseCase,
                    navigation,
                    addShopUiStateLiveDataWrapper,
                    // imageProcessor,
                    viewModelScope
                )

                TrainingSignUpViewModel::class.java -> TrainingSignUpViewModel(
                    navigation,
                    trainingsRepository,
                    getParentChildrensUseCase,
                    currentUserLiveDataWrapper,
                    currentParentChildrenListLiveDataWrapper,
                    clickedTrainingLiveDataWrapper,
                    //clickedTrainingToSignUpLiveDataWrapper,
                    signedUpForTrainingChildrensByParentLiveDataWrapper,
                    viewModelScope
                )

                InfoViewModel::class.java -> InfoViewModel(
                    navigation,
                    logoutUseCase,
                    currentUserLiveDataWrapper,
                    viewModelScope
                )

                EditCoachProfileViewModel::class.java -> EditCoachProfileViewModel(
                    navigation,
                    currentUserLiveDataWrapper,
                    updateCoachUseCase,
                    viewModelScope
                )

                CoachListViewModel::class.java -> CoachListViewModel(
                    navigation,
                    getCoachListUseCase,
                    clickedCoachLiveDataWrapper,
                    coachListLiveDataWrapper,
                    viewModelScope
                )

                CoachInfoViewModel::class.java -> CoachInfoViewModel(
                    navigation,
                    clickedCoachLiveDataWrapper
                )

                ChildrenViewModel::class.java -> ChildrenViewModel(
                    navigation,
                    clickedChildrenLiveDataWrapper,
                    getParentChildrensUseCase,
                    currentUserLiveDataWrapper,
                    parentChildsListLiveDataWrapper,
                    viewModelScope
                )

                AddChildrenViewModel::class.java -> AddChildrenViewModel(
                    navigation,
                    addChildrenUseCase,
                    deleteChildrenUseCase,
                    deleteChildrenFromParentUseCase,
                    currentUserLiveDataWrapper,
                    parentChildsListLiveDataWrapper,
                    clickedChildrenLiveDataWrapper,
                    addChildrenUiStateLiveDataWrapper,
                    viewModelScope
                )

                EditParentProfileViewModel::class.java -> EditParentProfileViewModel(
                    navigation,
                    updateParentUseCase,
                    getChildrenByIdUseCase,
                    getParentChildrensUseCase,
                    currentUserLiveDataWrapper,
                    clickedChildrenLiveDataWrapper,
                    parentChildsListLiveDataWrapper,
                    viewModelScope
                )

                MyAllTrainingParentViewModel::class.java -> MyAllTrainingParentViewModel(
                    getChildrenByIdUseCase,
                    getChildrenTrainings,
                    currentUserLiveDataWrapper,
                    yourChildrenTrainingsLiveDataWrapper,
                    viewModelScope
                )

                else -> throw IllegalStateException("unknown viewModelClass $viewModelClass")
            } as T

        }
    }
}