import com.ttj.hr.bl.managers.*;
import com.ttj.hr.bl.interfaces.managers.*;
import com.ttj.hr.bl.interfaces.pojo.*;
import com.ttj.hr.bl.pojo.*;
import com.ttj.hr.bl.exceptions.*;
import com.ttj.enums.*;
import java.util.*;
import java.text.*;
import java.math.*;

class EmployeeManagerAddTestCase
{
public static void main(String gg[])
{
try
{
String name="Kishan Tapadia";
DesignationInterface designation=new Designation();
designation.setCode(2);
Date dateOfBirth=new Date();
char gender='M';
boolean isIndian=true;
BigDecimal basicSalary=new BigDecimal("400000");
String panNumber="YR211098IT";
String aadhaarCardNumber="YR123456";
EmployeeInterface employee=new Employee();
employee.setName(name);
employee.setDesignation(designation);
employee.setDateOfBirth(dateOfBirth);
employee.setGender(GENDER.MALE);
employee.setIsIndian(isIndian);
employee.setBasicSalary(basicSalary);
employee.setPANNumber(panNumber);
employee.setAadhaarCardNumber(aadhaarCardNumber);

EmployeeManagerInterface employeeManager=EmployeeManager.getEmployeeManager();
employeeManager.addEmployee(employee);
System.out.println("Employee added with employee id : "+employee.getEmployeeId());
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