<%@ page import="top.linzeliang.domain.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<title>用户中心</title>

<script src="js/data/provinces.js"></script>
<script>
    $(function () {
        $("div.settingselect ol li").click(function () {
            $("div.settingselect ol li").each(function () {
                $(this).css("backgroundColor", "#bfbfbf");
            });
            $("div.bg-setting").each(function () {
                $(this).hide();
            });
            $(this).css("backgroundColor", "#f6f6f6");
            var show = $(this).attr("show");
            $("." + show).show();
        });

        // 自动填充省份
        var province = $("#province");
        $(provinceAndCityArray).each(function () {
            var option = "<option value='" + this.provinceName + "'>" + this.provinceName + "</option>";
            province.append(option);
        });

        // 二级联动
        var city = $("#city");
        $("#province").change(function () {
            var cityName = $("#province option:selected").val();

            // 先把原有的option数据删除掉
            $("#city option").each(function () {
                $(this).remove();
            });
            city.append("<option value='' selected>------</option>");

            // 填充市数据
            $(provinceAndCityArray).each(function () {
                if (this.provinceName == cityName) {
                    $(this.citys).each(function () {
                        var option = "<option value='" + this.citysName + "'>" + this.citysName + "</option>";
                        city.append(option);
                    });
                }
            });
        });

        // 回写地址数据
        var p = $("option.province-default").val();
        var c = $("option.city-default").val();
        $("#province option").each(function () {
            if (p == $(this).val()) {
                $(this).attr("selected", "selected");

                // 然后填充市数据
                $(provinceAndCityArray).each(function () {
                    if (this.provinceName == p) {
                        $(this.citys).each(function () {
                            var option = "<option value='" + this.citysName + "'>" + this.citysName + "</option>";
                            city.append(option);
                        });
                    }
                });
            }
        });
        $("#city option").each(function () {
            if (c == $(this).val()) {
                $(this).attr("selected", "selected");
            }
        });

        // 聚焦取消的时候就通过ajax检查原密码是否正确
        $("#oldpassword").blur(function () {
            var urlPage = "forecheckUserLogin";
            var oldpassword = $("#oldpassword").val();
            $.post(
                urlPage,
                {"oldpassword":oldpassword},
                res = function (result) {
                    if ("fail" == result) {
                        $("#oldpassword").attr("result", "error")
                    } else {
                        $("#oldpassword").removeAttr("result");
                    }
                }
            );
        });

        // 修改密码
        $("#passwordForm").submit(function () {
            if (0 == $("#oldpassword").val().length) {
                $("div.errorMsg").text("原密码不能为空");
                $("div.errorMsg").show();
                return false;
            } else {
                $("div.errorMsg").hide();
            }

            if ("error" == $("#oldpassword").attr("result")) {
                $("div.errorMsg").text("原密码错误");
                $("div.errorMsg").show();
                return false;
            } else {
                $("div.errorMsg").hide();
            }

            if (0 == $("#password").val().length) {
                $("div.errorMsg").text("请输入新密码");
                $("div.errorMsg").show();
                return false;
            } else {
                $("div.errorMsg").hide();
            }

            if (0 == $("#repeatpassword").val().length) {
                $("div.errorMsg").text("请输入重复密码");
                $("div.errorMsg").show();
                return false;
            } else {
                $("div.errorMsg").hide();
            }

            if (6 > $("#password").val().length) {
                $("div.errorMsg").text("新密码至少6位");
                $("div.errorMsg").show();
                return false;
            } else {
                $("div.errorMsg").hide();
            }

            if ($("#password").val() != $("#repeatpassword").val()) {
                $("div.errorMsg").text("重复密码不正确");
                $("div.errorMsg").show();
                return false;
            } else {
                $("div.errorMsg").hide();
            }
        });
    });
</script>

