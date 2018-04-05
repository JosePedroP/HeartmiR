package databaseAccess;

import java.util.ArrayList;
import java.util.Arrays;

import beans.SortObject;

public class CorrelationCentral extends CorrelationObject{

	//Expression
	protected Correlations[] corrs;
	
	public CorrelationCentral(Genes gen) {
		super(gen);
		
		Object[] corrs = gen.getCorrs().toArray();
		this.corrs = new Correlations[corrs.length];
		
		SortObject<Correlations>[] tempa = new SortObject[corrs.length];
		
		for(int i=0;i<corrs.length;i++)
		{
			Corr corr = (Corr)corrs[i];
			Correlations ocorr = new Correlations(corr.getMicrorna(), corr);
			tempa[i] = new SortObject<Correlations>(ocorr.getKendall(), ocorr);
		}
		
		Arrays.sort(tempa);
		
		for(int i=0;i<tempa.length;i++)
		{
			this.corrs[i] = tempa[i].getData();
		}
		
		/*for(int i=0;i<corrs.length;i++)
		{
			Corr corr = (Corr)corrs[i];
			this.corrs[i] = new Correlations(corr.getMicrorna(), corr);
		}*/
	}
	
	public CorrelationCentral(Genes gen, double threshold) {
		super(gen);
		
		Object[] corrs = gen.getCorrs().toArray();
//		this.corrs = new Correlations[corrs.length];
		
		ArrayList<SortObject<Correlations>> temppa = new ArrayList<SortObject<Correlations>>();
		
		for(int i=0;i<corrs.length;i++)
		{
			Corr corr = (Corr)corrs[i];
			
			double k1 = corr.getKendall();
			double k2 = corr.getKendalldev();
			
			if(k1<0) k1 = 0-k1;
			if(k2<0) k2 = 0-k2;
			
			if(k1>=threshold || k2>=threshold)
			{

				Correlations ocorr = new Correlations(corr.getMicrorna(), corr);
				temppa.add(new SortObject<Correlations>(ocorr.getKendall(), ocorr));
			}
		}
		
		SortObject<Correlations>[] tempa = temppa.toArray(new SortObject[]{});
		
		Arrays.sort(tempa);
		this.corrs = new Correlations[tempa.length];
		for(int i=0;i<tempa.length;i++)
		{
			this.corrs[i] = tempa[i].getData();
		}
		
	}
	
	public CorrelationCentral(Microrna mic) {
		super(mic);
		
		Object[] mixs = mic.getMicrornaexpressions().toArray();
		
		/*this.samples = new String[mixs.length];
		this.expression = new double[mixs.length];
		for(int i=0;i<mixs.length;i++)
		{
			Micrornaexpression mix = (Micrornaexpression)mixs[i];		
			this.samples[i] = mix.getSamples().getName();		
			this.expression[i] = mix.getExpression();
		}*/
		
		Object[] corrs = mic.getCorrs().toArray();
		this.corrs = new Correlations[corrs.length];
		
		SortObject<Correlations>[] tempa = new SortObject[corrs.length];
		
		for(int i=0;i<corrs.length;i++)
		{
			Corr corr = (Corr)corrs[i];
			Correlations ocorr = new Correlations(corr.getGenes(), corr);
			tempa[i] = new SortObject<Correlations>(ocorr.getKendall(), ocorr);
		}
		
		Arrays.sort(tempa);
		
		for(int i=0;i<tempa.length;i++)
		{
			this.corrs[i] = tempa[i].getData();
		}
		
		/*for(int i=0;i<corrs.length;i++)
		{
			Corr corr = (Corr)corrs[i];
			this.corrs[i] = new Correlations(corr.getGenes(), corr);
		}*/
	}
	
	public CorrelationCentral(Microrna mic, double threshold) {
		super(mic);
		
//		Object[] mixs = mic.getMicrornaexpressions().toArray();
		
		Object[] corrs = mic.getCorrs().toArray();
//		this.corrs = new Correlations[corrs.length];
		
//		SortObject<Correlations>[] tempa = new SortObject[corrs.length];
		
		ArrayList<SortObject<Correlations>> temppa = new ArrayList<SortObject<Correlations>>();
		
		for(int i=0;i<corrs.length;i++)
		{
			Corr corr = (Corr)corrs[i];
			
			double k1 = corr.getKendall();
			double k2 = corr.getKendalldev();
			
			if(k1<0) k1 = 0-k1;
			if(k2<0) k2 = 0-k2;
			
			if(k1>=threshold || k2>=threshold)
			{
				Correlations ocorr = new Correlations(corr.getGenes(), corr);
				temppa.add(new SortObject<Correlations>(ocorr.getKendall(), ocorr));
			}
			
		}
		
		SortObject<Correlations>[] tempa = temppa.toArray(new SortObject[]{});
		
		Arrays.sort(tempa);
		this.corrs = new Correlations[tempa.length];
		for(int i=0;i<tempa.length;i++)
		{
			this.corrs[i] = tempa[i].getData();
		}
		
	}
	
	public Correlations[] getCorrs() {
		return corrs;
	}
	
}
