package com.example.mvpsample.model.networking

import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {
    @GET("3lvis/3799feea005ed49942dcb56386ecec2b/raw/63249144485884d279d55f4f3907e37098f55c74/discover.json")
    fun getData(): Observable<Response>
}

