package com.example.mobieleapp.data.database.user

class UserFireBase {

    var username: String? =""
    var password: String?=""
    var role: String?=""
    var email: String?=""
    var phoneNr: String?=""
    var pic: ByteArray?=null

    constructor(
        username: String?,
        password: String?,
        role: String?,
        email: String?,
        phoneNr: String?,
        pic: ByteArray?,
    ) {
        this.username = username
        this.password = password
        this.role = role
        this.email = email
        this.phoneNr = phoneNr
        this.pic = pic
    }


}