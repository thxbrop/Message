package com.thxbrop.message.extensions

val <T>T.className: String get() = this!!::class.java.simpleName

val <T>T.TAG: String get() = className

val <T>T.objectHashCode: String get() = className + '@' + System.identityHashCode(this).toString(16)