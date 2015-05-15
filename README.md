Smart Integration with event handling and a focus on making the Java-API as close to the JS-API as possible.

### Code-Example ###

Note that you can simply call win.hide(); in the onClick of the button.

```
final ExtWindow win = new ExtWindow("hello-win");
win.setTitle(new Model<String>("Hello Dialog"));
win.setWidth(500);
win.setHeight(300);
				
win.addButton(new ExtButton(new Model<String>("Close")) {
	@Override
	protected void onClick(AjaxRequestTarget target) {
		win.hide();
	}
});
add(win);
```

### The same with only Ext ###
```
win = new Ext.Window({
    applyTo:'hello-win',
    width:500,
    height:300,

    buttons: [{
         text: 'Close',
         handler: function(){
              win.hide();
          }
    }]
});

```

## Licenses ##
Please see [the Licensing page on the WIKI](../../wiki/Licensing) for details on licensing.
