This project is an sample spring boot application developed by Steve Garncarz.

The requirements were as follows:

* A retailer offers a rewards program to its customers, awarding points based on each recorded purchase.

* A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent between $50 and $100 in each transaction.

	* (e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).

* Given a record of every transaction during a three month period, calculate the reward points earned for each customer per month and total.  

This application supports the following 3 endpoints:
* HomePage: 							http://localhost:8080/
* Display input File (json): 		http://localhost:8080/showinput
* Display 3 Month Report (json): 	http://localhost:8080/prev3MonthReport
