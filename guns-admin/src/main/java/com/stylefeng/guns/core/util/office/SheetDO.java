package com.stylefeng.guns.core.util.office;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 工作薄
 *
 * @author zhaopan
 * @date 2013年10月10日 下午4:31:50
 */
public class SheetDO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2561246045030171320L;
    /**
     * 工作薄名称
     */
    private String sheetName;

    /**
     * 最顶上的数据列表（在headList之上）
     */
    private List<List<Object>> firstList;

    /**
     * 最后的数据列表（在bodyList之下）
     */
    private List<List<Object>> lastList;

    /**
     * 头部行起始值
     */
    private int headIndex = 0;

    /**
     * 列表数据起始值
     */
    private int bodyIndex = 1;
    /**
     * 表头
     */
    private List<String> headlist;

    /**
     * 表体
     */
    private List<List<Object>> bodyList;

    public SheetDO() {
        super();
    }

    public SheetDO(String sheetName) {
        super();
        this.sheetName = sheetName;
    }

    public SheetDO(String sheetName, List<String> headlist,
                   List<List<Object>> bodyList) {
        super();
        this.sheetName = sheetName;
        this.headlist = headlist;
        this.bodyList = bodyList;
    }


    public SheetDO(List<String> headlist, List<List<Object>> bodyList) {
        super();
        this.headlist = headlist;
        this.bodyList = bodyList;
    }

    public List<String> getHeadlist() {
        return headlist;
    }

    /**
     * 添加表头
     *
     * @param col
     */
    public void addHead(Collection<String> col) {
        if (this.headlist == null) {
            this.headlist = new ArrayList<String>();
        }
        if (col == null || col.size() <= 0) {
            return;
        }
        this.headlist.addAll(col);
    }

    /**
     * 添加表头
     *
     * @param headName
     */
    public void addHead(String headName) {
        if (this.headlist == null) {
            this.headlist = new ArrayList<String>();
        }
        if (headName == null || headName.trim().length() <= 0) {
            return;
        }
        this.headlist.add(headName);
    }

    public void addRow(List<Object> row) {
        if (this.bodyList == null) {
            this.bodyList = new ArrayList<List<Object>>();
        }
        if (row == null || row.isEmpty()) {
            return;
        }
        this.bodyList.add(row);
    }

    /**
     * 列数
     */
    public int getColumns() {
        if (headlist == null) {
            return 0;
        }
        return headlist.size();
    }

    /**
     * 行数
     */
    public int getRows() {
        if (bodyList == null) {
            return 0;
        }
        return bodyList.size();
    }

    /*******************************************/
    public void setHeadlist(List<String> headlist) {
        this.headlist = headlist;
    }

    public List<List<Object>> getBodyList() {
        return bodyList;
    }

    public void setBodyList(List<List<Object>> bodyList) {
        this.bodyList = bodyList;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<List<Object>> getFirstList() {
        return firstList;
    }

    public void setFirstList(List<List<Object>> firstList) {
        this.firstList = firstList;
    }

    public List<List<Object>> getLastList() {
        return lastList;
    }

    public void setLastList(List<List<Object>> lastList) {
        this.lastList = lastList;
    }

    public int getHeadIndex() {
        return headIndex;
    }

    public void setHeadIndex(int headIndex) {
        this.headIndex = headIndex;
    }

    public int getBodyIndex() {
        return bodyIndex;
    }

    public void setBodyIndex(int bodyIndex) {
        this.bodyIndex = bodyIndex;
    }

    public void setAutoBodyIndex() {
        this.bodyIndex = getHeadIndex() + 1;
    }


}
