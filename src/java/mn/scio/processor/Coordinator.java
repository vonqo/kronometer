package mn.scio.processor;

import org.opencv.core.Core;
import org.opencv.highgui.Highgui;

/**
 *
 * @author lupino
 */
public class Coordinator {
    
    private static String fileName = "";
    //static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    public static void main(String[] args) throws Exception {
        // Load the native library.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String path = "src/mn/scio/resource/";
        
        HyperTextBuilder h = new HyperTextBuilder("test");
        h.initTemplate();
        h.finalizeTemplate();
        SketchRecognation a = new SketchRecognation(Highgui.imread(path + fileName));
        a.findRectangle();
    }
}