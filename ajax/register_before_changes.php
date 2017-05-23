<?php
  session_start();
  $HOST = "localhost";
  $NAME = "root";
  $PASS = "";
  $DB_NAME = "Project";
  $row = "";
  $conn = mysqli_connect($HOST , $NAME , $PASS , $DB_NAME);
  if(!$conn){
    die("Server Connection Failed!");
  }
  $mail = $_POST['mail'];
  $username = $_POST['un'];
  $pass = $_POST['pw'];
  $sql = "INSERT INTO user_login(username, email, password) VALUES ('$username', '$mail', '$pass')";
  $result = $conn->query($sql);
  $sql = "SELECT * FROM user_login WHERE password = '$pass' AND username = '$username'";
  $result = $conn->query($sql);
   if(!$row = mysqli_fetch_assoc($result)){
       //header("Location: index.php?s=f");
       die("CANNOT REGISTER");
   }
   else {
       $_SESSION['id'] = $row['user_id'];
       header("Location: index.html");
   }
 ?>
