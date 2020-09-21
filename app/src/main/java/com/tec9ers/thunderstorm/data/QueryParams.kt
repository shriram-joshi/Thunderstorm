package com.tec9ers.thunderstorm.data

class QueryParams {

    lateinit var Params: HashMap<String,String>

    fun getresponsebycity(city: String ): QueryParams {
        this.Params = hashMapOf("q" to city)
        return this
    }

    fun getresponsebylatlng(lat: Double , lng: Double ): QueryParams {
        this.Params = hashMapOf("lat" to lat.toString() , "lon" to lng.toString())
        return this
    }

    fun getresponsebyzipcode(zipcode: Long ): QueryParams {
        this.Params = hashMapOf("zip" to zipcode.toString())
        return this
    }
    fun getresponsebycityid(cityid: Long): QueryParams {
        this.Params = hashMapOf("id" to cityid.toString())
        return this
    }

}