CREATE TABLE IF NOT EXISTS products
(
    id INT PRIMARY KEY,
    name VARCHAR (250) NOT NULL,
    category VARCHAR (100) NOT NULL,
    description VARCHAR (1000)
);

CREATE SEQUENCE IF NOT EXISTS PRODUCTSSEQ START WITH 100000;

CREATE TABLE IF NOT EXISTS cart_items
(
    cartId UUID,
    productId INT,
    price DOUBLE PRECISION,
    currency VARCHAR (3),
    cartItemType VARCHAR (20)
);


CREATE TABLE IF NOT EXISTS orders
(
    id UUID PRIMARY KEY,
    clientId UUID,
    clientName VARCHAR (100), 
    deliveryAddress VARCHAR (200), 
    discountDescription VARCHAR (200), 
    paymentStatus VARCHAR (20), 
    orderStatus VARCHAR (20)
);

CREATE TABLE IF NOT EXISTS order_items
(
    orderId UUID, 
    productId INT, 
    productName VARCHAR (100), 
    productCategory VARCHAR (100),
    productDescription VARCHAR (1000),
    quantity INT,
    totalPrice DOUBLE PRECISION,
    totalPriceCurrency VARCHAR (3)
);

ALTER TABLE order_items
    ADD FOREIGN KEY (orderId) REFERENCES orders (id);
ALTER TABLE order_items
    ADD FOREIGN KEY (productId) REFERENCES products (id);