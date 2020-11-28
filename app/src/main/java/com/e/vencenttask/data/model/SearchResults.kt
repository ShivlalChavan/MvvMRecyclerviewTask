package com.e.vencenttask.data.model

import com.google.gson.annotations.SerializedName

data class SearchResults(

    @SerializedName("success")
    var success:Boolean,

    @SerializedName("message")
    var message:String ,

    @SerializedName("data")
    var dataList: List<IndustryDetail>
) {
    data class IndustryDetail(
        @SerializedName("stock_id")
        var stockId:String,

        @SerializedName("Manufacture_id")
        var manufactureId:String,

        @SerializedName("stock_name")
        var stockName:String,

        @SerializedName("stock_qty")
        var stockQty:String,

        @SerializedName("stock_dt_added")
        var stockAdded:String,

        @SerializedName("stock_ip_added")
        var stockIpAdded:String,

        @SerializedName("stock_status")
        var stockStatus:String,

        @SerializedName("stock_price")
        var stockPrice:String,

        @SerializedName("total_price")
        var stockTotalPrice:String

    )
}
