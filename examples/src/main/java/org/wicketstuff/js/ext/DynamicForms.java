package org.wicketstuff.js.ext;

import org.apache.wicket.model.Model;
import org.wicketstuff.js.ext.form.ExtFieldSet;
import org.wicketstuff.js.ext.form.ExtFormPanel;
import org.wicketstuff.js.ext.form.ExtFormPanel.LabelAlign;
import org.wicketstuff.js.ext.form.ExtHtmlEditor;
import org.wicketstuff.js.ext.form.ExtTextField;
import org.wicketstuff.js.ext.form.ExtTimeField;
import org.wicketstuff.js.ext.form.VTypes;
import org.wicketstuff.js.ext.layout.ColumnLayout;
import org.wicketstuff.js.ext.layout.FitLayout;
import org.wicketstuff.js.ext.layout.FormLayout;

public class DynamicForms extends ExamplesPage {

	public DynamicForms() {

		addSimpleForm();
		addFieldSetForm();
		addMultiColumnForm();
		addTabForm();
		addTabsForm2();
	}

	private void addSimpleForm() {
		ExtFormPanel<?> simpleForm = new ExtFormPanel<Void>("simpleForm");
		simpleForm.setLabelWidth(75);
		simpleForm.setFrame(true);
		simpleForm.setTitle(new Model<String>("Simple Form"));
		simpleForm.setBodyStyle("padding:5px 5px 0");
		simpleForm.setWidth(350);

		ExtTextField<?> field1 = new ExtTextField<String>(new Model<String>());
		field1.setWidth(230);
		field1.setFieldLabel(new Model<String>("First Name"));
		field1.setAllowBlank(false);

		ExtBoxComponent field2 = new ExtTextField<String>(new Model<String>());
		field2.setWidth(230);
		field2.setFieldLabel(new Model<String>("Last Name"));

		ExtBoxComponent field3 = new ExtTextField<String>(new Model<String>());
		field3.setWidth(230);
		field3.setFieldLabel(new Model<String>("Company"));

		ExtTextField<?> field4 = new ExtTextField<String>(new Model<String>());
		field4.setWidth(230);
		field4.setFieldLabel(new Model<String>("Email"));
		field4.setVtype(VTypes.EMAIL);

		ExtTimeField<?> field5 = new ExtTimeField<String>(new Model<String>());
		field5.setWidth(230);
		field5.setFieldLabel(new Model<String>("Time"));
		field5.setMinValue("8:00am");
		field5.setMaxValue("6:00pm");

		simpleForm.addItem(field1, field2, field3, field4, field5);

		AbstractExtButton saveButton = new ExtSubmitButton(new Model<String>("Save"));
		simpleForm.addButton(saveButton);

		AbstractExtButton cancelButton = new ExtButton(new Model<String>("Cancel"));
		simpleForm.addButton(cancelButton);

		add(simpleForm);
	}

