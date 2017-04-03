<%-- 
    Document   : index
    Created on : Apr 3, 2017, 12:56:19 PM
    Author     : lupino
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Kronometer</title>

        <!-- Bootstrap Core CSS -->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom Fonts -->
        <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

        <!-- Theme CSS -->
        <link href="css/grayscale.min.css" rel="stylesheet">

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

    <body id="page-top" data-spy="scroll" data-target=".navbar-fixed-top">

        <!-- Navigation -->
        <nav class="navbar navbar-custom navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-main-collapse">
                        Цэс <i class="fa fa-bars"></i>
                    </button>
                    <!--                    <a class="navbar-brand page-scroll" href="#page-top">
                                            <i class="fa fa-play-circle"></i> <span class="light">Start</span> Bootstrap
                                        </a>-->
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse navbar-right navbar-main-collapse">
                    <ul class="nav navbar-nav">
                        <!-- Hidden li included to remove active class from about link when scrolled up past about section -->
                        <li class="hidden">
                            <a href="#page-top"></a>
                        </li>
                        <li>
                            <a class="page-scroll" href="#about">Хэрхэн ажилладаг вэ</a>
                        </li>
                        <li>
                            <a class="page-scroll" href="#download">Татах</a>
                        </li>
                    </ul>
                </div>
                <!-- /.navbar-collapse -->
            </div>
            <!-- /.container -->
        </nav>

        <!-- Intro Header -->
        <header class="intro">
            <div class="intro-body">
                <div class="container">
                    <div class="row">
                        <div class="col-md-8 col-md-offset-2">
                            <div>
                                <img src="img/logo_medium.png" alt=""/>
                            </div>
                            <div id="demo-bar" style="">
                                <form class="form-horizontal">
                                    <div class="form-group">
                                        <label class="control-label col-sm-3" for="demo_name">Загвар нэр:</label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control" id="demo_name" placeholder="">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-sm-3" for="demo_img">Гар зураг:</label>
                                        <div class="col-sm-9">
                                            <input type="file" class="" id="demo_img">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div style="padding-top: 50px">
                                            <button type="submit" class="btn btn-lg">Загвар Урлах</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <p class="intro-text">Цэцэрлэгийн хүүхэд ч веб урлах боломжтой ...
                                <br></p>
                            <a href="#about" class="btn btn-circle page-scroll">
                                <i class="fa fa-angle-double-down animated"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </header>

        <!-- About Section -->
        <section id="about" class="container content-section text-center">
            <div class="row">
                <div class="col-lg-8 col-lg-offset-2">
                    <h2>Хэрхэн ажилладаг вэ</h2>
                    <p>text here</p>
                    <img src="img/workflow.png" alt=""/>
                    <img src="img/architecture.png" alt=""/>
                </div>
            </div>
        </section>

        <!-- Download Section -->
        <section id="download" class="content-section text-center">
            <div class="download-section">
                <div class="container">
                    <div class="col-lg-8 col-lg-offset-2">
                        <h2>Бахархалтайгаар Нээлттэй-Эх байршуулж байна</h2>
                        <p>Хамтдаа хөгжие! Хүсвэл та ч хөгжүүлэлтэнд оролцож боломжтой. Мөн өөрийнхөөрөө эх кодыг өөрчлөх боломж нээлттэй :D</p>
                        <ul class="list-inline banner-social-buttons">
                            <li>
                                <a href="https://github.com/IronSummitMedia/startbootstrap" class="btn btn-default btn-lg"><i class="fa fa-github fa-fw"></i> <span class="network-name">Github</span></a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </section>

        <!-- Contact Section -->
        <section id="contact" class="container content-section text-center">
            <div class="row">
                <div class="col-lg-8 col-lg-offset-2">
                    <h2>Холбогдох</h2>
                    <p></p>
                    <p><a href="mailto:lupino.liberty@gmail.com">lupino.liberty@gmail.com</a>
                    </p>
                </div>
            </div>
        </section>
        <!-- Footer -->
        <footer>
            <div class="container text-center">
                <p>Альфа тест | Хөгжүүлсэн Г.Энх-Амар</p>
            </div>
        </footer>

        <!-- jQuery -->
        <script src="vendor/jquery/jquery.js"></script>

        <!-- Bootstrap Core JavaScript -->
        <script src="vendor/bootstrap/js/bootstrap.min.js"></script>

        <!-- Plugin JavaScript -->
        <script src="js/jquery.easing.min.js"></script>

        <!-- Theme JavaScript -->
        <script src="js/grayscale.min.js"></script>

    </body>

</html>

