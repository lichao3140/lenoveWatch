package cn.ycgo.base.utils

import java.lang.RuntimeException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
/**
 * Author:Kgstt
 * Time: 2020/11/25
 * 得到指定类的指定下标的泛型的Class
 * 注：不支持泛型传递
 */
@Suppress("UNCHECKED_CAST")
fun <T> Any.getGenericsClass(targetClass: Class<*>, index: Int): Class<T> {
    val c = this.javaClass
    check(c.name != targetClass.name) { "target class cannot be own class" }
    c.superclass?.takeIf {
        it.name != "java.lang.Class"
    }?.run {
        c.genericSuperclass?.getGenericsClass<T>(this, targetClass, index)
    }?.run {
        return this
    }
    throw RuntimeException("object is not target class's child")
}

@Suppress("UNCHECKED_CAST")
private fun <T> Type.getGenericsClass(objectClass: Class<*>, targetClass: Class<*>, index: Int): Class<T>? {
    if (objectClass.name == targetClass.name) {
        check(this is ParameterizedType) { "target class is not a ParameterizedType" }
        check(actualTypeArguments.size > index) { "the arguments's length of target class must above index" }
        return actualTypeArguments[index] as? Class<T> ?:  throw RuntimeException("not support trans generics") //不支持泛型传递
    } else {
        objectClass.superclass?.takeIf {
            it.name != "java.lang.Class"
        }?.run {
            return objectClass.genericSuperclass?.getGenericsClass(this, targetClass, index)
        }
    }
    return null
}