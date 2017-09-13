<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/28 0028
  Time: 下午 8:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>百度</title>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=GS8k9LAScdMIqvPARjAZz8WkNO7Dmeu1">
        //v2.0版本的引用方式：src="http://api.map.baidu.com/api?v=2.0&ak=您的密钥"
        //v1.4版本及以前版本的引用方式：src="http://api.map.baidu.com/api?v=1.4&key=您的密钥&callback=initialize"
    </script>
<body>
<div><input type="button" value="生成jpg" onclick="jpg()"><input style="margin-left: 20px" type="button" value="生成png"
                                                               onclick="png()"><input style="margin-left: 20px"
                                                                                      type="button" value="转换图片"
                                                                                      onclick="exc()"><input
        style="margin-left: 20px" type="button" value="转换图片Dom" onclick="domto()"></div>
<div id="container" style="width: 2500px;height: 2500px"></div>

<script type="text/javascript">
    var map = new BMap.Map("container");          // 创建地图实例
    var point = new BMap.Point(114.511731, 38.043085);  // 创建点坐标
    map.centerAndZoom(point, 16);
    map.addControl(new BMap.NavigationControl());// 初始化地图，设置中心点坐标和地图级别
    //var marker = new BMap.Marker(point);
    addMarker(point, 1);


    function addMarker(point, index) {  // 创建图标对象
        var myIcon = new BMap.Icon("/images/location_zsy.png", new BMap.Size(40, 40), {
// 指定定位位置。
// 当标注显示在地图上时，其所指向的地理位置距离图标左上
// 角各偏移10像素和25像素。您可以看到在本例中该位置即是
            // 图标中央下端的尖角位置。
            anchor: new BMap.Size(10, 25)
        });
// 创建标注对象并添加到地图
        var marker = new BMap.Marker(point, {icon: myIcon});
        map.addOverlay(marker);
    }

    for (var i = 0; i < 10; i++) {
        var point = new BMap.Point(114.511731 + (Math.random() * 0.0666), 38.043085 + (Math.random() * 0.0666));
        addMarker(point, i);
    }
</script>

</body>
<script src="/js/html2canvas.min.js"></script>
<script src="/js/dom-to-image.js"></script>
<script src="/js/jquery.min.js"></script>
<script type="text/javascript">
    function jpg() {
        html2canvas(document.getElementById('container'), {
            onrendered: function (canvas) {
                var url = canvas.toDataURL("image/jpeg");
                window.open(url);
            },
            useCORS: true
        });
    }

    function png() {
        html2canvas(document.getElementById('container'), {
            onrendered: function (canvas) {
                var url = canvas.toDataURL();
                window.open(url);
            },
            useCORS: true
        });
    }

    function exc() {
        html2canvas(document.getElementById('container'), {
            onrendered: function (canvas) {
                var url = canvas.toDataURL("image/jpeg");
                $.ajax({
                    type: "POST",
                    url: '/upload',
                    data: {data: url},
                    timeout: 60000,
                    success: function (data) {
                        console.log(data) //服务器上保存图片路径，data是返回的文件名。
                    }
                });
            }
        });
    }

    function domto() {
        var node = document.getElementById('container');
        var responseText;
        domtoimage.toBlob(node)
                .then(function (blob) {
                    var xhr = new XMLHttpRequest();
                    xhr.open('POST', '/upload', true);
                    xhr.onreadystatechange = function () {
                        if (xhr.readyState == 4 && xhr.status == 200) {
                            responseText = xhr.responseText;
                            if (responseText != "") {
                                console.log(data)
                            }
                        }
                    };
                    xhr.setRequestHeader("Content-Type", "image/jpeg");
                    xhr.send(blob);
                });
    }

</script>
</html>
