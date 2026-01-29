create database "mini_dish_db";

create user "mini_dish_db_manager" with password '123456';

-- Grant all privileges
GRANT ALL PRIVILEGES ON DATABASE mini_dish_db TO mini_dish_db_manager;

-- Optional but recommended: allow the user to create tables and objects
ALTER DATABASE mini_dish_db OWNER TO mini_dish_db_manager;