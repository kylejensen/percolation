public class Percolation
{

    private boolean[][] opened;
    private int top = 0;
    private int bottom;
    private int size;
    private WeightedQuickUnionUF wqu;

    public Percolation (int n)
    {
        size = n;
        bottom = size * size + 1;
        wqu = new WeightedQuickUnionUF(size * size + 2);
        opened = new boolean[size][size];
    }

    public void open (int row, int col)
    {
        validate(row);
        validate(col);

        opened[row - 1][col - 1] = true;

        if (row == 1) {
            wqu.union(getQFIndex(row, col), top);
        } else if (row == size) {
            wqu.union(getQFIndex(row, col), bottom);
        }
        
        if (col < size && isOpen(row, col + 1)) {
            wqu.union(getQFIndex(row, col), getQFIndex(row, col + 1));
        }

        if (col > 1 && isOpen(row, col - 1)) {
            wqu.union(getQFIndex(row, col), getQFIndex(row, col - 1));
        }

        if (row < size && isOpen(row + 1, col)) {
            wqu.union(getQFIndex(row, col), getQFIndex(row + 1, col));
        }

        if (row > 1 && isOpen(row - 1, col)) {
            wqu.union(getQFIndex(row, col), getQFIndex(row - 1, col));
        }

    }

    public boolean isOpen (int row, int col)
    {
        validate(row);
        validate(col);

        return opened[row - 1][col - 1];
    }

    public boolean isFull (int row, int col)
    {
        validate(row);
        validate(col);

        if (0 < row && row <= size && 0 < col && col <= size) {
            return wqu.connected(top, getQFIndex(row , col));
        } else {
            throw new IndexOutOfBoundsException();
        }

    }

    public int numberOfOpenSites ()
    {
        int total = 0;
        for (int i = 1, len = opened.length; i < len; i++) {
            for (int j = 1; j < len; j++) {
                if (isOpen(i, j)) {
                    total += 1;
                }
            }
        }
        return total;
    }

    public boolean percolates ()
    {
        return wqu.connected(top, bottom);
    }

    private int getQFIndex(int row, int col)
    {
        return size * (row - 1) + col;
    }
    
    private void validate (int x)
    {
        if (x <= 0 || x > size) {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
    }

    public static void main (String[] args)
    {
       Percolation percolation = new Percolation (10);
       percolation.open(1, 1);
       percolation.open(2, 1);
       percolation.open(3, 1);
       percolation.open(4, 1);
       percolation.open(5, 1);
       percolation.open(6, 1);
       percolation.open(7, 1);
       percolation.open(8, 1);
       percolation.open(9, 1);
       percolation.open(10, 1);

       System.out.println(percolation.wqu.connected(percolation.getQFIndex(1, 1), percolation.getQFIndex(1, 2)));
       System.out.println(percolation.numberOfOpenSites());
       System.out.println(percolation.percolates());
    }
}