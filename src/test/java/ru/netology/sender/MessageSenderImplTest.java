package ru.netology.sender;

import org.junit.jupiter.api.*;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import ru.netology.entity.*;
import ru.netology.geo.*;
import ru.netology.i18n.*;

import java.util.HashMap;
import java.util.Map;

@DisplayName("Тест MessageSenderImpl")
public class MessageSenderImplTest {

    GeoService geoService = Mockito.mock(GeoServiceImpl.class);
    LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
    MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
    Map<String, String> headers = new HashMap<>();

    @BeforeEach
    void tearUp() {
        System.out.println("Начало теста.");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Окончание теста.\n");
    }

    @DisplayName("Тест send() на русский язык")
    @Test
    void sendTestRUS() {
        System.out.println("Тест send() на отправку русского текста.");
        Mockito.when(geoService.byIp(Mockito.anyString())).thenReturn(new Location("Moscow",
                Country.RUSSIA, null, 0));
        Mockito.when(localizationService.locale(Mockito.any())).thenReturn("Добро пожаловать!");

        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.");
        String expected = "Добро пожаловать!";

        String result = messageSender.send(headers);

        Assertions.assertEquals(expected, result);
        System.out.println(Country.RUSSIA + " -> " + result);
    }

    @DisplayName("Тест send() на English")
    @Test
    void sendTestUSA() {
        System.out.println("Тест send() на отправку English.");
        Mockito.when(geoService.byIp(Mockito.anyString())).thenReturn(new Location("New York",
                Country.USA, null, 0));
        Mockito.when(localizationService.locale(Mockito.any())).thenReturn("Welcome!");

        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.");
        String expected = "Welcome!";
        String result = messageSender.send(headers);

        Assertions.assertEquals(expected, result);
        System.out.println(Country.USA + " -> " + result);
    }

    @DisplayName("Тест на Verify")
    @Test
    void sendTestRUSVerify() {
        Mockito.when(geoService.byIp(Mockito.anyString())).thenReturn(new Location("Moscow",
                Country.RUSSIA, null, 0));
        Mockito.when(localizationService.locale(Mockito.any())).thenReturn("Добро пожаловать!");

        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.");
        String expected = "Добро пожаловать!";
        String result = messageSender.send(headers);
        Mockito.verify(geoService, Mockito.times(1)).byIp("172.");
    }

    @DisplayName("Тест на ArgumentCaptor")
    @Test
    void sendTestRUSCaptor() {
        Mockito.when(geoService.byIp(Mockito.anyString())).thenReturn(new Location("Moscow",
                Country.RUSSIA, null, 0));
        Mockito.when(localizationService.locale(Mockito.any())).thenReturn("Добро пожаловать!");

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.");
        messageSender.send(headers);

        Mockito.verify(geoService).byIp(argumentCaptor.capture());
        Assertions.assertEquals(headers.get(MessageSenderImpl.IP_ADDRESS_HEADER),
                argumentCaptor.getValue());
    }
}
