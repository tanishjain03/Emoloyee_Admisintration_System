package com.ttj.hr.dl.interfaces.dao;
import com.ttj.hr.dl.exceptions.*;
import com.ttj.hr.dl.interfaces.dto.*;
import java.util.*;
public interface EmployeeDAOInterface
{
public void add(EmployeeDTOInterface employeeDTO) throws DAOException;
public void update(EmployeeDTOInterface employeeDTO) throws DAOException;
public void delete(String employeeId) throws DAOException;
public Set<EmployeeDTOInterface> getAll() throws DAOException;
public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException;
public boolean isDesignationAlloted(int designationCode) throws DAOException;
public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException;
public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException;
public EmployeeDTOInterface getByAadhaarCardNumber(String aadhaarCardNumber) throws DAOException;
public boolean employeeIdExists(String employeeId) throws DAOException;
public boolean panNumberExists(String panNumber) throws DAOException;
public boolean aadhaarCardNumberExists(String aadhaarCardNumber) throws DAOException;
public int getCount() throws DAOException;
public int getCountByDesignation(int designationCode) throws DAOException;
}