import uuid
from bson.binary import Binary, UuidRepresentation


class Product:
    def __init__(self, name, size, image, url, in_stock, id=None):
        self.__id: uuid = uuid.uuid4() if id is None else id
        # self.__id: str = Binary(uuid.uuid4().bytes, subtype=UuidRepresentation.STANDARD) if id is None else id
        self.__name: str = name
        self.__size: str = size
        self.__image: str = image
        self.__url: str = url
        self.__in_stock: bool = in_stock

    @property
    def id(self):
        return self.__id

    @property
    def name(self):
        return self.__name

    @property
    def size(self):
        return self.__size

    @property
    def url(self):
        return self.__url

    @property
    def in_stock(self):
        return self.__in_stock

    @in_stock.setter
    def in_stock(self, value: bool):
        self.__in_stock = value

    def to_json(self):
        return {
            "_id": self.__id,
            "name": self.__name,
            "size": self.__size,
            "image": self.__image,
            "url": self.__url,
            "in_stock": self.__in_stock
        }