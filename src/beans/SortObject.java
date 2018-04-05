package beans;

public class SortObject<D> implements Comparable<SortObject<D>>{

	protected double compVal;
	protected D data;
	
	public SortObject(double compVal, D data) {
		super();
		this.compVal = compVal;
		this.data = data;
	}
	
	@Override
	public int compareTo(SortObject<D> o) {
		int res = 0;
		
		if(o.getCompVal()>this.compVal) res=-1;
		else if(o.getCompVal()<this.compVal) res=1;
		
		return res;
	}

	public double getCompVal() {
		return compVal;
	}

	public D getData() {
		return data;
	}
	
}
