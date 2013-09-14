package com.exacttarget.fuelsdk.soap;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.exacttarget.fuelsdk.ETClient;
import com.exacttarget.fuelsdk.ETConfiguration;
import com.exacttarget.fuelsdk.ETContentAreaService;
import com.exacttarget.fuelsdk.ETSdkException;
import com.exacttarget.fuelsdk.ETServiceResponse;
import com.exacttarget.fuelsdk.filter.ETFilter;
import com.exacttarget.fuelsdk.filter.ETFilterOperators;
import com.exacttarget.fuelsdk.filter.ETSimpleFilter;
import com.exacttarget.fuelsdk.model.ETContentArea;

@Ignore
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ETContentAreaServiceTest {

	protected static Logger logger = Logger.getLogger(ETContentAreaServiceTest.class);
	
	protected ETContentAreaService service;
	protected ETContentArea etObject;
	protected ETFilter filter;
	protected ETFilter filterUpdated;
	
	protected ETClient client = null;
	protected ETConfiguration configuration = null;
	
	@Before
    public void setUp()
        throws ETSdkException
    {
        configuration = new ETConfiguration("/fuelsdk-test.properties");
        client = new ETClient(configuration);
    	service = new ETContentAreaServiceImpl();
		filter = new ETSimpleFilter("name", ETFilterOperators.EQUALS, "TEST Content Area");
		filterUpdated = new ETSimpleFilter("name", ETFilterOperators.EQUALS, "TEST Content Area UPDATED");
		
		etObject = new ETContentArea();
		etObject.setName("TEST Content Area");
		etObject.setContent("TEST CONTENT AREA CONTENT");
	}
	
	@Test
	public void TestGetCollectionService() throws ETSdkException
	{
		ETServiceResponse<ETContentArea> response =  service.get(client);
		
		Assert.assertNotNull(response);
		Assert.assertTrue(response.getStatus());
		Assert.assertNotNull(response.getResults());
		
		for (ETContentArea ret : response.getResults()) {
			logger.debug(ret.toString());
		}
	}
	
	@Test
	public void TestCRUDService() throws ETSdkException
	{
		
		TestPost();
		
		ETContentArea found = TestRetrieveSingle();
		
		TestPatch(found);
		
		ETContentArea foundUpdated = TestRetrieveSingleUpdated();
		
		DeleteSingle(foundUpdated);
		
	}

	protected void TestPatch(ETContentArea found) throws ETSdkException {
		
		found.setName("TEST Content Area UPDATED");
		
		ETServiceResponse<ETContentArea> response = service.patch(client, found);
		Assert.assertTrue(response.getStatus());
		
	}
	
	protected ETContentArea TestRetrieveSingle() throws ETSdkException {
		ETServiceResponse<ETContentArea> response = service.get(client, filter);
		Assert.assertNotNull(response);
		Assert.assertTrue(response.getStatus());
		Assert.assertNotNull(response.getResults());
		Assert.assertEquals(1, response.getResults().size());
		logger.debug(response.getResults().get(0));
		return response.getResults().get(0);
		
	}
	
	protected ETContentArea TestRetrieveSingleUpdated() throws ETSdkException {
		ETServiceResponse<ETContentArea> response = service.get(client, filterUpdated);
		Assert.assertNotNull(response);
		Assert.assertTrue(response.getStatus());
		Assert.assertNotNull(response.getResults());
		Assert.assertEquals(1, response.getResults().size());
		logger.debug(response.getResults().get(0));
		return response.getResults().get(0);
	}

	protected void TestPost() throws ETSdkException
	{
		ETServiceResponse<ETContentArea> response =  service.post(client, etObject);
		Assert.assertTrue(response.getStatus());
	}
		
	
	protected void DeleteSingle(ETContentArea etObject) throws ETSdkException
	{
				
		ETServiceResponse<ETContentArea> response =  service.delete(client, etObject);
		Assert.assertTrue(response.getStatus());
		 
	}
}
