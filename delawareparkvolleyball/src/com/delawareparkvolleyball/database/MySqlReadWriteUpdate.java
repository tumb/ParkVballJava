package com.delawareparkvolleyball.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlReadWriteUpdate {

	private static Connection mysqlConnection = null ; 
	
	private static Connection fetchConnection() {
		try {
		if(mysqlConnection != null && ! mysqlConnection.isClosed()) {
			return mysqlConnection ; 
		}
		else {
			Class.forName("com.mysql.jdbc.Driver") ; 
			String url = "jdbc:mysql://localhost:3306/dpva" ;
			String username = "dev" ; 
			String password = "dddddd" ;
			mysqlConnection = DriverManager.getConnection(url, username, password) ;
		}
		}
		catch(Exception exception) {
			System.out.println("Error fetching connection: " + exception.getMessage()) ; 
		}
		return mysqlConnection ; 
	}
	
	public static void main(String[] arguments) {
		Connection connection = fetchConnection() ; 
		String selectSql = "select pAf.first_name, pBf.first_name, mr.team_A_wins, mr.team_B_wins, mr.play_date, l.day_of_week, " ;
			selectSql += "l.year from match_result mr " ;
			selectSql += "join team tA on mr.team_A_id = tA.id " ;
			selectSql += "join team tB on mr.team_B_id = tB.id " ;
			selectSql += "join person pAf on tA.woman_id = pAf.id " ;
			selectSql += "join person pBf on tB.woman_id = pBf.id " ;
			selectSql += "join league l on mr.league_id = l.id ; " ;
		try {
			Statement selectStatement = connection.createStatement() ;
			ResultSet selectResultSet = selectStatement.executeQuery(selectSql) ; 
			while(selectResultSet.next()) {
				System.out.println(selectResultSet.getString(1)) ; 
			}
		} catch (SQLException sqlException) {
			// TODO Auto-generated catch block
			sqlException.printStackTrace();
		} 
	}
	
}
