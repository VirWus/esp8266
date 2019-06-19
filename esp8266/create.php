<?php


$response = array();


if (isset($_POST['name']) && isset($_POST['email']) && isset($_POST['description'])) {

    
    
    $name = $_POST['name'];
    $email = $_POST['email'];
    $description = $_POST['description'];

    include_once("db_connect.php");
    
    $result = mysqli_query($connect,"INSERT INTO test VALUES(NULL,'$name','$email','$description',NULL,NULL)");


    if ($result) {
       
        $response["success"] = 1;
        $response["message"] = "Your registration is successful";

        
        echo json_encode($response);
    } else {
       
        $response["success"] = 0;
        $response["message"] = "The system detects an error, please try again";
        
      
        echo json_encode($response);
    }
} else {
    $response["success"] = 0;
    $response["message"] = "Please complete the action before starting your request";

 
    echo json_encode($response);
}
?>
