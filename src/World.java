import java.util.ArrayList;

public class World {
	private ArrayList<Cell> cellCulture;
	
	public World() {
		
	}
	
	public synchronized void addCell(Cell c) {
		cellCulture.add(c);
	}
	
	public synchronized void removeCell(Cell c) {
		cellCulture.remove(c);
	}
	
}
