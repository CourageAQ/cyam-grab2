package com.cyam.grab2.service;

import com.cyam.grab2.dao.CoalPriceDao;
import com.cyam.grab2.model.CoalPrice;

public class CoalPriceService {
	CoalPriceDao coalPriceDao = new CoalPriceDao();
	public void saveCoal(CoalPrice coalPrice){
		coalPriceDao.saveCoal(coalPrice);
	}
	public String selectCoal(){
		return coalPriceDao.selectCoal();
	}
	
	public int selectNumber(String date){
		return coalPriceDao.selectNumber(date);
	}
}
