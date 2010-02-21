package de.fj;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;


/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see de.fj.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{    
    /**
     * Constructor
     */
	public WicketApplication()
	{
	}
	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	public Class<? extends WebPage> getHomePage()
	{
		return ExamplesHome.class;
	}
	
	@Override
	protected void init() {
		mountBookmarkablePage("window/hello.html", HelloWorld.class);
		mountBookmarkablePage("window/layout.html", WindowLayout.class);
		mountBookmarkablePage("layout/accordion.html", Accordion.class);
		mountBookmarkablePage("tabs/tabs.html", Tabs.class);
		mountBookmarkablePage("grid/array-grid.html", ArrayGrid.class);
		
		
		getMarkupSettings().setStripWicketTags(true);
	}

}
