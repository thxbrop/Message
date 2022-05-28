package com.thxbrop.message.domain.model

import androidx.annotation.IntDef

data class Invite(
    val id: Int, val conId: Int, val fromId: Int, val toId: Int, @State val state: Int
) {
    @IntDef(State.PENDING, State.APPROVED, State.REFUSED)
    annotation class State {
        companion object {
            const val PENDING = 0
            const val APPROVED = 1
            const val REFUSED = 2
        }
    }
}
