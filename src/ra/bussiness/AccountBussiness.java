package ra.bussiness;

import ra.entity.Account;
import ra.entity.Employee;
import ra.util.ConnectionDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccountBussiness implements IBussiness<Account, String> {

    @Override
    public List<Account> findAll() {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Account> accountList = null;
        try {
            callSt = conn.prepareCall("{call get_all_status()}");
            ResultSet rs = callSt.executeQuery();
            accountList = new ArrayList<>();
            while (rs.next()) {
                Account account = new Account();
                account.setAccId(rs.getInt("Acc_id"));
                account.setUserName(rs.getString("User_name"));
                account.setPassword(rs.getString("Password"));
                account.setPermission(rs.getBoolean("Permission"));
                account.setEmpId(rs.getString("Emp_id"));
                account.setAccStatus(rs.getBoolean("Acc_status"));
                accountList.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return accountList;
    }

    @Override
    public boolean create(Account account) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            callSt = conn.prepareCall("{call create_account(?, ?, ?, ?)}");
            callSt.setInt(1, account.getAccId());
            callSt.setString(2, account.getUserName());
            callSt.setString(3, account.getPassword());
            callSt.setString(4, account.getEmpId());
            callSt.executeUpdate();
            conn.commit();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return result;
    }

    @Override
    public boolean update(Account account) {
        return false;
    }

    @Override
    public boolean updateStatus(Account account) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt1 = null;
        CallableStatement callSt2 = null;
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            callSt1 = conn.prepareCall("{call get_account_by_id(?, ?)}");
            callSt1.setInt(1, account.getAccId());
            callSt1.registerOutParameter(2, Types.INTEGER);
            callSt1.execute();
            int cnt_employee = callSt1.getInt(2);
            if (cnt_employee > 0) {
                callSt2 = conn.prepareCall("{call update_status_account(?)}");
                callSt2.setInt(1, account.getAccId());
                callSt2.executeUpdate();
                conn.commit();
                result = true;
            } else {
                result = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return result;
    }

    @Override
    public List<Account> findByName(String findName) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Account> accountList = null;
        try {
            callSt = conn.prepareCall("{call findAccount(?)}");
            callSt.setString(1, findName);
            ResultSet rs = callSt.executeQuery();
            accountList = new ArrayList<>();
            while (rs.next()) {
                Account account = new Account();
                account.setAccId(rs.getInt("Acc_id"));
                account.setUserName(rs.getString("User_name"));
                account.setPassword(rs.getString("Password"));
                account.setPermission(rs.getBoolean("Permission"));
                account.setEmpId(rs.getString("Emp_id"));
                account.setAccStatus(rs.getBoolean("Acc_status"));
                accountList.add(account);
            }
            return accountList;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return null;
    }
}
