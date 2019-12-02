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

import api.models.Post;

@Path("/posts")
public class PostsEndpoint {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPost() {
		ResultSet response = new DatabaseConnector().runSQL("Select * From post");
		ArrayList<Post> posts = mapToResponse(response);
		return Response.status(200).entity(posts)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Allow-Headers", "origin, application/vnd.api+json, application/json, content-type, accept, authorization").build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getPost(@PathParam("id") String id) {
		String sql = "Select * From post where id = '" + id + "'";
		ResultSet response = new DatabaseConnector().runSQL(sql);
		ArrayList<Post> posts = mapToResponse(response);
		return Response.status(200).entity(posts.get(0))
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Allow-Headers", "origin, application/vnd.api+json, application/json, content-type, accept, authorization").build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void createPost(Post post) {
		String sql = mapObjectToSQL(post);
		System.out.println(sql);
		new DatabaseConnector().changeData(sql);
	}
	
	private String mapObjectToSQL(Post post) {
		String sql = "INSERT INTO `post` (`thread`, `content`, `user`) VALUES (";
		sql += "'" + post.getThread()+ "', ";
		sql += "'" + post.getContent() + "', ";
		sql += "'" + post.getUser() + "')";
		return sql;
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
	
	private ArrayList<Post> mapToResponse(ResultSet response) {
		
		ArrayList<Post> posts = new ArrayList<Post>();
		try {
			while (response.next()) {
				Post post = new Post();
				post.setId(response.getInt("id"));
				post.setThread(response.getString("thread"));
				post.setContent(response.getString("content"));
				post.setUser(response.getString("user"));
				posts.add(post);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return posts;
	}

}
