package ercanduman.listanddetaildemo.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    @SerializedName("categoryId")
    val categoryId: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("salePrice")
    val salePrice: SalePrice,
    @SerializedName("url")
    val url: String
) : Parcelable