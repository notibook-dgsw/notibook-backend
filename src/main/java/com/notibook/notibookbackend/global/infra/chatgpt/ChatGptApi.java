package com.notibook.notibookbackend.global.infra.chatgpt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

import java.util.List;

public interface ChatGptApi {
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NoArgsConstructor
    class ChatGptCompletionResponse {
        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        @NoArgsConstructor
        public static class ChatGptCompletionChoice {
            private String text;
            private Integer index;

            @JsonProperty("finish_reason")
            private String finishReason;
        }

        private String id;
        private String model;
        private List<ChatGptCompletionChoice> choices;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    class ChatGptCompletionRequest {
        private String model;
        private String prompt;

        @JsonProperty("max_tokens")
        private Integer maxTokens;
    }

    @POST("/v1/completions")
    Call<ChatGptCompletionResponse> complete(
            @Header("Authorization") String bearerKey,
            @Body ChatGptCompletionRequest option);
}
