from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import requests
from bs4 import BeautifulSoup
import pymongo
from pymongo import MongoClient, UpdateOne
import uuid
import os
import psycopg2
from abc import ABC, abstractmethod
from overrides import overrides
from product import Product
from product_manager import ProductManager
from s3_manager import S3Manager
from scraper import Scraper
from url_fetcher import URLFetcher


class NikeScraper(Scraper):
    def __init__(self, product_manager: ProductManager, url_fetcher: URLFetcher, s3_manager: S3Manager):
        super().__init__(product_manager, url_fetcher, s3_manager)

    @overrides
    def parse_html(self):
        target_selector = "#buyTools > div:nth-child(1) > fieldset > div"
        urls = self.url_fetcher.get_url("nike")
        for url in urls:
            html = self.fetch_html_with_chrome(url=url, css_selector=target_selector)
            soup = BeautifulSoup(html, "html.parser")
            if self.product_manager.is_find_product(url):  # the product is already in mongodb
                print("the product is already in mongodb")
                self.parse_old_html(url, soup)
            else:
                print("the product is not in mongodb")
                product_list = self.parse_new_html(url, html, soup)
                self.product_manager.insert_products(product_list)

    def parse_old_html(self, url: str, soup):
        size_tags = soup.find("fieldset", {"class": "mt5-sm mb3-sm body-2 css-1pj6y87"})

        product_data_list = []
        for div in size_tags.find_all("div"):
            input_tag = div.find("input", {"name": "skuAndSize"})
            label_tag = div.find("label")

            # Proceed only if both input and label tags are found within the div
            if input_tag and label_tag:
                query = {
                    "size": label_tag.text,
                    "url": url,
                }
                new_values = {"$set": {"in_stock": not input_tag.has_attr("disabled")}}
                self.product_manager.update_product(query=query, new_values=new_values)

    def parse_new_html(self, url: str, html: str, soup):
        product_name = soup.title.text if soup.title else "Unknown Product"

        product_code = url.split("/")[-1]
        image_directory = "images/nike"
        # img_path = f"{image_directory}/{product_code}.png"
        image_tag = soup.find("img", {"aria-hidden": "false"})
        img_path = self.download_img(image_tag, image_directory, product_code)

        size_tags = soup.find("fieldset", {"class": "mt5-sm mb3-sm body-2 css-1pj6y87"})

        product_data_list = []
        for div in size_tags.find_all("div"):
            input_tag = div.find("input", {"name": "skuAndSize"})
            label_tag = div.find("label")

            # Proceed only if both input and label tags are found within the div
            if input_tag and label_tag:
                size = label_tag.text
                in_stock = not input_tag.has_attr("disabled")
                product = Product(product_name, size, img_path, url, in_stock)
                product_data_list.append(product)

        return product_data_list


