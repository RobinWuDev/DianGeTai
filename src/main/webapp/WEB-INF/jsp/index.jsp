<%--
  Created by IntelliJ IDEA.
  User: Robin
  Date: 16/6/14
  Time: 17:22
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>肉饼屋点歌台</title>
    <script src="<c:url value="/resources/js/main.js" />"></script>
</head>
<body>
<div>
    <p>当前播放:</p>
    <audio  controls autoplay name="media" style="max-width: 100%;max-height: 100%">
        <source src="<c:url value="/file/index.m3u8" />" type="application/x-mpegurl">
    </audio>
</div>
<div>
    <table id="playList">
    </table>
</div>
<div>
    上传者:<input type="text" id="uploader">
</div>
<div>
    <form name="uploadForm" action="add" method="post" enctype="multipart/form-data">
        文件:<input type="file" name="file" accept=".mp3,audio/*"><br/>
        <input type="button" value="点歌" onclick="uploadAndSubmit()">
    </form>
</div>

</body>
</html>
