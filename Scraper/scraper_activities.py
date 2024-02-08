from temporalio import activity

from product_manager import ProductManager
from s3_manager import S3Manager
from scraper import Scraper
from scraper_nike import NikeScraper
from data_object import GetScraperParams, ParseHTMLParams, SaveToDbParams
from url_fetcher import URLFetcher


@activity.defn(name="get_product_manager")
async def get_product_manager(connection_str):
    return ProductManager(connection_str)


@activity.defn(name="get_s3_manager")
async def get_s3_manager(bucket):
    return S3Manager(bucket)


@activity.defn(name="fetch_urls")
async def fetch_urls(conn_params):
    url_fetcher = URLFetcher(conn_params)
    return url_fetcher.get_all_brand_urls()


@activity.defn(name="get_scraper")
async def get_scraper(params: GetScraperParams):
    # product_manager: ProductManager, s3_manager: S3Manager,
    # urls: list, css_selector: str, brand: str):
    if params.brand == 'nike':
        css_selector = "#buyTools > div:nth-child(1) > fieldset > div"
        return NikeScraper(params.product_manager, params.s3_manager, params.urls, css_selector=css_selector)
    return


@activity.defn(name="fetch_html_activity")
async def fetch_html_activity(scraper: Scraper):
    return scraper.fetch_html()


@activity.defn(name="parse_html_activity")
async def parse_html_activity(params: ParseHTMLParams):
    # scraper: Scraper, htmls: list
    return params.scraper.parse_htmls(params.htmls)


@activity.defn(name="save_to_db_activity")
async def save_to_db_activity(params: SaveToDbParams):
    # scraper: Scraper, product_list:list):
    return params.scraper.save_to_db(params.product_list)
