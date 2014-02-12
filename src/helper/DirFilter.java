/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;
import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/**
 *
 * @author prasad
 */
public class DirFilter implements FilenameFilter
{
    private Pattern pattern;

    public DirFilter(String regex)
    {
        pattern = Pattern.compile(regex);
    }

    public boolean accept(File directorypath,String name)
    {
        return pattern.matcher(new File(name).getName()).matches();
    }
}
