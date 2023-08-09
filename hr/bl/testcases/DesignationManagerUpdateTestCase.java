import com.ttj.hr.bl.managers.*;
import com.ttj.hr.bl.interfaces.managers.*;
import com.ttj.hr.bl.interfaces.pojo.*;
import com.ttj.hr.bl.pojo.*;
import com.ttj.hr.bl.exceptions.*;
import java.util.*;

class DesignationManagerUpdateTestCase
{
public static void main(String gg[])
{
DesignationInterface designation=new Designation();
designation.setCode(2);
designation.setTitle("Clerk");
try
{
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
designationManager.updateDesignation(designation);
System.out.println("Designation update");
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
List<String> properties=blException.getProperties();
for(String property:properties)
{
System.out.println(blException.getException(property));
}
}
}
}