package mn.scio.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

/**
 *
 * @author lupino
 */
public class Coordinator {
    
    private static String fileName = "hand2.jpg";
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    
    public static void Start(String path) throws Exception {
        // Load the native library.
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println();
        SketchRecognation a = new SketchRecognation(Highgui.imread(path));
        a.findRectangle();
    }
}