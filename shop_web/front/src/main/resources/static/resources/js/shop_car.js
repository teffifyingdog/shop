$(function () {
    $.ajax({
        type:"POST",
        url:"http://192.168.252.1:60000/car/list",
        success:function (shopcars) {
            if (shopcars!=null&&shopcars.length>0){
                $("#car_number_id").html(shopcars.length);
                var html="";
                html="<ul>";
                for (var i = 0; i < shopcars.length; i++) {
                    html += "<li style='width: 100%; height: 60px'>";
                    html += "<img style='width: 50px; height: 50px; margin-right: 10px' src='" + shopcars[i].goods.cover + "'/>";
                    html += "<span style='margin-right: 10px'>" + shopcars[i].goods.title + "</span>";
                    html += "<span>" + shopcars[i].goods.price + " * " + shopcars[i].gnum + "</span>"
                    html += "</li>";
                }
                html+="</ul>";
                $("#car_id").html(html);
            }else {
                $("#car_id").html("<p>还没有商品，赶快去挑选！</p>");
            }
        },
        dataType:"json"
    });
});