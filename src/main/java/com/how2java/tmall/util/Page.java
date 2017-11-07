package com.how2java.tmall.util;

/**
 * 从页面接收start,结合静态defaultCount,提供给Mapper进行分页查询
 */
public class Page {
    private int start;//开始页数
    private int count;//每页显示个数
    private int total;//总个数
    private String param;//参数

    private static int defaultCount = 5;

    public Page() {
        count = defaultCount;
    }

    public Page(int start, int count) {
        this.start = start;
        this.count = count;
    }

    public boolean isHasPreviouse(){
        return start != 0;
    }

    public boolean isHasNext(){
        return start != getLast();
    }
    /*
    获得最后一页的索引
     */
    public int getLast() {
        int last;
        if (0 == total % count){
            last = total - count;
        }else {
            last = total - total % count;
        }
        last = last<0?0:last;
        return last;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotalPage(){
        int totalPage;
        if (0 == total % count){
            totalPage = total/count;
        }else {
            totalPage = total/count +1 ;
        }
        if (0 == totalPage){
            totalPage = 1;
        }
        return totalPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "Page{" +
                "start=" + start +
                ", count=" + count +
                ", total=" + total +
                ", param='" + param + '\'' +
                '}';
    }

}
