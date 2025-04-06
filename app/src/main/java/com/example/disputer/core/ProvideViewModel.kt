package com.example.disputer.core

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
import com.example.disputer.authentication.domain.usecase.RegistrationUseCase
import com.example.disputer.authentication.domain.utils.CurrentUserLiveDataWrapper
import com.example.disputer.authentication.presentation.forgotpassword.ForgotPasswordUiStateLiveDataWrapper
import com.example.disputer.authentication.presentation.login.LoginUiStateLiveDataWrapper
import com.example.disputer.authentication.presentation.register.RegisterUiStateLiveDataWrapper
import com.example.disputer.authentication.presentation.forgotpassword.ForgotPasswordViewModel
import com.example.disputer.authentication.presentation.main.MainViewModel
import com.example.disputer.authentication.presentation.register.RegisterViewModel
import com.example.disputer.coach.data.FirebaseCoachDataSource
import com.example.disputer.coach.domain.CoachDataSource
import com.example.disputer.parents.data.FirebaseParentDataSource
import com.example.disputer.training.domain.repository.TrainingsRepository
import com.example.disputer.training.presentation.training_parent.TrainingParentMainViewModel
import com.example.disputer.training.presentation.training_parent.TrainingsLiveDataWrapper
import com.example.disputer.schedule.ScheduleViewModel
import com.example.disputer.training.data.FirebaseTrainingDataSource
import com.example.disputer.training.data.TrainingRepositoryImpl
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.checkerframework.checker.units.qual.Current

interface ProvideViewModel {

    fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T

    class Base(
        private val clear: ClearViewModel
    ) : ProvideViewModel {
        private val fireBaseAuth = Firebase.auth
        private val fireBaseFirestore = Firebase.firestore
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

        //Register
        private val registerUiStateLiveDataWrapper = RegisterUiStateLiveDataWrapper.Base()
        private val registerUseCase = RegistrationUseCase(authRepository)

        //Reset password
        private val forgotPasswordUiStateLiveDataWrapper = ForgotPasswordUiStateLiveDataWrapper.Base()
        private val forgotPasswordUseCase = ForgotPasswordUseCase(passwordRepository)

        //Coach
        private val coachDataSource = FirebaseCoachDataSource(fireBaseFirestore)

        //Parent
        private val parentDataSource = FirebaseParentDataSource(fireBaseFirestore)

        //Get role of current user
        private val currentUserDataSource = FirebaseCurrentDataSource(fireBaseAuth, coachDataSource, parentDataSource)
        private val currentUserRepository = CurrentUserRepositoryImpl(currentUserDataSource)
        private val currentUserLiveDataWrapper = CurrentUserLiveDataWrapper.Base()
        private val getCurrentUserRoleUseCase = GetCurrentUserRoleUseCase(currentUserRepository)

        //Training
        private val trainingDataSource = FirebaseTrainingDataSource(fireBaseFirestore)
        private val trainingsRepository = TrainingRepositoryImpl(trainingDataSource)
        private val trainingsLiveDataWrapper = TrainingsLiveDataWrapper.Base()

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

                MainViewModel::class.java -> MainViewModel(navigation)

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
                    trainingsRepository,
                    trainingsLiveDataWrapper,
                    viewModelScope
                )

                ScheduleViewModel::class.java -> ScheduleViewModel(
                    trainingsRepository,
                    viewModelScope
                )
                else -> throw IllegalStateException("unknown viewModelClass $viewModelClass")
            } as T

        }
    }
}