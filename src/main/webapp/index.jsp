<%-- 
    Document   : index
    Created on : Feb 22, 2019, 10:04:57 AM
    Author     : Ghaemi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">

        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

        <!-- Latest compiled JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

    </head>
    <body>
        <div class="container" style="margin-bottom: 15px; margin-top: 20px;">
            <form style="width: 70%;min-width: 70%;margin: auto;" class="form-inline" method="get"  action="Crawler">
                <div  class="form-group">
                    <label for="tid">Tweeter ID:</label>
                    <input value="${param.tid}" type="text" class="form-control" placeholder="realDonaldTrump" name="tid"  id="tid">
                </div>
                <div style="margin-left: 20px;" class="form-group">
                    <label for="tcount">Number Of Tweets:</label>
                    <input value="${param.tcount}" type="number" class="form-control" placeholder="25" name="tcount" id="tcount">
                </div>
                <hr/>
                <div class="form-group">
                    <label for="keyw">Keyword:</label>
                    <input value="${param.keyw}" type="text" class="form-control" placeholder="trump" name="keyw" id="keyw">
                </div>
                <div style="margin-left: 20px;" class="form-group">
                    <label for="hlines">Number Of Headlines:</label>
                    <input value="${param.hlines}" type="number" class="form-control" placeholder="25" name="hlines" id="hlines">
                </div>
                <button type="submit" class="btn btn-default">Retrieve</button>
            </form>
        </div>
        <div class="container" style="border-top: 1px solid black;">
            <div class="row">
                <div class="col-sm-6" style="min-height: 200px;border-right: 1px solid black;">
                    <h3> CNN Headlines</h3>
                    <hr/>
                    <c:forEach items="${news}" var="cnews" varStatus="cnewss">
                        <div class="panel panel-default" onclick="showNews('${cnews.addr}', '${cnews.imgAddr}')" style="cursor: pointer;"> 
                            <div class="panel-body"><span style="margin-right: 10px;" class="badge">${cnewss.index+1}</span>

                                <span style="color:  black;font-weight: bold;">${cnews.imgTitle}</span></div>
                        </div>
                    </c:forEach>
                </div>
                <div class="col-sm-6" style="min-height: 200px;border-left: 1px solid black;">
                    <h3> Latest Tweets</h3>
                    <hr/>
                    <c:forEach items="${tweets}" var="tw" varStatus="ts">
                        <div class="panel panel-default">
                            <div class="panel-heading"><span class="badge">${ts.index+1}</span> ${tName} says:</div>
                            <div class="panel-body">${tw}</div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>


        <div class="loader-i" style="display: none;width:300px;height: 200px;position: fixed;top: 50%;left: 50%;text-align:center;z-index:2;overflow: auto;background-color: white;opacity: .9;    margin-left: -150px;margin-top: -100px;">
            <center>
                <img style="z-index: 100;" src="https://www.acrpnet.org/wp-content/themes/acrp/assets/img/loading2.gif" alt="loading..">
            </center>
        </div>     



        <div id="moddal" >
            <button style="visibility: hidden;" id="shMod" type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal">Open Modal</button>

            <!-- Modal -->
            <div id="myModal" class="modal fade" role="dialog">
                <div class="modal-dialog">

                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Modal Header</h4>
                        </div>
                        <div class="modal-body" style="font-weight: bold">

                            <img id="modal-body-img" class="img-rounded" src="" width="100%" style="margin-bottom: 20px;">
                            <div class="mm-body">

                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div>

                </div>
            </div>
        </div>        
    </body>

    <script type="text/javascript">
        //Getting the title and content of news from ShowNews servlet
        //and displaying them with Bootstrap Modal
        function showNews(obj, pic) {

            $.ajax({
                type: 'POST',
                url: 'ShowNews',
                data: {url: obj.toString()},
                dataType: 'json',
                success: function (data) {

                    $('#modal-body-img').attr('src', pic.toString());
                    $('.modal-title').text(data.title);
                    $('.modal-body .mm-body').html((data.content));
                    $('#shMod').click();

                },
                beforeSend: function () {
                    $('.loader-i').show()
                },
                complete: function () {
                    $('.loader-i').hide();
                }
            });
        }


    </script>
</html>