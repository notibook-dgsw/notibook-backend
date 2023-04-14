package com.notibook.notibookbackend.global.infra.libraryofkorea;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface NationalLibraryApi {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @NoArgsConstructor
    class ISBNQueryPage {
        private List<ISBNQueryResult> docs;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @NoArgsConstructor
    class ISBNQueryResult {
        @JsonProperty("TITLE")
        private String title;

        @JsonProperty("AUTHOR")
        private String author;

        @JsonProperty("PAGE")
        private String page;
    }

    @GET("seoji/SearchApi.do")
    Call<ISBNQueryPage> queryISBN(
            @Query("cert_key") String apiKey,
            @Query("result_style") String resultType,
            @Query("page_no") Integer page,
            @Query("page_size") Integer pageSize,
            @Query("isbn") String isbn);
}
