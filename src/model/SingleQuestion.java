package model;

public class SingleQuestion {

    private final String question;
    private final String[] options;
    private final String answer; // not correct option correct answer

    public SingleQuestion(String question, String[] options, String answer) {
        // Validate constructor parameters
        if (question == null || question.trim().isEmpty()) {
            throw new IllegalArgumentException("Question text cannot be null or empty");
        }
        if (options == null || options.length != 4) {
            throw new IllegalArgumentException("Options array must contain exactly 4 options");
        }
        for (int i = 0; i < options.length; i++) {
            if (options[i] == null || options[i].trim().isEmpty()) {
                throw new IllegalArgumentException("Option " + (i + 1) + " cannot be null or empty");
            }
        }
        if (answer == null || answer.trim().isEmpty()) {
            throw new IllegalArgumentException("Answer cannot be null or empty");
        }
        
        // Validate that answer matches one of the options
        boolean answerFound = false;
        for (String option : options) {
            if (option.trim().equals(answer.trim())) {
                answerFound = true;
                break;
            }
        }
        if (!answerFound) {
            throw new IllegalArgumentException("Answer must match one of the provided options");
        }

        this.question = question.trim();
        this.options = new String[4];
        for (int i = 0; i < options.length; i++) {
            this.options[i] = options[i].trim();
        }
        this.answer = answer.trim();
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options.clone(); // Return a copy to prevent external modification
    }

    public String getAnswer() {
        return answer;
    }

    public String toString() {
        return String.format(
                "%s\n    a) %s\n    b) %s\n    c) %s\n    d) %s\n", 
                question, options[0], options[1], options[2], options[3]
        );
    }
}