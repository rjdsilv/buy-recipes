-- liquibase formatted sql
-- changeset rjdsilv:createInitialDatabase

CREATE TABLE IF NOT EXISTS products (
    id             INT  NOT NULL PRIMARY KEY,
    name           TEXT NOT NULL UNIQUE,
    price_in_cents INT  NOT NULL
);

CREATE TABLE IF NOT EXISTS carts (
    id             INT NOT NULL PRIMARY KEY,
    total_in_cents INT NOT NULL
);

CREATE TABLE IF NOT EXISTS cart_items (
    id         INT NOT NULL PRIMARY KEY,
    cart_id    INT NOT NULL,
    product_id INT NOT NULL,

    CONSTRAINT fk_cart_items__cart_id
        FOREIGN KEY (cart_id)
            REFERENCES carts(id),
    CONSTRAINT fk_cart_items__product_id
        FOREIGN KEY (product_id)
            REFERENCES products(id)
);

CREATE TABLE IF NOT EXISTS recipes (
    id         INT            NOT NULL PRIMARY KEY,
    name       TEXT           NOT NULL
);

CREATE TABLE IF NOT EXISTS recipe_products (
    recipe_id  INT            NOT NULL,
    product_id INT            NOT NULL,
    quantity   NUMERIC(7, 2)  NOT NULL,
    unit       TEXT           NOT NULL,

    CONSTRAINT pk_recipe_products__recipe_id__product_id
        PRIMARY KEY (recipe_id, product_id),
    CONSTRAINT fk_recipe_products__recipe_id
        FOREIGN KEY (recipe_id)
            REFERENCES recipes(id),
    CONSTRAINT fk_recipe_products__product_id
        FOREIGN KEY (product_id)
            REFERENCES products(id)
);

-- Indexes to speed up searches and joins
CREATE INDEX ix_cart_items__cart_id    ON cart_items(cart_id);
CREATE INDEX ix_cart_items__product_id ON cart_items(product_id);
CREATE INDEX ix_recipes__name          ON recipes(name);
