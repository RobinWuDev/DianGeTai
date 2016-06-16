window.onload = init();

function init() {
    refreshPlayList();
    setInterval("refreshPlayList()",10000);//1000为1秒钟
}

function refreshPlayList() {
    var xmlhttp = new XMLHttpRequest();
    var url = "/playList";

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            var response = JSON.parse(xmlhttp.responseText);
            var code = response.code;
            if(code == 0) {
                updatePlayList(response.data);
            } else {
                alert("update faild");
            }
        }
    };
    xmlhttp.open("POST", url, true);
    xmlhttp.send();
}

function updatePlayList(arr) {
    var table = document.getElementById('playList');
    table.innerHTML = "<tr><th>歌曲名称</th><th>上传者</th><th>点赞数</th><th>点赞</th></tr>";
    var i;
    for(i = 0; i < arr.length; i++) {
        var music = arr[i];
        var trObj = document.createElement('tr');
        var out   = "<td>" + music.name + "</td>";
        out      += "<td>" + music.uploader + "</td>";
        out      += "<td>" + music.starNumber + "</td>";
        out      += "<td><button onclick='star("+music.id+")'>顶</button></td>";
        trObj.innerHTML = out;
        table.appendChild(trObj);
    }

}

function star(id) {
    var xmlhttp = new XMLHttpRequest();
    var url = "/star?id="+id;

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            var response = JSON.parse(xmlhttp.responseText);
            var code = response.code;
            if(code == 0) {
                refreshPlayList();
            } else {
                alert("star faild");
            }
        }
    };
    xmlhttp.open("get", url, true);
    xmlhttp.send();
}

function uploadAndSubmit() {
    var uplaoder = document.getElementById("uploader").value;
    if(uplaoder.length == 0) {
        alert("上传者不能为空!");
        return;
    }
    var form = document.forms["uploadForm"];

    if (form["file"].files.length > 0) {
        // 寻找表单域中的 <input type="file" ... /> 标签
        var file = form["file"].files[0];
        var fd = new FormData();
        fd.append("file",file);

        console.log("start upload");
        var xhr = new XMLHttpRequest();
        xhr.open("POST","/upload",true);
        // xhr.overrideMimeType("application/octet-stream");
        xhr.send(fd);
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4) {
                console.log("onreadystatechange:"+xhr.status);
                if (xhr.status == 200) {

                    console.log("upload complete");
                    console.log("response: " + xhr.responseText);
                    var response = JSON.parse(xhr.responseText);
                    var code = response.code;
                    if(code == 0) {
                        var path = response.data;
                        addMusic(file.name,path,uplaoder);
                        form.reset();
                    } else {
                        alert("star faild");
                    }
                }
            }
        }
    } else {
        alert ("Please choose a file.");
    }
}

function addMusic(name,path,uploader) {
    var xmlhttp = new XMLHttpRequest();
    var url = "/add?"+"name="+name+"&path="+path+"&uploader="+uploader;

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            var response = JSON.parse(xmlhttp.responseText);
            var code = response.code;
            if(code == 0) {
                refreshPlayList();
            } else {
                alert("add faild");
            }
        }
    };
    xmlhttp.open("POST", url, true);
    xmlhttp.send();
}