package project2;

/******************************************************************/

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Random;

import simpledb.remote.SimpleDriver;

public class SelectTestTables {
	static long startDateTime = 0;
	static long endDateTime = 0;
	static int equalityValue = 70;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Connection conn = null;
		Driver d = new SimpleDriver();
		String host = "localhost"; // you may change it if your SimpleDB server is running on a different machine
		String url = "jdbc:simpledb://" + host;
		Statement s = null;
		try {
			conn = d.connect(url, null);
			s = conn.createStatement();
			
			// 4 selection query			
			String unit = "\tnano second";
			// anther selection job as the first one in case any inaccuracy in the first one
			s.executeQuery("SELECT a1,a2 FROM test5 where a1=1;");
			// test1
			markStartTime();
			s.executeQuery("SELECT a1,a2 FROM test1 where a1=" + equalityValue + ";");
			markEndTime();
			System.out.println("Selection time of test1:\t" + cousumedTime() + unit);
			// test2
			markStartTime();
			s.executeQuery("SELECT a1,a2 FROM test2 where a1=" + equalityValue + ";");
			markEndTime();
			System.out.println("Selection time of test2:\t" + cousumedTime() + unit);
			// test3
			markStartTime();
			s.executeQuery("SELECT a1,a2 FROM test3 where a1=" + equalityValue + ";");
			markEndTime();
			System.out.println("Selection time of test3:\t" + cousumedTime() + unit);
			// test4
			markStartTime();
			s.executeQuery("SELECT a1,a2 FROM test4 where a1=" + equalityValue + ";");
			markEndTime();
			System.out.println("Selection time of test4:\t" + cousumedTime() + unit);
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * mark the current Date().time stored in global variable, startDateTime
	 */
	public static void markStartTime() {
		startDateTime = System.nanoTime();
	}
	
	/**
	 * mark the current Date().time stored in global variable, endDateTime
	 */
	public static void markEndTime() {
		endDateTime = System.nanoTime();
	}
	
	/**
	 * return the consumed time between start time and end time
	 * @return
	 */
	public static long cousumedTime() {
		return endDateTime - startDateTime;
	}
	
}