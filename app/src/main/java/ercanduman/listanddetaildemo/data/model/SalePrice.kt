package ercanduman.listanddetaildemo.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SalePrice(
    @SerializedName("amount")
    val amount: String,
    @SerializedName("currency")
    val currency: String
) : Parcelable