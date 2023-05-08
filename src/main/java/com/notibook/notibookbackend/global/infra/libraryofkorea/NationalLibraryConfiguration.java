package com.notibook.notibookbackend.global.infra.libraryofkorea;

import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Configuration
public class NationalLibraryConfiguration {
    @Bean
    public NationalLibraryApi nationalLibraryApi() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.nl.go.kr/")
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        return retrofit.create(NationalLibraryApi.class);
    }
}
