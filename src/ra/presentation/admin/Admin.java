package ra.presentation.admin;

import ra.bussiness.ReceiptBussiness;

import java.util.Scanner;

public class Admin {
    public static void AccountAmin() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean isExit = true;
        do {
            System.out.println("******************WAREHOUSE MANAGEMENT****************\n" +
                    "1. Quản lý sản phẩm\n" +
                    "2. Quản lý nhân viên\n" +
                    "3. Quản lý tài khoản\n" +
                    "4. Quản lý phiếu nhập\n" +
                    "5. Quản lý phiếu xuất\n" +
                    "6. Quản lý báo cáo\n" +
                    "7. Đăng xuất");
            System.out.println("Lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1:
                    ProductPesentation.productPesentation();
                    break;
                case 2:
                    EmployeePresentation.employeePresentation();
                    break;
                case 3:
                    AccountPresentation.accountPresentation();
                    break;
                case 4:
                    ReceiptPresentation.receiptPresentation();
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    isExit = false;
                    break;
                default:
                    System.err.println("Nhập từ 1-7");
            }
        }while (isExit);
    }
}
