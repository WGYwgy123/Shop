$(document).ready(function (){
    var addresId;
    $("[name='changeAddr']").click(function (){
        $("#update-addr").modal({
            backdrop:'static'
        });

        $("#name").val($(this).parents("#parent").find("#conname").text());
        $("#telephone").val($(this).parents("#parent").find("#contel").text());
        $("#detailaddress").val($(this).parents("#parent").find("#detailaddr").text());
         addresId=$(this).parents("#parent").find("#table").attr("address-id");

    });


    $("#saveAddr").click(function (){
        var saveAddr={};
        saveAddr.addressid=addresId;
        saveAddr.province=$("#provinceUpdate").val();
        saveAddr.city=$("#cityUpdate").val();
        saveAddr.county=$("#countyUpdate").val();
        saveAddr.detailaddr=$("#detailaddress").val();
        saveAddr.conname=$("#name").val();
        saveAddr.contel=$("#telephone").val();

        $.ajax({
            type: "POST",
            url: "/shop/saveAddr",
            contentType:"application/x-www-form-urlencoded; charset=utf-8",
            data:saveAddr,
            dateType:"json",
            success: function(response){
                if(response.errorCode === "100"){
                    //保存成功
                    $("#update-info").modal('hide');
                    swal("修改成功", "", "success");
                    $("button").click(function (){
                        location.reload();
                    });
                }else{
                    swal(response.objectMap.msg);
                }
            },
            error:function (){
                alert("更新失败");
            }
        });
    });

    $("[name='deleteAddr']").click(function (){
        addresId=$(this).parents("#parent").find("#table").attr("address-id");
        var address={};
        address.addressid=addresId;
        $.ajax({
            type: "POST",
            url: "/shop/deleteAddr",
            contentType:"application/x-www-form-urlencoded; charset=utf-8",
            data:address,
            dateType:"json",
            success:function (response){
                swal("删除成功", "", "success");
                $("button").click(function (){
                    location.reload();
                });
            },
            error:function (){
                swal("删除失败","","error");
            }
            });
    });

    $("[name='insertAddr']").click(function () {
        $("#insert-addr").modal({
            backdrop:'static'
        });
    });

    $("[name='returnOrder']").click(function () {
        window.location="/shop/order";
    });

    $("[name='returnMain']").click(function () {
        window.location="/shop/main";
    });

    $("#insertAddr").click(function (){
        var insertAddr={};
        insertAddr.addressid={};
        insertAddr.userid={};
        insertAddr.province=$("#provinceInsert").val();
        insertAddr.city=$("#cityInsert").val();
        insertAddr.county=$("#countyInsert").val();
        insertAddr.detailaddr=$("#detailaddressInsert").val();
        insertAddr.conname=$("#nameInsert").val();
        insertAddr.contel=$("#telephoneInsert").val();
        $.ajax({
           type:"POST",
           url:"/shop/insertAddr",
           contentType:"application/x-www-form-urlencoded; charset=utf-8",
           data:insertAddr,
           dataType:"json",
           success:function (response){
               swal("添加成功", "", "success");
               $("button").click(function (){
                   location.reload();
               });
           },
           error:function (){
               swal("添加失败");
           }
       });

    });
});