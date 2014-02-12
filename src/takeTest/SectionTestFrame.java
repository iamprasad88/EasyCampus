/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SectionTestFrame.java
 *
 * Created on May 8, 2009, 5:21:46 PM
 */
package takeTest;

import helper.Answer;
import helper.Question;
import helper.QuestionAnswer;
import helper.Section;
import helper.Test;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author pani
 */
public class SectionTestFrame extends javax.swing.JFrame
{
    Section SECTION;
    int CurrentQuestion = 0;
    int CurrentSection = 0;
    Test QUESTIONS;
    Test ANSWERS;
    ArrayList<JCheckBox> MCQOptions;
    private int QuestionType;
    Timer timer;
    Timer counter;
    int TimeSoFar = 0;
    MediaDialog Pics;
    ActionListener timerAction = new ActionListener()
    {
        public void actionPerformed(ActionEvent arg0)
        {
            TimeSoFar++;
            SectionTimeLabel.setText(TimeSoFar + " / " +
                    QUESTIONS.SectionList[CurrentSection].Time +
                    " Minutes");
            if(TimeSoFar == QUESTIONS.SectionList[CurrentSection].Time)
            {
                saveData();
                TimeSoFar = 0;
                CurrentSection++;
                BackButton.setEnabled(false);
                if(QUESTIONS.NoOfSections - CurrentSection == 0)
                {
                    finish();
                    timer.stop();
                    FinishButton.setEnabled(false);
                    ObjectOutputStream out = null;
                    try
                    {
                        String FileName =
                                ANSWERS.Name + ANSWERS.UserName + ANSWERS.USN +
                                ANSWERS.Class + ANSWERS.Section + ".rsp";

                        File file = new File(QUESTIONS.getRSP(),FileName);
                        out = new ObjectOutputStream(new FileOutputStream(file));
                        out.writeObject(ANSWERS);
                        out.flush();
                        pack();
                        repaint();
                        out.close();
                    }
                    catch(IOException ex)
                    {
                        Logger.getLogger(SectionTestFrame.class.getName()).
                                log(Level.SEVERE,null,ex);
                    }

//                    try
//                    {
//                        String FileName =
//                                QUESTIONS.Name + QUESTIONS.UserName +
//                                QUESTIONS.USN +
//                                QUESTIONS.Class + QUESTIONS.Section + ".rsp";
//
//                        File file = new File(QUESTIONS.getRSP(),FileName);
//                        out = new ObjectOutputStream(new FileOutputStream(file));
//                        //--------------------------------------------------
//                        for(int i = 0;i < ANSWERS.NoOfSections;i++)
//                        {
//                            if(QUESTIONS.SectionList[i].QAList.length !=
//                                    ANSWERS.SectionList[i].QAList.length)
//                            {
//
//                                double cutOff = QUESTIONS.SectionList[i].CutOff;
//                                int Time = QUESTIONS.SectionList[i].Time;
//                                String Name = QUESTIONS.SectionList[i].Name;
//                                ANSWERS.SectionList[i] = new Section(cutOff,
//                                        Time,Name,i,
//                                        Section.Answer);
//                                ANSWERS.SectionList[i].QAList =
//                                        new QuestionAnswer[QUESTIONS.SectionList[i].QAList.length];
//                                Answer List[] =
//                                        new Answer[QUESTIONS.SectionList[i].QAList.length];
//                                for(int j = 0;j <
//                                        ANSWERS.SectionList[i].QAList.length;
//                                        j++)
//                                {
//                                    List[i] =
//                                            (Answer) ANSWERS.SectionList[i].QAList[i];
//                                }
//                                for(int j =
//                                        ANSWERS.SectionList[i].QAList.length;
//                                        j < List.length;
//                                        j++)
//                                {
//                                    Question Q =
//                                            (Question) QUESTIONS.SectionList[i].QAList[j];
//                                    Answer A = new Answer(Q.Type,Q.Marks,
//                                            Q.NegMarks);
//                                    A.setBlankAnswer("");
//                                    A.setOptionAnswer(-1);
//                                    A.setEssayAnswer("");
//                                    List[i] = A;
//                                }
//
//                                ANSWERS.SectionList[i].QAList = List;
//                            }
//                        }
//
//                        //--------------------------------------------------
//                        out.writeObject(ANSWERS);
//                        out.flush();
//                        out.close();
//                        pack();
//                        repaint();
//                    }
//                    catch(IOException ex)
//                    {
//                        Logger.getLogger(SectionTestFrame.class.getName()).
//                                log(Level.SEVERE,null,ex);
//                    }
                    return;
                }
                CurrentQuestion = 0;
                loadData();
            }

            timer.restart();
        }
    };

