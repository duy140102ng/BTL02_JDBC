package ra.entity;

import ra.util.ConnectionDB;

import java.io.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Account {
    private int accId;
    private String userName;
    private String password;
    private boolean permission;
    private String empId;
    private boolean accStatus;

    public Account() {
    }

    public Account(int accId, String userName, String password, boolean permission, String empId, boolean accStatus) {
        this.accId = accId;
        this.userName = userName;
        this.password = password;
        this.permission = permission;
        this.empId = empId;
        this.accStatus = accStatus;
    }

    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public boolean isAccStatus() {
        return accStatus;
    }

    public void setAccStatus(boolean accStatus) {
        this.accStatus = accStatus;
    }

    public static String inputName(Scanner scanner) {
        System.out.println("Mời bạn nhập tên tài khoản");
        do {
            String accName = scanner.nextLine();
            if (!accName.isEmpty()) {
                boolean isDuplicate = checkDuplicateName(accName);
                if (!isDuplicate) {
                    return accName;
                } else {
                    System.err.println("Tên tài khoản đã có, vui lòng nhập lại");
                }
            } else {
                System.err.println("Tên tài khoản không được bỏ trống, vui lòng nhập lại!");
            }
        } while (true);
    }

    private static boolean checkDuplicateName(String accName) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean isDuplicate = false;
        try {
            callSt = conn.prepareCall("{call check_duplicate_nameAccount(?, ?)}");
            callSt.setString(1, accName);
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

    private static String inputPassword(Scanner scanner) {
        System.out.println("Mời bạn nhập mật khẩu: ");
        do {
            String passWord = scanner.nextLine();
            if (!passWord.isEmpty()) {
                return passWord;
            } else {
                System.err.println("Mật khẩu không được để trống, vui lòng nhập lại!");
            }
        } while (true);
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

    public void inputData(Scanner scanner) {
        this.userName = inputName(scanner);
        this.password = inputPassword(scanner);
        this.empId = inputId(scanner);
    }

    public void displayData() {
        System.out.printf("Mã tài khoản: %d - Tên tài khoản: %s - Mật khẩu: %s\n", this.accId, this.userName, this.password);
        System.out.printf("Quyền tài khoản: %s - Mã nhân viên: %s - Trạng thái: %s\n", (this.permission == true ? "User" : "Admin"), this.empId, (this.accStatus == true ? "Active" : "Block"));
    }

    public static void writeDataToFile(List<Account> accountList) {
        File file = new File("account.txt");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(accountList);
            oos.flush();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static List<Account> readDataFromFile() {
        List<Account> listAccount = null;
        File file = new File("account.txt");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            listAccount = (List<Account>) ois.readObject();
            return listAccount;
        } catch (FileNotFoundException e) {
            listAccount = new ArrayList<>();
        } catch (IOException e) {
            listAccount = new ArrayList<>();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return listAccount;
    }
}
