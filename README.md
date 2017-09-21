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

- `localhost:8080/categories`
  - GET to list all categories
  - POST/PUT`{name:foo, parent_id=1}` to create new category. Parent ID is optional here.

- `/categories/ID`
  - GET shows category with ID
  - DELETE to delete category with ID
  - POST/PUT `{name:foo, parent_id=1}` to update category with ID. 
Be aware to not make dependency circles within categories tree structure.

- `/products`
  - GET lists all products
  - POST/PUT `{name:foo, desc:foo_desc, price:12.56}` adds new product

- `/products/ID`                                        
  - GET shows product with ID
  - POST/PUT `{name:foo, desc:foo_desc, price:12.56}` updates product with ID 
  - DELETE to delete product with ID
  
- `/categories/ID/products`                                        
  - GET lists products in category with ID

- `/categories/$CATEGORY_ID/products/$PRODUCT_ID`
  - POST/PUT  to assign product with PRODUCT_ID to category with CATEGORY_ID
  - DELETE to remove product with PRODUCT_ID from category with CATEGORY_ID