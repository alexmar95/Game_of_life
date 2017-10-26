
public class SexyCell extends Cell {

	private boolean canFuck=true;
	
	public SexyCell(World w) {
		super(w);
	}
	public void readyToFuck() {
		canFuck=true;
	}
	public void reproduce() {
		super.reproduce();
		SexyCell toFuck = world.mate(this);
		if(toFuck == null) {
			canFuck = false;
		}else {
			toFuck.fuck();
			this.fuck();
			System.out.println(this+ " justFucked " +toFuck);
			giveBirth(new SexyCell(world));
		}
	}
}
