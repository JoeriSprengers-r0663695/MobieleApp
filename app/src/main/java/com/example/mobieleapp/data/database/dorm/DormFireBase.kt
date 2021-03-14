package com.example.mobieleapp.data.database.dorm

class DormFireBase {
    var adTitle: String? =""
    var streetName: String? =""
    var housenr: Int?=0
    var city: String? =""
    var postalcode: Int?=0
    var rent: Double?=0.0
    var description: String?=""
    var owner: String?=""
    //var pics: List<ByteArray>?=null

    constructor(
        adTitle: String?,
        streetName: String?,
        housenr: Int?,
        city: String?,
        postalcode: Int?,
        rent: Double?,
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
