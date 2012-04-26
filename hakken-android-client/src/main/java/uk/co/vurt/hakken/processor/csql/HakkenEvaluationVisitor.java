package uk.co.vurt.hakken.processor.csql;

import net.wmfs.coalesce.csql.EvaluationVisitor;
import net.wmfs.coalesce.csql.Expression.CustomItemExpression;
import net.wmfs.coalesce.csql.ExpressionException;
import android.util.Log;

public class HakkenEvaluationVisitor extends EvaluationVisitor {

	private final static String TAG = "HakkenEvaluationVisitor";
	
	@Override
	public void visitCustomItemExpression(
			CustomItemExpression nodeItemExpression) throws ExpressionException {

		Log.d(TAG, "nodeItemExpression: " + nodeItemExpression.getName());
		super.visitCustomItemExpression(nodeItemExpression);
	}
}
