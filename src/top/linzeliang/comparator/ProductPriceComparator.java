package top.linzeliang.comparator;

import top.linzeliang.domain.Product;

import java.util.Comparator;

/**
 * @Description: 产品价格比较器
 * @Author: LinZeLiang
 * @Date: 2021-02-14
 */
public class ProductPriceComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        // 升序，价格从低到高
        if (o1.getPromotePrice() - o2.getPromotePrice() > 0) {
            return 1;
        } else if (o1.getPromotePrice() - o2.getPromotePrice() < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
