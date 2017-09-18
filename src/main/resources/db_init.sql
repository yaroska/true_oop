DROP TABLE category;
CREATE TABLE category (
  id        INTEGER AUTO_INCREMENT PRIMARY KEY,
  name      CHAR(25) NOT NULL UNIQUE,
  parent_id INTEGER,
  FOREIGN KEY (parent_id) REFERENCES category (id) ON DELETE SET NULL
);

INSERT INTO category (name) VALUES ('foo');
INSERT INTO category (name, parent_id) VALUES ('bar', 1);
INSERT INTO category (name, parent_id) VALUES ('baz', 2);

SELECT *
FROM category;

DROP TABLE product;
CREATE TABLE product (
  id    INTEGER AUTO_INCREMENT PRIMARY KEY,
  name  CHAR(25)       NOT NULL,
  desc  CHAR(25)       NOT NULL,
  price DECIMAL(20, 2) NOT NULL
);

INSERT INTO product (name, desc, price) VALUES ('soap', 'super soap', 12.34);
INSERT INTO product (name, desc, price) VALUES ('rope', 'mega rope', 56.78);

SELECT *
FROM product;


DROP TABLE category_product;
CREATE TABLE category_product (
  category_id INTEGER NOT NULL,
  product_id  INTEGER NOT NULL,
  FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE CASCADE,
  FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE,
  PRIMARY KEY (category_id, product_id)
);

INSERT INTO category_product (category_id, product_id) VALUES (1, 1);
INSERT INTO category_product (category_id, product_id) VALUES (2, 2);
INSERT INTO category_product (category_id, product_id) VALUES (3, 1);
INSERT INTO category_product (category_id, product_id) VALUES (3, 2);

SELECT *
FROM category_product;
