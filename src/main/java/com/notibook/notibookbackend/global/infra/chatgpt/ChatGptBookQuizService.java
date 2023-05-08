package com.notibook.notibookbackend.global.infra.chatgpt;

import com.notibook.notibookbackend.domain.book.presentation.dto.response.BookQuizResponse;
import com.notibook.notibookbackend.domain.book.service.BookQuizService;
import com.notibook.notibookbackend.domain.book.service.BookSummerizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ChatGptBookQuizService implements BookQuizService {
    private final Environment environment;
    private final ChatGptApi chatGptApi;

    @Override
    public BookQuizResponse generateQuiz(String title) {
        final String API_KEY_HEADER = String.format("Bearer %s", environment.getProperty("OPENAI_API_KEY"));

        String prompt = String.format("<%s> 책 퀴즈 문제 하나:", title);
        try {
            ChatGptApi.ChatGptCompletionResponse response =
                    chatGptApi.complete(API_KEY_HEADER, ChatGptApi.ChatGptCompletionRequest.builder()
                            .prompt(prompt)
                            .model("text-davinci-003")
                            .maxTokens(350)
                            .build()).execute().body();

            // TODO: 선택 표시
            System.out.println(response.getChoices());

            return BookQuizResponse.builder()
                    .question("질문")
                    .answer("답")
                    .build();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
