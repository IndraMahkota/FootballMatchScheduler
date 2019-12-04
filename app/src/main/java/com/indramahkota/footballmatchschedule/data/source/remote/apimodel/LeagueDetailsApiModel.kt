package com.indramahkota.footballmatchschedule.data.source.remote.apimodel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LeagueDetailsApiModel(
    @SerializedName("dateFirstEvent")
    val dateFirstEvent: String? = null,
    @SerializedName("idCup")
    val idCup: String? = null,
    @SerializedName("idLeague")
    val idLeague: String? = null,
    @SerializedName("idSoccerXML")
    val idSoccerXML: String? = null,
    @SerializedName("intFormedYear")
    val intFormedYear: String? = null,
    @SerializedName("strBadge")
    val strBadge: String? = null,
    @SerializedName("strBanner")
    val strBanner: String? = null,
    @SerializedName("strComplete")
    val strComplete: String? = null,
    @SerializedName("strCountry")
    val strCountry: String? = null,
    @SerializedName("strDescriptionCN")
    val strDescriptionCN: String? = null,
    @SerializedName("strDescriptionDE")
    val strDescriptionDE: String? = null,
    @SerializedName("strDescriptionEN")
    val strDescriptionEN: String? = null,
    @SerializedName("strDescriptionES")
    val strDescriptionES: String? = null,
    @SerializedName("strDescriptionFR")
    val strDescriptionFR: String? = null,
    @SerializedName("strDescriptionHU")
    val strDescriptionHU: String? = null,
    @SerializedName("strDescriptionIL")
    val strDescriptionIL: String? = null,
    @SerializedName("strDescriptionIT")
    val strDescriptionIT: String? = null,
    @SerializedName("strDescriptionJP")
    val strDescriptionJP: String? = null,
    @SerializedName("strDescriptionNL")
    val strDescriptionNL: String? = null,
    @SerializedName("strDescriptionNO")
    val strDescriptionNO: String? = null,
    @SerializedName("strDescriptionPL")
    val strDescriptionPL: String? = null,
    @SerializedName("strDescriptionPT")
    val strDescriptionPT: String? = null,
    @SerializedName("strDescriptionRU")
    val strDescriptionRU: String? = null,
    @SerializedName("strDescriptionSE")
    val strDescriptionSE: String? = null,
    @SerializedName("strDivision")
    val strDivision: String? = null,
    @SerializedName("strFacebook")
    val strFacebook: String? = null,
    @SerializedName("strFanart1")
    val strFanart1: String? = null,
    @SerializedName("strFanart2")
    val strFanart2: String? = null,
    @SerializedName("strFanart3")
    val strFanart3: String? = null,
    @SerializedName("strFanart4")
    val strFanart4: String? = null,
    @SerializedName("strGender")
    val strGender: String? = null,
    @SerializedName("strLeague")
    val strLeague: String? = null,
    @SerializedName("strLeagueAlternate")
    val strLeagueAlternate: String? = null,
    @SerializedName("strLocked")
    val strLocked: String? = null,
    @SerializedName("strLogo")
    val strLogo: String? = null,
    @SerializedName("strNaming")
    val strNaming: String? = null,
    @SerializedName("strPoster")
    val strPoster: String? = null,
    @SerializedName("strRSS")
    val strRSS: String? = null,
    @SerializedName("strSport")
    val strSport: String? = null,
    @SerializedName("strTrophy")
    val strTrophy: String? = null,
    @SerializedName("strTwitter")
    val strTwitter: String? = null,
    @SerializedName("strWebsite")
    val strWebsite: String? = null,
    @SerializedName("strYoutube")
    val strYoutube: String? = null
) : Parcelable