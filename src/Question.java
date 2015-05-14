import java.util.ArrayList;
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
                //if(lineIn.charAt(lineIn.length()-2) == '?' || lineIn.charAt(lineIn.length()-3) == '?')
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
        for(int i = 0; i < 15; i++) //need to fix this shizz!!! gotta get it to ID when an answer has
                                    //two lines instead of just one :)
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
}
