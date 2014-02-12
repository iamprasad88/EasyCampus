/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package createTest;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author prasad
 */
public class GUISection
{
    JSpinner CutOff;
    JSpinner Time;
    JTextField Name;
    JLabel SNo;

    public GUISection(int SNO)
    {
        this.CutOff = new JSpinner(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.
                valueOf(0.0d), null, Double.valueOf(1.0d)));
        this.CutOff.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        this.CutOff.setToolTipText("Cutoff Marks");
        this.CutOff.setFont(new java.awt.Font("Dialog", 0, 12));

        this.Time = new JSpinner(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.
                valueOf(1), null, Integer.valueOf(1)));
        this.Time.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        this.Time.setToolTipText("Time in minutes");
        this.Time.setFont(new java.awt.Font("Dialog", 0, 12));

        this.Name = new JTextField("");
        this.Name.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        this.Name.setBackground(Color.WHITE);
        this.Name.setFont(new java.awt.Font("Dialog", 0, 12));

        this.SNo = new JLabel("Section " + Integer.toString(SNO));
        this.SNo.setHorizontalAlignment(JLabel.CENTER);
        this.SNo.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        this.SNo.setToolTipText("Section Number "+SNo);
        this.SNo.setFont(new java.awt.Font("Dialog", 0, 12));
    }
}
