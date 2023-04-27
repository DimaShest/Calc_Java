import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

public class Main {
    static Map<String, Integer> _states;
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println(calc(scanner.nextLine()));
    }
    public static String calc(String input) {
        char[] arrayLineExpression = input.toCharArray();
        int index = 0;
        int numberOne;
        int numberTwo;
        int[] arrayReturnedNumberOne = GetValueNumber(arrayLineExpression, index);
        index = arrayReturnedNumberOne[1];
        numberOne = arrayReturnedNumberOne[0];

        String[] arrayReturnedSign = GetExpressionSign(arrayLineExpression, index);
        index = Integer.valueOf(arrayReturnedSign[1]);


        int[] arrayReturnedNumberTwo = GetValueNumber(arrayLineExpression, index);
        index = arrayReturnedNumberTwo[1] + 1;
        numberTwo = arrayReturnedNumberTwo[0];

        if(CheckEndExpression(arrayLineExpression, index) == false){
            CloseApp("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        if(arrayReturnedNumberOne[2] != arrayReturnedNumberTwo[2]){
            CloseApp("Используются одновременно разные системы счисления");
        }
        if(arrayReturnedSign[0] == null){
            CloseApp("Cтрока не является математической операцией");
        }
        String signOperation = arrayReturnedSign[0];

        if(arrayReturnedNumberOne[2] == 0 && signOperation.equals("-") &&
                numberOne - numberTwo <= 0){
            CloseApp("В римской системе нет отрицательных чисел");
        }
        int resultNumber = ExecuteExpression(numberOne, numberTwo, arrayReturnedSign[0]);
        String strResultNumber = "";
        if(arrayReturnedNumberOne[2] == 0){
            strResultNumber = ConvertToRimNumber(resultNumber, "");
        }
        else strResultNumber = String.valueOf(resultNumber);
        return strResultNumber;
    }

    static String ConvertToRimNumber(int resultNumber, String strResultNumber){
        while(resultNumber >= 100) {
            resultNumber-=100;
            strResultNumber+="C";
        }
        if(resultNumber >= 90) {
            resultNumber-=90;
            strResultNumber+="XC";
        }
        if(resultNumber >= 50) {
            resultNumber-=50;
            strResultNumber+="L";
        }
        if(resultNumber >= 40) {
            resultNumber-=40;
            strResultNumber+="XL";
        }
        while(resultNumber >= 10) {
            resultNumber-=10;
            strResultNumber+="X";
        }
        if(resultNumber >= 9) {
            resultNumber-=9;
            strResultNumber+="IX";
        }
        if(resultNumber >= 5) {
            resultNumber-=5;
            strResultNumber+="V";
        }
        if(resultNumber >= 4) {
            resultNumber-=4;
            strResultNumber+="IV";
        }
        while(resultNumber >= 1) {
            resultNumber-=1;
            strResultNumber+="I";
        }
        return strResultNumber;
    }
    static int[] GetValueNumber(char[] arrayLineExpression, int index){
            String strNumber = "";
            for(int i = index; i < arrayLineExpression.length; i++){
                index = i;
               if((arrayLineExpression[i] >= '0' && arrayLineExpression[i] <= '9') ||
                arrayLineExpression[i] == 'I' || arrayLineExpression[i] == 'V' || arrayLineExpression[i] == 'X'){
                   strNumber += arrayLineExpression[i];
               }
               else {
                   break;
               }
            }
        int number = -1;
        int isArabicNumber = 0;
        try{
            number = Integer.parseInt(strNumber);
            if(!(number >= 1 && number <= 10)){
                CloseApp("Арабские числа для ввода могут быть только в диапазоне от 1 до 10 включительно");
            }
            isArabicNumber = 1;
        }
        catch (Exception e) {
            try{
                CreateStates();
                number = _states.get(strNumber);
                isArabicNumber = 0;
            }
            catch (Exception e1){
                CloseApp("Римские числа для ввода могут быть только в диапазоне от I до X включительно");
            }
        }
         int[] result = {number,index, isArabicNumber};
         return result;
    }
    static String[] GetExpressionSign(char[] arrayLineExpression, int index){
        String sign = null;
        for(int i = index; i < arrayLineExpression.length; i++){
            if(arrayLineExpression[i] == '+' || arrayLineExpression[i] == '-' ||
                    arrayLineExpression[i] == '*' || arrayLineExpression[i] == '/'){
                sign = String.valueOf(arrayLineExpression[i]);
            }
            else if(sign != null){
                index = i;
                break;
            }
        }
        String[] result = {sign, String.valueOf(index)};
        return result;
    }
    static boolean CheckEndExpression(char[] arrayLineExpression, int index){
        boolean isEndExpression = true;
        for(int i = index; i < arrayLineExpression.length; i++){
            if(arrayLineExpression[i] != ' '){
                isEndExpression = false;
            }
        }
        return isEndExpression;
    }
    static int ExecuteExpression(int number1, int number2, String sign){
        int resultNumber;
        switch (sign){
            case "+":
                resultNumber = number1 + number2;
                break;
            case "-":
                resultNumber = number1 - number2;
                break;
            case "*":
                resultNumber = number1 * number2;
                break;
            case "/":
                resultNumber = number1 / number2;
                break;
            default: resultNumber = -1;
        }
        return resultNumber;
    }
    static void CreateStates(){
        _states = new HashMap<>();
        _states.put("I", 1);
        _states.put("II", 2);
        _states.put("III", 3);
        _states.put("IV", 4);
        _states.put("V", 5);
        _states.put("VI", 6);
        _states.put("VII", 7);
        _states.put("VIII", 8);
        _states.put("IX", 9);
        _states.put("X", 10);
    }
    static void CloseApp(String message){
        System.out.println(message);
        System.exit(0);
    }
}