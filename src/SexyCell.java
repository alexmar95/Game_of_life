
public class SexyCell extends Cell {

	private boolean canFuck=true;
	
	public SexyCell(World w) {
		super(w);
	}
	public void readyToFuck() {
		canFuck=true;
	}
	public void fuck(){
		canFuck=true;
		super.fuck();
	}
	public void reproduce() {
		super.reproduce();
		if(canFuck==false)
			return;
		SexyCell toFuck = mate(this);
		if(toFuck == null) {
			canFuck = false;
		}else {
			toFuck.fuck();
			this.fuck();
			System.out.println(this+ " justFucked " +toFuck);
			giveBirth(this);
		}
	}
}
