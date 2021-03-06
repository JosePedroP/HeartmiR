package databaseAccess;

// Generated 7/nov/2016 14:40:34 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Microrna generated by hbm2java
 */
public class Microrna implements java.io.Serializable {

	private Integer id;
	private String ver20;
	private String affyid;
	private String tidarraydesgin;
	private String mirbase;
	private Set micrornaexpressions = new HashSet(0);
	private Set corrs = new HashSet(0);

	public Microrna() {
	}

	public Microrna(String ver20, String affyid, String tidarraydesgin) {
		this.ver20 = ver20;
		this.affyid = affyid;
		this.tidarraydesgin = tidarraydesgin;
	}

	public Microrna(String ver20, String affyid, String tidarraydesgin,
			String mirbase, Set micrornaexpressions, Set corrs) {
		this.ver20 = ver20;
		this.affyid = affyid;
		this.tidarraydesgin = tidarraydesgin;
		this.mirbase = mirbase;
		this.micrornaexpressions = micrornaexpressions;
		this.corrs = corrs;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVer20() {
		return this.ver20;
	}

	public void setVer20(String ver20) {
		this.ver20 = ver20;
	}

	public String getAffyid() {
		return this.affyid;
	}

	public void setAffyid(String affyid) {
		this.affyid = affyid;
	}

	public String getTidarraydesgin() {
		return this.tidarraydesgin;
	}

	public void setTidarraydesgin(String tidarraydesgin) {
		this.tidarraydesgin = tidarraydesgin;
	}

	public String getMirbase() {
		return this.mirbase;
	}

	public void setMirbase(String mirbase) {
		this.mirbase = mirbase;
	}

	public Set getMicrornaexpressions() {
		return this.micrornaexpressions;
	}

	public void setMicrornaexpressions(Set micrornaexpressions) {
		this.micrornaexpressions = micrornaexpressions;
	}

	public Set getCorrs() {
		return this.corrs;
	}

	public void setCorrs(Set corrs) {
		this.corrs = corrs;
	}

}
