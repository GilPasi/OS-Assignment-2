
public class HayArea extends Area{

	public HayArea(int size) {
		super(size);
	}
	
	public void addCow (CowThread cow ) {super.addCow( cow, "hay");}
}
