/**
 * 分类树
 */
Ext.define('core.jxywkj.erp.extend.PrefixTree', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.prefixtree',
    columns: [{
        text: "编号",
        xtype: 'treecolumn',
        dataIndex: "TypeCode",
        width: 80
    }, {
        text: "名称",
        dataIndex: "TypeName",
        width: 80
    }],
    rootVisible: false,
    editable: false,
    triggerCls: Ext.baseCSSPrefix + 'form-arrow-trigger',
    getSelect: function() {
        return this.getSelectionModel().getSelection();
    },
    getData: function() {
        var me = this,
            record = me.getSingleSelect(),
            mapping = me.mapping,
            data = {};

        return data;
    },
    getSingleSelect: function() {
        var records = this.getSelect();
        if (records.length == 0) {
            return null;
        } else {
            return records[0];
        }
    },
    parseListDataToTreeData: function(callback) {
        var me = this,
            result = [],
            root = Array.isArray(callback) ? callback : callback['root'],
            mapping = me.mapping;
        for (var i = 0; i < root.length; i++) {
            Utils.applyIf(root[i], {
                TypeCode: root[i][mapping ? mapping.TypeCode : 'TypeCode'],
                TypeName: root[i][mapping ? mapping.TypeName : 'TypeName'],
                children: [],
                leaf: true
            });
            if (root[i].TypeCode.length == 2) {
                //root[i]['leaf'] = false;
                me.loadTreeChildNode(root[i], root);
                result.push(root[i])
            }
        }
        return result;
    },
    isChildNodeExists: function(node, root) {
        var me = this;
        for (var i = 0; i < root.length; i++) {
            if (root[i][me.mapping.TypeCode].startWith(node.TypeCode)&&root[i][me.mapping.TypeCode].length-node.TypeCode.length==2) {
                return true;
            }
        }
        return false;
    },
    loadTreeChildNode: function(node, root) {
        var me = this,
            mapping = me.mapping;
        for (var i = 0; i < root.length; i++) {
            if (!root[i].TypeCode)
                Utils.applyIf(root[i], {
                    TypeCode: root[i][mapping.TypeCode],
                    TypeName: root[i][mapping.TypeName],
                    children: []
                });
            if (root[i].TypeCode.startWith(node.TypeCode)&&root[i].TypeCode.length-node.TypeCode.length==2) {
                node['leaf'] = false;
                if (!node.children) {
                    node.children = [];
                }
                node.children.push(root[i]);
                if (me.isChildNodeExists(root[i], root)) {
                    me.loadTreeChildNode(root[i], root)
                }
            }
        }
    },
    initComponent: function() {
        var me = this;
        me.callParent(arguments);
        me.on('beforerender', function(me) {
            Utils.ajax(me.action, me.params || {}, function(callback) {
                me.store.setRoot({TypeID: 'root', expanded: true, children: me.parseListDataToTreeData(callback)});
            });
        });
    }
});