package ra.presentation.admin;

import ra.bussiness.BillBussiness;
import ra.bussiness.ReceiptBussiness;
import ra.entity.Bill;
import ra.entity.Receipt;

import java.util.List;
import java.util.Scanner;

public class BillPrensentation {
    private static BillBussiness billBussiness = new BillBussiness();

    public static void billPresentation() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean isExit = true;
        do {
            System.out.println("******************BILL MANAGEMENT****************\n" +
                    "1. Danh sách phiếu xuất\n" +
                    "2. Tạo phiếu xuất\n" +
                    "3. Cập nhật thông tin phiếu xuất\n" +
                    "4. Chi tiết phiếu xuất\n" +
                    "5. Duyệt phiếu xuất\n" +
                    "6. Tìm kiếm phiếu xuất\n" +
                    "7. Thoát");
            try {
                System.out.println("Lựa chọn của bạn: ");
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception ex) {
                System.err.println("Có lỗi: " + ex);
            }
            switch (choice) {
                case 1:
                    List<Bill> billList = billBussiness.findAll();
                    billList.stream().forEach(bill -> bill.displayData());
                    break;
                case 2:
                    Bill bill = new Bill();
                    bill.inputData(scanner);
                    boolean resultCreate = billBussiness.create(bill);
                    if (resultCreate) {
                        System.out.println("Tạo phiếu xuất thành công");
                    } else {
                        System.err.println("Tao phiếu xuất thất bại");
                    }
                    break;
                case 3:
                    System.out.println("Nhập mã phiếu xuất hoặc mã code cần cập nhật: ");
                    String billId = scanner.nextLine();
                    if (!billId.isEmpty()) {
                        long billIdUpdate = Long.parseLong(billId);
                        Bill billUpdate = new Bill();
                        billUpdate.setBillId(billIdUpdate);
                        billUpdate.inputDataUpdate(scanner);
                        boolean ressultUpdate = billBussiness.update(billUpdate);
                        if (ressultUpdate) {
                            System.out.println("Cập nhật thành công");
                        } else {
                            System.err.println("Không tồn tại mã phiếu xuất hoặc mã code");
                        }
                    } else {
                        System.err.println("Mời bạn nhập mã phiếu xuất hoặc mã code cần cập nhật");
                    }
                    break;
                case 4:
                    List<Bill> listBill = billBussiness.findAllBillDetail();
                    listBill.stream().forEach(bills -> bills.displayDataBillDetail());
                    break;
                case 5:
                    System.out.println("Nhập mã phiếu xuất hoặc mã code cần cập nhật: ");
                    String billIdBrowse = scanner.nextLine();
                    if (!billIdBrowse.isEmpty()) {
                        long billIdBrowseBill = Long.parseLong(billIdBrowse);
                        Bill billBrowse = new Bill();
                        billBrowse.setBillId(billIdBrowseBill);
                        billBrowse.inputDataBrowse(scanner);
                        boolean ressultBrowse = billBussiness.browseBill(billBrowse);
                        if (ressultBrowse) {
                            List<Bill> listBrowse = billBussiness.findBrowse();
                            listBrowse.stream().forEach(bills -> bills.displayDataBrowse());
                        } else {
                            System.err.println("Không tồn tại mã phiếu xuất hoặc mã code");
                        }
                    } else {
                        System.err.println("Mời bạn nhập mã phiếu xuất hoặc mã code cần cập nhật");
                    }
                    break;
                case 6:
                    System.out.println("Nhập mã phiếu hoặc mã code cần tìm: ");
                    String billIds = scanner.nextLine().trim();
                    if (!billIds.isEmpty()) {
                        long billIdBrowseBill = Long.parseLong(billIds);
                        System.out.println("Danh sách phiếu");
                        List<Bill> listBillId = billBussiness.findById(billIdBrowseBill);
                        if (!listBillId.isEmpty()){
                            listBillId.forEach(Bill::displayData);
                        }else {
                            System.err.println("Không tìm thấy mã phiếu hoặc mã code, vui lòng nhập lại");
                        }
                    } else {
                        System.err.println("Mã phiếu hoặc mã code không được để trống, vui lòng nhập lại!");
                    }
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
