import java.io.*;

public class SerializationTest {
    public static void main(String[] args) {
        // Create a QuestionManager
        QuestionManager qm = new QuestionManager();
        
        // Create a sample Question
        String[] options = {"Option A", "Option B", "Option C", "Option D"};
        Question q = new Question("What is the capital of France?", "Geography Question", "GEO001", options, "Option B");
        
        // Add the Question to the QuestionManager
        qm.addQuestion(q);
        
        // Save the QuestionManager to a file
        System.out.println("Saving QuestionManager to file...");
        QuestionManager.saveTofile(qm);
        
        // Load the QuestionManager from the file
        System.out.println("Loading QuestionManager from file...");
        QuestionManager loadedQm = QuestionManager.loadFromfile();
        
        // Verify that the loaded QuestionManager has the correct Question
        if (loadedQm != null && loadedQm.getQuestions() != null && !loadedQm.getQuestions().isEmpty()) {
            Question loadedQ = loadedQm.getQuestions().get(0);
            System.out.println("Successfully loaded Question:");
            System.out.println("Question: " + loadedQ.getQuestion());
            System.out.println("Question Name: " + loadedQ.getQuestionName());
            System.out.println("Question Code: " + loadedQ.getQuestionCode());
            System.out.println("Options: ");
            for (String option : loadedQ.getOptions()) {
                System.out.println("  - " + option);
            }
            System.out.println("Answer: " + loadedQ.getAnswer());
        } else {
            System.out.println("Failed to load Question from file.");
        }
    }
}