import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class NaiveBayes {
    
    private Map<String, Map<String, Integer>> wordCountsPerClass = new HashMap<>();
    private Map<String, Integer> classCounts = new HashMap<>();
    private Set<String> vocabulary = new HashSet<>();
    private int totalDocuments = 0;
    public ArrayList<String> stopWords;
    /**
     * 
     */
    public NaiveBayes() {
    	this.stopWords=this.tokenisation("/home/abderrahim/eclipse-workspace/NaiveBayes/stopWords.text");
    }
    /**
     * 
     * @param file
     * @return
     */
    public ArrayList<String> tokenisation(String file) {
   	 ArrayList<String> list=new ArrayList<String>();
   	 try {
            Scanner scanner = new Scanner(new File(file));
            while (scanner.hasNext()) {
                String mot = scanner.next();
                list.add(mot);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
        	System.out.println("existe un erreur dans votre code en tokenisation");
            e.printStackTrace();
        }
   	 
   	return list;
   }
    /**
     * 
     * @param content
     * @return
     */
    public String removerStopWords(String content) {
    	String text=new String();
    	String[] wordsArray = content.toLowerCase().split("\\s+");
    	ArrayList<String> temp=new ArrayList<String>(Arrays.asList(wordsArray));
    	temp.removeAll(this.stopWords);
    	text= String.join(" ", temp);
    	return text; 
    }
    /**
     * 
     * @param text
     * @return
     * @throws IOException
     */
    public String stemText(String text) throws IOException {
        StringBuilder stemmedText = new StringBuilder();

        WhitespaceTokenizer tokenizer = new WhitespaceTokenizer();
        tokenizer.setReader(new StringReader(text));

        TokenStream tokenStream = new PorterStemFilter(tokenizer);
        tokenStream.reset();

        CharTermAttribute term = tokenStream.getAttribute(CharTermAttribute.class);
        while (tokenStream.incrementToken()) {
            stemmedText.append(term.toString()).append(" ");
        }

        return stemmedText.toString().trim();
    }
    
    /**
     * Entraîner le modèle avec un document et sa classe
     * @param classe
     * @param document
     * @throws IOException
     */
    public void train(String classe, String document) throws IOException {
    	String document2=this.stemText(this.removerStopWords(document));
        String[] words = document2.toLowerCase().split("\\s+");
        
        classCounts.put(classe, classCounts.getOrDefault(classe, 0) + 1);
        totalDocuments++;
        
        Map<String, Integer> wordCounts = wordCountsPerClass.getOrDefault(classe, new HashMap<>());
        for (String word : words) {
            vocabulary.add(word);
            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
        }
        wordCountsPerClass.put(classe, wordCounts);
    }
    
    /**
     * Méthode pour entraîner le modèle avec des fichiers texte
     * @param directoryPath
     * @throws IOException
     */
    public void trainModelFromDirectory(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        File[] classDirs = directory.listFiles(File::isDirectory);
        
        for (File classDir : classDirs) {
            String className = classDir.getName(); 
            File[] textFiles = classDir.listFiles((dir, name) -> !new File(dir, name).isDirectory());
            
            for (File textFile : textFiles) {
                String text = new String(Files.readAllBytes(textFile.toPath()));
                
                this.train(className, text);
                
            }
        }
    }
   /**
    * Calculer la probabilité pour une catégorie donnée
    * @param classe
    * @param words
    * @return
    */
    private double calculateClassProbability(String classe, String[] words) {
        double classProbability = Math.log((double) classCounts.get(classe) / totalDocuments);
        Map<String, Integer> wordCounts = wordCountsPerClass.get(classe);
        int totalWordsInClass = wordCounts.values().stream().mapToInt(Integer::intValue).sum();
        
        for (String word : words) {
            int wordCount = wordCounts.getOrDefault(word, 0) + 1; // Lissage de Laplace
            double wordProbability = Math.log((double) wordCount / (totalWordsInClass + vocabulary.size()));
            classProbability += wordProbability;
        }
        
        return classProbability;
    }
    /**
     * Classifier un texte
     * @param document
     * @return
     */
    public String classify(String document) {
        String[] words = document.toLowerCase().split("\\s+");
        String bestClass = null;
        double maxProbability = Double.NEGATIVE_INFINITY;
        
        for (String category : classCounts.keySet()) {
            double probability = calculateClassProbability(category, words);
            if (probability > maxProbability) {
                maxProbability = probability;
                bestClass = category;
            }
        }
        return bestClass;
    }
    /**
     * Cete méthode permet de classifier un fichier texte en fonction de son contenu,
     * puis de le copier vers un dossier de destination tout en le renommant pour
     * inclure la classe prédite.
     * @param filePath
     * @param destinationDir
     * @throws IOException
     */
    public void classifyAndCopyFile(String filePath, String destinationDir) throws IOException {
        File file = new File(filePath);
        
        if (!file.exists()) {
            throw new FileNotFoundException("Le fichier spécifié n'existe pas : " + filePath);
        }

        String text = new String(Files.readAllBytes(file.toPath()));
        String predictedClass = this.classify(text);
        File destinationFolder = new File(destinationDir);
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs();
        }

        String newName = file.getName() + "_" + predictedClass;
        File copiedFile = new File(destinationFolder, newName);
        Files.copy(file.toPath(), copiedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        
        System.out.println("Fichier déplacé et renommé en : " + copiedFile.getAbsolutePath());
    
    }
    /**
     * Méthode permettant de classifier tous les fichiers dans un répertoire 
     * donné et les déplacer dans un répertoire de destination classifié
     * @param directoryPath
     * @param destinationDir
     * @throws IOException
     */
    public void classifyAllFilesFromDirectory(String directoryPath,String destinationDir) throws IOException {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        
        for (File file : files) { 
           this.classifyAndCopyFile(file.getAbsolutePath(), destinationDir);
        }
    }
    /**
     * Cette méthode est utilisée pour sauvegarder un modèle Naïve Bayes 
     * entraîné dans un fichier. Cela permet de réutiliser le modèle ultérieurement 
     * sans avoir à le réentraîner.
     * @param filename
     * @throws IOException
     */
    public void saveModel(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            // Sérialiser les informations du modèle
            oos.writeObject(wordCountsPerClass);
            oos.writeObject(classCounts);
            oos.writeObject(vocabulary);
            oos.writeInt(totalDocuments);
        }
    }
    /**
     * Cette méthode permet de charger un modèle Naïve Bayes précédemment sauvegardé 
     * depuis un fichier. Elle désérialise les données nécessaires pour restaurer 
     * l'état du modèle, afin de pouvoir les utiliser directement pour classifier 
     * des textes.
     * @param filename
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void loadModel(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            // Charger les informations du modèle
            wordCountsPerClass = (Map<String, Map<String, Integer>>) ois.readObject();
            classCounts = (Map<String, Integer>) ois.readObject();
            vocabulary = (Set<String>) ois.readObject();
            totalDocuments = ois.readInt();
        }
    }

}
