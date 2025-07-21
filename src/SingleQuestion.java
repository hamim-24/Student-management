public class SingleQuestion {

    private String question;
    private String[] options;
    String answer; // not correct option correct answer

    public SingleQuestion(String question, String[] options, String answer) {
        this.question = question;
        this.options = options;
        this.answer = answer;
    }

    public String toString() {
        return String.format(
                "%s\n    a) %s\n    b) %s\n    c) %s\n    d) %s\n", question, options[0], options[1], options[2], options[3]
        );
    }
}