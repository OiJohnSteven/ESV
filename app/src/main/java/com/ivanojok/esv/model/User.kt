package com.ivanojok.esv.model


class Victim{
    var phone:String ?= null
    var firstName:String ?= null
    var lastName:String ?= null
    var nin:String ?= null
    var residence:String ?= null
    constructor(phone:String,
                firstName:String,
                lastName:String,
                nin:String,
                residence:String,){

        this.phone = phone
        this.firstName=firstName
        this.lastName=lastName
        this.nin=nin
        this.residence=residence
    }

    constructor()
}


class Counsellor {
    var phoneNo:String ?= null
    var name:String ?= null
    var nin:String ?= null
    var image:String ?= null
    var latitude:String ?= null
    var longitude:String ?= null

    constructor(phoneNo:String,name:String, nin:String, image:String, latitude:String, longitude:String){
        this.phoneNo = phoneNo
        this.name = name
        this.nin = nin
        this.image = image
        this.latitude = latitude
        this.longitude = longitude
    }

    constructor()
}


class Police {
    var phoneNo:String ?= null
    var name:String ?= null
    var latitude:String ?= null
    var longitude:String ?= null

    constructor(phoneNo:String,name:String, latitude:String, longitude:String){
        this.phoneNo = phoneNo
        this.name = name
        this.latitude = latitude
        this.longitude = longitude
    }

    constructor()
}

class Case {
    var caseId:String ?= null
    var info:String ?= null
    var latitude:String ?= null
    var longitude:String ?= null
    var victim: String ?= null

    constructor(caseId:String,info:String, latitude:String, longitude:String, victim: String){
        this.caseId = caseId
        this.info = info
        this.latitude = latitude
        this.longitude = longitude
        this.victim = victim
    }

    constructor()
}