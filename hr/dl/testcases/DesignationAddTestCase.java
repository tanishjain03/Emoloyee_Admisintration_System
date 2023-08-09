import com.ttj.hr.dl.exceptions.*;
import com.ttj.hr.dl.interfaces.dao.*;
import com.ttj.hr.dl.interfaces.dto.*;
import com.ttj.hr.dl.dto.*;
import com.ttj.hr.dl.dao.*;
public class DesignationAddTestCase
{
public static void main(String args[])
{
String title=args[0];
try
{
DesignationDTOInterface designationDTO=new DesignationDTO();
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO=new DesignationDAO();
designationDAO.add(designationDTO);
System.out.println("Designation : "+title+" added with code as : "+designationDTO.getCode());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}