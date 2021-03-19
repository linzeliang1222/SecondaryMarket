<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    function switchLoginModal() {
        $("button.loginModal").click();
    };

    function switchDeleteModal() {
        $("button.deleteModal").click();
    };

    $(function () {
        $("button.loginAjax").click(function () {
            var name = $("#nameAjax").val();
            var password = $("#passwordAjax").val();

            if (0 == name.length || 0 == password.length) {
                $("span.errorMessage").html("请输入账号和密码");
                $("div.loginErrorMessageDiv").show();
                return false;
            }

            var urlPage = "foreloginAjax";
            $.post(
                urlPage,
                {"name":name, "password":password},
                function (result) {
                    if ("success" == result) {
                        location.reload();
                    } else if ("forbidden" == result) {
                        $("span.errorMessage").html("该账号已禁用");
                        $("div.loginErrorMessageDiv").show();
                    } else {
                        $("span.errorMessage").html("账号或密码错误");
                        $("div.loginErrorMessageDiv").show();
                    }
                }
            );

            return true;
        });
    });
</script>

<!-- 登录模态框 -->
<!-- Button trigger modal -->
<button type="button" class="btn btn-primary loginModal" data-toggle="modal" data-target="#exampleModal" style="display: none;"></button>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 400px">
        <div class="modal-content" style="text-align: center">
            <div class="modal-header" style="padding-left: 170px">
                <h4 class="modal-title" id="exampleModalLabel">登 录</h4>
            </div>
            <div class="modal-body">
                <div class="input-group input-group-sm mb-3" style="width: 220px; margin-left: 70px">
                    <input type="text" class="form-control" aria-label="Sizing example input"
                           aria-describedby="inputGroup-sizing-sm" placeholder="账号" id="nameAjax">
                </div>
                <div class="input-group input-group-sm mb-3" style="width: 220px; margin-left: 70px">
                    <input type="password" class="form-control" aria-label="Sizing example input"
                           aria-describedby="inputGroup-sizing-sm" placeholder="密码" id="passwordAjax">
                </div>
                <div class="loginErrorMessageDiv" style="width: 220px; margin-left: 70px; display: none;">
                    <div class="alert alert-danger" style="font-size: 14px">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
                        <span class="errorMessage"></span>
                    </div>
                </div>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">关 闭</button>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <button type="button" class="btn btn-primary loginAjax">登 录</button>
            </div>
        </div>
    </div>
</div>

<!-- 确定删除模态框 -->
<!-- Button trigger modal -->
<button type="button" class="btn btn-primary deleteModal" style="display: none" data-toggle="modal" data-target="#deleteModalLabel"></button>

<!-- Modal -->
<div class="modal fade" id="deleteModalLabel" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 400px;">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabel">确认删除嘛？</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary hideModal" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary confirmdelete">确定</button>
            </div>
        </div>
    </div>
</div>