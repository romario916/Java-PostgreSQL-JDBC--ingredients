create type dish_type as enum ('STARTER', 'MAIN', 'DESSERT');


create table dish
(
    id        serial primary key,
    name      varchar(255),
    dish_type dish_type
);

create type ingredient_category as enum ('VEGETABLE', 'ANIMAL', 'MARINE', 'DAIRY', 'OTHER');

create table ingredient
(
    id       serial primary key,
    name     varchar(255),
    price    numeric(10, 2),
    category ingredient_category,
    id_dish  int references dish (id)
);

alter table dish
    add column if not exists price numeric(10, 2);


alter table ingredient
    add column if not exists required_quantity numeric(10, 2);

    


   --la suite
DROP TYPE IF EXISTS unit_type CASCADE;
CREATE TYPE unit_type AS ENUM ('PCS', 'KG', 'L');

DROP TYPE IF EXISTS ingredient_category CASCADE;
CREATE TYPE ingredient_category AS ENUM ('VEGETABLE', 'ANIMAL', 'MARINE', 'DAIRY', 'OTHER');

DROP TYPE IF EXISTS dish_type CASCADE;
CREATE TYPE dish_type AS ENUM ('STARTER', 'MAIN', 'DESSERT');

-- Table Dish (plat)
DROP TABLE IF EXISTS dish CASCADE;
CREATE TABLE dish (
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    dish_type       dish_type NOT NULL,
    selling_price   NUMERIC(10,2)          
);

-- Table Ingredient (ingrédient unique, non dupliqué)
DROP TABLE IF EXISTS ingredient CASCADE;
CREATE TABLE ingredient (
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    price           NUMERIC(10,2) NOT NULL,  
    category        ingredient_category NOT NULL
);

-- Table de jointure DishIngredient (relation + quantité/unité spécifique au plat)
DROP TABLE IF EXISTS dishingredient CASCADE;
CREATE TABLE dishingredient (
    id                  SERIAL PRIMARY KEY,               
    id_dish             INT NOT NULL REFERENCES dish(id) ON DELETE CASCADE,
    id_ingredient       INT NOT NULL REFERENCES ingredient(id) ON DELETE RESTRICT,
    quantity_required   NUMERIC(10,2) NOT NULL,
    unit                unit_type NOT NULL,

    CONSTRAINT unique_dish_ingredient UNIQUE (id_dish, id_ingredient)  
);

-- Index pour performances
CREATE INDEX idx_dishingredient_dish     ON dishingredient(id_dish);
CREATE INDEX idx_dishingredient_ingredient ON dishingredient(id_ingredient);



---
-- Enum pour le type de mouvement
DROP TYPE IF EXISTS movement_type CASCADE;
CREATE TYPE movement_type AS ENUM ('IN', 'OUT');

-- Table StockMovement
DROP TABLE IF EXISTS stockmovement CASCADE;
CREATE TABLE stockmovement (
    id                SERIAL PRIMARY KEY,
    id_ingredient     INT NOT NULL REFERENCES ingredient(id) ON DELETE CASCADE,
    quantity          NUMERIC(10,2) NOT NULL,
    type              movement_type NOT NULL,
    unit              unit_type NOT NULL DEFAULT 'KG',
    creation_datetime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_quantity_positive CHECK (quantity > 0)
);


-- Création de la table 
CREATE TABLE IF NOT EXISTS orders (
    id              SERIAL PRIMARY KEY,
    reference       VARCHAR(50) UNIQUE NOT NULL,
    order_date      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_price     NUMERIC(10,2) DEFAULT 0.00,
    order_type      order_type NOT NULL DEFAULT 'EAT_IN',
    order_status    order_status NOT NULL DEFAULT 'CREATED'
    -- Ajoute ici d'autres colonnes si besoin (id_client, etc.)
);

-- Si tu veux ajouter les colonnes sur une table existante qui s'appelle déjà autrement
ALTER TABLE orders
    ADD COLUMN IF NOT EXISTS order_type order_type NOT NULL DEFAULT 'EAT_IN',
    ADD COLUMN IF NOT EXISTS order_status order_status NOT NULL DEFAULT 'CREATED';

---
-- Enum PostgreSQL pour les types
DROP TYPE IF EXISTS order_type CASCADE;
CREATE TYPE order_type AS ENUM ('EAT_IN', 'TAKE_AWAY');

-- Enum pour les statuts
DROP TYPE IF EXISTS order_status CASCADE;
CREATE TYPE order_status AS ENUM ('CREATED', 'READY', 'DELIVERED');

-- Ajout / modification de la table order (si elle existe déjà)
ALTER TABLE "order" 
    ADD COLUMN IF NOT EXISTS order_type order_type NOT NULL DEFAULT 'EAT_IN',
    ADD COLUMN IF NOT EXISTS order_status order_status NOT NULL DEFAULT 'CREATED';