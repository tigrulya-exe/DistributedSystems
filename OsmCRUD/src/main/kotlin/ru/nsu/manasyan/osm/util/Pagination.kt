package ru.nsu.manasyan.osm.util

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

class PaginationRequestParams {
    enum class Order {
        ASCENDING,
        DESCENDING
    }

    var page: Int? = null
    var pageSize: Int? = null
    var order: Order? = null
    var orderBy: String? = null
}

fun getPageableOrDefault(
    parameters: PaginationRequestParams?,
    defaultPageSize: Int = 20
): Pageable {
    val sort = getSort(parameters)
    val pageable = getPageable(parameters, sort)
    return if (pageable.isPaged) pageable else PageRequest.of(0, defaultPageSize, sort)
}

fun getPageable(parameters: PaginationRequestParams?, sort: Sort): Pageable {
    return parameters?.page?.let { page ->
        parameters.pageSize?.let { from ->
            PageRequest.of(page, from, sort)
        }
    } ?: Pageable.unpaged()
}

fun getSort(parameters: PaginationRequestParams?): Sort {
    return parameters?.orderBy?.let {
        when (parameters.order) {
            PaginationRequestParams.Order.DESCENDING -> Sort.by(it).descending()
            else -> Sort.by(it).ascending()
        }
    } ?: Sort.unsorted()
}
