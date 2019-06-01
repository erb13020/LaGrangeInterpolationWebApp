//first create the board that we want to graph on
		var board = JXG.JSXGraph.initBoard('box', {boundingbox: [-10, 10, 10, -10], axis:true});
		var bounds = [-10,10,10,-10];
		var scale = 1.5;
		var points = [];

		String.prototype.replaceAll = function(str1, str2, ignore) {
    	return this.replace(new RegExp(str1.replace(/([\/\,\!\\\^\$\{\}\[\]\(\)\.\*\+\?\|\<\>\-\&])/g,"\\$&"),(ignore?"gi":"g")),(typeof(str2)=="string")?str2.replace(/\$/g,"$$$$"):str2);
		}	//replace all instances of str1 with str2 from https://stackoverflow.com/questions/2116558/fastest-method-to-replace-all-instances-of-a-character-in-a-string

		function parity(int){
			if (int % 2 == 0){
				return true; //integer is even
			}else{
				return false; //integer is odd
			}
		}

		function graphPoints(){
			var input = document.getElementById("points_list").value; //this is our user input
			
			input = input.replaceAll(" ", ""); //remove all spaces and brackets
			input = input.replaceAll("{", ""); //we only need commas to parse the input into our points array
			input = input.replaceAll("}", "");
			input = input.replaceAll("(", "");
			input = input.replaceAll(")", "");
			input = input.replaceAll("[", "");
			input = input.replaceAll("]", "");

			input = input.split(",");

			for (var i = 0; i < input.length; i++){
 				input[i] = Number(input[i]);
 			}

 			for (var i = 0; i < input.length; i += 2){
 				var x = input[i];
 				var y = input[i+1];
 				var xy = [x,y];
 				points.push(xy);

 				//automatically scale graph based on inputs
 				if(Math.abs(x) > bounds[2]){
 					bounds[0] = x * -scale;
 					bounds[2] = x * scale;
 				}
 				if (Math.abs(y) > Math.abs(bounds[1])){
 					bounds[1] = y * scale;
 					bounds[3] = y * -scale;
 				}

 				bounds[0] = Math.abs(bounds[0]) * -1; //neg x should be negative
 				bounds[1] = Math.abs(bounds[1]);	//pos y should be positive
 				bounds[2] = Math.abs(bounds[2]); //pos x should be positive
 				bounds[3] = Math.abs(bounds[3]) * -1; //neg y should be negative
 			}

 			board = JXG.JSXGraph.initBoard('box', {boundingbox: bounds, axis:true});

 			for (var i = 0; i < points.length; i++){
 			board.create('point',points[i], {name:'', size:3, fixed: true});
 			}
 			var points_filtered = document.getElementById("points_filtered");
 			points_filtered.innerHTML = points;
 			points = [];
		}
		function subStrAfterChars(str, char, pos){
			if(pos=='b')
   				return str.substring(str.indexOf(char) + 1);
  			else if(pos=='a') 
   			return str.substring(0, str.indexOf(char));
  			else
  			return str;  
		}

	//plots LaGrange Polynomial computed from the back end
	function graphPolynomial(){
		var polynomial = document.getElementById("polynomial").innerHTML;

		//now we re-format our LaGrange polynomial, which is in MathJAX/LaTeX format, into something JSXGraph can parse
		polynomial = polynomial.substring(polynomial.lastIndexOf("=") + 2);
		polynomial = polynomial.replaceAll("</scr", "");
		polynomial = polynomial.replaceAll("ipt>", "");
        polynomial = polynomial.replaceAll("x", " * x");
        polynomial = polynomial.replaceAll("^", " ** ");
        
		board.create('functiongraph',
                       [function(x){
                       	return eval(polynomial); //turn the string 'polynomial' into literal javascript code
                       }]
                    );
	}

	//clears the plot
	function clearAll(){
    	points = [];
    	bounds = [10,10,10,10];
    	board = JXG.JSXGraph.initBoard('box', {boundingbox: [-10, 10, 10, -10], axis:true});
	}