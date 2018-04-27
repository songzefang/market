package com.duolanjian.java.market.view;

import java.util.HashMap;

import com.duolanjian.java.market.bean.Evaluate;

public class EvaluateView extends HashMap<String, Object> {

	private static final long serialVersionUID = 7L;
	
	public EvaluateView(Evaluate evaluate) {
		put("id", evaluate.getId());
		put("content", evaluate.getContent());
		put("contentTime", evaluate.getCreateTime());
		put("append", evaluate.getAppend());
		put("appendTime", evaluate.getAppendTime());
		put("grade", evaluate.getGrade());
		
	}
}
