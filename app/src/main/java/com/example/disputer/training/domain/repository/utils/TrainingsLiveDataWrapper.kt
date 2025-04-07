package com.example.disputer.training.domain.repository.utils

import com.example.disputer.core.ListLiveDataWrapper
import com.example.disputer.training.data.Training

interface TrainingsLiveDataWrapper : ListLiveDataWrapper.Mutable<Training> {

    class Base : TrainingsLiveDataWrapper, ListLiveDataWrapper.Abstract<Training>()
}