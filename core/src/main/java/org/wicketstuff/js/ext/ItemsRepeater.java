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
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.AbstractRepeater;
import org.apache.wicket.util.collections.ReadOnlyIterator;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

final class ItemsRepeater<T extends ExtComponent> extends AbstractRepeater {
    private static final long serialVersionUID = 1L;

    static class ExtItem extends WebMarkupContainer {
        private static final long serialVersionUID = 1L;

        private ExtItem(final int iteration) {
            super(Integer.toString(iteration).intern());
            setRenderBodyOnly(true);
        }

        @Override
        public WebMarkupContainer remove(final Component component) {
            getParent().remove(this);
            return this;
        }
    }

    private int numItems = 0;

    ItemsRepeater(String id) {
        super(id);
        setRenderBodyOnly(true);
    }

    @Override
    protected Iterator<? extends Component> renderIterator() {
        final int iterations = size();
        return new ReadOnlyIterator<Component>() {
            private int index = 0;

            public boolean hasNext() {
                return index < iterations;
            }

            public Component next() {
                return get(Integer.toString(index++));
            }
        };
    }

    @Override
    protected void onPopulate() {
    }

    void add(T ec) {
        if (!"item".equals(ec.getId())) {
            throw new IllegalArgumentException("ExtComponent does not have 'item' as its wicket id");
        }
        // Create item for loop iteration
        ExtItem item = new ExtItem(numItems++);
        add(item);
        item.add(ec);
    }

    Iterator<T> extIterator() {
        return getExtComponents().iterator();
    }

    public final List<T> getExtComponents() {
        final List<T> itemsList = new ArrayList<T>();
        visitChildren(ExtComponent.class, new IVisitor<T, Void>() {

            @Override
            public void component(T component, IVisit<Void> visit) {
                itemsList.add(component);
                visit.dontGoDeeper();
            }

        });
        return itemsList;
    }

}