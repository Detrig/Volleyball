package com.example.disputer.shop.domain.utils

import com.example.disputer.core.ListLiveDataWrapper
import com.example.disputer.shop.data.Shop

interface ShopsLiveDataWrapper : ListLiveDataWrapper.Mutable<Shop> {
    class Base : ShopsLiveDataWrapper, ListLiveDataWrapper.Abstract<Shop>()
}