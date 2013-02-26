package org.wicketstuff.js.ext;

import java.util.Arrays;

import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.js.ext.ExtMessageBox;
import org.wicketstuff.js.ext.ExtMessageBoxCallback;
import org.wicketstuff.js.ext.ExtMessageBoxOptions;
import org.wicketstuff.js.ext.ExtWaitConfig;
import org.wicketstuff.js.ext.ExtMessageBox.Buttons;
import org.wicketstuff.js.ext.ExtMessageBox.Icons;
import org.wicketstuff.js.ext.util.ExtResourcesBehaviour;


public class MessageBox extends ExamplesPage {

	public MessageBox() {

		add(new ExtResourcesBehaviour());

		final ExtMessageBoxCallback showResult = new ExtMessageBoxCallback() {
			@Override
			public void onClick(AjaxRequestTarget target, String button, String text) {
				ExtExample.showResult(button);
			}
		};

		final ExtMessageBoxCallback showResultText = new ExtMessageBoxCallback() {
			@Override
			public void onClick(AjaxRequestTarget target, String button, String text) {
				ExtExample.showResultText(button, text);
			}
		};

		add(showResult);
		add(showResultText);

		WebMarkupContainer mb1 = new WebMarkupContainer("mb1");
		mb1.add(new AjaxEventBehavior("onclick") {

			@Override
			protected void onEvent(AjaxRequestTarget target) {
				ExtMessageBox.confirm("Confirm", "Are you sure you want to do that?", showResult);
			}

		});
		add(mb1);

		WebMarkupContainer mb2 = new WebMarkupContainer("mb2");
		mb2.add(new AjaxEventBehavior("onclick") {

			@Override
			protected void onEvent(AjaxRequestTarget target) {

				ExtMessageBox.prompt("Name", "Please enter your name:", showResultText);
			}

		});
		add(mb2);

		final WebMarkupContainer mb3 = new WebMarkupContainer("mb3");
		mb3.add(new AjaxEventBehavior("onclick") {

			@Override
			protected void onEvent(AjaxRequestTarget target) {

				ExtMessageBoxOptions options = new ExtMessageBoxOptions();
				options.setTitle("Address");
				options.setMsg("Please enter your address:");
				options.setWidth(300);
				options.setButtons(Buttons.OKCANCEL);
				options.setMultiline(true);
				options.setFn(showResultText);
				options.setAnimEl(mb3);

				ExtMessageBox.show(options);
			}

		});
		add(mb3);

		final WebMarkupContainer mb4 = new WebMarkupContainer("mb4");
		mb4.add(new AjaxEventBehavior("onclick") {

			@Override
			protected void onEvent(AjaxRequestTarget target) {

				ExtMessageBoxOptions options = new ExtMessageBoxOptions();
				options.setTitle("Save Changes?");
				options
						.setMsg("You are closing a tab that has unsaved changes. <br />Would you like to save your changes?");
				options.setButtons(Buttons.YESNOCANCEL);
				options.setFn(showResult);
				options.setAnimEl(mb4);
				options.setIcon(Icons.QUESTION);

				ExtMessageBox.show(options);
			}

		});
		add(mb4);

		final WebMarkupContainer mb6 = new WebMarkupContainer("mb6");
		mb6.add(new AjaxEventBehavior("onclick") {

			@Override
			protected void onEvent(AjaxRequestTarget target) {

				ExtMessageBoxOptions options = new ExtMessageBoxOptions();
				options.setTitle("Please wait");
				options.setMsg("Loading items...");
				options.setProgressText("Initializing...");
				options.setWidth(300);
				options.setProgress(true);
				options.setCloseable(false);
				options.setAnimEl(mb6);

				ExtMessageBox.show(options);

				mb6.add(new AbstractAjaxTimerBehavior(Duration.milliseconds(500)) {

					private int v = 1;

					@Override
					protected void onTimer(AjaxRequestTarget target) {
						if (v == 12) {
							ExtMessageBox.hide();
							ExtExample.msg("Done", "Your fake items were loaded!", null, null);
							stop();
						} else {
							float i = ((float) v) / 11;
							ExtMessageBox.updateProgress(i, Math.round(100 * i) + "% completed", null);
							++v;
						}
					}

				});

				target.addComponent(mb6);
			}

		});
		add(mb6);

		final WebMarkupContainer mb7 = new WebMarkupContainer("mb7");
		mb7.add(new AjaxEventBehavior("onclick") {

			@Override
			protected void onEvent(AjaxRequestTarget target) {

				ExtMessageBoxOptions options = new ExtMessageBoxOptions();
				options.setMsg("Saving your data, please wait...");
				options.setProgressText("Saving...");
				options.setWidth(300);
				options.setWait(true);
				ExtWaitConfig waitConfig = new ExtWaitConfig();
				waitConfig.setInterval(200);
				options.setWaitConfig(waitConfig);
				options.setIcon("ext-mb-download");
				options.setAnimEl(mb7);

				ExtMessageBox.show(options);

				mb6.add(new AbstractAjaxTimerBehavior(Duration.milliseconds(8000)) {

					@Override
					protected void onTimer(AjaxRequestTarget target) {
						ExtMessageBox.hide();
						ExtExample.msg("Done", "Your fake data was saved!", null, null);
						stop();
					}

				});

				target.addComponent(mb6);
			}

		});

		add(mb7);

		final WebMarkupContainer mb8 = new WebMarkupContainer("mb8");
		mb8.add(new AjaxEventBehavior("onclick") {

			@Override
			protected void onEvent(AjaxRequestTarget target) {

				ExtMessageBox.alert("Status", "Changes saved successfully.", showResult);
			}

		});
		add(mb8);

		Form<?> form = new Form<Void>("form");
		form.setOutputMarkupId(true);

		final IModel<Icons> icon = new Model<Icons>(Icons.ERROR);

		IChoiceRenderer<Icons> renderer = new IChoiceRenderer<Icons>() {
			@Override
			public Object getDisplayValue(Icons object) {
				if (Icons.INFO.equals(object)) {
					return "Informational";
				} else {
					return Character.toUpperCase(object.toString().charAt(0))
							+ object.toString().substring(1).toLowerCase();
				}
			}

			@Override
			public String getIdValue(Icons object, int index) {
				return object.toString();
			}
		};

		form.add(new DropDownChoice<Icons>("icons", icon, Arrays.asList(Icons.values()), renderer));

		final WebMarkupContainer mb9 = new AjaxButton("mb9") {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				ExtMessageBoxOptions options = new ExtMessageBoxOptions();
				options.setTitle("Icon Support");
				options.setMsg("Here is a message with an icon!");
				options.setButtons(Buttons.OK);
				options.setAnimEl(this);
				options.setFn(showResult);
				options.setIcon(icon.getObject());

				ExtMessageBox.show(options);
			}

		};
		form.add(mb9);

		add(form);
	}

}
