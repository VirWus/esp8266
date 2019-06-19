<?php

/*
 * kode untuk menghapus data pendaftar
 */

$response = array();

if (isset($_POST['pid'])) {
    $pid = $_POST['pid'];

   
    include_once("db_connect.php");
    
    // update by pendaftaran id (PID)
    $result = mysqli_query($connect,"DELETE FROM test WHERE pid = $pid");
    
    // cek aksi
    if (mysqli_affected_rows() > 0) {
        // jika sukses
        $response["success"] = 1;
        $response["message"] = "Data successfully deleted";

        // echoing JSON response
        echo json_encode($response);
    } else {
        $response["success"] = 0;
        $response["message"] = "No data available";

        // echo no users JSON
        echo json_encode($response);
    }
} else {
    $response["success"] = 0;
    $response["message"] = "Action can not be done";

    // echoing JSON response
    echo json_encode($response);
}
?>
