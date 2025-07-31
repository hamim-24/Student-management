package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionManager implements Serializable {

    private List<Question> questions;

    public QuestionManager() {
        questions = new ArrayList<Question>();
    }

    public void addQuestion(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        questions.add(question);
    }

    public List<Question> getQuestions() {
        return new ArrayList<>(questions); // Return a copy to prevent external modification
    }

    public void setQuestions(List<Question> questions) {
        if (questions == null) {
            throw new IllegalArgumentException("Questions list cannot be null");
        }
        this.questions = new ArrayList<>(questions);
    }

    public static void saveTofile(QuestionManager qm) {
        if (qm == null) {
            throw new IllegalArgumentException("QuestionManager cannot be null");
        }
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("abc.obj"))) {
            oos.writeObject(qm);
            System.out.println("QuestionManager saved successfully");
        } catch (IOException io) {
            System.err.println("Error saving QuestionManager to file: " + io.getMessage());
            throw new RuntimeException("Failed to save QuestionManager", io);
        }
    }

    public static QuestionManager loadFromfile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("abc.obj"))) {
            QuestionManager qm = (QuestionManager) ois.readObject();
            System.out.println("QuestionManager loaded successfully");
            return qm;
        } catch (FileNotFoundException fnf) {
            System.out.println("QuestionManager file not found, creating new instance");
            return new QuestionManager();
        } catch (IOException io) {
            System.err.println("Error loading QuestionManager from file: " + io.getMessage());
            return new QuestionManager(); // Return a new empty QuestionManager if file can't be loaded
        } catch (ClassNotFoundException cnf) {
            System.err.println("Error loading QuestionManager - class not found: " + cnf.getMessage());
            return new QuestionManager();
        } catch (ClassCastException cce) {
            System.err.println("Error loading QuestionManager - invalid file format: " + cce.getMessage());
            return new QuestionManager();
        }
    }
}
