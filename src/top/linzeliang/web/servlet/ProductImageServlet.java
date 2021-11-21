package top.linzeliang.web.servlet;

import top.linzeliang.dao.ProductImageDAO;
import top.linzeliang.domain.PageBean;
import top.linzeliang.domain.Product;
import top.linzeliang.domain.ProductImage;
import top.linzeliang.util.ImageUtil;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductImageServlet extends BaseBackServlet {
    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        InputStream inputStream = null;
        // 提交上传文件时的参数
        Map<String, String> params = new HashMap<>();
        // 解析上传
        inputStream = parseUpload(request, params);

        // 根据上传的参数生成productImage对象
        String type = params.get("type");
        int pid = Integer.parseInt(params.get("pid"));
        Product product = productService.getProductById(pid);

        ProductImage productImage = new ProductImage();
        productImage.setType(type);
        productImage.setProduct(product);

        // 添加productImage对象到数据库中
        productImageService.addProductImage(productImage);

        // 生成文件和文件夹
        String fileName = productImage.getId() + ".jpg";
        String imageFolder = null;
        String imageFolder_small = null;

        // 判断图片类型
        if (ProductImageDAO.type_single.equals(productImage.getType())) {
            imageFolder = request.getServletContext().getRealPath("img/productSingle");
            imageFolder_small = request.getServletContext().getRealPath("img/productSingleSmall");
        } else {
            imageFolder = request.getServletContext().getRealPath("img/productDetail");
        }
        // 创建文件
        File file = new File(imageFolder, fileName);
        // 同时创建文件夹(创建指定的目录，包括所有目录，如果有不存在的父目录则会被自动创建)
        file.getParentFile().mkdirs();

        // 复制文件到本地磁盘中
        try {
            if (null != inputStream && 0 != inputStream.available()) {
                try (
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                ) {
                    // 1MB大小
                    byte[] b = new byte[1024 * 1024];
                    int length = 0;
                    while ((length = inputStream.read(b)) != -1) {
                        fileOutputStream.write(b, 0, length);
                    }
                    // 清空缓存
                    fileOutputStream.flush();
                    // 将文件保存为jpg格式
                    BufferedImage img = ImageUtil.change2jpg(file);
                    ImageIO.write(img, "jpg", file);

                    // 缩略图
                    if (ProductImageDAO.type_single.equals(productImage.getType())) {
                        File image_single_small = new File(imageFolder_small, fileName);

                        image_single_small.getParentFile().mkdirs();

                        ImageUtil.resizeImage(file, 200, 200, image_single_small);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "@admin-productImage-list?pid=" + product.getId();
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int id = Integer.parseInt(request.getParameter("id"));
        ProductImage productImage = productImageService.getProductImageById(id);

        // 如果是单个图片
        if (ProductImageDAO.type_single.equals(productImage.getType())) {
            String imageFolder_single = request.getServletContext().getRealPath("img/productSingle");
            String imageFolder_single_small = request.getServletContext().getRealPath("img/productSingleSmall");
            // 获取文件
            File image_single = new File(imageFolder_single, productImage.getId() + ".jpg");
            File image_single_small = new File(imageFolder_single_small, productImage.getId() + ".jpg");
            // 删除
            image_single.delete();
            image_single_small.delete();
        } else {
            // 详细介绍图
            String imageFolder_detail = request.getServletContext().getRealPath("img/productDetail");
            // 获取文件
            File image_detail = new File(imageFolder_detail, productImage.getId() + ".jpg");
            // 删除
            image_detail.delete();
        }

        productImageService.deleteProductImage(id);

        return "@admin-productImage-list?pid=" + productImage.getProduct().getId();
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        return "";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        return "";
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int pid = Integer.parseInt(request.getParameter("pid"));
        Product product = productService.getProductById(pid);

        List<ProductImage> pisSingle = productImageService.findAllProductImage(product, "type_single");
        ProductImage productImageSingle = null;
        if (pisSingle.size() != 0) {
            productImageSingle = pisSingle.get(0);
        }
        List<ProductImage> productImageDetail = productImageService.findAllProductImage(product, "type_detail");

        request.setAttribute("product", product);
        request.setAttribute("productImageSingle", productImageSingle);
        request.setAttribute("productImageDetail", productImageDetail);

        return "admin/listProductImage.jsp";
    }
}
