package ra.presentation.user;

import ra.presentation.admin.ProductPesentation;

import java.util.Scanner;

public class User {
    public static void AccountUser(){
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean isExit = true;
        do {
            System.out.println("******************WAREHOUSE MANAGEMENT****************\n" +
                    "1. Danh sách phiếu nhập theo trạng thái\n" +
                    "2. Tạo phiếu nhập\n" +
                    "3. Cập nhật phiếu nhập\n" +
                    "4. Tìm kiếm phiếu nhập\n" +
                    "5. Danh sách phiếu xuất theo trạng thái\n" +
                    "6. Tạo phiếu xuất\n" +
                    "7. Cập nhật phiếu xuất\n" +
                    "8. Tìm kiếm phiếu xuất\n" +
                    "9. Đăng xuất\n");
            System.out.println("Lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1:
                    ProductPesentation.productPesentation();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    isExit = false;
                    break;
                default:
                    System.err.println("Nhập từ 1-9");
            }
        }while (isExit);
    }
}
