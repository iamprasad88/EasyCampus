/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.io.Serializable;

/**
 *
 * @author Phaneendra
 */
public class Report implements Serializable
{

    public double CutOff[];
    public double QM[][];
    public double SM[];
    public boolean SResult[];
    public boolean FinalResult;
    public String UserName;
    public String Class;
    public String USN;
    public String Section;
    public double MaxTotal;
    public double GotTotal;

    public Report(Test Q, Test R)
    {
        QM = new double[Q.NoOfSections][];
        CutOff = new double[Q.NoOfSections];
        SM = new double[Q.NoOfSections];
        SResult = new boolean[Q.NoOfSections];
        FinalResult = true;

        UserName = R.UserName;
        USN = R.USN;
        Class = R.Class;
        Section = R.Section;

        MaxTotal = 0.0d;
        GotTotal = 0.0d;

        for (int i = 0; i < Q.NoOfSections; i++)
        {
            CutOff[i] = Q.SectionList[i].CutOff;
            SResult[i] = false;
            QM[i] = new double[Q.SectionList[i].QAList.length];

            SM[i] = 0.0d;

            for (int j = 0; j < Q.SectionList[i].QAList.length; j++)
            {
                QM[i][j] = 0.0d;
                MaxTotal += ((Question) Q.SectionList[i].QAList[j]).Marks;
            }
        }
    }

    public void process()
    {
        for (int i = 0; i < SM.length; i++)
        {

            for (int j = 0; j < QM[i].length; j++)
            {
                SM[i] += QM[i][j];
                
            }
            if (SM[i] < CutOff[i])
            {
                SResult[i] = false;
                FinalResult = false;
            }
            else
            {
                SResult[i] = true;
            }
            GotTotal += SM[i];
        }

    }
}
