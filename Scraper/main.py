import asyncio
from temporal_manager import TemporalManager


async def main():
    conn_params = {
        "dbname": "updater",
        "user": "postgres",
        "password": "postgres",
        "host": "localhost",
        "port": "5432",
    }
    connection_str = "mongodb://root:password@localhost:27017/admin"
    bucket_name = "images"
    manager = TemporalManager(
        address="localhost:7233",
        connection_str=connection_str,
        bucket_name=bucket_name,
        conn_params=conn_params,
    )

    await manager.connect()

    await manager.execute_workflow()

    # product_manager = ProductManager(connection_str)
    # url_fetcher = URLFetcher(conn_params)
    # s3_manager = S3Manager('image')
    # urls = url_fetcher.get_all_brand_urls()
    # for brand, urls in urls.items():
    #     if brand == 'nike':
    #         scraper = NikeScraper(product_manager, s3_manager, urls)
    #         target_selector = "#buyTools > div:nth-child(1) > fieldset > div"
    #     elif brand == 'lv':
    #         pass
    #     htmls = scraper.fetch_html()
    #     product_list = scraper.parse_htmls(htmls)
    #     scraper.save_to_db(product_list)


if __name__ == "__main__":
    asyncio.run(main())
    # main()
