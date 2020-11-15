package es.iessaladillo.pedrojoya.intents.data.local.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Pokemon(val id:Long ,
                   val name:String,
                   @DrawableRes
                   val imageResId:Int,
                   val power:Int,
): Parcelable