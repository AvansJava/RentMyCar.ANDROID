package com.rentmycar.rentmycar.repository;

import com.rentmycar.rentmycar.network.NetworkLayer;
import com.rentmycar.rentmycar.network.response.GetCarByIdResponse;

class CarRepository {

    suspend fun getCarList(): List<GetCarByIdResponse> {
        val request = NetworkLayer.carClient.getCarList()

        if (request.failed || !request.isSuccessful) {
            return emptyList()
        }

        return request.body
    }
}
