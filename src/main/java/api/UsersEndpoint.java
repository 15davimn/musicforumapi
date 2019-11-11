package api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TimeZone;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import api.models.User;

@Path("/users")
public class UsersEndpoint {

	static int lport;
	static String rhost;
	static int rport;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser() {
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
			try {
				Statement st = con.createStatement();
				String sql = "UPDATE user " + "SET tag = 'ripon.wasim@smile.com' WHERE username='bob'";

				int update = st.executeUpdate(sql);
				if (update >= 1) {
					System.out.println("Row is updated.");
				} else {
					System.out.println("Row is not updated.");
				}
			} catch (SQLException s) {
				System.out.println("SQL statement is not executed!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public String accessDatabase() {
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
