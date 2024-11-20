import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
        try {
        	
            NaiveBayes trainer = new NaiveBayes();
            trainer.trainModelFromDirectory("/home/abderrahim/eclipse-workspace/NaiveBayes/mini_newsgroups");           
//            
//         	Sauvegarder le modèle entraîné
            trainer.saveModel("/home/abderrahim/eclipse-workspace/NaiveBayes/modelTrainng");
            
            String directoryPath="/home/abderrahim/eclipse-workspace/NaiveBayes/testDocuments/";
            String destinationDir="/home/abderrahim/eclipse-workspace/NaiveBayes/classified_files";
            trainer.classifyAllFilesFromDirectory(directoryPath, destinationDir);
           
        	
//        	Charger le modèle Naïve Bayes 
            
//        	NaiveBayes nbLoaded = new NaiveBayes();
//	        nbLoaded.loadModel("/home/abderrahim/eclipse-workspace/NaiveBayes/modelTrainng");
//          String directoryPath="/home/abderrahim/eclipse-workspace/NaiveBayes/testDocuments/";
//          String destinationDir="/home/abderrahim/eclipse-workspace/NaiveBayes/classified_files2";
//          nbLoaded.classifyAllFilesFromDirectory(directoryPath, destinationDir);
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
}
