package com.example.demo;


public class PageInfo {
	private int cPage;
	private int perPageNum;
	
	public PageInfo() {
		cPage = 1;
		perPageNum = 10;
	}
	public int getStartPage()
	{
		return (cPage - 1) * perPageNum;
	}
	public int getcPage() {
		return cPage;
	}
	public void setcPage(int cPage) {
		if(cPage < 1) {
			cPage = 1;
		}
		this.cPage = cPage;
	}
	public int getPerPageNum() {
		return perPageNum;
	}
	public void setPerPageNum(int perPageNum) {
		this.perPageNum = perPageNum;
	}
	
	
}
