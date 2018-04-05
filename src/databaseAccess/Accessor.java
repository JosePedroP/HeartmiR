package databaseAccess;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Accessor {

	public static CorrelationCentral[] searchForGenes(SessionFactory sessionFactory, String[] inputids, double threshold, boolean useregex) {
		
		CorrelationCentral[] corrs = new CorrelationCentral[]{};
		
		Session s = null;
		
		try{
			s = sessionFactory.openSession();
		} catch(Exception e)
		{e.printStackTrace();}
		
		if(s==null) return corrs;
		
		try {
			Transaction tx = s.beginTransaction();
			try {
				
				String queryString;
				Query queryObject;
				
				if(!useregex)
				{
					queryString = "select gen from Genes as gen " +
						"where ((gen.probe in (:ids)) or (gen.symbol in (:ids)) or (gen.name in (:ids))) " +
						"and gen.corrs.size>0 and gen.geneexpressions.size>0";
					queryObject = s.createQuery(queryString).setParameterList("ids", inputids);
				}
				else
				{
					queryString = convertToSQLQueryGene(inputids);
					queryObject = s.createSQLQuery(queryString).addEntity("gen",Genes.class);
				}
				
				List l = queryObject.list();
				
				ArrayList<CorrelationCentral> tempc = new ArrayList<CorrelationCentral>();
				
//				corrs = new CorrelationCentral[l.size()];
				
				for(int y=0;y<l.size();y++)
				{
					Genes gen = (Genes)l.get(y);
//					corrs[y] = new CorrelationCentral(gen, threshold);
					CorrelationCentral cc = new CorrelationCentral(gen, threshold);
					
					if(cc.getCorrs().length>0) tempc.add(cc);
				}
				
				corrs = tempc.toArray(new CorrelationCentral[]{});
				
				tx.commit();
				
			} catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
			
		}	catch(Exception e)
		{e.printStackTrace();}
		finally
		{s.close();}
		
		return corrs;
	}

	protected static String deregularexpressionfiy(String regex) {
		
		String res = regex.replaceAll("\\", "\\\\").replaceAll("+", "\\+").replaceAll("\\?", "\\?").
			replaceAll("\\$", "\\$").replaceAll("\\(", "\\(").replaceAll("\\)", "\\)").replaceAll("\\[", "\\[").
			replaceAll("\\]", "\\]").replaceAll("\\#", "\\#").replaceAll("\\|", "\\|").replaceAll("\\.", "\\.").
			replaceAll("\\{", "\\{").replaceAll("\\}", "\\}").replaceAll("\\^", "\\^");
		
		return res;
	}
	
	protected static String convertToSQLQueryMICRNA(String[] inputids) {
		String res = "SELECT {mic.*} FROM microrna AS {mic} WHERE";
		//TODO: Take note potential vulenrability if MYSQL valid regex is suplied, probably not worth it the trouble of taking care of
		for(int i=0;i<inputids.length;i++)
		{
//			String idt = processId(inputids[i]);
			String idt = inputids[i];

			if(i>0) res += " OR";
			res += " ver20 REGEXP '"+idt+"' OR affyid REGEXP '"+idt+"'";
		}
		
		return res;
	}
	
	protected static String convertToSQLQueryMICRNANoUserRegex(String[] inputids) {
		String res = "SELECT {mic.*} FROM microrna AS {mic} WHERE";
		//TODO: Take note potential vulenrability if MYSQL valid regex is suplied, probably not worth it the trouble of taking care of
		for(int i=0;i<inputids.length;i++)
		{
//			String idt = processId(inputids[i]);
			String idt = inputids[i];

			if(i>0) res += " OR";
			
			if(idt.toLowerCase().matches("^(mir|let)-[0-9]+([a-b]?)"))
			{
				idt = "^"+idt+"[^0-9]";
				res += " ver20 REGEXP '"+idt+"'";
			}
			else res += " ver20 = '"+idt+"' OR affyid = '"+idt+"'";
		}
		
//		System.out.println(res);
		
		return res;
	}
	
	protected static String convertToSQLQueryGene(String[] inputids) {
		String res = "SELECT {gen.*} FROM genes AS {gen} WHERE";
		//TODO: Take note potential vulenrability if MYSQL valid regex is suplied, probably not worth it the trouble of taking care of
		for(int i=0;i<inputids.length;i++)
		{
//			String idt = processId(inputids[i]);
			String idt = inputids[i];
			
			if(i>0) res += " OR";
			res += " probe REGEXP '"+idt+"' OR symbol REGEXP '"+idt+"' OR name REGEXP '"+idt+"'";
		}
		
		return res;
	}
	
	public static String processId(String id)
	{
//		return id.replaceAll("\\*", ".*");
		String res = id;
		if(!id.startsWith("^")) res = ".*"+res;
		if(!id.endsWith("$")) res = res+".*";
		
		return res;
	}
	
	public static boolean matchTwo(String[] bigs, String small) {
		
		boolean res = false;
		String psmall = processId(small);
		
		for(int i=0;!res && i<bigs.length;i++)
		{
			res = bigs[i].matches(psmall);
		}
		
		return res;
	}
	
	
	public static CorrelationCentral[] searchForMicroRNAs(SessionFactory sessionFactory, String[] inputids, double threshold, boolean useregex) {
		
		CorrelationCentral[] corrs = new CorrelationCentral[]{};
		
		Session s = null;
		
		try{
			s = sessionFactory.openSession();
		} catch(Exception e)
		{e.printStackTrace();}
		
		if(s==null) return corrs;
		
		try {
			Transaction tx = s.beginTransaction();
			try {
				//TODO: put back right
				/* 
				
				*/
				
//				
//				System.out.println(convertToSQLQueryMICRNA(inputids));
//				String queryString = "select {mic.*} from microrna as {mic} "
//					+ "where ver20 REGEXP 'mir-10.*'";
				
				String queryString;
				Query queryObject;
				
				if(!useregex)
				{
//					queryString = "select mic from Microrna as mic " +
//						"where ((mic.ver20 in (:ids)) or (mic.affyid in (:ids)))) " +
//						"and mic.corrs.size>0 and mic.micrornaexpressions.size>0";
//					queryObject = s.createQuery(queryString).setParameterList("ids", inputids);
					queryString = convertToSQLQueryMICRNANoUserRegex(inputids);
					queryObject = s.createSQLQuery(queryString).addEntity("mic",Microrna.class);
				}
				else
				{
					queryString = convertToSQLQueryMICRNA(inputids);
					queryObject = s.createSQLQuery(queryString).addEntity("mic",Microrna.class);
				}
				
				List l = queryObject.list();
				
				ArrayList<CorrelationCentral> tempc = new ArrayList<CorrelationCentral>();
				
//				corrs = new CorrelationCentral[l.size()];
				
				for(int y=0;y<l.size();y++)
				{
					Microrna mi = (Microrna)l.get(y);
//					corrs[y] = new CorrelationCentral(mi, threshold);
					CorrelationCentral cc = new CorrelationCentral(mi, threshold);
					
					if(cc.getCorrs().length>0) tempc.add(cc);
				}
				
				corrs = tempc.toArray(new CorrelationCentral[]{});
				
				tx.commit();
				
			} catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
			
		}	catch(Exception e)
		{e.printStackTrace();}
		finally
		{s.close();}
		
		return corrs;
	}
	
	public static CorrelationCentral[] searchForBoth(SessionFactory sessionFactory, String[] inputids, double threshold, boolean useregex) {
		
		CorrelationCentral[] corrs = new CorrelationCentral[]{};
		
		Session s = null;
		
		try{
			s = sessionFactory.openSession();
		} catch(Exception e)
		{e.printStackTrace();}
		
		if(s==null) return corrs;
		
		try {
			ArrayList<CorrelationCentral> temp = new ArrayList<CorrelationCentral>();
			
			Transaction tx = s.beginTransaction();
			try {
				
				String queryString;
				Query queryObject;
				
				if(!useregex)
				{
					queryString = "select gen from Genes as gen " +
						"where ((gen.probe in (:ids)) or (gen.symbol in (:ids)) or (gen.name in (:ids))) " +
						"and gen.corrs.size>0 and gen.geneexpressions.size>0";
					queryObject = s.createQuery(queryString).setParameterList("ids", inputids);
				}
				else
				{
					queryString = convertToSQLQueryGene(inputids);
					queryObject = s.createSQLQuery(queryString).addEntity("gen",Genes.class);
				}
				
				List l = queryObject.list();
				
				corrs = new CorrelationCentral[l.size()];
				
				for(int y=0;y<l.size();y++)
				{
					Genes gen = (Genes)l.get(y);
//					corrs[y] = new CorrelationCentral(gen);

					CorrelationCentral cc = new CorrelationCentral(gen, threshold);
					
					if(cc.getCorrs().length>0)  temp.add(cc);
				}
				/*
				
				queryString = "select mic from Microrna as mic " +
					"where ((mic.ver20 in (:ids)) or (mic.affyid in (:ids)) or (mic.tidarraydesgin in (:ids))) " +
					"and mic.corrs.size>0 and mic.micrornaexpressions.size>0";
				*/
//				queryString = "select mic from Microrna as mic " +
//					"where ((mic.ver20 in (:ids)) or (mic.affyid in (:ids))) " +
//					"and mic.corrs.size>0 and mic.micrornaexpressions.size>0";
//				
//				queryObject = s.createQuery(queryString).setParameterList("ids", inputids);;

//				System.out.println("useregex "+useregex);
				
				if(!useregex)
				{
//					queryString = "select mic from Microrna as mic " +
//						"where ((mic.ver20 in (:ids)) or (mic.affyid in (:ids)))) " +
//						"and mic.corrs.size>0 and mic.micrornaexpressions.size>0";
//					queryObject = s.createQuery(queryString).setParameterList("ids", inputids);
					queryString = convertToSQLQueryMICRNANoUserRegex(inputids);
					queryObject = s.createSQLQuery(queryString).addEntity("mic",Microrna.class);
				}
				else
				{
					queryString = convertToSQLQueryMICRNA(inputids);
					queryObject = s.createSQLQuery(queryString).addEntity("mic",Microrna.class);
				}
				
				l = queryObject.list();
				
				corrs = new CorrelationCentral[l.size()];
				
				for(int y=0;y<l.size();y++)
				{
					Microrna mi = (Microrna)l.get(y);
//					corrs[y] = new CorrelationCentral(mi);
//					temp.add(new CorrelationCentral(mi));

					CorrelationCentral cc = new CorrelationCentral(mi, threshold);
					
					if(cc.getCorrs().length>0)  temp.add(cc);
				}
				
				tx.commit();
				
				corrs = temp.toArray(new CorrelationCentral[]{}); 
				
			} catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
			
		}	catch(Exception e)
		{e.printStackTrace();}
		finally
		{s.close();}
		
		return corrs;
	}
	
	public static String[] searchForCorr(SessionFactory sessionFactory, String gene, String mirna) {
		
		String[] res = null;
		
		Session s = null;
		
		try{
			s = sessionFactory.openSession();
		} catch(Exception e)
		{e.printStackTrace();}
		
		if(s==null) return res;
		
		try {
			ArrayList<CorrelationCentral> temp = new ArrayList<CorrelationCentral>();
			
			Transaction tx = s.beginTransaction();
			try {
				
				String queryString = "select cor from Corr as cor join cor.genes as gen join cor.microrna as mic " +
					"where ((gen.probe in (:gene)) or (gen.symbol in (:gene)) or (gen.name in (:gene))) " +
					"and ((mic.ver20 in (:mirna)) or (mic.affyid in (:mirna)) or (mic.tidarraydesgin in (:mirna)))";
				
				Query queryObject = s.createQuery(queryString).setParameterList("gene", new String[]{gene}).setParameterList("mirna", new String[]{mirna});
				
				List l = queryObject.list();
				
				if(l.size()>0)
				{
					res = new String[11];
					
					Corr cr = (Corr)l.get(0);
					
					CorrelationObject gn = new CorrelationObject(cr.getGenes());
					CorrelationObject mn = new CorrelationObject(cr.getMicrorna());
					
					String plot = "";
					String plot1 = "";
					String plot2 = "";
					
					String[] tem = gn.getSamples();
					double[] ten = gn.getExpression();
					double[] tez = mn.getExpression();
					
					for(int k=0;k<tem.length;k++)
					{
						if(k==0)
						{
							plot = tem[k];
							plot1 += ten[k];
							plot2 += tez[k];
						}
						else
						{
							plot += "<,>"+tem[k];
							plot1 += "<,>"+ten[k];
							plot2 += "<,>"+tez[k];
						}
					}
					
					plot += "<|>"+gn.getIdM()+"<:>"+plot1+"<|>"+mn.getIdM()+"<:>"+plot2;
					
					res[0] = ""+cr.getKendall();
					res[1] = ""+cr.getKendalldev();
					res[2] = cr.getGenes().getProbe();
					res[3] = cr.getGenes().getSymbol();
					res[4] = cr.getGenes().getName();
					res[5] = ""+cr.getGenes().getGeneId();
					res[6] = cr.getMicrorna().getVer20();
					res[7] = cr.getMicrorna().getAffyid();
					res[8] = cr.getMicrorna().getTidarraydesgin();
					res[9] = plot;
					res[10] = cr.getMicrorna().getMirbase();
				}
				
				tx.commit();
				
			} catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
			
		}	catch(Exception e)
		{e.printStackTrace();}
		finally
		{s.close();}
		
		return res;
	}
}