package api;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;

import api.models.Thread;

@Path("/threads")
public class ThreadsEndpoint {



	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getThread() throws JsonProcessingException {
		DatabaseConnector con = new DatabaseConnector();
		ResultSet response = con.runSQL("Select * From thread");
		ArrayList<Thread> threads = mapToResponse(response);
		Response toReturn = Response.status(200).entity(threads)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Allow-Headers", "origin, application/vnd.api+json, application/json, content-type, accept, authorization").build();
		con.close();
		return toReturn;
		
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
	public Response getThread(@PathParam("id") String id) {
		String sql = "Select * From thread where id = '" + id + "'";
		DatabaseConnector con = new DatabaseConnector();
		ResultSet response = con.runSQL(sql);
		ArrayList<Thread> threads = mapToResponse(response);
		Response toReturn = Response.status(200).entity(threads.get(0))
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Allow-Headers", "origin, application/vnd.api+json, application/json, content-type, accept, authorization").build();
		con.close();
		return toReturn;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void createThread(Thread thread) {
		String sql = mapObjectToSQL(thread);
		System.out.println(sql);
		DatabaseConnector con = new DatabaseConnector();
		con.changeData(sql);
		con.close();
	}
	
	@OPTIONS
	public Response preFlightCheck() {
		return Response.status(200)
				.header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Allow-Headers", "origin, application/vnd.api+json, application/json, content-type, accept, authorization").build();
	}
	
	private String mapObjectToSQL(Thread thread) {
		String sql = "INSERT INTO thread values ('";
		sql += thread.getId() + "', ";
		sql += "'" + thread.getOwner() + "')";
		return sql;
	}
	
	private ArrayList<Thread> mapToResponse(ResultSet response) {
		
		ArrayList<Thread> threads = new ArrayList<Thread>();
		try {
			while (response.next()) {
				Thread thread = new Thread();
				thread.setId(response.getString("id"));
				thread.setOwner(response.getString("owner"));
				threads.add(thread);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return threads;
	}
}
