package org.wicketstuff.js.ext;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.HttpSessionStore;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.session.ISessionStore;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start
 * class.
 *
 * @see de.fj.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
    /**
     * Constructor
     */
    public WicketApplication() {
    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    public Class<? extends WebPage> getHomePage() {
        return ExamplesHome.class;
    }

    @Override
    protected ISessionStore newSessionStore() {
        // return new SecondLevelCacheSessionStore(this, new DiskPageStore());
        return new HttpSessionStore(this);
    }

    @Override
    protected void init() {
        mountBookmarkablePage("window/hello.html", HelloWorld.class);
        mountBookmarkablePage("window/layout.html", WindowLayout.class);
        mountBookmarkablePage("layout/accordion.html", Accordion.class);
        mountBookmarkablePage("tabs/tabs.html", Tabs.class);
        mountBookmarkablePage("grid/array-grid.html", ArrayGrid.class);
        mountBookmarkablePage("message-box/msg-box.html", MessageBox.class);
        mountBookmarkablePage("layout/column.html", ColumnLayoutExample.class);
        mountBookmarkablePage("layout/table.html", TableLayoutExample.class);
        mountBookmarkablePage("form/dynamic.html", DynamicForms.class);
        getMarkupSettings().setStripWicketTags(true);

        getResourceSettings().setResourcePollFrequency(null);
    }

}
