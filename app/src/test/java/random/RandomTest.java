package random;

import org.junit.Test;

import java.util.Random;

public class RandomTest {

    @Test
    public void main() {
        Random random1 = new Random(10);
        Random random2 = new Random(10);
        for (int i = 0; i < 10; i++) {
            int j = random1.nextInt(10);
            int k = random2.nextInt(10);
            System.out.println("j " + j + " k " + k);

        }
    }
}
