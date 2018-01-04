#This program is an example of string manipulation. 
Reading in an arbitrary string in a specific format, extracting the data and running calculations. Further setting the results into a string for output.


SPECIFICS OF PROGRAM
	//Terms
Net Assets Valuation (NAV) = value of total asset in portfolio.
Percentage NAV = percentage weight of an asset based on a bench mark.
	//Equations
NAV(portfolio) = Sum(quantity(X1)*price(X1)):X1 is element(asset) of portfolio
%NAV(X1-portfolio) = (quantity(X1)*price(X1)) * 100 / NAV(Portfolio)
	//More Terms
portfolio(overweight) = (%NAV(X1-portfolio)) > (%NAV(X1-benchmark)
portfolio(underoverweight) = (%NAV(X1-portfolio)) < (%NAV(X1-benchmark)

Program:
If given string in the format: <portfolioName>:<asset1>,<quantity>,<price>;...|<BenchMark>:<asset1>,<quantity>,<price>;...
ex) PRTF:AYU,0,10;VFR,20,30;GYR,10,30|BENCH:AYU,50,10;VFR,30,30;FWT,30,20

Calculate and display the difference in %NAV 

Output of program should be in format(assets also need to be in alphabetical order):
<asset1>:<(%NAV(X1-portfolio)) - (%NAV(X1-benchmark)>,<asset2>:<(%NAV(X2-portfolio)) - (%NAV(X2-benchmark)>,...
ex)AYU:-25.00,FWT:-30.00,GYR:33.33,VFR:21.67