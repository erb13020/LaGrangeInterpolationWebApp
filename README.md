# LaGrangeInterpolationWebApp
Web application for computing LaGrange Interpolating Polynomials

#What are Lagrange Interpolating Polynomials?

The LaGrange Interpolating Polynomial takes in a set of n x,y coordinates (ex. [(3,2),(4,-6),(1,3)] ) and returns the polynomial f(x) of degree n-1 such that f(x) passes through each point. In this example, the polynomial would be f(x) = (-5.0/2.0)x^2 + (19.0/2.0)x^1 + (-4.0/1.0)x^0.

#How it will work
User will input x and y coordinates on a javascript front end. This gets formatted into a JSON file which is sent to the Java backend. Java parses the JSON, computes the polynomial, and sends it back to the front end. The front end displays the polynomial and graphs it using the JSXGraph javascript graphing calculculator library.

#What has been done
-Java can already compute the Lagrange polynomial for any valid input.
-It can output it in either rational or decimal format.

#What needs to be done
-Start working on Front End
-Be able to display a graph given a function f(x)
-Connect front end to back end
-Send a JSON file containing input to Backend
-Send output back to front end to be displayed
