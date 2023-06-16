package com.chooongg.formAdapter.boundary

import androidx.annotation.IntDef

/**
 * 边界信息
 */
class Boundary(
    @BoundaryType start: Int,
    @BoundaryType top: Int,
    @BoundaryType end: Int,
    @BoundaryType bottom: Int
) {

    constructor() : this(NONE)
    constructor(any: Int) : this(any, any, any, any)

    @BoundaryType
    var startType: Int = start
        internal set

    @BoundaryType
    var topType: Int = top
        internal set

    @BoundaryType
    var endType: Int = end
        internal set

    @BoundaryType
    var bottomType: Int = bottom
        internal set

    companion object {
        const val NONE = 0
        const val LOCAL = 1
        const val GLOBAL = 2
    }

    @IntDef(NONE, LOCAL, GLOBAL)
    annotation class BoundaryType
}