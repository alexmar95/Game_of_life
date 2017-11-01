

public abstract class Cell implements Runnable {

	private final static int T_full = 1000;
	private final static int T_starve = 3000;
	private World world;
	private long lastMeal;
	private int size = 0;
	
	public Cell(World w) {
		world = w;
		lastMeal = System.currentTimeMillis() - T_full;
	}
	
	//TODO
	public boolean isAlive() {
		return System.currentTimeMillis()-lastMeal<T_full + T_starve;
	}
	
	private void eat() {
		synchronized(world.foodSema()){
			if(!isAlive()) {
				return;
			}
			if(world.getFood()) {
				lastMeal = System.currentTimeMillis();
				size++;
				System.out.println(this+ " yumyum size=" +size);
			}	
		}
	}
	
	public void resetSize() {
		
	}
	
	public void reproduce() {
		if(!isAlive()) {
			return;
		}
	}
	
	public void setHungry() {
		lastMeal = System.currentTimeMillis() - T_full;
	}
	public void giveBirth(SexyCell c) {
		world.addCell(new SexyCell(world));
	}
	public void giveBirth(UnsexyCell c) {
		world.addCell(new UnsexyCell(world));
		//System.out.println(this+ " gata");
	}
	private void die() {
		System.out.println(this+ " goodbye cruel world!");
		world.removeCell(this);
	}
	public void fuck() {
		size=0;
	}
	//TODO
	public boolean hungry() {
		return System.currentTimeMillis()-lastMeal > T_full;
	}
	
	public boolean horny() {
		return size>=10 && !hungry();
	}
	
	public SexyCell mate(SexyCell c){
		return world.mate(c);
	}
	
	@Override
	public void run() {
		System.out.println(this + " started");
		while(isAlive()) {
			if(hungry()) {
				eat();
			}
			if(horny()) {
				reproduce();
			}
		}
		die();
	}

}
