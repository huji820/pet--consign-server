Ext.define('core.search.doc.District', {
	extend: 'Ext.form.ComboBox',
	alias: 'widget.district',
	displayField: 'name',
    valueField: 'district',
    store: Ext.create('Ext.data.Store', {
			fields : ['id', 'name'],
			proxy: {
				type: 'ajax',
				url: '/api/area/district',
				actionMethods: {read: 'GET'},
				reader: {
					type: 'json',
					rootProperty: 'data',
			        totalProperty: 'total'
				}
			},
			autoLoad: false
	})
})