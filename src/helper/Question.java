/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author prasad
 */
public class Question implements QuestionAnswer,Serializable
{
//  Question Types
    public static final int MultipleChoice = 0;
    public static final int FillInTheBlanks = 1;
    public static final int Essay = 2;
    public static File Dir;
//    Question Members
    public double Marks;
    public double NegMarks;
    public String QuestionString;
    public int Type;
    public String MCQOptions[];
    public String Images[];
    public String Audio[];

    public Question(double Marks,Double NegMarks,
            String QuestiuonString,int Type)
    {
        this.Marks = Marks;
        this.NegMarks = NegMarks;
        this.QuestionString = QuestiuonString;
        this.Type = Type;
        Images = new String[0];
        Audio = new String[0];
    }

    public static void setDir(File Dir)
    {
        if(Dir.isDirectory())
        {
            Question.Dir = Dir;
        }
    }
}