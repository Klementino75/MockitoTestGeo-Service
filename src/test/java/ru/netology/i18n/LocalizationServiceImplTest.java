package ru.netology.i18n;

import org.junit.jupiter.api.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import ru.netology.entity.Country;

import org.hamcrest.MatcherAssert;
import static org.hamcrest.Matchers.is;

@DisplayName("Тест LocalizationServiceImpl")
public class LocalizationServiceImplTest {

    LocalizationServiceImpl loc = new LocalizationServiceImpl();

    @BeforeEach
    void tearUp() {
        System.out.println("Начало теста.");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Окончание теста.\n");
    }

    @DisplayName("Тест locale() на Русский")
    @Test
    void localeTestRus() {
        System.out.println("Тест locale() на вывод русского текста.");
        final Country country = Country.RUSSIA;
        final String expected = "Добро пожаловать!";

        final String result = loc.locale(country);

        MatcherAssert.assertThat(expected, is(result));
        System.out.println(country + " -> " + result);
    }

    @DisplayName("Тест locale() на English")
    @ParameterizedTest(name = "{index} - {0} понимают English")
    @EnumSource(value = Country.class, names = { "USA", "GERMANY", "BRAZIL" })
    void localeTestEnglish(Country country) {
        System.out.println("Parameterized Test locale() на вывод английского текста.");
        final String expected = "Welcome!";

        final String result = loc.locale(country);

        MatcherAssert.assertThat(expected, is(result));
        System.out.println(country + " -> " + result);
    }
}
