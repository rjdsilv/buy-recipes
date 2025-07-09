INSERT INTO products(name, price_in_cents)
VALUES
    ('Product 1', 359),
    ('Product 2', 559),
    ('Product 3', 2057),
    ('Product 4', 1037),
    ('Product 5', 1737),
    ('Product 6', 237),
    ('Product 7', 925),
    ('Product 8', 109),
    ('Product 9', 1257),
    ('Product 10', 447)
ON CONFLICT DO NOTHING;

INSERT INTO recipes(name)
VALUES
    ('3 Products Recipe'),
    ('2 Products Recipe'),
    ('5 Products Recipe'),
    ('4 Products Recipe')
ON CONFLICT DO NOTHING;

INSERT INTO recipe_products(recipe_id, product_id)
VALUES
    ((SELECT id FROM recipes WHERE name = '3 Products Recipe'), (SELECT id FROM products WHERE name = 'Product 1')),
    ((SELECT id FROM recipes WHERE name = '3 Products Recipe'), (SELECT id FROM products WHERE name = 'Product 2')),
    ((SELECT id FROM recipes WHERE name = '3 Products Recipe'), (SELECT id FROM products WHERE name = 'Product 3')),
    ((SELECT id FROM recipes WHERE name = '2 Products Recipe'), (SELECT id FROM products WHERE name = 'Product 1')),
    ((SELECT id FROM recipes WHERE name = '2 Products Recipe'), (SELECT id FROM products WHERE name = 'Product 2')),
    ((SELECT id FROM recipes WHERE name = '5 Products Recipe'), (SELECT id FROM products WHERE name = 'Product 1')),
    ((SELECT id FROM recipes WHERE name = '5 Products Recipe'), (SELECT id FROM products WHERE name = 'Product 2')),
    ((SELECT id FROM recipes WHERE name = '5 Products Recipe'), (SELECT id FROM products WHERE name = 'Product 3')),
    ((SELECT id FROM recipes WHERE name = '5 Products Recipe'), (SELECT id FROM products WHERE name = 'Product 4')),
    ((SELECT id FROM recipes WHERE name = '5 Products Recipe'), (SELECT id FROM products WHERE name = 'Product 5')),
    ((SELECT id FROM recipes WHERE name = '4 Products Recipe'), (SELECT id FROM products WHERE name = 'Product 1')),
    ((SELECT id FROM recipes WHERE name = '4 Products Recipe'), (SELECT id FROM products WHERE name = 'Product 2')),
    ((SELECT id FROM recipes WHERE name = '4 Products Recipe'), (SELECT id FROM products WHERE name = 'Product 3')),
    ((SELECT id FROM recipes WHERE name = '4 Products Recipe'), (SELECT id FROM products WHERE name = 'Product 4'))
ON CONFLICT DO NOTHING;
