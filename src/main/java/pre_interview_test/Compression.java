package pre_interview_test;

import java.util.*;

public class Compression {

    private int timesCalledToGetCompressionRatio = 0;
    private double sumOfCompressionRatio = 0;

    private final Map<Integer, String> mapTripleDigitInt2Letter = new HashMap<>();
    private final Map<String, Integer> mapLetter2TripleDigitInt = new HashMap<>();
    private final ArrayList<String> arrayNumbers = new ArrayList<>();
    private final Map<Integer, String> mapScore2Letter = new HashMap<>();
    private final Map<String, Integer> mapLetter2Score = new HashMap<>();
    private final Map<Integer, String> mapDoubleDigitInt2Char = new HashMap<>();
    private final Map<String, Integer> mapChar2DoubleDigitInt = new HashMap<>();


    /**
     * Init compression object - filled all maps and arrays that will be instruction for serialize/deserialize
     */
    public Compression() {
        ArrayList<String> usedChars = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            arrayNumbers.add(String.valueOf(i));
            usedChars.add(String.valueOf(i));
        }

        for (int i = 10; i <= 30; i++) {
            String letter = String.valueOf((char) ('A' + (i - 10)));
            mapTripleDigitInt2Letter.put(i, letter);
            mapLetter2TripleDigitInt.put(letter, i);
            usedChars.add(letter);
        }

        for (int i = 2; i <= 6; i++) {
            String letter = String.valueOf((char) ('V' + (i - 2)));
            if (i == 6) {
                mapScore2Letter.put(-1, letter);
                mapLetter2Score.put(letter, -1);
            } else {
                mapScore2Letter.put(i, letter);
                mapLetter2Score.put(letter, i);
            }
            usedChars.add(letter);
        }

