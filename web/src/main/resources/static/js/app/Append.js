Ext.define('core.append.area.Province', {
	extend: 'Ext.form.ComboBox',
	alias: 'widget.province',
	displayField: 'name',
    valueField: 'name',
	initComponent: function() {
		this.store = Ext.create('Ext.data.Store', {
			fields : ['id', 'name'],
			proxy: {
				type: 'ajax',
				url: '/query',
				extraParams: {xtype: 'query_province'},
				actionMethods: {read: 'POST'},
				reader: {
					type: 'json',
					rootProperty: 'root',
			        totalProperty: 'total'
				}
			}
		});
		this.callParent(arguments);
	},
	listeners: {
		change: function(me) {
			var city = me.up('form').down('city');
			if (!Utils.isEmpty(city)) {
				city.setValue('');
				store = city.store;
				store.getProxy().extraParams = {xtype: 'query_city', province: me.getValue()};
				store.load();
			}
		}
	}
});
Ext.define('core.append.area.City', {
	extend: 'Ext.form.ComboBox',
	alias: 'widget.city',
	displayField: 'name',
    valueField: 'name',
    store: Ext.create('Ext.data.Store', {
			fields : ['id', 'name'],
			proxy: {
				type: 'ajax',
				url: '/query',
				actionMethods: {read: 'POST'},
				reader: {
					type: 'json',
					rootProperty: 'root',
			        totalProperty: 'total'
				}
			},
			autoLoad: false
	}),
	listeners: {
		change: function(me) {
			if (me.getValue()=='') {
				return;
			}
			var district = me.up('form').down('district');
			if (!Utils.isEmpty(district)) {
				if (!district.override)
					district.setValue('');
				store = district.store;
				store.getProxy().extraParams = {xtype: 'query_district', city: me.getValue()};
				store.load();
			}
		}
	}
});

Ext.define('core.extend.YesNoCheckbo', {
    extend: 'Ext.form.field.ComboBox',
    alias: 'widget.ynchbo',
    displayField: 'text',
    valueField: 'value',
    editable: false,
    initComponent: function () {
        var me = this;

        me.store = Ext.create('Ext.data.Store', {
            fields: ['text', 'value'],
            data: [{
                'text': '是',
                'value': '是'
            }, {
                'text': '否',
                'value': '否'
            }]
        });

        this.callParent(arguments);
    }
});

Ext.define('Ext.ux.form.SearchField', {
    extend: 'Ext.form.field.Text',
    alias: 'widget.searchfield',
    triggers: {
        clear: {
            weight: 0,
            cls: Ext.baseCSSPrefix + 'form-clear-trigger',
            hidden: true,
            handler: 'onClearClick',
            scope: 'this'
        },
        search: {
            weight: 1,
            cls: Ext.baseCSSPrefix + 'form-search-trigger',
            handler: 'onSearchClick',
            scope: 'this'
        }
    },

    hasSearch: false,
    paramName: 'query',

    initComponent: function () {
        var me = this,
            store = me.store,
            proxy;

        me.callParent(arguments);
        me.on('specialkey', function (f, e) {
            if (e.getKey() == e.ENTER) {
                me.onSearchClick();
            }
        });
    },

    onClearClick: function () {
        var me = this,
            activeFilter = me.activeFilter;

        if (activeFilter) {
            me.setValue('');
            me.store.getFilters().remove(activeFilter);
            me.activeFilter = null;
            me.getTrigger('clear').hide();
            me.updateLayout();
        }
    },

    onSearchClick: function () {
        var me = this,
            value = me.getValue();

        if (value.length > 0) {
            // Param name is ignored here since we use custom encoding in the proxy.
            // id is used by the Store to replace any previous filter
            me.activeFilter = new Ext.util.Filter({
                property: me.paramName,
                value: value
            });
            // me.store.getFilters().add(me.activeFilter);
            me.getTrigger('clear').show();
            me.updateLayout();
        }

        this.fireEvent('search', this, this.getValue());
    }
});


Ext.define('core.append.area.District', {
	extend: 'Ext.form.ComboBox',
	alias: 'widget.district',
	displayField: 'name',
    valueField: 'name',
    store: Ext.create('Ext.data.Store', {
			fields : ['id', 'name'],
			proxy: {
				type: 'ajax',
				url: '/query',
				actionMethods: {read: 'POST'},
				reader: {
					type: 'json',
					rootProperty: 'root',
			        totalProperty: 'total'
				}
			},
			autoLoad: false
	})
});


Ext.define('core.coreApp.TreePanel', {
	extend: 'Ext.tree.Panel',
    alias: 'widget.fltree',
    columns: [{
    	text: "分类编号",
        xtype: 'treecolumn',
        dataIndex: "TypeCode",
        width: 100
    }, {
        text: "分类名称",
        dataIndex: "TypeName",
        width: 250
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
    	if (record != null) {
    		data[mapping['TypeCode']] = record.get('TypeCode');
    		data[mapping['TypeName']] = record.get('TypeName');
    	}
    	
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
            if (root[i].TypeCode.length == 2 || me.simple) {
            	root[i]['leaf'] = true;
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
        	var queryParam = me.queryParam || {};
        	queryParam['xtype'] = me.queryPlan || 'query_Area_a';
        	if (me.datas) {
                me.store.setRoot({TypeID: 'root', expanded: true, TypeName: '区域分类', children: me.parseListDataToTreeData(me.datas)});
        	} else {
        		Utils.ajax("/query", queryParam, function(callback) {
                    me.store.setRoot({TypeID: 'root', expanded: true, TypeName: '区域分类', children: me.parseListDataToTreeData(callback)});
                });
        	}
        });
    }
});

Ext.define('core.coreApp.append.WarehouseTree', {
	extend: 'Ext.tree.Panel',
	alias: "widget.warehouseTree",
    closeAction: "hide",
    rootVisible: false,
    lines: false,
    expanded: true,
    store: Ext.create("Ext.data.TreeStore", {
        expanded: true,
        proxy: {
            type: "ajax",
            url: "/listWareHouseType"
        },
        folderSort: true,
        sorters: [{
            property: "id",
            direction: "ASC"
        }]
    })
});


Ext.define('core.coreApp.append.CommodityClassifyWithNum', {
	extend: 'Ext.tree.Panel',
	alias: "widget.commodityClassifyTreeWithNum",
    closeAction: "hide",
    rootVisible: false,
    lines: false,
    expanded: true,
    store: Ext.create("Ext.data.TreeStore", {
        expanded: true,
        proxy: {
            type: "ajax",
            url: "/listCommodityTypeWithCommodityNum"
        },
        folderSort: true,
        sorters: [{
            property: "id",
            direction: "ASC"
        }]
    })
});

Ext.define('core.coreApp.append.CommodityClassify', {
	extend: 'Ext.tree.Panel',
	alias: "widget.commodityClassifyTree",
    closeAction: "hide",
    rootVisible: false,
    lines: false,
    expanded: true,
    store: Ext.create("Ext.data.TreeStore", {
        expanded: true,
        proxy: {
            type: "ajax",
            url: "/listCommodityType"
        },
        folderSort: true,
        sorters: [{
            property: "id",
            direction: "ASC"
        }]
    })
});
