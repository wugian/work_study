package cn.lee.demo;

import java.util.Observable;
import java.util.Observer;

public class UnderCoverA implements Observer {

	private String time;

	@Override
	public void update(Observable o, Object arg) {
		time = (String) arg;
		System.out.println("卧底A接到消息，行动时间为：" + time);
	}

}
