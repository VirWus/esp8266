<?php


$response = array();
   
    include_once("db_connect.php");
    
    $result = mysqli_query($connect,"SELECT * FROM adress_ip");
	
	$response["esp8266"] = array();
    
    if (!empty($result)) {
      
        if (mysqli_num_rows($result) > 0) {

            $result =  mysqli_fetch_assoc($result);

            $data = array();
            $data["ip"] = $result["ip"];
           
      
            $response["success"] = 1;


            array_push($response["esp8266"], $data);


            echo json_encode($response);
        } else {
           
            $response["success"] = 0;
            $response["message"] = "No data";


            echo json_encode($response);
        }
    } else {
        $response["success"] = 0;
        $response["message"] = "No data";

        echo json_encode($response);
    } 
?>
