from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import requests
from abc import ABC, abstractmethod
from product_manager import ProductManager
from s3_manager import S3Manager
from temporalio import activity

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
    def __init__(self, product_manager: ProductManager, s3_manager: S3Manager, urls: list, css_selector: str, ):
        self.driver = init_selenium_driver()  # TODO: 是否要放到别的activity里
        self.product_manager: ProductManager = product_manager
        self.s3_manager = s3_manager
        self.urls = urls
        self.css_selector = css_selector

    @activity.defn(name="fetch_html")
    def fetch_html(self) -> list[tuple]:
        htmls = []
        for url in self.urls:
            self.driver.get(url)
            try:
                WebDriverWait(self.driver, 10).until(
                    EC.presence_of_element_located((By.CSS_SELECTOR, self.css_selector))
                )
            except:
                print("error")
            html = self.driver.page_source
            htmls.append((url, html))
        # self.driver.close()
        return htmls

    def save_to_db(self, product_list: list):
        if product_list:
            for product in product_list:
                if product.isinstance(tuple):  # update product
                    for query, new_values in product_list:
                        self.product_manager.update_product(query=query, new_values=new_values)
                else:  # insert new product
                    self.product_manager.insert_products(product_list)
        return

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

    @abstractmethod
    def parse_htmls(self, htmls: list):
        pass
