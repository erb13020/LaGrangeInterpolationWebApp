import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.json.simple.JSONObject;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class LagrangeInterpolation{
    private static String _output = new String();
    
  public static double[] findPolynomialFactors (double[] x, double[] y)
    throws RuntimeException
  {
    int n = x.length;

    double[][] data = new double[n][n];
    double[]   rhs  = new double[n];
    
    for (int i = 0; i < n; i++) {
      double v = 1;
      for (int j = 0; j < n; j++) {
        data[i][n-j-1] = v;
        v *= x[i];
      }

      rhs[i] = y[i];
    }

    // Solve m * s = b
    
    Matrix m = new Matrix (data);
    Matrix b = new Matrix (rhs, n);

    Matrix s = m.solve (b);

    return s.getRowPackedCopy();
  }

  static private String convertDecimalToFraction(double x){
	    if (x < 0){
	        return "-" + convertDecimalToFraction(-x);
	    }
	    double tolerance = 1.0E-6;
	    double h1=1; double h2=0;
	    double k1=0; double k2=1;
	    double b = x;
	    do {
	        double a = Math.floor(b);
	        double aux = h1; h1 = a*h1+h2; h2 = aux;
	        aux = k1; k1 = a*k1+k2; k2 = aux;
	        b = 1/(b-a);
	    } while (Math.abs(x-h1/k1) > x*tolerance);
	    
	    return h1+"/"+k1;
	    
	}
  

  
  public static void main (String args[])
  {
	  JSONObject json_inputs = new JSONObject();
	  ArrayList<Double> xi = new ArrayList<Double>();
		
	  ArrayList<Double> yi = new ArrayList<Double>();
		
	  Scanner reader = new Scanner(System.in);
		
	  xi.add(5.0);
	  xi.add(6.0);
	  xi.add(3.0);
		
	  yi.add(7.0);
	  yi.add(6.0);
	  yi.add(-3.0);
	  int sx = xi.size();
	  int yx = yi.size();
		
	  double x[] = new double[sx];
	    
	    for (int i = 0; i < sx; i++) {
	    	x[i] = xi.get(i);
	    }
	    
	    double y[] = new double[yx];
	    
	    for (int i = 0; i < yx; i++) {
	    	y[i] = yi.get(i);
	    }
	    
	    
	if(!(x.length == y.length)) {
		System.exit(0);
	}
	

    double f[] = LagrangeInterpolation.findPolynomialFactors (x, y);

    int n = Array.getLength(y);
    
    String rationalCVector[] = new String[n]; 
    
    int r = 1;
    //int r = 0;
    
    if(r == 0) {
    	 for (int i = 0; i < n; i++) {
    	    	DecimalFormat df = new DecimalFormat("#");
    	        df.setMaximumFractionDigits(1000000);
    	        int power = n - (i+1);
    	        if(i == 0) {
    	        	System.out.print("f(x)= ");
    	        }
    	        System.out.print(df.format(f[i]));
    	        System.out.print("x" + "^" + power);
    	        if((i<n-1)) {
    	        	System.out.print(" + ");
    	        }
    	    }
    }
    
    if(r == 1) {
    	for (int i = 0; i < n; i++) {
    		
        	String rational = convertDecimalToFraction(f[i]);
        	rationalCVector[i] = rational;
        	int power = n - (i+1);
        	if((i==0)) {
        		//System.out.print("f(x)= ");
        		_output = _output + "f(x) = ";
        	}
        	//System.out.print("(" + rationalCVector[i] + ")" + "x" + "^" + power);
        	_output = _output + "(" + rationalCVector[i] + ")" + "x" + "^" + power;
        	if((i<n-1)) {
        		//System.out.print(" + ");
        		_output = _output + " + ";
        	}
        }
    }
reader.close();
System.out.println(_output);
  }
}