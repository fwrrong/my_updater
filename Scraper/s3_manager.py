import boto3


class S3Manager:
    def __init__(self, bucket):
        self.client = boto3.client('s3',
                                   endpoint_url='http://localhost:4566',  # LocalStack default endpoint
                                   aws_access_key_id='test',  # Placeholder access key
                                   aws_secret_access_key='test',  # Placeholder secret key
                                   region_name='us-east-1'
                                   )
        self.bucket_name = bucket
        self.client.create_bucket(Bucket=self.bucket_name)

    def upload_file(self, file_name, key):
        self.client.upload_file(file_name, key, self.bucket_name)
        return file_name

    def download_file(self, file_name, key):
        self.client.download_file(file_name, key, self.bucket_name)
        return file_name
