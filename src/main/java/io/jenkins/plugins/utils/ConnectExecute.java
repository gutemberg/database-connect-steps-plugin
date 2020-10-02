package io.jenkins.plugins.utils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.jenkinsci.plugins.workflow.steps.StepContext;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

import hudson.model.TaskListener;
import io.jenkins.plugins.DataBaseConnectStep;

public class ConnectExecute {
	DataBaseConnectStep step;	
	TaskListener listener;
	Connection connection = null;
	
	public ConnectExecute(DataBaseConnectStep oracleConnectStep, StepContext context) throws SQLException, IOException, InterruptedException, ClassNotFoundException {
		step = oracleConnectStep;	
		listener = context.get(TaskListener.class);
		connection = getConnection();
	}
	
	public String executeQuery() throws IOException, InterruptedException {
		JsonObject result = new JsonObject();

        try {
            Statement statement = connection.createStatement();
            listener.getLogger().println("Connected on " + step.getHost() 
            	+ ", port " + step.getPort() + ", database " + step.getDatabase() 
            	+ " \nExecuting query \"" + step.getQuery() + "\"");

            JsonObject queries = new JsonObject();

            if(step.getQuery() != null) {
                queries.addProperty("query", step.getQuery());
            } else if(step.getQueries() != null ) {
                Gson gson = new Gson();
                Type gsonType = new TypeToken<HashMap>(){}.getType();
                queries = gson.toJsonTree(step.getQueries(), gsonType).getAsJsonObject();
            }

            String query = "";

            for(Map.Entry<String, JsonElement> item : queries.entrySet()){
                String key = item.getKey();
                JsonPrimitive _query = item.getValue().getAsJsonPrimitive();
                query = _query.getAsString();
                
                if(query.toLowerCase().startsWith("select")) {
                	result.add(key, selectExecute(statement, query));
                } else {
                	result.add(key, updateExecute(statement, query));
                }
            }
            statement.close();
        }catch(SQLException e) {
            listener.getLogger().println(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                listener.getLogger().println(throwables.getMessage());
            }
        }

        if(step.getQuery() != null){
            return result.toString();
        } else if(step.getQueries() != null) {
            return result.getAsJsonObject("query").toString();
        } else {
            return null;
        }
	}
	
	private JsonArray selectExecute(Statement statement, String query) throws SQLException {
		ResultSet resultSet = statement.executeQuery(query);
        JsonArray jsonArray = new JsonArray();
        ResultSetMetaData rsMetaData = resultSet.getMetaData();

        while (resultSet.next()) {
            int numColumns = rsMetaData.getColumnCount();
            JsonObject obj = new JsonObject();

            for (int i = 1; i <= numColumns; i++) {
                String columnName = rsMetaData.getColumnName(i);
                
                Object data = resultSet.getObject(columnName);
                
                obj.addProperty(columnName, data != null ? data.toString() : null);
            }
            
            jsonArray.add(obj);
        }
        
        return jsonArray;
	}

	private JsonArray updateExecute(Statement statement, String query) throws SQLException {
		JsonArray jsonArray = new JsonArray();
		JsonObject obj = new JsonObject();
		
		obj.addProperty("affectedrows", statement.executeUpdate(query));
		jsonArray.add(obj);
		
		return jsonArray;
	}

	private Connection getConnection() throws SQLException, ClassNotFoundException {
		String connectString = "";
		String driverString = "";
		if(step.isPostGres()) {
			driverString = "org.postgresql.Driver";
			connectString = "jdbc:postgresql://" + step.getHost() + ":" + step.getPort() + "/" + step.getDatabase();
		} else if(step.isOracle()) {
			driverString = "oracle.jdbc.driver.OracleDriver";
			connectString = "jdbc:oracle:thin:@" + step.getHost() + ":" + step.getPort() + "/" + step.getDatabase();
		} else if(step.isMySql()){
			driverString = "com.mysql.jdbc.Driver";
			connectString = "jdbc:mysql://" + step.getHost() + ":" + step.getPort() + "/" + step.getDatabase();
		}
		
		Class.forName(driverString);
		
		return DriverManager.getConnection(connectString, step.getUser(), step.getPassword());
	}
}
