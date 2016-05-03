package edu.iis.mto.staticmock;


import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;


import org.junit.Before;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import edu.iis.mto.staticmock.Configuration;
import edu.iis.mto.staticmock.ConfigurationLoader;
import edu.iis.mto.staticmock.IncomingNews;
import edu.iis.mto.staticmock.NewsReaderFactory;
import edu.iis.mto.staticmock.reader.NewsReader;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ConfigurationLoader.class, NewsReaderFactory.class })
public class NewsLoaderTest {
	ConfigurationLoader configurationLoader;
	NewsReader newsReader;

	@Before
	public void setUp() {

		mockStatic(ConfigurationLoader.class);
		configurationLoader = mock(ConfigurationLoader.class);
		when(configurationLoader.loadConfiguration()).thenReturn(new Configuration());
		when(ConfigurationLoader.getInstance()).thenReturn(configurationLoader);

		mockStatic(NewsReaderFactory.class);
		newsReader = mock(NewsReader.class);
		when(newsReader.read()).thenReturn(new IncomingNews());
		when(NewsReaderFactory.getReader(anyString())).thenReturn(newsReader);
	}
}
