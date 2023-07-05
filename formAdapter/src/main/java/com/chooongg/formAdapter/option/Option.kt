package com.chooongg.formAdapter.option

data class Option(
    val _name: String,
    val _value: String?,
    val _secondaryName: String?
) : BaseOption {

    constructor(name: String) : this(name, name, null)

    constructor(name: String, secondaryName: String) : this(name, name, secondaryName)

    override fun getName() = _name
    override fun getValue() = _value
    override fun getSecondaryName() = _secondaryName
}