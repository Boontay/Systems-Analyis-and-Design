-- table structure for table 'product'

 SHOW DATABASES;

 CREATE DATABASE IF NOT EXISTS online_store;

 SHOW DATABASES;

 USE online_store;

DROP TABLE IF EXISTS products;

CREATE TABLE products (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(512) CHARACTER SET utf8 NOT NULL,
  description varchar(1024) CHARACTER SET utf8 NOT NULL,
  category varchar(1024) CHARACTER SET utf8 NOT NULL,
  price int(11) DEFAULT 0,
  product_id varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  quantity_in_stock INT(11) DEFAULT 0,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(512) CHARACTER SET utf8 NOT NULL,
  email varchar(512) CHARACTER SET utf8 NOT NULL,
  password varchar(512) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;