/**
 * Created by hongzhan on 2016/5/18.
 */

$(function () {
    $("[datepicker]").datepicker({
        dateFormat: "yy-mm-dd",
    });
    $( "#day" ).datepicker( "setDate", "-1d" );
    search();
});

var search=function(){
    $("#loading").show(); 
    var day=$('#day').datepicker( "getDate" ).getTime();

    $.post(ctx + "/history/ajax/get", {day:day},
        function (res) {
            $('#table tbody').html('<tr><th>地点</th><th>面积（M<sup>2</sup>）</th><th>确认时间</th></tr>');
            for(d in res.data){
                $('#table tbody').append('<tr><td>'+res.data[d].location+'</td><td>'+res.data[d].area.toFixed(2)+'</td><td>'+moment(res.data[d].ts).format("YYYY-MM-DD HH:mm") +'</td></tr>');

            }
            $("#loading").hide();
        }
    );
           
}

