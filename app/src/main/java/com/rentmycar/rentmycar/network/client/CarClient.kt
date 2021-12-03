package com.rentmycar.rentmycar.network.client

import com.rentmycar.rentmycar.network.response.GetCarByIdResponse
import com.rentmycar.rentmycar.network.response.SimpleResponse
import com.rentmycar.rentmycar.network.service.CarService

class CarClient(
    private val carService: CarService
    ): BaseClient() {

    suspend fun getCarList(): SimpleResponse<List<GetCarByIdResponse>> {
        return safeApiCall { carService.getCarsList() }
    }
}