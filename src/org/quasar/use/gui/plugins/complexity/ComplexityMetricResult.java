package org.quasar.use.gui.plugins.complexity;

public class ComplexityMetricResult implements IComplexityMetricResult {

	private int nnr;
	private int nan;
	private int wno;
	private int nnc;;
	private int nuca;
	private int nuco;
	private int wnn;
	private int dn;
	private int wco;

	public ComplexityMetricResult() {
	}

	public int getNNR() {
		return Math.max(nnr, 0);
	}

	public void setNNR(int nnr) {
		this.nnr = nnr;
	}

	public int getNAN() {
		return Math.max(nan, 0);
	}

	public void setNAN(int nan) {
		this.nan = nan;
	}

	public int getWNO() {
		return Math.max(wno, 0);
	}

	public void setWNO(int wno) {
		this.wno = wno;
	}

	public int getNNC() {
		return Math.max(nnc, 0);
	}

	public void setNNC(int nnc) {
		this.nnc = nnc;
	}

	public int getNUCA() {
		return Math.max(nuca, 0);
	}

	public void setNUCA(int nuca) {
		this.nuca = nuca;
	}

	public int getNUCO() {
		return Math.max(nuco, 0);
	}

	public void setNUCO(int nuco) {
		this.nuco = nuco;
	}

	public int getWNN() {
		return Math.max(wnn, 0);
	}

	public void setWNN(int wnn) {
		this.wnn = wnn;
	}

	public int getDN() {
		return Math.max(dn, 0);
	}

	public void setDN(int dn) {
		this.dn = dn;
	}

	public int getWCO() {
		return Math.max(wco, 0);
	}

	public void setWCO(int wco) {
		this.wco = wco;
	}

}
