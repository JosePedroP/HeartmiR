package databaseAccess;

import org.apache.commons.math3.util.Precision;

public class Correlations extends CorrelationObject{
	
	protected Double scoremrnaorg;
	protected Double scorepita;
	protected Double scoremirdb;
	protected Double scoretscan;
	protected double kendall;
	protected double kendallDev;
	protected String experiments;
	protected String supporttype;
	protected String referencespmid;
	protected double ranking;
	protected int rank;
	private String pubmedid;
	private boolean hd;
	private boolean tf;
	
	public Correlations(Genes gen, Corr corr)
	{
		super(gen);
		this.setCorr(corr);
	}
	
	public Correlations(Microrna mic, Corr corr)
	{
		super(mic);
		this.setCorr(corr);
	}
	
	protected void setCorr(Corr corr)
	{
		this.scoremrnaorg = corr.getScoremrnaorg();
		this.scorepita = corr.getScorepita();
		this.scoremirdb = corr.getScoremirdb();
		this.scoretscan = corr.getScoretscan();
		this.kendall = Precision.round(corr.getKendall(),3);
		this.kendallDev = Precision.round(corr.getKendalldev(),3);
		this.experiments = corr.getExperiments();
		this.supporttype = corr.getSupporttype();
		this.referencespmid = corr.getReferencespmid();
		this.ranking = corr.getRanking();
		this.rank = corr.getRank();
		this.pubmedid = corr.getPubmedid();
		this.hd = corr.getHd().booleanValue();
		this.tf = corr.getTf().booleanValue();
	}

	
	protected String isNA(Double d)
	{
		if(d==null) return "-";
		else return ""+d.intValue();
	}
	
	public String getScoremrnaorg() {
		return isNA(scoremrnaorg);
	}

	public void setScoremrnaorg(String scoremrnaorg) {
	}

	public String getScorepita() {
		return isNA(scorepita);
	}

	public void setScorepita(String scorepita) {
	}

	public String getScoremirdb() {
		return isNA(scoremirdb);
	}

	public void setScoremirdb(String scoremirdb) {
	}

	public String getScoretscan() {
		return isNA(scoretscan);
	}

	public void setScoretscan(double scoretscan) {
	}
	
	public double getKendall() {
		return kendall;
	}

	public void setKendall(double kendall) {
		this.kendall = kendall;
	}

	public double getKendallDev() {
		return kendallDev;
	}

	public void setKendallDev(double kendallDev) {
		this.kendallDev = kendallDev;
	}

	public String getExperiments() {
		return experiments;
	}

	public void setExperiments(String experiments) {
		this.experiments = experiments;
	}

	public String getSupporttype() {
		return supporttype;
	}

	public void setSupporttype(String supporttype) {
		this.supporttype = supporttype;
	}

	public String getReferencespmid() {
		return referencespmid;
	}

	public void setReferencespmid(String referencespmid) {
		this.referencespmid = referencespmid;
	}

	public double getRanking() {
		return ranking;
	}

	public void setRanking(double ranking) {
		this.ranking = ranking;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getPubmedid() {
		return pubmedid;
	}

	public boolean isHd() {
		return hd;
	}

	public boolean isTf() {
		return tf;
	}
	
}
