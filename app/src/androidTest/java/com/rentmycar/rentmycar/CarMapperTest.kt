package com.rentmycar.rentmycar

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rentmycar.rentmycar.domain.mapper.CarMapper
import com.rentmycar.rentmycar.domain.model.Car
import com.rentmycar.rentmycar.network.response.GetCarResponse
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class CarMapperTest {

    @Test
    @Throws(Exception::class)
    fun getCarWithoutLocationReturnMappedCarObject() {
        val testCar = createCarResponse()
        val car = createCar()
        val mappedCar = CarMapper.buildFrom(testCar, null, emptyList())

        assertEquals(mappedCar, car)
    }

    private fun createCarResponse(): GetCarResponse {
        return GetCarResponse(
            1,
            "Volkswagen",
            "Polo",
            "TDI 1.5",
            "ABC-123",
            3.6,
            21500,
            "ICE",
            1,
            null,
            "2022-01-03T09:25:24.863252",
            null
        )
    }

    private fun createCar(): Car {
        return Car(
            1,
            "Volkswagen",
            "Polo",
            "TDI 1.5",
            "ABC-123",
            3.6,
            21500,
            "ICE",
            emptyList(),
            null,
            null,
            "2022-01-03T09:25:24.863252",
            null,
            1
        )
    }
}