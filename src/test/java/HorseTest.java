

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class HorseTest {
    @Test
    void horseConstructorNameParamIsNullTest() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(null, 10, 15)
        );
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "\r"})
    void horseConstructorNameParamIsBlankTest(String str) {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(str, 10, 15)
        );
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void horseConstructorSpeedParamIsNegativeTest() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Any", -1, 15)
        );
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void horseConstructorDistanceParamIsNegativeTest() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Any", 10, -1)
        );
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void horseGetNameTest() {
        Horse horse = new Horse("Burka", 15, 15);
        Field name;
        try {
            name = Horse.class.getDeclaredField("name");
            name.setAccessible(true);
            String horseName = (String) name.get(horse);
            assertEquals("Burka", horseName);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        assertEquals("Burka", horse.getName());
    }

    @Test
    void horseGetSpeedTest() {
        Horse horse = new Horse("Burka", 15, 20);
        Field speed;
        try {
            speed = Horse.class.getDeclaredField("speed");
            speed.setAccessible(true);
            double horseSpeed = (double) speed.get(horse);
            assertEquals(15, horseSpeed);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException();
        }
        assertEquals(15, horse.getSpeed());
    }

    @Test
    void horseGetDistanceTest() {
        Horse horse = new Horse("Burka", 15, 20);
        Field distance;
        try {
            distance = Horse.class.getDeclaredField("distance");
            distance.setAccessible(true);
            double horseDistance = (double) distance.get(horse);
            assertEquals(20, horseDistance);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException();
        }
        assertEquals(20, horse.getDistance());
        Horse horse2 = new Horse("Burka2", 15);
        assertEquals(0, horse2.getDistance());
    }

    @Test
    void horseMoveMethodUsesGetRandomTest() {
        try (MockedStatic<Horse> mockedStaticHorse = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("Burka", 10);
            horse.move();
            mockedStaticHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.3, 0.8})
    void horseMoveTest2(double random) {
        try (MockedStatic<Horse> mockedStaticHorse = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("aa", 10, 0);
            mockedStaticHorse.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);
            horse.move();
            double distance2 = 0;
            distance2 += horse.getSpeed() * Horse.getRandomDouble(0.2, 0.9);
            assertEquals(distance2, horse.getDistance());
        }
    }
}
