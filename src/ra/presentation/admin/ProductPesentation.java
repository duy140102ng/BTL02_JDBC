package ra.presentation.admin;

import ra.bussiness.IBussiness;
import ra.bussiness.ProductBussiness;
import ra.entity.Product;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductPesentation {
    private static ProductBussiness productBussiness = new ProductBussiness();

    public static void productPesentation() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean isExit = true;
        do {
            String blackBold = "\033[1;30m";
            String blueText = "\033[1;34m";
            String reset = "\u001B[0m";

            System.out.println(blackBold + blueText + "+------------------------------------------------------+" + reset);
            System.out.println(blackBold + blueText + "|                    PRODUCT MANAGEMENT                |" + reset);
            System.out.println(blackBold + blueText + "|                                                      |" + reset);
            System.out.println(blackBold + blueText + "|   1. Danh sách sản phẩm                              |" + reset);
            System.out.println(blackBold + blueText + "|   2. Thêm mới sản phẩm                               |" + reset);
            System.out.println(blackBold + blueText + "|   3. Cập nhật sản phẩm                               |" + reset);
            System.out.println(blackBold + blueText + "|   4. Tìm kiếm sản phẩm                               |" + reset);
            System.out.println(blackBold + blueText + "|   5. Cập nhật trạng thái sản phẩm                    |" + reset);
            System.out.println(blackBold + blueText + "|   6. Thoát                                           |" + reset);
            System.out.println(blackBold + blueText + "|                                                      |" + reset);
            System.out.println(blackBold + blueText + "+------------------------------------------------------+" + reset);
            try {
                System.out.println("Lựa chọn của bạn: ");
                choice = Integer.parseInt(scanner.nextLine());
            }catch (Exception ex){
                System.err.println("Có lỗi: " +ex);
            }
            switch (choice) {
                case 1:
                    List<Product> listProduct = productBussiness.findAll();
                    listProduct.stream().forEach(product -> product.displayData());
                    break;
                case 2:
                    Product product = new Product();
                    product.inputData(scanner);
                    boolean resultCreate = productBussiness.create(product);
                    if (resultCreate) {
                        System.out.println("Thêm thành công");
                    } else {
                        System.err.println("Thêm thất bại");
                    }
                    break;
                case 3:
                    System.out.println("Nhập mã sản phẩm cần cập nhật: ");
                    String productId = scanner.nextLine();
                    Product productUpdate = new Product();
                    productUpdate.setProductId(productId);
                    productUpdate.inputDataUpdate(scanner);
                    boolean ressultUpdate = productBussiness.update(productUpdate);
                    if (ressultUpdate) {
                        System.out.println("Cập nhật thành công");
                    } else {
                        System.err.println("Không tồn tại mã sản phẩm");
                    }
                    break;
                case 4:
                    System.out.println("Nhập tên sản phẩm cần tìm: ");
                    String productName = scanner.nextLine().trim();
                    if (!productName.isEmpty()) {
                        System.out.println("Danh sách sản phẩm");
                        List<Product> listProductByName = productBussiness.findByName(productName);
                        if (!listProductByName.isEmpty()){
                            listProductByName.forEach(product1 -> product1.displayData());
                        }else {
                            System.err.println("Không tìm thấy tên sản phẩm");
                        }
                    } else {
                        System.err.println("Không tìm thấy tên sản phẩm");
                    }
                    break;
                case 5:
                    System.out.println("Nhập mã sản phẩm cần cập nhật: ");
                    String productIdUpdateStatus = scanner.nextLine();
                    Product productUpdateStatus = new Product();
                    productUpdateStatus.setProductId(productIdUpdateStatus);
                    boolean resultUpdateStatus = productBussiness.updateStatus(productUpdateStatus);
                    if (!resultUpdateStatus) {
                        System.err.println("Không tồn tại mã sản phẩm");
                    } else {
                        System.out.println("Cập nhật thành công");
                    }
                    break;
                case 6:
                    isExit = false;
                    break;
                default:
                    System.err.println("Nhập từ 1-6");
            }
        } while (isExit);
    }
}
