import java.util.Arrays;

public class Solution {

    public static String replaceSpace(String s) {
        //return s.replaceAll(" ", "%20");
        char[] chars = s.toCharArray();
        int oldLength = chars.length;
        if (oldLength == 0) return s;
        int blankNums = 0;
        for (int i = 0; i < oldLength; i++) {
            if (chars[i] == ' ') {
                ++blankNums;
            }
        }
        int newLenght = blankNums * 2 + oldLength;
        if (newLenght == oldLength || newLenght == 0) {
            return s;
        }
        int i = oldLength - 1;
        int j = newLenght - 1;
        char[] newChar = new char[newLenght];
        while (i >= 0 ) {
            if (chars[i] == ' ') {
                newChar[j--] = '0';
                newChar[j--] = '2';
                newChar[j--] = '%';
            } else {
                newChar[j--] = chars[i];

            }
            i--;
        }
        return new String(newChar);
    }

    public static void main(String[] args) {
        String s = replaceSpace("We are happy");
        System.out.println(s);

    }

}
