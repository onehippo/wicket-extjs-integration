package org.wicketstuff.js.ext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.resolver.IComponentResolver;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wicketstuff.js.ext.layout.DefaultLayout;
import org.wicketstuff.js.ext.layout.ILayout;
import org.wicketstuff.js.ext.layout.LayoutType;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.JSONIdentifier;

@ExtClass("Ext.Container")
public class ExtContainer extends ExtBoxComponent implements IComponentResolver {
    private static final long serialVersionUID = 1L;

    protected ILayout layout = new DefaultLayout();
    private ItemsRepeater items;

    public ExtContainer() {
        this("item");
    }

    public ExtContainer(String id) {
        super(id);
        add(items = new ItemsRepeater("items"));
    }

    @Override
    protected final boolean hasExtItems() {
        return true;
    }

    @Override
    protected void onBeforeRender() {
        // wrap children in a BoxComponent
        final List<Component> children = new LinkedList<Component>();
        visitChildren(new IVisitor<Component>() {

            @Override
            public Object component(Component component) {
                if ((!(component instanceof ExtComponent)) && (!(component instanceof ItemsRepeater))
                        && (!(component instanceof ItemsRepeater.ExtItem))) {
                    children.add(component);
                }
                return CONTINUE_TRAVERSAL_BUT_DONT_GO_DEEPER;
            }

        });
        if (children.size() > 1) {
            for (Component component : children) {
                ExtBoxComponent boxComponent = new ExtBoxComponent();
                boxComponent.add(component);
                add(boxComponent);
            }
        }

        items.beforeRender();

        // before render of parent first
        super.onBeforeRender();
    }

    protected String getName() {
        return "x-container";
    }

    @Override
    protected void onRenderProperties(JSONObject properties) throws JSONException {
        super.onRenderProperties(properties);

        JSONArray jsonItems = new JSONArray();
        for (Component item : getItems()) {
            jsonItems.put(new JSONIdentifier(item.getMarkupId()));
        }
        setIfNotNull(properties, "items", jsonItems);

        if (layout.getType() != LayoutType.AUTO) {
            properties.put("layout", layout.getType().toString().toLowerCase());
        }
        layout.applyLayout(this);
    }

    public List<Component> getItems() {
        final List<Component> itemsList = new ArrayList<Component>();
        Iterator<? extends ExtComponent> iterator = items.extIterator();
        while (iterator.hasNext()) {
            itemsList.add(iterator.next());
        }
        return itemsList;
    }

    public ExtContainer add(ExtComponent... components) {
        for (ExtComponent component : components) {
            if ("item".equals(component.getId())) {
                items.add(component);
            } else {
                super.add(component);
            }
        }
        return this;
    }

    public void setLayout(ILayout layout) {
        this.layout = layout;
    }

    @Override
    protected void onRender(MarkupStream markupStream) {
        super.onRender(markupStream);

        // items are not in the markup so they need to be "rendered" separately
        items.render(null);
    }

    @Override
    public boolean resolve(MarkupContainer container, MarkupStream markupStream, ComponentTag tag) {
        String id = tag.getId();

        // FIXME: cache this list of components during rendering
        final List<Component> leafs = new LinkedList<Component>();
        visitChildren(new IVisitor<Component>() {

            @Override
            public Object component(Component component) {
                if (component instanceof ExtComponent) {
                    return CONTINUE_TRAVERSAL;
                }
                if (component instanceof ItemsRepeater) {
                    return CONTINUE_TRAVERSAL;
                }
                if (component instanceof ItemsRepeater.ExtItem) {
                    return CONTINUE_TRAVERSAL;
                }
                leafs.add(component);
                return CONTINUE_TRAVERSAL_BUT_DONT_GO_DEEPER;
            }

        });
        for (Component component : leafs) {
            if (id.equals(component.getId())) {
                component.render(markupStream);
                return true;
            }
        }
        return false;
    }

}
