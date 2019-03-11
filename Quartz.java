package rainfall;


public  class Quartz {

	
	double w, x, y,z;
	public Quartz(double w, double x, double y, double z) {
		this.w = w;
		this.x = x;
		this.y =y;
		this.z = z;
	}
	
	
	public double getW() {
		return w;
	}


	public void setW(double w) {
		this.w = w;
	}


	public String toString() {
		return "(" + w + "," + x + "," + y + "," + z +  ")";
	}
	public double getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public  double getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	
	
}