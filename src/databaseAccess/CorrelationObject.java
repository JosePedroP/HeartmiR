package databaseAccess;

public class CorrelationObject {

	//Object id data
	protected int mainObjectDatabaseID;
	protected boolean isGene;
	protected String id1; //probe or ver20
	protected String id2; //symbol or affyid
	protected String id3; //name or tidarraydesgin
	protected int geneId; //only for gene
	protected String mirbase; //only for gene
	
	//Expression Data
	
	protected String[] samples;
	protected double[] expression;
	protected String[] order = {"E10.5","E11.5","E12.5","E13.5","E14.5","E15.5","E16.5","E17.5","E18.5","E19.5","Adult","Old"};
	
	public CorrelationObject(Genes gen) {
		super();
		this.mainObjectDatabaseID = gen.getId();
		this.isGene = true;
		this.id1 = gen.getProbe();
		this.id2 = gen.getSymbol();
		this.id3 = gen.getName();
		this.geneId = gen.getGeneId();
		this.mirbase = null;
		
		Object[] genx = gen.getGeneexpressions().toArray();
		
		this.samples = order;
		this.expression = new double[genx.length];
		for(int i=0;i<genx.length;i++)
		{
			Geneexpression gex = (Geneexpression)genx[i];
			boolean stop = false;
			for(int y=0;!stop && y<order.length;y++)
			{
				if(gex.getSamples().getName().equals(order[y]))
				{
					stop = true;
					this.expression[y] = gex.getExpression();
				}
			}	
			
		}
	}
	
	public CorrelationObject(Microrna mic) {
		super();
		this.mainObjectDatabaseID = mic.getId();
		this.isGene = false;
		this.id1 = mic.getVer20();
		this.id2 = mic.getAffyid();
		this.id3 = mic.getTidarraydesgin();
		this.geneId = -1;
		this.mirbase = mic.getMirbase();
		
		Object[] micx = mic.getMicrornaexpressions().toArray();
		
		this.samples = order;
		this.expression = new double[micx.length];
		for(int i=0;i<micx.length;i++)
		{
			Micrornaexpression gex = (Micrornaexpression)micx[i];
			boolean stop = false;
			for(int y=0;!stop && y<order.length;y++)
			{
				if(gex.getSamples().getName().equals(order[y]))
				{
					stop = true;
					this.expression[y] = gex.getExpression();
				}
			}	
			
		}
	}
	
	public int getMainObjectDatabaseID() {
		return mainObjectDatabaseID;
	}

	public boolean isGene() {
		return isGene;
	}

	public String getId1() {
		return id1;
	}

	public String getId2() {
		return id2;
	}

	public String getId3() {
		return id3;
	}

	public int getGeneId() {
		return geneId;
	}

	public String[] getSamples() {
		return samples;
	}

	public double[] getExpression() {
		return expression;
	}
	
	public String getIdM() {
		if(this.isGene) return id2;
		else return id1;
//		return id2;
	}

	public String getMirbase() {
		return mirbase;
	}
	
	public String getLink() {
		if(this.isGene) return ""+geneId;
		else return mirbase;
	}
	
	//Will have to change to handle regualar expressions
	public boolean match(String lid)
	{
		String id = lid.toLowerCase();
//		return 
//			(isGene && (id1.toLowerCase().equals(id) || id2.toLowerCase().equals(id) || id3.toLowerCase().equals(id))) || 
//			(!isGene && (id1.toLowerCase().equals(id) || id2.toLowerCase().equals(id)));
		
		String[] lids = null;
		
		if(isGene) lids = new String[]{id1.toLowerCase(), id2.toLowerCase(), id3.toLowerCase()};
		else lids = new String[]{id1.toLowerCase(), id2.toLowerCase()};
		
		return Accessor.matchTwo(lids, id);
		
	}
}
