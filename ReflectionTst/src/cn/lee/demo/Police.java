package cn.lee.demo;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Police extends Observable {
	private String time;

	public Police(List<Observer> list) {
		super();
		for (Observer o : list) {
			addObserver(o);
		}
	}

	public void change(String time) {
		this.time = time;
		setChanged();
		notifyObservers(this.time);
	}
}
