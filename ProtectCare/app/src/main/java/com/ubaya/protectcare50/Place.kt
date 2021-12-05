package com.ubaya.protectcare50

data class Place(val code:String, val placeName:String){
    override fun toString(): String {
        return placeName
    }
}

