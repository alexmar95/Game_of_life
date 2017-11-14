import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class World {
	private ArrayList<Cell> cellCulture = new ArrayList<Cell>();
	private int foodBits[];
	private Object ctSema[];
	private Semaphore foodSema ;
	private final int max = 1024;
	private Object sexSema = new Object();
	private SexyCell sexMate;
	private Random rand = new Random(System.currentTimeMillis());
	private int containers;
	
	public World(int food, int no_containers) {
		containers=no_containers;
		foodSema = new Semaphore(max);
		foodBits = new int[no_containers];
		ctSema = new Object[no_containers];
		partitionFood(food, no_containers);
	}
	
	private void repartitionFood() {
		int sum=0;
		//foodSema.acquireUninterruptibly(max);
		for(int i=0;i<containers;i++) {
			sum+=foodBits[i];
		}
		partitionFood(sum, containers);
		//foodSema.release(max);
	}
	
	
	
	private void partitionFood(int food,int no_containers) {
		int chunk=food/no_containers;
		int remain=food%no_containers;
		for(int i=0;i<no_containers;i++) {
			ctSema[i] = new Object();
			if(i<remain)
				foodBits[i]=chunk+1;
			else 
				foodBits[i]=chunk;
			//System.out.println(foodBits[i]+" ");
		}
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
		int ct = rand.nextInt(containers);
		try {
			foodSema.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		synchronized(ctSema[ct]) {
			foodBits[ct]+= rand.nextInt(5)+1;
			System.out.println("More FOOD!!!: "+foodBits);
		}
		foodSema.release();
	}
	public Object foodSema() {
		return foodSema;
	}
	
	public boolean getFood() {
		int ct = rand.nextInt(containers);
		try {
			foodSema.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		synchronized (ctSema[ct]) {
			if(foodBits[ct]>0) {
				foodBits[ct]--;
				foodSema.release();
				return true;
			}else {
				foodSema.release();
				repartitionFood();
				return false;
			}
		}
	}
	
}
