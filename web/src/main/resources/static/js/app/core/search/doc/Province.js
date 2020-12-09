Ext.define('core.search.doc.Province', {
	extend: 'Ext.form.ComboBox',
	alias: 'widget.province',
	displayField: 'name',
    valueField: 'province',
	initComponent: function() {
		this.store = Ext.create('Ext.data.Store', {
			fields : ['id', 'name'],
			proxy: {
				type: 'ajax',
				url: '/api/area/province',
				extraParams: {name: '', type: 1},
				actionMethods: {read: 'POST'},
				reader: {
					type: 'json',
					rootProperty: 'data',
			        totalProperty: 'total'
				}
			}
		});
		this.callParent(arguments);
	},
	listeners: {
		change: function(me) {
			var city = me.up('form').down('city');
			var	district = me.up('form').down('district');
			if (!Utils.isEmpty(city)) {
				city.setValue('');
				district.setValue('');
				store = city.store;
				store.getProxy().extraParams = {name: me.getValue(), type: 2};
				store.load();
			}
		}
	}
})