	private void addFieldSetForm() {
		ExtFormPanel<?> fieldsetForm = new ExtFormPanel<Void>("fieldsetForm");
		fieldsetForm.setLabelWidth(75);
		fieldsetForm.setFrame(true);
		fieldsetForm.setTitle(new Model<String>("Simple Form with FieldSets"));
		fieldsetForm.setBodyStyle("padding:5px 5px 0");
		fieldsetForm.setWidth(350);

		ExtFieldSet fieldset1 = new ExtFieldSet();
		fieldset1.setCheckboxToggle(true);
		fieldset1.setTitle(new Model<String>("User Information"));
		fieldset1.setAutoHeight(true);
		fieldset1.setCollapsed(true);

		ExtTextField<?> field11 = new ExtTextField<String>(new Model<String>());
		field11.setWidth(210);
		field11.setFieldLabel(new Model<String>("First Name"));
		field11.setAllowBlank(false);

		ExtBoxComponent field12 = new ExtTextField<String>(new Model<String>());
		field12.setWidth(210);
		field12.setFieldLabel(new Model<String>("Last Name"));

		ExtBoxComponent field13 = new ExtTextField<String>(new Model<String>());
		field13.setWidth(210);
		field13.setFieldLabel(new Model<String>("Company"));

		ExtTextField<?> field14 = new ExtTextField<String>(new Model<String>());
		field14.setWidth(210);
		field14.setFieldLabel(new Model<String>("Email"));
		field14.setVtype(VTypes.EMAIL);

		fieldset1.addItem(field11, field12, field13, field14);

		fieldsetForm.addItem(fieldset1);

		ExtFieldSet fieldset2 = new ExtFieldSet();
		fieldset2.setTitle(new Model<String>("Phone Number"));
		fieldset2.setCollapsible(true);
		fieldset2.setAutoHeight(true);

		ExtBoxComponent field21 = new ExtTextField<String>(new Model<String>("(888) 555-1212"));
		field21.setWidth(210);
		field21.setFieldLabel(new Model<String>("Home"));

		ExtBoxComponent field22 = new ExtTextField<String>(new Model<String>());
		field22.setWidth(210);
		field22.setFieldLabel(new Model<String>("Business"));

		ExtBoxComponent field23 = new ExtTextField<String>(new Model<String>());
		field23.setWidth(210);
		field23.setFieldLabel(new Model<String>("Mobile"));

		ExtBoxComponent field24 = new ExtTextField<String>(new Model<String>());
		field24.setWidth(210);
		field24.setFieldLabel(new Model<String>("Fax"));

		fieldset2.addItem(field21, field22, field23, field24);

		fieldsetForm.addItem(fieldset2);

		AbstractExtButton saveButton = new ExtSubmitButton(new Model<String>("Save"));
		fieldsetForm.addButton(saveButton);

		AbstractExtButton cancelButton = new ExtButton(new Model<String>("Cancel"));
		fieldsetForm.addButton(cancelButton);

		add(fieldsetForm);
	}

	private void addMultiColumnForm() {
		ExtFormPanel<?> multicolumnForm = new ExtFormPanel<Void>("multicolumnForm");
		multicolumnForm.setLabelAlign(LabelAlign.TOP);
		multicolumnForm.setFrame(true);
		multicolumnForm.setTitle(new Model<String>("Multi Column, Nested Layouts and Anchoring"));
		multicolumnForm.setBodyStyle("padding:5px 5px 0");
		multicolumnForm.setWidth(600);

		ExtPanel columnLayout = new ExtPanel();
		columnLayout.setLayout(new ColumnLayout());

		ExtPanel leftForm = new ExtPanel();
		leftForm.setColumnWidth(.5);
		leftForm.setLayout(new FormLayout());

		ExtBoxComponent left1 = new ExtTextField<String>(new Model<String>());
		left1.setFieldLabel(new Model<String>("First Name"));
		left1.setAnchor("95%");
		ExtBoxComponent left2 = new ExtTextField<String>(new Model<String>());
		left2.setFieldLabel(new Model<String>("Company"));
		left2.setAnchor("95%");

		leftForm.addItem(left1, left2);

		ExtPanel rightForm = new ExtPanel();
		rightForm.setColumnWidth(.5);
		rightForm.setLayout(new FormLayout());

		ExtBoxComponent right1 = new ExtTextField<String>(new Model<String>());
		right1.setFieldLabel(new Model<String>("Last Name"));
		right1.setAnchor("95%");
		ExtTextField<?> right2 = new ExtTextField<String>(new Model<String>());
		right2.setFieldLabel(new Model<String>("Email"));
		right2.setVtype(VTypes.EMAIL);
		right2.setAnchor("95%");

		rightForm.addItem(right1, right2);

		columnLayout.addItem(leftForm, rightForm);

		multicolumnForm.addItem(columnLayout);

		ExtHtmlEditor<String> editor = new ExtHtmlEditor<String>(new Model<String>());
		// editor.setId("bio");
		editor.setFieldLabel(new Model<String>("Biography"));
		editor.setHeight(200);
		editor.setAnchor("98%");
		multicolumnForm.addItem(editor);

		AbstractExtButton saveButton = new ExtSubmitButton(new Model<String>("Save"));
		multicolumnForm.addButton(saveButton);

		AbstractExtButton cancelButton = new ExtButton(new Model<String>("Cancel"));
		multicolumnForm.addButton(cancelButton);

		add(multicolumnForm);
	}

