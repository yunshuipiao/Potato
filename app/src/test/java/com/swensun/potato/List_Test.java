package com.swensun.potato;

import org.junit.Test;

import java.util.ArrayList;

public class List_Test {

    @Test
    public void remove_test() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 50; i++) {
            list.add(i);
        }

        for (int ii: list) {
            if (ii == 10) {
                list.remove(ii);
            }
        }
    }
}
