# LaGrangeInterpolationWebApp
Web application for computing LaGrange Interpolating Polynomials

#What are Lagrange Interpolating Polynomials?

The LaGrange Interpolating Polynomial takes in a set of n x,y coordinates (ex. [(3,2),(4,-6),(1,3)] ) and returns the polynomial f(x) of degree n-1 such that f(x) passes through each point. In this example, the polynomial would be f(x) = (-5.0/2.0)x^2 + (19.0/2.0)x^1 + (-4.0/1.0)x^0.

# How it will work
User will input x and y coordinates on a javascript front end. This gets formatted into a JSON file which is sent to the Java backend. Java parses the JSON, computes the polynomial, and sends it back to the front end. The front end displays the polynomial and graphs it using the JSXGraph javascript graphing calculculator library.

# What has been done (Back End)
- Java can already compute the Lagrange polynomial for any valid input from a JSON file.
- It can output it in either rational or decimal format.

# What needs to be done (Back End)
- Set up Java Servlet and Apache Tomcat to handle requests from front end

# What has been done (Front End)
- Barebones infastructure (Graph, Buttons, MathJAX for displaying the polynomials using LaTeX
- Implemented dynamic point plotting using JSXGraph

# What needs to be done (Front End)
- Implement polynomial plotting using JSXGraph
- Add clear graph, plot lagrange buttons
- Organize everything using CSS and Bootstrap
- Be able to send JSON file containing points and function display format to the Java servlet
