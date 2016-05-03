package edu.iis.mto.staticmock;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import edu.iis.mto.staticmock.Configuration;
import edu.iis.mto.staticmock.ConfigurationLoader;
import edu.iis.mto.staticmock.IncomingNews;
import edu.iis.mto.staticmock.NewsReaderFactory;
import edu.iis.mto.staticmock.reader.NewsReader;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;

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

	@Test
	public void nonSubscriberTest() {
		IncomingNews incomingNews = new IncomingNews();
		when(newsReader.read()).thenReturn(incomingNews);
		NewsLoader newsLoader = new NewsLoader();
		PublishableNews publishableNews = newsLoader.loadNews();
		publishableNews.addPublicInfo("content");
		assertThat(publishableNews.getPublicContent().size(), is(1));
	}

	@Test
	public void subscriberTest() {
		IncomingNews incomingNews = new IncomingNews();
		when(newsReader.read()).thenReturn(incomingNews);
		NewsLoader newsLoader = new NewsLoader();
		PublishableNews publishableNews = newsLoader.loadNews();
		publishableNews.addForSubscription("content", SubsciptionType.A);
		assertThat(publishableNews.getSubscribentContent().size(), is(1));
	}
	
	@Test
	public void loadNewsTest(){
		NewsLoader newsLoader = new NewsLoader();
		newsLoader.loadNews();
		verify(configurationLoader).loadConfiguration();
		verify(newsReader).read();
	}

}
