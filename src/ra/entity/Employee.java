package ra.entity;

import ra.util.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;

public class Employee {
    private String empId;
    private String empName;
    private LocalDate birthOfDate;
    private String email;
    private String phone;
    private String address;
    private Short empStatus;

    public Employee() {
    }

    public Employee(String empId, String empName, LocalDate birthOfDate, String email, String phone, String address, Short empStatus) {
        this.empId = empId;
        this.empName = empName;
        this.birthOfDate = birthOfDate;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.empStatus = empStatus;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public LocalDate getBirthOfDate() {
        return birthOfDate;
    }

    public void setBirthOfDate(LocalDate birthOfDate) {
        this.birthOfDate = birthOfDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Short getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(Short empStatus) {
        this.empStatus = empStatus;
    }

    public static String inputId(Scanner scanner) {
        System.out.println("Mời bạn nhập mã nhân viên: ");
        do {
            String empId = scanner.nextLine();
            if (!empId.isEmpty()) {
                if (empId.length() <= 5) {
                    boolean isDuplicate = checkDuplicateId(empId);
                    if (!isDuplicate) {
                        return empId;
                    } else {
                        System.err.println("Mã nhân viên đã có, vui lòng nhập lại");
                    }
                } else {
                    System.err.println("Mã nhân viên không được quá 5 ký tự, vui lòng nhập lại!");
                }
            } else {
                System.err.println("Mã nhân viên không được bỏ trống, nhập lại!");
            }
        } while (true);
    }
    private static boolean checkDuplicateId(String empId) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean isDuplicate = false;
        try {
            callSt = conn.prepareCall("{call check_duplicate_id(?, ?)}");
            callSt.setString(1, empId);
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
        System.out.println("Mời bạn nhập tên nhân viên");
        do {
            String empName = scanner.nextLine();
            if (!empName.isEmpty()) {
                boolean isDuplicate = checkDuplicateName(empName);
                if (!isDuplicate) {
                    return empName;
                } else {
                    System.err.println("Tên nhân viên đã có, vui lòng nhập lại");
                }
            } else {
                System.err.println("Tên nhân viên không được bỏ trống, vui lòng nhập lại!");
            }
        } while (true);
    }

    private static boolean checkDuplicateName(String empName) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean isDuplicate = false;
        try {
            callSt = conn.prepareCall("{call check_duplicate_name(?, ?)}");
            callSt.setString(1, empName);
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

    private static LocalDate inputBirth(Scanner scanner) {
        System.out.println("Mời bạn nhập ngày sinh (yyyy-mm-dd): ");
        do {
            String birthOfDate = scanner.nextLine();
            if (!birthOfDate.isEmpty()) {
                try {
                    LocalDate dateOfBirth = LocalDate.parse(birthOfDate);
                    return dateOfBirth;
                } catch (DateTimeException dt) {
                    System.err.println("Ngày sinh không hợp lệ, vui lòng nhập lại theo (yyyy-mm-dd): ");
                }
            } else {
                System.err.println("Ngày sinh không được để trống, vui lòng nhập ngày sinh!");
            }
        } while (true);
    }

    private static String inputEmail(Scanner scanner) {
        System.out.println("Mời bạn nhập Email: ");
        do {
            String email = scanner.nextLine();
            if (!email.isEmpty()) {
                if (email.contains("@") && email.contains(".")) {
                    return email;
                } else {
                    System.err.println("Email không hợp lệ, vui lòng nhập lại!");
                }
            } else {
                System.err.println("Email không được để trống, vui lòng nhập Email");
            }
        } while (true);
    }

    private static String inputPhone(Scanner scanner) {
        System.out.println("Mơ bạn nhập số điện thoại: ");
        do {
            String phone = scanner.nextLine();
            if (!phone.isEmpty()) {
                if (phone.startsWith("0") && phone.length() >= 10 && phone.length() <= 11) {
                    return phone;
                } else {
                    System.err.println("Sô điện thoại không hợp lệ, vui lòng nhập lại!");
                }
            } else {
                System.err.println("Số điện thoại không được bỏ trống, vui lòng nhập số điện thoại!");
            }
        } while (true);
    }

    private static String inputAddress(Scanner scanner) {
        System.out.println("Mời bạn nhập địa chỉ: ");
        do {
            String address = scanner.nextLine();
            if (!address.isEmpty()) {
                return address;
            } else {
                System.err.println("Địa chỉ không được để trống, vui lòng nhập lại!");
            }
        } while (true);
    }

    private static Short inputStatus(Scanner scanner) {
        System.out.println("Mời bạn nhập trạng thái(0-Hoạt động, 1-Nghỉ chế độ, 2-Nghỉ việc): ");
        do {
            String status = scanner.nextLine();
            if (!status.isEmpty()) {
                try {
                    short statuss = Short.parseShort(status);
                    return statuss;
                } catch (NumberFormatException nf) {
                    System.err.println("Nhập không hợp lệ, vui lòng nhập lại");
                }
            }else {
                System.err.println("Trạng thái không được để trống, vui lòng nhập trạng thái!");
            }
        } while (true);
    }

    public void inputData(Scanner scanner) {
        this.empId = inputId(scanner);
        this.empName = inputName(scanner);
        this.birthOfDate = inputBirth(scanner);
        this.email = inputEmail(scanner);
        this.phone = inputPhone(scanner);
        this.address = inputAddress(scanner);
        this.empStatus = inputStatus(scanner);
    }
    public void inputDataUpdate(Scanner scanner) {
        this.empName = inputName(scanner);
        this.birthOfDate = inputBirth(scanner);
        this.email = inputEmail(scanner);
        this.phone = inputPhone(scanner);
        this.address = inputAddress(scanner);
        this.empStatus = inputStatus(scanner);
    }

    public void displayData() {
        System.out.printf("Mã nhân viên: %s - Tên nhân viên: %s - Ngày sinh: %s - Email: %s\n", this.empId, this.empName, this.birthOfDate, this.email);
        System.out.printf("Số điện thoại: %s - Địa chỉ: %s - Trạng thái: %s\n", this.phone, this.address, (this.empStatus == 0 ? "Hoạt động" : (this.empStatus == 1 ? "Nghỉ chế độ" : "Nghỉ việc")));
    }
}
