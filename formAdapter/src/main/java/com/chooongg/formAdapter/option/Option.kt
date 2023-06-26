package com.chooongg.formAdapter.option

data class Option(val _name: String, val _value: Any?) : BaseOption {

    constructor(name: String) : this(name, name)

    override fun getName() = _name
    override fun getValue() = _value
}