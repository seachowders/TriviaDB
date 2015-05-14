/**
 * Created by seach_000 on 5/1/2015.
 */
import org.apache.pdfbox.*;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class pdfParse
{
    Scanner toParse;
    public pdfParse(String fileName, int startPage, int endPage)throws Exception
    {
        PDFTextStripper pdfStrip =null;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
        PDFParser parser = null;
        File file = new File(fileName);
        try
        {
            parser = new PDFParser(new FileInputStream(file));
        }
        catch(Exception e)
        {
            System.out.println("LOL");
        }
        parser.parse();
        cosDoc = parser.getDocument();
        pdfStrip = new PDFTextStripper();
        pdDoc = new PDDocument(cosDoc);
        pdfStrip.setStartPage(startPage);
        pdfStrip.setEndPage(endPage);
        String parsedText = pdfStrip.getText(pdDoc);
        pdDoc.close();
        toParse = new Scanner(parsedText);
        //System.out.println(parsedText);
      //  System.out.println(toParse.nextLine());
    }

    public static Scanner openMasterList()
    {
        Scanner masterList = null;
        File inf = new File("masterlist.txt");
        try{
            masterList = new Scanner(inf);
        }
        catch(Exception e)
        {
            System.out.println("\nFile did not open :( ");
        }
        return masterList;
    }

}
