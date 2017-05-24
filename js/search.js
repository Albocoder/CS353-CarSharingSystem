$(document).ready(function () {
        $.post( "ajax/getUserData.php",function( returneddata ) {
        returneddata = JSON.parse(returneddata);
        if(returneddata.status=="Error"){
            alert("Error connecting to the database");
        }
        else{
            if(returneddata.status=="Failure"){
                //console.log(returneddata);
                window.location.replace('index.html');
            }
            else{
                loadpage(returneddata);
            }
        }
    });
    }
);

function loadpage(jsonData) {
    $('.navbar-brand').text("Welcome "+jsonData.username);
    //console.log(jsonData);
    if(jsonData.driver == 'True'){
        $('#driverStuff').css({'display':'block'});
    }
}

function searchTrips() {
    start = $('#search-start').val();
    end = $('#search-end').val();
    requestData = {'startpoint':start,'endpoint':end};
    $.post( "ajax/searchTrip.php",requestData,function( returneddata ) {
        returneddata = JSON.parse(returneddata);
        if(returneddata.status=="Error"){
            alert("Error connecting to the database");
        }
        else{
            if(returneddata.status=="Failure"){
                alert("Error in Start and End locations!");
            }
            else{
                addTrips(returneddata);
            }
        }
    });
}

function addTrips(jsonData){
    size = jsonData.size;
    var html = "";
    for (i = 0; i < size; i++){
        var formattedhour = ("0" + jsonData[i].dep_h).slice(-2);
        var formattedminute = ("0" + jsonData[i].dep_m).slice(-2);
        html += "<tr><td>" + jsonData[i].startName +"</td>" +
            "<td>"+jsonData[i].endName+"</td>" +
            "<td>"+jsonData[i].driverName+" "+jsonData[i].driverSname+"</td>" +
            "<td>"+(jsonData[i].endPrice-jsonData[i].startPrice)+"</td>" +
            "<td>"+formattedhour+"<b> : </b>"+formattedminute+"</td>"+
            "</tr>";
    }
    $('#dumpingPoint').html(html);
}