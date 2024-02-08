from dataclasses import dataclass

from product_manager import ProductManager
from s3_manager import S3Manager
from scraper import Scraper


@dataclass
class GetScraperParams:
    product_manager: ProductManager
    s3_manager: S3Manager
    urls: list
    brand: str


@dataclass
class ParseHTMLParams:
    scraper: Scraper
    htmls: list


@dataclass
class SaveToDbParams:
    scraper: Scraper
    product_list: list


@dataclass
class WorkflowParams:
    connection_str: str
    bucket_name: str
    conn_params: dict
