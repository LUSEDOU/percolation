import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int TOP = 0;
    private final boolean[][] open;
    private final int size;
    private final int bottom;
    private int opensites;
    private final WeightedQuickUnionUF qf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if (n <=0) {
            throw new IllegalArgumentException();
        }
        size = n;
        bottom = size * size + 1;
        open = new boolean[size][size];
        opensites = 0;
        qf = new WeightedQuickUnionUF(size * size + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        checkException(row, col);
        open[row -1][col -1] = true;
        ++opensites;

        if (row == 1) {
            qf.union(getQuickFindIndex(row, col), TOP);
        }
        
        if (row == size) {
            qf.union(getQuickFindIndex(row, col), bottom);
        }

        if (row > 1 && isOpen(row - 1, col)) {
            qf.union(
                getQuickFindIndex(row, col),
                getQuickFindIndex(row -1, col)
            );
        }

        if (row < size && isOpen(row + 1, col)) {
            qf.union(
                getQuickFindIndex(row, col),
                getQuickFindIndex(row + 1, col)
            );
        }

        if (col > 1 && isOpen(row, col - 1)) {
            qf.union(
                getQuickFindIndex(row, col),
                getQuickFindIndex(row, col - 1)
            );
        }

        
        if (col < size && isOpen(row, col + 1)) {
            qf.union(
                getQuickFindIndex(row, col),
                getQuickFindIndex(row, col + 1)
            );
        }
    }

    private int getQuickFindIndex(int row, int col) {
        return size * (row - 1) + col;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        checkException(row, col);
        return open[row - 1][col - 1];

    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        if ((row > 0 && row <=size) && (col > 0 && col <=size)) {
            return qf.find(TOP) == qf.find(
                getQuickFindIndex(row, col)
            );
        } else throw new IllegalArgumentException();
        //return false;
    }

    
    //     returns the number of open sites
    public int numberOfOpenSites(){
      return opensites;
    }

    // does the system percolate?
    public boolean percolates(){
        return qf.find(TOP) == qf.find(bottom);
    }

    private void checkException(int row, int col){
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException();
        }
    }
    
    // test client (optional)
    public static void main(String[] args){

    }
}
