INSERT INTO users (user_id, email, password, first_name, last_name, flag_url)
VALUES (1, 'test@gmail.com', 'password', 'Test', 'User', 'www.flag.url');

INSERT INTO orders (order_id, order_amount, user_id)
VALUES (1, 10.0, 1);

INSERT INTO products (product_id, product_name, product_type, product_description, price, image_url)
VALUES (1, 'Product A', 'Type A', 'Description A', 10.0, 'image.url A');
