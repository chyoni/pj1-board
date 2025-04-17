from locust import HttpUser, task, between, constant
import random

class AddPosts(HttpUser):
    wait_time = constant(1)

    user_ids = [
        "user766012", "user734460", "user771177", "user694673", "user318369", "user688967",
        "user106744", "user735110", "user887094", "user932585", "user685511", "user791797",
        "user489617", "user445143", "user327235", "user104908", "user834000", "user343050",
        "user450862", "user958136", "user807822", "user310292", "user966804", "user899631",
        "user665413", "user322882", "user124163", "user36651", "user703571", "user482565",
        "user518499", "user658058", "user710964", "user569729", "user463689", "user983897",
        "user336252", "user844763", "user193054", "user236243", "user919318", "user536265",
        "user448041", "user661662", "user966659", "user579161", "user379450", "user911345",
        "user380073", "user23883", "user990908", "user954126", "user250063", "user883915",
        "user368579", "user889814", "user75746", "admin"
    ]

    def on_start(self):
        self.user_id = random.choice(self.user_ids)
        self.client.post("/users/login", json={"userId": self.user_id, "password": "123"})


    @task
    def add_post(self):
        self.client.post("/posts", json={
            "name": "테스트 게시글" + str(random.randint(1, 1000000)),
            "contents": "테스트 컨텐츠" + str(random.randint(1, 1000000)),
            "categoryId": random.randint(2, 3),
        })











