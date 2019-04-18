function SilimarColorsSegmentation() {

    var canvasCtx;
    var width;
    var height;
    var n;
    var weightedQUF;

    this.init = function() {
        var canvas = document.getElementById("myCanvas");

        var img = document.getElementById("foto");
        width = img.width;
        height = img.height;

        canvas.width = width;
        canvas.height = height;
        canvasCtx = canvas.getContext("2d");
        canvasCtx.drawImage(img, 0, 0);
    
        
        n = width * height;

        weightedQUF = new WeightedQuickUnionFind(n)
    
        doConnections();
    }

    //x -> col
    //y -> row
    //searches for all connected pixels based on the clicked pixel (x, y coord)
    this.markSimilarRegion = function(x, y) {
        var imgData = canvasCtx.getImageData(0, 0, width, height);

        var seq = width * y + x;

        //choose a random color
        var r = Math.random() * 255;
        var g = Math.random() * 255;
        var b = Math.random() * 255;

        for (var i = 0; i < n; i++) {
            if (weightedQUF.connected(i, seq)) {
            //multiply for 4 because eache pixel has (r, g, b, a)
            var iToChange = i * 4;
            
            imgData.data[iToChange] = r;
            imgData.data[iToChange + 1] = g;
            imgData.data[iToChange + 2] = b;
            imgData.data[iToChange + 3] = 255;
            }
        }

        canvasCtx.putImageData(imgData, 0, 0);
    }

    function doConnections() {
      
      var imgData = canvasCtx.getImageData(0, 0, width, height);
      var pixelData = imgData.data;

      for (var i = 0; i < pixelData.length; i += 4) {

        var r = pixelData[i];
        var g = pixelData[i+1];
        var b = pixelData[i+2];

        var seq = (i / 4);

        //console.log('-', seq);

        //isn't the last column?
        if ((seq + 1) % width != 0) {
          //get the right pixel
          var rNext = pixelData[i+4];
          var gNext = pixelData[i+5];
          var bNext = pixelData[i+6];
          //console.log('>', i+4);

          union(seq, [r, g, b], seq + 1, [rNext, gNext, bNext]);
        }

        //isn't the last row?
        if (seq / width < height - 1) {
          //get the bellow pixel
          var iPrimeiro = (i + width) * 4;
          var rBaixo = pixelData[iPrimeiro];
          var gBaixo = pixelData[iPrimeiro + 1];
          var bBaixo = pixelData[iPrimeiro + 2];
          //console.log('|', iPrimeiro);

          union(seq, [r, g, b], seq + width, [rNext, gNext, bNext]);
        }

      }
    }

    //compares the two colors and make the union if they are similars
    function union(p, rgbP, q, rgbQ) {
      var r = rgbP[0];
      var g = rgbP[1];
      var b = rgbP[2];
      var a = 255;
      
      var next_r = rgbQ[0];
      var next_g = rgbQ[1];
      var next_b = rgbQ[2];
      
      var distance = Math.sqrt(
              Math.pow(next_r - r, 2) +
              Math.pow(next_g - g, 2) +
              Math.pow(next_b - b, 2));
                      
      if (distance <= parseInt(document.getElementById('distance').value)) {
        weightedQUF.union(p, q);
      }
    }

}