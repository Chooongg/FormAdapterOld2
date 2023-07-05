package com.chooongg.formAdapter.option

data class Option(val _name: String, val _value: String?) : BaseOption {

    constructor(name: String) : this(name, name)

    override fun getName() = _name
    override fun getSecondaryName() = null
    override fun getValue() = _value
}