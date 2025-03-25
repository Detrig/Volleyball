package com.example.disputer.core

interface Navigation : LiveDataWrapper.Mutable<Screen> {

    class Base : Navigation, LiveDataWrapper.Abstract<Screen>()
}