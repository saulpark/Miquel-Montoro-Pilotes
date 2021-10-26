DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS client;
DROP TABLE IF EXISTS address;

CREATE TABLE client (
  client_id VARCHAR(250) PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  telephone VARCHAR(250) NOT NULL
);

CREATE TABLE address (
  address_id VARCHAR(250) PRIMARY KEY,
  postcode VARCHAR(250) NOT NULL,
  city VARCHAR(250) NOT NULL,
  country VARCHAR(250) NOT NULL,
  street VARCHAR(250) NOT NULL
);

CREATE TABLE orders (
  order_id VARCHAR(250) PRIMARY KEY,
  order_number VARCHAR(250),
  address_id VARCHAR(250) NOT NULL,
  pilotes INT NOT NULL,
  order_total DECIMAL(20, 2) NOT NULL,
  client_id VARCHAR(250) NOT NULL,
  order_timestamp TIMESTAMP
 );

ALTER TABLE orders
ADD FOREIGN KEY (address_id)
REFERENCES address(address_id);

ALTER TABLE orders
ADD FOREIGN KEY (client_id)
REFERENCES client(client_id);

