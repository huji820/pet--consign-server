Ext.define('core.coreApp.fm.withdraw.Station', {
    extend: 'Ext.form.Panel',
    layout: 'border',
    alias: 'widget.admin_withDraw_station',
    tbar: [{
        action: 'query',
        method: 'get',
        uri: '/admin/withDraw/station'
    }, {
        action: 'submit',
        text: '确认',
        active: 'select',
        follow: 'reflush',
        uri: '/admin/withDraw/station',
        method: 'put'
    }, {
        action: 'submit',
        text: '驳回',
        active: 'select',
        follow: 'reflush',
        uri: '/admin/withDraw/station',
        method:'delete'
    }],
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
            fieldLabel: '站点'
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
            margin: '0 0 0 50',
            name: 'active',
            width: 200,
            boxLabel: '审核'
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
            renderer: function(val) {
                if (val != '已完成') {
                    return "<span style='color: red'>"+val+"</span>";
                }
                return val;
            }
        }],
        listeners: {
            cell
        }
    }]
});