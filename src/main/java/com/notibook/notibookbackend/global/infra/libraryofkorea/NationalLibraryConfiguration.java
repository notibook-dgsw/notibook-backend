package com.notibook.notibookbackend.global.infra.libraryofkorea;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@RequiredArgsConstructor
@Configuration
public class NationalLibraryConfiguration {
    @Bean
    public NationalLibraryApi nationalLibraryApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.nl.go.kr/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        return retrofit.create(NationalLibraryApi.class);
    }
}
