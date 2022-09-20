function post(){
    var parentId = $("input[type=hidden]").val();
    var content = $('#question-reply').val();
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
            console.log(response);
            if(response.code==2000){
                $('.question-reply').hide();
            }else if(response.code==3000){
                var conf = confirm("用户未登录，是否登录？");
                if(conf){
                    window.open("https://github.com/login/oauth/authorize?client_id=759f8690db5883391e56&scope=user");
                    window.localStorage.setItem("visuable",true);
                }
            }else{
                alert(response.message);
            }
        },
        dataType: "json"
    });
}