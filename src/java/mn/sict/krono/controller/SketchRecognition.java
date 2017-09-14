package mn.sict.krono.controller;

import java.util.ArrayList;
import java.util.List;
import mn.sict.krono.renders.ImageDetection;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author lupino Core sketch recognition process
 */
public class SketchRecognition {

    private Mat image;
    private Mat originalImage;
    private List<MatOfPoint> contours;
    private Mat hierarchy;
    private int HEIGHT;
    private int WIDTH;
    private List<Rect> rects = new ArrayList<Rect>();

    public SketchRecognition(Mat input) {
        this.originalImage = input;
        this.image = new Mat();
        this.hierarchy = new Mat();
        this.contours = new ArrayList<MatOfPoint>();
        this.HEIGHT = input.height();
        this.WIDTH = input.width();
    }

    private void setFilter() {
        //Apply gaussian blur to remove noise
        Imgproc.GaussianBlur(image, image, new Size(5, 5), 0);

        //Threshold
        Imgproc.adaptiveThreshold(image, image, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 7, 1);
        
        //Invert the image
        Core.bitwise_not(image, image);

        //Dilate
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_DILATE, new Size(3, 3), new Point(1, 1));
        Imgproc.dilate(image, image, kernel);
    }

    public void findRectangle() {
        Imgproc.cvtColor(originalImage, image, Imgproc.COLOR_BGR2GRAY);
        setFilter();
        this.rects.clear();

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
                        if (cos > mcos) {
                            mcos = cos;
                        }
                    }
                    if (mcos < 0.3) {
                        // Get bounding rect of contour
                        Rect rect = Imgproc.boundingRect(points);

//                        if (Math.abs(rect.height - rect.width) < 1000) {
                            System.out.println(i + "| x: " + rect.x + " + width("+rect.width+"), y: " + rect.y + "+ width("+rect.height+")");
                            rects.add(rect);
                            Core.rectangle(originalImage, rect.tl(), rect.br(), new Scalar(20, 20, 20), -1, 4, 0);
                            Imgproc.drawContours(originalImage, contours, i, new Scalar(0, 255, 0, .8), 2);
                            
                            // Highgui.imwrite("detected_layers"+i+".png", originalImage);
//                        }
                    }
                }
            }
        }
        // Pass raw parameters
        ImageDetection id = new ImageDetection();
        HyperTextBuilder.rects = this.rects;
        HyperTextBuilder.rect_height = this.HEIGHT;
        HyperTextBuilder.rect_width = this.WIDTH;
        id.setData(Utility.matToBufferedImage(originalImage));
    }

    public Mat getMat() {
        return this.originalImage;
    }
}