	private void addTabForm() {
		ExtFormPanel<?> tabForm = new ExtFormPanel<Void>("tabForm");
		tabForm.setBorder(false);
		tabForm.setWidth(350);

		ExtTabPanel tabPanel = new ExtTabPanel();
		tabPanel.setActiveTab(0);

		ExtPanel tab1 = new ExtPanel();
		tab1.setAutoHeight(true);
		tab1.setBodyStyle("padding:10px");
		tab1.setTitle(new Model<String>("Personal Details"));
		tab1.setLayout(new FormLayout());

		ExtTextField<?> field1 = new ExtTextField<String>(new Model<String>("Jack"));
		field1.setFieldLabel(new Model<String>("First Name"));
		field1.setAllowBlank(false);
		field1.setWidth(230);
		tab1.addItem(field1);

		ExtTextField<?> field2 = new ExtTextField<String>(new Model<String>("Slocum"));
		field2.setFieldLabel(new Model<String>("Last Name"));
		field2.setWidth(230);
		tab1.addItem(field2);

		ExtTextField<?> field3 = new ExtTextField<String>(new Model<String>("Ext JS"));
		field3.setFieldLabel(new Model<String>("Company"));
		field3.setWidth(230);
		tab1.addItem(field3);

		ExtTextField<?> field4 = new ExtTextField<String>(new Model<String>(""));
		field4.setFieldLabel(new Model<String>("Email"));
		field4.setWidth(230);
		field4.setVtype(VTypes.EMAIL);
		tab1.addItem(field4);

		tabPanel.addItem(tab1);

		ExtPanel tab2 = new ExtPanel();
		tab2.setAutoHeight(true);
		tab2.setBodyStyle("padding:10px");
		tab2.setTitle(new Model<String>("Phone Numbers"));
		tab2.setLayout(new FormLayout());

		ExtTextField<?> field21 = new ExtTextField<String>(new Model<String>("(888) 555-1212"));
		field21.setFieldLabel(new Model<String>("Home"));
		field21.setWidth(230);
		tab2.addItem(field21);

		ExtTextField<?> field22 = new ExtTextField<String>(new Model<String>(""));
		field22.setFieldLabel(new Model<String>("Business"));
		field22.setWidth(230);
		tab2.addItem(field22);

		ExtTextField<?> field23 = new ExtTextField<String>(new Model<String>(""));
		field23.setFieldLabel(new Model<String>("Mobile"));
		field23.setWidth(230);
		tab2.addItem(field23);

		ExtTextField<?> field24 = new ExtTextField<String>(new Model<String>(""));
		field24.setFieldLabel(new Model<String>("Fax"));
		field24.setWidth(230);
		tab2.addItem(field24);

		tabPanel.addItem(tab2);

		tabForm.addItem(tabPanel);

		AbstractExtButton saveButton = new ExtSubmitButton(new Model<String>("Save"));
		tabForm.addButton(saveButton);

		AbstractExtButton cancelButton = new ExtButton(new Model<String>("Cancel"));
		tabForm.addButton(cancelButton);

		add(tabForm);
	}

