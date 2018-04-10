package com.xwguan.autofund.dto.common;

/**
 * 分页对象, 包含分页查询参数
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-17
 */
public class Page implements Cloneable {

    /**
     * 总条目数, 默认为0
     */
    private int totalItem = 0;

    /**
     * 目标页, 默认为0
     */
    private int targetPage = 1;

    /**
     * 每页条目数, {@code LIMIT offset, itemPerPage}, 默认为10
     */
    private int itemPerPage = 10;

    /**
     * 总页数
     */
    private int totalPage = 1;

    /**
     * 从第几条开始, {@code LIMIT offset, itemPerPage}
     */
    private int offset = 0;

    public Page() {
        super();
    }

    public Page(int targetPage, int itemPerPage) {
        this.targetPage = targetPage;
        this.itemPerPage = itemPerPage;
    }

    /**
     * 计算Page对象各属性, 使用Page对象进行分页查询时<b>必须执行</b>, 否则永远只能查询到第一页数据
     */
    public void calculateFields() {
        // reset invalid value of fields to default
        this.totalItem = totalItem >= 0 ? totalItem : 0;
        this.targetPage = targetPage > 0 ? targetPage : 1;
        this.itemPerPage = itemPerPage > 0 ? itemPerPage : 10;
        // calculate fields
        int totalPageTmp = totalItem / itemPerPage;
        if (totalPageTmp % itemPerPage != 0) {
            totalPageTmp += 1;
        }
        if (totalPageTmp <= 0) {
            totalPageTmp = 1;
        }
        this.totalPage = totalPageTmp;
        if (targetPage > totalPage) {
            targetPage = totalPage;
        }
        offset = (targetPage - 1) * itemPerPage;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public int getTargetPage() {
        return targetPage;
    }

    public void setTargetPage(int targetPage) {
        this.targetPage = targetPage;
    }

    public int getItemPerPage() {
        return itemPerPage;
    }

    public void setItemPerPage(int itemPerPage) {
        this.itemPerPage = itemPerPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "Page [totalItem=" + totalItem + ", targetPage=" + targetPage + ", itemPerPage=" + itemPerPage
            + ", totalPage=" + totalPage + ", offset=" + offset + "]";
    }

    @Override
    public Page clone() throws CloneNotSupportedException {
        return (Page) super.clone();
    }

}
