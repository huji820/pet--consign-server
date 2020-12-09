Ext.define('core.search.doc.City', {
	extend: 'Ext.window.Window',
	alias: 'widget.city',
	width: 700,
	height: 350,
	title: '城市检索',
	layout: 'border',
	items: [{
		region: 'north',
		xtype: 'form',
		layout: 'column',
		items: [{
			xtype: 'textfield',
			name: 'city',
			fieldLabel: '站点名称'
		}, {
			xtype: 'button',
			action: 'query',
			uri: '/admin/station/city',
			text: '查询'
		}]
	}, {
		region: 'center',
		xtype: 'grid',
		autoInsert: false,
		allowInsert: false,
		fields: [{
			name: 'cityName',
			text: '站点名称'
		}]
	}]
});