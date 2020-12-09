Ext.define('core.search.doc.Station', {
    extend: 'Ext.window.Window',
    alias: 'widget.search_station',
    width: 700,
    height: 350,
    title: '站点检索',
    layout: 'border',
    items: [{
        region: 'north',
        xtype: 'form',
        layout: 'column',
        items: [{
            xtype: 'textfield',
            name: 'stationName',
            fieldLabel: '站点名称'
        }, {
            xtype: 'button',
            action: 'query',
            uri: '/admin/station/info',
            text: '查询'
        }]
    }, {
        region: 'center',
        xtype: 'grid',
        autoInsert: false,
        allowInsert: false,
        pagination: true,
        fields: [{
            name: 'stationNo',
            text: '站点编号'
        }, {
            name: 'stationName',
            text: '站点名称'
        }, {
            name: 'province',
            text: '省'
        }, {
            name: 'city',
            text: '市'
        }, {
            name: 'contact',
            text: '联系人'
        }, {
            name: 'phone',
            text: '联系电话'
        }, {
            name: 'address',
            text: '联系地址'
        }]
    }]
});