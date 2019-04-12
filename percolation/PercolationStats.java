import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double STDDEV_LENGTH = 1.96;
    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;

    public PercolationStats(int n, int trials) {
        // perform trials independent experiments on an n-by-n grid

        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();

        double[] thresholds = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                // System.out.println(row + ", " + col);
                percolation.open(row, col);
            }
            double threshold = percolation.numberOfOpenSites() / (double) (n * n);
            thresholds[i] = threshold;
        }

        this.mean = StdStats.mean(thresholds);
        this.stddev = StdStats.stddev(thresholds);
        this.confidenceLo = this.mean - (STDDEV_LENGTH * this.stddev) / Math.sqrt(trials);
        this.confidenceHi = this.mean + (STDDEV_LENGTH * this.stddev) / Math.sqrt(trials);
    }

    public double mean() {
        // sample mean of percolation threshold
        return this.mean;
    }

    public double stddev() {
        // sample standard deviation of percolation threshold
        return this.stddev;
    }

    public double confidenceLo() {
        // low endpoint of 95% confidence interval
        return this.confidenceLo;
    }

    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return this.confidenceHi;
    }

    public static void main(String[] args) {
        // test client (described below)
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, t);
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println(
                "95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi()
                        + "]");
    }

}
