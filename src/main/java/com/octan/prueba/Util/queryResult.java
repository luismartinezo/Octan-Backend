/**
 * 
 */
package com.octan.prueba.Util;

import java.util.List;

/**
 * @author LUIS MARTINEZ
 * @since 01/2022
 * @version 1.0
 */
public class queryResult {

	private int totalRecords;
    private List<Object> list;

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }
}
