Ext.define('core.coreApp.station.Station', {
    extend: 'Ext.form.Panel',
    layout: 'border',
    padding: 10,
    alias: 'widget.admin_station_station',
    items: [{
        region: 'north',
        layout: 'column',
        baseCls: 'x-plain',
        items: [{
            xtype: 'textfield',
            labelWidth: 60,
            name: 'stationNo',
            fieldLabel: '站点',
            hidden: true
        }, {
            xtype: 'textfield',
            labelWidth: 60,
            name: 'stationName',
            fieldLabel: '站点',
            search: 'core.search.doc.Station'
        }, {
            xtype: 'checkbox',
            margin: '0 0 0 30',
            name: 'active',
            width: 50,
            boxLabel: '审核'
        }, {
            xtype: 'button',
            text: '查询',
            action: 'query',
            method: 'get',
            uri: '/admin/station/info'
        }, {
            action: 'delete',
            text: '关闭',
            active: 'select',
            follow: 'reflush',
            uri: '/admin/station/info',
            method:'delete'
        }]
    } ,{
        region: 'center',
        xtype: 'grid',
        pagination: true,
        autoInsert: false,
        selType: 'checkboxmodel',
        action: '/admin/station/station',
        exception: {
            key: 'withdrawNo'
        },
        fields: [{
            name: 'stationNo',
            text: '站点编号',
            width: 200
        }, {
            name: 'stationName',
            text: '站点名称',
            width: 200
        }, {
            name: 'province',
            text: '省',
            width: 200
        }, {
            name: 'city',
            text: '市',
            width: 100
        }, {
            name: 'district',
            text: '县',
            width: 200
        }, {
            name: 'contact',
            text: '联系人',
            width: 200
        }, {
            name: 'phone',
            text: '联系电话',
            width: 200
        }, {
            name: 'address',
            text: '联系地址',
            width: 200
        }, {
            name: 'state',
            text: '状态',
            width: 200,
            renderer: function (value) {
                return value == 1 ? "正常" : "关闭";
            }
        }]
    }]
});