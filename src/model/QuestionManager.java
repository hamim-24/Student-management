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
        questions.add(question);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public static void saveTofile(QuestionManager qm) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("abc.obj"));

            oos.writeObject(qm);
        } catch (IOException io) {
            System.out.println("Error saving file");
        }
    }

    public static QuestionManager loadFromfile() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("abc.obj"));
            return (QuestionManager) ois.readObject();
        } catch (IOException | ClassNotFoundException io) {
            System.out.println("Error loading file");
            return new QuestionManager(); // Return a new empty QuestionManager if file can't be loaded
        }
    }
}
