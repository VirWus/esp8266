<?php

$response = array();


include_once("db_connect.php");


$result = mysqli_query($connect,"SELECT * FROM data ") or die(mysql_error());


if (mysqli_num_rows($result) > 0) {
 
    $response["esp8266"] = array();

    while ($row =  mysqli_fetch_assoc($result)) {

        
        $data = array();
        $data["led"] = $row["led"];
        $data["led1"] = $row["led1"];
        $data["door"] = $row["door"];
        $data["door1"] = $row["door1"];
        $data["temp"] = $row["temp"];
        $data["hum"] = $row["hum"];
        $data["person"] = $row["person"];


        array_push($response["esp8266"], $data);
    }

    $response["success"] = 1;


    echo json_encode($response);
} else {
    $response["success"] = 0;
    $response["message"] = "No data found";

    echo json_encode($response);
}
?>
