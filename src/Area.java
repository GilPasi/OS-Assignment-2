import java.util.ArrayList;

public abstract class Area {
	ArrayList <CowThread> cowsPool = new ArrayList<CowThread>();
	int  capcacity;
	boolean capacityLimited;
	
	
	protected Area(int capcacity) {
		capacityLimited = capcacity != -1;//-1 signify no capacity limit
		this.capcacity = capcacity;
	}
	
	
	public void addCow(CowThread newCow, String area){
		
		if(capacityLimited) {
			
			if(cowsPool.size() < capcacity)cowsPool.add(newCow);
			
			else System.out.println("Area " + area + " reached maximum capacity");			
		}
		else cowsPool.add(newCow);
	}
	
	
	public void removeCow(CowThread remCow){cowsPool.remove(remCow);}
}
