package com.cyam.grab2.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.cyam.grab2.model.CoalPrice;
import com.cyam.grab2.service.CoalPriceService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UrlController {
	JSONObject jsonObject = null;
	JSONArray jsonArray = null;
	JSONObject js  = null;
	BufferedReader reader= null;
	URL connect;
	public void show(String date,String pullDate){
		CoalPriceService coalPriceService = new CoalPriceService();
		try {
			connect = new URL("http://www.cqcoal.com/mars-web//indexmark/listnew");
			try {
				HttpURLConnection connection = (HttpURLConnection) connect.openConnection();
				connection.setRequestMethod("POST");
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String line;
		        while ((line = reader.readLine()) != null) {     
		        	jsonObject = JSONObject.fromObject(line);
		        	jsonArray = jsonObject.getJSONArray("data");
		        }
		        if (date.equals(pullDate)) {
		        	String datetime = coalPriceService.selectCoal();
		        	if (!date.equals(datetime)) {
		        		for (int i = 0; i <jsonArray.size(); i++) {
				        	js = jsonArray.getJSONObject(i);
				        	if (js.getString("port_name").equals("秦皇岛港")){
				        			CoalPrice coalPrice = new CoalPrice();
					        		String pub_nam = js.getString("pub_nam");
					        		coalPrice.setHeat(Integer.valueOf(pub_nam.substring(0, pub_nam.length()-1)));
					        		coalPrice.setNowpeace(js.getString("this_price_min")+ "-" +js.getString("this_price_max"));
					        		coalPrice.setLastpeace(js.getString("pre_price_min")+ "-" +js.getString("pre_price_max"));
					        		coalPrice.setLasttime(js.getString("lastyear_price_min") + "-" +js.getString("lastyear_price_max"));
					        		coalPrice.setDegree("无数据");
					        		coalPrice.setHuanbi(js.getString("chain_relative"));
					        		coalPrice.setTongbi(js.getString("same_relative"));
					        		coalPriceService.saveCoal(coalPrice);
							}
						}
					}else{
						System.out.println("今天是发布的日子，但是数据已经获取！");
					}
				}else {
					System.out.println("已经获得过数据！还没有发布新数据");
				}
			} catch (IOException e) {
				System.out.println("网络连接失败");
			}finally {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		} catch (MalformedURLException e) {
			System.out.println("没有网络连接呀");
		}
		
	}
	public String getDate(){
		String date = null;
		try {
			connect = new URL("http://www.cqcoal.com/mars-web//indexmark/infonew");
			try {
				HttpURLConnection connection = (HttpURLConnection) connect.openConnection();
				connection.setRequestMethod("POST");
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String line;
		        while ((line = reader.readLine()) != null) {     
		        	jsonObject = JSONObject.fromObject(line);
		        }
		        JSONObject js = (JSONObject) jsonObject.get("data");
		        
		        date = js.getString("fdate2"); 
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return date;
	}
}
