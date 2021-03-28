package com.example.mobieleapp.data.database.dorm

class DormFireBase {
    var adTitle: String? =""
    var streetName: String? =""
    var housenr: Long?=0
    var city: String? =""
    var postalcode: Long?=0
    var rent: String?= ""
    var description: String?=""
    var owner: String?=""
    //var pics: List<ByteArray>?=null

    constructor(
        adTitle: String?,
        streetName: String?,
        housenr: Long?,
        city: String?,
        postalcode: Long?,
        rent: String?,
        description: String?,
        owner: String?
    ){
        this.adTitle = adTitle
        this.streetName = streetName
        this.housenr = housenr
        this.city = city
        this.postalcode = postalcode
        this.rent = rent
        this.description = description
        this.owner = owner
    }


}
