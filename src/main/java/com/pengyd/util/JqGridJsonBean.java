/**
 * 版权所有, 
 * Author: 郭 荣誉出品
 * copyright: 2018
 */
package com.pengyd.util;

import java.util.List;

/**

 * @description: TODO - 
 * @author:  pengyd
 * @createTime: 2018年3月9日 上午10:41:00
 *
 */
public class JqGridJsonBean {
    private int page;

    private int total;

    private int records;

    private List<?> root;

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRecords() {
        return this.records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public List<?> getRoot() {
        return this.root;
    }

    public void setRoot(List<?> root) {
        this.root = root;
    }
}