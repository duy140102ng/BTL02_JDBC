package ra.presentation.user;

import ra.bussiness.IBussiness;
import ra.entity.Bill;
import ra.util.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserBussiness implements IBussiness<User, String> {
    @Override
    public List<User> findAll() {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<User> userList = null;
        try {
            callSt = conn.prepareCall("{call get_all_receipt()}");
            ResultSet rs = callSt.executeQuery();
            userList = new ArrayList<>();
            while (rs.next()) {
                boolean billType = rs.getBoolean("Bill_Type");
                if (billType){
                    User user = new User();
                    user.setBillId(rs.getLong("Bill_id"));
                    user.setBillCode(rs.getString("Bill_Code"));
                    user.setBillType(billType);
                    user.setEmpIdCreated(rs.getString("Emp_id_created"));
                    user.setCreated(rs.getDate("Created"));
                    user.setProductId(rs.getString("Product_Id"));
                    user.setQuantity(rs.getInt("Quantity"));
                    user.setPrice(rs.getFloat("Price"));
                    user.setBillStatus(rs.getInt("Bill_Status"));
                    userList.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return userList;
    }

    public List<User> findAllExportBill() {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<User> userList = null;
        try {
            callSt = conn.prepareCall("{call get_all_receipt()}");
            ResultSet rs = callSt.executeQuery();
            userList = new ArrayList<>();
            while (rs.next()) {
                boolean billType = rs.getBoolean("Bill_Type");
                if (!billType){
                    User user = new User();
                    user.setBillId(rs.getLong("Bill_id"));
                    user.setBillCode(rs.getString("Bill_Code"));
                    user.setBillType(billType);
                    user.setEmpIdCreated(rs.getString("Emp_id_created"));
                    user.setCreated(rs.getDate("Created"));
                    user.setProductId(rs.getString("Product_Id"));
                    user.setQuantity(rs.getInt("Quantity"));
                    user.setPrice(rs.getFloat("Price"));
                    user.setBillStatus(rs.getInt("Bill_Status"));
                    userList.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return userList;
    }


    @Override
    public boolean create(User user) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            callSt = conn.prepareCall("{call create_receipt(?, ?, ?, ?, ?, ?)}");
            callSt.setString(1, user.getBillCode());
            callSt.setBoolean(2, user.isBillType());
            callSt.setString(3, user.getEmpIdCreated());
            callSt.setString(4, user.getProductId());
            callSt.setInt(5, user.getQuantity());
            callSt.setFloat(6, user.getPrice());
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
    public boolean update(User user) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt1 = null;
        CallableStatement callSt2 = null;
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            callSt1 = conn.prepareCall("{call get_receipt_by_id(?, ?, ?)}");
            callSt1.setLong(1, user.getBillId());
            callSt1.setString(2, user.getBillCode());
            callSt1.registerOutParameter(3, Types.INTEGER);
            callSt1.execute();
            int cnt_user = callSt1.getInt(3);
            if (cnt_user == 0 || cnt_user == 1) {
                callSt2 = conn.prepareCall("{call update_receipt(?, ?, ?, ?, ?, ?, ?)}");
                callSt2.setLong(1, user.getBillId());
                callSt2.setString(2, user.getBillCode());
                callSt2.setString(3, user.getEmpIdCreated());
                callSt2.setInt(4, user.getBillStatus());
                callSt2.setString(5, user.getProductId());
                callSt2.setInt(6, user.getQuantity());
                callSt2.setFloat(7, user.getPrice());
                int updateCount = callSt2.executeUpdate();
                if (updateCount > 0) {
                    conn.commit();
                    result = true;
                } else {
                    conn.rollback();
                }
            } else {
                System.err.println("Không thể cập nhật phiếu khi đang ở trạng thái duyệt");
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
    public boolean updateStatus(User user) {
        return false;
    }

    @Override
    public List<User> findByName(String s) {
        return null;
    }

    public List<User> findById(long billId) {
        return findById(billId, null);
    }

    public List<User> findById(long billId, String billCode) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<User> userList = null;
        try {
            callSt = conn.prepareCall("{call find_receipt(?, ?)}");
            callSt.setLong(1, billId);
            callSt.setString(2, billCode);
            ResultSet rs = callSt.executeQuery();
            userList = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setBillId(rs.getLong("Bill_id"));
                user.setBillCode(rs.getString("Bill_Code"));
                user.setBillType(rs.getBoolean("Bill_Type"));
                user.setEmpIdCreated(rs.getString("Emp_id_created"));
                user.setCreated(rs.getDate("Created"));
                user.setProductId(rs.getString("Product_Id"));
                user.setQuantity(rs.getInt("Quantity"));
                user.setPrice(rs.getFloat("Price"));
                user.setBillStatus(rs.getInt("Bill_Status"));
                userList.add(user);
            }
            return userList;
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
