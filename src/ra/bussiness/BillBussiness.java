package ra.bussiness;

import ra.entity.Bill;
import ra.entity.Receipt;
import ra.util.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillBussiness implements IBussiness<Bill, String> {
    @Override
    public List<Bill> findAll() {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Bill> billList = null;
        try {
            callSt = conn.prepareCall("{call get_all_receipt()}");
            ResultSet rs = callSt.executeQuery();
            billList = new ArrayList<>();
            while (rs.next()) {
                boolean billType = rs.getBoolean("Bill_Type");
                if (!billType){
                    Bill bill = new Bill();
                    bill.setBillId(rs.getLong("Bill_id"));
                    bill.setBillCode(rs.getString("Bill_Code"));
                    bill.setBillType(billType);
                    bill.setEmpIdCreated(rs.getString("Emp_id_created"));
                    bill.setCreated(rs.getDate("Created"));
                    bill.setProductId(rs.getString("Product_Id"));
                    bill.setQuantity(rs.getInt("Quantity"));
                    bill.setPrice(rs.getFloat("Price"));
                    bill.setBillStatus(rs.getInt("Bill_Status"));
                    billList.add(bill);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return billList;
    }

    @Override
    public boolean create(Bill bill) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            callSt = conn.prepareCall("{call create_receipt(?, ?, ?, ?, ?, ?)}");
            callSt.setString(1, bill.getBillCode());
            callSt.setBoolean(2, bill.isBillType());
            callSt.setString(3, bill.getEmpIdCreated());
            callSt.setString(4, bill.getProductId());
            callSt.setInt(5, bill.getQuantity());
            callSt.setFloat(6, bill.getPrice());
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
    public boolean update(Bill bill) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt1 = null;
        CallableStatement callSt2 = null;
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            callSt1 = conn.prepareCall("{call get_receipt_by_id(?, ?, ?)}");
            callSt1.setLong(1, bill.getBillId());
            callSt1.setString(2, bill.getBillCode());
            callSt1.registerOutParameter(3, Types.INTEGER);
            callSt1.execute();
            int cnt_bill = callSt1.getInt(3);
            if (cnt_bill == 0 || cnt_bill == 1) {
                callSt2 = conn.prepareCall("{call update_receipt(?, ?, ?, ?, ?, ?, ?)}");
                callSt2.setLong(1, bill.getBillId());
                callSt2.setString(2, bill.getBillCode());
                callSt2.setString(3, bill.getEmpIdCreated());
                callSt2.setInt(4, bill.getBillStatus());
                callSt2.setString(5, bill.getProductId());
                callSt2.setInt(6, bill.getQuantity());
                callSt2.setFloat(7, bill.getPrice());
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

    public List<Bill> findAllBillDetail() {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Bill> billList = null;
        try {
            callSt = conn.prepareCall("{call get_billDetail_receipt()}");
            ResultSet rs = callSt.executeQuery();
            billList = new ArrayList<>();
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillDetailId(rs.getLong("Bill_Detail_Id"));
                bill.setBillId(rs.getLong("Bill_id"));
                bill.setProductId(rs.getString("Product_Id"));
                bill.setQuantity(rs.getInt("Quantity"));
                bill.setPrice(rs.getFloat("Price"));
                billList.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return billList;
    }

    public boolean browseBill(Bill bill) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt1 = null;
        CallableStatement callSt2 = null;
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            callSt1 = conn.prepareCall("{call get_receipt_by_id(?, ?, ?)}");
            callSt1.setLong(1, bill.getBillId());
            callSt1.setString(2, bill.getBillCode());
            callSt1.registerOutParameter(3, Types.INTEGER);
            callSt1.execute();
            int cnt_product = callSt1.getInt(3);
            if (cnt_product == 1) {
                callSt2 = conn.prepareCall("{call browse_bill()}");
                int updateCount = callSt2.executeUpdate();
                if (updateCount > 0) {
                    conn.commit();
                    result = true;
                } else {
                    conn.rollback();
                }
            } else {
                System.err.println("Không thể duyệt phiếu khi đang ở trạng thái hủy");
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

    public List<Bill> findBrowse() {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Bill> billList = null;
        try {
            callSt = conn.prepareCall("{call browse_bill()}");
            ResultSet rs = callSt.executeQuery();
            billList = new ArrayList<>();
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getLong("Bill_id"));
                bill.setBillCode(rs.getString("Bill_Code"));
                bill.setBillType(rs.getBoolean("Bill_Type"));
                bill.setEmpIdCreated(rs.getString("Emp_id_created"));
                bill.setCreated(rs.getDate("Created"));
                bill.setProductId(rs.getString("Product_Id"));
                bill.setQuantity(rs.getInt("Quantity"));
                bill.setPrice(rs.getFloat("Price"));
                bill.setBillStatus(rs.getInt("Bill_Status"));
                billList.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return billList;
    }

    @Override
    public boolean updateStatus(Bill bill) {
        return false;
    }

    @Override
    public List<Bill> findByName(String s) {
        return null;
    }

    public List<Bill> findById(long billId) {
        return findById(billId, null);
    }

    public List<Bill> findById(long billId, String billCode) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Bill> listBill = null;
        try {
            callSt = conn.prepareCall("{call find_receipt(?, ?)}");
            callSt.setLong(1, billId);
            callSt.setString(2, billCode);
            ResultSet rs = callSt.executeQuery();
            listBill = new ArrayList<>();
            while (rs.next()) {
                boolean billType = rs.getBoolean("Bill_Type");
                if (!billType){
                    Bill bill = new Bill();
                    bill.setBillId(rs.getLong("Bill_id"));
                    bill.setBillCode(rs.getString("Bill_Code"));
                    bill.setBillType(billType);
                    bill.setEmpIdCreated(rs.getString("Emp_id_created"));
                    bill.setCreated(rs.getDate("Created"));
                    bill.setProductId(rs.getString("Product_Id"));
                    bill.setQuantity(rs.getInt("Quantity"));
                    bill.setPrice(rs.getFloat("Price"));
                    bill.setBillStatus(rs.getInt("Bill_Status"));
                    listBill.add(bill);
                }
            }
            return listBill;
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
