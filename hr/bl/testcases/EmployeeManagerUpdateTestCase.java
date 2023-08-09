import com.ttj.hr.bl.managers.*;
import com.ttj.hr.bl.interfaces.managers.*;
import com.ttj.hr.bl.interfaces.pojo.*;
import com.ttj.hr.bl.pojo.*;
import com.ttj.hr.bl.exceptions.*;
import com.ttj.enums.*;
import java.util.*;
import java.text.*;
import java.math.*;

class EmployeeManagerUpdateTestCase
{
public static void main(String gg[])
{
try
{
String employeeId="A10000001";
String name="Yash Rughwani";
DesignationInterface designation=new Designation();
designation.setCode(1);
Date dateOfBirth=new Date();
char gender='M';
boolean isIndian=true;
BigDecimal basicSalary=new BigDecimal("99999999");
String panNumber="YR211098IT";
String aadhaarCardNumber="YR123456";
EmployeeInterface employee=new Employee();
employee.setEmployeeId(employeeId);
employee.setName(name);
employee.setDesignation(designation);
employee.setDateOfBirth(dateOfBirth);
employee.setGender(GENDER.MALE);
employee.setIsIndian(isIndian);
employee.setBasicSalary(basicSalary);
employee.setPANNumber(panNumber);
employee.setAadhaarCardNumber(aadhaarCardNumber);

EmployeeManagerInterface employeeManager=EmployeeManager.getEmployeeManager();
employeeManager.updateEmployee(employee);
System.out.println("Employee updated");
}catch(BLException blException)
{
if(blException.hasGenericException()) System.out.println(blException.getGenericException());
List<String> properties=blException.getProperties();
for(String property : properties)
{
System.out.println(blException.getException(property));
}
}
}
}