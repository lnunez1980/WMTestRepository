package com.wireless.mobile.test.api.models

import com.google.gson.annotations.SerializedName

data class Currencies(

    @SerializedName(
        "AED",
        alternate = [
            "AFN", "ALL", "AMD", "ANG", "AOA", "ZWL",
            "ARS", "AUD", "AWG", "AZN", "BAM", "BBD",
            "BDT", "BGN", "BHD", "BIF", "BMD", "BND",
            "BOB", "BRL", "BSD", "BTN", "BWP", "BYN",
            "BZD", "CAD", "CDF", "CHF", "CKD", "CLP",
            "CNY", "COP", "CRC", "CUC", "CUP", "CVE",
            "CZK", "DJF", "DKK", "DOP", "DZD", "EGP",
            "ERN", "ETB", "EUR", "FJD", "FKP", "FOK",
            "GBP", "GEL", "GGP", "GHS", "GIP", "GMD",
            "GNF", "GTQ", "GYD", "HKD", "HNL", "HRK",
            "HTG", "HUF", "IDR", "ILS", "IMP", "INR",
            "IQD", "IRR", "ISK", "JEP", "JMD", "JOD",
            "JPY", "KES", "KGS", "KHR", "KID", "KMF",
            "KPW", "KRW", "KWD", "KYD", "KZT", "LAK",
            "LBP", "LKR", "LRD", "LSL", "LYD", "MAD",
            "MDL", "MGA", "MKD", "MMK", "MNT", "MOP",
            "MRU", "MUR", "MVR", "MWK", "MXN", "MYR",
            "MZN", "NAD", "NGN", "NIO", "NOK", "NPR",
            "NZD", "OMR", "PAB", "PEN", "PGK", "PHP",
            "PKR", "PLN", "PYG", "QAR", "RON", "RSD",
            "RUB", "RWF", "SAR", "SBD", "SCR", "SDG",
            "SEK", "SGD", "SHP", "SLL", "SOS", "SRD",
            "SSP", "STN", "SYP", "SZL", "THB", "TJS",
            "TMT", "TND", "TOP", "TRY", "TTD", "TVD",
            "TWD", "TZS", "UAH", "UGX", "USD", "UYU",
            "UZS", "VES", "VND", "VUV", "WST", "XAF",
            "XCD", "XOF", "XPF", "YER", "ZAR", "ZMW",
            "   ZWLsy"],
    ) val currency: CurrencyModel? = null
)