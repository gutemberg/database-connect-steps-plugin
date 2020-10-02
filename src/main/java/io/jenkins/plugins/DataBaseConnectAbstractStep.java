package io.jenkins.plugins;

import java.util.Map;

import org.jenkinsci.plugins.workflow.steps.Step;
import org.kohsuke.stapler.DataBoundSetter;

public abstract class DataBaseConnectAbstractStep extends Step {
    public DataBaseConnectAbstractStep(String dbType, String host, String port, String database, String user,
			String password, String query, Map<String, String> queries) {
    	this.setDbType(dbType);
    	this.setPort(port);
		this.setPassword(password);
		this.setQuery(query);
		this.setHost(host);
		this.setQueries(queries);
		this.setDatabase(database);
		this.setUser(user);
	}

	public String getDbType() {
		return dbType;
	}
    
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	
	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public String getPort() {
		return port;
	}
	
	public void setPort(String port) {
		this.port = port;
	}
	
	public String getDatabase() {
		return database;
	}
	
	public void setDatabase(String database) {
		this.database = database;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getQuery() {
		return query;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
	public Map<String, String> getQueries() {
		return queries;
	}
	
	public void setQueries(Map<String, String> queries) {
		this.queries = queries;
	}
	
	
	public boolean isPostGres() {
		// TODO Auto-generated method stub
		return this.dbType.equals("POSTGRES");
	}

	public boolean isMySql() {
		// TODO Auto-generated method stub
		return this.dbType.equals("MYSQL");
	}

	public boolean isOracle() {
		// TODO Auto-generated method stub
		return this.dbType.equals("ORACLE");
	}
	
	@DataBoundSetter
    String dbType;
    @DataBoundSetter
    String host;
    @DataBoundSetter
    String port;
    @DataBoundSetter
    String database;
    @DataBoundSetter
    String user;
    @DataBoundSetter
    String password;
    @DataBoundSetter
    String query;
    @DataBoundSetter
    Map<String, String> queries;
}
