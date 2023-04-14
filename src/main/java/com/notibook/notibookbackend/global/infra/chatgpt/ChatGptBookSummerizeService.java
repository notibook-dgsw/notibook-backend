package com.notibook.notibookbackend.global.infra.chatgpt;

import com.notibook.notibookbackend.domain.book.service.BookSummerizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ChatGptBookSummerizeService implements BookSummerizeService {
    private final Environment environment;
    private final ChatGptApi chatGptApi;

    @Override
    public String summerize(String title, String author) {
        final String API_KEY_HEADER = String.format("Bearer %s", environment.getProperty("OPENAI_API_KEY"));

        String prompt = String.format("%s의 <%s> 한줄 요약:", author, title);
        try {
            ChatGptApi.ChatGptCompletionResponse response =
                    chatGptApi.complete(API_KEY_HEADER, ChatGptApi.ChatGptCompletionRequest.builder()
                            .prompt(prompt)
                            .model("text-davinci-003")
                            .maxTokens(350)
                            .build()).execute().body();

            return response
                    .getChoices().stream().findFirst()
                    .map(result -> result.getText().endsWith(".") ?
                            result.getText() : String.format("%s⋯", result.getText()))
                    .orElseThrow();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
