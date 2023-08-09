import com.ttj.hr.dl.interfaces.dao.*;
import com.ttj.hr.dl.interfaces.dto.*;
import com.ttj.hr.dl.dao.*;
import com.ttj.hr.dl.dto.*;
import com.ttj.hr.dl.exceptions.*;
import com.ttj.enums.*;
import java.util.*;
import java.math.*;
import java.text.*;
import java.io.*;

class EmployeeAddTestCase
{
public static void main(String args[])
{
try
{
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
employeeDTO.setName("Tanish Jain");
employeeDTO.setDesignationCode(2);
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
try
{
employeeDTO.setDateOfBirth(simpleDateFormat.parse("03/12/2003"));
}catch(ParseException parseException)
{
// do nothing
}
employeeDTO.setGender(GENDER.MALE);
employeeDTO.setIsIndian(true);
employeeDTO.setBasicSalary(new BigDecimal(200000000));
employeeDTO.setPANNumber("TJ211080EC");
employeeDTO.setAadhaarCardNumber("TJ");
new EmployeeDAO().add(employeeDTO);
System.out.println("Employee added with employee Id : "+employeeDTO.getEmployeeId());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}