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

class EmployeeGetAllTestCase
{
public static void main(String args[])
{
try
{
Set<EmployeeDTOInterface> employeesDTO;
employeesDTO=new EmployeeDAO().getAll();
for(EmployeeDTOInterface employeeDTO:employeesDTO)
{
System.out.println(employeeDTO.getEmployeeId());
System.out.println(employeeDTO.getName());
System.out.println(employeeDTO.getDesignationCode());
System.out.println(employeeDTO.getDateOfBirth());
System.out.println(employeeDTO.getGender());
System.out.println(employeeDTO.getIsIndian());
System.out.println(employeeDTO.getBasicSalary());
System.out.println(employeeDTO.getPANNumber());
System.out.println(employeeDTO.getAadhaarCardNumber());
System.out.println("-----------------------------");
}
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}