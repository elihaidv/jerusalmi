package com.vanunu.elihai.jerusalmi;


public class Gematria {
    private static  String defaultMessage = "Unknown character in UTF-16 encoding";
    public Gematria() {
    }

    public int getWordValue(String word) throws LetterNotFoundException {
        int letterValue;
        int totalWordValue = 0;
        for (char letter : word.toCharArray()) {
            letterValue = getLetterValue(letter);
            totalWordValue += letterValue;
        }
        return totalWordValue;
    }
    public int getLetterValue(char letter) throws LetterNotFoundException {
        int letterValue = letter;
        // System.out.print(letter +": " + letterValue + System.lineSeparator());
        if (letterValue == -1) {
            throw new LetterNotFoundException();
        }
        switch(letter) {
            case 'א': return 1;
            case 'ב': return 2;
            case 'ג': return 3;
            case 'ד': return 4;
            case 'ה': return 5;
            case 'ו': return 6;
            case 'ז': return 7;
            case 'ח': return 8;
            case 'ט': return 9;
            case 'י': return 10;
            case 'כ':
            case 'ך': return 20;
            case 'ל': return 30;
            case 'מ':
            case 'ם': return 40;
            case 'נ':
            case 'ן': return 50;
            case 'ס': return 60;
            case 'ע': return 70;
            case 'פ':
            case 'ף': return 80;
            case 'צ':
            case 'ץ': return 90;
            case 'ק': return 100;
            case 'ר': return 200;
            case 'ש': return 300;
            case 'ת': return 400;
            default:  return 0;
        }
    }
    public static String getLetters(int i, boolean apos) {
        boolean thousands = false;


        if(i > 9999 || i < 1)
            return "";

        StringBuffer sb = new StringBuffer();
        if(thousands == false)
            i %= 1000;

        if(i >= 9000) { sb.append("\u05D8\u05F3"); i -= 9000; }
        if(i >= 8000) { sb.append("\u05D7\u05F3"); i -= 8000; }
        if(i >= 7000) { sb.append("\u05D6\u05F3"); i -= 7000; }
        if(i >= 6000) { sb.append("\u05D5\u05F3"); i -= 6000; }
        if(i >= 5000) { sb.append("\u05D4\u05F3"); i -= 5000; }
        if(i >= 4000) { sb.append("\u05D3\u05F3"); i -= 4000; }
        if(i >= 3000) { sb.append("\u05D2\u05F3"); i -= 3000; }
        if(i >= 2000) { sb.append("\u05D1\u05F3"); i -= 2000; }
        if(i >= 1000) { sb.append("\u05D0\u05F3"); i -= 1000; }
        if(i >= 900) { sb.append("\u05EA\u05EA\u05E7"); i -= 900; }
        if(i >= 800) { sb.append("\u05EA\u05EA"); i -= 800; }
        if(i >= 700) { sb.append("\u05EA\u05E9"); i -= 700; }
        if(i >= 600) { sb.append("\u05EA\u05E8"); i -= 600; }
        if(i >= 500) { sb.append("\u05EA\u05E7"); i -= 500; }
        if(i >= 400) { sb.append("\u05EA"); i -= 400; }
        if(i >= 300) { sb.append("\u05E9"); i -= 300; }
        if(i >= 200) { sb.append("\u05E8"); i -= 200; }
        if(i >= 100) { sb.append("\u05E7"); i -= 100; }
        if(i >= 90) { sb.append("\u05E6"); i -= 90; }
        if(i >= 80) { sb.append("\u05E4"); i -= 80; }
        if(i >= 70) { sb.append("\u05E2"); i -= 70; }
        if(i >= 60) { sb.append("\u05E1"); i -= 60; }
        if(i >= 50) { sb.append("\u05E0"); i -= 50; }
        if(i >= 40) { sb.append("\u05DE"); i -= 40; }
        if(i >= 30) { sb.append("\u05DC"); i -= 30; }
        if(i >= 20) { sb.append("\u05DB"); i -= 20; }
        if(i == 16) { sb.append("\u05D8\u05D6"); i -= 16; }
        if(i == 15) { sb.append("\u05D8\u05D5"); i -= 15; }
        if(i >= 10) { sb.append("\u05D9"); i -= 10; }
        if(i >= 9) { sb.append("\u05D8"); i -= 9; }
        if(i >= 8) { sb.append("\u05D7"); i -= 8; }
        if(i >= 7) { sb.append("\u05D6"); i -= 7; }
        if(i >= 6) { sb.append("\u05D5"); i -= 6; }
        if(i >= 5) { sb.append("\u05D4"); i -= 5; }
        if(i >= 4) { sb.append("\u05D3"); i -= 4; }
        if(i >= 3) { sb.append("\u05D2"); i -= 3; }
        if(i >= 2) { sb.append("\u05D1"); i -= 2; }
        if(i >= 1) { sb.append("\u05D0"); i -= 1; }

        String s = sb.toString();
        if(apos == true) {
            if(s.length() == 1) {
                s = s + "\u05F3";
            }
            else if(s.length() > 1)  {
                // s = s.substring(0, s.length() - 1) + "\u05F4" + s.substring(s.length() - 1);
                s = s.substring(0, s.length() - 1) + "\"" + s.substring(s.length() - 1);
            }
        }

        return s;
    }
    private class LetterNotFoundException extends Exception {

        private final long serialVersionUID = -1679049940938078260L;


        public LetterNotFoundException() {
            super(defaultMessage);
        }

        public LetterNotFoundException(String message) {
            super(message);
        }

        public String getDefaultMessage() {
            return defaultMessage;
        }

    }
}