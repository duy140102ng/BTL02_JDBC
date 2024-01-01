package ra.presentation.login;

import ra.presentation.admin.Admin;
import ra.presentation.user.User;
import ra.util.ConnectionDB;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class login {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        do {
            Connection conn = ConnectionDB.openConnection();
            CallableStatement callSt = null;
            CallableStatement callSt1 = null;
            CallableStatement callSt2 = null;
            System.out.println("       Chào mừng bạn đến với chương trình quản lý kho");
            System.out.println("************************QUẢN LÝ KHO**********************");
            System.out.println("Tài khoản: ");
            String userName = scanner.nextLine();
            System.out.println("Mật khẩu: ");
            String passWord = scanner.nextLine();
            try {
                callSt = conn.prepareCall("{call login(? , ?, ?)}");
                callSt.setString(1, userName);
                callSt.setString(2, passWord);
                callSt.setInt(3, Types.INTEGER);
                callSt.execute();
                int result = callSt.getInt(3);
                if (result > 0) {
                    callSt1 = conn.prepareCall("{call login_check_status(?)}");
                    callSt1.setString(1, userName);
                    ResultSet rs = callSt1.executeQuery();
                    int status_check = 0;
                    if (rs.next()) {
                        status_check = rs.getInt(1);
                    }
                    if (status_check == 1) {
                        callSt2 = conn.prepareCall("{call check_permission(?)}");
                        callSt2.setString(1, userName);
                        ResultSet rs1 = callSt2.executeQuery();
                        int checkPermission = 0;
                        if (rs1.next()) {
                            checkPermission = rs1.getInt(1);
                        }
                        ConnectionDB.closeConnection(conn);
                        if (checkPermission == 1) {
                            User.AccountUser();
                        } else {
                            Admin.AccountAmin();
                        }
                    } else {
                        System.err.println("Tài khoản đã khóa");
                    }
                } else {
                    System.err.println("Tài khoản hoặc mật khẩu nhập bị sai, thử lại.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                ConnectionDB.closeConnection(conn);
            }
        } while (true);
    }
}
