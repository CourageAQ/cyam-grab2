package com.cyam.grab2.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	public void show(String pullDate){
		CoalPriceService coalPriceService = new CoalPriceService();
		String datetime = coalPriceService.selectCoal();
		if (pullDate != null && !pullDate.equals(datetime) && !datetime.equals("") && !pullDate.equals("")) {
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
	        		for (int i = 0; i <jsonArray.size(); i++) {
			        	js = jsonArray.getJSONObject(i);
	        			CoalPrice coalPrice = new CoalPrice();
		        		String pub_nam = js.getString("pub_nam");
		        		coalPrice.setHeat(Integer.valueOf(pub_nam.substring(0, pub_nam.length()-1)));
		        		coalPrice.setNowpeace(js.getString("this_price_min")+ "-" +js.getString("this_price_max"));
		        		coalPrice.setLastpeace(js.getString("pre_price_min")+ "-" +js.getString("pre_price_max"));
		        		if (js.getString("lastyear_price_max") == "null" ||  js.getString("lastyear_price_min") == "null") {
		        			coalPrice.setLasttime(null);
						}else{
							coalPrice.setLasttime(js.getString("lastyear_price_min") + "-" +js.getString("lastyear_price_max"));
						}
		        		coalPrice.setDegree(null);
		        		coalPrice.setHuanbi(js.getString("chain_relative"));
		        		coalPrice.setTongbi(js.getString("same_relative") == "null" ? null : js.getString("same_relative"));
		        		coalPrice.setDate(pullDate);
		        		coalPrice.setType(js.getString("port_name"));
		        		coalPriceService.saveCoal(coalPrice);
					}
				} catch (IOException e) {
					System.out.println("第二步：获取发布数据失败！");
				}
				
			} catch (MalformedURLException e) {
				System.out.println("第二步：获取数据时url地址不正确！");
			}
		}else{
			if (pullDate == null) {
				System.out.println("从Internet获取的时间为空");
			}else if (datetime.equals("")) {
				System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "由于数据库建立连接失败，导致无法从数据库中获取时间，因此默认获取时间为空！");
			}else{
				System.out.println("已经得过最新的数据！数据发布时间为：" + pullDate);
			}
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
		        if (js.get("fdate2") != null) {
		        	 date = js.getString("fdate2"); 
				}else{
					date = null;
				}
			} catch (IOException e) {
				System.out.println("第一步：获取发布时间失败！失败原因可能是网络没有正确连接，正在重试,请您检查外部网络连接");
			}
		} catch (MalformedURLException e) {
			System.out.println("第一步：获取时间时url地址不正确！");
		}
		return date;
	}
}
