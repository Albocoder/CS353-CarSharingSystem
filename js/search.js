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
    console.log(jsonData);
    if(jsonData.driver == 'True'){
        $('#driverStuff').css({'display':'block'});
    }
}