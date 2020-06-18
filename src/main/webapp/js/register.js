$(function () {
    //点击注册按钮时发送请求
    $("#register_btn").click(function () {
        //因为按钮存在在表单中，所以点击按钮的时候需要阻止表单的默认行为
        window.event.returnValue = false;
        //发送ajax请求
        $.ajax({
            url: "/shop/registerresult",
            method: "POST",
            data: {
                "username": $("#username").val(),
                "password": $("#password").val(),
                "confirmPassword" :$("#confirmPassword").val(),
                "email" :$("#email").val(),
                "emailcode" :$("#emailcode").val(),
                "telephone" :$("#telephone").val()
            },
            success: function(response){
                console.log(response.errorCode);
                if(response.errorCode === "100"){
                    alert("恭喜，账号注册成功，请登录吧！");
                    window.location = "/shop/login";
                }else {
                    $("#errorMsg").text(response.objectMap.msg).css({"color":"red"});
                }
            },
            error: function () {
                /*$("#errorMsg").text("请求迷路了，小二正在赶来的路上，请稍后再试..").css({"color":"red"});*/
                alert("请求迷路了，小二正在赶来的路上，请稍后再试..");
                window.location = "/shop/register";
            }
        });
    });
});
