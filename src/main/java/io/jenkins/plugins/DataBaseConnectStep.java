package io.jenkins.plugins;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepDescriptor;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Extension;
import hudson.FilePath;
import hudson.model.TaskListener;

public final class DataBaseConnectStep extends DataBaseConnectAbstractStep {
    @DataBoundConstructor
    public DataBaseConnectStep(String dbType, String host, String port, String database, String user, String password, String query, Map<String, String> queries) {
    	super(dbType, host, port, database, user, password, query, queries);
	}
    
	@Override
	public StepExecution start(StepContext context) throws Exception {
		// TODO Auto-generated method stub
		if(this.isOracle()) {		
			context.get(TaskListener.class).getLogger().println("Connecting on oracle database");
			return new Execution(this, context);
		} else if(this.isMySql()){
			context.get(TaskListener.class).getLogger().println("Connecting on MySql database");
			return new Execution(this, context);
		} else if(this.isPostGres()){
			context.get(TaskListener.class).getLogger().println("Connecting on PostGres database");
			return new Execution(this, context);
		} else {
			context.get(TaskListener.class).getLogger().println("Database type is inválid");
			return null;
		}
	}

	@Extension
	public static final class DescriptorImpl extends StepDescriptor {

		@Override
		public Set<? extends Class<?>> getRequiredContext() {
			// TODO Auto-generated method stub
			return Collections.singleton(FilePath.class);
		}

		@Override
		public String getFunctionName() {
			// TODO Auto-generated method stub
			return "dataBaseConnect";
		}
		
		@Override
		public String getDisplayName() {
			// TODO Auto-generated method stub
			return "Connect and execute query in databases (Oracle, Mysql e PostGres) with JSON return";
		}
	}	
}
