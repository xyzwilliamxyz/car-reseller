package com.carreseller

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

open class BaseInstrumentationTest {

    fun getJson(file: String): String {
        return BaseInstrumentationTest::class.java.getResource("/$file.json").readText()
    }

    val validDispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when {
                request.path.startsWith("/car-types/manufacturer?page=0&pageSize=15")
                -> MockResponse().setResponseCode(200).setBody(getJson("manufacturers_response_page1"))
                request.path.startsWith("/car-types/manufacturer?page=1&pageSize=15")
                -> MockResponse().setResponseCode(200).setBody(getJson("manufacturers_response_page2"))
                request.path.startsWith("/car-types/main-types?page=0&pageSize=15")
                -> MockResponse().setResponseCode(200).setBody(getJson("main_car_type_response_page1"))
                request.path.startsWith("/car-types/main-types?page=1&pageSize=15")
                -> MockResponse().setResponseCode(200).setBody(getJson("main_car_type_response_page2"))
                request.path.startsWith("/car-types/built-dates")
                -> MockResponse().setResponseCode(200).setBody(getJson("built_date_response"))

                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    val errorDispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return MockResponse().setResponseCode(500).setBody("Internal Server Error")
        }
    }
}
