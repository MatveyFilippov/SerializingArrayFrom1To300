package pre_interview_test;

/**
 * Class of methods, that help to fill arrays
 */
public class ArrayFilling {

    public static int[] getRandomNumbers(int len) {
        int[] numbers = new int[len];
        for (int i = 0; i < len; i++) {
            numbers[i] = (int) (Math.random() * 300 + 1);
        }
        return numbers;
    }

    private static int[] getFilledArrayBySomeDigitNumbers(int len) {
        int[] numbers = new int[len];
        int num2add = (int) Math.pow(10, (String.valueOf(len).length() - 1));
        for (int i = 0; i < len; i++) {
            numbers[i] = i + num2add;
        }
        return numbers;
    }

    public static int[] getSingleDigitNumbers() {
        return getFilledArrayBySomeDigitNumbers(9);
    }

    public static int[] getDoubleDigitNumbers() {
        return getFilledArrayBySomeDigitNumbers(90);
    }

    public static int[] getTripleDigitNumbers() {
        return getFilledArrayBySomeDigitNumbers(201);
    }

    public static int[] getThreeOfEachNumber() {
        int[] numbers = new int[900];
        int num = 1;

        for (int i = 0; i < 900; i += 3) {
            numbers[i] = num;
            numbers[i+1] = num;
            numbers[i+2] = num;
            num++;
        }

        return numbers;
    }

}
