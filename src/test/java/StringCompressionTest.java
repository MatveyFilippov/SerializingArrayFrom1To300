import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pre_interview_test.ArrayFilling;
import pre_interview_test.Compression;
import java.util.Arrays;


public class StringCompressionTest {
    private static Compression testCompression;

    @BeforeAll
    static void initCompression() {
        testCompression = new Compression();
    }

    @Test
    void simpleArraysTest() {
        printNewCategory("Простейшие короткие");
        for (int i = 0; i < 10; i++) {
            doTest(ArrayFilling.getRandomNumbers((int) (Math.random() * 10 + 5)));
        }
    }

    @ParameterizedTest
    @CsvSource({"50", "100", "500", "1000"})
    void randomArraysTest(String numOfNumbersInArray) {
        printNewCategory("Случайные - " + numOfNumbersInArray + " чисел");
        doTest(ArrayFilling.getRandomNumbers(Integer.parseInt(numOfNumbersInArray)));
    }

    @Test
    void singleDigitArrayTest() {
        printNewCategory("Все числа 1 знака");
        doTest(ArrayFilling.getSingleDigitNumbers());
    }

    @Test
    void doubleDigitArrayTest() {
        printNewCategory("Все числа 2х знаков");
        doTest(ArrayFilling.getDoubleDigitNumbers());
    }

    @Test
    void tripleDigitArrayTest() {
        printNewCategory("Все числа 3х знаков");
        doTest(ArrayFilling.getTripleDigitNumbers());
    }

    @Test
    void threeOfEachNumberArrayTest() {
        printNewCategory("Каждого числа по 3 (всего чисел 900)");
        doTest(ArrayFilling.getThreeOfEachNumber());
    }

    @AfterAll
    static void printAverageMeanOfCompressionRatio() {
        printNewCategory("Итого");
        double average = testCompression.getAverageMeanOfCompressionRatio();
        System.out.println("\n   Average mean of compression ratio: " + average);
        Assertions.assertTrue(average >= 2);
    }


    private void doTest(int[] array) {
        // get serialized string
        StringBuilder serializedString = testCompression.serialize(array);

        // look that line contain only ASCII chars
        Assertions.assertTrue(checkForNonAscii(serializedString.toString()));

        // print data that was asked in docx
        printRequestedData(array, serializedString);

        // get deserialized array
        int[] deserializedArray = testCompression.deserialize(serializedString);

        // look that source and final arrays is equal (without looking to indexes)
        Arrays.sort(array);
        Arrays.sort(deserializedArray);
        Assertions.assertArrayEquals(array, deserializedArray);
    }

    private void printRequestedData(int[] srcArr, StringBuilder result) {
        StringBuilder srcString = new StringBuilder();
        System.out.print("\n   Original string: ");
        for (int num : srcArr) {
            System.out.print(num + " ");
            srcString.append(num).append(" ");
        }
        srcString.deleteCharAt(srcString.length() - 1);

        System.out.println("\n   Serialized string: " + result);

        double compressionRatio = testCompression.getCompressionRatio(srcString, result);
        System.out.println("   Compression ratio: " + compressionRatio);
    }

    private static void printNewCategory(String name) {
        System.out.println();
        for (int i = 0; i < 30; i++) {
            System.out.print("-----");
        }
        System.out.println("\n\n" + name + ":");
    }

    private boolean checkForNonAscii(String value) {
        return value.matches("\\A\\p{ASCII}*\\z");
    }

}