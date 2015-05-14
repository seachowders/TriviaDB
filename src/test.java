import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Created by seach_000 on 5/2/2015.
 */
public class test {
    public static void main(String[] args) throws Exception
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
        PreparedStatement statement = conn.prepareStatement("insert into QUESTIONS" +
                        " VALUES(?, ?, ?, ?, ?, ?)");
        stat.executeUpdate("drop table if exists Questions");
        stat.executeUpdate("create table Questions(Question, Choice1, choice2, Choice3, Choice4, Answer, unique(Question));");
      //  ResultSet result;
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
            String query = "";
            try {
                for (int i = 0; i < 15; i++) {
                    statement.setString(1, temp.get(i).question);
                    statement.setString(2, temp.get(i).choices[0]);
                    statement.setString(3, temp.get(i).choices[1]);
                    statement.setString(4, temp.get(i).choices[2]);
                    statement.setString(5, temp.get(i).choices[3]);
                    statement.setString(6, temp.get(i).answer);
                    statement.executeUpdate();
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
}
