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

    $id = $_SESSION['id'];
    $time_of_dept_m =  $_POST['timem'];
    $time_of_dept_h =  $_POST['timeh'];

    $cp1_name = $_POST['ch1_name'];
    $cp1_loc_lat = $_POST['ch1_lat'];
    $cp1_loc_lon = $_POST['ch1_lon'];
    $cp1_price = $_POST['ch1_price'];
    $cp1_hour = $_POST['ch1_h'];
    $cp1_min = $_POST['ch1_m'];

    $cp2_name = $_POST['ch2_name'];
    $cp2_loc_lat = $_POST['ch2_lat'];
    $cp2_loc_lon = $_POST['ch2_lon'];
    $cp2_price = $_POST['ch2_price'];
    $cp2_hour = $_POST['ch2_h'];
    $cp2_min = $_POST['ch2_m'];



    $sql = "SELECT max(trip_id) FROM trip";
    $result = $conn->query($sql);
    if(!$row = mysqli_fetch_assoc($result)){
        $toprint = array('status' => 'Failure','msg' => 'Max tripid not selected.');
        echo json_encode($toprint);
        die();
    }
    $t_id = row['trip_id']+1;

    $sql = "INSERT INTO has_driver(user_id) VALUES('$tid','$id') ";
    $result = $conn->query($sql);
    if(!$result){
        $toprint = array('status' => 'Failure','msg' => 'Insertion in has_driver table failed.');
    }
    else
    {
        $sql = "INSERT INTO trip(time_of_departure_h, time_of_departure_m, status, free_seats ) VALUES('$time_of_dept_h', '$time_of_dept_m', 'open', 4) WHERE trip_id = '$t_id'";
        $result = $conn->query($sql);
        if(!$result){
            $toprint = array('status' => 'Failure','msg'=>'Could not do the insertion on trip');
            echo json_encode($toprint);
            die();
        }

        $sql = "INSERT INTO trip_has(trip_id) VALUES('$t_id')";
        $result = $conn->query($sql);
        if(!$result){
            $toprint = array('status' => 'Failure','msg'=>'Could not do the insertion on trip_has');
            echo json_encode($toprint);
            die();
        }

        $r_id = $row['r_id'];
        $sql = "INSERT INTO checkpoints(r_id, location_name, location_lat, location_lon, price, ETA_hour, ETA_min) VALUES('$r_id','$cp1_name', '$cp1_loc_lat', '$cp1_loc_lon', '$cp1_price', '$cp1_hour', '$cp1_min' ) ";
        $result = $conn->query($sql);

        $sql = "INSERT INTO checkpoints(r_id, location_name, location_lat, location_lon, price, ETA_hour, ETA_min) VALUES('$r_id','$cp2_name', '$cp2_loc_lat', '$cp2_loc_lon', '$cp2_price', '$cp2_hour', '$cp2_min' ) ";
        $result = $conn->query($sql);
        $toprint = array('status' => 'Success');
    }
    echo json_encode($toprint);
?>