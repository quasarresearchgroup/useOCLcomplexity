package org.quasar.use.gui.plugins.complexity;

import java.util.List;
import java.util.Stack;

import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.expr.ExpAllInstances;
import org.tzi.use.uml.ocl.expr.ExpAny;
import org.tzi.use.uml.ocl.expr.ExpAsType;
import org.tzi.use.uml.ocl.expr.ExpAttrOp;
import org.tzi.use.uml.ocl.expr.ExpBagLiteral;
import org.tzi.use.uml.ocl.expr.ExpClosure;
import org.tzi.use.uml.ocl.expr.ExpCollect;
import org.tzi.use.uml.ocl.expr.ExpCollectNested;
import org.tzi.use.uml.ocl.expr.ExpCollectionLiteral;
import org.tzi.use.uml.ocl.expr.ExpConstBoolean;
import org.tzi.use.uml.ocl.expr.ExpConstEnum;
import org.tzi.use.uml.ocl.expr.ExpConstInteger;
import org.tzi.use.uml.ocl.expr.ExpConstReal;
import org.tzi.use.uml.ocl.expr.ExpConstString;
import org.tzi.use.uml.ocl.expr.ExpConstUnlimitedNatural;
import org.tzi.use.uml.ocl.expr.ExpEmptyCollection;
import org.tzi.use.uml.ocl.expr.ExpExists;
import org.tzi.use.uml.ocl.expr.ExpForAll;
import org.tzi.use.uml.ocl.expr.ExpIf;
import org.tzi.use.uml.ocl.expr.ExpIsKindOf;
import org.tzi.use.uml.ocl.expr.ExpIsTypeOf;
import org.tzi.use.uml.ocl.expr.ExpIsUnique;
import org.tzi.use.uml.ocl.expr.ExpIterate;
import org.tzi.use.uml.ocl.expr.ExpLet;
import org.tzi.use.uml.ocl.expr.ExpNavigation;
import org.tzi.use.uml.ocl.expr.ExpNavigationClassifierSource;
import org.tzi.use.uml.ocl.expr.ExpObjAsSet;
import org.tzi.use.uml.ocl.expr.ExpObjOp;
import org.tzi.use.uml.ocl.expr.ExpObjRef;
import org.tzi.use.uml.ocl.expr.ExpObjectByUseId;
import org.tzi.use.uml.ocl.expr.ExpOclInState;
import org.tzi.use.uml.ocl.expr.ExpOne;
import org.tzi.use.uml.ocl.expr.ExpOrderedSetLiteral;
import org.tzi.use.uml.ocl.expr.ExpQuery;
import org.tzi.use.uml.ocl.expr.ExpRange;
import org.tzi.use.uml.ocl.expr.ExpReject;
import org.tzi.use.uml.ocl.expr.ExpSelect;
import org.tzi.use.uml.ocl.expr.ExpSelectByKind;
import org.tzi.use.uml.ocl.expr.ExpSelectByType;
import org.tzi.use.uml.ocl.expr.ExpSequenceLiteral;
import org.tzi.use.uml.ocl.expr.ExpSetLiteral;
import org.tzi.use.uml.ocl.expr.ExpSortedBy;
import org.tzi.use.uml.ocl.expr.ExpStdOp;
import org.tzi.use.uml.ocl.expr.ExpTupleLiteral;
import org.tzi.use.uml.ocl.expr.ExpTupleSelectOp;
import org.tzi.use.uml.ocl.expr.ExpUndefined;
import org.tzi.use.uml.ocl.expr.ExpVariable;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.ExpressionVisitor;
import org.tzi.use.uml.ocl.expr.ExpressionWithValue;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.expr.VarDeclList;

import com.google.common.collect.Lists;

public class ExpressionComplexityVisitor implements ExpressionVisitor {

	private Stack<MOperation> operationStack = new Stack<MOperation>();
	private IComplexityMetric metric;
	private boolean expandOperations;

	public ExpressionComplexityVisitor(IComplexityMetric metric, boolean expandOperations) {
		this.metric = metric;
		this.expandOperations = expandOperations;
	}

	public void visitAllInstances(ExpAllInstances exp) {
		System.out.println("visitAllInstances: " + exp);
		metric.insertAllInstances(exp);
	}

	public void visitAny(ExpAny exp) {
		System.out.println("visitAny: " + exp);
		visitCollectionOperation(exp);
	}

	public void visitAsType(ExpAsType exp) {
		System.out.println("visitAsType: " + exp);
	}

	public void visitAttrOp(ExpAttrOp exp) {
		System.out.println("visitAttrOp: " + exp);
		metric.insertAttribute(exp.attr());
		exp.objExp().processWithVisitor(this);
	}

