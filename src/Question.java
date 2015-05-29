/**
 * Created by seach_000 on 5/22/2015.
 */
public abstract class Question {
    private String answer;
    private String question;
    private Boolean hasBeenAsked;
    abstract String getQuestion();
    abstract String getAnswer();
    abstract void setQuestion(String question);
    abstract void setAnswer(String answer);

    abstract void poseQuestion();
    abstract boolean takeAndEvaluateAnswer();

    private void flag_hasBeenAsked()
    {
        this.hasBeenAsked = true;
    }

    public static void questionEvent(Question pi)
    {
        pi.poseQuestion();
        pi.flag_hasBeenAsked();
        if(pi.takeAndEvaluateAnswer())
            System.out.println("\nYou did it!!!!");
        else
            System.out.println("\nFAIL");

    }
}
