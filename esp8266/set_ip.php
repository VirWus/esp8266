<?php

$response = array();

if (isset($_GET['ip'])) {
    
    $ip = $_GET['ip'];
  

    include_once("db_connect.php");


    $result = mysqli_query($connect,"UPDATE adress_ip SET ip = '$ip' where id = '1' ");


    if ($result) {

        $response["success"] = 1;
        $response["message"] = "Your data is updated successfully";
        

        echo json_encode($response);
    } else {
        
    }
} else {
    $response["success"] = 0;
    $response["message"] = "Please complete your data";


    echo json_encode($response);
}
?>
