package com.github.spyunderscore04.omegaskyblock.util

import com.github.spyunderscore04.omegaskyblock.OmegaSkyblock
import kotlin.reflect.KClass
import kotlin.reflect.KProperty


class I18nKey private constructor(
    val string: String
) {

    companion object {

        fun <A : Any> of(base: KClass<A>, property: KProperty<Any>): I18nKey {
            val modId = OmegaSkyblock.modId
            val groupId = base.simpleName!!.inSnakeCase()
            val itemId = property.name.inSnakeCase()
            return I18nKey("$modId.$groupId.$itemId")
        }
    }
}
