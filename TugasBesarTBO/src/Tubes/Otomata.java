package Tubes;

import jdk.nashorn.internal.ir.Assignment;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Otomata {
    private static enum Token {
        NOTEQUALTO("[!][=]"),
        OR("\\|"),
        ARRAYSIZE("[a-zA-z]+\\[[0-9]+\\]"),
        ARRAYSIZEVAR("[a-zA-Z]+\\[[a-zA-Z]+\\]"),
        ARRAYMULTI("[a-zA-Z]+\\[[\\[\\]]+\\]"),
        ARRAY("\\[\\]+[a-zA-Z]*"),
        KONTINYU("-?[0-9]+\\.[0-9]+"),
        NUMBER("-?[0-9]+"),
        OPERATOR("[|/|+|%|\\-|]"),
        SKIP("[\t\f\r\n]+"),
        STRING ("\"[a-zA-Z0-9]*\""),
        VARIABLE ("[a-z]"),   // VARIABLE("[a-z|a-z0-9|a-z[0-9]+\\.[0-9]+]+"),
        TIPEDATA("INT|STRING|DOUBLE|CHAR"),
        LITERALBOOLEAN("TRUE|FALSE"),
        NOT("!") ,
        AND("&"),
        DISKRIT("[+0-9]"),
        SHIFTRIGHT("[>][>]"),
        SHIFTLEFT("[<][<]"),
        GREATHERTHANOREQUALTO("[>][=]"),
        LESSTHANOREQUALTO("[<][=]"),
        GREATHERTHAN(">"),
        LESSTHAN("<"),
        EQUALTO("[=][=]"),
        TAMPIL("[a-z]+=+\"[a-z0-9]*\"");



        private final String pattern;

        private Token(String pattern) {
            this.pattern = pattern;
        }
    }

    private static class Word {
        private final Token token;
        private final String lexeme;

        private Word(Token token, String lexeme) {
            this.token = token;
            this.lexeme = lexeme;
        }

        @Override
        public String toString() {
            return String.format("%-15s => [%s]", token.name(), lexeme);
        }
    }

    private static ArrayList<Word> lex(String input) {
        // The tokens to return
        ArrayList<Word> words = new ArrayList<>();

        // Lexer logic begins here --> ini tu buat biar mengubah kode untuk menerima simbol
        StringBuilder tokenPatternsBuffer = new StringBuilder();
        for (Token token : Token.values())
            tokenPatternsBuffer.append(String.format("|(?<%s>%s)", token.name(), token.pattern));
        Pattern tokenPatterns = Pattern.compile(tokenPatternsBuffer.substring(1));

        // Begin matching tokens
        Matcher matcher = tokenPatterns.matcher(input);
        while (matcher.find()) {
            for (Token token : Token.values())
                if (matcher.group(token.name()) != null) {
                    words.add(new Word(token, matcher.group(token.name())));
                }
        }
        return words;
    }

    public static void main(String[] args) {
        String input = "STRING = \"a\" 'a' a=\"a\" INT a1 = 2 * 2.09 \t DOUBLE = 2.0 + 30 - 2 * 1 +5\t TRUE FALSE tubes[1] fifa[comel] klsO[][] []labF | & ! >> << >= <= > < == != % / ^ CHAR " ;

        ArrayList<Word> words = lex(input);
        words.forEach((word) -> {
            System.out.println(word);
        });

        System.out.println("\nOperasi Aritmatika");

        int A=100, B=30, C=5;
        int jumlah = A+B;
        int kurang = A-B;
        int kali = A*B;
        float bagi = (float)A/B;
        int modulo = A%B;
        int kompleks1 = (C+B) * A;
        int kompleks2 = (A+B) + C;
        int kompleks3 = ((A+B) + C);

        System.out.println("Penjumlahan: "+A+" + "+B+" = "+jumlah);
        System.out.println("Pengurangan: "+A+" - "+B+" = "+kurang);
        System.out.println("Perkalian: "+A+" * "+B+" = "+kali);
        System.out.println("Pembagian: "+A+" / "+B+" = "+bagi);
        System.out.println("Modulo: "+A+" mod "+B+" = "+modulo);
        System.out.println("Aritmatika 1 : ("+C+"+"+B+") * "+A+ " = " +kompleks1);
        System.out.println("Aritmatika 2 : ("+A+"+"+B+") + "+C+ " = " +kompleks2);
        System.out.println("Aritmatika 3 : (("+A+"+"+B+") + "+C+") = " +kompleks3);

        //int A=100, B=30;
        System.out.println("\nOperasi Relasi");

        boolean lb = A>B;
        boolean lk = A<B;
        boolean lbs = A>=B;
        boolean lks = A<=B;
        boolean sm = A==B;
        boolean tsm = A!=B;

        System.out.println(A+" > "+B+" => "+lb);
        System.out.println(A+" < "+B+" => "+lk);
        System.out.println(A+" >= "+B+" => "+lbs);
        System.out.println(A+" <= "+B+" => "+lks);
        System.out.println(A+" = "+B+" => "+sm);
        System.out.println(A+" != "+B+" => "+tsm);

        System.out.println("\nOperasi Bit");

        int a = 60;    /* 60 = 0011 1100 */
        int b = 13;    /* 13 = 0000 1101 */
        int c = 0;

        c = a & b;       /* 12 = 0000 1100 */
        System.out.println("a & b = " + c);

        c = a | b;       /* 61 = 0011 1101 */
        System.out.println("a | b = " + c);

        c = a << 2;     /* 240 = 1111 0000 */
        System.out.println("a << 2 = " + c);

        c = a >> 2;     /* 215 = 1111 */
        System.out.println("a >> 2  = " + c);

    }


}
