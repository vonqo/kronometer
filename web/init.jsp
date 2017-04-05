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
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <!-- Bootstrap Core CSS -->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom Fonts -->
        <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

        <!-- Theme CSS -->
        <link href="css/grayscale.min.css" rel="stylesheet">
        <link rel="icon" href="img/favicon.png" type="image/x-icon" />

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->

        <style type="text/css">
            #demo-bar{
                font-size: 18px;
            }
        </style>
    </head>
    <body>
        <%
            Utility util = new Utility();
            String imgurl = request.getParameter("req");
            URL url = new URL("http://localhost:8080/khronometer/imageOriginal?img=" + imgurl);
            BufferedImage img = null;
            img = ImageIO.read(url);

            byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();

            Mat mat = new Mat(img.getHeight(), img.getWidth(), CvType.CV_8UC3);
            mat.put(0, 0, pixels);
            SketchRecognition a = new SketchRecognition(mat);
            a.findRectangle();
            HyperTextBuilder builder = new HyperTextBuilder(imgurl);
            builder.initTemplate();
            builder.finalizeTemplate();
        %>
        <!-- Intro Header -->
        <header class="intro">
            <div class="intro-body">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <div style="padding-bottom: 500px;">
                                <div class="col-sm-6">
                                    <img src="imageOriginal?img=<%=imgurl%>" width="400">
                                </div>
                                <div class="col-sm-6">
                                    <img src="imageDetection" width="400">
                                </div>
                            </div>
                        </div>  
                        <div id="demo-bar">
                            <button type="button" id="download" style="" class="btn btn-default btn-lg">Загвар татах</button>
                            <button type="button" id="goback" style="" class="btn btn-default btn-lg">Буцах</button>
                        </div>
                        <p class="intro-text"><br><br>АЛЬФА ТЕСТ</p>
                    </div>
                </div>
            </div>
        </header>
        <script>
            var btn = document.getElementById("download");
            var btn2 = document.getElementById("goback");
            btn.onclick = function () {
                location.href = '/khronometer/DownloadTemplate';
            };
            btn2.onclick = function () {
                location.href = '/khronometer';
            };
        </script>
    </body>
</html>