        int tmpDoubleDigitNum = 10;
        for (int i = 33; i < 127; i++) {
            String c = String.valueOf((char) i);
            if (!usedChars.contains(c) && !c.equals("-")) {
                mapDoubleDigitInt2Char.put(tmpDoubleDigitNum, c);
                mapChar2DoubleDigitInt.put(c, tmpDoubleDigitNum);
                if (tmpDoubleDigitNum == 58) {
                    tmpDoubleDigitNum = 90;
                }
                tmpDoubleDigitNum++;
            }
        }
    }


    /**
     * Function for serializing with 'compression' an array of numbers from 1 to 300 to string (StringBuilder).
     * SingleDigitNumbers won't be converted ('compressed'), just append to StringBuilder.
     * DoubleDigitNumbers will be converted ('compressed') by exporting them to char using 'mapDoubleDigitInt2Char'
     * (if map doesn't contain num -> it will use '100 - num' with sign '-') and append to StringBuilder.
     * TripleDigitNumbers will be converted ('compressed') by exporting first 2 signs using 'mapTripleDigitInt2Letter'
     * to letter (upper) + adding lust sign to this letter, result will be appended to StringBuilder.
     * If number faced in array more, than one time, we will add letter (from V to Z) before number.
     * <br> Example: int[] arr = [3, 76, 112] -> StringBuilder result = 'B23-0' <br/>
     * Explain example:
     * <br> We get numbers to 'compress' from hashed map 'numbersAndScore', so they aren't in right index. <br/>
     * First is '112', we export '11' (first 2 signs) to 'B' and plus '2' -> 'B2'.
     * <br> Second will be number '3', this is SingleDigit, so we don't do anything -> '3'. <br/>
     * Lust one is '76', we checked and see that map for export hasn't this key, lets try 100-76='24', we get char '0',
     * add '-' (because it is irregular) -> '-0'.
     *
     * @param arr your int array of numbers [1-300] that will be serialized
     * @return serialized string (StringBuilder)
     */
    public StringBuilder serialize(int[] arr) {
        Arrays.sort(arr);
        Map<Integer, Integer> numbersAndScore = getNumbersAndScoreOfIntArray(arr);
        StringBuilder serialized = new StringBuilder();

        for (Map.Entry<Integer, Integer> entry : numbersAndScore.entrySet()) {
            serialized.append(
                    getCompressedNumber(entry.getKey(), entry.getValue())
            );
        }

        return serialized;
    }

    /**
     * Function for deserializing string (StringBuilder) with 'compression' to array of numbers from 1 to 300.
     * In StringBuilder will be looked every char and by if-else statement
     * they will be glue in 'pairs' that means SomeDigitNumbers.
     * After, this 'pairs' converting to integer (using reversed 'serialize' algorithm) and append to array.
     * <br><b>Note: returned array contain all numbers as in source, but theirs indexes isn't equal<b/><br/>
     *
     * @param serialized your serialized string (StringBuilder) of 'compressed' numbers
     * @return int array of numbers [1-300] that was deserialized
     */
    public int[] deserialize(StringBuilder serialized) {
        ArrayList<Integer> tmpResultArray = new ArrayList<>();
        int flag2addScore = 1;
        int number = -1;

        for (int i = 0; i < serialized.length(); i++) {
            String actualChar = String.valueOf(serialized.charAt(i));

            if (mapLetter2Score.containsKey(actualChar)) {
                if (actualChar.equals(mapScore2Letter.get(-1))) {
                    i++;
                    flag2addScore = Integer.parseInt(String.valueOf(serialized.charAt(i)));
                    continue;
                }
                flag2addScore = mapLetter2Score.get(actualChar);
                continue;
            } else if (arrayNumbers.contains(actualChar)) {
                number = Integer.parseInt(actualChar);
            } else if (actualChar.equals("-")) {
                i++;
                number = 100 - mapChar2DoubleDigitInt.get(String.valueOf(serialized.charAt(i)));
            } else if (mapChar2DoubleDigitInt.containsKey(actualChar)) {
                number = mapChar2DoubleDigitInt.get(actualChar);
            } else if (mapLetter2TripleDigitInt.containsKey(actualChar)) {
                StringBuilder tmpTripleDigitNum = new StringBuilder(actualChar);
                i++;
                tmpTripleDigitNum.append(serialized.charAt(i));
                number = getTripleDigitIntFromCompressedSymbol(tmpTripleDigitNum);
            }

            for (int j = 0; j < flag2addScore; j++) {
                tmpResultArray.add(number);
            }
            flag2addScore = 1;
        }

        return exportArrayList2IntArr(tmpResultArray);
    }

    /**
     * Calculate compression ratio by dividing source string to compressed.
     *
     * @param src source string (array numbers sipped ' ')
     * @param compressed compressed string (that you get from 'serialize()')
     * @return coefficient of compression
     */
    public double getCompressionRatio(StringBuilder src, StringBuilder compressed) {
        double ratio = (double) src.length() / (double) compressed.length();
        sumOfCompressionRatio += ratio;
        timesCalledToGetCompressionRatio++;
        return ratio;
    }

    /**
     * Calculate average mean of compression ratio by dividing sum of all compression ratio to
     * times, that compression ratio was calculated.
     *
     * @return average mean of all compression ratios
     */
    public double getAverageMeanOfCompressionRatio() {
        return sumOfCompressionRatio / timesCalledToGetCompressionRatio;
    }

    private StringBuilder getCompressedSymbolFromTripleDigitInt(int num) {
        StringBuilder result = new StringBuilder();
        result.append(mapTripleDigitInt2Letter.get(num / 10)).append(num % 10);
        return result;
    }

    private StringBuilder getCompressedSymbolFromDoubleDigitInt(int num) {
        StringBuilder result = new StringBuilder();
        if (mapDoubleDigitInt2Char.containsKey(num)) {
            result.append(mapDoubleDigitInt2Char.get(num));
        } else {
            result.append("-").append(mapDoubleDigitInt2Char.get(100 - num));
        }
        return result;
    }

    private int getTripleDigitIntFromCompressedSymbol(StringBuilder str) {
        int result = mapLetter2TripleDigitInt.get(String.valueOf(str.charAt(0))) * 10;
        result += Integer.parseInt(String.valueOf(str.charAt(1)));
        return result;
    }

    private int[] exportArrayList2IntArr(ArrayList<Integer> src) {
        int arrLen = src.size();
        int[] result = new int[arrLen];
        for (int i = 0; i < arrLen; i++) {
            result[i] = src.get(i);
        }
        return result;
    }

    private Map<Integer, Integer> getNumbersAndScoreOfIntArray(int[] arr) {
        Map<Integer, Integer> result = new HashMap<>();
        for (int num : arr) {
            if (result.containsKey(num)) {
                result.put(num, result.get(num) + 1);
            } else {
                result.put(num, 1);
            }
        }
        return result;
    }

    private StringBuilder getCompressedNumber(int number, int count) {
        StringBuilder compressed = new StringBuilder();

        if (count != 1) {
            while (count > 9) {
                compressed.append(getCompressedNumber(number, 9));
                count -= 9;
            }
            if (mapScore2Letter.containsKey(count)) {
                compressed.append(mapScore2Letter.get(count));
            } else {
                compressed.append(mapScore2Letter.get(-1)).append(count);
            }
        }

        if (1 <= number && number <= 9) {
            compressed.append(number);
        } else if (10 <= number && number <= 99) {
            compressed.append(getCompressedSymbolFromDoubleDigitInt(number));
        } else if (100 <= number && number <= 300) {
            compressed.append(getCompressedSymbolFromTripleDigitInt(number));
        }

        return compressed;
    }

}
