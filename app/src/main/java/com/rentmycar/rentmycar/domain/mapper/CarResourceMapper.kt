package com.rentmycar.rentmycar.domain.mapper

import com.rentmycar.rentmycar.domain.model.CarResource
import com.rentmycar.rentmycar.network.response.GetCarResourceResponse

object CarResourceMapper {
    fun buildFrom(carResource: GetCarResourceResponse): CarResource {
        return CarResource(
            id = carResource.id,
            filePath = carResource.filePath
        )
    }
}