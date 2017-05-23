<?php
    require_once('database_credentials.php');
    $row_start = "";
    $row_dest = "";

    $conn = mysqli_connect($HOST , $NAME , $PASS , $DB_NAME);
    if(!$conn){
        $toprint = array('status' => 'Error');
        echo json_encode($toprint);
        die();
    }

    $startpoint = $_POST['startpoint'];
    $endpoint = $_POST['endpoint'];
    if(!isset($startpoint) || empty($startpoint)){
        $startpoint = "";
    }
    if(!isset($endpoint) || empty($endpoint)){
        $endpoint = "";
    }
    $sql = "SELECT DISTINCT trip_id FROM trip_has NATURAL JOIN checkpoint WHERE location_name LIKE '%$startpoint%' AND free_seats > 0";
    $result = $conn->query($sql);
    if(!$row_start = mysqli_fetch_assoc($result)){
        $toprint = array('status' => 'Failure','msg' => 'No trip with that starting point found!');
        echo json_encode($toprint);
        die();
    }
    $col_start = array();
    $col_start[] = $row_start['trip_id'];

    while($row_start = mysqli_fetch_array($result))
        $col_start[] = $row_start['trip_id'];

    $sql = "SELECT DISTINCT trip_id FROM trip_has NATURAL JOIN checkpoint WHERE location_name LIKE '%$endpoint%' AND free_seats > 0";
    $result = $conn->query($sql);
    if(!$row_dest = mysqli_fetch_assoc($result)){
        $toprint = array('status' => 'Failure','msg' => 'No trip with that destination point found!');
        echo json_encode($toprint);
        die();
    }
    $col_dest = array();
    $col_dest[] = $row_dest['trip_id'];

    while($row_dest = mysqli_fetch_array($result))
        $col_dest[] = $row_dest['trip_id'];

    $trips_found = array_intersect($col_start,$col_dest);

    $res = array();

    $i = 0;
    foreach ( $trips_found as $tid){
        $sql = "SELECT price, location_name FROM checkpoint NATURAL JOIN trip_has WHERE trip_id='$tid' AND location_name LIKE '%$startpoint%'";
        $result = $conn->query($sql);
        if(!$row = mysqli_fetch_assoc($result)){
            $toprint = array('status' => 'Failure');
            echo json_encode($toprint);
            die();
        }
        $startPrice = $row['price'];
        $startName = $row['location_name'];

        $sql = "SELECT price, location_name FROM checkpoint NATURAL JOIN trip_has WHERE trip_id='$tid' AND location_name LIKE '%$endpoint%' AND location_name NOT LIKE '%$startpoint%'";
        $result = $conn->query($sql);
        if(!$row = mysqli_fetch_assoc($result)){
            $toprint = array('status' => 'Failure');
            echo json_encode($toprint);
            die();
        }
        $endPrice = $row['price'];
        $endName = $row['location_name'];

        $sql = "SELECT name, surname FROM user NATURAL JOIN has_driver WHERE trip_id = '$tid'";
        $result = $conn->query($sql);
        if(!$row = mysqli_fetch_assoc($result)){
            $toprint = array('status' => 'Failure');
            echo json_encode($toprint);
            die();
        }
        $driverName = $row['name'];
        $driverSname = $row['surname'];

        $sql = "SELECT time_of_departure_h, time_of_departure_m FROM trip WHERE trip_id = '$tid'";
        $result = $conn->query($sql);
        if(!$row = mysqli_fetch_assoc($result)){
            $toprint = array('status' => 'Failure');
            echo json_encode($toprint);
            die();
        }
        $dep_h = $row['time_of_departure_h'];
        $dep_m = $row['time_of_departure_m'];
        array_push($res,array('startPrice'=>$startPrice,'startName'=>$startName,
        'endPrice'=>$endPrice,'endName'=>$endName,'driverName'=>$driverName,
        'driverSname'=>$driverSname,'dep_h'=>$dep_h,'dep_m'=>$dep_m));
        $i = $i + 1;
    }
    $res['size'] = $i;
    echo json_encode($res);
?>