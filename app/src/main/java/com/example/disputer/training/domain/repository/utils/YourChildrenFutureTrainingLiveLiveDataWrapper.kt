package com.example.disputer.training.domain.repository.utils

import com.example.disputer.core.ListLiveDataWrapper
import com.example.disputer.training.data.Training

interface YourChildrenFutureTrainingLiveLiveDataWrapper : ListLiveDataWrapper.All<Training> {
    class Base: YourChildrenFutureTrainingLiveLiveDataWrapper, ListLiveDataWrapper.Abstract<Training>()
}