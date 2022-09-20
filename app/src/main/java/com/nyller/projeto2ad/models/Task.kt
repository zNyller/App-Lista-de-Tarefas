package com.nyller.projeto2ad.models

data class Task(
    val title : String,
    val description : String,
    var state : Boolean = false
)
