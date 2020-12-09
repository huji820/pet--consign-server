Ext.define('core.coreApp.control.design.code.Generator', {
	extend: 'Ext.panel.Panel',
	layout: 'border',
	items: [{
		region: 'west',
		width: 180,
		xtype: 'treepanel',
		split : true,
		containerScroll : true,// 是否支持滚动条
		store: Ext.create('Ext.data.TreeStore', {  
            root: {  
            	text: '工具栏',   
                expanded: true,//默认不展开  
                children: [{
                	text: "容器", 
                	leaf: false, 
                	expanded: true, 
                	children: [{
                		text: 'container', leaf: true
                	}, {
                		text: 'panel', leaf: true
            		}, {
            			text: 'form', leaf: true
            		}, {
            			text: 'toolbar', leaf: true
            		}, {
            			text: 'tabpanel', leaf: true
            		}, {
            			text: 'fieldset', leaf: true
            		}]
                }, {
                	text: "公共控件", 
                	leaf: false, 
                	expanded: true, 
                	children: [{
                		text: 'textfield', leaf: true
                	}, {
                		text: 'datefield', leaf: true
            		}, {
            			text: 'combo', leaf: true
            		}, {
            			text: 'checkbox', leaf: true
            		}, {
            			text: 'textarea', leaf: true
            		}, {
            			text: 'filefield', leaf: true
            		}]
                }, {
                	text: "布局", 
                	leaf: false, 
                	expanded: true, 
                	children: [{
                		text: 'borderLayout', leaf: true
                	}, {
                		text: 'columnLayout', leaf: true
            		}, {
            			text: 'formLayout', leaf: true
            		}, {
            			text: 'cardLayout', leaf: true
            		}]
                }]  
            }  
        }),
        listeners: {
        	render: function(v) {
        		Ext.create('Ext.dd.DragZone', v.getEl(), {  
        			ddGroup:"zone2DD", 
        	        getDragData: function(e) {  
        	            var sourceEl = e.getTarget(v.itemSelector, 10), d;  
        	            if (sourceEl) {  
        	                d = sourceEl.cloneNode(true);  
        	                d.id = Ext.id();  
        	                return (v.dragData = {  
        	                    sourceEl: sourceEl,  
        	                    repairXY: Ext.fly(sourceEl).getXY(),  
        	                    ddel: d,
        	                    record: v.getView().getRecord(sourceEl)
        	                });  
        	            }  
        	        },  
        	  
        	        getRepairXY: function() {  
        	            return this.dragData.repairXY;  
        	        }  
        	    });  
        	}
        }
	}, {
		xtype: 'panel',
		region: 'center',
		listeners: {
			render: function(v) {
				Ext.create('Ext.dd.DropZone', v.getEl(), {
					ddGroup:"zone2DD", 
			        getTargetFromEvent: function(e) {  
			            //console.log("getTargetFromEvent...:");  
			            return e.getTarget(e.target);  
			        },  
			  
			        onNodeEnter: function(target, dd, e, data){  
			            //console.log("onNodeEnter...");  
			        },  
			  
			        onNodeOut: function(target, dd, e, data){  
			            //console.log("onNodeOut...");  
			        },  
			        onNodeOver: function(target, dd, e, data){  
			            var proto = Ext.dd.DropZone.prototype;  
			            //console.log("proto.dropAllowed:"+proto.dropAllowed+"------proto.dropNotAllowed:"+proto.dropNotAllowed);  
			  
			            return proto.dropAllowed;  
			        },  
			        onNodeDrop: function(target, dd, e, data){  
			            var name = data.record.get('text'),
			            	panel = target.component;
			            // container
			            // 如果是布局的
			            if (name == 'panel') {
			            	panel.setLayout({layout: 'fit'});
			            	panel.add({
			            		xtype: 'panel',
			            		title: '123'
			            	});
			            	panel.updateLayout();
			            } else if (name == 'form') {
			            	console.log('form');
			            } else if (name == 'container') {
			            	console.log('container');
			            } else if (name == 'toolbar') {
			            	console.log('toolbar');
			            } else if (name == 'tabpanel') {
			            	console.log('tabpanel');
			            }
			            
			            console.log(Global.jsonToStr(panel.getConfig()));
			            return true;  
			        }  
			    }); 
			}
		}
	}]
});