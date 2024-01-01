package ra.bussiness;

import ra.entity.Product;
import ra.util.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductBussiness implements IBussiness<Product, String> {
    @Override
    public List<Product> findAll() {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Product> listProduct = null;
        try {
            callSt = conn.prepareCall("{call get_all_product()}");
            ResultSet rs = callSt.executeQuery();
            listProduct = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("Product_Id"));
                product.setProductName(rs.getString("Product_name"));
                product.setManufacturer(rs.getString("Manufacturer"));
                product.setCreated(rs.getDate("Created"));
                product.setBatch(rs.getInt("Batch"));
                product.setQuantity(rs.getInt("Quantity"));
                product.setProductStatus(rs.getBoolean("Product_Status"));
                listProduct.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return listProduct;
    }

    @Override
    public boolean create(Product product) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            callSt = conn.prepareCall("{call create_product(?, ?, ?, ?)}");
            callSt.setString(1, product.getProductId());
            callSt.setString(2, product.getProductName());
            callSt.setString(3, product.getManufacturer());
            callSt.setInt(4, product.getBatch());
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
    public boolean update(Product product) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt1 = null;
        CallableStatement callSt2 = null;
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            callSt1 = conn.prepareCall("{call get_product_by_id(?, ?)}");
            callSt1.setString(1, product.getProductId());
            callSt1.registerOutParameter(2, Types.INTEGER);
            callSt1.execute();
            int cnt_product = callSt1.getInt(2);
            if (cnt_product > 0) {
                callSt2 = conn.prepareCall("{call update_product(?, ?, ?, ?, ?, ?)}");
                callSt2.setString(1, product.getProductId());
                callSt2.setString(2, product.getProductName());
                callSt2.setString(3, product.getManufacturer());
                callSt2.setInt(4, product.getQuantity());
                callSt2.setInt(5, product.getBatch());
                callSt2.setBoolean(6, product.isProductStatus());
                int updateCount = callSt2.executeUpdate();
                if (updateCount > 0) {
                    conn.commit();
                    result = true;
                } else {
                    conn.rollback();
                }
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
    public boolean updateStatus(Product product) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt1 = null;
        CallableStatement callSt2 = null;
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            callSt1 = conn.prepareCall("{call get_product_by_id(?, ?)}");
            callSt1.setString(1, product.getProductId());
            callSt1.registerOutParameter(2, Types.INTEGER);
            callSt1.execute();
            int cnt_product = callSt1.getInt(2);
            if (cnt_product > 0){
                callSt2 = conn.prepareCall("{call update_status_product(?)}");
                callSt2.setString(1, product.getProductId());
                int updateCount = callSt2.executeUpdate();
                if (updateCount > 0) {
                    conn.commit();
                    result = true;
                } else {
                    conn.rollback();
                }
            }else {
                return false;
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
    public List<Product> findByName(String inputSearch) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Product> listProduct = null;
        try {
            callSt = conn.prepareCall("{call find_product(?)}");
            callSt.setString(1, inputSearch);
            ResultSet rs = callSt.executeQuery();
            listProduct = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("Product_Id"));
                product.setProductName(rs.getString("Product_name"));
                product.setManufacturer(rs.getString("Manufacturer"));
                product.setCreated(rs.getDate("Created"));
                product.setBatch(rs.getInt("Batch"));
                product.setQuantity(rs.getInt("Quantity"));
                product.setProductStatus(rs.getBoolean("Product_Status"));
                listProduct.add(product);
            }
            return listProduct;
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
