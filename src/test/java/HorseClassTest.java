

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class HorseClassTest {
    @Test
    public void horseConstructorFirstParamIsNullTest() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(null, 10, 15)
        );
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "\r"})
    public void horseConstructorFirstParamIsBlankTest(String str) {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(str, 10, 15)
        );
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    public void horseConstructorSecondParamIsNegativeTest() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Any", -1, 15)
        );
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    public void horseConstructorThirdParamIsNegativeTest() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Any", 10, -1)
        );
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    public void horseGetNameTest() {
        Horse horse = new Horse("Burka", 15, 15);
        assertEquals("Burka", horse.getName());
    }

    @Test
    public void horseGetSpeedTest() {
        Horse horse = new Horse("Burka", 15, 20);
        assertEquals(15, horse.getSpeed());
    }

    @Test
    public void horseGetDistanceTest() {
        Horse horse = new Horse("Burka", 15, 20);
        assertEquals(20, horse.getDistance());
    }

    @Test
    public void horseMoveTest1() {
        try (MockedStatic<Horse> mockedStaticHorse = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("aa", 10);
            horse.move();
            mockedStaticHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @CsvSource({"0.2, 0.9"})
    public void horseMoveTest2(double min, double max) {
        try (MockedStatic<Horse> mockedStaticHorse = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("aa", 10, 0);
            mockedStaticHorse.when(() -> Horse.getRandomDouble(min, max)).thenReturn(8.0);
            horse.move();
            double distance2 = 0;
            distance2 += horse.getSpeed() * Horse.getRandomDouble(min, max);
            assertEquals(horse.getDistance(), distance2);
        }
    }
}
