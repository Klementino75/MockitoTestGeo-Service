package ru.netology.geo;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import ru.netology.entity.*;

import java.util.stream.Stream;

import org.hamcrest.MatcherAssert;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Тест GeoServiceImpl")
public class GeoServiceImplTest {
    GeoServiceImpl geoService = new GeoServiceImpl();

    @BeforeEach
    void tearUp() {
        System.out.println("Начало теста.");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Окончание теста.\n");
    }

    @DisplayName("Test byIp() Full IP")
    @ParameterizedTest
    @MethodSource
    public void byIpTestParamsFullIP(String ip, Country country, String city, String street, int builing) {
        System.out.println("Parameterized Test byIp() на локацию по полному IP.");

        final Location result = geoService.byIp(ip);

        Assertions.assertEquals(country, result.getCountry());
        Assertions.assertEquals(street, result.getStreet());
        Assertions.assertEquals(city, result.getCity());
        Assertions.assertEquals(builing, result.getBuiling());
        System.out.println(ip + " -> "
                + result.getCountry() + ", "
                + result.getCity() + ", "
                + result.getStreet() + " "
                + result.getBuiling());
    }
    public static Stream<Arguments> byIpTestParamsFullIP() {
        return Stream.of(Arguments.of(GeoServiceImpl.LOCALHOST,
                        null, null, null, 0),
                Arguments.of(GeoServiceImpl.MOSCOW_IP,
                        Country.RUSSIA, "Moscow", "Lenina", 15),
                Arguments.of(GeoServiceImpl.NEW_YORK_IP,
                        Country.USA, "New York", "10th Avenue", 32));
    }

    @DisplayName("Test byIp() Start IP")
    @ParameterizedTest
    @MethodSource
    void byIpTestParamsStartIP(String ip, String startIP) {
        System.out.println("Parameterized Test byIp() по началу IP.");

        final Location result = geoService.byIp(ip);

        MatcherAssert.assertThat(ip, startsWith(startIP));
        System.out.println(ip + " -> " + result.getCity());
    }
    public static Stream<Arguments> byIpTestParamsStartIP() {
        return Stream.of(Arguments.of(GeoServiceImpl.MOSCOW_IP, "172."),
                Arguments.of(GeoServiceImpl.NEW_YORK_IP, "96."));
    }

    @DisplayName("Тест byIp() Unknown IP")
    @Test
    void byIpTestNull() {
        System.out.println("Test byIp() на неизвестный IP.");
        final String ip = "192.168.1.1";
        Location expected = null;

        final Location result = geoService.byIp(ip);

        Assertions.assertEquals(expected, result);
        System.out.println("Неизвестный IP: " + ip);
    }

    @DisplayName("Тест на Exception")
    @Test
    void byCoordinatesException() {
        System.out.println("Test byCoordinates() на исключение.");
        final double latitude = 0;
        final double longitude = 0;

        assertThrows(RuntimeException.class, () -> geoService.byCoordinates(latitude, longitude));
    }
}
