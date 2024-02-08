from typing import Dict

import psycopg2
from temporalio import activity
from collections import defaultdict


class URLFetcher:
    def __init__(self, conn_params: Dict[str, str]):
        self.conn = psycopg2.connect(**conn_params)
        self.cursor = self.conn.cursor()

    @activity.defn(name="fetch_urls")
    def get_all_brand_urls(self):
        # SQL query to fetch all brands and their URLs
        query = "SELECT brand, url FROM product_urls"

        # Execute the query
        self.cursor.execute(query)

        # Fetch all brand-url pairs
        brand_url_pairs = self.cursor.fetchall()  # This will be a list of tuples

        # Initialize an empty dictionary to hold the brand and URLs
        brand_urls_dict = defaultdict(list)

        # Iterate through the fetched brand-url pairs
        for brand, url in brand_url_pairs:
            brand_urls_dict[brand].append(url)

        return brand_urls_dict

    def get_url(self, brand: str):
        # SQL query to fetch URLs
        query = f"SELECT url FROM product_urls WHERE brand = '{brand}'"

        # Execute the query
        self.cursor.execute(query)

        # Fetch all URLs
        urls = self.cursor.fetchall()  # This will be a list of tuples

        # Extract URLs from tuples
        return [url[0] for url in urls]

    def close(self):
        # Close the cursor and connection
        self.cursor.close()
        self.conn.close()
