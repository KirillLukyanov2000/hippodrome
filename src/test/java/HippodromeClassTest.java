import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class HippodromeClassTest {

    @Test
    void hippodromeConstructorParamIsNullTest() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(null)
        );
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    void hippodromeConstructorParamIsEmptyTest() {
        List<Horse> horses = new ArrayList<>();
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(horses)
        );
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void hippodromeGetHorsesTest() {
        List<Horse> horses = new ArrayList<>();
        String name = "horse";
        double speed = 1.0;
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse(name, speed));
            name += String.valueOf(i);
            speed += i;
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
        for (int i = 0; i < horses.size(); i++) {
            assertEquals(horses.get(i), hippodrome.getHorses().get(i));
        }
    }

    @Test
    void hippodromeMoveTest() {
        Horse mockHorse = Mockito.mock(Horse.class);
        List<Horse> horseList = Mockito.spy(ArrayList.class);
        for (int i = 0; i < 50; i++) {
            horseList.add(mockHorse);
        }
        Hippodrome hippodrome = Mockito.spy(new Hippodrome(horseList));
        hippodrome.move();
        for (Horse horse : horseList
        ) {
            Mockito.verify(horse, Mockito.times(50)).move();
        }
    }

    @Test
    void hippodromeGetWinnerTest() {
        List<Horse> horses = new ArrayList<>();
        String name = "horse";
        double speed = 1.0;
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse(name, speed));
            name += String.valueOf(i);
            speed += i;
        }
        for (Horse horse : horses) {
            horse.move();
        }
        horses.sort(Comparator.comparingDouble(Horse::getDistance));
        Hippodrome hippodrome = new Hippodrome(horses);
        Horse winner = hippodrome.getWinner();
        assertEquals(horses.get(horses.size() - 1), winner);
    }
}
