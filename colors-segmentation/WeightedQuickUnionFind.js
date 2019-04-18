

function WeightedQuickUnionFind(n) {

    var ids = [n];
    var size =[n];

    for (var i = 0; i < n; i++) {
        ids[i] = i;
        size[i] = 1;
    }

    this.union = function(p, q) {
        var pRoot = this.find(p);
        var qRoot = this.find(q);
        if (pRoot == qRoot) {
            return;
        }

        if (size[pRoot] > size[qRoot]) {
            ids[qRoot] = pRoot;
            size[pRoot] += size[qRoot];
        } else {
            ids[pRoot] = qRoot;
            size[qRoot] += size[pRoot];
        }
    }

    this.connected = function(p, q) {
        var pRoot = this.find(p);
        var qRoot = this.find(q);

        return pRoot == qRoot;
    }

    this.find = function(i) {
        while (i != ids[i]) {
            i = ids[ids[i]];
        }
        return i;
    }

    this.rootsWithMinSize = function(n) {
        var newSizes = size.filter((val) => {
            return val >= n;
        });
        console.log(newSizes);
    }

    this.print = function() {
        console.log(ids);
    }

}