### Naive implementation of pure OOP ideas of [Yegor Bugayenko](http://www.yegor256.com/). 

**Warehouse** is web app with REST interfaces and **without** ORM. Because as everybody knows 
[ORM Is an Offensive Anti-Pattern](https://dzone.com/articles/orm-offensive-anti-pattern).


#### How to run

First of all you'll need to init H2 database schema. You can try to do it this way:

- In Intellij Idea create *embeded* H2 datasource
- Set path to `/tmp/warehouse`
- Set password to `sa`

Your connection URL should look like this: `jdbc:h2:/tmp/warehouse`

Now fill you DB with `db_init.sql` script from project resources folder.

Now you can run sample warehouse app from command line:
 
```
$ mvn clean integration-test -Phit-refresh -Dport=8080
```

#### How to use
Now in browser open `localhost:8080/categories` to see list of categories.

GET `localhost:8080/categories` - to list all categories
POST/PUT `localhost:8080/categories` with`{name:foo, parent_id=1}` - to create new category. 
Parent ID is optional here.

GET `/categories/ID` - shows category with ID
DETELE `/categories/ID` - to delete category with ID
POST/PUT `/categories/ID` - with`{name:foo, parent_id=1}` to update category with ID. 
Be aware to not make dependency circles within categories tree structure.

GET `/products` - list of all products
POST,PUT `/products' with `{name:foo, desc:foo_desc, price:12.56}`- add new product
                                        
GET `/products/ID` - to get product with ID
POST/PUT `/products/ID` with `{name:foo, desc:foo_desc, price:12.56}` - to update product with ID 
DELETE `/products/ID` - to delete product with ID
                                        
GET `/categories/ID/products` - to get list of products in category with ID

POST/PUT `/categories/$CATEGORY_ID/products/$PRODUCT_ID` to assign product with PRODUCT_ID to category with CATEGORY_ID
DELETE `/categories/$CATEGORY_ID/products/$PRODUCT_ID` to remove product with PRODUCT_ID from category with CATEGORY_ID