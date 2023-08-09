package com.ttj.hr.bl.managers;
import com.ttj.hr.bl.interfaces.pojo.*;
import com.ttj.hr.bl.interfaces.managers.*;
import com.ttj.hr.bl.pojo.*;
import com.ttj.hr.bl.exceptions.*;
import com.ttj.hr.dl.interfaces.dto.*;
import com.ttj.hr.dl.interfaces.dao.*;
import com.ttj.hr.dl.exceptions.*;
import com.ttj.hr.dl.dao.*;
import com.ttj.hr.dl.dto.*;
import java.util.*;

public class DesignationManager implements DesignationManagerInterface
{

private Map<Integer,DesignationInterface> codeWiseDesignationsMap;
private Map<String,DesignationInterface> titleWiseDesignationsMap;
private Set<DesignationInterface> designationsSet;
private static DesignationManager designationManager=null;

private DesignationManager() throws BLException
{
populateDataStructures();
}

private void populateDataStructures() throws BLException
{
this.codeWiseDesignationsMap=new HashMap<>();
this.titleWiseDesignationsMap=new HashMap<>();
this.designationsSet=new TreeSet<>();
try
{
Set<DesignationDTOInterface> dlDesignations;
dlDesignations=new DesignationDAO().getAll();
DesignationInterface designation;
for(DesignationDTOInterface dlDesignation:dlDesignations)
{
designation=new Designation();
designation.setCode(dlDesignation.getCode());
designation.setTitle(dlDesignation.getTitle());
this.codeWiseDesignationsMap.put(new Integer(designation.getCode()),designation);
this.titleWiseDesignationsMap.put(designation.getTitle().toUpperCase(),designation);
this.designationsSet.add(designation);
}
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}

public static DesignationManagerInterface getDesignationManager() throws BLException
{
if(designationManager==null) designationManager=new DesignationManager();
return designationManager;
}

public void addDesignation(DesignationInterface designation) throws BLException
{
BLException blException=new BLException();
if(designation==null)
{
blException.setGenericException("Designation required");
throw blException;
}
int code=designation.getCode();
String title=designation.getTitle();
if(code!=0) blException.addException("code","Code should be zero");
if(title==null)
{
blException.addException("title","Title required");
title="";
}
else
{
title=title.trim();
if(title.length()==0)blException.addException("title","Title required");
}
if(title.length()>0)
{
if(this.titleWiseDesignationsMap.containsKey(title.toUpperCase()))
{
blException.addException("title","Designation : "+title+" exists");
}
}
if(blException.hasExceptions()) throw blException;
try
{
DesignationDTOInterface designationDTO=new DesignationDTO();
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO=new DesignationDAO();
designationDAO.add(designationDTO);
code=designationDTO.getCode();
designation.setCode(code);
Designation dsDesignation=new Designation();
dsDesignation.setCode(code);
dsDesignation.setTitle(title);
codeWiseDesignationsMap.put(code,dsDesignation);
titleWiseDesignationsMap.put(title.toUpperCase(),dsDesignation);
designationsSet.add(dsDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}

public void updateDesignation(DesignationInterface designation) throws BLException
{
BLException blException=new BLException();
if(designation==null)
{
blException.setGenericException("Designation required");
throw blException;
}
int code=designation.getCode();
String title=designation.getTitle();
if(code<=0)
{
blException.addException("code","Invalid code : "+code);
}
else
{
if(this.codeWiseDesignationsMap.containsKey(code)==false)
{
blException.addException("code","Invalid code : "+code);
throw blException;
}
}
if(title==null)
{
blException.addException("title","Title required");
title="";
}
else
{
title=title.trim();
if(title.length()==0)blException.addException("title","Title required");
}
if(title.length()>0)
{
DesignationInterface d;
d=titleWiseDesignationsMap.get(title.toUpperCase());
if(d!=null && d.getCode()!=code)
{
blException.addException("title","Designation : "+title+" exists");
}
}
if(blException.hasExceptions()) throw blException;
try
{
DesignationInterface dsDesignation=codeWiseDesignationsMap.get(code);
DesignationDTOInterface dlDesignation=new DesignationDTO();
dlDesignation.setCode(code);
dlDesignation.setTitle(title);
new DesignationDAO().update(dlDesignation);
codeWiseDesignationsMap.remove(code);
titleWiseDesignationsMap.remove(dsDesignation.getTitle().toUpperCase());
designationsSet.remove(dsDesignation);
dsDesignation.setTitle(title);
codeWiseDesignationsMap.put(code,dsDesignation);
titleWiseDesignationsMap.put(title.toUpperCase(),dsDesignation);
designationsSet.add(dsDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}

public void removeDesignation(int code) throws BLException
{
BLException blException=new BLException();
if(code<=0)
{
blException.addException("code","Invalid code : "+code);
throw blException;
}
else
{
if(this.codeWiseDesignationsMap.containsKey(code)==false)
{
blException.addException("code","Invalid code : "+code);
throw blException;
}
}
try
{
DesignationInterface dsDesignation=codeWiseDesignationsMap.get(code);
new DesignationDAO().delete(code);
codeWiseDesignationsMap.remove(code);
titleWiseDesignationsMap.remove(dsDesignation.getTitle().toUpperCase());
designationsSet.remove(dsDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}

public DesignationInterface getDesignationByCode(int code) throws BLException
{
BLException blException=new BLException();
if(code<=0)
{
blException.addException("code","Invalid code : "+code);
throw blException;
}
DesignationInterface designation;
designation=codeWiseDesignationsMap.get(code);
if(designation==null)
{
blException.addException("code","Invalid code : "+code);
throw blException;
}
DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
return d;
}

DesignationInterface getDSDesignationByCode(int code)
{
DesignationInterface designation;
designation=codeWiseDesignationsMap.get(code);
return designation;
}

public DesignationInterface getDesignationByTitle(String title) throws BLException
{
DesignationInterface designation;
designation=titleWiseDesignationsMap.get(title.toUpperCase());
if(designation==null)
{
BLException blException=new BLException();
blException.addException("title","Invalid designation : "+title);
throw blException;
}
DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
return d;
}

public int getDesignationCount()
{
return designationsSet.size();
}

public boolean designationCodeExists(int code)
{
return codeWiseDesignationsMap.containsKey(code);
}

public boolean designationTitleExists(String title)
{
return titleWiseDesignationsMap.containsKey(title.toUpperCase());
}

public Set<DesignationInterface> getDesignations()
{
Set<DesignationInterface> designations;
designations=new TreeSet<>();
designationsSet.forEach((designation)->{
DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
designations.add(d);
});
return designations;
}

}