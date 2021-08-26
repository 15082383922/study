package com.chancy.test
/**
 * Created by HZ08950 on 2021/8/13/013
 */

fun main(args: Array<String>) {
    println("test")
    val sum = sum(5, 7)
    println(sum)
    //类型后面加?表示可为空
    val age: String? = "23"
    //抛出空指针异常
    val ages = age!!.toInt()
    println(ages)
    //不做处理返回 null
    val ages1 = age?.toInt()
    println(ages1)
    //age为空返回-1
    val ages2 = age?.toInt() ?: -1
    println(ages2)

    for (i in 99 downTo 56 step 2)  println(i)
}


fun sum(a: Int, b: Int) = a + b;
