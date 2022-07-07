import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    
    private final double[] fractions;
    private final int experimentCount;
    private static final double const96 = 1.96;
    
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Given n <= 0 || t <= 0");
        }
        experimentCount = trials;
        fractions = new double[experimentCount];
        for (int expNum = 0; expNum < experimentCount; expNum++) {
            Percolation pr = new Percolation(n);
            int opensites = 0;
            while (!pr.percolates()) {
                int l = StdRandom.uniform(1, n + 1);
                int j = StdRandom.uniform(1, n + 1);
                if (!pr.isOpen(l, j)) {
                    pr.open(l, j);
                    opensites++;
                }
            }
            double fraction = (double) opensites / (n * n);
            fractions[expNum] = fraction;
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(fractions);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(fractions);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean() - ((const96 * stddev()) / Math.sqrt(experimentCount));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean() + ((const96 * stddev()) / Math.sqrt(experimentCount));
    }

   // test client (see below)
   public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
   }
}
