<%-- 
    Document   : init
    Created on : Feb 14, 2017, 5:47:28 PM
    Author     : lupino
--%>

<%@page import="java.awt.image.DataBufferByte"%>
<%@page import="org.opencv.core.Mat"%>
<%@page import="org.opencv.core.CvType"%>
<%@page import="mn.scio.processor.Utility"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="javafx.scene.image.Image"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="org.opencv.highgui.Highgui"%>
<%@page import="mn.scio.processor.SketchRecognation"%>
<%@page import="org.opencv.core.Core"%>
<%@page import="java.net.URL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Khronometer</title>
    </head>
    <body>
        <h1>Alpha test phase</h1>
        <% 
//            URL url = getClass().getResource("/mn/scio/resource/hand2.jpg");
//            String dnk = "/home/lupino/GlassFish_Server/glassfish/domains/domain1/config/dankla.png";
//            StringBuilder path = new StringBuilder(url.toString());
//            path = path.delete(0, 5);
            //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            
            URL url = new URL("http://localhost:8080/khronometer/imageOriginal");
            BufferedImage img = ImageIO.read(url);
            Utility openUtil = new Utility();
            
            Mat mat = new Mat(img.getHeight(), img.getWidth(), CvType.CV_8UC3);
            byte[] data = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
            mat.put(0, 0, data);
            
            SketchRecognation a = new SketchRecognation(mat);
            a.findRectangle();
            
        %>
            <br>Original img<br>
            <img src="imageOriginal" width="500">
            <br>Detection img<br>
            <img src="imageDetection" width="500">
    </body>
</html>
