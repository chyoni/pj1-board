from locust import HttpUser, task, between
import random

class Board(HttpUser):

    def on_start(self):
        self.client.post("/users/login", json={"userId": "admin", "password": "123"})

    @task
    def search(self):
        sortStatus = random.choice(["CATEGORIES", "NEWEST", "OLDEST"])
        categoryId = random.randint(2, 3)
        name = f"테스트 게시글{random.randint(1, 1000000)}"
        headers = {"Content-Type": "application/json"}
        data = {
            "sortStatus": sortStatus,
            "categoryId": categoryId,
            "name": name
        }

        self.client.post("/posts/search", json=data, headers=headers)


