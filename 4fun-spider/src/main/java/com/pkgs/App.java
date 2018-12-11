package com.pkgs;

import com.pkgs.task.TopAnswerWorker;
import com.pkgs.task.TopicWorker;

/**
 * App
 *
 * 
 * @author cs12110 at 2018年12月10日下午9:38:34
 *
 */
public class App {

	public static void main(String[] args) {
		new Thread(new TopicWorker()).start();
		new Thread(new TopAnswerWorker()).start();
	}

}
