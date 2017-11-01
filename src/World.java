import java.util.ArrayList;
import java.util.Random;

public class World {
	private ArrayList<Cell> cellCulture = new ArrayList<Cell>();
	private int foodBits;
	private Object foodSema = new Object(),sexSema = new Object();
	private SexyCell sexMate;
	private Random rand = new Random(System.currentTimeMillis());
	
	public World(int food) {
		foodBits = food;
	}

	public SexyCell mate(SexyCell sexy) {
		synchronized(sexSema) {
			if(sexMate==null || !sexMate.isAlive()) {
				sexMate = sexy;
				return null;
			}else {
				sexMate.readyToBang();
				if(sexMate.hungry()) {
					sexMate = sexy;
					return null;
				}
				SexyCell tmp = sexMate;
				sexMate = null;
				return tmp;
			}
		}
	}
	public void startWorld(int sexy, int unsexy) {
		for(int i=0;i<sexy;i++) {
			cellCulture.add(new SexyCell(this));
		}
		for(int i=0;i<unsexy;i++) {
			cellCulture.add(new UnsexyCell(this));
		}
		for(Cell c : cellCulture) {
			new Thread(c).start();
		}
	}
	
	public synchronized void addCell(Cell c) {
		cellCulture.add(c);
		new Thread(c).start();
	}
	
	public void removeCell(Cell c) {
		synchronized(this) {
		cellCulture.remove(c);
		}
		synchronized(foodSema) {
			foodBits += rand.nextInt(5)+1;
			System.out.println("More FOOD!!!: "+foodBits);
		}
	}
	public Object foodSema() {
		return foodSema;
	}
	
	public boolean getFood() {
		if(foodBits>0) {
			foodBits--;
			return true;
		}else {
			return false;
		}
	}
	
	public void setFood(int f) {
		foodBits = f;
	}
	
}
