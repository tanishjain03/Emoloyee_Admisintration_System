package com.ttj.hr.bl.managers;
import com.ttj.hr.bl.interfaces.managers.*;
import com.ttj.hr.bl.interfaces.pojo.*;
import com.ttj.hr.bl.pojo.*;
import com.ttj.hr.bl.exceptions.*;
import com.ttj.hr.dl.interfaces.dao.*;
import com.ttj.hr.dl.interfaces.dto.*;
import com.ttj.hr.dl.dao.*;
import com.ttj.hr.dl.dto.*;
import com.ttj.hr.dl.exceptions.*;
import java.util.*;
import java.math.*;
import com.ttj.enums.*;
import java.text.*;

public class EmployeeManager implements EmployeeManagerInterface
{

private Map<String,EmployeeInterface> employeeIdWiseEmployeesMap;
private Map<String,EmployeeInterface> panNumberWiseEmployeesMap;
private Map<String,EmployeeInterface> aadhaarCardNumberWiseEmployeesMap;
private Map<Integer,Set<EmployeeInterface>> designationCodeWiseEmployeesMap;
private Set<EmployeeInterface> employeesSet;

private static EmployeeManager employeeManager=null;
private EmployeeManager() throws BLException
{
populateDataStructures();
}

private void populateDataStructures() throws BLException
{
this.employeeIdWiseEmployeesMap=new HashMap<>();
this.panNumberWiseEmployeesMap=new HashMap<>();
this.aadhaarCardNumberWiseEmployeesMap=new HashMap<>();
this.designationCodeWiseEmployeesMap=new HashMap<>();
this.employeesSet=new TreeSet<>();
try
{
Set<EmployeeDTOInterface> dlEmployees;
dlEmployees=new EmployeeDAO().getAll();
EmployeeInterface employee;
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
DesignationInterface designation;
Set<EmployeeInterface> ets;
for(EmployeeDTOInterface dlEmployee:dlEmployees)
{
employee=new Employee();
employee.setEmployeeId(dlEmployee.getEmployeeId());
employee.setName(dlEmployee.getName());
designation=designationManager.getDesignationByCode(dlEmployee.getDesignationCode());
employee.setDesignation(designation);
employee.setDateOfBirth(dlEmployee.getDateOfBirth());
if(dlEmployee.getGender()=='M') employee.setGender(GENDER.MALE);
else employee.setGender(GENDER.FEMALE);
employee.setIsIndian(dlEmployee.getIsIndian());
employee.setBasicSalary(dlEmployee.getBasicSalary());
employee.setPANNumber(dlEmployee.getPANNumber());
employee.setAadhaarCardNumber(dlEmployee.getAadhaarCardNumber());
this.employeeIdWiseEmployeesMap.put(employee.getEmployeeId().toUpperCase(),employee);
this.panNumberWiseEmployeesMap.put(employee.getPANNumber().toUpperCase(),employee);
this.aadhaarCardNumberWiseEmployeesMap.put(employee.getAadhaarCardNumber().toUpperCase(),employee);
this.employeesSet.add(employee);
ets=designationCodeWiseEmployeesMap.get(designation.getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(employee);
designationCodeWiseEmployeesMap.put(new Integer(designation.getCode()),ets);
}
else
{
ets.add(employee);
}
}
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}

public static EmployeeManagerInterface getEmployeeManager() throws BLException
{
if(employeeManager==null) employeeManager=new EmployeeManager();
return employeeManager;
}


public void addEmployee(EmployeeInterface employee) throws BLException
{
BLException blException=new BLException();
String employeeId=employee.getEmployeeId();
String name=employee.getName();
DesignationInterface designation=employee.getDesignation();
Date dateOfBirth=employee.getDateOfBirth();
char gender=employee.getGender();
boolean isIndian=employee.getIsIndian();
BigDecimal basicSalary=employee.getBasicSalary();
String panNumber=employee.getPANNumber();
String aadhaarCardNumber=employee.getAadhaarCardNumber();
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
if(employeeId!=null)
{
employeeId=employeeId.trim();
if(employeeId.length()>0)
{
blException.addException("employeeId","Employee Id should be nil/empty");
}
}
if(name==null)
{
blException.addException("name","Name required"); 
}
else
{
name=name.trim();
if(name.length()==0) blException.addException("name","Name required"); 
}
if(designation==null)
{
blException.addException("designation","Designation required");
}
else
{
if(designationManager.designationCodeExists(designation.getCode())==false)
{
blException.addException("designation","Invalid designation");
}
}
if(dateOfBirth==null) blException.addException("dateOfBirth","Date of birth required");
if(gender==' ') blException.addException("gender","Gender required");
if(basicSalary==null) blException.addException("basicSalary","Basic salary required");
else if(basicSalary.signum()==-1) blException.addException("basicSalary","Basic salary can not be negative");
if(panNumber==null)
{
blException.addException("panNumber","PAN number required"); 
}
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0) blException.addException("panNumber","PAN number required"); 
}
if(aadhaarCardNumber==null)
{
blException.addException("aadhaarCardNumber","Aadhaar card number required"); 
}
else
{
aadhaarCardNumber=aadhaarCardNumber.trim();
if(aadhaarCardNumber.length()==0) blException.addException("aadhaarcardNumber","Aadhaar card number required"); 
}
if(panNumber!=null && panNumber.length()>0)
{
if(panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase()))
{
blException.addException("panNumber","PAN number "+panNumber+" exists");
}
}
if(aadhaarCardNumber!=null && aadhaarCardNumber.length()>0)
{
if(aadhaarCardNumberWiseEmployeesMap.containsKey(aadhaarCardNumber.toUpperCase()))
{
blException.addException("aadhaarCardNumber","Aadhaar card number "+aadhaarCardNumber+" exists");
}
}
if(blException.hasExceptions()) throw blException;
try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
EmployeeDTOInterface dlEmployee=new EmployeeDTO();
dlEmployee.setName(name);
dlEmployee.setDesignationCode(designation.getCode());
dlEmployee.setDateOfBirth(dateOfBirth);
dlEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dlEmployee.setIsIndian(isIndian);
dlEmployee.setBasicSalary(basicSalary);
dlEmployee.setPANNumber(panNumber);
dlEmployee.setAadhaarCardNumber(aadhaarCardNumber);
employeeDAO.add(dlEmployee);
employee.setEmployeeId(dlEmployee.getEmployeeId());
EmployeeInterface dsEmployee=new Employee();
dsEmployee.setEmployeeId(employee.getEmployeeId());
dsEmployee.setName(name);
dsEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));
dsEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dsEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setIsIndian(isIndian);
dsEmployee.setBasicSalary(basicSalary);
dsEmployee.setPANNumber(panNumber);
dsEmployee.setAadhaarCardNumber(aadhaarCardNumber);
employeesSet.add(dsEmployee);
employeeIdWiseEmployeesMap.put(dsEmployee.getEmployeeId().toUpperCase(),dsEmployee);
panNumberWiseEmployeesMap.put(panNumber.toUpperCase(),dsEmployee);
aadhaarCardNumberWiseEmployeesMap.put(aadhaarCardNumber.toUpperCase(),dsEmployee);
Set<EmployeeInterface> ets;
ets=designationCodeWiseEmployeesMap.get(dsEmployee.getDesignation().getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(dsEmployee);
designationCodeWiseEmployeesMap.put(new Integer(dsEmployee.getDesignation().getCode()),ets);
}
else
{
ets.add(dsEmployee);
}
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}

