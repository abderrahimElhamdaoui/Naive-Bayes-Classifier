

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;

public class StemmerUtil {

    // Fonction de stemming qui prend un texte en entrée et renvoie la version stemmée du texte
    public static String stemText(String text) throws IOException {
        // Initialiser un StringBuilder pour accumuler les mots stemmés
        StringBuilder stemmedText = new StringBuilder();

        // Tokeniser le texte par espaces
        WhitespaceTokenizer tokenizer = new WhitespaceTokenizer();
        tokenizer.setReader(new StringReader(text));

        // Appliquer le PorterStemFilter pour chaque token
        TokenStream tokenStream = new PorterStemFilter(tokenizer);
        tokenStream.reset();

        // Parcourir chaque token, appliquer le stemming, et ajouter au StringBuilder
        CharTermAttribute term = tokenStream.getAttribute(CharTermAttribute.class);
        while (tokenStream.incrementToken()) {
            // Ajouter le mot stemmé au texte final
            stemmedText.append(term.toString()).append(" ");
        }

        // Retourner le texte avec tous les mots stemmés, enlever l'espace final
        return stemmedText.toString().trim();
    }

    public static void main_1(String[] args) {
        try {
            // Exemple d'utilisation de la fonction de stemming pour un texte
            String originalText = "Running runners run quickly";
            String stemmedText = stemText(originalText);

            // Afficher les résultats
            System.out.println("Original Text: " + originalText);
            System.out.println("Stemmed Text: " + stemmedText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
