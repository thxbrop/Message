package com.thxbrop.message.data.remote

class ServerException(
    val code: Int
) : RuntimeException() {
    companion object {
        val REGISTER_EMAIL_EXIST = ServerException(501)
        val LOGIN_EMAIL_EXIST = ServerException(502)
        val INVITE_EXIST = ServerException(503)
        val INVITE_NOT_EXIST = ServerException(504)
        val CONVERSATION_NOT_EXIST = ServerException(505)
        val INVITE_APPROVED = ServerException(506)
        val INVITE_REFUSED = ServerException(507)
        val PERMISSION_DENIED = ServerException(508)
        val MEMBER_EXIST = ServerException(509)
        val USER_NOT_EXIST = ServerException(510)
        val FROM_USER_NOT_EXIST = ServerException(511)
        val TO_USER_NOT_EXIST = ServerException(512)
        val WRONG_PASSWORD = ServerException(513)
        val INVALIDATE_TOKEN = ServerException(514)
        val UNKNOWN = ServerException(999)


    }
}