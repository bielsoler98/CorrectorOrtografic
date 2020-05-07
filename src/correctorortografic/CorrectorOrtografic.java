/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correctorortografic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author biels
 */
public class CorrectorOrtografic {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            String word = "koko";
            HashSet<String> dicc = loadDiccionari();
            if (!dicc.contains(word)) {
                PalabraIncorrecta pi = new PalabraIncorrecta(word);
                dicc.forEach((s) -> {
                    int distance = getDistance(pi.getContent(), s);
                    if (distance < pi.getMinDistance()) {
                        pi.setMinDistance(distance);
                        pi.addWord(s);
                    } else if (distance == pi.getMinDistance()) {
                        pi.addWord(s);
                    }
                });
                pi.getNearestWords().forEach((it) -> System.out.println(it + " Distance: " + pi.getMinDistance()));
            } else {
                System.out.println("La palabra es correcta");
            }
        } catch (IOException ex) {
            Logger.getLogger(CorrectorOrtografic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static HashSet<String> loadDiccionari() throws IOException {
        HashSet<String> dicc = new HashSet();
        try (Stream<String> stream = Files.lines(Paths.get("diccionari/esutfnobom.dic"))) {
            stream.forEach((it) -> dicc.add(it));
        }
        return dicc;
    }

    public String[] getParaules(String s) {
        char[] invalidChar = {',', '.', '"', '<', '>', '?', '¿', '!', '¡', '(', ')', '[', ']',
            '\'', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', ':', ';', '-', '@', '_',
            '#', '%', '&',};
        for (char c : invalidChar) {
            s = s.replace(c, ' ');
        }
        String[] sarr = s.split(" ");
        for (String o : sarr) {
            System.out.println(o);
        }
        return sarr;
    }

    public static int getDistance(String wrongWord, String wordToCompare) {
        int[][] dp = new int[wrongWord.length() + 1][wordToCompare.length() + 1];
        for (int i = 0; i <= wrongWord.length(); i++) {
            for (int j = 0; j <= wordToCompare.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = min(dp[i - 1][j - 1]
                            + costOfSubstitution(wrongWord.charAt(i - 1), wordToCompare.charAt(j - 1)),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1);
                }
            }
        }

        return dp[wrongWord.length()][wordToCompare.length()];
    }

    public static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    public static int min(int... numbers) {
        return Arrays.stream(numbers)
                .min().orElse(Integer.MAX_VALUE);
    }
}
