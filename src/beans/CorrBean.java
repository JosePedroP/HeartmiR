package beans;

import java.io.OutputStream;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import databaseAccess.Accessor;
import databaseAccess.CorrelationCentral;
import databaseAccess.Correlations;
import databaseAccess.StartUpBean;

@ManagedBean
@RequestScoped
public class CorrBean {

	@ManagedProperty(value = "#{startUpBean}")
	protected StartUpBean startUpBean;

	protected CorrelationCentral[] corrs = new CorrelationCentral[]{};
	protected String[] seachIds = new String[]{"Gata4","miR-22-5p"};
	
	protected boolean notGene = false;
	
	protected String downloadTable = "";
	
	protected boolean render = false;
	protected boolean nomatch = false;
	
	protected String mrna = "Smoc2";
	protected String mirna = "miR-19b-3p";
	protected String[] corsearch = new String[]{"1","1","1","1","1","1","1","1","1","1"};
	
	protected String threshold = "0.4";
	
	protected String notFound = "";
	
	protected boolean useregex = false;
	
	public boolean isUseregex() {
		return useregex;
	}

	public void setUseregex(boolean useregex) {
		this.useregex = useregex;
	}

	public String searchForCorr() {
		String[] corsearch = Accessor.searchForCorr(this.startUpBean.getSessionFactory(), this.mrna, this.mirna);
		
		if(corsearch!=null)
		{
			this.render = true;
			this.nomatch = false;
			this.corsearch = corsearch;
		}
		else
		{
			this.render = false;
			this.nomatch = true;
		}
		
		return null;
	}
	
	public String searchForGenes() {
		
		double thres = 0;
		
		try {
			thres = new Double(this.threshold); 
		} catch(Exception e)
		{thres = 0;}
		
		this.corrs = Accessor.searchForGenes(this.startUpBean.getSessionFactory(), this.seachIds, thres, this.useregex);
		this.notFound = getTheUnfound(this.seachIds, this.corrs);
		fillDownloadTable();
		return null;
	}
	
	public String searchForMicroRNAs() {
		
		double thres = 0;
		
		try {
			thres = new Double(this.threshold); 
		} catch(Exception e)
		{thres = 0;}
		
		this.corrs = Accessor.searchForMicroRNAs(this.startUpBean.getSessionFactory(), this.seachIds, thres, this.useregex);
		this.notFound = getTheUnfound(this.seachIds, this.corrs);
		fillDownloadTable();
		return null;
	}
	
	public String searchForAll() {
		
		double thres = 0;
		
		try {
			thres = new Double(this.threshold); 
		} catch(Exception e)
		{thres = 0;}
		
		this.corrs = Accessor.searchForBoth(this.startUpBean.getSessionFactory(), this.seachIds, thres, this.useregex);
		this.notFound = getTheUnfound(this.seachIds, this.corrs);
		fillDownloadTable();
		return null;
	}
	
	
	protected String getTheUnfound(String[] ids, CorrelationCentral[] corrs)
	{
		String res = "";
		boolean first = true;
		
		ArrayList<String> temp = new ArrayList<String>();
		
		for(int a=0;a<ids.length;a++)
		{
			boolean stop = false;
			for(int b=0;!stop && b<corrs.length;b++)
			{
				if(corrs[b].match(ids[a])) stop = true;
			}
			if(!stop)
			{
				temp.add(ids[a]);
				
				/*
				if(first)
				{
					first = false;
					res = "No matches for: "+ids[a];
				}
				else if(a==(ids.length-1))
				{
					res +=" and "+ids[a];
				}
				else
				{
					res +=","+ids[a];
				}*/
			}
		}
		
		for(int a=0;a<temp.size();a++)
		{
			if(a==0) res = "No matches for: "+temp.get(a);
			else if(a==(temp.size()-1)) res +=" and "+temp.get(a);
			else res +=","+temp.get(a);
		}
		
		return res;
	}
	
	protected void fillDownloadTable() {
		
		this.downloadTable = "";
		
		if(corrs.length>0)
		{
			this.render = true;
			this.nomatch = false;
			for(int i=0;i<corrs.length;i++)
			{
				CorrelationCentral cor = corrs[i];

				if(i>0) this.downloadTable += "<.>";
				
				if(cor.isGene())
				{
					this.downloadTable += "Probe: "+cor.getId1()+"<;>Gene Symbol: "+
						cor.getId2()+"<;>Name: "+cor.getId3()+"<;>Gene Id: "+cor.getGeneId()+
						"<|>Micro RNA<;>";
				}
				else
				{
					this.downloadTable += "Ver20: "+cor.getId1()+"<;>Affyid: "+cor.getId2()+
						"<;>Tidarraydesgin: "+cor.getId3()+"<|>Gene<;>";
				}
				
				Correlations[] crrs = cor.getCorrs();
				this.downloadTable += "Support Type<;>PubMed<;>Heart development<;>Transcription Factor<;>Kendall<;>Kendall Dev";
				
//				System.out.println("crrs.length: "+crrs.length);
				
				for(Correlations crr: crrs)
				{
					String ht = "-";
					String tf = "-";
					
					if(crr.isHd()) ht = "+";
					if(crr.isTf()) tf = "+";
					
					this.downloadTable += "<|>"+crr.getIdM()+"<;>"+crr.getSupporttype()+"<;>"+crr.getPubmedid()+"<;>"+ht+"<;>"+tf+"<;>"+crr.getKendall()+"<;>"+crr.getKendallDev();
				}
			}
		}
		else
		{
			this.render = false;
			this.nomatch = true;
		}
		
	}
	
