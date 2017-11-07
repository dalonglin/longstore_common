package com.longstore.common.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果对象
 */
public class Page<T> implements Serializable{
    private static final long serialVersionUID = 4059385751925912483L;
    
    /** 需要查看的页码 */
	private int pageNo = 1;
	/** 需要查看的页码大小 */
	private int pageSize = 10;
    /** 当前页数据集合 */
	private List<T> result;
    /** 总记录数 */
	private int totalCount;
    /** 总页数 */
    private int totalPage;
    
    public Page(){

    }
        
    public Page(int pageNo, int pageSize, int totalCount, List<T> result){
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.result = result;
        this.totalPage = (totalCount + pageSize - 1)/pageSize;
    }

	/** 总页数 */
	public int getTotalPage() {
		return totalPage;
	}
    public int getPageNo() {
        return pageNo;
    }
    public int getPageSize() {
        return pageSize;
    }
    public List<T> getResult() {
        return result;
    }
    public int getTotalCount() {
        return totalCount;
    }
    public int getPrePage() {
        return pageNo - 1;
    }
    public int getNextPage() {
        return pageNo + 1;
    }
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public void setResult(List<T> result) {
        this.result = result;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    
}
