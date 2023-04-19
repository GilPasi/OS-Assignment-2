
public class StrayArea extends Area{

	public StrayArea(int size) {
		super(size);
	}
	public void addCow (CowThread cow ) {super.addCow( cow, "walk");}
}
