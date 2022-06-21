package com.wireless.mobile.test.api.models

import com.google.gson.annotations.SerializedName

data class Languages(

    @SerializedName(
        "afr",
        alternate = [
            "amh", "ara", "arc", "aym", "aze", "bar", "bel",
            "ben", "ber", "bis", "bjz", "bos", "bul", "bwg",
            "cal", "cat", "ces", "cha", "ckb", "cnr", "crs",
            "dan", "deu", "div", "dzo", "ell", "eng", "est",
            "fao", "fas", "fij", "fil", "fin", "fra", "gil",
            "gle", "glv", "grn", "gsw", "hat", "heb", "her",
            "hgm", "hif", "hin", "hmo", "hrv", "hun", "hye",
            "ind", "isl", "ita", "jam", "jpn", "kal", "kat",
            "kaz", "kck", "khi", "khm", "kin", "kir", "kon",
            "kor", "kwn", "lao", "lat", "lav", "lin", "lit",
            "loz", "ltz", "lua", "mah", "mey", "mfe", "mkd",
            "mlg", "mlt", "mon", "mri", "msa", "mya", "nau",
            "nbl", "ndc", "nde", "ndo", "nep", "nfr", "niu",
            "nld", "nno", "nob", "nor", "nrf", "nso", "nya",
            "nzs", "pap", "pau", "pih", "pol", "por", "pov",
            "prs", "pus", "que", "rar", "roh", "ron", "run",
            "rus", "sag", "sin", "slk", "slv", "smi", "smo",
            "sna", "som", "sot", "spa", "sqi", "srp", "ssw",
            "swa", "swe", "tam", "tet", "tgk", "tha", "tir",
            "tkl", "toi", "ton", "tpi", "tsn", "tso", "tuk",
            "tur", "tvl", "ukr", "urd", "uzb", "ven", "vie",
            "xho", "zdj", "zho", "zib", "zul"
        ],

        ) val idiom: String
)