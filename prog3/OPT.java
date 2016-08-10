import java.util.ArrayList;
import java.util.List;

public class OPT extends Algorithm {
	
	/* int to hold furthest referenced page */
	private int lastRef;
	/* int to hold number of current pages */
	private int pageCount;
	/* page table array */
	List<Integer> pageTable;
	
	/* Constructor accepts all arguments necessary for an algorithm and initializes the page table array */
	public OPT(int nf, int np, int nr, List<Integer> references) {
		super(nf, np, nr, references);
		pageTable = new ArrayList<Integer>(nf);
	}
	
	/** This method computes the amount of page faults which would occur by using the OPT (optimal page
	 *  transfered) algorithm.
	 */
	@Override
	public void compute() {
		pageCount = 0;
		for(int inc = 0; inc < nr; inc++) {
			if(pageTable.contains(references.get(inc))) {
				continue;
			}
			else if(pageCount < nf) {
				pageTable.add(pageCount, references.get(inc));
				pageCount++;
				pageFault++;
			}
			else {
				/* index of the page table to be replaced */
				int index = 0;
				/* copy of the current page table */
				int currentPageTable[][] = new int[nf][2];
				for (int i = 0; i < nf; i++) {
					currentPageTable[i][0] = pageTable.get(i);
					currentPageTable[i][1] = -1;
				}
				/* copy of the current reference string */
				int currentRefString[] = new int[nr-inc];
				for (int i = 0; i < nr - inc; i++) {
					currentRefString[i] = references.get(inc+i);
				}
				/* fill the second row of the matrix with the amount of iterations until finding the corresponding
				 * first row's page number. If the reference is not found, currentPageTable[i][1] remains -1
				 */
				for (int i = 0; i < currentPageTable.length; i++) {
					lastRef = 0;
					for (int j = 0; j < currentRefString.length; j++) {
						if ( currentPageTable[i][0] == currentRefString[j] ) {
							currentPageTable[i][1] = lastRef;
							break;
						}
						else {
							lastRef++;
						}
					}
				}
				lastRef = 0;
				/* loop through the array to find the page referenced furthest away */
				for (int i = 0; i < currentPageTable.length; i++) {
					/* if the reference was never found, this page can be safely replaced */
					if (currentPageTable[i][1] == -1) {
						index = i;
						break;
					}
					/* otherwise, find the page with the furthest reference point to replace */
					else if (currentPageTable[i][1] >= lastRef) {
						lastRef = currentPageTable[i][1];
						index = i;
					}
				}
				/* replace the page table entry */
				pageTable.set(index, references.get(inc));
				/* log the fault */
				pageFault++;
			}
		}
		faultRate = (double) pageFault / (double) nr;
	}

	@Override
	public void print() {
		System.out.printf("\tOPT: %d faults, fault rate = %.3f\n", pageFault, faultRate);
		
	}
	
}
