package ra.presentation.user;

import ra.util.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.Scanner;

public class User {
    private long billId;
    private String billCode;
    private boolean billType;
    private String empIdCreated;
    private Date created;
    private String empIdAuth;
    private Date authDate;
    private int quantity;
    private float price;
    private long billDetailId;
    private String productId;
    private int billStatus;

    public User() {
    }

    public User(long billId, String billCode, boolean billType, String empIdCreated, Date created, String empIdAuth, Date authDate, int quantity, float price, long billDetailId, String productId, int billStatus) {
        this.billId = billId;
        this.billCode = billCode;
        this.billType = billType;
        this.empIdCreated = empIdCreated;
        this.created = created;
        this.empIdAuth = empIdAuth;
        this.authDate = authDate;
        this.quantity = quantity;
        this.price = price;
        this.billDetailId = billDetailId;
        this.productId = productId;
        this.billStatus = billStatus;
    }

    public boolean isBillType() {
        return billType;
    }

    public void setBillType(boolean billType) {
        this.billType = billType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getBillDetailId() {
        return billDetailId;
    }

    public void setBillDetailId(long billDetailId) {
        this.billDetailId = billDetailId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getEmpIdCreated() {
        return empIdCreated;
    }

    public void setEmpIdCreated(String empIdCreated) {
        this.empIdCreated = empIdCreated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getEmpIdAuth() {
        return empIdAuth;
    }

    public void setEmpIdAuth(String empIdAuth) {
        this.empIdAuth = empIdAuth;
    }

    public Date getAuthDate() {
        return authDate;
    }

    public void setAuthDate(Date authDate) {
        this.authDate = authDate;
    }

    public int getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(int billStatus) {
        this.billStatus = billStatus;
    }

    private static String inputBillCode(Scanner scanner) {
        System.out.println("Mời bạn nhập mã code: ");
        do {
            String billCode = scanner.nextLine();
            if (!billCode.isEmpty()) {
                return billCode;
            } else {
                System.err.println("Mã code không được để trống, vui lòng nhập lại!");
            }
        } while (true);
    }

    private static Boolean inputBillType(Scanner scanner) {
        System.out.println("Chọn loại phiếu:");
        System.out.println("0. Xuất");
        System.out.println("1. Nhập");
        System.out.print("Lựa chọn của bạn: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 0:
                    return false;
                case 1:
                    return true;
                default:
                    System.err.println("lựa chọn không hợp lệ, vui lòng nhập lại!");
                    return false;
            }
        }catch (NumberFormatException nf){
            System.err.println("Lựa chọn không hợp lệ, vui lòng nhập lại");
            return false;
        }
    }

    private static String inputEmpId(Scanner scanner) {
        System.out.println("Mời bạn nhập mã nhân viên: ");
        do {
            String empId = scanner.nextLine();
            if (!empId.isEmpty()) {
                if (checkDuplicateId(empId)) {
                    return empId;
                } else {
                    System.err.println("Mã nhân viên không tồn tại, vui lòng nhập lại!");
                }
            } else {
                System.err.println("Mã nhân viên không được để trống, vui lòng nhập lại!");
            }
        } while (true);
    }

    private static boolean checkDuplicateId(String receiptId) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        try {
            callSt = conn.prepareCall("{call createBillId(?, ?)}");
            callSt.setString(1, receiptId);
            callSt.registerOutParameter(2, Types.BOOLEAN);
            callSt.execute();
            return callSt.getBoolean(2);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return false;
    }

    private static String inputEmpIdAuth(Scanner scanner) {
        System.out.println("Mời bạn nhập mã nhân viên duyệt: ");
        do {
            String empIdAuth = scanner.nextLine();
            if (!empIdAuth.isEmpty()) {
                if (checkDuplicateIdAuth(empIdAuth)) {
                    return empIdAuth;
                } else {
                    System.err.println("Mã nhân viên duyệt không tồn tại, vui lòng nhập lại!");
                }
            } else {
                System.err.println("Mã nhân viên duyệt không được để trống, vui lòng nhập lại!");
            }
        } while (true);
    }

    private static boolean checkDuplicateIdAuth(String BillId) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        try {
            callSt = conn.prepareCall("{call createBillId(?, ?)}");
            callSt.setString(1, BillId);
            callSt.registerOutParameter(2, Types.BOOLEAN);
            callSt.execute();
            return callSt.getBoolean(2);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return false;
    }

    private static String inputProductId(Scanner scanner) {
        System.out.println("Mời bạn nhập mã sản phẩm: ");
        do {
            String productId = scanner.nextLine();
            if (!productId.isEmpty()) {
                if (checkDuplicateProductId(productId)) {
                    return productId;
                } else {
                    System.err.println("Mã sản phẩm không tồn tại, vui lòng nhập lại!");
                }
            } else {
                System.err.println("Mã sản phẩm không được để trống, vui lòng nhập lại!");
            }
        } while (true);
    }

    private static boolean checkDuplicateProductId(String billId) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        try {
            callSt = conn.prepareCall("{call createBillIdProduct(?, ?)}");
            callSt.setString(1, billId);
            callSt.registerOutParameter(2, Types.BOOLEAN);
            callSt.execute();
            return callSt.getBoolean(2);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return false;
    }


    private static int inputQuantity(Scanner scanner) {
        System.out.println("Mời bạn nhập số lượng xuất: ");
        do {
            String billQuantyti = scanner.nextLine();
            if (!billQuantyti.isEmpty()) {
                int billQuantiti = Integer.parseInt(billQuantyti);
                if (billQuantiti > 0) {
                    return billQuantiti;
                } else {
                    System.err.println("Số lượng xuất không được âm, vui lòng nhập lại!");
                }
            } else {
                System.err.println("Số lượng xuất không được để trống, vui lòng nhập lại!");
            }
        } while (true);
    }

    private static float inputPrice(Scanner scanner) {
        System.out.println("Mời bạn nhập giá xuất: ");
        do {
            String billPrice = scanner.nextLine();
            if (!billPrice.isEmpty()) {
                float billPrices = Float.parseFloat(billPrice);
                if (billPrices > 0) {
                    return billPrices;
                } else {
                    System.err.println("Gía xuất không được âm, vui lòng nhập lại!");
                }
            } else {
                System.err.println("Gía xuất không được để trống, vui lòng nhập lại!");
            }
        } while (true);
    }
    private static int inputQuantityIn(Scanner scanner) {
        System.out.println("Mời bạn nhập số lượng nhập: ");
        do {
            String billQuantyti = scanner.nextLine();
            if (!billQuantyti.isEmpty()) {
                int billQuantiti = Integer.parseInt(billQuantyti);
                if (billQuantiti > 0) {
                    return billQuantiti;
                } else {
                    System.err.println("Số lượng nhập không được âm, vui lòng nhập lại!");
                }
            } else {
                System.err.println("Số lượng nhập không được để trống, vui lòng nhập lại!");
            }
        } while (true);
    }

    private static float inputPriceIn(Scanner scanner) {
        System.out.println("Mời bạn nhập giá nhập: ");
        do {
            String billPrice = scanner.nextLine();
            if (!billPrice.isEmpty()) {
                float billPrices = Float.parseFloat(billPrice);
                if (billPrices > 0) {
                    return billPrices;
                } else {
                    System.err.println("Gía nhập không được âm, vui lòng nhập lại!");
                }
            } else {
                System.err.println("Gía nhập không được để trống, vui lòng nhập lại!");
            }
        } while (true);
    }

    private static int inputStatus(Scanner scanner) {
        System.out.println("Chọn trạng thái nhân viên:");
        System.out.println("0. Tạo");
        System.out.println("1. Hủy");
        System.out.println("2. Duyệt");
        System.out.print("Lựa chọn của bạn: ");
        int choice = Integer.parseInt(scanner.nextLine());
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
        return billStatus;
    }

    public void inputData(Scanner scanner) {
        this.billCode = inputBillCode(scanner);
        this.billType = inputBillType(scanner);
        this.empIdCreated = inputEmpId(scanner);
        this.productId = inputProductId(scanner);
        this.quantity = inputQuantity(scanner);
        this.price = inputPrice(scanner);
    }

    public void inputDataUpdate(Scanner scanner) {
        this.billCode = inputBillCode(scanner);
        this.empIdCreated = inputEmpId(scanner);
        this.billStatus = inputStatus(scanner);
        this.productId = inputProductId(scanner);
        this.quantity = inputQuantity(scanner);
        this.price = inputPrice(scanner);
    }

    public void displayData() {
        System.out.printf("Mã phiếu: %d - Mã code: %s - Loại phiếu: %s\n", this.billId, this.billCode, (this.billType == true ? "Phiếu nhập" : "Phiếu xuất"));
        System.out.printf("Mã nhân viên xuất: %s - Ngày tạo: %s - Số lượng xuất: %d - Gía xuất: %f\n", this.empIdCreated, this.created, this.quantity, this.price);
        System.out.printf("Mã sản phẩm: %s - Trạng thái: %s\n", this.productId, (this.billStatus == 0 ? "Tạo" : (this.billStatus == 1 ? "Hủy" : "Duyệt")));
    }

    public void displayDataIn() {
        System.out.printf("Mã phiếu: %d - Mã code: %s - Loại phiếu: %s\n", this.billId, this.billCode, (this.billType == true ? "Phiếu nhập" : "Phiếu xuất"));
        System.out.printf("Mã nhân viên nhập: %s - Ngày tạo: %s - Số lượng nhập: %d - Gía nhập: %f\n", this.empIdCreated, this.created, this.quantity, this.price);
        System.out.printf("Mã sản phẩm: %s - Trạng thái: %s\n", this.productId, (this.billStatus == 0 ? "Tạo" : (this.billStatus == 1 ? "Hủy" : "Duyệt")));
    }


}
