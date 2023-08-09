import com.ttj.hr.dl.exceptions.*;
import com.ttj.hr.dl.interfaces.dao.*;
import com.ttj.hr.dl.dao.*;
public class DesignationGetCountTestCase
{
public static void main(String args[])
{
try
{
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
System.out.println("Designation count : "+designationDAO.getCount());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}