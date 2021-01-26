CREATE TABLE role
(
--     id   SMALLSERIAL PRIMARY KEY,
    id   SERIAL PRIMARY KEY,
    name VARCHAR(16) UNIQUE NOT NULL
);

CREATE TABLE users
(
    id           BIGSERIAL PRIMARY KEY,
    user_name    CHARACTER VARYING(64) NOT NULL,
    phone_number CHARACTER VARYING(13) NOT NULL,
    name         CHARACTER VARYING(64) NOT NULL UNIQUE,
    password     CHARACTER VARYING(80) NOT NULL UNIQUE,
    role_id      SMALLINT              NOT NULL REFERENCES role (id)
);

CREATE TABLE country
(
    id   SERIAL PRIMARY KEY,
    name CHARACTER VARYING(128) NOT NULL,
    abbreviation CHARACTER VARYING(32) NOT NULL
);

CREATE TABLE maker
(
    id         SERIAL PRIMARY KEY,
    name       CHARACTER VARYING(128) NOT NULL,
    country_id INTEGER                NOT NULL REFERENCES country (id)
);

CREATE TABLE category
(
    id   SERIAL PRIMARY KEY,
    name CHARACTER VARYING(128)
);

CREATE TABLE type
(
    id          SERIAL PRIMARY KEY,
    name        CHARACTER VARYING(128),
    category_id INTEGER NOT NULL REFERENCES category (id)
);

CREATE TABLE product
(
    id          BIGSERIAL PRIMARY KEY,
    name        CHARACTER VARYING(256) NOT NULL,
    price       numeric(7, 2)          NOT NULL,
    description CHARACTER VARYING(560),
    type_id     INTEGER               NOT NULL REFERENCES type (id),
    maker_id    INTEGER               NOT NULL REFERENCES maker (id)
);

CREATE TABLE count
(
    id          BIGSERIAL PRIMARY KEY,
    count       INTEGER NOT NULL,
    product_id     INTEGER               NOT NULL REFERENCES product (id)
);

CREATE TABLE users_product
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT NOT NULL REFERENCES users (id),
    product_id BIGINT NOT NULL REFERENCES product (id)
);

CREATE TABLE orders
(
    id              BIGSERIAL PRIMARY KEY,
    time_of_orders  TIMESTAMP             NOT NULL,
    price           NUMERIC(12, 2),
    type_of_payment CHARACTER VARYING(64) NOT NULL,
    status          CHARACTER VARYING(16) NOT NULL,
    user_id BIGINT NOT NULL REFERENCES Users(id)
);

CREATE TABLE orders_item
(
    id         BIGSERIAL PRIMARY KEY,
    orders_id  BIGINT        NOT NULL REFERENCES orders (id),
    product_id BIGINT        NOT NULL REFERENCES Product (id),
    price      NUMERIC(7, 2) NOT NULL
);

-- DROP TABLE product cascade;
-- DROP TABLE category cascade ;
-- DROP TABLE type cascade;
-- DROP TABLE maker cascade;
-- DROP TABLE country CASCADE;
-- DROP TABLE users CASCADE;
-- DROP TABLE role CASCADE;
-- DROP TABLE orders CASCADE;