package Test;
import Main.EmployeePayrollData;
import Main.EmployeePayrollService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;


public class EmployeePayrollTest {
    @Test
    public void given3Employees_WhenWrittenToFile_ShouldMatchEmployeeEntries() {
        EmployeePayrollData[] arrayOfEmployees = {
                new EmployeePayrollData(1, "Jeff Bezos", 100000.0, LocalDate.now()),
                new EmployeePayrollData(2, "Bill Gates", 200000.0, LocalDate.now()),
                new EmployeePayrollData(3, "Mark Zuckerberg", 300000.0, LocalDate.now())
        };
        EmployeePayrollService employeePayrollService;
        employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmployees));
        employeePayrollService.writeEmployeePayrollData(EmployeePayrollService.IOService.FILE_IO);

        employeePayrollService.printData(EmployeePayrollService.IOService.FILE_IO);
        long entries = employeePayrollService.countEntries(EmployeePayrollService.IOService.FILE_IO);
        assertEquals(3, entries);

    }

    @Test
    public void givenFile_WhenRead_ShouldReturnNumberOfEntries() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        long entries = employeePayrollService.readDataFromFile(EmployeePayrollService.IOService.FILE_IO);
        assertEquals(3, entries);
    }

    @Test
    public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() {

        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        System.out.println(employeePayrollData.size());
        assertEquals(0, employeePayrollData.size());
    }

    @Test
    public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB() {

        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        employeePayrollService.updateEmployeeSalary("Bill", 7000000.00);
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Bill");
        Assertions.assertTrue(result);

    }

    @Test
    public void givenName_WhenFound_ShouldReturnEmployeeDetails() {

        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        String name = "Rosa Diaz";
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.getEmployeeDetailsBasedOnName(EmployeePayrollService.IOService.DB_IO, name);
        String resultName = employeePayrollData.get(0).employeeName;
        Assertions.assertEquals(name, resultName);
    }


    @Test
    public void givenStartDateRange_WhenMatches_ShouldReturnEmployeeDetails() {

        String startDate = "2013-01-01";
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.getEmployeeDetailsBasedOnStartDate(EmployeePayrollService.IOService.DB_IO, startDate);
        System.out.println(employeePayrollData.size());
        assertEquals(0, employeePayrollData.size());
    }
}