public void updateEmployee(EmployeeInterface employee) throws BLException
{
BLException blException=new BLException();
String employeeId=employee.getEmployeeId();
String name=employee.getName();
DesignationInterface designation=employee.getDesignation();
Date dateOfBirth=employee.getDateOfBirth();
char gender=employee.getGender();
boolean isIndian=employee.getIsIndian();
BigDecimal basicSalary=employee.getBasicSalary();
String panNumber=employee.getPANNumber();
String aadhaarCardNumber=employee.getAadhaarCardNumber();
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
if(employeeId==null)
{
blException.addException("employeeId","Employee Id required");
}
else
{
employeeId=employeeId.trim();
if(employeeId.length()==0) blException.addException("employeeId","Employee Id required");
else
{
if(employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false)
{
blException.addException("employeeId","Ivalid employee Id "+employeeId);
throw blException;
}
}
}
if(name==null)
{
blException.addException("name","Name required"); 
}
else
{
name=name.trim();
if(name.length()==0) blException.addException("name","Name required"); 
}
if(designation==null)
{
blException.addException("designation","Designation required");
}
else
{
if(designationManager.designationCodeExists(designation.getCode())==false)
{
blException.addException("designation","Invalid designation");
}
}
if(dateOfBirth==null) blException.addException("dateOfBirth","Date of birth required");
if(gender==' ') blException.addException("gender","Gender required");
if(basicSalary==null) blException.addException("basicSalary","Basic salary required");
else if(basicSalary.signum()==-1) blException.addException("basicSalary","Basic salary can not be negative");
if(panNumber==null)
{
blException.addException("panNumber","PAN number required"); 
}
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0) blException.addException("panNumber","PAN number required"); 
}
if(aadhaarCardNumber==null)
{
blException.addException("aadhaarCardNumber","Aadhaar card number required"); 
}
else
{
aadhaarCardNumber=aadhaarCardNumber.trim();
if(aadhaarCardNumber.length()==0) blException.addException("aadhaarcardNumber","Aadhaar card number required"); 
}
if(panNumber!=null && panNumber.length()>0)
{
EmployeeInterface ee=panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
if(ee!=null && ee.getEmployeeId().equalsIgnoreCase(employeeId)==false)
{
blException.addException("panNumber","PAN number "+panNumber+" exists");
}
}
if(aadhaarCardNumber!=null && aadhaarCardNumber.length()>0)
{
EmployeeInterface ee=aadhaarCardNumberWiseEmployeesMap.get(aadhaarCardNumber.toUpperCase());
if(ee!=null && ee.getEmployeeId().equalsIgnoreCase(employeeId)==false)
{
blException.addException("aadhaarCardNumber","Aadhaar card number "+aadhaarCardNumber+" exists");
}
}
if(blException.hasExceptions()) throw blException;
try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
EmployeeDTOInterface dlEmployee=new EmployeeDTO();
EmployeeInterface dsEmployee;
dsEmployee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
String oldPANNumber=dsEmployee.getPANNumber();
String oldAadhaarCardNumber=dsEmployee.getAadhaarCardNumber();
int oldDesignationCode=dsEmployee.getDesignation().getCode();
dlEmployee.setEmployeeId(dsEmployee.getEmployeeId());
dlEmployee.setName(name);
dlEmployee.setDesignationCode(designation.getCode());
dlEmployee.setDateOfBirth(dateOfBirth);
dlEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dlEmployee.setIsIndian(isIndian);
dlEmployee.setBasicSalary(basicSalary);
dlEmployee.setPANNumber(panNumber);
dlEmployee.setAadhaarCardNumber(aadhaarCardNumber);
employeeDAO.update(dlEmployee);
dsEmployee.setName(name);
dsEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));
dsEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dsEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setIsIndian(isIndian);
dsEmployee.setBasicSalary(basicSalary);
dsEmployee.setPANNumber(panNumber);
dsEmployee.setAadhaarCardNumber(aadhaarCardNumber);
employeesSet.remove(dsEmployee);
employeeIdWiseEmployeesMap.remove(employeeId.toUpperCase());
panNumberWiseEmployeesMap.remove(oldPANNumber.toUpperCase());
aadhaarCardNumberWiseEmployeesMap.remove(oldAadhaarCardNumber.toUpperCase());
Set<EmployeeInterface> ets=designationCodeWiseEmployeesMap.get(oldDesignationCode);
ets.remove(dsEmployee);
employeesSet.add(dsEmployee);
employeeIdWiseEmployeesMap.put(dsEmployee.getEmployeeId().toUpperCase(),dsEmployee);
panNumberWiseEmployeesMap.put(panNumber.toUpperCase(),dsEmployee);
aadhaarCardNumberWiseEmployeesMap.put(aadhaarCardNumber.toUpperCase(),dsEmployee);
ets=designationCodeWiseEmployeesMap.get(dsEmployee.getDesignation().getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(dsEmployee);
designationCodeWiseEmployeesMap.put(new Integer(dsEmployee.getDesignation().getCode()),ets);
}
else
{
ets.add(dsEmployee);
}
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}

