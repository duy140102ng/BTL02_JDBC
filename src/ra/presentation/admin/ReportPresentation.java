package ra.presentation.admin;

import ra.entity.Receipt;

import java.util.List;
import java.util.Scanner;

public class ReportPresentation {
    public static void reportPresentation() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean isExit = true;
        do {
            String blackBold = "\033[1;30m";
            String orangeText = "\033[1;33m";
            String reset = "\u001B[0m";

            System.out.println(blackBold + orangeText + "+------------------------------------------------------+");
            System.out.println(blackBold + orangeText + "|                    REPORT MANAGEMENT                 |" + reset);
            System.out.println(blackBold + orangeText + "|                                                      |" + reset);
            System.out.println(blackBold + orangeText + "|   1. Thống kê chi phí theo ngày, tháng, năm          |" + reset);
            System.out.println(blackBold + orangeText + "|   2. Thống kê chi phí theo khoảng thời gian          |" + reset);
            System.out.println(blackBold + orangeText + "|   3. Thống kê doanh thu theo ngày, tháng, năm        |" + reset);
            System.out.println(blackBold + orangeText + "|   4. Thống kê doanh thu theo khoảng thời gian        |" + reset);
            System.out.println(blackBold + orangeText + "|   5. Thống kê số nhân viên theo từng trạng thái      |" + reset);
            System.out.println(blackBold + orangeText + "|   6. Thống kê sản phẩm nhập nhiều nhất trong khoảng  |" + reset);
            System.out.println(blackBold + orangeText + "|      thời gian                                       |" + reset);
            System.out.println(blackBold + orangeText + "|   7. Thống kê sản phẩm nhập ít nhất trong khoảng     |" + reset);
            System.out.println(blackBold + orangeText + "|      thời gian                                       |" + reset);
            System.out.println(blackBold + orangeText + "|   8. Thống kê sản phẩm xuất nhiều nhất trong khoảng  |" + reset);
            System.out.println(blackBold + orangeText + "|      thời gian                                       |" + reset);
            System.out.println(blackBold + orangeText + "|   9. Thống kê sản phẩm xuất ít nhất trong khoảng     |" + reset);
            System.out.println(blackBold + orangeText + "|      thời gian                                       |" + reset);
            System.out.println(blackBold + orangeText + "|   10. Thoát                                          |" + reset);
            System.out.println(blackBold + orangeText + "|                                                      |" + reset);
            System.out.println(blackBold + orangeText + "+------------------------------------------------------+");
            try {
                System.out.println("Lựa chọn của bạn: ");
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception ex) {
                System.err.println("Có lỗi: " + ex);
            }
            switch (choice) {
                case 1:
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
                    break;
                case 10:
                    isExit = false;
                    break;
                default:
                    System.err.println("Nhập từ 1-10");
            }
        } while (isExit);
    }
}
