package api;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import api.models.User;

@Path("/users")
public class UsersEndpoint {

	static int lport;
	static String rhost;
	static int rport;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser() {
		ResultSet response = new DatabaseConnector().runSQL("Select * From user");
		ArrayList<User> users = mapToResponse(response);
		return Response.status(200).entity(users)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Allow-Headers", "origin, application/vnd.api+json, application/json, content-type, accept, authorization").build();
	}

	@OPTIONS
	public Response preFlightCheck() {
		return Response.status(200)
				.header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Allow-Headers", "origin, application/vnd.api+json, application/json, content-type, accept, authorization").build();
	}
	
	@OPTIONS
	@Path("{id}")
	public Response preFlightCheckOnFind() {
		return Response.status(200)
				.header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Allow-Headers", "origin, application/vnd.api+json, application/json, content-type, accept, authorization").build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getUser(@PathParam("id") String id) {
		String sql = "Select * From user where id = '" + id + "'";
		ResultSet response = new DatabaseConnector().runSQL(sql);
		ArrayList<User> users = mapToResponse(response);
		return Response.status(200).entity(users.get(0))
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Allow-Headers", "origin, application/vnd.api+json, application/json, content-type, accept, authorization").build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void createUser(User user) {
		String sql = mapObjectToSQL(user);
		System.out.println(sql);
		new DatabaseConnector().changeData(sql);
	}
	
	private String mapObjectToSQL(User user) {
		String sql = "INSERT INTO user values ('";
		sql += user.getId() + "', ";
		sql += "'" + user.getPassword() + "', ";
		sql += "'" + user.getIcon() + "', ";
		sql += "'" + user.getTag() + "')";
		return sql;
	}
	
	private ArrayList<User> mapToResponse(ResultSet response) {
		
		ArrayList<User> users = new ArrayList<User>();
		try {
			while (response.next()) {
				User user = new User();
				user.setId(response.getString("id"));
				user.setPassword(response.getString("password"));
				user.setIcon(response.getString("icon"));
				user.setTag(response.getString("tag"));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
}
