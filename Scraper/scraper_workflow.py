import logging
from datetime import timedelta

from temporalio import workflow
from selenium import webdriver

from data_object import GetScraperParams, ParseHTMLParams, SaveToDbParams
from scraper_activities import get_product_manager, get_s3_manager, fetch_urls, fetch_html_activity, get_scraper, \
    parse_html_activity, save_to_db_activity

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


@workflow.defn(name="ScraperWorkflow")
class ScraperWorkflow:
    def __init__(self):
        self.driver = init_selenium_driver()

    @workflow.run
    async def run(self, connection_str: str, bucket_name: str, conn_params: dict):
        logger = logging.getLogger(__name__)
        logger.info("Starting workflow")
        product_manager = await workflow.execute_activity(
            get_product_manager,
            connection_str,
            start_to_close_timeout=timedelta(seconds=10),
        )
        s3_manager = await workflow.execute_activity(
            get_s3_manager,
            bucket_name,
            start_to_close_timeout=timedelta(seconds=10),
        )
        urls = await workflow.execute_activity(
            fetch_urls,
            conn_params,
            start_to_close_timeout=timedelta(seconds=10),
        )
        for brand, urls in urls.items():
            scraper = await workflow.execute_activity(
                get_scraper,
                GetScraperParams(product_manager, s3_manager, urls, brand),
                start_to_close_timeout=timedelta(seconds=10),
            )

            htmls = await workflow.execute_activity(
                fetch_html_activity,
                scraper,
                start_to_close_timeout=timedelta(seconds=10),
            )

            product_list = await workflow.execute_activity(
                parse_html_activity,
                ParseHTMLParams(scraper, htmls),
                start_to_close_timeout=timedelta(seconds=10),
            )

            await workflow.execute_activity(
                save_to_db_activity,
                SaveToDbParams(scraper, product_list),
                start_to_close_timeout=timedelta(seconds=10),
            )

        logger.info("Workflow completed")
        return
