INSERT INTO users (user_id, email, password, first_name, last_name, flag_url)
VALUES (1, 'testuser@gmail.com', 'password', 'Test', 'User', 'www.test.com');

INSERT INTO orders (order_id, order_amount, user_id)
VALUES (1, 10.0, 1);

INSERT INTO products (product_id, product_name, product_type, product_description, price, image_url)
VALUES (1, 'Test Product', 'Test Type', 'This is a test product', 25.0, 'https://www.example.com/test_product.jpg');
