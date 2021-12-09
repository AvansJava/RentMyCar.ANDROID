package com.rentmycar.rentmycar.network.response

import java.time.LocalDateTime
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities

data class GetCarResourceResponse(
    val id: Int,
    val filePath: String,
    val createdAt: String,
    val updatedAt: String?
)