	public void visitBagLiteral(ExpBagLiteral exp) {
		System.out.println("visitBagLiteral: " + exp);
		visitCollectionLiteral(exp);
	}

	public void visitCollect(ExpCollect exp) {
		System.out.println("visitCollect: " + exp);
		visitCollectionOperation(exp);
	}

	public void visitCollectNested(ExpCollectNested exp) {
		System.out.println("visitCollectNested: " + exp);
		visitCollectionOperation(exp);
	}

	public void visitConstBoolean(ExpConstBoolean exp) {
		System.out.println("visitConstBoolean: " + exp);
	}

	public void visitConstEnum(ExpConstEnum exp) {
		System.out.println("visitConstEnum: " + exp);
	}

	public void visitConstInteger(ExpConstInteger exp) {
		System.out.println("visitConstInteger: " + exp);
	}

	public void visitConstReal(ExpConstReal exp) {
		System.out.println("visitConstReal: " + exp);
	}

	public void visitConstString(ExpConstString exp) {
		System.out.println("visitConstString: " + exp);
	}

	public void visitEmptyCollection(ExpEmptyCollection exp) {
		System.out.println("visitEmptyCollection: " + exp);
	}

	public void visitExists(ExpExists exp) {
		System.out.println("visitExists: " + exp);
		visitCollectionOperation(exp);
	}

	public void visitForAll(ExpForAll exp) {
		System.out.println("visitForAll: " + exp);
		visitCollectionOperation(exp);
	}

	public void visitIf(ExpIf exp) {
		System.out.println("visitIf: " + exp);
		exp.getCondition().processWithVisitor(this);
		exp.getThenExpression().processWithVisitor(this);
		exp.getElseExpression().processWithVisitor(this);
	}

	public void visitIsKindOf(ExpIsKindOf exp) {
		System.out.println("visitIsKindOf: " + exp);
		exp.getSourceExpr().processWithVisitor(this);
	}

	public void visitIsTypeOf(ExpIsTypeOf exp) {
		System.out.println("visitIsTypeOf: " + exp);
		exp.getSourceExpr().processWithVisitor(this);
	}

	public void visitIsUnique(ExpIsUnique exp) {
		System.out.println("visitIsUnique: " + exp);
		visitQuery(exp);
	}

	public void visitIterate(ExpIterate exp) {
		System.out.println("visitIterate: " + exp);
		visitQuery(exp);
	}

	public void visitLet(ExpLet exp) {
		System.out.println("visitLet: " + exp);
		exp.getVarExpression().processWithVisitor(this);
		exp.getInExpression().processWithVisitor(this);
	}

	public void visitNavigation(ExpNavigation exp) {
		System.out.println("visitNavigation: " + exp);
		metric.insertNavigation(exp.getSource(), exp.getDestination());
		exp.getObjectExpression().processWithVisitor(this);
	}

	public void visitNavigationClassifierSource(ExpNavigationClassifierSource exp) {
		System.out.println("visitNavigationClassifierSource: " + exp);
		metric.insertNavigation(exp.getDestination());
		exp.getObjectExpression().processWithVisitor(this);
	}

	public void visitObjAsSet(ExpObjAsSet exp) {
		System.out.println("visitObjAsSet: " + exp);
		exp.getObjectExpression().processWithVisitor(this);
	}

	public void visitObjOp(ExpObjOp exp) {
		System.out.println("visitObjOp: " + exp);
		metric.insertObjectOperation(exp);

		for (Expression ex : exp.getArguments()) {
			ex.processWithVisitor(this);
		}

		if (expandOperations && exp.getOperation().hasExpression() && !operationStack.contains(exp.getOperation())) {
			operationStack.push(exp.getOperation());
			exp.getOperation().expression().processWithVisitor(this);
			operationStack.pop();
		}
	}

	public void visitObjRef(ExpObjRef exp) {
		System.out.println("visitObjRef: " + exp);
		exp.processWithVisitor(this);
	}

	public void visitOne(ExpOne exp) {
		System.out.println("visitOne: " + exp);
		visitQuery(exp);
	}

	public void visitOrderedSetLiteral(ExpOrderedSetLiteral exp) {
		System.out.println("visitOrderedSetLiteral: " + exp);
		visitCollectionLiteral(exp);
	}

	public void visitQuery(ExpQuery exp) {
		System.out.println("visitQuery: " + exp);
		exp.getRangeExpression().processWithVisitor(this);
		exp.getQueryExpression().processWithVisitor(this);
	}

	public void visitReject(ExpReject exp) {
		System.out.println("visitReject: " + exp);
		visitQuery(exp);
	}

	public void visitWithValue(ExpressionWithValue exp) {
		System.out.println("visitWithValue: " + exp);
	}

