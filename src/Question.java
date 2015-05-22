import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by seach_000 on 5/2/2015.
 * 5/8 -- added the formatAnswers method that is used to aid in processing our question and answer data sets
 *      returns nothing but does alter the answer string object to indicate what choice is the correct one
 *
 *      added the findAndAlter method to match the correct answer with it's corresponding letter choice
 */
public class Question {
    String question;
    String[] choices;
    String answer;

    public Question()
    {
        question = "";
        choices = new String[4];
        answer = "";
    }

    public void setQuestion(String inc)
    {
        this.question = inc;
    }
    public void setChoice(int index, String choice)
    {
        this.choices[index] = choice;
    }
    public void setAnswer(String inc)
    {
        this.answer = inc;
    }

    public String getQuestion(){return this.question;}
    public String getAnswer(){return this.answer;}
    public String[] getChoices() {return this.choices;}
    //in this I want to get questions, and choices
    public static void scanForQuestions(ArrayList<Question> list, Scanner toScan){
        String lineIn= "";
        Question toAdd;
        for(int i =0; i < 15; i++)
        {
            while (lineIn.contains(">") != true)
            {
                lineIn =toScan.nextLine();
            }
            toAdd = new Question();
            toAdd.question += lineIn;
            lineIn = toScan.nextLine();
            while (lineIn.length() >=3 && lineIn.contains("a.") != true)
            {
                if(lineIn.contains("a.")!= true)
                {
                    toAdd.question += lineIn;
                    lineIn = toScan.nextLine();
                }
            }
            while(lineIn.length() < 2)
                lineIn = toScan.nextLine();
            if(lineIn.contains("a.") == true)
            {
                toAdd.choices[0] = lineIn;
            }
            else
                toAdd.choices[0] = toScan.nextLine();
            toAdd.choices[1] = toScan.nextLine();
            toAdd.choices[2] = toScan.nextLine();
            toAdd.choices[3] = toScan.nextLine();
            list.add(toAdd);
            lineIn = toScan.nextLine();
        }
        while(lineIn.contains("Answer") != true)
        {
            lineIn = toScan.nextLine();
        }
        for(int i = 0; i < 15; i++)
        {
            lineIn = toScan.nextLine();
            if(lineIn.contains(">")!= true && lineIn.length() == 1)
            {
                i--;
                //list.get(i).answer += lineIn;

            }
            else
            {
                if(lineIn.contains(">") == true)
                {
                    list.get(i).answer = lineIn;
                }
                else
                if(lineIn.charAt(lineIn.length()-3) == '.' || lineIn.charAt(lineIn.length()-2) == '.')
                {
                    i--;
                    list.get(i).answer += lineIn;
                }
            }
           // if(list.get(i).answer.length()<2)
            //    i--;
        }

    }

    /*
      this needs to go into each question in our list.  Needs to get the correct answer, then compare the correct answer
      with the choices, to see what letter the answer corresponds to...I think we would just put the letter in front of
      our correct answer
     */
    public static void formatAnswers(ArrayList<Question> list)
    {
        String a, b, c, d, newAns;
        for(int i =0; i < list.size(); i ++)
        {
            a = list.get(i).choices[0].substring(6);
            b = list.get(i).choices[1].substring(6);
            c = list.get(i).choices[2].substring(6);
            d = list.get(i).choices[3].substring(6);
            newAns = findAndAlterAnswer(a, b, c, d, list.get(i).answer);
            list.get(i).answer = newAns;
        }


    }

    public static String findAndAlterAnswer(String a, String b, String c, String d, String ans)
    {
        String newAns = "";
        if(ans.contains(a))
        {
            newAns ="a" + ans.substring(2);
            return newAns;
        }
        else
            if(ans.contains(b))
            {
                newAns ="b" + ans.substring(2);
                return newAns;
            }
            else
                if(ans.contains(c))
                {
                    newAns = "c" + ans.substring(2);
                    return newAns;
                }
                else
                    if(ans.contains(d))
                    {
                        newAns ="d" + ans.substring(2);
                        return newAns;
                    }

    return newAns;
    }
    /*
    public static void cleanQuestionFromFile(ArrayList<Question> list)//this needs to strip off the letters and numbers
    {
        String answer, question;
        String choices[];
        int loc;
        for(int i = 0; i < list.size(); i ++)
        {
            answer = list.get(i).getAnswer();
            question = list.get(i).getQuestion();
            choices = list.get(i).getChoices();
            loc = question.indexOf(">");
            list.get(i).setQuestion(question.substring(loc+1));
            list.get(i).setAnswer(answer.substring(2));




        }
    }
*/
    public static void CreateAndAddQuestion()
    {
        Question toAdd = createQuestion();

    }

    public static Question createQuestion()
    {
        Scanner scanner = new Scanner(System.in);
        Question toAdd  = new Question();
        String question = "";
        String choice1 = "";
        String choice2 = "";
        String choice3 = "";
        String answer = "";
        System.out.println("\nPlease enter the question you wish to add: ");
        question = scanner.nextLine();
        System.out.println("\nPlease enter the correct answer: ");
        answer = scanner.nextLine();
        System.out.println("\nPlease enter a false choice: ");
        choice1 = scanner.nextLine();
        System.out.println("\nPlease enter a second false choice: ");
        choice2 = scanner.nextLine();
        System.out.println("\nPlease enter a third false choice: ");
        choice3 = scanner.nextLine();
        toAdd.setQuestion(question);
        toAdd.setAnswer(answer);
        toAdd.scrambleChoices(choice1, choice2, choice3, answer);



        return null;
    }

    public void scrambleChoices(String one, String two, String three, String four)
    {
        Random rand = new Random();
        int ans = rand.nextInt(4);
        this.setChoice(ans, four);
        this.setChoice((ans+1)%4, one);
        this.setChoice((ans+2)%4, two);
        this.setChoice((ans+3)%4, three);
    }


    private void poseQuestion()
    {
        System.out.println("\nQuestion: ");
        System.out.println(this.getQuestion());
        System.out.println("\nChoices: ");
        System.out.println("1 " + this.getChoices()[0]);
        System.out.println("2 " + this.getChoices()[1]);
        System.out.println("3 " + this.getChoices()[2]);
        System.out.println("4 " + this.getChoices()[3]);
    }

    private boolean takeAndEvaluateAnswer()
    {
        boolean ansIn = false;
        Scanner scanner = new Scanner(System.in);
        String answerIn;
        int choice = 0;
        do
        {
            try
            {
                choice = scanner.nextInt();
            } catch (InputMismatchException e)
            {
                System.out.println("\nInvalid Selection");
            }
        }while(choice<1 || choice>4);
        answerIn = this.getChoices()[choice];

    }
    public static void questionEvent(Question pi)
    {
        pi.poseQuestion();
        pi.takeAndEvaluateAnswer();
    }
}
