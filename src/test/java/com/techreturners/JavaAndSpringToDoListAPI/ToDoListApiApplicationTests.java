package com.techreturners.JavaAndSpringToDoListAPI;

import com.techreturners.JavaAndSpringToDoListAPI.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ToDoListApiApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenPostRequestThenTaskCreated() {
        var task = Task.of("Gardening", false);

        webTestClient.post().uri("/tasks").bodyValue(task).exchange().expectStatus().isCreated().expectBody(Task.class).value(actualTask -> {
            assertThat(actualTask).isNotNull();
            assertThat(actualTask.description()).isEqualTo(task.description());
        });
        webTestClient.get().uri("/tasks").exchange().expectStatus().isOk().expectBodyList(Task.class).hasSize(1);

    }

    @Test
    void whenGetRequestBeforeAddingTasksThenShowListSizeZero() {
        webTestClient.get().uri("/tasks").exchange().expectStatus().isOk().expectBodyList(Task.class).hasSize(0);
    }

    @Test
    void whenPostWithInvalidDescriptionThenShowError() {
        var actualTask = Task.of("Ga", false);

        webTestClient.post().uri("/tasks")
                .bodyValue(actualTask)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Task.class)
                .value(response -> {
                    assertThat(response.description())
                            .isEqualTo("The task should at least be 3 characters long");
                });
    }
}
