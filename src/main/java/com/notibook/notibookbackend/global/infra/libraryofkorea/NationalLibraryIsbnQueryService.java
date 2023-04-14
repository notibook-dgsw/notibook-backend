package com.notibook.notibookbackend.global.infra.libraryofkorea;

import com.notibook.notibookbackend.domain.book.entity.BookEntity;
import com.notibook.notibookbackend.domain.book.service.BookSummerizeService;
import com.notibook.notibookbackend.domain.book.service.IsbnQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import retrofit2.Response;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Component
public class NationalLibraryIsbnQueryService implements IsbnQueryService {
    private final Environment environment;
    private final NationalLibraryApi nationalLibraryApi;
    private final BookSummerizeService bookSummerizeService;

    private Integer stringToPage(String stringPage) {
        final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");

        if(stringPage == null || stringPage.isEmpty()) return null;
        Matcher matcher = NUMBER_PATTERN.matcher(stringPage);
        if(matcher.find()) {
            return Integer.parseInt(matcher.group());
        } else return null;
    }

    @Override
    public Optional<BookEntity> searchBookByIsbn(String isbn) {
        final String API_KEY = environment.getProperty("NATIONAL_LIBRARY_API_KEY");

        try {
            Response<NationalLibraryApi.ISBNQueryPage> response = nationalLibraryApi.queryISBN(API_KEY, "json", 1, 1, isbn).execute();
            NationalLibraryApi.ISBNQueryResult result = response.body().getDocs().get(0);

            return Optional.of(BookEntity.builder()
                    .isbn(isbn)
                    .title(result.getTitle())
                    .author(result.getAuthor())
                    .pageCount(stringToPage(result.getPage()))
                    .summary(bookSummerizeService.summerize(result.getTitle(), result.getAuthor()))
                    .build()
            );
        } catch (IndexOutOfBoundsException exception) {
            exception.printStackTrace();
            return Optional.empty();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
