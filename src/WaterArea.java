
public class WaterArea extends Area{

	public WaterArea(int size) {
		super(size);
	}
	
	public void addCow (CowThread cow ) {super.addCow( cow, "water");}

}