public void removeEmployee(String employeeId) throws BLException
{
if(employeeId==null)
{
BLException blException=new BLException();
blException.addException("employeeId","Employee Id required");
throw blException;
}
else
{
employeeId=employeeId.trim();
if(employeeId.length()==0)
{
BLException blException=new BLException();
blException.addException("employeeId","Employee Id required");
throw blException;
}
else
{
if(employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false)
{
BLException blException=new BLException();
blException.addException("employeeId","Ivalid employee Id "+employeeId);
throw blException;
}
}
}
try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
EmployeeInterface dsEmployee;
dsEmployee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
employeeDAO.delete(dsEmployee.getEmployeeId());
employeesSet.remove(dsEmployee);
employeeIdWiseEmployeesMap.remove(employeeId.toUpperCase());
panNumberWiseEmployeesMap.remove(dsEmployee.getPANNumber().toUpperCase());
aadhaarCardNumberWiseEmployeesMap.remove(dsEmployee.getAadhaarCardNumber().toUpperCase());
Set<EmployeeInterface> ets=designationCodeWiseEmployeesMap.get(dsEmployee.getDesignation().getCode());
ets.remove(dsEmployee);
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}

public EmployeeInterface getEmployeeByEmployeeId(String employeeId) throws BLException
{
employeeId=employeeId.trim();
EmployeeInterface dsEmployee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
if(dsEmployee==null)
{
BLException blException=new BLException();
blException.addException("employeeId","Invalid employee Id "+employeeId);
throw blException;
}
EmployeeInterface employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
DesignationInterface dsDesignation=dsEmployee.getDesignation();
DesignationInterface designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadhaarCardNumber(dsEmployee.getAadhaarCardNumber());
return employee;
}

