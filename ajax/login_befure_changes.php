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
   $username = $_POST['un'];
   $password = $_POST['pw'];
   $sql = "SELECT * FROM user_login WHERE password = '$password' AND username = '$username'";
   $result = $conn->query($sql);
   if(!$row = mysqli_fetch_assoc($result)){
       header("Location: index.html?s=f");
   }
   else {
       $_SESSION['id'] = $row['user_id'];
       header("Location: index.html?s=s");
   }
?>
