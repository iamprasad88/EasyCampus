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
public class Section implements Serializable
{

    public static final int Question = 0;
    public static final int Answer = 1;
    public double CutOff;
    public int Time;
    public QuestionAnswer QAList[] = null;
    //While taking testcast QAList to Question[] before use
    public String Name;
    public int SNo;

    public Section(double CutOff,int Time,String Name,
            int SNo,int Type)
    {
        this.CutOff = CutOff;
        this.Time = Time;
        this.Name = Name;
        this.SNo = SNo;
        if(Type == Question)
        {
            QAList = new Question[0];
        }
        else
        {
            QAList = new Answer[0];
        }
    }

    public void add(Question Ques)
    {
        if(QAList instanceof Question[])
        {
            Question List[] = new Question[QAList.length + 1];
            for(int i = 0;i < QAList.length;i++)
            {
                List[i] = (Question) QAList[i];
            }
            List[QAList.length] = Ques;

            QAList = List;
        }
    }

    public void edit(int Qno,Question Ques)
    {
        if(QAList instanceof Question[])
        {
            QAList[Qno] = Ques;
        }
    }

    public void edit(int Qno,Answer Ans)
    {
        if(QAList instanceof Answer[])
        {
            QAList[Qno] = Ans;
        }
    }

    public void add(Answer Ans)
    {
        if(QAList instanceof Answer[])
        {
            Answer List[] = new Answer[QAList.length + 1];
            for(int i = 0;i < QAList.length;i++)
            {
                List[i] = (Answer) QAList[i];
            }
            List[QAList.length] = Ans;

            QAList = List;
        }
    }

    public void remove(int Qno)
    {
        if(Qno < QAList.length && Qno >= 0 && QAList instanceof Question[])
        {
            Question t[] = new Question[QAList.length-1];
            for(int i=0;i<Qno;i++)
            {
                t[i] = (Question) QAList[i];
            }
            for(int i=Qno;i<t.length;i++)
            {
                t[i] = (Question) QAList[i+1];
            }

            QAList = t;
        }
    }
}
