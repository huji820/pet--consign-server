Ext.define('core.coreApp.fm.withdraw.Station', {
    extend: 'Ext.form.Panel',
    layout: 'border',
    padding: 10,
    alias: 'widget.admin_withDraw_station',
    items: [{
        region: 'north',
        layout: 'column',
        baseCls: 'x-plain',
        items: [{
            xtype: 'textfield',
            name: 'withdrawNo',
            labelWidth: 60,
            fieldLabel: '单据编号'
        }, {
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
            xtype: 'datefield',
            name: 'startDate',
            labelWidth: 60,
            width: 200,
            fieldLabel: '开始时间'
        }, {
            xtype: 'datefield',
            name: 'endDate',
            width: 200,
            labelWidth: 60,
            fieldLabel: '结束时间'
        }, {
            xtype: 'checkbox',
            margin: '0 0 0 30',
            name: 'active',
            width: 50,
            boxLabel: '审核'
        }, {
            xtype: 'button',
            action: 'query',
            text: '查询',
            method: 'get',
            uri: '/admin/withDraw/station'
        }, {
            xtype: 'button',
            action: 'submit',
            text: '确认',
            active: 'select',
            follow: 'reflush',
            uri: '/admin/withDraw/station',
            method: 'put'
        }, {
            xtype: 'button',
            action: 'submit',
            text: '驳回',
            active: 'select',
            follow: 'reflush',
            uri: '/admin/withDraw/station',
            method:'delete'
        }]
    } ,{
        region: 'center',
        xtype: 'grid',
        pagination: true,
        selType: 'checkboxmodel',
        autoInsert: false,
        allowInsert: false,
        action: '/admin/withDraw/station',
        exception: {
            key: 'withdrawNo'
        },
        fields: [{
            name: 'withdrawNo',
            text: '提现单据编号',
            width: 200
        }, {
            name: 'withdrawTime',
            text: '申请提现时间',
            width: 200
        }, {
            name: 'stationName',
            text: '提现站点',
            mapping: 'station.stationName',
            width: 200
        }, {
            name: 'amount',
            text: '提现金额',
            width: 100
        }, {
            name: 'state',
            text: '提现状态',
            width: 200,
        }],
        viewConfig : {
            forceFit: true,
            getRowClass: function (record, rowIndex, rowParams, store) {
                if (record.get("state") == "待审核") {
                    return "red-row";
                }
            }
        }
    }]
});