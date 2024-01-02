package ra.presentation.admin;

import ra.bussiness.ReceiptBussiness;

import java.util.Scanner;

public class Admin {
    public static void AccountAdmin() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean isExit = true;
        do {
            String reset = "\u001B[0m";
            String yellowBold = "\033[1;33m";
            System.out.println(yellowBold + "+------------------------------------------------------+" + reset);
            System.out.println(yellowBold + "|                  WAREHOUSE MANAGEMENT                |" + reset);
            System.out.println(yellowBold + "| 1. Quản lý sản phẩm                                  |");
            System.out.println(yellowBold + "| 2. Quản lý nhân viên                                 |");
            System.out.println(yellowBold + "| 3. Quản lý tài khoản                                 |");
            System.out.println(yellowBold + "| 4. Quản lý phiếu nhập                                |");
            System.out.println(yellowBold + "| 5. Quản lý phiếu xuất                                |");
            System.out.println(yellowBold + "| 6. Quản lý báo cáo                                   |");
            System.out.println(yellowBold + "| 7. Đăng xuất                                         |");
            System.out.println(yellowBold + "+------------------------------------------------------+" + reset);
            System.out.print("Lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
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
                    BillPrensentation.billPresentation();
                    break;
                case 6:
                    ReportPresentation.reportPresentation();
                    break;
                case 7:
                    isExit = false;
                    break;
                default:
                    System.err.println("Nhập từ 1-7");
            }
        } while (isExit);
    }
}
