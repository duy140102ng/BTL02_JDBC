package ra.bussiness;

import ra.entity.Product;
import ra.entity.Receipt;
import ra.util.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceiptBussiness implements IBussiness<Receipt, String> {
    @Override
    public List<Receipt> findAll() {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Receipt> receiptList = null;
        try {
            callSt = conn.prepareCall("{call get_all_receipt()}");
            ResultSet rs = callSt.executeQuery();
            receiptList = new ArrayList<>();
            while (rs.next()) {
                Receipt receipt = new Receipt();
                receipt.setBillId(rs.getLong("Bill_id"));
                receipt.setBillCode(rs.getString("Bill_Code"));
                receipt.setBillType(rs.getBoolean("Bill_Type"));
                receipt.setEmpIdCreated(rs.getString("Emp_id_created"));
                receipt.setCreated(rs.getDate("Created"));
                receipt.setProductId(rs.getString("Product_Id"));
                receipt.setQuantity(rs.getInt("Quantity"));
                receipt.setPrice(rs.getFloat("Price"));
                receipt.setBillStatus(rs.getInt("Bill_Status"));
                receiptList.add(receipt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return receiptList;
    }

    @Override
    public boolean create(Receipt receipt) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            callSt = conn.prepareCall("{call create_receipt(?, ?, ?, ?, ?, ?)}");
            callSt.setString(1, receipt.getBillCode());
            callSt.setBoolean(2, receipt.isBillType());
            callSt.setString(3, receipt.getEmpIdCreated());
            callSt.setString(4, receipt.getProductId());
            callSt.setInt(5, receipt.getQuantity());
            callSt.setFloat(6, receipt.getPrice());
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

    public List<Receipt> findAllBillDetail() {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Receipt> receiptList = null;
        try {
            callSt = conn.prepareCall("{call get_billDetail_receipt()}");
            ResultSet rs = callSt.executeQuery();
            receiptList = new ArrayList<>();
            while (rs.next()) {
                Receipt receipt = new Receipt();
                receipt.setBillDetailId(rs.getLong("Bill_Detail_Id"));
                receipt.setBillId(rs.getLong("Bill_id"));
                receipt.setProductId(rs.getString("Product_Id"));
                receipt.setQuantity(rs.getInt("Quantity"));
                receipt.setPrice(rs.getFloat("Price"));
                receiptList.add(receipt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return receiptList;
    }

    @Override
    public boolean update(Receipt receipt) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt1 = null;
        CallableStatement callSt2 = null;
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            callSt1 = conn.prepareCall("{call get_receipt_by_id(?, ?, ?)}");
            callSt1.setLong(1, receipt.getBillId());
            callSt1.setString(2, receipt.getBillCode());
            callSt1.registerOutParameter(3, Types.INTEGER);
            callSt1.execute();
            int cnt_product = callSt1.getInt(3);
            if (cnt_product == 0 || cnt_product == 1) {
                callSt2 = conn.prepareCall("{call update_receipt(?, ?, ?, ?, ?, ?, ?)}");
                callSt2.setLong(1, receipt.getBillId());
                callSt2.setString(2, receipt.getBillCode());
                callSt2.setString(3, receipt.getEmpIdCreated());
                callSt2.setInt(4, receipt.getBillStatus());
                callSt2.setString(5, receipt.getProductId());
                callSt2.setInt(6, receipt.getQuantity());
                callSt2.setFloat(7, receipt.getPrice());
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
    public boolean updateStatus(Receipt receipt) {
        return false;
    }

    public boolean browseBill(Receipt receipt) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt1 = null;
        CallableStatement callSt2 = null;
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            callSt1 = conn.prepareCall("{call get_receipt_by_id(?, ?, ?)}");
            callSt1.setLong(1, receipt.getBillId());
            callSt1.setString(2, receipt.getBillCode());
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

    public List<Receipt> findBrowse() {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Receipt> receiptList = null;
        try {
            callSt = conn.prepareCall("{call browse_bill()}");
            ResultSet rs = callSt.executeQuery();
            receiptList = new ArrayList<>();
            while (rs.next()) {
                Receipt receipt = new Receipt();
                receipt.setBillId(rs.getLong("Bill_id"));
                receipt.setBillCode(rs.getString("Bill_Code"));
                receipt.setBillType(rs.getBoolean("Bill_Type"));
                receipt.setEmpIdAuth(rs.getString("Emp_id_auth"));
                receipt.setAuthDate(rs.getDate("Auth_date"));
                receipt.setProductId(rs.getString("Product_Id"));
                receipt.setQuantity(rs.getInt("Quantity"));
                receipt.setPrice(rs.getFloat("Price"));
                receipt.setBillStatus(rs.getInt("Bill_Status"));
                receiptList.add(receipt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return receiptList;
    }

    @Override
    public List<Receipt> findByName(String s) {
        return null;
    }

    public List<Receipt> findById(long billId) {
        return findById(billId, null);
    }

    public List<Receipt> findById(long billId, String billCode) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Receipt> listReceipt = null;
        try {
            callSt = conn.prepareCall("{call find_receipt(?, ?)}");
            callSt.setLong(1, billId);
            callSt.setString(2, billCode);
            ResultSet rs = callSt.executeQuery();
            listReceipt = new ArrayList<>();
            while (rs.next()) {
                boolean billType = rs.getBoolean("Bill_Type");
                if (billType == true){
                    Receipt receipt = new Receipt();
                    receipt.setBillId(rs.getLong("Bill_id"));
                    receipt.setBillCode(rs.getString("Bill_Code"));
                    receipt.setBillType(billType);
                    receipt.setEmpIdCreated(rs.getString("Emp_id_created"));
                    receipt.setCreated(rs.getDate("Created"));
                    receipt.setProductId(rs.getString("Product_Id"));
                    receipt.setQuantity(rs.getInt("Quantity"));
                    receipt.setPrice(rs.getFloat("Price"));
                    receipt.setBillStatus(rs.getInt("Bill_Status"));
                    listReceipt.add(receipt);
                }
            }
            return listReceipt;
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
