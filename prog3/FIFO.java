import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class FIFO extends Algorithm {
	
	private int pageCount;
	private int pageFault;
	private double faultRate;
	List<Integer> pageTable;
	
	public FIFO(int nf, int np, int nr, List references) {
		super(nf, np, nr, references);
		pageTable = new ArrayList<Integer>(nf);
	}

	@Override
	public void compute() {
		pageCount = 0;
		for(int i = 0; i < nr; i++){
			if(pageTable.contains(references.get(i))) { //hit
				//System.out.printf("Hit on %d\n", references.get(i));
				continue;
			}
			else if(pageCount < nf){ //empty frame available
				pageTable.add(references.get(i));
				//System.out.printf("Loading %d \n", references.get(i));
				pageFault++;
				pageCount++;
			}
			else{
				//System.out.printf("Removing %d\n", pageTable.get(0));
				pageTable.remove(0);
				pageTable.add(references.get(i));
				//System.out.printf("Added %d\n", pageTable.get(7));
				pageFault++;
			}
		}
	faultRate = (double) pageFault / (double) nr;
	}

	@Override
	public void print() {
		System.out.printf("\tFIFO: %d faults, fault rate = %.3f\n", pageFault, faultRate);

	}

}