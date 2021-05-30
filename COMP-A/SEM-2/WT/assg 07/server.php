<?php

    session_start();
    
    $db = mysqli_connect('localhost', 'root', 'Mysql@24', 'curd');

    if($db == false){
        die("Error: Could not Connect!... ".mysqli_connect_error());
    }

    $name = "";
    $eid = "";
    $exp = "";
    $area = "";
    $doj = "";
    $id = 0;

    $originalEid = "";
    $update = false;

    if(isset($_POST['save'])){
        $eid = $_POST['eid'];
        $name = $_POST['name'];
        $exp = $_POST['exp'];
        $area = $_POST['area'];
        $doj = $_POST['doj'];

        $result = mysqli_query($db, "INSERT INTO employee VALUES($eid, '$name', $exp, '$area', '$doj')");

        $querySent = "INSERT INTO employee VALUES($eid, '$name', $exp, '$area', '$doj')";
    
        $database = mysqli_query($db, "SELECT * FROM employee");

        $return_arr = array();

        while($row = mysqli_fetch_array($database)){
            $eid = $row['e_id'];
            $name = $row['name'];
            $exp = $row['exp'];
            $area = $row['area'];
            $doj = $row['doj'];

            $return_arr[] = array(
                'eid' => $eid,
                'name' => $name,
                'exp' => $exp,
                'area' => $area,
                'doj' => $doj
            );
        }

        if(!$result){
            $return_arr[] = array('message' => "Query Sent : ".$querySent."<br>Error occured!...");
        }
        else{
            $return_arr[] = array('message' => "Query Sent : ".$querySent."<br>Employee Data Saved Successfully!");
        }

        echo json_encode($return_arr);
        
    }
        
        
    if (isset($_POST['update'])) {
        $eid = $_POST['eid'];
        $name = $_POST['name'];
        $exp = $_POST['exp'];
        $area = $_POST['area'];
        $doj = $_POST['doj'];

        $originalEid = $_POST['originalEid'];

        $result = mysqli_query($db, "UPDATE employee SET name='$name', e_id=$eid, exp='$exp', area='$area', doj='$doj' WHERE e_id=$originalEid");    

        $querySent = "UPDATE employee SET name='$name', e_id=$eid, exp='$exp', area='$area', doj='$doj' WHERE e_id=$originalEid";

        $database = mysqli_query($db, "SELECT * FROM employee");

        $return_arr = array();

        while($row = mysqli_fetch_array($database)){
            $eid = $row['e_id'];
            $name = $row['name'];
            $exp = $row['exp'];
            $area = $row['area'];
            $doj = $row['doj'];

            $return_arr[] = array(
                'eid' => $eid,
                'name' => $name,
                'exp' => $exp,
                'area' => $area,
                'doj' => $doj
            );
        }

        if(!$result){
            $return_arr[] = array('message' => "Query Sent : ".$querySent."<br>Error occured!...");
        }
        else{
            $return_arr[] = array('message' => "Query Sent : ".$querySent."<br>Employee Data Updated Successfully!");
        }

        echo json_encode($return_arr);

    }

    if (isset($_GET['del'])) {
        $id = $_GET['del'];
        
        $result = mysqli_query($db, "DELETE FROM employee WHERE e_id=$id");

        $querySent = "DELETE FROM employee WHERE e_id=$id";

        $database = mysqli_query($db, "SELECT * FROM employee");

        $return_arr = array();

        while($row = mysqli_fetch_array($database)){
            $eid = $row['e_id'];
            $name = $row['name'];
            $exp = $row['exp'];
            $area = $row['area'];
            $doj = $row['doj'];

            $return_arr[] = array(
                'eid' => $eid,
                'name' => $name,
                'exp' => $exp,
                'area' => $area,
                'doj' => $doj
            );
        }

        if(!$result){
            $return_arr[] = array('message' => "Query Sent : ".$querySent."<br>Error occured!...");
        }
        else{
            $return_arr[] = array('message' => "Query Sent : ".$querySent."<br>Employee Data Deleted Successfully!");
        }

        echo json_encode($return_arr);
    }
    

?>