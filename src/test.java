import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Created by seach_000 on 5/2/2015.
 */
public class test {
    public static void main(String[] args) throws Exception
    {
     //   load_DB_FromFile();
      // ArrayList<MC_Question> qList = loadUpMC_Questions();
      // MC_Question.createAndAddQuestion(qList);
       // loadTFQuestionsDB();
     //   loadSAQuestionsDB();
        ArrayList<TF_Question> qList = loadUpTF_Questions();
        System.out.println("LOL");


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
        stat.executeUpdate("drop table if exists Questions1");
        stat.executeUpdate("create table Questions1(QID int , Question, Choice1, choice2, Choice3, Choice4, Answer, unique(Question, QID));");
        PreparedStatement statement = conn.prepareStatement("insert into QUESTIONS1" +
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
            ArrayList<MC_Question> temp = new ArrayList<MC_Question>(15);
            MC_Question.scanForQuestions(temp, test.toParse);
            MC_Question.formatAnswers(temp);
            //Question.cleanQuestionFromFile(temp);
            String query = "";
            try {
                for (int i = 0; i < 15; i++) {
                    if(temp.get(i).answer.length() >5)
                    {
                        statement.setString(1, Integer.toString(count++));
                        statement.setString(2, temp.get(i).question);
                        statement.setString(3, temp.get(i).getChoice(0).toString()); //.choices[0]);
                        statement.setString(4, temp.get(i).getChoice(1).toString());
                        statement.setString(5, temp.get(i).getChoice(2).toString());
                        statement.setString(6, temp.get(i).getChoice(3).toString());
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

    public static ArrayList<MC_Question> loadUpMC_Questions() throws Exception
    {
        ArrayList<MC_Question> questionList = new ArrayList<MC_Question>();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:test0.db");
        int numQuestions;
        MC_Question toAdd;
        Statement statement = conn.createStatement();

        ResultSet results = null;
        PreparedStatement pullQuestion = conn.prepareStatement("SELECT * FROM QUESTIONS1" +
                " WHERE QID like ?");
        results =statement.executeQuery("SELECT COUNT(QID) as total FROM QUESTIONS1");
        numQuestions = results.getInt("total");
        for(int counter =1; counter <= numQuestions; counter++ )
        {
            pullQuestion.setString(1, Integer.toString(counter));
            results = pullQuestion.executeQuery();
            toAdd = new MC_Question();
            toAdd.setQuestion(results.getString("Question"));
            toAdd.setAnswer(results.getString("Answer"));
            toAdd.scrambleAndSetChoices(results.getString("Choice1"), results.getString("Choice2"),
                    results.getString("Choice3"), results.getString("Choice4"));
            toAdd.findAns();
            questionList.add(toAdd);
            System.out.println(counter);
        }
        conn.close();
        return questionList;

    }

    public static ArrayList<TF_Question> loadUpTF_Questions() throws Exception
    {
        ArrayList<TF_Question> questionList = new ArrayList<TF_Question>();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:test0.db");
        int numQuestions;
        TF_Question toAdd;
        Statement statement = conn.createStatement();

        ResultSet results = null;
        PreparedStatement pullQuestion = conn.prepareStatement("SELECT * FROM TF_Questions" +
                " WHERE QID like ?");
        results =statement.executeQuery("SELECT COUNT(QID) as total FROM TF_Questions");
        numQuestions = results.getInt("total");
        for(int counter =1; counter <= numQuestions; counter++ )
        {
            pullQuestion.setString(1, Integer.toString(counter));
            results = pullQuestion.executeQuery();
            toAdd = new TF_Question();
            toAdd.setQuestion(results.getString("Question"));
            toAdd.setAnswer(results.getString("Answer"));
            questionList.add(toAdd);
            System.out.println(counter);
        }

        conn.close();
        return questionList;

    }
   public static void loadTFQuestionsDB() throws Exception
   {
       Connection conn = DriverManager.getConnection("jdbc:sqlite:test0.db");
       Statement stat = conn.createStatement();
       stat.executeUpdate("drop table if exists TF_Questions");
       stat.executeUpdate("create table TF_Questions(QID int , Question, Answer, unique(Question, QID));");
       PreparedStatement statement = conn.prepareStatement("insert into TF_Questions" +
               " VALUES(?, ?, ?)");


       ArrayList<TF_Question> qList = new ArrayList<TF_Question>();
       TF_Question toAdd = new TF_Question();
       File file = new File("TFQuestions.txt");
       String question, answer = "";
       Scanner scanner = new Scanner(file);
       while(scanner.hasNextLine())
       {
           question = scanner.nextLine();
           answer = scanner.nextLine();
           question = question.replace('?', '.');
           toAdd = new TF_Question();
           toAdd.setQuestion(question);
           toAdd.setAnswer(answer);
           qList.add(toAdd);
       }
       for(int i = 0; i < qList.size(); i ++)
       {
           try {
               statement.setString(1, Integer.toString(i + 1));
               statement.setString(2, qList.get(i).getQuestion());
               statement.setString(3, qList.get(i).getAnswer());
               statement.executeUpdate();
           }
           catch(Exception e)
           {
               System.out.println("\nSORRYRYRYRYRYR");
           }
       }

   }

    public static void loadSAQuestionsDB() throws Exception
    {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:test0.db");
        Statement stat = conn.createStatement();
        stat.executeUpdate("drop table if exists SA_Questions");
        stat.executeUpdate("create table SA_Questions(QID int , Question, Answer, unique(Question, QID));");
        PreparedStatement statement = conn.prepareStatement("insert into SA_Questions" +
                " VALUES(?, ?, ?)");

        ArrayList<SA_Question> qList = new ArrayList<SA_Question>();
        SA_Question toAdd = new SA_Question();
        File file = new File("SAQuestions.txt");
        String question, answer = "";
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine())
        {
            question = scanner.nextLine();
            answer = scanner.nextLine();
            question = ridBlankSpace(question);
            answer = ridBlankSpace(answer);
           // question = question.replace('?', '.');
            toAdd = new SA_Question();
            toAdd.setQuestion(question);
            toAdd.setAnswer(answer);
            qList.add(toAdd);
            Question.questionEvent(toAdd);
        }
        for(int i = 0; i < qList.size(); i ++)
        {
            try {
                statement.setString(1, Integer.toString(i + 1));
                statement.setString(2, qList.get(i).getQuestion());
                statement.setString(3, qList.get(i).getAnswer());
                statement.executeUpdate();
            }
            catch(Exception e)
            {
                System.out.println("\nSORRYRYRYRYRYR");
            }
        }
        conn.close();

    }
    public static String ridBlankSpace(String string)
    {
        String rt = string;
        while(rt.charAt(rt.length()-1) == ' ')
        {
            rt = rt.substring(0, rt.length()-1);
        }
        return rt;
    }
}
