package com.cyam.grab2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.cyam.grab2.controller.UrlController;



public class Application {
	public void runApplication(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
					System.out.println(simpleDateFormat.format(new Date()) + "开始执行,每隔两个小时执行一次*60*60*2");
					UrlController u = new UrlController();
					u.show(u.getDate());
			}
		};
		timer.schedule(task, new Date(), 1000*30);
    }
}
