<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Registration</title>
    <link rel="stylesheet" href="registerStyle.css">
</head>
<body>
    <form name="regForm" action= "register" method= "post">
        <h1 class="heading">Student Registration</h1>
        <div class="container">
            <table align="center">
    
                <tr><th>Student Roll.No:</th>
                <td> <input id ="rno" type="text" name="roll_no" onblur="rnoval('rno','1')" ></td>
                <td><div id="1" value=""></div></td>
                </tr>
        
                <tr><th>Student Name:</th>
                <td> <input id ="sname" type="text" name="name" onblur="nameval('sname','2')" ></td>
                <td><div id="2" value=""></div></td>
                </tr>
        
                <tr><th>Branch Of Engineering : </th>
                <td><select id="select_branch" name="branch">
		                <option>Computer Engineering</option>
		                <option>Mechanical Engineering</option>
		                <option>Civil Engineering</option>
		                <option>Production Engineering</option>
		            </select>
                <td><div id="3" value=""></div></td>
                </tr>
        
                <tr><th>Year Of Engineering : </th>
                <td><select id="select_year" name="year">
                      <option>FE</option>
                      <option>SE</option>
                      <option>TE</option>
                      <option>BE</option>
                    </select>
                <td><div id="4" value=""></div></td>
                </tr>

                <tr><th>Contact No:</th>
                  <td> <input id ="cno" type="text" name="contact" onblur="conoval('cno','5')" ></td>
                  <td><div id="5" value=""></div></td>
                </tr>
        
                <tr><th>Email :</th>
                  <td> <input id ="email" type="text" name="email" onblur="eval('email','6')" ></td>
                  <td><div id="6" value=""></div></td>
                </tr>
        
                <tr><th> Password : </th>
                  <td> <input id ="password" type="password" name="password" onblur="passval('password','7')" ></td>
                  <td><div id="7" value=""></div></td>
                </tr>
        
            </table>
            <button type="submit" value= "Submit" onclick="final()">Submit</button>
            <a href="./login.jsp" class= "loginLink">Click here to Login</a>
        </div>
    </form>







    <!-- --------------------------------------------------------------------------------------------------------------------------- -->










  
    <script>
    var flag1=0;
    var flag2=0;
    var flag3=0;
    var flag4=0;
    var flag5=0;
    function rnoval(id,lb)
    {
      var x = document.getElementById(id).value ;
      var txt = document.getElementById(lb).value ;
        if(x != ""){
        if(isNaN(x) == true)
        document.getElementById(lb).innerHTML =" <font color=red>   Enter a valid number</font>";
        else{
            document.getElementById(lb).innerHTML = null;
            flag1++;
            }
        
        }
        else
        document.getElementById(lb).innerHTML = "<font color=red>     Cant' be empty</font>";
    }
    
    function conoval(id,lb)
    {
      var x = document.getElementById(id).value ;
      var txt = document.getElementById(lb).value ;
     if(x != ""){
      if(isNaN(x) == true  ||  x.trim().length > 10  ||  x.trim().length < 10)
       document.getElementById(lb).innerHTML =" <font color=red>   Enter a valid number</font>";
      else
        {
         document.getElementById(lb).innerHTML = null;
         flag2++;
         }
    }
    else
    document.getElementById(lb).innerHTML = "<font color=red>     Cant' be empty</font>";
    }
    
    function nameval(id,lb)
    {
      var x = document.getElementById(id).value ;
      var txt = document.getElementById(lb).value ;
     if(x != ""){
      if(isNaN(x) == false)
       document.getElementById(lb).innerHTML =" <font color=red>   Enter a valid name</font>";
      else
        {
         document.getElementById(lb).innerHTML = null;
         flag3++;
         }
    }
    else
    document.getElementById(lb).innerHTML = "<font color=red>     Cant' be empty</font>";
    }
    
    function eval(id,lb)
    {
      var x = document.getElementById(id).value ;
      var txt = document.getElementById(lb).value ;
      var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
      if(x != ""){
        if(reg.test(x) == false){
          document.getElementById(lb).innerHTML =" <font color=red>   Enter a valid email address</font>";
        }
        else
        {
          document.getElementById(lb).innerHTML = null;
          flag4++;
        }
      }
      else
        document.getElementById(lb).innerHTML = "<font color=red>     Cant' be empty</font>";
    }

    function passval(id, lb){
      var x = document.getElementById(id).value ;
      var txt = document.getElementById(lb).value ;
      if(x != ""){
          document.getElementById(lb).innerHTML = null;
          flag5++;
      }
      else{
        document.getElementById(lb).innerHTML = "<font color=red>     Cant' be empty</font>";
        flag5 = 0;
      }
    }
    
    function final()
    {
     if (flag1 !=0 && flag2 !=0 && flag3 !=0 && flag4 !=0 && flag5 != 0)
     {
      alert("form submitted successfully!");
      location.reload();
     }
    else
     alert("Wrong");
    }
    
    </script>
</body>
</html>