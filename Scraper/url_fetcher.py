from typing import Dict

import psycopg2
from psycopg2._psycopg import cursor


class URLFetcher:
    def __init__(self, conn_params: Dict[str, str]):
        self.conn = psycopg2.connect(**conn_params)
        self.cursor = self.conn.cursor()

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

