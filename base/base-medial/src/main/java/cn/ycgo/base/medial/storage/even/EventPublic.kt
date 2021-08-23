package cn.ycgo.base.medial.storage.even

/**
 *Author:Kgstt
 *Time: 21-3-29
 */

enum class Type {
    SELECT_SKU_ADDRESS
}

class EventPublic(val type: Type, val data: Any? = null)


class EventConsultMsgPush(
val imUserName: String = "",
val text: String = ""
)

class EventPiazzaMsgPush(
    val imUserName: String = "",
    val text: String = ""
)