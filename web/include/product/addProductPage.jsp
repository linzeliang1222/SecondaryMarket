<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<title>添加商品</title>

<script>
    function checkEmpty(id, name) {
        var value = $("#" + id).val();
        if (value.length == 0) {
            alert(name + "不能为空");
            $("#" + id)[0].focus();
            return false;
        }
        return true;
    }

    function checkNumber(id, name) {
        var value = $("#" + id).val();
        if (value.length == 0) {
            alert(name + "不能为空");
            $("#" + id)[0].focus();
            return false;
        }
        if (isNaN(value)) {
            alert(name + "必须是数字");
            $("#" + id)[0].focus();
            return false;
        }
        return true;
    }

    function checkInt(id, name) {
        var value = $("#" + id).val();
        if (value.length == 0) {
            alert(name + "不能为空");
            $("#" + id)[0].focus();
            return false;
        }
        if (parseInt(value) != value) {
            alert(name + "必须是整数");
            $("#" + id)[0].focus();
            return false;
        }
        return true;
    }

    $(function () {
        $(".addProductForm").submit(function () {
            if (!checkEmpty("name", "产品名称")) {
                return false;
            }
            if (!checkNumber("originalPrice", "原价格")) {
                return false;
            }
            if (!checkNumber("promotePrice", "优惠价格")) {
                return false;
            }
            if (!checkEmpty("description", "产品描述")) {
                return  false;
            }
            if (!checkEmpty("simpleImage", "图片文件")) {
                return false;
            }
            return true;
        });
    });
</script>

<!-- 内容S -->
<article>
    <div class="container">
        <div aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${contextPath}">主页</a></li>
                <li aria-current="page" class="breadcrumb-item active">发布产品</li>
            </ol>
        </div>

        <div>
            <div style="height: 500px; width: 100%; background-color: #b4d6e3; padding: 10px">
                <form action="foreconfirmAddProduct" method="post" class="addProductForm" enctype="multipart/form-data">
                    <table style="margin-left: 200px; margin-top: 50px">
                        <tr style="height: 60px;">
                            <td style="width: 100px">产品名：</td>
                            <td>
                                <input name="name" id="name" type="text" style="width: 180px" class="form-control" placeholder="商品名称">
                            </td>
                            <td style="width: 100px;">
                                所属分类：
                            </td>
                            <td>
                                <select name="cid" id="category" class="custom-select mr-sm-2" style="width: 180px !important; font-size: 16px; float: left; width: 108px; display: inline">
                                    <c:forEach items="${categorys}" var="c">
                                        <option value="${c.id}">${c.name}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr style="height: 60px;">
                            <td style="width: 100px">原价：</td>
                            <td>
                                <input type="text" class="form-control" style="width: 180px; margin-right: 90px;"
                                       placeholder="原价格" name="originalPrice" id="originalPrice">
                            </td>
                            <td style="width: 100px;">
                                优惠价：
                            </td>
                            <td>
                                <input type="text" class="form-control" style="width: 180px" placeholder="促销价"
                                       name="promotePrice" id="promotePrice">
                            </td>
                        </tr>
                        <tr style="height: 100px;">
                            <td style="width: 100px; padding-top: 0 !important;">
                                产品描述：
                            </td>
                            <td colspan="3"><textarea
                                    style="width: 550px; height: 100px; resize: none; display: inline-block; margin-top: 8px"
                                    class="form-control" placeholder="写一些关于商品的描述" name="description"
                                    id="description"></textarea></td>
                        </tr>
                        <tr style="height: 60px;">
                            <td colspan="1" style="width: 100px">预览图片：</td>
                            <td colspan="3" style="width: 60px;">
                                <div class="" style="width: 100%">
                                    <input type="file" name="simpleImage" id="simpleImage" accept="image/*">
                                </div>
                            </td>
                        </tr>
                        <tr>
                           <td colspan="4"><button type="submit" class="btn btn-info" style="margin-left: 295px; margin-top: 20px">发布</button></td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
</article>
<!-- 内容E -->