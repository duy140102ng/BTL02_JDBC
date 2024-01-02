package ra.presentation.admin;

import ra.bussiness.EmployeeBussiness;
import ra.bussiness.IBussiness;
import ra.bussiness.ProductBussiness;
import ra.entity.Account;
import ra.entity.Employee;
import ra.entity.Product;

import java.util.List;
import java.util.Scanner;

public class EmployeePresentation {
    private static IBussiness employeeBussiness = new EmployeeBussiness();

    public static void employeePresentation() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean isExit = true;
        do {
            String blackBold = "\033[1;30m";
            String greenText = "\033[1;32m";
            String reset = "\u001B[0m";

            System.out.println(blackBold + greenText + "+------------------------------------------------------+");
            System.out.println(blackBold + greenText + "|                     EMPLOYEE MANAGEMENT              |" + reset);
            System.out.println(blackBold + greenText + "|                                                      |" + reset);
            System.out.println(blackBold + greenText + "|   1. Danh sách nhân viên                             |" + reset);
            System.out.println(blackBold + greenText + "|   2. Thêm mới nhân viên                              |" + reset);
            System.out.println(blackBold + greenText + "|   3. Cập nhật thông tin nhân viên                    |" + reset);
            System.out.println(blackBold + greenText + "|   4. Cập nhật trạng thái nhân viên                   |" + reset);
            System.out.println(blackBold + greenText + "|   5. Tìm kiếm nhân viên                              |" + reset);
            System.out.println(blackBold + greenText + "|   6. Thoát                                           |" + reset);
            System.out.println(blackBold + greenText + "|                                                      |" + reset);
            System.out.println(blackBold + greenText + "+------------------------------------------------------+");
            System.out.println("Lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    List<Employee> listEmployee = employeeBussiness.findAll();
                    listEmployee.stream().forEach(employee -> employee.displayData());
                    break;
                case 2:
                    Employee employee = new Employee();
                    employee.inputData(scanner);
                    boolean resultCreate = employeeBussiness.create(employee);
                    if (resultCreate) {
                        System.out.println("Thêm thành công");
                    } else {
                        System.err.println("Thêm thất bại");
                    }
                    break;
                case 3:
                    System.out.println("Nhập mã nhân viên cần cập nhật: ");
                    String employeeId = scanner.nextLine();
                    Employee employeeUpdate = new Employee();
                    employeeUpdate.setEmpId(employeeId);
                    employeeUpdate.inputDataUpdate(scanner);
                    boolean ressultUpdate = employeeBussiness.update(employeeUpdate);
                    if (ressultUpdate) {
                        System.out.println("Cập nhật thành công");
                    } else {
                        System.err.println("Không tồn tại mã nhân viên");
                    }
                    break;
                case 4:
                    System.out.println("Nhập mã nhân viên cần cập nhật: ");
                    String empId = scanner.nextLine();
                    if (!empId.isEmpty()) {
                        System.out.println("Chọn trạng thái nhân viên:");
                        System.out.println("0. Hoạt động");
                        System.out.println("1. Nghỉ chế độ");
                        System.out.println("2. Nghỉ việc");
                        System.out.print("Lựa chọn của bạn: ");
                        choice = Integer.parseInt(scanner.nextLine());
                        if (choice < 0 || choice > 2) {
                            System.err.println("Lựa chọn không hợp lệ.");
                            return;
                        }
                        Employee empUpdate = new Employee();
                        empUpdate.setEmpId(empId);
                        switch (choice) {
                            case 0:
                                empUpdate.setEmpStatus((short) 0);
                                break;
                            case 1:
                                empUpdate.setEmpStatus((short) 1);
                                break;
                            case 2:
                                empUpdate.setEmpStatus((short) 2);
                                break;
                        }
                        boolean result = employeeBussiness.updateStatus(empUpdate);
                        if (result) {
                            System.out.println("Cập nhật trạng thái thành công.");
                        } else {
                            System.err.println("Cập nhật trạng thái thất bại.");
                        }
                    } else {
                        System.err.println("Mời bạn nhập mã nhân viên cần cập nhật");
                    }
                    break;
                case 5:
                    System.out.println("Nhập tên nhân viên cần tìm: ");
                    String empName = scanner.nextLine().trim();
                    if (!empName.isEmpty()) {
                        System.out.println("Danh sách nhân viên");
                        List<Employee> listEmpByName = employeeBussiness.findByName(empName);
                        if (!listEmpByName.isEmpty()) {
                            listEmpByName.stream().forEach(employee1 -> employee1.displayData());
                        } else {
                            System.err.println("Không tìm thấy tên nhân viên");
                        }
                    } else {
                        System.err.println("Không tìm thấy tên sản phẩm");
                    }
                    break;
                case 6:
                    isExit = false;
                    break;
                default:
                    System.err.println("Mời bạn nhập từ 1-6");
            }
        } while (isExit);
    }
}
