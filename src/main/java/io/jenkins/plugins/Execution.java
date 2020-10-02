package io.jenkins.plugins;

import java.io.IOException;

import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.SynchronousStepExecution;

import io.jenkins.plugins.utils.ConnectExecute;
import io.jenkins.plugins.utils.Util;
import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

public class Execution extends SynchronousStepExecution<Object> {
	final StepContext context;
	final DataBaseConnectStep step;
	
	protected Execution(DataBaseConnectStep step, StepContext context) throws IOException, InterruptedException {
		super(context);
		
		this.context = context;
		this.step = step;
	}

	@Override
	protected Object run() throws Exception {
		ConnectExecute connectExecute = new ConnectExecute(this.step, context);
		
		String result = connectExecute.executeQuery();	
		
		JSON json = JSONSerializer.toJSON(result);
		Object objtect = Util.transformToJavaLangStructures(json);
		
		return objtect;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}