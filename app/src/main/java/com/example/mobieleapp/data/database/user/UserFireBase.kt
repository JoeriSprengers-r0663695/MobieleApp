package com.example.mobieleapp.data.database.user

class UserFireBase {

    var idUser: Int = 0
    var username: String? =""
    var password: String?=""
    var role: String?=""
    var email: String?=""
    var phoneNr: String?=""
    var pic: ByteArray?=null

    constructor(
        idUser: Long,
        username: String?,
        password: String?,
        role: String?,
        email: String?,
        phoneNr: String?,
        pic: ByteArray?,
    ) {
        this.idUser = idUser.toInt()
        this.username = username
        this.password = password
        this.role = role
        this.email = email
        this.phoneNr = phoneNr
        this.pic = pic
    }


}