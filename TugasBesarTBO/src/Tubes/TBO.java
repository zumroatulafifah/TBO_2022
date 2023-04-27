package Tubes;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TBO {
    private static enum Token {
        KONTINU("-?[0-9]+\\.[0-9]+"),
        NUMBER("-?[0-9]+"), BOOLEAN("(TRUE|FALSE|true|false)"),
        VARIABEL("[a-z|A-Z|0-9]+"), OPERATOR("[*|/|+|-|%|^|(|)|[|]|{|}|,|]"), SKIP("[ \t\f\r\n]+"),
        STRING("\"[a-zA-Z0-9]*\""), RELASI("!=|==|>|<|>=|<="), SELECTION("if|else(\\s+if)?|switch"),
        OPERASI("^|&|~|>>");

        private final String pattern;

        private Token(String pattern) {
            this.pattern = pattern;
        }
    }

    private static class Word {
        private Token token;
        private String lexeme;

        private Word(Token token, String lexeme) {
            this.token = token;
            this.lexeme = lexeme;
        }

        @Override
        public String toString() {
            return String.format("%-10s => [%s]", token.name(), lexeme);
        }
    }

    private static ArrayList<Word> lex(String input) {
        // The tokens to return
        ArrayList<Word> words = new ArrayList<Word>();

        // Lexer logic begins here
        StringBuffer tokenPatternsBuffer = new StringBuffer();
        for (Token token : Token.values())
            tokenPatternsBuffer.append(String.format("|(?<%s>%s)", token.name(), token.pattern));
        Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)));

        // Begin matching tokens
        Matcher matcher = tokenPatterns.matcher(input);
        while (matcher.find()) {
            for (Token token : Token.values())
                if (matcher.group(token.name()) != null) {
                    words.add(new Word(token, matcher.group(token.name())));
                    continue;
                }
        }
        return words;
    }


    public static void main(String[] args) {
        String input = "string_Sujimmy = != 11 + 22 - 33 % 5+5.757657.65765 " + "\"AU\"" +  "2 + 2 = 4" + "true"
                + "4 * 2 = 8" + "false" + " 11 % 3 = 2" + "\t" + "a != b == c > d < e " + "\t" + "a >= b <= c"
                + "\t" + "if (a == b) { c = d; } else if (e == f) { g = h; } else { i = j; }" + "\t"
                + "switch (a) { case 1: b = c; break; case 2: d = e; break; default: f = g; }"
                + "a = b & c | d ^ e ~ f";

        ArrayList<Word> words = lex(input);
        for (Word word : words)
            System.out.println(word);
    }
}
