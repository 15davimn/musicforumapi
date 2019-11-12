package api;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import api.models.Thread;

@Path("/threads")
public class ThreadsEndpoint {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getThread() {
		ResultSet response = new DatabaseConnector().runSQL("Select * From thread");
		ArrayList<Thread> threads = mapToResponse(response);
		return Response.status(200).entity(threads).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getThread(@PathParam("id") String id) {
		String sql = "Select * From thread where id = '" + id + "'";
		ResultSet response = new DatabaseConnector().runSQL(sql);
		ArrayList<Thread> threads = mapToResponse(response);
		return Response.status(200).entity(threads.get(0)).build();
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void createThread(Thread thread) {
		String sql = mapObjectToSQL(thread);
		System.out.println(sql);
		new DatabaseConnector().changeData(sql);
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
