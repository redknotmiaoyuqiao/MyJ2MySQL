package com.database.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.database.miaowu.Model;

public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*
		 * Model m = new Model("aaa"); String[] params = {"aa","xixix"};
		 * List<Map<String, Object>> s = m.SqlQuery(
		 * "select * from miaoyuqiuao where xixi = ? or xixi = ?",params);
		 * 
		 * for(Map<String, Object> m1 : s){
		 * System.out.println((String)m1.get("xixi")); }
		 */

		/*
		 * Model m = new Model(""); String[] params = {"aaa","www","qwewqr"};
		 * m.SqlUpdate("insert into miaoyuqiuao (xixi,haha,mimi) values (?,?,?)"
		 * , params);
		 */
		Model m = new Model("miaoyuqiuao");
		Map<String, String> params = new HashMap<>();
		params.put("xixi", "hahasad");
		params.put("haha", "hahadsa");
		params.put("mimi", "xixixixi");

		List<Map<String, Object>> s = m.select(params);

		for (Map<String, Object> m1 : s) {
			System.out.println((String) m1.get("xixi"));
		}
	}

}
