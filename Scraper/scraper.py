import os
import re

from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import requests
from bs4 import BeautifulSoup
import pymongo
from abc import ABC, abstractmethod
from pymongo import MongoClient
from product_manager import ProductManager
from s3_manager import S3Manager
from url_fetcher import URLFetcher

CHROME_DRIVER_PATH = (
    "/Users/fanweijun/Desktop/ABX_project/Scraper/chromedriver-mac-arm64/chromedriver"
)


def init_selenium_driver():
    options = webdriver.ChromeOptions()
    options.add_argument("--headless=new")
    chrome_service = webdriver.chrome.service.Service(
        executable_path=CHROME_DRIVER_PATH
    )
    return webdriver.Chrome(options=options, service=chrome_service)


class Scraper(ABC):
    def __init__(self, product_manager: ProductManager, url_fetcher: URLFetcher, s3_manager: S3Manager):
        self.driver = init_selenium_driver()
        self.product_manager: ProductManager = product_manager
        self.url_fetcher = url_fetcher
        self.s3_manager = s3_manager

    # @staticmethod
    # def fetch_html(url: str) -> str:
    #     headers = {
    #         "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36",
    #         "Referer": "https://www.nike.com/w/air-force-1-shoes-5sj3yzy7ok",
    #     }
    #     response = requests.get(url, headers=headers)
    #     if response.status_code != 200:
    #         return ""
    #     print(response.status_code)
    #     html = response.text
    #     with open(f"{url}.html", "w") as f:
    #         f.write(html)
    #
    #     return f"{url}.html"

    def fetch_html_with_chrome(self, url: str, css_selector) -> str:

        # headers = {
        #     "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36",
        #     "Referer": "https://www.nike.com/w/air-force-1-shoes-5sj3yzy7ok",
        # }
        self.driver.get(url)

        try:
            element = WebDriverWait(self.driver, 10).until(
                EC.presence_of_element_located((By.CSS_SELECTOR, css_selector))
            )
        except:
            print("error")

        html = self.driver.page_source
        # safe_filename = re.sub(r"[^\w\-_\. ]", '_', url)
        # with open(f"{safe_filename}.html", "w") as f:
        #     f.write(html)
        return html

    @abstractmethod
    def parse_html(self):
        pass

    # @abstractmethod
    # def save_to_db(self):
    #     pass

    def download_img(self, image_tag, image_directory, product_code):
        if image_tag:
            image_url = image_tag["src"]
            response = requests.get(image_url, stream=True)
            if response.status_code == 200:
                s3_path = f"{image_directory}/{product_code}.png"
                self.s3_manager.client.upload_fileobj(response.raw, Key=s3_path, Bucket=self.s3_manager.bucket_name)
                return f"s3://{self.s3_manager.bucket_name}/{s3_path}"
            else:
                print(f"Failed to download image from {image_url}")
        else:
            print("Image tag is empty.")
        return "Image not found or download failed"
