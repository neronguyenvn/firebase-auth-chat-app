package com.neronguyen.psychicmemory.core.database.model

import com.neronguyen.psychicmemory.core.model.ChatMessage
import com.neronguyen.psychicmemory.core.model.SenderInfo
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.datetime.Instant
import org.mongodb.kbson.ObjectId

class MessageEntity : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var content: String = ""
    var timestamp: RealmInstant? = null
    var senderInfo: SenderInfoEntity? = null
}

class SenderInfoEntity : EmbeddedRealmObject {
    var uid: String = ""
    var name: String = ""
    var email: String = ""
    var photoUrl: String = ""
}

fun MessageEntity.asExternalModel(currentUserId: String): ChatMessage {
    return if (currentUserId == senderInfo?.uid) ChatMessage.CurrentUserMessage(
        content = content,
        senderInfo = senderInfo!!.asExternalModel(),
        timestamp = Instant.fromEpochSeconds(timestamp!!.epochSeconds)
    ) else ChatMessage.OtherMessage(
        content = content,
        senderInfo = senderInfo!!.asExternalModel(),
        timestamp = Instant.fromEpochSeconds(timestamp!!.epochSeconds)
    )
}

fun SenderInfoEntity.asExternalModel() = SenderInfo(
    uid, name, email, photoUrl
)
