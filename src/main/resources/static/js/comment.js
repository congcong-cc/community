function post(){
    var parentId = $("input[type=hidden]").val();
    var content = $('#question-reply').val();
    if(!content){
        alert("内容不能为空!")
        return;
    }
    $.ajax({
        type: "POST",
        contentType:'application/json; charset=utf-8',
        url: "/comment",
        data: JSON.stringify({
            "parentId":parentId,
            "type":1,
            "content":content
        }),
        success:function (response){
            if(response.code==2000){
                window.location.reload();
            }else if(response.code==3000){
                var conf = confirm("用户未登录，是否登录？");
                if(conf){
                    window.open("https://github.com/login/oauth/authorize?client_id=759f8690db5883391e56&scope=user");
                    window.localStorage.setItem("visuable",true);
                }
            }else if(response.code==7001){
                alert("内容不能为空!");
            }else{
                alert(response.message);
            }
        },
        dataType: "json"
    });
}