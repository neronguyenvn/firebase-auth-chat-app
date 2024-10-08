package com.neronguyenvn.chattoggle.core.model

import com.neronguyenvn.chattoggle.core.database.model.SenderInfoEntity

data class User(
    val id: String,
    val name: String,
    val email: String,
    val photoUrl: String
)

fun User.asEntity() = SenderInfoEntity().apply {
    uid = this@asEntity.id
    name = this@asEntity.name
    email = this@asEntity.email
    photoUrl = this@asEntity.photoUrl
}
