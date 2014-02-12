/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 *
 * @author prasad
 */
public class QuestionImage implements Serializable
{

    private static final long serialVersionUID = 9209240593753698654L;
    ImageIcon Image;
    String Name;

    public QuestionImage(ImageIcon Image,String Name)
    {
        this.Image = Image;
        this.Name = Name;
    }

    public ImageIcon getImage()
    {
        return Image;
    }

    public String getName()
    {
        return Name;
    }

    public void setImage(ImageIcon Image)
    {
        this.Image = Image;
    }

    public void setName(String Name)
    {
        this.Name = Name;
    }
}
