package mn.scio.processor;


import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import mn.scio.renders.ImageDetection;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import static org.opencv.highgui.Highgui.imread;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import static org.opencv.highgui.Highgui.imread;
import static org.opencv.highgui.Highgui.imread;
import static org.opencv.highgui.Highgui.imread;

/**
 *
 * @author lupino
 * Core sketch recognition process
 */
public class SketchRecognition {
    
    private Mat image;
    private Mat originalImage;
    private List<MatOfPoint> contours;
    private Mat hierarchy;
    
    public SketchRecognition(Mat input){
        this.originalImage = input;
        this.image = new Mat();
        this.hierarchy = new Mat();
        this.contours = new ArrayList<MatOfPoint>();
    }
    
    private void setFilter(){
        //Apply gaussian blur to remove noise
        Imgproc.GaussianBlur(image, image, new Size(5, 5), 0);
        
        //Threshold
        //Imgproc.adaptiveThreshold(image, image, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 3, 1);
        
        //Invert the image
        Core.bitwise_not(image, image);
        
        //Dilate
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_DILATE, new Size(3, 3), new Point(1, 1));
        Imgproc.dilate(image, image, kernel);
    }
    
    public void findRectangle() {
        Imgproc.cvtColor(originalImage, image, Imgproc.COLOR_BGR2GRAY);
        setFilter();

        //Find Contours
        Imgproc.findContours(image, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));
        
        //For conversion later on
        MatOfPoint2f approxCurve = new MatOfPoint2f();

        //For each contour found
        for (int i = 0; i < contours.size(); i++) {

            //Convert contours from MatOfPoint to MatOfPoint2f
            MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(i).toArray());
            //Processing on mMOP2f1 which is in type MatOfPoint2f
            double approxDistance = Imgproc.arcLength(contour2f, true) * 0.02;

            if (approxDistance > 1) {
                //Find Polygons
                Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);

                //Convert back to MatOfPoint
                MatOfPoint points = new MatOfPoint(approxCurve.toArray());

                //Rectangle Checks - Points, area, convexity
                if (points.total() == 4 && Math.abs(Imgproc.contourArea(points)) > 1000 && Imgproc.isContourConvex(points)) {
                    double cos = 0;
                    double mcos = 0;
                    for (int sc = 2; sc < 5; sc++) {
                        // TO-DO Figure a way to check angle
                        if (cos > mcos) { mcos = cos; }
                    }
                    if (mcos < 0.3) {
                        // Get bounding rect of contour
                        Rect rect = Imgproc.boundingRect(points);
                        
                        if (Math.abs(rect.height - rect.width) < 50000) {
                            System.out.println(i+" x: "+rect.x+", y: "+rect.y);
                            
                            Core.rectangle(originalImage, rect.tl(), rect.br(), new Scalar(20, 20, 20), -1, 4, 0);
                            Imgproc.drawContours(originalImage, contours, i, new Scalar(0, 255, 0, .8), 2);
                            
                            Utility util = new Utility();
                            ImageDetection id = new ImageDetection();
                            id.setData(util.matToBufferedImage(originalImage));
                            Highgui.imwrite("detected_layers.png", originalImage);
                        }
                    }
                }
            }
        }
    }
    
    public Mat getMat(){
        return this.originalImage;
    }
}