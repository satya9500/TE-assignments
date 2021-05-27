<?php include('server.php');  ?>

<?php 
	if (isset($_GET['edit'])) {
		$id = $_GET['edit'];

		$update = true;
		$record = mysqli_query($db, "SELECT * FROM employee WHERE e_id=$id");
		
		if (!$record) {
			echo '<p style="color: red; font-size: 20px;">Could not run query: </p>'.$mysqli->error;
		}

		if ($record->num_rows > 0) {
			$n = mysqli_fetch_array($record);
			$name = $n['name'];
			$eid = $n['e_id'];
            $exp = $n['exp'];
            $area = $n['area'];
            $doj = $n['doj'];
		}
		else{
			$name = "";
			$eid = "";
            $exp = "";
            $area = "";
            $doj = "";
		}
	}
?>

<!DOCTYPE HTML>
<html>
    <head>
        <meta charset="utf-8">
        <title>Employee records</title>
        <!-- <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous"> -->
        
        <?php
        //include CSS Style Sheet
        echo "<link rel='stylesheet' type='text/css' href='./style.css' />";
        ?>

    </head>
    
    <body>
                
        <?php $results = mysqli_query($db, "SELECT * FROM employee"); ?>
        
        <!-- Displaying the database values -->

        <div class="database">

			<div>
				<?php if (isset($_SESSION['message'])): ?>
					<div class="msg">
						<?php 
							echo $_SESSION['message']; 
							unset($_SESSION['message']);
						?>
					</div>
				<?php endif ?>
			</div>

            <h1>DATABASE</h1>
            
			<table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Employee ID</th>
                        <th>Year of Experience</th>
                        <th>Area</th>
                        <th>DOJ</th>
                        <th colspan="2">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <?php while($row = mysqli_fetch_array($results)){?>
                            
                        <tr>
                            <td><?php echo $row['name']; ?></td>
                            <td><?php echo $row['e_id']; ?></td>
                            <td><?php echo $row['exp']; ?></td>
                            <td><?php echo $row['area']; ?></td>
                            <td><?php echo $row['doj']; ?></td>   
                            <td>
                                <a href="index.php?edit=<?php echo $row['e_id']; ?>" class="edit_btn" >Edit</a>
                            </td>
                            <td>
                                <a href="server.php?del=<?php echo $row['e_id']; ?>" class="del_btn">Delete</a>
                            </td>
                        </tr>
                        
                
                    <?php }?>

					<tr>
						<td colspan="7" style="background-color: white; border: none; text-align: right;">Records Fetched : <?php echo $results->num_rows?></td>
					</tr>
                </tbody>
                
            </table>
        </div>
        

        <!-- ------------------------------------------------------------------------------------------------------------------------------- -->

        <hr>
        
        <div class="Register">
			
			<?php if ($update == true): ?>
				<h1>
					Update Details of Employee
				</h1>
			<?php else: ?>
				<h1>
					Register New Employee
				</h1>
			<?php endif ?>

			<form method="post" action="server.php">
				<table>
					<tr>
					<td>Name:</td> <td><input type="text" name="name" value="<?php echo $name; ?>"></td>
					<tr>

					<tr>	
					<td>Employee ID:</td> <td><input type="number" name="eid" value="<?php echo $eid; ?>"></td>
					</tr>
					
					<tr>
					<td>Year of Experience:</td> <td><input type="number" name="exp" value="<?php echo $exp; ?>"></td>
					</tr>
					
					<tr>
					<td>Area:</td> <td><input type="text" name="area" value="<?php echo $area; ?>"></td>
					</tr>
					
					<tr>
					<td>DOJ:</td> <td><input type="date" name="doj" value="<?php echo $doj; ?>"></td>
					</tr>

					<input type="number" style="display: none;" name="originalEid" value="<?php echo $eid; ?>">

					<tr class="submit">
						<td colspan="2">
							<?php if ($update == true): ?>
								<button class="btn" type="submit" name="update" >update</button>
							<?php else: ?>
								<button class="btn" type="submit" name="save" >Save</button>
							<?php endif ?>
						</td>
					</tr>
        		
				</table>
			</form>
        </div>
        
    </body>
    
</html>