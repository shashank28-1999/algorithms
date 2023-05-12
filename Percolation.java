import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    private boolean[][] a;
    private int top;
    private int bot;
    private int open;
    private WeightedQuickUnionUF per;
    private WeightedQuickUnionUF full;

    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("grid size must be atleast 1");
        }
        size = n;
        int length = n * n + 2;
        a = new boolean[n][n];
        open = 0;
        top = 0;
        bot = size * size + 1;
        per = new WeightedQuickUnionUF(length);
        full = new WeightedQuickUnionUF(length);

    }

    private void check(int x, int y) {
        if (isCorrect(x, y) == false) {
            throw new IllegalArgumentException("index out of bounds");
        }
    }

    private boolean isCorrect(int x, int y) {
        return (x - 1 >= 0 && y - 1 >= 0 && x - 1 < size && y - 1 < size);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        check(row, col);
        if (isOpen(row, col)) {
            return;
        }
        a[row - 1][col - 1] = true;
        open++;
        int index = indexOf(row, col);
        if (row == 1) {
            per.union(top, index);
            full.union(top, index);
        }
        if (row == size) {
            per.union(bot, index);
        }
        connect(index, row, col + 1);
        connect(index, row, col - 1);
        connect(index, row + 1, col);
        connect(index, row - 1, col);
    }

    private void connect(int x, int y, int z) {
        if (isCorrect(y, z) && isOpen(y, z)) {
            per.union(x, indexOf(y, z));
            full.union(x, indexOf(y, z));
        }
    }

    private int indexOf(int x, int y) {
        return size * (x - 1) + y;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        check(row, col);
        return a[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        check(row, col);
        boolean b = a[row - 1][col - 1];
        if (b == false) {
            return false;
        }
        return full.find(top) == full.find(indexOf(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return open;
    }

    // does the system percolate?
    public boolean percolates() {
        return per.find(top) == per.find(bot);
    }
}
