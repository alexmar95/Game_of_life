
public class UnsexyCell extends Cell {
	
	public UnsexyCell(World w) {
		super(w);
	}
	
	public void reproduce() {
		super.reproduce();
		setHungry();
		System.out.println(this+ " unsexySexyTime");
		giveBirth(new UnsexyCell(world));
	}
	
}
