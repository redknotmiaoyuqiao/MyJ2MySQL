package com.database.miaowu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {

	private String url = "jdbc:mysql://127.0.0.1:3306/kefu";
	private String username = "root";
	private String password = "miaoyuqiao";

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet resultSet = null;

	private String table_name;

	public Model(String table_name) {
		this.table_name = table_name;

		try {
			conn = (Connection) DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* Sql */
	public List<Map<String, Object>> SqlQuery(String sql, String[] params) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		try {
			this.pstmt = (PreparedStatement) this.conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstmt.setString(i + 1, params[i]);
			}

			resultSet = pstmt.executeQuery();

			ResultSetMetaData rsmd = resultSet.getMetaData();
			int count = rsmd.getColumnCount();

			while (resultSet.next()) {
				Map<String, Object> column = new HashMap<String, Object>();
				for (int i = 1; i <= count; i++) {
					String columnName = rsmd.getColumnName(i);
					Object object = resultSet.getObject(columnName);
					column.put(columnName, object);
				}
				list.add(column);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		release();

		return list;
	}

	public int SqlUpdate(String sql, String[] params) {
		int res = -1;
		try {
			this.pstmt = (PreparedStatement) this.conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstmt.setString(i + 1, params[i]);
			}
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		release();

		return res;
	}

	/* 单表 */
	public int insert(Map<String, String> params) {
		int res = -1;

		String sql = "insert into " + this.table_name + " (";

		List<String> keyList = new ArrayList<>();

		for (String key : params.keySet()) {
			keyList.add(key);
		}

		for (int i = 0; i < keyList.size(); i++) {
			String key = keyList.get(i);
			if (i == keyList.size() - 1) {
				sql = sql + key;
			} else {
				sql = sql + key + ",";
			}
		}

		sql = sql + ") values (";

		for (int i = 0; i < keyList.size(); i++) {

			if (i == keyList.size() - 1) {
				sql = sql + "?";
			} else {
				sql = sql + "?" + ",";
			}
		}

		sql = sql + ")";

		try {
			this.pstmt = (PreparedStatement) this.conn.prepareStatement(sql);

			for (int i = 0; i < keyList.size(); i++) {
				String key = keyList.get(i);
				pstmt.setString(i + 1, params.get(key));
			}
			res = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		release();
		return res;
	}

	public boolean update() {
		return true;
	}

	public int del(Map<String, String> params) {
		int res = -1;
		List<String> keyList = new ArrayList<>();

		for (String key : params.keySet()) {
			keyList.add(key);
		}

		String sql = "delete from " + this.table_name;

		if (keyList.size() > 0) {
			sql = sql + " where ";
		}

		for (int i = 0; i < keyList.size(); i++) {
			if (i == keyList.size() - 1) {
				sql = sql + keyList.get(i) + "= ?";
			}else{
				sql = sql + keyList.get(i) + " = ? and ";
			}
		}

		System.out.println(sql);
		
		try {
			this.pstmt = (PreparedStatement) this.conn.prepareStatement(sql);

			for (int i = 0; i < keyList.size(); i++) {
				String key = keyList.get(i);
				pstmt.setString(i + 1, params.get(key));
			}
			res = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		release();

		return res;
	}

	public List<Map<String, Object>> select(Map<String, String> params) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		return list;
	}

	private void release() {
		try {
			if (resultSet != null) {
				resultSet.close();
				resultSet = null;
			}
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			// System.out.println("数据库关闭错误");
			e.printStackTrace();
		}
	}

	static {
		try {
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
