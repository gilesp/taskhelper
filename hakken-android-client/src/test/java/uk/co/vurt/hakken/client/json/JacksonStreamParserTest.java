/**
 * 
 */
package uk.co.vurt.hakken.client.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.co.vurt.hakken.domain.job.DataItem;
import uk.co.vurt.hakken.domain.job.JobDefinition;

/**
 * @author giles
 * 
 */
public class JacksonStreamParserTest {

	private BufferedInputStream in = null;

	@Before
	public void setup() throws IOException {
		in = new BufferedInputStream(getClass().getResourceAsStream(
				"/json_stream_parser_1.js"));
	}

	@After
	public void teardown() throws IOException {
		if (in != null) {
			in.close();
		}

		in = null;
	}

	/**
	 * Test method for
	 * {@link uk.co.vurt.hakken.client.json.JacksonStreamParser#parseJobDefinitionStream(java.io.InputStream)}
	 * .
	 */
	@Test
	public void testParseJobDefinitionStream() {
		JacksonStreamParser streamParser = new JacksonStreamParser();
		try {
			List<JobDefinition> jobs = streamParser.parseJobDefinitionStream(in);
			assertEquals(1, jobs.size());
			JobDefinition testJob = jobs.get(0);
			assertNotNull(testJob);
			assertEquals(1l, testJob.getId().longValue());
			assertEquals("Test Job 1", testJob.getName());
			assertEquals("AWAITING", testJob.getStatus());
			assertEquals("This is a test job", testJob.getNotes());
			Set<DataItem> dataItems = testJob.getDataItems();
			assertNotNull(dataItems);
			assertEquals(1l,dataItems.size());
			//TODO test data item is parsed correctly
			assertFalse(testJob.isModified());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

}
