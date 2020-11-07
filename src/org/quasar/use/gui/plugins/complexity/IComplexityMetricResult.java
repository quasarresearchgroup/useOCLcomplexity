package org.quasar.use.gui.plugins.complexity;

public interface IComplexityMetricResult {

	/**
	 * Number of Navigated Relationships: This metric counts the total number of
	 * navigated relationships in an expression. If a relationship is navigated
	 * twice, for example using different properties of a class or interface, this
	 * relationship is counted only once.
	 */
	int getNNR();

	void setNNR(int nnr);

	/**
	 * Number of Attributes referred through Navigations: The number of attributes
	 * referred through navigations in an expression.
	 */
	int getNAN();

	void setNAN(int nan);

	/**
	 * Weighted Number of referred Operations through navigations: The metric is
	 * defined as the sum of weighted operations (operations which are referred
	 * through navigations). For that reason we must consider the operation calls.
	 * The operations are weighted by the number of actual parameters (only the
	 * values of all in or in/out parameters are necessary to specify in the
	 * operation call) and the number of out parameters acceded (also considering
	 * the operation return type or result). The definition of the WNO metric is
	 * defined as ∑ ( 1 + | Par(m) | ) (1+ R + |Pout, in/out (m)|) m ∈
	 * N(expression), where: ● N(expression): Set of different operations referred
	 * through navigations. ● |Par(m)|: quantity of actual parameter of m. R stand
	 * for the m operation result and represents the value of 1. ● |Pout, in/out
	 * (m)|: quantity of out and in/out parameter of the m operation. Through a
	 * navigation it is possible to exclusively access one result as a time, this
	 * weighted formula giving a higher weight to those operations used many times
	 * to access different results.
	 */
	int getWNO();

	void setWNO(int wno);

	/**
	 * Number of Navigated Classes: This metric counts the total number of classes,
	 * association classes or interfaces to which an expression navigates to.
	 */
	int getNNC();

	void setNNC(int nnc);

	/**
	 * Weighted Number of Messages: The number of messages defined in an expression
	 * weighted by its actual parameters. The weighted operation is carried out
	 * according to: ∑ ( 1 + | Par(m) | ) m ∈ M(expression), where: ● M(expression):
	 * Set of different operations2 used through messaging in an expression. ●
	 * |Par(m)|: quantity of actual parameter of the m operation.
	 */
	int getWNM();

	void setWNM(int wnm);

	/**
	 * Number of Parameters whose Types are classes defined in the class diagram:
	 * This metric is specially used in pre- and post-condition expressions, and it
	 * counts the method parameters, and the return type (also called result) used
	 * in an expression, having each parameter/result a type representing a class or
	 * interface defined in the class diagram.
	 */
	int getNPT();

	void setNPT(int npt);

	/**
	 * Number of Utility Class Attributes used: The number of attributes belonging
	 * to a utility class used in an expression. Attributes are counted once if they
	 * belong to the same utility class and are also used more than once.
	 */
	int getNUCA();

	void setNUCA(int nuca);

	/**
	 * Number of Utility Class Operations used: The definition of this metric is
	 * analogous to the NUCA metric, but considering operations instead of
	 * attributes.
	 */
	int getNUCO();

	void setNUCO(int nuco);

	/**
	 * Weighted Number of Navigations: As we explain in the previous section, an
	 * operation collection is composed of an expression which is evaluated for each
	 * collection element, and if the evaluated expression involves a new navigation
	 * (or many) we will give a higher weight to the new navigation used inside the
	 * definition of the outermost expression.
	 */
	int getWNN();

	void setWNN(int wnn);

	/**
	 * Depth of Navigations: Given that in an OCL expression there can be many
	 * navigations regarding its definition, we build a tree of navigation using the
	 * class name to which we navigates to. We will only consider navigations
	 * starting from the contextual instance (from self). The root of the tree is
	 * the class name which self represents. Self is always the starting point of
	 * any expression (sometimes self can be left implicit in an expression), then
	 * we build a branch for each navigation, where each class we navigate to is a
	 * node in the branch. Nodes are connected by “navigation relations”. DN is the
	 * maximum depth of the tree.
	 */
	int getDN();

	void setDN(int dn);

	/**
	 * Weighted Number of Collection Operations: The collection operations used in
	 * the expression definition are weighted according to the level in which they
	 * are defined, so the metric is defined thus: WCO = ∑ weight of the level *
	 * number of collection operations of the level.
	 */
	int getWCO();

	void setWCO(int wco);

}
