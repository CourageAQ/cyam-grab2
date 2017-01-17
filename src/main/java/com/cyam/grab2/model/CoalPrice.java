package com.cyam.grab2.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CoalPrice {
	private Long id;
	private int heat;	//发热量
	private String nowpeace;//本期价格
	private String lastpeace;//上期价格
	private String Degree;		//涨幅度
	private String huanbi;		//环比
	private String lasttime;	//去年同期
	private String tongbi;	//同比
	private String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public int getHeat() {
		return heat;
	}
	public void setHeat(int heat) {
		this.heat = heat;
	}
	public String getNowpeace() {
		return nowpeace;
	}
	public void setNowpeace(String nowpeace) {
		this.nowpeace = nowpeace;
	}
	public String getLastpeace() {
		return lastpeace;
	}
	public void setLastpeace(String lastpeace) {
		this.lastpeace = lastpeace;
	}
	public String getDegree() {
		return Degree;
	}
	public void setDegree(String degree) {
		Degree = degree;
	}
	public String getHuanbi() {
		return huanbi;
	}
	public void setHuanbi(String huanbi) {
		this.huanbi = huanbi;
	}
	public String getLasttime() {
		return lasttime;
	}
	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}
	public String getTongbi() {
		return tongbi;
	}
	public void setTongbi(String tongbi) {
		this.tongbi = tongbi;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public CoalPrice() {
		super();
		// TODO Auto-generated constructor stub
	}
}
