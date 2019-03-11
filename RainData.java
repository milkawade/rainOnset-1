package rainfall;

public class RainData {
	
	double raindata;
	boolean threshold;
	public RainData(double raindata, boolean threshold) {
		super();
		this.raindata = raindata;
		this.threshold = threshold;
	}
	public double getRaindata() {
		return raindata;
	}
	public void setRaindata(double raindata) {
		this.raindata = raindata;
	}
	public boolean isThreshold() {
		return threshold;
	}
	public void setThreshold(boolean threshold) {
		this.threshold = threshold;
	}
	

}
