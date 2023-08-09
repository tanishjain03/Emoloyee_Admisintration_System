import com.ttj.hr.bl.managers.*;
import com.ttj.hr.bl.interfaces.managers.*;
import com.ttj.hr.bl.interfaces.pojo.*;
import com.ttj.hr.bl.pojo.*;
import com.ttj.hr.bl.exceptions.*;
import java.util.*;

class DesignationManagerRemoveTestCase
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
try
{
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
designationManager.removeDesignation(code);
System.out.println("Designation removed");
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