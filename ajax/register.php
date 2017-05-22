<?php
    session_start();
    require_once('database_credentials.php');
    $row = "";

    $conn = mysqli_connect($HOST , $NAME , $PASS , $DB_NAME);
    if(!$conn){
        $toprint = array('status' => 'Error');
        echo json_encode($toprint);
        die();
    }

    $mail = $_POST['mail'];
    $username = $_POST['un'];
    $pass = $_POST['pw'];
    $role = $_POST['role'];
    if($role == 1){
        $role = 'TRUE';
    }
    else{
        $role = 'FALSE';
    }

    $sql = "CALL register_user($role,'$mail','$username','$pass',NULL,NULL,0)";
    $result = $conn->query($sql);
    if(!$result){
        $toprint = array('status' => 'Failure','msg'=>'Function call failed!');
        echo json_encode($toprint);
        die();
    }

    $sql = "SELECT * FROM credentials_view WHERE password = '$pass' AND username = '$username'";
    $result = $conn->query($sql);

    if(!$row = mysqli_fetch_assoc($result)){
       $toprint = array('status' => 'Failure','msg'=>'Account was not found in the database!');
    }
    else {
        $id = $row['user_id'];
        $_SESSION['id'] = $id;
        $toprint = array('status' => 'Success');
    }

    echo json_encode($toprint);
 ?>
