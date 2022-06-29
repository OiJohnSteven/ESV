package com.ivanojok.esv.model

data class Chat (
    var id:String ?= null,
    var message:String ?= null,
    var time:String ?= null,
    var status:String ?= null
)