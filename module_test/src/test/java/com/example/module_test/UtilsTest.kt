package com.example.module_test

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

/**
 * @author sunwen
 * @Date 2019-11-29
 * @Project Potato
 */
class UtilsTest {

    var a = 0
    var b = 0

    @Before
    fun setUp() {
        a = 10
        b = 20
    }

    @After
    fun tearDown() {
        println("$a, $b")
    }

    @Test
    fun add() {
        assertEquals(30, Utils().add(a, b))
    }
}