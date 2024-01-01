package ra.presentation.admin;

import ra.bussiness.AccountBussiness;
import ra.bussiness.IBussiness;
import ra.entity.Account;
import ra.entity.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccountPresentation {
    private static IBussiness accountBussiness = new AccountBussiness();
    public static List<Account> listAccount;

    public static void accountPresentation() {
         listAccount = Account.readDataFromFile();
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean isExit = true;
        do {
            System.out.println("******************ACCOUNT MANAGEMENT****************\n" +
                    "1. Danh sách tài khoản\n" +
                    "2. Tạo tài khoản mới\n" +
                    "3. Cập nhật trạng thái tài khoản\n" +
                    "4. Tìm kiếm tài khoản\n" +
                    "5. Thoát");
            System.out.println("Lụa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    List<Account> accountList = accountBussiness.findAll();
                    accountList.stream().forEach(account -> account.displayData());
                    break;
                case 2:
                    Account account = new Account();
                    account.inputData(scanner);
                    boolean resultCreate = accountBussiness.create(account);
                    if (resultCreate) {
                        System.out.println("Tạo tài khoản thành công thành công");
                    } else {
                        System.err.println("Tạo tài khoản thất bại");
                    }
                    Account.writeDataToFile(listAccount);
                    break;
                case 3:
                    System.out.println("Nhập mã tài khoản cần cập nhật: ");
                    String accId = scanner.nextLine();
                    if (!accId.isEmpty()) {
                        Integer accid = Integer.parseInt(accId);
                        Account accUpdateStatus = new Account();
                        accUpdateStatus.setAccId(accid);
                        boolean ressultUpdateStatus = accountBussiness.updateStatus(accUpdateStatus);
                        if (ressultUpdateStatus) {
                            System.out.println("Cập nhật thành công");
                        } else {
                            System.err.println("Không tồn tại mã tài khoản");
                        }
                    } else {
                        System.err.println("Mời bạn nhập mã tài khoản cần cập nhật");
                    }
                    Account.writeDataToFile(listAccount);
                    break;
                case 4:
                    System.out.println("Nhập tên tài khoản hoặc tên nhân viên cần tìm: ");
                    String accName = scanner.nextLine().trim();
                    if (!accName.isEmpty()) {
                        System.out.println("Danh sách tài khoản");
                        List<Account> listAccount = accountBussiness.findByName(accName);
                        if (!listAccount.isEmpty()) {
                            listAccount.stream().forEach(account1 -> account1.displayData());
                        } else {
                            System.err.println("Không tìm thấy tên tài khoản hoặc tên nhân viên");
                        }
                    } else {
                        System.err.println("Mời bạn nhập tên tài khoản hoặc tên nhân viên");
                    }
                    break;
                case 5:
                    isExit = false;
                    break;
                default:
                    System.err.println("Mời bạn nhập từ 1-6");
            }
        } while (isExit);
    }
}
