/**
 * Created by hongzhan on 2016/5/18.
 */

$(function () {
    $("[datepicker]").datepicker({
        dateFormat: "yy-mm-dd",
    });
    $( "#start" ).datepicker( "setDate", "-7d" );
    $( "#end" ).datepicker( "setDate", "+0d" );
    search();
});

var search=function(){
    $("#loading").show();
    var start=$('#start').datepicker( "getDate" ).getTime();
    var end=$('#end').datepicker( "getDate" ).getTime();
    var kw=$("#groupSel").val();
    $.post(ctx + "/bu/monitor/ajax/getDiagrams", {start:start,end:end,group:kw},
        function (res) {
            if(res.status=="fail"){
                alert(res.msg);
            }else{
                var data=eval('(' + res.data + ')');
                for(d in data){
                    var diagram=data[d];
                    diagram.title={text:null};
                    diagram.credits={enabled:false};
                    diagram.legend={enabled:false};

                    diagram.yAxis={min: 0,title:{text:null}};
                    var xLength=diagram.series[0].data.length;
                    diagram.xAxis.tickInterval=Math.round(xLength>12? xLength/12:1);
                    diagram.xAxis.startOnTick=true;
                    diagram.tooltip= {
                        formatter: function () {
                            return  this.x + ' <br> <b>数量：' + this.y + '</b>';
                        }
                    };
                    $('#'+d).highcharts(diagram);
                }
            }
            $("#loading").hide();
        });

}