import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Created by seach_000 on 5/2/2015.
 */
public class test {
    public static void main(String[] args) throws Exception
    {
      //load_DB_FromFile();
       ArrayList<Question> qList = loadUpQuestions();
       Question.questionEvent(qList.get(0));

    }

    public static void load_DB_FromFile() throws Exception
    {
        ArrayList<ArrayList<Question>> questionHolder = new ArrayList<ArrayList <Question>>();
        String input = "y";
        String pdf_File = "";
        Scanner masterList = pdfParse.openMasterList();
//        System.out.println(masterList.next());
        int numPages;
        int x = 1;
        Scanner kb = new Scanner(System.in);
        Connection conn = DriverManager.getConnection("jdbc:sqlite:test0.db");
        Statement stat = conn.createStatement();
        stat.executeUpdate("drop table if exists Questions");
        stat.executeUpdate("create table Questions(QID int , Question, Choice1, choice2, Choice3, Choice4, Answer, unique(Question, QID));");
        PreparedStatement statement = conn.prepareStatement("insert into QUESTIONS" +
                " VALUES(?, ?, ?, ?, ?, ?, ?)");
        //  ResultSet result;
        int count = 1;
        while(masterList.hasNext() == true)
        {
//            System.out.println("Please enter the name of the file to parse:"); //we will get this info from our file
            pdf_File = masterList.next();
            //          System.out.println("Please enter the number of pages:");
            numPages = masterList.nextInt();
            // masterList.nextLine();
            pdfParse test = null;
            try{
                test = new pdfParse(pdf_File + ".pdf", 1, numPages);
            }
            catch(Exception e)
            {

                System.out.println("\n\nWhoops, file didn't open!");
            }
            ArrayList<Question> temp = new ArrayList<Question>(15);
            Question.scanForQuestions(temp, test.toParse);
            Question.formatAnswers(temp);
            //Question.cleanQuestionFromFile(temp);
            String query = "";
            try {
                for (int i = 0; i < 15; i++) {
                    if(temp.get(i).answer.length() >5)
                    {
                        statement.setString(1, Integer.toString(count++));
                        statement.setString(2, temp.get(i).question);
                        statement.setString(3, temp.get(i).choices[0]);
                        statement.setString(4, temp.get(i).choices[1]);
                        statement.setString(5, temp.get(i).choices[2]);
                        statement.setString(6, temp.get(i).choices[3]);
                        statement.setString(7, temp.get(i).answer);
                        statement.executeUpdate();
                    }
/*
               */ }
            }
            catch(Exception e)
            {
                System.out.println("\nEASY THERE BUDDY!!!");
            }

            //result = stat.executeQuery("Select * from people");
            //System.out.println(result.getString("name"));


            System.out.println(x++);
            //input = kb.next();
            // kb.nextLine();

        }
    }

    public static ArrayList<Question> loadUpQuestions() throws Exception
    {
        ArrayList<Question> questionList = new ArrayList<Question>();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:test0.db");
        int numQuestions;
        Question toAdd;
        Statement statement = conn.createStatement();

        ResultSet results = null;
        PreparedStatement pullQuestion = conn.prepareStatement("SELECT * FROM QUESTIONS" +
                " WHERE QID like ?");
        results =statement.executeQuery("SELECT COUNT(QID) as total FROM QUESTIONS");
        numQuestions = results.getInt("total");
        for(int counter =1; counter <= numQuestions; counter++ )
        {
            pullQuestion.setString(1, Integer.toString(counter));
            results = pullQuestion.executeQuery();
            toAdd = new Question();
            toAdd.setQuestion(results.getString("Question"));
            toAdd.setAnswer(results.getString("Answer"));
            toAdd.scrambleChoices(results.getString("Choice1"), results.getString("Choice2"),
                    results.getString("Choice3"), results.getString("Choice4"));
            questionList.add(toAdd);
        }
        return questionList;
    }
}
