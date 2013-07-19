/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.js.ext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wicketstuff.js.ext.layout.DefaultLayout;
import org.wicketstuff.js.ext.layout.ILayout;
import org.wicketstuff.js.ext.layout.LayoutType;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtPropertyConverter;
import org.wicketstuff.js.ext.util.JSONIdentifier;

@ExtClass("Ext.Container")
public class ExtContainer extends ExtBoxComponent {

    private final ItemsRepeater<ExtComponent> items;

    protected ILayout layout = new DefaultLayout();

    public ExtContainer() {
        this("item");
    }

    public ExtContainer(String id) {
        super(id);
        add(items = new ItemsRepeater("items"));
    }

    protected boolean hasExtItems() {
        return true;
    }

    @Override
    protected void onBeforeRender() {
        if (!hasBeenRendered()) {
            wrapNonExtComponents();

            items.beforeRender();
        }

        // before render of parent first
        super.onBeforeRender();
    }

    private void wrapNonExtComponents() {
        // wrap children in a BoxComponent
        final List<Component> children = new LinkedList<Component>();
        visitChildren(new IVisitor<Component, Void>() {

            @Override
            public void component(Component component, IVisit<Void> visit) {
                if ((!(component instanceof ExtComponent)) && (!(component instanceof ItemsRepeater))
                        && (!(component instanceof ItemsRepeater.ExtItem))) {
                    children.add(component);
                }
                visit.dontGoDeeper();
            }

        });
        if (children.size() > 1) {
            for (Component component : children) {
                ExtBoxComponent boxComponent = new ExtBoxComponent("item");
                boxComponent.add(component);
                add(boxComponent);
            }
        }
    }

    protected final MarkupContainer getItemsContainer() {
        return items;
    }

    protected String getName() {
        return "x-container";
    }

    @Override
    protected boolean isRenderFromMarkup() {
        return super.isRenderFromMarkup() || !hasExtItems();
    }

    @Override
    protected void onRenderProperties(JSONObject properties) throws JSONException {
        JSONArray jsonItems = new JSONArray();
        for (ExtComponent item : getItems()) {
            jsonItems.put(new JSONIdentifier(item.getExtId()));
        }
        properties.put("items", jsonItems);

        if (layout.getType() != LayoutType.AUTO) {
            properties.put("layout", layout.getType().toString().toLowerCase());

            JSONObject layoutConfig = new JSONObject();
            ExtPropertyConverter.addProperties(layout, layout.getClass(), layoutConfig);
            properties.put("layoutConfig", layoutConfig);
        }
        layout.applyLayout(this);

        super.onRenderProperties(properties);
    }

    public List<ExtComponent> getItems() {
        final List<ExtComponent> itemsList = new ArrayList<ExtComponent>();
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

    public void addItem(ExtComponent... components) {
        for (ExtComponent component : components) {
            items.add(component);
        }
    }

    public void setLayout(ILayout layout) {
        this.layout = layout;
    }

}