<!-- 内容S -->
<article>
    <div class="container">
        <div aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${contextPath}">主页</a></li>
                <li aria-current="page" class="breadcrumb-item active">用户中心：<span style="color: skyblue; font-weight: bold">${user.name}</span></li>
            </ol>
        </div>

        <div class="settings">
            <div class="settingselect">
                <ol>
                    <li show="baseinfo" style="background-color: #f6f6f6">基本信息</li>
                    <li show="userinfosetting">修改个人信息</li>
                    <li show="passwordsetting">修改密码</li>
                </ol>
            </div>
            <div class="bg-setting baseinfo" style="height: 400px;">
                <table style="margin-left: 130px; margin-top: 0px">
                    <tr>
                        <td>账号:</td>
                        <td class="card">${user.name}</td>
                        <td>余额:</td>
                        <td class="card">
                            <fmt:formatNumber value="${user.balance}" minFractionDigits="2"/> 元
                        </td>
                    </tr>
                    <tr>
                        <td>手机号:</td>
                        <td class="card">${user.mobile}</td>
                        <td>邮箱:</td>
                        <td class="card">${user.mail}</td>
                    </tr>
                    <tr>
                        <td>QQ:</td>
                        <td class="card">${user.qq}</td>
                        <td>地址:</td>
                        <td class="card" style="font-size: 14px">
                            ${user.address}
                        </td>
                    </tr>
                    <tr>
                        <td>性别:</td>
                        <td class="card">
                            <c:if test="${1 == user.gender}">男</c:if>
                            <c:if test="${0 == user.gender}">女</c:if>
                        </td>
                        <td>充值:</td>
                        <td>
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#topUp">+</button>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="bg-setting userinfosetting" style="height: 400px; display: none;">
                <form action="foreuserinfochange" method="post" style="margin-left: 100px; margin-top: 20px">
                    <table>
                        <tr>
                            <td>账号:</td>
                            <td><input type="text" value="${user.name}" disabled class="form-control"
                                       readonly="readonly"></td>
                            <td>性别:</td>
                            <td style="text-align: left">
                                &nbsp;&nbsp;
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="gender" id="male"
                                           value="1" <c:if test="${1 == user.gender}">checked</c:if>>
                                    <label class="form-check-label" for="male">男</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="gender" id="female"
                                           value="0" <c:if test="${0 == user.gender}">checked</c:if>>
                                    <label class="form-check-label" for="female">女</label>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>手机号:</td>
                            <td><input name="mobile" type="tel" class="form-control" value="${user.mobile}"></td>
                            <td>邮箱:</td>
                            <td><input name="mail" type="email" class="form-control" value="${user.mail}"></td>
                        </tr>
                        <tr>
                            <td>QQ:</td>
                            <td><input name="qq" type="text" class="form-control" value="${user.qq}"></td>
                            <td>地址:</td>
                            <td style="text-align: left">
                                <div class="col-auto my-1" style="width: 200px; padding: 0 !important; display: inline">
                                    <select name="province" id="province" class="custom-select mr-sm-2"
                                            style="font-size: 16px; float: left; width: 108px; display: inline">
                                        <option class="province-default" value="${user.province}" selected>------</option>
                                    </select>

                                    <select name="city" id="city" class="custom-select mr-sm-2"
                                            style="font-size: 16px; float: right; width: 108px; display: inline-block;  margin: 0 !important;">
                                        <option class="city-default" value="${user.city}" selected>------</option>
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4"><input type="submit" value="更 新" class="btn btn-secondary"></td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="bg-setting passwordsetting" style="height: 400px; display: none; padding-top: 25px">
                <form action="foreuserpasswordchange" method="post" style="padding-left: 236px" id="passwordForm">
                    <table>
                        <tr>
                            <td>账号:</td>
                            <td><input type="text" value="${user.name}" disabled class="form-control"
                                       readonly="readonly"></td>
                        </tr>
                        <tr>
                            <td>旧密码:</td>
                            <td><input type="password" class="form-control" name="oldpassword" id="oldpassword"></td>
                        </tr>
                        <tr>
                            <td>新密码:</td>
                            <td><input type="password" class="form-control" name="password" id="password"></td>
                        </tr>
                        <tr>
                            <td>重复密码:</td>
                            <td><input type="password" class="form-control" name="repeatpassword" id="repeatpassword"></td>
                        </tr>
                        <tr>
                            <td colspan="2"><input type="submit" value="修 改" class="btn btn-secondary"></td>
                        </tr>
                    </table>
                    <div class="alert alert-danger errorMsg" role="alert" style="width: 320px !important; margin-top: 10px; height: 40px; padding-top: 5px; text-align: center; display: none;">
                    </div>
                </form>
            </div>
        </div>
    </div>
</article>
<!-- 内容E -->

<!-- 充值模态界面S -->
<div class="modal fade" id="topUp" tabindex="-1" aria-labelledby="topUp" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">充值（请备注账号）</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body text-center" style="padding-bottom: 30px">
                <img src="img/tooUp/zhifubao.jpg" width="250px" height="250px">
            </div>
        </div>
    </div>
</div>
<!-- 充值模态界面E -->