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
                        id UUID NOT NULL DEFAULT uuid_generate_v4(),
                        user_id UUID,
                        product_id UUID,
                        FOREIGN KEY (user_id) REFERENCES app_user(id),
                        PRIMARY KEY (id)
);

-- Insert data into the follow table
INSERT INTO follow (user_id, product_id) VALUES
                                                 ('4d174e35-57f6-402d-b621-1a5a78f4ec9a', 'ffbfc587-a458-4940-9bef-049fe2857dc0'),
                                                 ('1a03359d-f78a-4970-859e-1ba14c211908', 'ffbfc587-a458-4940-9bef-049fe2857dc0'),
                                                 ('d7fb7a46-7276-4f8b-b4a4-f1001a1963ed', 'ffbfc587-a458-4940-9bef-049fe2857dc0'),
                                                 ('14f741f2-7424-477e-89ab-3fafa754332e', 'ffbfc587-a458-4940-9bef-049fe2857dc0'),
                                                 ('d7fb7a46-7276-4f8b-b4a4-f1001a1963ed', 'ffbfc587-a458-4940-9bef-049fe2857dc0');

CREATE TABLE  IF NOT EXISTS product_urls (
    brand varchar(255),
    url varchar(255),
    PRIMARY KEY (url)
);

INSERT INTO product_urls (brand, url) VALUES
                                          ('nike', 'https://www.nike.com/t/air-force-1-07-mens-shoes-jBrhbr/CW2288-001'),
                                          ('nike', 'https://www.nike.com/t/air-force-1-07-mens-shoes-jBrhbr/CW2288-111'),
                                          ('nike', 'https://www.nike.com/t/air-force-1-07-mens-shoes-jBrhbr/CT2302-002'),
                                          ('nike', 'https://www.nike.com/t/air-force-1-07-mens-shoes-jBrhbr/CT2302-100'),
                                          ('nike', 'https://www.nike.com/t/air-jordan-1-mid-womens-shoes-Kg3nnj/DV0991-111'),
                                          ('nike', 'https://www.nike.com/t/air-jordan-1-mid-womens-shoes-Kg3nnj/DV0991-001');


CREATE INDEX brand_url_index ON product_urls(brand);