	public void visitSelect(ExpSelect exp) {
		System.out.println("visitSelect: " + exp);
		visitCollectionOperation(exp);
	}

	private void visitCollectionOperation(ExpQuery exp) {
		metric.startCollectionOperation();
		exp.getQueryExpression().processWithVisitor(this);
		metric.stopCollectionOperation();
		exp.getRangeExpression().processWithVisitor(this);
	}

	private void visitCollectionOperation(ExpStdOp exp) {
		metric.startCollectionOperation();
		Expression[] args = exp.args();
		if (args.length == 0) {
		} else {
			args[0].processWithVisitor(this);
			if (args.length > 1) {
				for (int i = 1; i < args.length; i++) {
					if (i > 1) {
					}
					args[i].processWithVisitor(this);
				}
			}
		}
		metric.stopCollectionOperation();
	}

	public void visitSequenceLiteral(ExpSequenceLiteral exp) {
		System.out.println("visitSequenceLiteral: " + exp);
		visitCollectionLiteral(exp);
	}

	public void visitSetLiteral(ExpSetLiteral exp) {
		System.out.println("visitSetLiteral: " + exp);
		visitCollectionLiteral(exp);
	}

	public void visitSortedBy(ExpSortedBy exp) {
		System.out.println("visitSortedBy: " + exp);
		visitQuery(exp);
	}

	public void visitStdOp(ExpStdOp exp) {
		Expression[] args = exp.args();
		String operationName;

		if (exp.getOperation().isInfixOrPrefix()) {
			operationName = exp.opname();
			metric.insertOperation(operationName);
			if (args.length == 1) {
				args[0].processWithVisitor(this);
			} else { // Infix has two arguments
				args[0].processWithVisitor(this);
				args[1].processWithVisitor(this);
			}
		} else {
			operationName = exp.opname();
			if (exp.isPre()) {
				operationName += "@pre";
			}

			List<String> collectionOperation = Lists.newArrayList("size", "asSet", "sum", "notEmpty", "isEmpty",
					"intersection", "union", "includes", "count", "at");
			if (collectionOperation.contains(operationName)) { // is collection operation?
				visitCollectionOperation(exp);
			} else {
				metric.insertOperation(operationName);

				if (args.length == 0) {
				} else {
					args[0].processWithVisitor(this);
					if (args.length > 1) {
						for (int i = 1; i < args.length; i++) {
							if (i > 1) {
							}
							args[i].processWithVisitor(this);
						}
					}
				}
			}

		}
	}

	public void visitTupleLiteral(ExpTupleLiteral exp) {
		for (ExpTupleLiteral.Part part : exp.getParts()) {
			part.getExpression().processWithVisitor(this);
		}
	}

	public void visitTupleSelectOp(ExpTupleSelectOp exp) {
		System.out.println("visitTupleSelectOp: " + exp);
	}

	public void visitUndefined(ExpUndefined exp) {
		System.out.println("visitUndefined: " + exp);
	}

	public void visitVariable(ExpVariable exp) {
		System.out.println("visitVariable: " + exp);
		metric.insertVariable(exp);
	}

	protected void visitCollectionLiteral(ExpCollectionLiteral exp) {
		for (Expression ex : exp.getElemExpr()) {
			ex.processWithVisitor(this);
		}
	}

	public void visitClosure(ExpClosure exp) {
		System.out.println("visitClosure: " + exp);
		visitQuery(exp);
	}

	public void visitOclInState(ExpOclInState expOclInState) {
		expOclInState.getSourceExpr().processWithVisitor(this);
	}

	public void visitVarDeclList(VarDeclList varDeclList) {
		for (int i = 0; i < varDeclList.size(); ++i) {
			varDeclList.varDecl(i).processWithVisitor(this);
		}
	}

	public void visitVarDecl(VarDecl varDecl) {
		System.out.println("visitVarDecl");
	}

	public void visitObjectByUseId(ExpObjectByUseId expObjectByUseId) {
		expObjectByUseId.getIdExpression().processWithVisitor(this);
	}

	public void visitConstUnlimitedNatural(ExpConstUnlimitedNatural expressionConstUnlimitedNatural) {
		System.out.println("visitConstUnlimitedNatural");
	}

	public void visitSelectByKind(ExpSelectByKind expSelectByKind) {
		expSelectByKind.getSourceExpression().processWithVisitor(this);
	}

	public void visitExpSelectByType(ExpSelectByType expSelectByType) {
		expSelectByType.getSourceExpression().processWithVisitor(this);
	}

	public void visitRange(ExpRange exp) {
		exp.getStart().processWithVisitor(this);
		exp.getEnd().processWithVisitor(this);
	}

}