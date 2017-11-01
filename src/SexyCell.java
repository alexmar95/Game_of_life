
public class SexyCell extends Cell {

	private boolean canMakeLove=true;
	
	public SexyCell(World w) {
		super(w);
	}
	public void readyToBang() {
		canMakeLove=true;
	}
	public void makeLove(){
		canMakeLove=true;
		super.makeLove();
	}
	public void reproduce() {
		super.reproduce();
		if(canMakeLove==false)
			return;
		SexyCell toLove = mate(this);
		if(toLove == null) {
			canMakeLove = false;
		}else {
			toLove.makeLove();
			this.makeLove();
			System.out.println(this+ " justMadeLove " +toLove);
			giveBirth(this);
		}
	}
}
