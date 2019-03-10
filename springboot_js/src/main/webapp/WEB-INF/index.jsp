<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <title>Title</title>
</head>


<body>
    index.jsp
</body>
</html>

<script>
    //alert("jquery 引入成功")
    $(function () {
        //alert("jquery 引入成功")
    });
    if ($("#mId").val() == '') {
        var map, geolocation;
        //加载地图，调用浏览器定位服务
        map = new AMap.Map('map', {
            resizeEnable: true
        });
        map.plugin('AMap.Geolocation', function () {
            geolocation = new AMap.Geolocation({
                enableHighAccuracy: true, //是否使用高精度定位，默认:true
                timeout: 10000, //超过10秒后停止定位，默认：无穷大
                buttonPosition: 'RB'
            });
            map.addControl(geolocation);
            geolocation.getCurrentPosition();
            AMap.event.addListener(geolocation, 'complete', onComplete); //返回定位信息
            AMap.event.addListener(geolocation, 'error', onError); //返回定位出错信息
        });

        //解析定位结果
        function onComplete(data) {
            var str = ['定位成功'];
            var lng = data.position.getLng();
            var lat = data.position.getLat();
            getPosition(lng, lat);
            jQuery.ajax({
                type: "post",
                url: "/homepage/savePosition.jhtml",
                dataType: "json",
                data: {lng: lng, lat: lat},
                async: true,
                success: function (data) {
                    if (data.status == true) {
                        console.log("保存地理位置成功");
                    }
                }
            })

        }

        //解析定位错误信息
        function onError(data) {
            console.error("computer capture position is faild: " + data.message + "you can see it on the your phone");
        }

        //测试
        function getPosition(lng, lat) {
            jQuery.ajax({
                type: "GET",
                url: "/homepage/locationAddress.jhtml",
                data: {"lng": lng, "lat": lat},
                dataType: 'json',
                async: false,
                success: function (data) {
                    var pitchedPlace = $(".qyxz").find('.land').html().trim();
                    if (data.status == "1") {
                        if (pitchedPlace != data.mName) {
                            showPop(data.msg);
                        }
                        $(".box-true").click(function () {
                            window.location.href = "/mobile/index.jhtml?mallId=" + data.mId + "&status=" + data.status;
                        })
                    } else if (data.status == "4") {
                        //showPop(data.msg);
                        if (pitchedPlace != data.mName) {
                            showPop(data.msg);
                        }
                        $(".box-true").click(function () {
                            window.location.href = "/mobile/index.jhtml?mallId=" + data.mIdTemp + "&status=" + data.status;
                        })
                    } else if (data.status == "3") {
                        $("#em2").html(data.mName);
                        $("#em2").prev().removeClass("active");
                        $("#em2").parent().attr("onclick", "checkJump('em2','" + data.mId + "','" + data.status + "')");
                        $("#em1").html(data.mNameTemp);
                        $("#em1").parent().attr("onclick", "checkJump('em1','" + data.mIdTemp + "','" + data.status + "')");
                        $("#em1").prev().removeClass("active");
                        $("#mall_select").fadeIn(100);
                    }
                }
            });
        };
    }
</script>
