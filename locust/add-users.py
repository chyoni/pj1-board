from locust import HttpUser, task, between
import random

class AddUsers(HttpUser):
    wait_time = between(1, 3)

    @task
    def add_user(self):
        self.client.post("/users/signup", json={
            "userId": "user" + str(random.randint(1, 1000000)),
            "password": "123",
            "nickname": "user" + str(random.randint(1, 1000000)),
            "admin": False,
            "withdraw": False,
        })