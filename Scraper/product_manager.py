from typing import List
from pymongo import MongoClient
from product import Product


class ProductManager:
    def __init__(self, connection_str: str):
        client = MongoClient(connection_str, uuidRepresentation="standard")
        db = client["updater_db"]
        self.collection = db["products"]

    def insert_products(self, product_list: List[Product]):
        inserted_document = [p.to_json() for p in product_list]
        self.collection.insert_many(inserted_document)

    def update_products(self, product_list: List[Product]):
        for product_data in product_list:
            query = {"size": product_data.size, "url": product_data.url}
            new_values = {"$set": {"in_stock": product_data.in_stock}}
            self.collection.update_one(query, new_values)

    def update_product(self, query, new_values):
        self.collection.update_one(query, new_values)

    def find_product(self, query):
        document = self.collection.find_one(query)
        if document:
            return Product(name=document.get("name"),
                           size=document.get("size"),
                           image=document.get("image"),
                           url=document.get("url"),
                           in_stock=document.get("in_stock"),
                           id=document.get("id")
                           )
        else:
            return None

    def is_find_product(self, url: str):
        product = self.collection.find_one({"url": url})
        return product is not None
