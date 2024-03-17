# eatndrinkapp

This is a rest api was developed for food and beverages ordering for customers.
In this backend project, there are properties users can.

There are 4 roles which are ADMIN, MANAGER, CUSTOMER and OWNER.

CUSTOMERS can do these
- setting an order.
- updating/ deleting the order unless 10 minutes lasted from the order create time.
- making a review for a restaurant if the customer order something from there.
 
OWNERS can do these
- adding/updating/deleting a food or drink to restaurants. 
- adding new restaurant.
- seeing restaurants profit and income. 

MANAGERS can do these
- seeing top n customers which they orders something most.
- seeing the all the owners of the restaurants

ADMINS can do these
- seeing top 5 owners which they have the most income.

The software technologies used here
- Java SE 20
- Spring Data JPA
- JWT
- Spring Security
- Spring Web
- MySQL.
