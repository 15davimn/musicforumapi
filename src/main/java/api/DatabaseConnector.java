package api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

//Code for this class taken from https://stackoverflow.com/questions/1968293/connect-to-remote-mysql-database-through-ssh-using-java
public class DatabaseConnector {

	static int lport;
	static String rhost;
	static int rport;
	
	public int changeData(String sql) {
		accessDatabase();
		System.out.println("An example for updating a Row from Mysql Database!");
		Connection con = null;
		String driver = "com.mysql.jdbc.Driver";
		String db = "c2375b11test";
		String url = "jdbc:mysql://" + rhost + ":" + lport + "/";
		String dbUser = "c2375b11";
		String dbPasswd = "c2375bU!";
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url + db, dbUser, dbPasswd);
				Statement st = con.createStatement();
				return st.executeUpdate(sql);
		} catch (Exception e) {
			return -1;
		}

	}

	public ResultSet runSQL(String sql) {
		accessDatabase();
		System.out.println("An example for updating a Row from Mysql Database!");
		Connection con = null;
		String driver = "com.mysql.jdbc.Driver";
		String db = "c2375b11test";
		String url = "jdbc:mysql://" + rhost + ":" + lport + "/";
		String dbUser = "c2375b11";
		String dbPasswd = "c2375bU!";
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url + db, dbUser, dbPasswd);
				Statement st = con.createStatement();
				return st.executeQuery(sql);
		} catch (Exception e) {
			return null;
		}

	}

	private String accessDatabase() {
		String username = "c2375b11";
		String password = "Madison#44";
		String host = "ps11.pstcc.edu";
		int port = 22;
		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(username, host, port);
			lport = 4321;
			rhost = "localhost";
			rport = 3306;
			session.setPassword(password);
			session.setConfig("StrictHostKeyChecking", "no");
			System.out.println("Establishing Connection...");
			session.connect();
			int assinged_port = session.setPortForwardingL(lport, rhost, rport);
			System.out.println("localhost:" + assinged_port + " -> " + rhost + ":" + rport);
		} catch (Exception e) {
			System.err.print(e);
		}
		return "";
	}

}
