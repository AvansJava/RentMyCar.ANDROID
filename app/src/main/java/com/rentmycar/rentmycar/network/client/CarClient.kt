package com.rentmycar.rentmycar.network.client

import com.rentmycar.rentmycar.network.response.GetCarResponse
import com.rentmycar.rentmycar.network.response.GetCarResourceResponse
import com.rentmycar.rentmycar.network.response.SimpleResponse
import com.rentmycar.rentmycar.network.service.CarService

class CarClient(
    private val carService: CarService
    ): BaseClient() {

    suspend fun getCarList(): SimpleResponse<List<GetCarResponse>> {
        return safeApiCall { carService.getCarsList() }
    }

    suspend fun getCarById(id: Int): SimpleResponse<GetCarResponse> {
        return safeApiCall { carService.getCarById(id) }
    }

    suspend fun getCarResources(id: Int): SimpleResponse<List<GetCarResourceResponse>> {
        return safeApiCall { carService.getCarResources(id) }
    }
}