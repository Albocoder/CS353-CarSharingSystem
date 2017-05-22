$('.carousel').carousel({
    interval: 3000
})

function login() {
    username = $('#usrnm').val();
    password = $('#passwd').val();
    $.post( "ajax/login.php",{'un':username ,'pw':password},function( returneddata ) {
        returneddata = JSON.parse(returneddata);
        if(returneddata.status==="Error"){
            alert("Error connecting to the database");
        }
        else{
            if(returneddata.status==="Failure"){
                alert("Username/Password combination is wrong");
            }
            else{
                window.location.href = 'search.html';
            }
        }
    });
}

function register() {
    password = $('#registerpw').val();
    retrypassword = $('#retrypw').val();
    //must do this
    if (retrypassword != password){
        alert("Passwords don't match!");
        return;
    }
    role = $('#rolepicker label.active input').val();
    if(role != 1 && role != 2){
        alert("You must choose a role!");
        return;
    }
    username = $('#un').val();
    email = $('#email').val();
    $.post( "ajax/register.php",{'mail':email,'un':username, 'pw':password,'role':role},function( returneddata ) {
        returneddata = JSON.parse(returneddata);
        if(returneddata.status==="Error") {
            alert("Error connecting to the database!");
        }
        else {
            if(returneddata.status==="Failure") {
                console.log(returneddata);
                alert("Username or Email already exists!");
            }
            else {
                window.location.href = 'search.html';
            }
        }
    });
}