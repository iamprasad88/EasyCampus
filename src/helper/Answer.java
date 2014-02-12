/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.io.Serializable;

/**
 *
 * @author prasad
 */
public class Answer implements QuestionAnswer, Serializable
{

    public final int Type;
    public final double Marks;
    public final double NegMarks;
    private String BlankAnswer;
    private int OptionAnswer;
    private String EssayAnswer;

    public Answer(int Type, double Marks, double NegMarks)
    {
        this.Type = Type;
        this.Marks = Marks;
        this.NegMarks = NegMarks;
    }

    public void setBlankAnswer(String BlankAnswer)
    {
        this.BlankAnswer = BlankAnswer;
    }

    public void setOptionAnswer(int OptionAnswer)
    {
        this.OptionAnswer = OptionAnswer;
    }

    public void setEssayAnswer(String EssayAnswer)
    {
        this.EssayAnswer = EssayAnswer;
    }

    public String getBlankAnswer()
    {
        return BlankAnswer;
    }

    public String getEssayAnswer()
    {
        return EssayAnswer;
    }

    public int getOptionAnswer()
    {
        return OptionAnswer;
    }
}
