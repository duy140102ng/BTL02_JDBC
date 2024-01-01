package ra.entity;

import ra.util.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Product {
    private String productId;
    private String productName;
    private String manufacturer;
    private Date created;
    private int batch;
    private int quantity;
    private boolean productStatus;

    public Product() {
    }

    public Product(String productId, String productName, String manufacturer, Date created, int batch, int quantity, boolean productStatus) {
        this.productId = productId;
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.created = created;
        this.batch = batch;
        this.quantity = quantity;
        this.productStatus = productStatus;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }


    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isProductStatus() {
        return productStatus;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }

    public static String inputId(Scanner scanner) {
        System.out.println("Mời bạn nhập mã sản phẩm: ");
        do {
            String productId = scanner.nextLine();
            if (!productId.isEmpty()) {
                if (productId.length() <= 5) {
                    boolean isDuplicate = checkDuplicateId(productId);
                    if (!isDuplicate) {
                        return productId;
                    } else {
                        System.err.println("Mã sản phẩm đã có, vui lòng nhập lại");
                    }
                } else {
                    System.err.println("Mã sản phẩm không được quá 5 ký tự, vui lòng nhập lại!");
                }
            } else {
                System.err.println("Mã sản phẩm không được bỏ trống, nhập lại!");
            }
        } while (true);
    }
    private static boolean checkDuplicateId(String productId) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean isDuplicate = false;
        try {
            callSt = conn.prepareCall("{call check_duplicate_idProduct(?, ?)}");
            callSt.setString(1, productId);
            callSt.registerOutParameter(2, Types.BOOLEAN);
            callSt.execute();
            isDuplicate = callSt.getBoolean(2);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return isDuplicate;
    }

    public static String inputName(Scanner scanner) {
        System.out.println("Mời bạn nhập tên sản phẩm: ");
        do {
            String productName = scanner.nextLine();
            if (!productName.isEmpty()) {
                boolean isDuplicate = checkDuplicateName(productName);
                if (!isDuplicate) {
                    return productName;
                } else {
                    System.err.println("Tên sản phẩm đã có, vui lòng nhập lại");
                }
            } else {
                System.err.println("Tên sản phẩm không được bỏ trống, vui lòng nhập lại!");
            }
        } while (true);
    }

    private static boolean checkDuplicateName(String productName) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean isDuplicate = false;
        try {
            callSt = conn.prepareCall("{call check_duplicate_nameProduct(?, ?)}");
            callSt.setString(1, productName);
            callSt.registerOutParameter(2, Types.BOOLEAN);
            callSt.execute();
            isDuplicate = callSt.getBoolean(2);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return isDuplicate;
    }

    private static String inputManufacturer(Scanner scanner) {
        System.out.println("Mời bạn nhập nhà sản xuất: ");
        do {
            String manufacturer = scanner.nextLine();
            if (!manufacturer.isEmpty()) {
                return manufacturer;
            } else {
                System.err.println("Nhà sản xuất không được để trống, vui lòng nhập lại!");
            }
        } while (true);
    }
    private static int inputBatch(Scanner scanner) {
        System.out.println("Mời bạn nhập lô chứa sản phẩm: ");
        do {
            String batch = scanner.nextLine();
            if (!batch.isEmpty()) {
                int batchs = Integer.parseInt(batch);
                return batchs;
            } else {
                System.err.println("Lô chứa sản phẩm không được để trống, vui lòng nhập lại!");
            }
        } while (true);
    }
    public void inputData(Scanner scanner){
        this.productId = inputId(scanner);
        this.productName = inputName(scanner);
        this.manufacturer = inputManufacturer(scanner);
        this.batch = inputBatch(scanner);
    }
    public void inputDataUpdate(Scanner scanner){
        this.productName = inputName(scanner);
        this.manufacturer = inputManufacturer(scanner);
        this.batch = inputBatch(scanner);
    }

    public void displayData(){
        System.out.printf("Mã sản phẩm: %s - Tên sản phẩm: %s - Nhà sản xuất: %s\n", this.productId, this.productName, this.manufacturer);
        System.out.printf("Ngày tạo: %s - Lô: %s - Số lượng: %s - Trạng thái: %s\n" , this.created, this.batch, this.quantity, (this.productStatus == true ? "Hoạt động" : "Không hoạt động"));
    }
}
