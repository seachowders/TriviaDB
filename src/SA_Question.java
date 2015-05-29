import java.util.Scanner;

/**
 * Created by seach_000 on 5/27/2015.
 */
public class SA_Question extends Question {


    public SA_Question()
    {
        super.answer = "";
        super.question = "";
        super.hasBeenAsked = false;
    }
    @Override
    String getQuestion() {
        return this.question;
    }

    @Override
    String getAnswer() {
        return this.answer;
    }

    @Override
    void setQuestion(String question)
    {
        this.question = question;
    }

    @Override
    void setAnswer(String answer)
    {
        this.answer = answer;
    }

    @Override
    void poseQuestion()
    {
        String questionType = "\nShort Answer";
        String question = "\nQuestion: " + this.getQuestion();
        System.out.println(questionType);
        System.out.println(question);
    }

    @Override
    boolean takeAndEvaluateAnswer()
    {
        Scanner scanner = new Scanner(System.in);
        String answerIn = "";
        String promptForAnswer = "\nPlease enter an answer";
        System.out.println(promptForAnswer);
        answerIn = scanner.nextLine();
        if(this.getAnswer().equalsIgnoreCase(answerIn))
            return true;
        return false;
    }
}
