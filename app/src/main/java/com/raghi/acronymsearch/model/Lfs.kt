package com.raghi.acronymsearch.model

data class Lfs(
    val lf: String,
    val freq: Int,
    val since: Int,
    val vars: List<Vars>
)
