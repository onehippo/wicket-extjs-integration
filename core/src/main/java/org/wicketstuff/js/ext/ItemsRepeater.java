 /**
 * 
 */
package org.wicketstuff.js.ext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;

final class ItemsRepeater extends MarkupContainer {
    private static final long serialVersionUID = 1L;

    static class ExtItem extends WebMarkupContainer {
        private static final long serialVersionUID = 1L;

        private ExtItem(final int iteration) {
            super(Integer.toString(iteration).intern());
        }

        @Override
        public void remove(final Component component) {
            getParent().remove(this);
        }

        @Override
        public void onRender(MarkupStream markupStream) {
            final Component item = get("item");
            item.render(null);
        }
    }

    private int numItems = 0;
    
    ItemsRepeater(String id) {
        super(id);
    }

    void add(ExtComponent ec) {
        if (!"item".equals(ec.getId())) {
            throw new IllegalArgumentException("ExtComponent does not have 'item' as it's wicket id");
        }
        // Create item for loop iteration
        ExtItem item = new ExtItem(numItems++);
        add(item);
        item.add(ec);
    }

    Iterator<ExtComponent> extIterator() {
        return getExtComponents().iterator();
    }

    public final List<ExtComponent> getExtComponents() {
        final List<ExtComponent> itemsList = new ArrayList<ExtComponent>();
        visitChildren(new IVisitor() {

            @Override
            public Object component(Component component) {
                if (component instanceof ExtComponent) {
                    itemsList.add((ExtComponent) component);
                    return CONTINUE_TRAVERSAL_BUT_DONT_GO_DEEPER;
                }
                return CONTINUE_TRAVERSAL;
            }

        });
        return itemsList;
    }

    @Override
    protected void onRender(MarkupStream markupStream) {
        visitChildren(new IVisitor() {
            @Override
            public Object component(Component component) {
                component.render(null);
                return IVisitor.CONTINUE_TRAVERSAL_BUT_DONT_GO_DEEPER;
            }
        });
    }
}