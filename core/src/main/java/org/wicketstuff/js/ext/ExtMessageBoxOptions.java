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

import org.apache.wicket.Component;
import org.wicketstuff.js.ext.ExtMessageBox.Buttons;
import org.wicketstuff.js.ext.ExtMessageBox.Icons;
import org.wicketstuff.js.ext.util.ExtOptions;
import org.wicketstuff.js.ext.util.ExtProperty;
import org.wicketstuff.js.ext.util.JSONIdentifier;

@ExtOptions
public class ExtMessageBoxOptions {

    @ExtProperty
    protected String title;
    @ExtProperty
    protected String msg;
    @ExtProperty
    protected Number width;
    @ExtProperty
    protected Buttons buttons;
    @ExtProperty
    protected Boolean multiline;
    @ExtProperty
    protected Component animEl;
    @ExtProperty
    protected ExtMessageBoxCallback fn;
    @ExtProperty
    protected Object icon;
    @ExtProperty
    protected String progressText;
    @ExtProperty
    protected Boolean progress;
    @ExtProperty
    protected Boolean closeable;
    @ExtProperty
    protected Boolean wait;
    @ExtProperty
    protected ExtWaitConfig waitConfig;

    public ExtMessageBoxOptions() {

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setWidth(Number width) {
        this.width = width;
    }

    public void setButtons(Buttons buttons) {
        this.buttons = buttons;
    }

    public void setMultiline(Boolean multiline) {
        this.multiline = multiline;
    }

    public void setAnimEl(Component animEl) {
        animEl.setOutputMarkupId(true);
        this.animEl = animEl;
    }

    public void setFn(ExtMessageBoxCallback fn) {
        this.fn = fn;
    }

    public void setIcon(Icons icon) {
        this.icon = new JSONIdentifier("Ext.MessageBox." + icon.toString());
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setProgressText(String progressText) {
        this.progressText = progressText;
    }

    public void setProgress(Boolean progress) {
        this.progress = progress;
    }

    public void setCloseable(Boolean closeable) {
        this.closeable = closeable;
    }

    public void setWait(Boolean wait) {
        this.wait = wait;
    }

    public void setWaitConfig(ExtWaitConfig waitConfig) {
        this.waitConfig = waitConfig;
    }


}