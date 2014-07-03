package cn.lee.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * <li>警察是可被观察对象，可以为其添加观察者，卧底是观察者可以观察被观察者，以确定其下一步谋划</li> 
 * <li>这个例子不太恰当，另外一个例子是关于出版社和读者的。</li> 
 * <li>出版社是可观察对象，能够出版读物，读者观察，如有兴趣则进行订阅</li>
 * 
 * @author pc
 *
 */
public class TestObserver {

	public static void main(String[] args) {

		UnderCoverA o1 = new UnderCoverA();
		List<Observer> list = new ArrayList<>();
		list.add(o1);
		Police subject = new Police(list);
		subject.change("02:25");
		System.out.println("===========由于消息败露，行动时间提前=========");
		subject.change("01:05");
	}

}
