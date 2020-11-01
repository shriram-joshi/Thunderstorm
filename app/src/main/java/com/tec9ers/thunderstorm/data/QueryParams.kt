package com.tec9ers.thunderstorm.data

class QueryParams {

    lateinit var params: HashMap<String, String>

    fun getResponseByCity(city: String): QueryParams {
        this.params = hashMapOf("q" to city)
        return this
    }

    fun getResponseByCoordinates(lat: Float, lon: Float): QueryParams {
        this.params = hashMapOf("lat" to lat.toString(), "lon" to lon.toString())
        return this
    }

    fun excludeField(field: String): QueryParams {
        this.params["exclude"] = field
        return this
    }

    fun getResponseByZipCode(zipCode: Long): QueryParams {
        this.params = hashMapOf("zip" to zipCode.toString())
        return this
    }

    fun getResponseById(cityId: Long): QueryParams {
        this.params = hashMapOf("id" to cityId.toString())
        return this
    }
}
