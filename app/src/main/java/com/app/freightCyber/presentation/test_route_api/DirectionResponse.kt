package com.app.freightCyber.presentation.test_route_api
import com.google.gson.annotations.SerializedName

data class DirectionRespponse(
    @SerializedName("routes") val routes: List<Route>
)

data class Route(
    @SerializedName("overview_polyline") val overviewPolyline: OverviewPolyline
)

data class OverviewPolyline(
    @SerializedName("points") val points: String
)