package org.kainos.ea.api;

import org.kainos.ea.cli.Contractor;
import org.kainos.ea.cli.Employee;
import org.kainos.ea.cli.IPayable;
import org.kainos.ea.cli.SalesEmployee;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    public List<IPayable> getEmployees(){
        Employee employee = new Employee(1, "Shaun", 20000);
        SalesEmployee salesEmployee = new SalesEmployee(1, "John", 21000, 1000, 0.01f);
        Contractor contractor = new Contractor("Jamie", 800, 4);
        List<IPayable> employees = new ArrayList<>();
        employees.add(employee);
        employees.add(salesEmployee);
        employees.add(contractor);

        for (IPayable e: employees){
            System.out.println(e.calcPay());
        }

        return employees;
    }
}
