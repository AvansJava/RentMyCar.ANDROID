package com.rentmycar.rentmycar.domain.model

data class LocalException(
    val title: String = "",
    val description: String = ""
) : Exception()
