
public class PercolationMyImplOfUF {

    // reserva as ultimas posicoes do array para virtual site
    private final int virtualTopSite;

    private final int n;
    private boolean[][] states;
    private int[] ids;
    private int[] rootToWeight;
    private int numberOfOpenSites;

    public PercolationMyImplOfUF(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        this.n = n;

        virtualTopSite = n * n;

        this.states = new boolean[n][n];
        this.ids = new int[n * n + 1]; // +1 para os virtuais sites

        // os indices representam o root e o valor representa o peso daquele root
        this.rootToWeight = new int[n * n + 1];
        for (int i = 0; i < this.rootToWeight.length; i++) {
            // todos começam com 1, exceto os virtual sites
            this.rootToWeight[i] = 1;
        }
        this.rootToWeight[virtualTopSite] = 2;

        // fill the ids with the index
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.states[i][j] = false;

                // incrementa um para simular como o visualizador envia
                int seqIndex = getSequencialIndex(i + 1, j + 1);

                if (i == 0) { // primeira linha
                    this.ids[seqIndex] = virtualTopSite;
                }
                else {
                    this.ids[seqIndex] = seqIndex;
                }

                // System.out.print(seqIndex + " "); // debug
            }
            // System.out.println(); // debug
        }
        this.ids[virtualTopSite] = virtualTopSite;
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

        // top
        if (row > 1 && isOpen(row - 1, col)) {
            int iAdjacent = seqIndex - n;
            // System.out.println("connect (" + iAdjacent + "," + seqIndex + ")");
            union(iAdjacent, seqIndex);
        }
        // right
        if (col < n && isOpen(row, col + 1)) {
            int iAdjacent = seqIndex + 1;
            // System.out.println("connect (" + iAdjacent + "," + seqIndex + ")");
            union(iAdjacent, seqIndex);
        }
        // left
        if (col > 1 && isOpen(row, col - 1)) {
            int iAdjacent = seqIndex - 1;
            // System.out.println("connect (" + iAdjacent + "," + seqIndex + ")");
            union(iAdjacent, seqIndex);
        }
        // bottom
        if (row < n && isOpen(row + 1, col)) {
            int iAdjacent = seqIndex + n;
            // System.out.println("connect (" + iAdjacent + "," + seqIndex + ")");
            union(iAdjacent, seqIndex);
        }

        // print();
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
        int indexRoot = root(sequencialIndex);

        return indexRoot == virtualTopSite;
    }

    public int numberOfOpenSites() {
        // number of open sites
        return numberOfOpenSites;
    }

    public boolean percolates() {
        // does the system percolate?
        // check if the last row are connected with the top
        int leftBottomIndex = n * n - n;
        for (int i = leftBottomIndex; i < n * n; i++) {
            int col = Math.max(1, (i + 1) - leftBottomIndex);
            if (root(i) == virtualTopSite && isOpen(n, col)) {
                return true;
            }
        }
        return false;
    }

    private int root(int i) {
        while (i != ids[i]) {
            i = ids[ids[i]];
        }
        return i;
    }

    private void union(int p, int q) {
        int pRoot = root(p);
        int qRoot = root(q);

        // nao substitui root do topo
        if (pRoot == virtualTopSite) {
            ids[qRoot] = ids[pRoot];
            rootToWeight[pRoot] += rootToWeight[qRoot];
        }
        else if (qRoot == virtualTopSite) {
            ids[pRoot] = ids[qRoot];
            rootToWeight[qRoot] += rootToWeight[pRoot];
        }
        else {
            if (rootToWeight[pRoot] >= rootToWeight[qRoot]) {
                ids[qRoot] = ids[pRoot];
                rootToWeight[pRoot] += rootToWeight[qRoot];
            }
            else {
                ids[pRoot] = ids[qRoot];
                rootToWeight[qRoot] += rootToWeight[pRoot];
            }
        }

    }

    private void checkInputs(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) throw new IllegalArgumentException();
    }

}
