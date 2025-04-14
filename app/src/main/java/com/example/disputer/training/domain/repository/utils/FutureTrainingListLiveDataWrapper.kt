package com.example.disputer.training.domain.repository.utils

import com.example.disputer.core.ListLiveDataWrapper
import com.example.disputer.training.data.Training

interface FutureTrainingListLiveDataWrapper : ListLiveDataWrapper.All<Training> {
    class Base : FutureTrainingListLiveDataWrapper, ListLiveDataWrapper.Abstract<Training>()
}