    /** Creates new form SectionTestFrame */
    private SectionTestFrame()
    {
        initComponents();
        Pics = new MediaDialog(this);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public SectionTestFrame(Test TEST)
    {
        this();

        QUESTIONS = TEST;
        SECTION = QUESTIONS.SectionList[0];
        MCQOptions = new ArrayList<JCheckBox>(0);
        this.QuestionArea.setEditable(false);

        BackButton.setEnabled(false);
        if(QUESTIONS.SectionList[CurrentSection].QAList.length <= 1)
        {
            NextButton.setEnabled(false);

        }

        ANSWERS = new Test(QUESTIONS.Name,QUESTIONS.Course,QUESTIONS.SubName,
                QUESTIONS.SubCode,
                QUESTIONS.Instruction,QUESTIONS.DateOfTest,
                QUESTIONS.NoOfSections);
        ANSWERS.UserName = QUESTIONS.UserName;
        ANSWERS.USN = QUESTIONS.USN;
        ANSWERS.Class = QUESTIONS.Class;
        ANSWERS.Section = QUESTIONS.Section;
        for(int i = 0;i < ANSWERS.NoOfSections;i++)
        {
            double cutOff = QUESTIONS.SectionList[i].CutOff;
            int Time = QUESTIONS.SectionList[i].Time;
            String Name = QUESTIONS.SectionList[i].Name;
            ANSWERS.SectionList[i] = new Section(cutOff,Time,Name,i,
                    Section.Answer);
        }
        timer = new Timer(10000,timerAction);
        timer.start();
        loadData();
        this.setTitle(this.getTitle() + " - " + QUESTIONS.Name);

//        timer = new Timer();
//        timer.
//        timer.schedule(task,QUESTIONS.SectionList[CurrentSection].Time*10000);

    }

    private String checkAnswers()
    {
        String Err = "";
        for(int j = 0;j <
                ANSWERS.SectionList[CurrentSection - 1].QAList.length;
                j++)
        {
            Answer Ans =
                    (Answer) ANSWERS.SectionList[CurrentSection - 1].QAList[j];
            if(Ans.Type == Question.MultipleChoice)
            {
                int ans =
                        Ans.getOptionAnswer();
                if(ans == -1)
                {
                    Err += (j + 1) + "  ";
                }
            }
            else if(Ans.Type == Question.FillInTheBlanks)
            {
                if(Ans.getBlankAnswer().equals(""))
                {
                    Err += (j + 1) + "  ";

                }
            }
            else if(Ans.Type == Question.Essay)
            {
                if(Ans.getEssayAnswer().equals(""))
                {
                    Err += (j + 1) + "  ";
                }
            }
        }

        return Err;
    }

    private void clear()
    {
        QuestionArea.setText("");
        AnswerFieldPanel.removeAll();
        Marks.setText("");
        Nmarks.setText("");
        SectionNameLabel.setText("");
        SectionCuttOffLabel.setText("");
        SectionNoLabel.setText("");
        SectionTimeLabel.setText("");
        QNoLabel.setText("");
        Marks.setText("");
        Nmarks.setText("");
        pack();
        repaint();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MCQAnsPanel = new javax.swing.JPanel();
        MCQAnsGroup = new javax.swing.ButtonGroup();
        jScrollPane2 = new javax.swing.JScrollPane();
        EssayAns = new javax.swing.JTextArea();
        FITBAnsPanel = new javax.swing.JPanel();
        FITBAns = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        QuestionPanel = new javax.swing.JPanel();
        QNo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        QuestionArea = new javax.swing.JTextArea();
        Nmarks = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Marks = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        BackButton = new javax.swing.JButton();
        NextButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        AnswerFieldPanel = new javax.swing.JPanel();
        FinishButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        SectionNoLabel = new javax.swing.JLabel();
        SectionNameLabel = new javax.swing.JLabel();
        SectionTimeLabel = new javax.swing.JLabel();
        SectionCuttOffLabel = new javax.swing.JLabel();
        QNoLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        MCQAnsPanel.setLayout(new java.awt.GridLayout(0, 1));

        EssayAns.setColumns(20);
        EssayAns.setRows(5);
        jScrollPane2.setViewportView(EssayAns);

        FITBAns.setFont(new java.awt.Font("Dialog", 1, 12));
        FITBAns.setPreferredSize(new java.awt.Dimension(18, 325));

        jLabel9.setText("Please fill in the Blank below : ");

        javax.swing.GroupLayout FITBAnsPanelLayout = new javax.swing.GroupLayout(FITBAnsPanel);
        FITBAnsPanel.setLayout(FITBAnsPanelLayout);
        FITBAnsPanelLayout.setHorizontalGroup(
            FITBAnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FITBAnsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(FITBAnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(FITBAns, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE))
                .addContainerGap())
        );
        FITBAnsPanelLayout.setVerticalGroup(
            FITBAnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FITBAnsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FITBAns, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        FITBAnsPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {FITBAns, jLabel9});

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("EasyCampus Easy Test Maker - TEST");

        QuestionPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        QuestionArea.setColumns(20);
        QuestionArea.setRows(5);
        jScrollPane1.setViewportView(QuestionArea);

        jLabel2.setText("-ve Marks");

        jLabel1.setText("Marks");

        BackButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Icons/back.png"))); // NOI18N
        BackButton.setText("Back");
        BackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackButtonActionPerformed(evt);
            }
        });

        NextButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Icons/next.png"))); // NOI18N
        NextButton.setText("Next");
        NextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Answer :");

        AnswerFieldPanel.setLayout(new java.awt.BorderLayout());
        jScrollPane3.setViewportView(AnswerFieldPanel);

        javax.swing.GroupLayout QuestionPanelLayout = new javax.swing.GroupLayout(QuestionPanel);
        QuestionPanel.setLayout(QuestionPanelLayout);
        QuestionPanelLayout.setHorizontalGroup(
            QuestionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QuestionPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(QuestionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Nmarks, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(QuestionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2)
                        .addComponent(Marks, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(QuestionPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(QNo, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(525, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, QuestionPanelLayout.createSequentialGroup()
                .addContainerGap(508, Short.MAX_VALUE)
                .addComponent(BackButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NextButton)
                .addContainerGap())
            .addGroup(QuestionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(592, Short.MAX_VALUE))
            .addGroup(QuestionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE)
                .addContainerGap())
        );
        QuestionPanelLayout.setVerticalGroup(
            QuestionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QuestionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(QNo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(QuestionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(QuestionPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Marks, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Nmarks, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(QuestionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NextButton)
                    .addComponent(BackButton))
                .addContainerGap())
        );

        FinishButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Icons/accept.png"))); // NOI18N
        FinishButton.setText("Finish");
        FinishButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FinishButtonActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Icons/cancel.png"))); // NOI18N
        jButton2.setText("Quit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setText("Section No : ");

        jLabel5.setText("Section Name : ");

        jLabel6.setText("Time : ");

        jLabel7.setText("Section CutOff : ");

        jLabel8.setText("Question No : ");

        SectionTimeLabel.setForeground(new java.awt.Color(255, 51, 51));

        jButton1.setText("<html>  Click for Media</html>");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(QNoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(SectionCuttOffLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(SectionTimeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addComponent(SectionNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SectionNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(FinishButton))
                            .addComponent(QuestionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {FinishButton, jButton2});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel4, jLabel5, jLabel6, jLabel7, jLabel8});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {QNoLabel, SectionCuttOffLabel, SectionNameLabel, SectionNoLabel, SectionTimeLabel});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel8))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(SectionNoLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(SectionNameLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(SectionTimeLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(SectionCuttOffLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(QNoLabel)))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(QuestionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FinishButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {QNoLabel, SectionCuttOffLabel, SectionNameLabel, SectionNoLabel, SectionTimeLabel, jLabel4, jLabel5, jLabel6, jLabel7, jLabel8});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void FinishButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_FinishButtonActionPerformed
    {//GEN-HEADEREND:event_FinishButtonActionPerformed
        saveData();
        ObjectOutputStream out = null;
        try
        {
            String FileName =
                    ANSWERS.Name + ANSWERS.UserName + ANSWERS.USN +
                    ANSWERS.Class + ANSWERS.Section + ".rsp";

            File file = new File(QUESTIONS.getRSP(),FileName);
            out = new ObjectOutputStream(new FileOutputStream(file));
            //--------------------------------------------------------
//
//            for(int i = 0;i < ANSWERS.NoOfSections;i++)
//            {
//                if(QUESTIONS.SectionList[i].QAList.length !=
//                        ANSWERS.SectionList[i].QAList.length)
//                {
//
//                    double cutOff = QUESTIONS.SectionList[i].CutOff;
//                    int Time = QUESTIONS.SectionList[i].Time;
//                    String Name = QUESTIONS.SectionList[i].Name;
//                    ANSWERS.SectionList[i] = new Section(cutOff,Time,Name,i,
//                            Section.Answer);
//                    ANSWERS.SectionList[i].QAList =
//                            new QuestionAnswer[QUESTIONS.SectionList[i].QAList.length];
//                    Answer List[] = new Answer[QUESTIONS.SectionList[i].QAList.length];
//                    for(int j = 0;j < ANSWERS.SectionList[i].QAList.length;j++)
//                    {
//                        List[i] = (Answer) ANSWERS.SectionList[i].QAList[i];
//                    }
//                    for(int j = ANSWERS.SectionList[i].QAList.length;i < List.length;
//                            j++)
//                    {
//                        Question Q = (Question) QUESTIONS.SectionList[i].QAList[j];
//                        Answer A = new Answer(Q.Type,Q.Marks,Q.NegMarks);
//                        A.setBlankAnswer("");
//                        A.setOptionAnswer(-1);
//                        A.setEssayAnswer("");
//                        List[i] = A;
//                    }
//
//                    ANSWERS.SectionList[i].QAList = List;
//                }
//            }
            //--------------------------------------------------------

            out.writeObject(ANSWERS);
            out.flush();
            pack();
            repaint();
        }
        catch(IOException ex)
        {
            Logger.getLogger(SectionTestFrame.class.getName()).
                    log(Level.SEVERE,null,ex);
        }
        finally
        {
            try
            {
                out.close();
            }
            catch(IOException ex)
            {
                Logger.getLogger(SectionTestFrame.class.getName()).
                        log(Level.SEVERE,null,ex);
            }
        }
}//GEN-LAST:event_FinishButtonActionPerformed

    private void NextButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_NextButtonActionPerformed
    {//GEN-HEADEREND:event_NextButtonActionPerformed
        // TODO add your handling code here:
//        if(QUESTIONS.SectionList[CurrentSection].QAList[CurrentQuestion].type)

        Question Ques =
                ((Question) QUESTIONS.SectionList[CurrentSection].QAList[CurrentQuestion]);
        QuestionType = Ques.Type;
        BackButton.setEnabled(true);
        saveData();

        CurrentQuestion++;

        if(CurrentQuestion ==
                QUESTIONS.SectionList[CurrentSection].QAList.length)
        {
            CurrentSection++;
            if(checkAnswers().equals(""))
            {

                if(JOptionPane.showConfirmDialog(this,"You are about enter Section " +
                        (CurrentSection + 1) + " .Are you sure?","Warning!!!",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE) ==
                        JOptionPane.CANCEL_OPTION)
                {
                    CurrentSection--;
                    CurrentQuestion =
                            QUESTIONS.SectionList[CurrentSection].QAList.length -
                            1;

                    return;
                }
                TimeSoFar = 0;
                timer.restart();
            }
            else
            {
                if(JOptionPane.showConfirmDialog(this,"You are about enter Section " +
                        (CurrentSection + 1) + " Without answering  Questions" +
                        checkAnswers() + ".Are you sure?","Warning!!!",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE) ==
                        JOptionPane.CANCEL_OPTION)
                {
                    CurrentSection--;
                    CurrentQuestion =
                            QUESTIONS.SectionList[CurrentSection].QAList.length -
                            1;
                    return;
                }
                TimeSoFar = 0;
                timer.restart();
            }
            BackButton.setEnabled(false);
            CurrentQuestion = 0;
            if(QUESTIONS.SectionList[CurrentSection].QAList.length == 0)
            {
                clear();
                JOptionPane.showMessageDialog(this,
                        "Section " + (CurrentSection + 1) + " is Empty",
                        "Empty Section",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }

        loadData();
        if(QUESTIONS.NoOfSections - CurrentSection == 1 && CurrentQuestion ==
                QUESTIONS.SectionList[CurrentSection].QAList.length - 1)
        {
            NextButton.setEnabled(false);
            repaint();
            return;
        }

    }//GEN-LAST:event_NextButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton2ActionPerformed
    {//GEN-HEADEREND:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void BackButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_BackButtonActionPerformed
    {//GEN-HEADEREND:event_BackButtonActionPerformed
        // TODO add your handling code here:
        saveData();
        CurrentQuestion--;
        loadData();
        if(CurrentQuestion == 0)
        {
            BackButton.setEnabled(false);
        }
        NextButton.setEnabled(true);
    }//GEN-LAST:event_BackButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        // TODO add your handling code here:
//        Pics.setQUESTION((Question) QUESTIONS.SectionList[CurrentSection].QAList[CurrentQuestion]);
        Pics.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    private static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new SectionTestFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AnswerFieldPanel;
    private javax.swing.JButton BackButton;
    private javax.swing.JTextArea EssayAns;
    private javax.swing.JTextField FITBAns;
    private javax.swing.JPanel FITBAnsPanel;
    private javax.swing.JButton FinishButton;
    private javax.swing.ButtonGroup MCQAnsGroup;
    private javax.swing.JPanel MCQAnsPanel;
    private javax.swing.JLabel Marks;
    private javax.swing.JButton NextButton;
    private javax.swing.JLabel Nmarks;
    private javax.swing.JLabel QNo;
    private javax.swing.JLabel QNoLabel;
    private javax.swing.JTextArea QuestionArea;
    private javax.swing.JPanel QuestionPanel;
    private javax.swing.JLabel SectionCuttOffLabel;
    private javax.swing.JLabel SectionNameLabel;
    private javax.swing.JLabel SectionNoLabel;
    private javax.swing.JLabel SectionTimeLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables

    void loadData()
    {

        SectionNoLabel.setText(Integer.toString(CurrentSection + 1));
        SectionNameLabel.setText(QUESTIONS.SectionList[CurrentSection].Name);
        SectionCuttOffLabel.setText(Double.toString(
                QUESTIONS.SectionList[CurrentSection].CutOff));
        SectionTimeLabel.setText(TimeSoFar + " / " +
                QUESTIONS.SectionList[CurrentSection].Time +
                " Minutes");
        QNoLabel.setText(Integer.toString(CurrentQuestion + 1));
        QuestionArea.setText(
                ((Question) QUESTIONS.SectionList[CurrentSection].QAList[CurrentQuestion]).QuestionString);
        Pics.setQUESTION(
                (Question) QUESTIONS.SectionList[CurrentSection].QAList[CurrentQuestion]);
        Question Ques =
                ((Question) QUESTIONS.SectionList[CurrentSection].QAList[CurrentQuestion]);
        Marks.setText(Double.toString(Ques.Marks));
        Nmarks.setText(Double.toString(Ques.NegMarks));
        QuestionType = Ques.Type;
        if(Ques.Type == Question.MultipleChoice)
        {
            loadMCQ(Ques);
        }
        else if(Ques.Type == Question.FillInTheBlanks)
        {
            loadFITB(Ques);
        }
        else if(Ques.Type == Question.Essay)
        {
            loadEssay(Ques);
        }
        pack();
        repaint();
        if(Ques.Audio.length != 0 || Ques.Images.length != 0)
        {
            Pics.setQUESTION(Ques);
            Pics.setVisible(true);
        }
    }

    private void loadEssay(Question Ques)
    {
        if(Ques.Type != Question.Essay)
        {
            return;
        }
        AnswerFieldPanel.removeAll();
        EssayAns.setText("");
        AnswerFieldPanel.add(EssayAns);
        if(ANSWERS.SectionList[CurrentSection].QAList.length - 1 >=
                CurrentQuestion)
        {
            String ans =
                    ((Answer) ANSWERS.SectionList[CurrentSection].QAList[CurrentQuestion]).
                    getEssayAnswer();
            EssayAns.setText(ans);
        }
        EssayAns.requestFocusInWindow();
    }

    private void loadFITB(Question Ques)
    {
        if(Ques.Type != Question.FillInTheBlanks)
        {
            return;
        }

        AnswerFieldPanel.removeAll();
        FITBAns.setText("");
        AnswerFieldPanel.add(FITBAnsPanel);
        if(ANSWERS.SectionList[CurrentSection].QAList.length - 1 >=
                CurrentQuestion)
        {
            String ans =
                    ((Answer) ANSWERS.SectionList[CurrentSection].QAList[CurrentQuestion]).
                    getBlankAnswer();
            FITBAns.setText(ans);
        }
        FITBAns.requestFocusInWindow();

    }

    private void loadMCQ(Question Ques)
    {
        if(Ques.Type != Question.MultipleChoice)
        {
            return;
        }

        MCQOptions.clear();
        MCQAnsPanel.removeAll();

        for(int i = 0;i < Ques.MCQOptions.length;i++)
        {
            JCheckBox temp = new JCheckBox(Ques.MCQOptions[i]);
            MCQOptions.add(temp);
            MCQAnsGroup.add(temp);
            MCQAnsPanel.add(temp);
        }
        AnswerFieldPanel.removeAll();
        AnswerFieldPanel.add(MCQAnsPanel);

        if(ANSWERS.SectionList[CurrentSection].QAList.length - 1 >=
                CurrentQuestion)
        {
            int ans =
                    ((Answer) ANSWERS.SectionList[CurrentSection].QAList[CurrentQuestion]).
                    getOptionAnswer();
            if(ans != -1)
            {
                MCQOptions.get(ans).setSelected(true);
            }
            else
            {
                MCQOptions.clear();
            }
        }
        MCQAnsPanel.requestFocusInWindow();
    }

    private boolean saveData()
    {
        if(QuestionType == Question.MultipleChoice)
        {
            int ans = -1;
            for(int i = 0;i < MCQOptions.size();i++)
            {
                if(MCQOptions.get(i).isSelected())
                {
                    ans = i;
                    break;
                }
            }


            double marks =
                    ((Question) QUESTIONS.SectionList[CurrentSection].QAList[CurrentQuestion]).Marks;
            double negMarks =
                    ((Question) QUESTIONS.SectionList[CurrentSection].QAList[CurrentQuestion]).NegMarks;
            Answer ANS = new Answer(QuestionType,marks,negMarks);
            ANS.setOptionAnswer(ans);
            if(CurrentQuestion ==
                    ANSWERS.SectionList[CurrentSection].QAList.length)
            {
                ANSWERS.SectionList[CurrentSection].add(ANS);
            }
            else
            {
                ANSWERS.SectionList[CurrentSection].edit(CurrentQuestion,
                        ANS);

                return true;
            }
        }
        else if(QuestionType == Question.FillInTheBlanks)
        {
            double marks =
                    ((Question) QUESTIONS.SectionList[CurrentSection].QAList[CurrentQuestion]).Marks;
            double negMarks =
                    ((Question) QUESTIONS.SectionList[CurrentSection].QAList[CurrentQuestion]).NegMarks;
            Answer ANS = new Answer(QuestionType,marks,negMarks);
            ANS.setBlankAnswer(FITBAns.getText());
            if(CurrentQuestion ==
                    ANSWERS.SectionList[CurrentSection].QAList.length)
            {
                ANSWERS.SectionList[CurrentSection].add(ANS);
            }
            else
            {
                ANSWERS.SectionList[CurrentSection].edit(CurrentQuestion,ANS);
            }
            return true;
        }
        else if(QuestionType == Question.Essay)
        {
            double marks =
                    ((Question) QUESTIONS.SectionList[CurrentSection].QAList[CurrentQuestion]).Marks;
            double negMarks =
                    ((Question) QUESTIONS.SectionList[CurrentSection].QAList[CurrentQuestion]).NegMarks;
            Answer ANS = new Answer(QuestionType,marks,negMarks);
            ANS.setEssayAnswer(EssayAns.getText());
            if(CurrentQuestion ==
                    ANSWERS.SectionList[CurrentSection].QAList.length)
            {
                ANSWERS.SectionList[CurrentSection].add(ANS);
            }
            else
            {
                ANSWERS.SectionList[CurrentSection].edit(CurrentQuestion,ANS);
            }
            return true;
        }
        return false;
    }

    private void finish()
    {
        NextButton.setEnabled(false);
        clear();
        Pics.finish();
        JOptionPane.showMessageDialog(null,QUESTIONS.getFinishMsg(),
                "Finished test",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
