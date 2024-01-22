# Serializing array with integer from 1 to 300 with my algorithm of compression numbers using only ASCII symbols
It was created for passing pre interview test from LaboratoryOfSystemTechnologies

## Where I can look how it works?
#### Run [src/test/java/StringCompressionTest.java](src/test/java/StringCompressionTest.java)
It contains a set of tests with output `source string`, `compressed string`, `compression ratio`
and checking that `average compression ratio is greater than two`,
`array passed through the algorithm is equivalent to the original`, and `compressed string contains only characters`

## How serializing with 'compression' works?
#### serialize(int[] arr);
> SingleDigitNumbers won't be converted ('compressed'), just append to StringBuilder

> DoubleDigitNumbers will be converted ('compressed') by exporting them to char using 'mapDoubleDigitInt2Char'
> (if map doesn't contain num -> it will use '100 - num' with sign '-') and append to StringBuilder

> TripleDigitNumbers will be converted ('compressed') by exporting first 2 signs using 'mapTripleDigitInt2Letter' 
> to letter (upper) + adding lust sign to this letter, result will be appended to StringBuilder

> If number faced in array more, than one time, we will add letter (from V to Z) before number

#### Example: `int[] arr = [3, 76, 112] -> StringBuilder result = 'B23-0'`
We get numbers to 'compress' from hashed map 'numbersAndScore', so they are not in right index

First is `112`, we export `11` (first 2 signs) to `B` and plus `2` -> `B2`

Second will be number `3`, this is SingleDigit, so we don't do anything -> `3`

Lust one is `76`, we checked and see that map for export hasn't this key, lets try 100-76=`24`, we get char `0`,
add `-` (because it is irregular) -> `-0`

## What also contain this rep?
> [ArrayFilling.java](src/main/java/pre_interview_test/ArrayFilling.java)
> --- Class that helps to fill arrays (that is required in tests)
>> `getRandomNumbers(int count)` - return array with random numbers (size = inputted 'len')
>>
>> `getSingleDigitNumbers()` - return array with numbers from 1 to 9 (size = 9)
>>
>> `getDoubleDigitNumbers()` - return array with numbers from 10 to 99 (size = 90)
>>
>> `getTripleDigitNumbers()` - return array with numbers from 100 to 300 (size = 201)
>>
>> `getThreeOfEachNumber()` - return array with numbers from 1 to 300 using them 3 times (size = 900)

> [Compression.java](src/main/java/pre_interview_test/Compression.java)
> --- Class that helps to work with compression
>> `deserialize(StringBuilder serialized)` - return array with numbers that was compressed to 'serialized'
>>
>> `getCompressionRatio(StringBuilder src, StringBuilder compressed)` - return coefficient of ratio
>> calculated by divide source to compressed
>>
>> `getAverageMeanOfCompressionRatio()` - average mean of all compression ratios,
>> that was called by 'getCompressionRatio'

## Created by MatveyFilippov:
TG - [@xxxmat1](https://t.me/xxxmat1)

Email - mdfilippov_2@edu.hse.ru

***

###### PreInterviewTestLaboratoryOfSystemTechnologies -v1.0