public EmployeeInterface getEmployeeByPANNumber(String panNumber) throws BLException
{
panNumber=panNumber.trim();
EmployeeInterface dsEmployee=panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
if(dsEmployee==null)
{
BLException blException=new BLException();
blException.addException("panNumber","Invalid PAN number "+panNumber);
throw blException;
}
EmployeeInterface employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
DesignationInterface dsDesignation=dsEmployee.getDesignation();
DesignationInterface designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadhaarCardNumber(dsEmployee.getAadhaarCardNumber());
return employee;
}

public EmployeeInterface getEmployeeByAadhaarCardNumber(String aadhaarCardNumber) throws BLException
{
aadhaarCardNumber=aadhaarCardNumber.trim();
EmployeeInterface dsEmployee=aadhaarCardNumberWiseEmployeesMap.get(aadhaarCardNumber.toUpperCase());
if(dsEmployee==null)
{
BLException blException=new BLException();
blException.addException("aadhaarCardNumber","Invalid Aadhaar card number "+aadhaarCardNumber);
throw blException;
}
EmployeeInterface employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
DesignationInterface dsDesignation=dsEmployee.getDesignation();
DesignationInterface designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadhaarCardNumber(dsEmployee.getAadhaarCardNumber());
return employee;
}

public int getEmployeeCount()
{
return employeesSet.size();
}

public boolean employeeIdExists(String employeeId)
{
return employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase());
}

public boolean employeePANNumberExists(String panNumber)
{
return panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase());
}

public boolean employeeAadhaarCardNumberExists(String aadhaarCardNumber)
{
return aadhaarCardNumberWiseEmployeesMap.containsKey(aadhaarCardNumber.toUpperCase());
}

public Set<EmployeeInterface> getEmployees()
{
Set<EmployeeInterface> employees=new TreeSet<>();
EmployeeInterface employee;
DesignationInterface dsDesignation;
DesignationInterface designation;
for(EmployeeInterface dsEmployee: employeesSet)
{
employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
dsDesignation=dsEmployee.getDesignation();
designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadhaarCardNumber(dsEmployee.getAadhaarCardNumber());
employees.add(employee);
}
return employees;
}

public Set<EmployeeInterface> getEmployeesByDesignationCode(int designationCode) throws BLException
{
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
if(designationManager.designationCodeExists(designationCode)==false)
{
BLException blException=new BLException();
blException.addException("designationCode","Invalid designation code : "+designationCode);
throw blException;
}
Set<EmployeeInterface> employeesByDesignationCode;
employeesByDesignationCode=new TreeSet<>();
Set<EmployeeInterface> dsEmployeesByDesignationCode=designationCodeWiseEmployeesMap.get(designationCode);
if(dsEmployeesByDesignationCode==null) return employeesByDesignationCode;
DesignationInterface designation;
DesignationInterface dsDesignation;
EmployeeInterface employeeByDesignationCode;
for(EmployeeInterface dsEmployeeByDesignationCode:dsEmployeesByDesignationCode)
{
employeeByDesignationCode=new Employee();
employeeByDesignationCode.setEmployeeId(dsEmployeeByDesignationCode.getEmployeeId());
employeeByDesignationCode.setName(dsEmployeeByDesignationCode.getName());
dsDesignation=dsEmployeeByDesignationCode.getDesignation();
designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employeeByDesignationCode.setDesignation(designation);
employeeByDesignationCode.setDateOfBirth((Date)dsEmployeeByDesignationCode.getDateOfBirth().clone());
employeeByDesignationCode.setGender((dsEmployeeByDesignationCode.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employeeByDesignationCode.setIsIndian(dsEmployeeByDesignationCode.getIsIndian());
employeeByDesignationCode.setBasicSalary(dsEmployeeByDesignationCode.getBasicSalary());
employeeByDesignationCode.setPANNumber(dsEmployeeByDesignationCode.getPANNumber());
employeeByDesignationCode.setAadhaarCardNumber(dsEmployeeByDesignationCode.getAadhaarCardNumber());
employeesByDesignationCode.add(employeeByDesignationCode);
}
return employeesByDesignationCode;
}

public int getEmployeeCountByDesignationCode(int designationCode) throws BLException
{
Set<EmployeeInterface> ets;
ets=this.designationCodeWiseEmployeesMap.get(designationCode);
if(ets==null) return 0;
return ets.size();
}

public boolean designationAllotted(int designationCode) throws BLException
{
return this.designationCodeWiseEmployeesMap.containsKey(designationCode);
}

}