	public boolean isGene() {
		if(this.corrs.length==0) return false;
		else return this.corrs[0].isGene();
	}

	public void setGene(boolean gene) {
	}

	public boolean isProbe() {
		if(this.corrs.length==0) return true;
		else return this.corrs[0].isGene()==false;
	}

	public void setProbe(boolean probe) {
	}
	
	public CorrelationCentral[] getCorrs() {
		return corrs;
	}

	public void setCorrs(CorrelationCentral[] corrs) {
	}

	public StartUpBean getStartUpBean() {
		return startUpBean;
	}


	public void setStartUpBean(StartUpBean startUpBean) {
		this.startUpBean = startUpBean;
	}
	
	public String getSeachIds() {
		String res = "";
		
		for(int i=0;i<this.seachIds.length;i++)
		{
			if(i==0) res += this.seachIds[i];
			else res += ","+this.seachIds[i];
		}
		
		return res;
	}


	public void setSeachIds(String seachIds) {
		this.seachIds = seachIds.split(",|\\s+");
	}
	
	public String getHeatmapdata() {
		
		String res = "";
		String res2 = "";
		
		for(int i=0;i<this.corrs.length;i++)
		{
			if(i==0)
			{
				String[] samps = this.corrs[i].getSamples();
				for(int y=0;y<samps.length;y++)
				{
					if(y>0) res += "<,>";
					res += samps[y];
				}
			}
			
			res += "<|>"+this.corrs[i].getIdM()+"<:>"+this.corrs[i].getId1()+"<!>";
			
			double[] exprs = this.corrs[i].getExpression();
			for(int y=0;y<exprs.length;y++)
			{
				if(y>0) res += "<,>";
				res += exprs[y];
			}
			
			//part 2 add the correlationed elments data
			
			if(!res2.equals("")) res2+="<|>";
			
			Correlations[] crrs = this.corrs[i].getCorrs();
			

//			for(int y=0;y<crrs.length;y++)
			for(int y=crrs.length-1;y>=0;y--)
			{
//				if(y>0) res2 += "<!>";
				if(y!=crrs.length-1) res2 += "<!>";
				res2 += crrs[y].getIdM()+"<!>";
				
				double[] samps = crrs[y].getExpression();
				for(int z=0;z<samps.length;z++)
				{
					if(z>0) res2 += "<,>";
					res2 += samps[z];
				}
			}
		}
		
		
		res+="<*>"+res2;
		
		
		
//		System.out.println(res);
		
		return res;
	}
	
	public void setHeatmapdata(String heatmapdata) {
	}

	public String getDownloadTable() {
		return downloadTable;
	}

	public void setDownloadTable(String downloadTable) {
		this.downloadTable = downloadTable;
	}
	
	public String getText()
	{
//		System.out.println(this.downloadTable);
		
		try {
			String res = "";
			
			String[] tables = this.downloadTable.split("<\\.>");
			
			for(String table: tables)
			{
				String[] lines = table.split("<\\|>");
				
//				res += table+"\n";
				
				for(int i=0;i<lines.length;i++)
				{
					String[] line = lines[i].split("<;>");
					String sep;
					if(i>0) sep = "\t";
					else sep = "\n";
					
					for(int y=0;y<line.length;y++)
					{
						String wod = line[y];
						if(y>0) res += sep;
						res += wod;
					}
					res += "\n";

				}
				res += "\n";
			}
			
			byte[] file = res.getBytes();
			FacesContext fc = FacesContext.getCurrentInstance();
		    ExternalContext ec = fc.getExternalContext();
		    ec.responseReset();
		    ec.setResponseContentType("text/plain");
		    ec.setResponseContentLength(file.length);
		    ec.setResponseHeader("Content-Disposition", "attachment; filename=\"table.txt\"");
		    OutputStream output = ec.getResponseOutputStream();
		    output.write(file);
		    fc.responseComplete();
			
		} catch(Exception e)
		{e.printStackTrace();}
		
		return null;
	}

	public boolean isRender() {
		return render;
	}

	public void setRender(boolean render) {
		this.render = render;
	}

	public boolean isNomatch() {
		return nomatch;
	}

	public void setNomatch(boolean nomatch) {
		this.nomatch = nomatch;
	}

	public String getMrna() {
		return mrna;
	}

	public void setMrna(String mrna) {
		this.mrna = mrna;
	}

	public String getMirna() {
		return mirna;
	}

	public void setMirna(String mirna) {
		this.mirna = mirna;
	}

	public String[] getCorsearch() {
		return corsearch;
	}

	public void setCorsearch(String[] corsearch) {
	}

	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public String getNotFound() {
		return notFound;
	}

	public void setNotFound(String notFound) {
	}
	
}
