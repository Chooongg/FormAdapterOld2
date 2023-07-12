package com.chooongg.formAdapter

class FormDataVerificationException(
    val name: CharSequence?,
    val globalPosition: Int,
) : RuntimeException() {
}