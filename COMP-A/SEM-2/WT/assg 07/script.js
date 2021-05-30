
const updateDatabaseForm = document.getElementById("updateDatabaseForm");

updateDatabaseForm.addEventListener("submit", function(event) {

    event.preventDefault(); // prevents the default action of the form on submit


    var name =  document.getElementsByName("name")[0].value;
    var eid = document.getElementsByName("eid")[0].value;
    var exp = document.getElementsByName("exp")[0].value;
    var area = document.getElementsByName("area")[0].value;
    var doj = document.getElementsByName("doj")[0].value;
    var originalEid = document.getElementsByName("originalEid")[0].value;    

    var saveButton = document.querySelector("#save");
    var updateButton = document.querySelector("#update");

    var data = `name=${name}&eid=${eid}&exp=${exp}&area=${area}&doj=${doj}&originalEid=${originalEid}`;

    if(saveButton.style.display != 'none'){
        saveButton = saveButton.value;

        data = data + `&save=${saveButton}`;
    }

    if(updateButton.style.display != 'none'){
        updateButton=  updateButton.value;

        data = data + `&update=${updateButton}`;
    }

   

    var request = new XMLHttpRequest();
    var url = "./server.php";

    request.open("POST", url, true); // the third parameter asks if we want the request to be async or not, hence we set it to true bcz we want to send async req

    request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");    


    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) { // ready state 4 tell that the processing of request is completed and status 200 tells that the request was successfully sent

            //console.log(request.responseText);

            var str = request.responseText; 

            var arr = JSON.parse(str);

            var message = arr[arr.length-1]['message'];

            var table = document.querySelector('#databaseTable');

            var tableData = "";

            
            for(var i = 0 ; i < arr.length - 1 ; i++){
                tableData +=  `<tr>
                                    <td>${arr[i]['name']}</td>
                                    <td>${arr[i]['eid']}</td>
                                    <td>${arr[i]['exp']}</td>
                                    <td>${arr[i]['area']}</td>
                                    <td>${arr[i]['doj']}</td>   
                                    <td>
                                        <label onclick="editData('${arr[i]['eid']}', '${arr[i]['name']}', '${arr[i]['exp']}', '${arr[i]['area']}', '${arr[i]['doj']}')" class="edit_btn" >Edit</label>
                                    </td>
                                    <td>
                                        <label onclick="deleteData('${arr[i]['eid']}')" class="del_btn">Delete</a>
                                    </td>
                                </tr>`;
            }

            table.innerHTML = `<thead>
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
                                
                                    ${tableData}

                                    <tr>
                                        <td colspan="7" style="background-color: white; border: none; text-align: right;">Records Fetched : ${arr.length-1}</td>
                                    </tr>
                                </tbody>`;

        }

        document.querySelector('.msg').innerHTML = message;
    };
    
    request.send(data);

});  


var editData = function(eid, name, exp, area, doj){
    //console.log(eid, name, exp, area, doj);
    document.querySelector('.msg').innerHTML = "";

    var editName =  document.getElementsByName("name")[0];
    var editEid = document.getElementsByName("eid")[0];
    var editExp = document.getElementsByName("exp")[0];
    var editArea = document.getElementsByName("area")[0];
    var editDoj = document.getElementsByName("doj")[0];
    var editOriginalEid = document.getElementsByName("originalEid")[0];

    editName.value = name;
    editEid.value = eid;
    editExp.value = exp;
    editArea.value = area;
    editDoj.value = doj;
    editOriginalEid.value = eid;

    document.querySelector('#save').style.display = 'none';
    document.querySelector('#update').style.display = 'inline-block';
}

var deleteData = function(eid){
    var request = new XMLHttpRequest();
    var url = "./server.php";

    var data = `del=${eid}`;

    request.open("GET", url + `?${data}`, true); // the third parameter asks if we want the request to be async or not, hence we set it to true bcz we want to send async req

    request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");    

    

    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) { // ready state 4 tell that the processing of request is completed and status 200 tells that the request was successfully sent

            console.log(request.responseText);

            var str = request.responseText; 

            var arr = JSON.parse(str);

            var message = arr[arr.length-1]['message'];

            var table = document.querySelector('#databaseTable');

            var tableData = "";

            
            for(var i = 0 ; i < arr.length - 1 ; i++){
                tableData +=  `<tr>
                                    <td>${arr[i]['name']}</td>
                                    <td>${arr[i]['eid']}</td>
                                    <td>${arr[i]['exp']}</td>
                                    <td>${arr[i]['area']}</td>
                                    <td>${arr[i]['doj']}</td>   
                                    <td>
                                        <label onclick="editData('${arr[i]['eid']}', '${arr[i]['name']}', '${arr[i]['exp']}', '${arr[i]['area']}', '${arr[i]['doj']}')" class="edit_btn" >Edit</label>
                                    </td>
                                    <td>
                                        <label onclick="deleteData('${arr[i]['eid']}')" class="del_btn">Delete</a>
                                    </td>
                                </tr>`;
            }

            table.innerHTML = `<thead>
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
                                
                                    ${tableData}

                                    <tr>
                                        <td colspan="7" style="background-color: white; border: none; text-align: right;">Records Fetched : ${arr.length-1}</td>
                                    </tr>
                                </tbody>`;

        }

        document.querySelector('.msg').innerHTML = message;
    };
    
    request.send(data);
}