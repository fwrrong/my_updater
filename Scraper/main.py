from product_manager import ProductManager
from s3_manager import S3Manager
from scraper_nike import NikeScraper
from url_fetcher import URLFetcher


def main():
    conn_params = {
        "dbname": "updater",
        "user": "postgres",
        "password": "postgres",
        "host": "localhost",
        "port": "5432",
    }
    connection_str = "mongodb://root:password@localhost:27017/admin"
    product_manager = ProductManager(connection_str)
    url_fetcher = URLFetcher(conn_params)
    s3_manager = S3Manager('image')
    nike_scraper = NikeScraper(product_manager, url_fetcher, s3_manager)
    nike_scraper.parse_html()


if __name__ == "__main__":
    main()