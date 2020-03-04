package com.swensun.potato

import org.junit.Test

import java.util.ArrayList

class List_Test {

    @Test
    fun remove_test() {
        //        ArrayList<Integer> list = new ArrayList<Integer>();
        //        for (int i = 0; i < 50; i++) {
        //            list.add(i);
        //        }
        //
        //        for (int ii: list) {
        //            if (ii == 10) {
        //                list.remove(ii);
        //            }
        //        }
        var result = 1f
        (0 until 30).forEach {
            result *= 1.15f
            println(result)
        }
        println()
    }
}
