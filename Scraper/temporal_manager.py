import uuid

from temporalio.client import Client
from temporalio.worker import Worker

import scraper_activities
import scraper_workflow
from data_object import WorkflowParams
from scraper_workflow import ScraperWorkflow


class TemporalManager:
    def __init__(self, address: str, connection_str: str, bucket_name: str, conn_params: dict):
        self.address = address
        self.client = None
        self.connection_str = connection_str
        self.bucket_name = bucket_name
        self.conn_params = conn_params

    async def connect(self):
        self.client = await Client.connect(self.address)

    async def start_worker(self):
        if not self.client:
            await self.connect()
        worker = Worker(
            self.client,
            task_queue="scraper-task-queue",
            workflows=[scraper_workflow.ScraperWorkflow],
            activities=[scraper_activities.get_product_manager,
                        scraper_activities.get_s3_manager,
                        scraper_activities.fetch_urls,
                        scraper_activities.get_scraper,
                        scraper_activities.fetch_html_activity,
                        scraper_activities.parse_html_activity,
                        scraper_activities.save_to_db_activity
                        ],
        )
        await worker.run()

    async def execute_workflow(self):
        if not self.client:
            await self.connect()

        # logger = logging.getLogger(__name__)

        batch_id = str(uuid.uuid4())

        return await self.client.execute_workflow(

            workflow=ScraperWorkflow.run,
            args=(self.connection_str,
                  self.bucket_name,
                  self.conn_params),
            id=f"scraper-{batch_id}",
            task_queue="your-task-queue",
        )
