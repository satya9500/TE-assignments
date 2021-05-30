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
    
        if(!$result){
            $_SESSION['message'] = "Query Sent : ".$querySent."<br>Error occured!...";
        }
        else{
            $_SESSION['message'] = "Query Sent : ".$querySent."<br>SAVED into Curd Database!";
        }
        
        header('location: index.php');
        
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
    
    if(!$result){
        $_SESSION['message'] = "Query Sent : ".$querySent."<br>Error occured!...";
    }
    else{
        $_SESSION['message'] = "Query Sent : ".$querySent."<br>Employee Data Updated Successfully!";
    }

	header('location: index.php'); // redirects to index.php
}

if (isset($_GET['del'])) {
	$id = $_GET['del'];
	mysqli_query($db, "DELETE FROM employee WHERE e_id=$id");
	$_SESSION['message'] = "Record of employee deleted!"; 
	header('location: index.php'); // redirects to index.php
}
    

?>