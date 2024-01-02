package ra.presentation.user;

import ra.bussiness.BillBussiness;
import ra.entity.Bill;
import ra.presentation.admin.ProductPesentation;

import java.util.List;
import java.util.Scanner;

public class UserPresentation {
    private static UserBussiness userBussiness = new UserBussiness();
    public static void AccountUser(){
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean isExit = true;
        do {
            String blackBold = "\033[1;30m";
            String redText = "\033[1;31m";
            String reset = "\u001B[0m";

            System.out.println(blackBold + redText + "+---------------------------------------------------------+");
            System.out.println(blackBold + redText + "|                     WAREHOUSE MANAGEMENT                |" + reset);
            System.out.println(blackBold + redText + "| " + "1. Danh sách phiếu nhập theo trạng thái                 |" + reset);
            System.out.println(blackBold + redText + "| " + "2. Tạo phiếu nhập                                       |" + reset);
            System.out.println(blackBold + redText + "| " + "3. Cập nhật phiếu nhập                                  |" + reset);
            System.out.println(blackBold + redText + "| " + "4. Tìm kiếm phiếu nhập                                  |" + reset);
            System.out.println(blackBold + redText + "| " + "5. Danh sách phiếu xuất theo trạng thái                 |" + reset);
            System.out.println(blackBold + redText + "| " + "6. Tạo phiếu xuất                                       |" + reset);
            System.out.println(blackBold + redText + "| " + "7. Cập nhật phiếu xuất                                  |" + reset);
            System.out.println(blackBold + redText + "| " + "8. Tìm kiếm phiếu xuất                                  |" + reset);
            System.out.println(blackBold + redText + "| " + "9. Đăng xuất                                            |" + reset);
            System.out.println(blackBold + redText + "+---------------------------------------------------------+");
            System.out.println("Lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1:
                    System.out.println("Chọn trạng thái để hiển thị:");
                    System.out.println("0. Tạo");
                    System.out.println("1. Hủy");
                    System.out.println("2. Duyệt");
                    System.out.print("Lựa chọn của bạn: ");
                    choice = Integer.parseInt(scanner.nextLine());
                    int billStatus = -1;
                    switch (choice) {
                        case 0:
                            billStatus = 0;
                            break;
                        case 1:
                            billStatus = 1;
                            break;
                        case 2:
                            billStatus = 2;
                            break;
                        default:
                            System.err.println("lựa chọn không hợp lệ, vui lòng nhập lại!");
                    }
                    if (billStatus != -1){
                        List<User> userList = userBussiness.findAll();
                        userList.stream().forEach(user -> user.displayData());
                    }
                    break;
                case 2:
                    User user = new User();
                    user.inputData(scanner);
                    boolean resultCreate = userBussiness.create(user);
                    if (resultCreate) {
                        System.out.println("Tạo phiếu nhập thành công");
                    } else {
                        System.err.println("Tao phiếu nhập thất bại");
                    }
                    break;
                case 3:
                    System.out.println("Nhập mã phiếu nhập hoặc mã code cần cập nhật: ");
                    String billId = scanner.nextLine();
                    if (!billId.isEmpty()) {
                        long billIdUpdate = Long.parseLong(billId);
                        User billUpdate = new User();
                        billUpdate.setBillId(billIdUpdate);
                        billUpdate.inputDataUpdate(scanner);
                        boolean ressultUpdate = userBussiness.update(billUpdate);
                        if (ressultUpdate) {
                            System.out.println("Cập nhật thành công");
                        } else {
                            System.err.println("Không tồn tại mã phiếu nhập hoặc mã code");
                        }
                    } else {
                        System.err.println("Mời bạn nhập mã phiếu nhập hoặc mã code cần cập nhật");
                    }
                    break;
                case 4:
                    System.out.println("Nhập mã phiếu hoặc mã code cần tìm: ");
                    String billIds = scanner.nextLine().trim();
                    if (!billIds.isEmpty()) {
                        long billIdBrowseBill = Long.parseLong(billIds);
                        System.out.println("Danh sách phiếu");
                        List<User> listBillId = userBussiness.findById(billIdBrowseBill);
                        if (!listBillId.isEmpty()){
                            listBillId.forEach(User::displayData);
                        }else {
                            System.err.println("Không tìm thấy mã phiếu hoặc mã code, vui lòng nhập lại");
                        }
                    } else {
                        System.err.println("Mã phiếu hoặc mã code không được để trống, vui lòng nhập lại!");
                    }
                    break;
                case 5:
                    System.out.println("Chọn trạng thái để hiển thị:");
                    System.out.println("0. Tạo");
                    System.out.println("1. Hủy");
                    System.out.println("2. Duyệt");
                    System.out.print("Lựa chọn của bạn: ");
                    choice = Integer.parseInt(scanner.nextLine());
                    int userStatus = -1;
                    switch (choice) {
                        case 0:
                            userStatus = 0;
                            break;
                        case 1:
                            userStatus = 1;
                            break;
                        case 2:
                            userStatus = 2;
                            break;
                        default:
                            System.err.println("lựa chọn không hợp lệ, vui lòng nhập lại!");
                    }
                    if (userStatus != -1){
                        List<User> userList = userBussiness.findAll();
                        userList.stream().forEach(users -> users.displayData());
                    }
                    break;
                case 6:
                    User users = new User();
                    users.inputData(scanner);
                    boolean resultCreates = userBussiness.create(users);
                    if (resultCreates) {
                        System.out.println("Tạo phiếu xuất thành công");
                    } else {
                        System.err.println("Tao phiếu xuất thất bại");
                    }
                    break;
                case 7:
                    System.out.println("Nhập mã phiếu xuất hoặc mã code cần cập nhật: ");
                    String userBill = scanner.nextLine();
                    if (!userBill.isEmpty()) {
                        long billIdUpdate = Long.parseLong(userBill);
                        User billUpdate = new User();
                        billUpdate.setBillId(billIdUpdate);
                        billUpdate.inputDataUpdate(scanner);
                        boolean ressultUpdate = userBussiness.update(billUpdate);
                        if (ressultUpdate) {
                            System.out.println("Cập nhật thành công");
                        } else {
                            System.err.println("Không tồn tại mã phiếu xuất hoặc mã code");
                        }
                    } else {
                        System.err.println("Mời bạn nhập mã phiếu xuất hoặc mã code cần cập nhật");
                    }
                    break;
                case 8:
                    System.out.println("Nhập mã phiếu hoặc mã code cần tìm: ");
                    String userIdBill = scanner.nextLine().trim();
                    if (!userIdBill.isEmpty()) {
                        long billIdBrowseBill = Long.parseLong(userIdBill);
                        System.out.println("Danh sách phiếu");
                        List<User> listBillId = userBussiness.findById(billIdBrowseBill);
                        if (!listBillId.isEmpty()){
                            listBillId.forEach(User::displayData);
                        }else {
                            System.err.println("Không tìm thấy mã phiếu hoặc mã code, vui lòng nhập lại");
                        }
                    } else {
                        System.err.println("Mã phiếu hoặc mã code không được để trống, vui lòng nhập lại!");
                    }
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
