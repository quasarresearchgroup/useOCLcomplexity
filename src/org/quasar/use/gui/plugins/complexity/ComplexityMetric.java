package org.quasar.use.gui.plugins.complexity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MNavigableElement;
import org.tzi.use.uml.ocl.expr.ExpAllInstances;
import org.tzi.use.uml.ocl.expr.ExpObjOp;
import org.tzi.use.uml.ocl.expr.ExpVariable;
import org.tzi.use.uml.ocl.expr.Expression;

import com.google.common.collect.Lists;

public class ComplexityMetric implements IComplexityMetric {

	// NNR
	private Set<String> navigatedRelationships;
	// NAN
	private Set<String> referredAttributes;
	// WNO
	private int wno;
	// NNC
	private Set<String> navigatedClasses;
	// NUCA, NUCO
	private List<String> utilityClasses = Lists.newArrayList("CalendarDate", "Instant");
	private Set<String> nuca;
	private Set<String> nuco;
	// WNN
	private int wnn;
	private List<Integer> wnnStack = new ArrayList<Integer>();
	// DN
	private DNNode dnTree;
	// WCO
	private Map<Integer, Integer> wcoOperations = new HashMap<Integer, Integer>();
	private int wco;

	public ComplexityMetric() {
		navigatedRelationships = new HashSet<String>();
		referredAttributes = new HashSet<String>();
		wno = 0;
		navigatedClasses = new HashSet<String>();
		nuca = new HashSet<String>();
		nuco = new HashSet<String>();
		wco = 0;
	}

	public void insertAllInstances(ExpAllInstances expression) {
		processExpAllInstances(expression);
	}

	private void processExpAllInstances(Expression expression) {
		ExpAllInstances expAllInstances = (ExpAllInstances) expression;
		if (expAllInstances.getSourceType().isTypeOfClass()) {
			String className = ((MClass) expAllInstances.getSourceType()).name();
			insertNavigatedClass(className);
		}
	}

	public void insertOperation(String operationName) {
		List<String> operations1 = Lists.newArrayList("round", "min", "max", "not", "isDefined", "isUndefined");
		List<String> operations2 = Lists.newArrayList("/", "-", "+", "*", "=", "<>", "and", "<", ">", "or", "<=", ">=");
		List<String> operations3 = Lists.newArrayList("substring");
		if (operations1.contains(operationName)) {
			wno += ((1 + 2) * (1 + 1 + 1));
			System.out.println("insertOperation: " + operationName);
		} else if (operations2.contains(operationName)) {
			wno += ((1 + 3) * (1 + 1 + 2));
			System.out.println("insertOperation: " + operationName);
		} else if (operations3.contains(operationName)) {
			wno += ((1 + 4) * (1 + 1 + 3));
			System.out.println("insertOperation: " + operationName);
		} else {
			System.err.println("operation not inserted: " + operationName);
		}
	}

	public void startCollectionOperation() {
		wnnStack.add(wnnStack.size() + 1);
		int totalOperationsOnLevel = wcoOperations.containsKey(wnnStack.size()) ? wcoOperations.get(wnnStack.size()) + 1
				: 1;
		wcoOperations.put(wnnStack.size(), totalOperationsOnLevel);
	}

	public void stopCollectionOperation() {
		wnnStack.remove(wnnStack.size() - 1);
		if (dnTree != null) {
			if (dnTree.getLeftChild() != null) {
				dnTree.getLeftChild().setWeight(-1);
			}
			if (dnTree.getRightChild() != null) {
				dnTree.getRightChild().setWeight(-1);
			}
		}
	}

	public void insertNavigation(MNavigableElement source, MNavigableElement target) {
		// TODO: the same rolename can be used in different classes!
		wnn += (wnnStack.size() + 1);
		insertNavigationSource(source);
		insertNavigationTarget(target);

		DNNode leftChild, rightChild = null;
		if (dnTree == null) {
			leftChild = DNNode.createNode(target.cls().name());
		} else {
			leftChild = dnTree;
			if (leftChild.getClassName() != target.cls().name()) {
				rightChild = DNNode.createNode(target.cls().name());
			}
		}
		DNNode parent = DNNode.createNode(source.cls().name());
		parent.setLeftChild(leftChild);
		if (rightChild != null) {
			parent.setRightChild(rightChild);
		}
		dnTree = parent;
	}

	public void insertNavigation(MNavigableElement target) {
		insertNavigationTarget(target);
	}

	private void insertNavigationSource(MNavigableElement source) {
		String className = source.cls().name();
		insertNavigatedClass(className);
	}

	private void insertNavigationTarget(MNavigableElement target) {
		String className = target.cls().name();
		insertNavigatedRelationship(className);
		insertNavigatedClass(className);
	}

	private void insertNavigatedRelationship(String navigationName) {
		navigatedRelationships.add(navigationName);
	}

	public void insertVariable(ExpVariable expression) {
		if (expression.type().isTypeOfClass()) {
			String className = ((MClass) expression.type()).name();
			insertNavigatedClass(className);
		}
	}

	private void insertNavigatedClass(String className) {
		if (!utilityClasses.contains(className)) {
			navigatedClasses.add(className.toLowerCase());
		}
	}

	public void insertAttribute(MAttribute attribute) {
		if (utilityClasses.contains(attribute.owner().name())) {
			nuca.add(attribute.name());
		} else {
			referredAttributes.add(attribute.name().toLowerCase());
		}
	}

	public void insertObjectOperation(ExpObjOp exp) {
		if (utilityClasses.contains(exp.getOperation().cls().name())) {
			nuco.add(exp.getOperation().name());
			System.out.println("insertObjectOperation: " + exp);
		}
	}

	public IComplexityMetricResult getResult() {
		IComplexityMetricResult result = new ComplexityMetricResult();
		result.setNNR(navigatedRelationships.size());
		result.setNAN(referredAttributes.size());
		result.setWNO(wno);
		result.setNNC(navigatedClasses.size());
		result.setNUCA(nuca.size());
		result.setNUCO(nuco.size());
		result.setWNN(wnn);
		if (dnTree != null) {
			result.setDN(dnTree.getLength());
		} else {
			result.setDN(0);
		}
		result.setWCO(getWco());
		return result;
	}

	private int getWco() {
		wcoOperations.keySet().forEach(new Consumer<Integer>() {
			public void accept(Integer key) {
				int totalOperationsOnLevel = wcoOperations.get(key);
				wco += key * totalOperationsOnLevel;
			}
		});
		return wco;
	}

}
