/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hfmapitest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import oracle.epm.fm.common.datatype.transport.DIMENSIONTYPE;
import oracle.epm.fm.common.datatype.transport.Dimension;
import oracle.epm.fm.common.datatype.transport.GridDefinition;
import oracle.epm.fm.common.datatype.transport.HsxRequestContext;
import oracle.epm.fm.common.datatype.transport.POVDimension;
import oracle.epm.fm.common.datatype.transport.ServerTaskInfo;
import oracle.epm.fm.common.datatype.transport.SessionInfo;
import oracle.epm.fm.common.datatype.transport.WEBOMDATAGRIDTASKMASKENUM;
import oracle.epm.fm.domainobject.application.JHsxClient;
import oracle.epm.fm.domainobject.application.SessionOM;
import oracle.epm.fm.domainobject.data.grid.DataGridOM;
import oracle.epm.fm.domainobject.metadata.MetadataOM;
import oracle.epm.fm.domainobject.pov.Slice;
import oracle.epm.fm.domainobject.security.Security;

/**
 *
 * @author pablo
 */
public class HFMApiTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
			Security security = new Security();
			security.authenticateUser("admin", "p4ssw0rD", null);
			String token = security.getSsoToken();
			
			HsxRequestContext context = new HsxRequestContext(null, token, null, null);
			JHsxClient client = JHsxClient.getInstance();
			SessionOM som = new SessionOM();
			SessionInfo session = som.createSession(token, Locale.ENGLISH, "HFMCLUSTER", "FRUIT");
			System.out.println("Established HFM Session with ID: " + session.getSessionId());

			List<POVDimension> pov = new ArrayList<POVDimension>();
			
			pov.add(new POVDimension("Account", null, "1001G", null, null,false, false,0,0));
			pov.add(new POVDimension("Entity", null, "TOTAPPLE_MGT", null, null,false, false,0,0));
			pov.add(new POVDimension("Scenario", null, "Actual", null, null,false, false,0,0));
			pov.add(new POVDimension("Year", null, "2017", null, null,false, false,0,0));
			pov.add(new POVDimension("Period", null, "Sep", null, null,false, false,0,0));
			pov.add(new POVDimension("View", null, "Periodic", null, null,false, false,0,0));
			pov.add(new POVDimension("Value", null, "USD", null, null,false, false,0,0));
			pov.add(new POVDimension("ICP", null, "[ICP Top]", null, null,false, false,0,0));
			pov.add(new POVDimension("Custom1", null, "TopC1", null, null,false, false,0,0));
			pov.add(new POVDimension("Custom2", null, "TopC2", null, null,false, false,0,0));
			pov.add(new POVDimension("Custom3", null, "TopC3", null, null,false, false,0,0));
			pov.add(new POVDimension("Custom4", null, "TopC4", null, null,false, false,0,0));
			
			
			
			GridDefinition gd = new GridDefinition();
			gd.setPovDims(pov);	
			
			//fill in gridDef with appropriate values..
			DataGridOM dgOM = new DataGridOM(session);			
			String gridID = dgOM.defineGrid(gd);
			
   
			ServerTaskInfo consolidateTask = dgOM.executeServerTask(token, WEBOMDATAGRIDTASKMASKENUM.WEBOM_DATAGRID_TASK_CONSOLIDATEALLWITHDATA, null);
			
			
//			AdministrationOM admin = new AdministrationOM(session);
//			Map<String,Member> acctMap = new HashMap<String,Member>();
			Map<String,String> sliceMembers = new HashMap<String,String>();
//			
			MetadataOM metadataOM = new MetadataOM(session);
//			DataOM dataOM = new DataOM(session);
			
			List<DIMENSIONTYPE> dimType = new ArrayList<DIMENSIONTYPE>();
	        
	        dimType.add(DIMENSIONTYPE.ALL);
	        
	        List<Dimension> dimensions = metadataOM.getDimensions(dimType);
	        
	        
		} catch (Exception e){
			e.printStackTrace();
		} 
    }
    
}
