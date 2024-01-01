package ra.presentation.admin;

import ra.bussiness.IBussiness;
import ra.bussiness.ReceiptBussiness;
import ra.entity.Account;
import ra.entity.Product;
import ra.entity.Receipt;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class ReceiptPresentation {
    private static ReceiptBussiness receiptBussiness = new ReceiptBussiness();

    public static void receiptPresentation() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean isExit = true;
        do {
            System.out.println("******************RECEIPT MANAGEMENT****************\n" +
                    "1. Danh sách phiếu nhập\n" +
                    "2. Tạo phiếu nhập\n" +
                    "3. Cập nhật thông tin phiếu nhập\n" +
                    "4. Chi tiết phiếu nhập\n" +
                    "5. Duyệt phiếu nhập\n" +
                    "6. Tìm kiếm phiếu nhập\n" +
                    "7. Thoát");
            try {
                System.out.println("Lựa chọn của bạn: ");
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception ex) {
                System.err.println("Có lỗi: " + ex);
            }
            switch (choice) {
                case 1:
                    List<Receipt> receiptList = receiptBussiness.findAll();
                    receiptList.stream().forEach(receipt -> receipt.displayData());
                    break;
                case 2:
                    Receipt receipt = new Receipt();
                    receipt.inputData(scanner);
                    boolean resultCreate = receiptBussiness.create(receipt);
                    if (resultCreate) {
                        System.out.println("Tạo phiếu nhập thành công");
                    } else {
                        System.err.println("Tao phiếu nhập thất bại");
                    }
                    break;
                case 3:
                    System.out.println("Nhập mã phiếu nhập hoặc mã code cần cập nhật: ");
                    String receiptId = scanner.nextLine();
                    if (!receiptId.isEmpty()) {
                        long receiptIdUpdate = Long.parseLong(receiptId);
                        Receipt receiptUpdate = new Receipt();
                        receiptUpdate.setBillId(receiptIdUpdate);
                        receiptUpdate.inputDataUpdate(scanner);
                        boolean ressultUpdate = receiptBussiness.update(receiptUpdate);
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
                    List<Receipt> listReceipt = receiptBussiness.findAllBillDetail();
                    listReceipt.stream().forEach(receipts -> receipts.displayDataBillDetail());
                    break;
                case 5:
                    System.out.println("Nhập mã phiếu nhập hoặc mã code cần cập nhật: ");
                    String receiptIdBrowse = scanner.nextLine();
                    if (!receiptIdBrowse.isEmpty()) {
                        long receiptIdBrowseBill = Long.parseLong(receiptIdBrowse);
                        Receipt receiptBrowse = new Receipt();
                        receiptBrowse.setBillId(receiptIdBrowseBill);
                        receiptBrowse.inputDataBrowse(scanner);
                        boolean ressultBrowse = receiptBussiness.browseBill(receiptBrowse);
                        if (ressultBrowse) {
                            List<Receipt> listBrowse = receiptBussiness.findBrowse();
                            listBrowse.stream().forEach(receipts -> receipts.displayDataBrowse());
                        } else {
                            System.err.println("Không tồn tại mã phiếu nhập hoặc mã code");
                        }
                    } else {
                        System.err.println("Mời bạn nhập mã phiếu nhập hoặc mã code cần cập nhật");
                    }
                    break;
                case 6:
                    System.out.println("Nhập mã phiếu hoặc mã code cần tìm: ");
                    String billId = scanner.nextLine().trim();
                    if (!billId.isEmpty()) {
                        long receiptIdBrowseBill = Long.parseLong(billId);
                        System.out.println("Danh sách phiếu");
                        List<Receipt> listReceiptId = receiptBussiness.findById(receiptIdBrowseBill);
                        if (!listReceiptId.isEmpty()){
                            listReceiptId.forEach(Receipt::displayData);
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
