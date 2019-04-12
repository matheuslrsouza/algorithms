import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // reserva as ultimas posicoes do array para virtual site
    private final int virtualTopSite;
    private final int virtualBottomSite;

    private final int n;
    private boolean[][] states;
    private int numberOfOpenSites;
    private final WeightedQuickUnionUF quWithTop;
    private final WeightedQuickUnionUF quWithTopAndBottom;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        quWithTop = new WeightedQuickUnionUF(n * n + 1);
        quWithTopAndBottom = new WeightedQuickUnionUF(n * n + 2);

        this.n = n;

        virtualTopSite = n * n;
        virtualBottomSite = n * n + 1;

        this.states = new boolean[n][n];

        // fill the ids with the index
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.states[i][j] = false;

                // incrementa um para simular como o visualizador envia
                int seqIndex = getSequencialIndex(i + 1, j + 1);

                if (i == 0) { // primeira linha
                    quWithTop.union(virtualTopSite, seqIndex);
                    quWithTopAndBottom.union(virtualTopSite, seqIndex);
                }
            }
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        checkInputs(row, col);

        if (isOpen(row, col)) {
            return;
        }

        numberOfOpenSites++;
        this.states[row - 1][col - 1] = true;

        // transform row col in sequencial index
        int seqIndex = getSequencialIndex(row, col);


        // bind with adjacents

        // connects with the virtual bottom se for a ultima linha
        if (row == n) {
            quWithTopAndBottom.union(virtualBottomSite, seqIndex);
        }

        // top
        if (row > 1 && isOpen(row - 1, col)) {
            int iAdjacent = seqIndex - n;
            quWithTop.union(iAdjacent, seqIndex);
            quWithTopAndBottom.union(iAdjacent, seqIndex);
        }
        // right
        if (col < n && isOpen(row, col + 1)) {
            int iAdjacent = seqIndex + 1;
            quWithTop.union(iAdjacent, seqIndex);
            quWithTopAndBottom.union(iAdjacent, seqIndex);
        }
        // left
        if (col > 1 && isOpen(row, col - 1)) {
            int iAdjacent = seqIndex - 1;
            quWithTop.union(iAdjacent, seqIndex);
            quWithTopAndBottom.union(iAdjacent, seqIndex);
        }
        // bottom
        if (row < n && isOpen(row + 1, col)) {
            int iAdjacent = seqIndex + n;
            quWithTop.union(iAdjacent, seqIndex);
            quWithTopAndBottom.union(iAdjacent, seqIndex);
        }
    }

    private int getSequencialIndex(int row, int col) {
        return n * (row - 1) + col - 1;
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkInputs(row, col);
        return this.states[row - 1][col - 1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        checkInputs(row, col);

        if (!isOpen(row, col))
            return false;

        int sequencialIndex = getSequencialIndex(row, col);

        return quWithTop.connected(sequencialIndex, virtualTopSite);
    }

    public int numberOfOpenSites() {
        // number of open sites
        return numberOfOpenSites;
    }

    public boolean percolates() {
        // does the system percolate?
        return quWithTopAndBottom.connected(virtualTopSite, virtualBottomSite);
    }

    private void checkInputs(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) throw new IllegalArgumentException();
    }

}
