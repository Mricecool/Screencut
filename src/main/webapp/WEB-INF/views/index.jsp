<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>高德</title>
    <link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
</head>
<body>
<div id="container" style="width: 5000px;height: 5000px"></div>
<div style="position: absolute"><input style="margin-left: 20px" type="button" value="生成图片DomToImage" onclick="takeScreenshot()"/><input style="margin-left: 20px" type="button" value="生成图片HtmlToCanvas" onclick="takeScreenshot2()"/></div>
<script src="/js/jquery.min.js"></script>
<script src="/js/dom-to-image.js"></script>
<script src="/js/html2canvas.min.js"></script>
<script src="http://webapi.amap.com/maps?v=1.3&key=3c3fc378a45ac1957d120ce02a8df538&callback=init"></script>

<script>
    function init(){
        var map = new AMap.Map('container', {
            center: [114.511731,38.043085],
            zoom: 15
        });
        map.plugin(["AMap.ToolBar"], function() {
            map.addControl(new AMap.ToolBar());
        });
    }

    function takeScreenshot() {
        var node = document.getElementById('container');
        domtoimage.toPng(node)
                .then(function (dataUrl) {
                    var img = new Image();
                    img.src = dataUrl;
                    window.open(dataUrl)
                })
                .catch(function (error) {
                    console.error('oops, something went wrong!', error);
                });
    }

    function takeScreenshot2() {
            html2canvas(document.getElementById('container'), {
                onrendered: function (canvas) {
                    var url = canvas.toDataURL();
                    window.open(url);
                },
                useCORS: true
            });
    }
</script>
</body>
</html>
