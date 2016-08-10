import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemoryController {

	Scanner in = new Scanner(System.in);
	
	/* the number of page frames the process is allowed to use (a <= nf <= 30) */
	private int nf;
	/* the number of pages in the process (1 <= np <= 300) */
	private int np;
	/* the length of the reference string (1 <= nr <= 5000) */
	private int nr;
	/* the seed for the rand random number generator if S > 0
	 * if S = 0, then the reference string will be explicitly specified in the input data
	 */
	private int s;
	/* the mean value of the normal distribution from which the nr random page numbers for the
	 * reference string are chosen
	 */
	private double m;
	/* the standard deviation of the normal distribution */
	private double sd;
	/* array which holds the reference string */
	List<Integer> references;
	/* random object from the  rnorm class */
	rnorm rando;
	/* simulation / case number */
	int runCount = 0;
	
	public MemoryController() {
		
	}
	
	private void parseInput() throws IOException {
		references = new ArrayList<Integer>();
		nf = in.nextInt();
		if(nf == 0)
			throw new IOException();
		np = in.nextInt();
		nr = in.nextInt();
		s = in.nextInt();
		if ( s == 0 )
		{
			m = -1;
			sd = -1;
			for(int i = 0; i < nr; i++)
				references.add(i,in.nextInt());
		}
		else {
			rando.xsrand(s);
			m = in.nextDouble();
			sd = in.nextDouble();
			for(int i = 0; i < nr; i++) {
				int pageNum = (int) rando.rnormms(m, sd);
				if (pageNum < 1 || pageNum > np)
					i--;
				else 
					references.add(i, pageNum);
			}
		}
		
	}

	public void run() {
		while(true) {
			try {
			parseInput();
			} catch (IOException e) {
				System.exit(0);
			}
			System.out.printf("Case %d\n", ++runCount);
			Algorithm opt = new OPT(nf,np,nr,references);
			opt.compute();
			opt.print();
			Algorithm fifo = new FIFO(nf,np,nr,references);
			fifo.compute();
			fifo.print();
			Algorithm lru = new LRU(nf,np,nr,references);
			lru.compute();
			lru.print();  
			System.out.println(); 
		}
		
	}

}
