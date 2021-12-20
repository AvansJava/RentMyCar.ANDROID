package com.rentmycar.rentmycar.network.response

data class GetAvailabilityPageResponse(
    val content: List<GetAvailabilityResponse>,
    val pageable: Pageable,
    val last: Boolean,
    val totalPages: Int,
    val totalElements: Int,
    val size: Int,
    val number: Int,
    val sort: Sort,
    val first: Boolean,
    val numberOfElements: Int,
    val empty: Boolean
) {
    data class Pageable(
        val sort: Sort,
        val offset: Int,
        val pageNumber: Int,
        val pageSize: Int,
        val paged: Boolean,
        val unpaged: Boolean
    )

    data class Sort(
        val sorted: Boolean,
        val unsorted: Boolean,
        val empty: Boolean
    )
}
