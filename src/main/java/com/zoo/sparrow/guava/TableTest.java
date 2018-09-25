package com.zoo.sparrow.guava;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * Created by David.Liu on 17/6/27.
 */
public class TableTest {

    public static void main(String[] args) {
        Table<String, Integer, String> table = HashBasedTable.create();
        // 二维表数据结构

        // A={1=A1, 2=A2, 3=A3} B={1=B1, 2=B2, 3=B3}
        for (char a = 'A'; a < 'C'; ++a) {
            for (Integer b = 1; b <= 3; ++b) {
                table.put(Character.toString(a), b, String.format("%s%s", a, b));
            }
        }

        System.out.println(table.row("A"));
        System.out.println(table.rowMap());
        System.out.println(table.column(1));
        System.out.println(table.columnMap());
    }
}
