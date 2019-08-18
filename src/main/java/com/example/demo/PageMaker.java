package com.example.demo;

public class PageMaker {
	private PageInfo pInfo;
	private int startPage;
	private int endPage;
	private int totalCount;
	private int pageBlock;
	private boolean next;
	private boolean prev;
	
	public PageMaker() {
		pageBlock = 5;
	}
	
	private void calcData() {
		endPage = (int)Math.ceil(pInfo.getcPage() / (double)pageBlock) * pageBlock;
		startPage = endPage - pageBlock + 1;
		int limitPage = (int)Math.ceil(totalCount / (double)pInfo.getPerPageNum());
		if(endPage > limitPage) {
			endPage = limitPage;
		}
		
		prev = startPage == 1 ? false : true;
		next = endPage == limitPage ? false : true;
		
	}
	public PageInfo getpInfo() {
		return pInfo;
	}
	public void setpInfo(PageInfo pInfo) {
		this.pInfo = pInfo;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData();
	}
	public int getPageBlock() {
		return pageBlock;
	}
	public void setPageBlock(int pageBlock) {
		this.pageBlock = pageBlock;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	
	
	
}
