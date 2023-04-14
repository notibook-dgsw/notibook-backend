package com.notibook.notibookbackend.global.infra.chatgpt;

import com.notibook.notibookbackend.domain.book.entity.BookEntity;
import com.notibook.notibookbackend.domain.book.service.BookRecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatGptBookRecommendService implements BookRecommendService {
    private final Environment environment;
    private final ChatGptApi chatGptApi;

    private List<BookEntity> getRecommendCandidates(List<BookEntity> books, int maxSize) {
        final Random random = new Random();
        List<BookEntity> result = new ArrayList<>();

        int size = books.size();
        int n = 0;
        BookEntity book = null;
        while(n < maxSize) {
            book = books.get(random.nextInt(size));

            if(n == size)
                break;

            if (!result.contains(result)) {
                result.add(book);
                n++;
            }
        }

        return result;
    }

    @Override
    public String recommend(List<BookEntity> books) {
        final String API_KEY_HEADER = String.format("Bearer %s", environment.getProperty("OPENAI_API_KEY"));

        String prompt = String.format("%s를 읽은 사람에게 추천해 줄 책을 3권 알려줘:",
                getRecommendCandidates(books, 3).stream()
                        .map(book -> String.format("<%s>", book.getTitle()))
                        .collect(Collectors.joining(", ")));
        System.out.println(prompt);

        try {
            ChatGptApi.ChatGptCompletionResponse response =
                    chatGptApi.complete(API_KEY_HEADER, ChatGptApi.ChatGptCompletionRequest.builder()
                            .prompt(prompt)
                            .model("text-davinci-003")
                            .maxTokens(300)
                            .build()).execute().body();

            return response
                    .getChoices().stream().findFirst()
                    .map(result -> result.getText())
                    .orElseThrow();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
