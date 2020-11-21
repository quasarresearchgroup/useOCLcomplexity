package org.quasar.use.gui.plugins.complexity;

import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MNavigableElement;
import org.tzi.use.uml.ocl.expr.ExpAllInstances;
import org.tzi.use.uml.ocl.expr.ExpObjOp;
import org.tzi.use.uml.ocl.expr.ExpVariable;

public interface IComplexityMetric {

	void insertAllInstances(ExpAllInstances expression);

	void insertOperation(String operationName);

	void startCollectionOperation();

	void stopCollectionOperation();

	void insertNavigation(MNavigableElement source, MNavigableElement target);

	void insertNavigation(MNavigableElement target);

	void insertVariable(ExpVariable expression);

	void insertAttribute(MAttribute attribute);

	IComplexityMetricResult getResult();

	void insertObjectOperation(ExpObjOp exp);

}
