package com.bebit.rabbitreactive;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(classes = RabbitReactiveApplication.class)
class RabbitReactiveApplicationTests {

  @Test
  public void whenSendingAMessageToQueue_thenAcceptedReturnCode() throws InterruptedException {

    WebTestClient client = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
    List<User> users = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      User user = new User();
      user.setId(i);
      user.setName(UUID.randomUUID().toString().substring(1, 10).replace("-", ""));
      user.setAddress(UUID.randomUUID().toString().substring(1, 10).replace("-", ""));
      users.add(user);
      // TimeUnit.MILLISECONDS.wait(500);
      TimeUnit.MILLISECONDS.sleep(500);
      client.post().uri("/queue/NYSE").bodyValue(user).exchange().expectStatus().isAccepted();
    }

    // client.post().uri("/queue/NYSE").syncBody(users).exchange().expectStatus().isAccepted();

  }

  static class User {
    private int id;
    private String name;
    private String address;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getAddress() {
      return address;
    }

    public void setAddress(String address) {
      this.address = address;
    }

  }

}
