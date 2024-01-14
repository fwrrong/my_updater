CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create the app_user table
CREATE TABLE IF NOT EXISTS app_user (
                          id UUID PRIMARY KEY,
                          email VARCHAR(255),
                          name VARCHAR(255),
                          password VARCHAR(255)
);

-- Insert data into the app_user table
INSERT INTO app_user (id, email, name, password) VALUES
                                                     ('d7fb7a46-7276-4f8b-b4a4-f1001a1963ed', 'email1', 'name1', 'password1'),
                                                     ('1a03359d-f78a-4970-859e-1ba14c211908', 'email2', 'name2', 'password2'),
                                                     ('04fd1b10-feab-4d93-af98-4fa6a57974c4', 'email3', 'name3', 'password3'),
                                                     ('14f741f2-7424-477e-89ab-3fafa754332e', 'email4', 'name4', 'password4'),
                                                     ('4d174e35-57f6-402d-b621-1a5a78f4ec9a', 'email5', 'name5', 'password5');


-- -- Create the product table
-- CREATE TABLE IF NOT EXISTS product (
--                          id UUID PRIMARY KEY,
--                          name VARCHAR(255),
--                          image_url VARCHAR(255),
--                          link_url VARCHAR(255)
-- );
--
-- -- Insert data into the product table
-- INSERT INTO product (id, name, image_url, link_url) VALUES
--                                                         ('dddf3243-4272-4b9c-bd87-b1334d1ea1d5', 'Product Name 1', 'Image URL 1', 'Link URL 1'),
--                                                         ('d12cd055-bce2-4243-a599-76e26d5b1d6f', 'Product Name 2', 'Image URL 2', 'Link URL 2'),
--                                                         ('8e124d6c-5331-44cd-a977-109ad0c2a9a8', 'Product Name 3', 'Image URL 3', 'Link URL 3'),
--                                                         ('d12c69db-751f-4265-a531-f178c96749ce', 'Product Name 4', 'Image URL 4', 'Link URL 4'),
--                                                         ('c95e87bc-e6fe-4c7e-b10d-dc7fa99a9200', 'Product Name 5', 'Image URL 5', 'Link URL 5');


-- Create the follow table
CREATE TABLE IF NOT EXISTS follow (
                        id UUID PRIMARY KEY,
                        user_id UUID,
                        product_id UUID,
                        FOREIGN KEY (user_id) REFERENCES app_user(id),
                        FOREIGN KEY (product_id) REFERENCES product(id)
);

-- Insert data into the follow table
INSERT INTO follow (id, user_id, product_id) VALUES
                                                 ('37ba0511-7803-4e66-a54c-a353f1f92a77', '4d174e35-57f6-402d-b621-1a5a78f4ec9a', 'c95e87bc-e6fe-4c7e-b10d-dc7fa99a9200'),
                                                 ('72e6f680-62a3-4151-b227-afe27046db79', '1a03359d-f78a-4970-859e-1ba14c211908', 'd12cd055-bce2-4243-a599-76e26d5b1d6f'),
                                                 ('7de650a1-10d1-4850-8ab2-b2e96ccdccdf', 'd7fb7a46-7276-4f8b-b4a4-f1001a1963ed', 'd12cd055-bce2-4243-a599-76e26d5b1d6f'),
                                                 ('813e90f9-33f3-4464-a6df-0d877cecaf79', '14f741f2-7424-477e-89ab-3fafa754332e', 'd12c69db-751f-4265-a531-f178c96749ce'),
                                                 ('930dd3cd-4dd3-4b10-8112-3a84289a775e', 'd7fb7a46-7276-4f8b-b4a4-f1001a1963ed', 'd12cd055-bce2-4243-a599-76e26d5b1d6f');
