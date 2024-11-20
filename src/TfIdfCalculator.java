import java.util.*;
import java.util.stream.Collectors;

public class TfIdfCalculator {

    public double calculateTF(String word, String[] document) {
        double wordCount = 0;
        for (String w : document) {
            if (w.equalsIgnoreCase(word)) {
                wordCount++;
            }
        }
        return wordCount / document.length;
    }

    public double calculateIDF(String word, List<String[]> documents) {
        double docsWithWord = 0;
        for (String[] doc : documents) {
            for (String w : doc) {
                if (w.equalsIgnoreCase(word)) {
                    docsWithWord++;
                    break;
                }
            }
        }
        // Ajout de 1 dans le numérateur et le dénominateur pour éviter les valeurs négatives et divisions par zéro
        return Math.log((documents.size() + 1) / (1 + docsWithWord));
    }

    public double calculateTFIDF(String word, String[] document, List<String[]> documents) {
        double tf = calculateTF(word, document);
        double idf = calculateIDF(word, documents);
        return tf * idf;
    }

    public Map<String, Double> calculateTFIDFForDocument(String[] document, List<String[]> documents) {
        Map<String, Double> tfidfScores = new HashMap<>();
        Set<String> uniqueWords = new HashSet<>(Arrays.asList(document));
        
        for (String word : uniqueWords) {
            double tfidf = calculateTFIDF(word, document, documents);
            tfidfScores.put(word, tfidf);
        }
        
        return tfidfScores;
    }

    public List<Map<String, Double>> calculateTFIDFMatrix(List<String[]> documents) {
        List<Map<String, Double>> tfidfMatrix = new ArrayList<>();
        
        for (String[] document : documents) {
            Map<String, Double> tfidfScores = calculateTFIDFForDocument(document, documents);
            tfidfMatrix.add(tfidfScores);
        }
        
        return tfidfMatrix;
    }
    
}
