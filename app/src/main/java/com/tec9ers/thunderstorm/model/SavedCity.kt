package com.tec9ers.thunderstorm.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class SavedCity(
    @PrimaryKey
    var cityName: String = "",
    var lat: Float = -200f,
    var lon: Float = -200f
) : RealmObject()
