package ra.bussiness;

import ra.entity.Employee;
import ra.entity.Product;
import ra.util.ConnectionDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBussiness implements IBussiness<Employee, String> {
    @Override
    public List<Employee> findAll() {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Employee> listEmployee = null;
        try {
            callSt = conn.prepareCall("{call get_all_employee()}");
            ResultSet rs = callSt.executeQuery();
            listEmployee = new ArrayList<>();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmpId(rs.getString("Emp_Id"));
                employee.setEmpName(rs.getString("Emp_name"));
                java.sql.Date birthDate = rs.getDate("Birth_of_date");
                LocalDate birthLocalDate = birthDate.toLocalDate();
                employee.setBirthOfDate(birthLocalDate);
                employee.setEmail(rs.getString("Email"));
                employee.setPhone(rs.getString("Phone"));
                employee.setAddress(rs.getString("Address"));
                employee.setEmpStatus(rs.getShort("Emp_Status"));
                listEmployee.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return listEmployee;
    }

    @Override
    public boolean create(Employee employee) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            callSt = conn.prepareCall("{call create_employee(?, ?, ?, ?, ?, ?, ?)}");
            callSt.setString(1, employee.getEmpId());
            callSt.setString(2, employee.getEmpName());
            java.sql.Date sqlDate = java.sql.Date.valueOf(employee.getBirthOfDate());
            callSt.setDate(3, sqlDate);
            callSt.setString(4, employee.getEmail());
            callSt.setString(5, employee.getPhone());
            callSt.setString(6, employee.getAddress());
            callSt.setShort(7, employee.getEmpStatus());
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
    public boolean update(Employee employee) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt1 = null;
        CallableStatement callSt2 = null;
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            callSt1 = conn.prepareCall("{call get_employee_by_id(?, ?)}");
            callSt1.setString(1, employee.getEmpId());
            callSt1.registerOutParameter(2, Types.INTEGER);
            callSt1.execute();
            int cnt_employee = callSt1.getInt(2);
            if (cnt_employee > 0) {
                callSt2 = conn.prepareCall("{call update_employee(?, ?, ?, ?, ?, ?, ?)}");
                callSt2.setString(1, employee.getEmpId());
                callSt2.setString(2, employee.getEmpName());
                java.sql.Date sqlDate = java.sql.Date.valueOf(employee.getBirthOfDate());
                callSt2.setDate(3, sqlDate);
                callSt2.setString(4, employee.getEmail());
                callSt2.setString(5, employee.getPhone());
                callSt2.setString(6, employee.getAddress());
                callSt2.setShort(7, employee.getEmpStatus());
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
    public boolean updateStatus(Employee employee) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt1 = null;
        CallableStatement callSt2 = null;
        CallableStatement callSt3 = null;
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            callSt1 = conn.prepareCall("{call get_employee_by_id(?, ?)}");
            callSt1.setString(1, employee.getEmpId());
            callSt1.registerOutParameter(2, Types.INTEGER);
            callSt1.execute();
            int cnt_employee = callSt1.getInt(2);
            if (cnt_employee > 0) {
                callSt2 = conn.prepareCall("{call update_status_employee(?, ?)}");
                callSt2.setString(1, employee.getEmpId());
                callSt2.setShort(2, employee.getEmpStatus());
                callSt2.executeUpdate();
                if (employee.getEmpStatus() != null && (employee.getEmpStatus() == 1) || employee.getEmpStatus() == 2){
                    callSt3 = conn.prepareCall("{call update_status_account(?,?)}");
                    callSt3.setString(1, employee.getEmpId());
                    callSt3.setString(2, "Block");
                    callSt3.executeUpdate();
                }
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
    public List<Employee> findByName(String employName) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Employee> listEmployee = null;
        try {
            callSt = conn.prepareCall("{call find_employee(?)}");
            callSt.setString(1, employName);
            ResultSet rs = callSt.executeQuery();
            listEmployee = new ArrayList<>();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmpId(rs.getString("Emp_Id"));
                employee.setEmpName(rs.getString("Emp_name"));
                java.sql.Date birthDate = rs.getDate("Birth_of_date");
                LocalDate birthLocalDate = birthDate.toLocalDate();
                employee.setBirthOfDate(birthLocalDate);
                employee.setEmail(rs.getString("Email"));
                employee.setPhone(rs.getString("Phone"));
                employee.setAddress(rs.getString("Address"));
                employee.setEmpStatus(rs.getShort("Emp_Status"));
                listEmployee.add(employee);
            }
            return listEmployee;
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
