package uk.co.vurt.hakken.processor.csql;

import net.wmfs.coalesce.csql.EvaluationVisitor;
import net.wmfs.coalesce.csql.Expression.CustomItemExpression;
import net.wmfs.coalesce.csql.ExpressionException;
import uk.co.vurt.hakken.domain.job.DataItem;
import uk.co.vurt.hakken.processor.JobProcessor;
import android.util.Log;

public class HakkenEvaluationVisitor extends EvaluationVisitor {

	private final static String TAG = "HakkenEvaluationVisitor";
	
	private JobProcessor jobProcessor;
	
	@Override
	public void visitCustomItemExpression(
			CustomItemExpression nodeItemExpression) throws ExpressionException {

		Log.d(TAG, "nodeItemExpression: " + nodeItemExpression.getName());
		String name = nodeItemExpression.getName();
		if (name.startsWith("DATAITEM.")) {
			visitDataItemExpression(name.substring(9), nodeItemExpression);
		} else {
			throw new ExpressionException("custom node " + name
					+ " is not allowed");
		}
	}
	
	protected void visitDataItemExpression(String id,
			CustomItemExpression expr) throws ExpressionException {
		if (jobProcessor == null) {
			result = null;
		} else {
			String[] identifiers = id.split("\\$");
			Log.d(TAG, "Page: " + identifiers[0]);
			Log.d(TAG, "Name: " + identifiers[1]);
			Log.d(TAG, "Type: " + identifiers[2]);
			DataItem dataItem = jobProcessor.retrieveDataItem(identifiers[0], identifiers[1], identifiers[2]);

			if (dataItem != null && dataItem.getValue() != null) {
				result = dataItem.getValue();
			} else {
				result = null;
			}
		}
		Log.d(TAG, "getting value for: " + id + " = " + result);
	}

	public void setJobProcessor(JobProcessor jobProcessor) {
		this.jobProcessor = jobProcessor;
	}
	
	
}