	private void addTabsForm2() {
		ExtFormPanel<?> multicolumnForm = new ExtFormPanel<Void>("tabsForm2");
		multicolumnForm.setLabelAlign(LabelAlign.TOP);
		multicolumnForm.setTitle(new Model<String>("Inner Tabs"));
		multicolumnForm.setBodyStyle("padding:5px");
		multicolumnForm.setWidth(600);

		ExtPanel columnLayout = new ExtPanel();
		columnLayout.setLayout(new ColumnLayout());
		columnLayout.setBorder(false);

		ExtPanel leftForm = new ExtPanel();
		leftForm.setColumnWidth(.5);
		leftForm.setLayout(new FormLayout());
		leftForm.setBorder(false);

		ExtBoxComponent left1 = new ExtTextField<String>(new Model<String>());
		left1.setFieldLabel(new Model<String>("First Name"));
		left1.setAnchor("95%");
		ExtBoxComponent left2 = new ExtTextField<String>(new Model<String>());
		left2.setFieldLabel(new Model<String>("Company"));
		left2.setAnchor("95%");

		leftForm.addItem(left1, left2);

		ExtPanel rightForm = new ExtPanel();
		rightForm.setColumnWidth(.5);
		rightForm.setLayout(new FormLayout());
		rightForm.setBorder(false);

		ExtBoxComponent right1 = new ExtTextField<String>(new Model<String>());
		right1.setFieldLabel(new Model<String>("Last Name"));
		right1.setAnchor("95%");
		ExtTextField<?> right2 = new ExtTextField<String>(new Model<String>());
		right2.setFieldLabel(new Model<String>("Email"));
		right2.setVtype(VTypes.EMAIL);
		right2.setAnchor("95%");

		rightForm.addItem(right1, right2);

		columnLayout.addItem(leftForm, rightForm);

		multicolumnForm.addItem(columnLayout);

		ExtTabPanel tabPanel = new ExtTabPanel();
		tabPanel.setPlain(true);
		tabPanel.setActiveTab(0);
		tabPanel.setHeight(235);
		tabPanel.setDeferredRender(false);

		ExtPanel tab1 = new ExtPanel();
		tab1.setAutoHeight(true);
		tab1.setBodyStyle("padding:10px");
		tab1.setTitle(new Model<String>("Personal Details"));
		tab1.setLayout(new FormLayout());

		ExtTextField<?> field1 = new ExtTextField<String>(new Model<String>("Jack"));
		field1.setFieldLabel(new Model<String>("First Name"));
		field1.setAllowBlank(false);
		field1.setWidth(230);
		tab1.addItem(field1);

		ExtTextField<?> field2 = new ExtTextField<String>(new Model<String>("Slocum"));
		field2.setFieldLabel(new Model<String>("Last Name"));
		field2.setWidth(230);
		tab1.addItem(field2);

		ExtTextField<?> field3 = new ExtTextField<String>(new Model<String>("Ext JS"));
		field3.setFieldLabel(new Model<String>("Company"));
		field3.setWidth(230);
		tab1.addItem(field3);

		ExtTextField<?> field4 = new ExtTextField<String>(new Model<String>(""));
		field4.setFieldLabel(new Model<String>("Email"));
		field4.setWidth(230);
		field4.setVtype(VTypes.EMAIL);
		tab1.addItem(field4);

		tabPanel.addItem(tab1);

		ExtPanel tab2 = new ExtPanel();
		tab2.setAutoHeight(true);
		tab2.setBodyStyle("padding:10px");
		tab2.setTitle(new Model<String>("Phone Numbers"));
		tab2.setLayout(new FormLayout());

		ExtTextField<?> field21 = new ExtTextField<String>(new Model<String>("(888) 555-1212"));
		field21.setFieldLabel(new Model<String>("Home"));
		field21.setWidth(230);
		tab2.addItem(field21);

		ExtTextField<?> field22 = new ExtTextField<String>(new Model<String>(""));
		field22.setFieldLabel(new Model<String>("Business"));
		field22.setWidth(230);
		tab2.addItem(field22);

		ExtTextField<?> field23 = new ExtTextField<String>(new Model<String>(""));
		field23.setFieldLabel(new Model<String>("Mobile"));
		field23.setWidth(230);
		tab2.addItem(field23);

		ExtTextField<?> field24 = new ExtTextField<String>(new Model<String>(""));
		field24.setFieldLabel(new Model<String>("Fax"));
		field24.setWidth(230);
		tab2.addItem(field24);

		tabPanel.addItem(tab2);

		ExtPanel tab3 = new ExtPanel();
		tab3.setCls("x-plain");
		tab3.setTitle(new Model<String>("Biography"));
		tab3.setLayout(new FitLayout());

		ExtHtmlEditor<String> editor = new ExtHtmlEditor<String>(new Model<String>());
		// editor.setId("bio");
		editor.setFieldLabel(new Model<String>("Biography"));
		tab3.addItem(editor);

		tabPanel.addItem(tab3);

		multicolumnForm.addItem(tabPanel);

		AbstractExtButton saveButton = new ExtSubmitButton(new Model<String>("Save"));
		multicolumnForm.addButton(saveButton);

		AbstractExtButton cancelButton = new ExtButton(new Model<String>("Cancel"));
		multicolumnForm.addButton(cancelButton);

		add(multicolumnForm);
	}

}
