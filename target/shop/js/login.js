/* 设置默认属性 */
$.validator.setDefaults({
	submitHandler: function() {
		form.submit();
	}
});
$(document).ready(function() {
	$('#form2').validate({
		rules: {
			username: "required",

			password: {
				required: true,
			},
			confirmlogo: "required",
		},
		messages: {
			username: "用户名输入不能为空",
			
			password: {
				required: "密码输入不能为空",
			},
			confirmlogo: "验证码输入不能为空",
		}
	});
});
$(function(){
	//点击登录按钮时，发送ajax请求
	$("#login_btn").click(function () {
		//因为按钮存在在表单中，所以点击按钮的时候需要阻止表单的默认行为
		window.event.returnValue = false;
		//发送ajax请求
		$.ajax({
			url: "/shop/loginconfirm",
			method:"POST",
			//通过id获取到data
			data: {
				//val() 方法返回或设置被选元素的 value 属性
				"username": $("#username").val(),
				"password": $("#password").val(),
				"confirmlogo": $("#confirmlogo").val()
			},
			//成功的话返回response
			success:function (response) {
				if(response.errorCode === "100"){
					//登录成功
					alert("恭喜，登录成功");
					window.location = "/shop/main";

				}else{
					//登录失败
					swal(response.objectMap.msg,"","error");
				}
			},
			//失败的话
			error: function () {
				swal("请求发送失败","","error");
			}
		});
	});
});