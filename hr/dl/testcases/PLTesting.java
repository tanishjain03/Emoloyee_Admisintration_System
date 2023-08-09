import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import com.ttj.hr.dl.dao.*;
import com.ttj.hr.dl.dto.*;
import com.ttj.hr.dl.interfaces.dao.*;
import com.ttj.hr.dl.interfaces.dto.*;
import com.ttj.hr.dl.exceptions.*;
import java.util.*;

class StudentTableModel extends AbstractTableModel
{
private Set<DesignationDTOInteface> designations;
private String title[];
StudentTableModel()
{
populateDataStructure();
}
private void populateDataStructure()
{
title=new String[2];
title[0]="Code";
title[1]="Designation";
DesignationDAOInterface designationDAO=new DesignationDAO();
designations=designationDAO.getAll();
}
public int getRowCount()
{
return designations.size();
}
public int getColumnCount()
{
return title.length;
}
public String getColumnName(int columnIndex)
{
return title[columnIndex];
}
public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0)
{

}
if(columnIndex==1)
{

}
}
public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}
public Class getColumnClass(int columnIndex)
{
Class c=null;
try
{
if(columnIndex==0 || columnIndex==1) c=Class.forName("java.lang.Integer");
if(columnIndex==2) c=Class.forName("java.lang.String");
}catch(Exception e)
{
System.out.println(e);
}
return c;
}
}
class swing3 extends JFrame
{
private JTable table;
private JScrollPane jsp;
private StudentTableModel studentTableModel;
private Container container;
swing3()
{
studentTableModel=new StudentTableModel();
table=new JTable(studentTableModel);
Font font=new Font("Times New Roman",Font.PLAIN,24);
table.setFont(font);
table.setRowHeight(30);
table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
table.getTableHeader().setReorderingAllowed(false);
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();
container.setLayout(new BorderLayout());
container.add(jsp);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
int width=600;
int height=600;
setSize(width,height);
int x=(d.width/2)-(width/2);
int y=(d.height/2)-(height/2);
setLocation(x,y);
setVisible(true);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
}
class swing3psp
{
public static void main(String gg[])
{
swing3 s=new swing3();
}
}