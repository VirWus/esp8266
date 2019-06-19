<?php

$response = array();

 // include db connect
include_once("db_connect.php");





if (isset($_GET['led']) && isset($_GET['led1']) && isset($_GET['door']) && isset($_GET['door1']) && isset($_GET['temp']) && isset($_GET['hum']) && isset($_GET['person'])) {


        $led = $_GET['led'];
        $led1 = $_GET['led1'];
        $door = $_GET['door'];
        $door1 = $_GET['door1'];
        $temp = $_GET['temp'];
        $hum = $_GET['hum'];
        $person = $_GET['person'];

    
    $result = mysqli_query($connect,"UPDATE data SET led = '$led', led1 = '$led1', door = '$door' , door1 = '$door1', temp = '$temp',hum = '$hum',person = '$person' ");

    
    if ($result) {
      
        $response["success"] = 1;
        $response["message"] = "Your data is updated successfully";
        
     
        echo json_encode($response);
    } else {
        
    }
} else {


    if (isset($_GET['led'])){
       
        $led = $_GET['led'];
    
        $result = mysqli_query($connect,"UPDATE data SET led = '$led' ");
 echo "string";
    }
    

    if (isset($_GET['led1'])) {
        
        $led1 = $_GET['led1'];

    $result = mysqli_query($connect,"UPDATE data SET  led1 = '$led1' ");

    }

    if (isset($_GET['door'])) {
       
        $door = $_GET['door'];
    
$result = mysqli_query($connect,"UPDATE data SET  door = '$door' ");
    }


    if (isset($_GET['door1'])) {
        
        $door1 = $_GET['door1'];

    $result = mysqli_query($connect,"UPDATE data SET  door1 = '$door1' ");

    }


    if (isset($_GET['temp'])) {
        
        $temp = $_GET['temp'];

   $result = mysqli_query($connect,"UPDATE data SET temp = '$temp' ");

    }


    if (isset($_GET['hum'])) {
       
        $hum = $_GET['hum'];
  
$result = mysqli_query($connect,"UPDATE data SET hum = '$hum'");
    }

    if (isset($_GET['person'])) {
        
        $person = $_GET['person'];
    
$result = mysqli_query($connect,"UPDATE data SET person = '$person' ");

    }



     
    if ($result) {
    $response["success"] = 1;
    $response["message"] = "Your data is updated successfully";
    echo json_encode($response);
    } else {
    }
        
        




}
?>
