<%-- 
    Document   : init
    Created on : Feb 14, 2017, 5:47:28 PM
    Author     : lupino
--%>

<%@page import="mn.scio.processor.HyperTextBuilder"%>
<%@page import="java.awt.image.DataBufferByte"%>
<%@page import="org.opencv.core.Mat"%>
<%@page import="org.opencv.core.CvType"%>
<%@page import="mn.scio.processor.Utility"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="javafx.scene.image.Image"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="org.opencv.highgui.Highgui"%>
<%@page import="mn.scio.processor.SketchRecognition"%>
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
            Utility util = new Utility();
            
            String imgurl = "glasses";
            URL url = new URL("http://localhost:8080/khronometer/imageOriginal?img="+imgurl);
            BufferedImage img = null;
            img = ImageIO.read(url);
            
            byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
            
            Mat mat = new Mat(img.getHeight(), img.getWidth(), CvType.CV_8UC3);
            mat.put(0,0,pixels);
            SketchRecognition a = new SketchRecognition(mat);
            a.findRectangle();
            HyperTextBuilder builder = new HyperTextBuilder("alphatest");
            builder.initTemplate();
            builder.finalizeTemplate();
        %>
            <br>Original img<br>
            <img src="imageOriginal?img=<%=imgurl%>" width="500">
            <br>Detection img<br>
            <img src="imageDetection" width="500">
            <button id="download">Download Template</button>
            <script>
                var btn = document.getElementById("download");
                btn.onclick = function(){
                    location.href = '/khronometer/DownloadTemplate';
                }
            </script>
    </body>
</html>
