insert into dish (id, name, dish_type)
values (1, 'Salaide fraîche', 'STARTER'),
       (2, 'Poulet grillé', 'MAIN'),
       (3, 'Riz aux légumes', 'MAIN'),
       (4, 'Gâteau au chocolat ', 'DESSERT'),
       (5, 'Salade de fruits', 'DESSERT');


insert into ingredient (id, name, category, price, id_dish)
values (1, 'Laitue', 'VEGETABLE', 800.0, 1),
       (2, 'Tomate', 'VEGETABLE', 600.0, 1),
       (3, 'Poulet', 'ANIMAL', 4500.0, 2),
       (4, 'Chocolat ', 'OTHER', 3000.0, 4),
       (5, 'Beurre', 'DAIRY', 2500.0, 4);



update dish
set price = 2000.0
where id = 1;

update dish
set price = 6000.0
where id = 2;





--la suite
-- Dishes (avec selling_price)
INSERT INTO dish (id, name, dish_type, selling_price) VALUES
(1, 'Salade fraîche',   'STARTER',  3500.00),
(2, 'Poulet grillé',    'MAIN',    12000.00),
(3, 'Riz aux légumes',  'MAIN',    NULL),
(4, 'Gâteau au chocolat','DESSERT', 8000.00),
(5, 'Salade de fruits', 'DESSERT',  NULL);

-- Ingrédients uniques (pas de duplication)
INSERT INTO ingredient (id, name, price, category) VALUES
(1, 'Laitue',   800.00,  'VEGETABLE'),
(2, 'Tomate',   600.00,  'VEGETABLE'),
(3, 'Poulet',  4500.00,  'ANIMAL'),
(4, 'Chocolat', 3000.00, 'OTHER'),
(5, 'Beurre',   2500.00, 'DAIRY');

-- Relations + quantités (table dishingredient)
-- id | id_dish | id_ingredient | quantity_required | unit
INSERT INTO dishingredient (id, id_dish, id_ingredient, quantity_required, unit) VALUES
(1, 1, 1, 0.20, 'KG'),   
(2, 1, 2, 0.15, 'KG'),   
(3, 2, 3, 1.00, 'KG'),   
(4, 4, 4, 0.30, 'KG'),   
(5, 4, 5, 0.20, 'KG');  



---
-- Stock initial (mouvements IN au début)
INSERT INTO stockmovement (id, id_ingredient, quantity, type, unit, creation_datetime) VALUES
(1, 1, 5.0,  'IN',  'KG', '2024-01-06 00:00:00'),  -- Laitue initial
(2, 2, 10.0, 'IN',  'KG', '2024-01-06 00:00:00'),  -- Tomate initial
(3, 3, 9.0,  'IN',  'KG', '2024-01-06 00:00:00'),  -- Poulet initial
(4, 4, 3.0,  'IN',  'KG', '2024-01-06 00:00:00'),  -- Beurre initial
(5, 5, 2.5,  'IN',  'KG', '2024-01-06 00:00:00');  -- Chocolat initial

-- Mouvements OUT du test (aux dates indiquées)
INSERT INTO stockmovement (id, id_ingredient, quantity, type, unit, creation_datetime) VALUES
(6,  1, 0.2, 'OUT', 'KG', '2024-01-06 12:00:00'),
(7,  2, 0.15,'OUT', 'KG', '2024-01-06 12:00:00'),
(8,  3, 1.0, 'OUT', 'KG', '2024-01-06 12:00:00'),
(9,  4, 0.3, 'OUT', 'KG', '2024-01-06 12:00:00'),
(10, 5, 0.2, 'OUT', 'KG', '2024-01-06 12:00:00');
