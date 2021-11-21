<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<title>产品图片管理</title>

<script>
    $(function () {
        $(".addFormSingle").submit(function () {
            if (checkEmpty("filepathSingle", "图片文件")) {
                $("#filepathSingle").value("");
                return true;
            }
            return false;
        });
        $(".addFormDetail").submit(function () {
            if (checkEmpty("filepathDetail", "图片文件")) {
                return true;
            }
            return false;
        });
        $("table.addPictureTable tr.submitTR button.disabled").click(function () {
            return false;
        });
    });
</script>

<!-- 内容S -->
<article>
    <div class="container">
        <div aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${contextPath}">主页</a></li>
                <li class="breadcrumb-item"><a href="forerelease">我发布的</a></li>
                <li aria-current="page" class="breadcrumb-item active">更新产品图片：${product.name}</li>
            </ol>
        </div>

        <div>
            <div style="min-height: 500px; width: 100%; background-color: #b4d6e3; padding: 10px 10px 60px 10px;">
                <table align="center">
                    <tr style="text-align: center">
                        <td style=" display: inline-block; float: left; margin-right: 110px; margin-left: 90px; margin-top: 70px;">
                            <div>
                                <div style="margin-bottom: 16px; width: 400px">
                                    <div style="text-align: center; background-color: #ffddd2; height: 50px;
                                     padding-top: 12px; border-top-left-radius: 6px; border-top-right-radius: 6px;">
                                        产品<b style="font-weight: bold; color: skyblue"> 预览 </b>图片
                                    </div>
                                    <div style="background-color: #efefef; height: 150px; border-bottom-left-radius: 6px;
                                     border-bottom-right-radius: 6px;">
                                        <form method="post" class="addFormSingle" action="foreconfirmAddProductImage" enctype="multipart/form-data">
                                            <table style="text-align: center; display: inline-block">
                                                <tr>
                                                    <td style="font-size: 16px; margin-bottom: 10px; margin-top: 10px; display: inline-block">请选择本地图片 <span style="color: #ff6700; font-weight: bold">尺寸350X350</span> 为佳</td>
                                                </tr>
                                                <tr>
                                                    <td style="font-size: 16px; margin-bottom: 10px; display: inline-block; margin-left: 84px">
                                                        <input id="filepathSingle" type="file" name="filepath" accept="image/*" <c:if test="${!empty productImageSingle}">disabled ="disabled"</c:if> style="font-size: 14px; <c:if test="${!empty productImageSingle}">cursor: no-drop</c:if>"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td align="center">
                                                        <input type="hidden" name="type" value="type_single"/>
                                                        <input type="hidden" name="pid" value="${product.id}"/>
                                                        <button type="submit" style="width: 70px; height: 40px; margin-top: 10px; padding-top: 5px;
                                                        <c:if test="${!empty productImageSingle}">cursor: no-drop</c:if>"
                                                                class="btn btn-info" <c:if test="${!empty productImageSingle}">disabled ="disabled"</c:if>>
                                                            添 加
                                                        </button>
                                                    </td>
                                                </tr>
                                            </table>
                                        </form>
                                    </div>
                                </div>
                                <table>
                                    <thead style="height: 30px; display: block; font-size: 16px; padding-top: 4px">
                                    <tr>
                                        <th style="height: 30px; display: inline-block; width: 100px; border: 1px solid #ececec; background-color: #dff0d8">ID</th>
                                        <th style="height: 30px; display: inline-block; width: 200px; border: 1px solid #ececec; background-color: #dff0d8">产品预览图</th>
                                        <th style="height: 30px; display: inline-block; width: 100px; border: 1px solid #ececec; background-color: #dff0d8">删除</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:if test="${!empty productImageSingle}">
                                        <tr style="height: 70px!important; background-color: #eeeeee">
                                            <td style="width: 100px; display: inline-block; vertical-align: middle; padding-top: 5px">${productImageSingle.id}</td>
                                            <td style="width: 200px; display: inline-block; vertical-align: middle; display: inline-block; margin-top: 10px;">
                                                <a title="点击查看原图" href="img/productSingle/${productImageSingle.id}.jpg" target="_blank">
                                                    <img height="50px" width="50px" src="img/productSingleSmall/${productImageSingle.id}.jpg">
                                                </a>
                                            </td>
                                            <td style="width: 100px; display: inline-block; vertical-align: middle; padding-top: 5px">
                                                <a deleteLink="true" href="foreconfirmDeleteProductImage?id=${productImageSingle.id}">
                                                    <span style="color: red">x</span>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:if>
                                    </tbody>
                                </table>
                            </div>
                        </td>
                        <td  style=" display: inline-block; margin-top: 70px;  float: right; margin-right: 70px">
                            <div>
                                <div style="margin-bottom: 16px; width: 400px">
                                    <div  style="text-align: center; background-color: #ffddd2; height: 50px;
                                     padding-top: 12px; border-top-left-radius: 6px; border-top-right-radius: 6px;">
                                        产品<b style="font-weight: bold; color: skyblue"> 详情 </b>图片
                                    </div>
                                    <div style="background-color: #efefef; height: 150px; border-bottom-left-radius: 6px;
                                     border-bottom-right-radius: 6px;">
                                        <form method="post" class="addFormDetail" action="foreconfirmAddProductImage" enctype="multipart/form-data">
                                            <table style="text-align: center; display: inline-block">
                                                <tr>
                                                    <td style="font-size: 16px; margin-bottom: 10px; margin-top: 10px; display: inline-block">请选择本地图片 <span style="color: #ff6700; font-weight: bold">宽度790</span> 为佳</td>
                                                </tr>
                                                <tr>
                                                    <td style="font-size: 16px; margin-bottom: 10px; display: inline-block; margin-left: 84px">
                                                        <input id="filepathDetail" type="file" name="filepath"
                                                               accept="image/*" style="font-size: 14px; margin-left: 10px"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td align="center">
                                                        <input type="hidden" name="type" value="type_detail"/>
                                                        <input type="hidden" name="pid" value="${product.id}"/>
                                                        <button type="submit" class="btn btn-warning" style="width: 70px; height: 40px;
                                                        margin-top: 10px; padding-top: 5px; color: #4e4e4e">添 加</button>
                                                    </td>
                                                </tr>
                                            </table>
                                        </form>
                                    </div>
                                </div>
                                <table>
                                    <thead style="height: 30px; display: block; font-size: 16px; padding-top: 4px">
                                    <tr>
                                        <th style="height: 30px; display: inline-block; width: 100px; border: 1px solid #ececec; background-color: #dff0d8">ID</th>
                                        <th style="height: 30px; display: inline-block; width: 200px; border: 1px solid #ececec; background-color: #dff0d8">产品详情图</th>
                                        <th style="height: 30px; display: inline-block; width: 100px; border: 1px solid #ececec; background-color: #dff0d8">删除</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${productImageDetail}" var="pi">
                                        <tr style="height: 70px!important; background-color: #eeeeee">
                                            <td style="width: 100px; display: inline-block; vertical-align: middle; padding-top: 5px">${pi.id}</td>
                                            <td style="width: 200px; display: inline-block; vertical-align: middle; display: inline-block; margin-top: 10px;">
                                                <a title="点击查看原图" href="img/productDetail/${pi.id}.jpg" target="_blank">
                                                    <img height="50px" src="img/productDetail/${pi.id}.jpg">
                                                </a>
                                            </td>
                                            <td style="width: 100px; display: inline-block; vertical-align: middle; padding-top: 5px">
                                                <a deleteLink="true" href="foreconfirmDeleteProductImage?id=${pi.id}">
                                                    <span style="color: red">x</span>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</article>
<!-- 内容E -->