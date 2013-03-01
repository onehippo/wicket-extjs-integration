package org.wicketstuff.js.ext;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;
import org.wicketstuff.js.ext.form.ExtFormPanel;
import org.wicketstuff.js.ext.layout.DefaultLayout;

public class TestExtComponent extends WicketTester {

    @Test
    public void testExtComponentRenders() {
        TestPage page = new TestPage();
        page.add(new ExtComponent("ext") {
        });
        WicketTester tester = new WicketTester();
        tester.startPage(page);
    }

    @Test
    public void testExtFormPanelRenders() {
        TestPage page = new TestPage();
        page.add(new ExtFormPanel("ext"));
        WicketTester tester = new WicketTester();
        tester.startPage(page);
    }

    @Test
    public void testExtContainerWithItem() {
        TestPage page = new TestPage();
        final ExtContainer container = new ExtContainer("ext");
        page.add(container);
        container.addItem(new ExtComponent("item") {
        });
        WicketTester tester = new WicketTester();
        tester.startPage(page);
        tester.dumpPage();
    }

    @Test
    public void testExtContainerWithLayout() {
        TestPage page = new TestPage();
        final ExtContainer container = new ExtContainer("ext");
        page.add(container);
        container.setLayout(new DefaultLayout());
        WicketTester tester = new WicketTester();
        tester.startPage(page);
        tester.dumpPage();
    }

    @Test
    public void testExtPanelWithButton() {
        TestPage page = new TestPage();
        final ExtPanel container = new ExtPanel("ext");
        page.add(container);
        container.addButton(new ExtButton("item", Model.of("hello")));
        WicketTester tester = new WicketTester();
        tester.startPage(page);
        tester.assertContains("hello");
        tester.dumpPage();
    }

    @Test
    public void testExtComponentWithNonExtChild() {
        TestPage page = new TestPage();
        TestPanel panel = new TestPanel("ext");
        panel.add(new Label("child", Model.of("hello")));
        page.add(panel);
        WicketTester tester = new WicketTester();
        tester.startPage(page);
        tester.assertContains("hello");
        tester.dumpPage();
    }

}
