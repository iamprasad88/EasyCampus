/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author prasad
 */
public class Test implements Serializable
{
    public String Name; //Compulsary
    public String Course;
    public String SubName;
    public String SubCode;
    public String Instruction;
    public Date DateOfTest;
    public int NoOfSections;
    public Section SectionList[];
    File RSP = new File(".");
    private String FinishMsg = "";

    public Test(String Name,String Course,String SubName,String SubCode,String Instruction,
            Date DateOfTest,int NoOfSections)
    {
        this.Name = Name;
        this.Course = Course;
        this.SubName = SubName;
        this.SubCode = SubCode;
        this.Instruction = Instruction;
        this.DateOfTest = DateOfTest;
        this.NoOfSections = NoOfSections;

        SectionList = new Section[NoOfSections];
    }
    public String UserName;
    public String USN;
    public String Class;
    public String Section;
//  if the status is selected, the field must be displayed while taking test
//  execpt UserName which is compulsary
    boolean USNStatus;
    boolean ClassStatus;
    boolean SectionStatus;

    public void setUSNStatus(boolean USNStatus)
    {
        this.USNStatus = USNStatus;
    }

    public void setClassStatus(boolean ClassStatus)
    {
        this.ClassStatus = ClassStatus;
    }

    public void setSectionStatus(boolean SectionStatus)
    {
        this.SectionStatus = SectionStatus;
    }

    public boolean getUSNStatus()
    {
        return USNStatus;
    }

    public boolean getClassStatus()
    {
        return ClassStatus;
    }

    public boolean getSectionStatus()
    {
        return SectionStatus;
    }

    public File getRSP()
    {
        return RSP;
    }

    public void setRSP(File RSP)
    {
        this.RSP = RSP;
    }

    public void setFinishMsg(String FinishMsg)
    {
        this.FinishMsg = FinishMsg;
    }

    public String getFinishMsg()
    {
        return FinishMsg;
    }
}