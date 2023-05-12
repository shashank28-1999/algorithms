import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // perform independent trials on an n-by-n grid
    private double confidence = 1.96;
    private int nooftrails;
    private double[] trailres;

    public PercolationStats(int n, int t) {
        if (n < 1 || t < 1) {
            throw new IllegalArgumentException("N must be more than 0");
        }
        nooftrails = t;
        trailres = new double[nooftrails];
        for (int i = 0; i < nooftrails; i++) {
            Percolation p = new Percolation(n);
            boolean b[][] = new boolean[n][n];
            int row;
            int col;
            while (p.percolates() == false) {
                do {
                    row = StdRandom.uniformInt(0, n);
                    col = StdRandom.uniformInt(0, n);
                } while (b[row][col]);
                p.open(row + 1, col + 1);
                b[row][col] = true;
            }
            int open = p.numberOfOpenSites();
            double result = (double) open / (n * n);
            trailres[i] = result;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(trailres);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(trailres);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (confidence * stddev()) / Math.sqrt(nooftrails);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (confidence * stddev()) / Math.sqrt(nooftrails);
    }

    public static void main(String[] args) {
        int gridSize = 10;
        int totalTrials = 10;
        if (args.length >= 2) {
            gridSize = Integer.parseInt(args[0]);
            totalTrials = Integer.parseInt(args[1]);
        }
        PercolationStats ps = new PercolationStats(gridSize, totalTrials);
        String con = "[" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]";
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + con);
    }
}
