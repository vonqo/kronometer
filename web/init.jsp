<%-- 
    Document   : init
    Created on : Feb 14, 2017, 5:47:28 PM
    Author     : lupino
--%>

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
        loading: 
        <% 
            URL url = getClass().getResource("/mn/scio/resource/hand2.jpg");
            String dnk = "/home/lupino/GlassFish_Server/glassfish/domains/domain1/config/dankla.png";
            StringBuilder path = new StringBuilder(url.toString());
            path = path.delete(0, 5);
            mn.scio.processor.Coordinator.Start(path.toString()); %>
            <%=path.toString()%>
            <br>Original img<br>
            <img src="<%=path.toString()%>" width="500">
            <br>Detection img<br>
            <img src="<%=dnk%>" width="500">
    </body>
</html>
