package helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author prasad
 */
public class Mp3Player extends Thread
{
    Player P;
    File mp3;

    public Mp3Player(File f)
    {
        mp3 = f;
    }

    @Override
    public void run()
    {
        InputStream i = null;
        try
        {
            super.run();
            i = new FileInputStream(mp3);
            P = new Player(i);
            P.play();
        }
        catch(JavaLayerException ex)
        {
            Logger.getLogger(Mp3Player.class.getName()).log(Level.SEVERE,null,ex);
        }
        catch(FileNotFoundException ex)
        {
            Logger.getLogger(Mp3Player.class.getName()).log(Level.SEVERE,null,ex);
        }

    }

    public void finish()
    {
        P.close();
    }
}
