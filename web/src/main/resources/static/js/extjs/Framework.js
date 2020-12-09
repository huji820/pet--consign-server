var exprs = new Array(), // 全局的表达式
    // 全局的常用方法
    Utils = {
        // html代码转换
        // 说明：如果提交的数据包含html代码的话，系统将会无法解析成Json,会抛出异常
        //      所以数据在提交之前要先转换html格式的代码
        // 参数:
        //      html html类型的代码
        // 调用：Utils.htmlEscape('<a>我是html代码</a>')
        // 返回值：转换之后的String类型字符创
		htmlEscape: function(html) {
			return html.replace(/[<>"&]/g, function(match, pos, originalText){
			    switch(match){
			    case "<": return "&lt;";
			    case ">":return "&gt;";
			    case "&":return "&amp;";
			    case "\"":return "&quot;";
			  }
		   });
		},

        // 单据再现
        // 说明：统一的单据在线方案
        // 参数:
        //      className 需要在线的类名称
        //      title 显示在导航栏的标题
        //      record 附带的数据(代办任务列表的情况下)
        // 调用：Utils.repeat(core.coreApp.jxc.Xsck, '销售出库', record);
        // 返回值：无
        repeat: function(className, title, record) {
        	var lis = $(".menuTab"),
	    		link = className, 
	    		exists = false,
	    		id;
	    	for (var i = 0; i < lis.length; i++) {
	    		if ($(lis[i]).attr('className') == link) {
	    			var currentId = $(lis[i]).data('id');
	    			id = $(lis[i]).attr('el-id');
	                $('.mainContent .LRADMS_iframe').each(function () {
	                    if ($(this).data('id') == currentId) {
	                        $(this).show().siblings('.LRADMS_iframe').hide();
	                        return false;
	                    }
	                });
	                $(lis[i]).addClass('active').siblings('.menuTab').removeClass('active');
	                
	                var items = $("#content-main > div");
	                for (var i = 0; i < items.length; i++) {
	                	$(items[i]).hide();
	                }
	                
	                if ($(this).data('id') == -1) {
	                	$('.mainContent .LRADMS_iframe').show();
	                } else {
	                	$('.mainContent .LRADMS_iframe').hide();
	                	$("#"+id).show();
	                }
	                
	                $.learuntab.scrollToTab($(lis[i]));
	
	    			exists=true;
	    			break;
	    		}
	    	}
	    	
	    	if (!exists) {
	            var items = $("#content-main > div");
	            for (var i = 0; i < items.length; i++) {
	                $(items[i]).hide();
	            }
	
	            if (record && record.data['taskid']) {
	                record.data['stopTask'] = true;
	            }
	            item = Ext.create(className, {
	                // 代办任务列表的情况下，包含流程信息
	                audit: record ? record.data['auditStatus'] : '',
	                height: $(".mainContent").css('height'),
	                isRepeat: true,
	                renderTo: 'content-main',
	                style: 'border-width: 1px;border-style: solid;border-color: rgb(207, 207, 207);border-image: initial;background: rgb(255, 255, 255);'
	            });
	
	            // 查找item下面所有的按钮，如果不是submit和print全部影藏
	            var buttons = Ext.ComponentQuery.query("button", item),
	            	postParam = {billNo: record.data['billNo'],
	                nid: record.data['nid'],
	                xtype: item.xtype};
	            
	            Utils.applyIf(postParam, record.data);
	         
	            Utils.ajax('/repeat', postParam, function(callback) {
	                item.pageDataFill(callback);
	                
	                if (item.audit && item.audit == '已审核') {
	                	$("#"+item.id).append('<div style="position:absolute; left:1000px;top: 0px;width: 150px; height: 74px; background: url(/images/audit.png) 0 0 no-repeat; position: absolute;z-index:999999999"></div>');
	                } 
	            });
	
	          var str = '<a href="javascript:;" class="active menuTab" el-id="' + item.id + '"data-id="' + $('.page-tabs-content').children().length + '">' + (title) + ' <i class="icon icon-remove"></i></a>';
	            $('.menuTab').removeClass('active');
	            $('.mainContent').find('iframe.LRADMS_iframe').hide();
	            $('.mainContent iframe:visible').load(function() {});
	            $('.menuTabs .page-tabs-content').append(str);
	            $.learuntab.scrollToTab($('.menuTab.active'));
	            item.pageDataFill(record.data);
	    	}
        },

        // 弃用
        mergeSystemParam: function(json) {
            return json;
        },

        clickTimeout: {
            _timeout: null,
            /**  
             *
             */
            set: function(fn) {
                var that = this;
                that.clear();
                that._timeout = window.setTimeout(fn, 500);
            },
            clear: function() {
                var that = this;
                if (that._timeout) {
                    window.clearTimeout(that._timeout);
                    that._timeout = null;
                }
            }
        },
        
        // 对象拷贝
        // 说明：解决对象引用的问题
        // 参数:
        //      obj 对象
        // 调用：Utils.copy(obj);
        // 返回值：无
        copy: function(obj) {
            if (typeof obj != 'object' || obj instanceof Array) {
                return obj;
            }
            var newobj = {};
            for (var attr in obj) {
                newobj[attr] = Utils.copy(obj[attr]);
            }
            return newobj;
        },

        // 获取X位数的随机码
        // 说明：返回多少位数的随机码
        // 参数:
        //      len 长度
        // 调用：Utils.GetRandomNum(6);
        // 返回值：6位长度的随机字符串数字
        GetRandomNum: function(len) {
            var Num = "";
            for (var i = 0; i < 6; i++) {
                Num += Math.floor(Math.random() * 10);
            }
            return Num;
        },

        // 更改grid里面record的值
        // 说明：用于grid的renderType为checkbox的使用，展示位checkbox的形式
        // 参数: checkbox对象
        //      changeModel 按行，还是按列，还是按Cell
        // 调用：Utils.changeGridCellRecord(this);
        // 返回值：
        changeGridCellRecord: function(checkbox,  changeModel) {
            let id = checkbox.id,
                gridId = id.split('_')[0],
                row = id.split('_')[1],
                grid = Ext.getCmp(gridId),
                column = id.split('_')[2],
                record = grid.store.getAt(row);
            
            if (!changeModel || changeModel == 'cell')  {
                let field = grid.fields[column - (grid.oper ? 2 : 1)].name;

                record.set(field, checkbox.checked ? '是' : '否');
            }
            else if (changeModel == 'row') {
            	let fields = grid.fields, field;
            	for (var i = 0; i < fields.length; i++ ) {
            		if (fields[i].renderType == 'check') {
            			field = grid.fields[i].name;
            			record.set(field, checkbox.checked ? '是' : '否');
            		}
            		else if (fields[i].type == 'boolean') {
            			field = grid.fields[i].name;
            			record.set(field, checkbox.checked);
            		}
            	}
            } 
        },
        

        // 执行为String类型的JS代码
        // 说明：主要用于计算字符创类型的数字表达式
        //       比如"(1+2)"
        // 参数: str 字符串的计算表达式
        // 调用：Utils.eval('1+2');
        // 返回值： 表达式计算后的数字
        eval: function(str) {
            var parser = new MathUtils(str);
            try {
                return parser.parse();
            } catch (error) {
                // alert(str);
                console.log(str);
            }
        },

        // json覆盖
        // 说明：属于强制性覆盖，不管原有的JSON内存在不存在，强制性用后面的覆盖前面
        // 参数:
        //      params 被覆盖的json
        //      vars 覆盖的JSON
        // 调用：Utils.apply({aa:'bb'}, {aa:'cc', bb: 'dd'});
        // 结果为 {aa:'cc', bb: 'dd'}
        // 返回值：json
        apply: function(params, vars) {
            if (vars) {
                for (var variable in vars) {
                    params[variable] = vars[variable];
                }
            }
            return params;
        },

        // json覆盖
        // 说明：属于非强制性覆盖，只对被覆盖的对象进行增加级的覆盖，既原有属性值不存在的才添加
        // 参数:
        //      params 被覆盖的json
        //      vars 覆盖的JSON
        // 调用：Utils.applyIf({aa:'bb'}, {aa:'cc', bb:'dd'});
        // 结果为 {aa:'bb', bb:'dd'}
        // 返回值：json
        applyIf: function(params, vars) {
            if (vars) {
                for (var variable in vars) {
                    if (Utils.isEmpty(params[variable]))
                        params[variable] = vars[variable];
                }
            }
            return params;
        },

        // 获取当前系统时间
        // 说明：返回的系统时间是格式化后的，年月日 星期
        // 参数：
        // 返回值：当前系统时间
        getCurrentTimer: function() {
            var day = '',
                month = '',
                ampm = '',
                ampmhour = '',
                myweekday = '',
                year = '',
                myHours = '',
                myMinutes = '',
                mySeconds = '',
                mydate = new Date(),
                myweekday = mydate.getDay(),
                mymonth = parseInt(mydate.getMonth() + 1) < 10 ? "0" + (mydate.getMonth() + 1) : mydate.getMonth() + 1,
                myday = mydate.getDate(),
                myyear = mydate.getYear(),
                myHours = mydate.getHours(),
                myMinutes = mydate.getMinutes(),
                mySeconds = parseInt(mydate.getSeconds()) < 10 ? "0" + mydate.getSeconds() : mydate.getSeconds(),
                year = (myyear > 200) ? myyear : 1900 + myyear;
            if (myweekday == 0) weekday = " 星期日 ";
            else if (myweekday == 1) weekday = " 星期一 ";
            else if (myweekday == 2) weekday = " 星期二 ";
            else if (myweekday == 3) weekday = " 星期三 ";
            else if (myweekday == 4) weekday = " 星期四 ";
            else if (myweekday == 5) weekday = " 星期五 ";
            else if (myweekday == 6) weekday = " 星期六 ";
            return year + "年" + mymonth + "月" + myday + "日 " + weekday;
        },

        // 字符串转换成函数
        // 说明：字符串转换成函数
        // 参数：Str 字符串类型的函数方法
        // 返回值：Function
        strToFun: function(str) {
            return (new Function("return " + str))();
        },

        // 计算字段汇总值
        // 说明：根据字段进行汇总
        // 参数：
        //      fields 字段列表
        //      columns grid的列
        //      record 当前grid的数据
        //      flFields 分组的字段
        //      hzFields 汇总的字段
        //      pjFields 平均的字段
        // 返回值: Number
        calculationTypeAndSummary: function(fields, columns, records, flFields, hzFields, pjFields) {
        	var result = new Array();

            if (Utils.isEmpty(pjFields)) {
                pjFields = new Array();
            }
            var showFields = '[{"name": "repeat", "type": "int"},',
                usedFields = "[",
                tmFields = "[",
                hzUsed = "[",
                pjUsed = "[",
                flModel = '',
                hzModel = '',
                realHz = '',
                flFun = '',
                hzFun = '';
            pjModel = '';
            pjFun = '';
            for (var i = 0; i < flFields.length; i++) {
                if (flFields[i].getValue()) {
                    for (var j = 0; j < fields.length; j++) {
                        if (flFields[i].name == fields[j].name) {
                            showFields = showFields + '{"name":"' + fields[j].name + '", "type": "' + fields[j].type + '"}' + ",";
                            usedFields = usedFields + '{"name":"' + fields[j].name + '", "type": "' + fields[j].type + '"}' + ",";
                            tmFields = tmFields + '{"name":"' + fields[j].name + '", "type": "' + fields[j].type + '"}' + ",";
                        }
                    }
                    flModel = flModel + flFields[i].name + ": '', ";
                    flFun = flFun + flFields[i].name + ",";
                }
            }
            for (var i = 0; i < hzFields.length; i++) {
                if (hzFields[i].getValue()) {
                    for (var j = 0; j < fields.length; j++) {
                        if (hzFields[i].name == fields[j].name) {
                            showFields = showFields + '{"name":"' + fields[j].name + '_hz", "type": "' + fields[j].type + '"}' + ",";
                            hzUsed = hzUsed + '{"name":"' + fields[j].name + '_hz", "type": "' + fields[j].type + '"}' + ",";
                        }
                    }
                    hzModel = hzModel + hzFields[i].name + "_hz" + ": 0,";
                    hzFun = hzFun + hzFields[i].name + "_hz" + ",";
                    realHz = hzModel;
                }
            }
            for (var i = 0; i < pjFields.length; i++) {
                if (pjFields[i].getValue()) {
                    for (var j = 0; j < fields.length; j++) {
                        if (pjFields[i].name == fields[j].name) {
                            showFields = showFields + '{"name":"' + fields[j].name + '_pj", "type": "' + fields[j].type + '"}' + ",";
                            pjUsed = pjUsed + '{"name":"' + fields[j].name + '_pj", "type": "' + fields[j].type + '"}' + ",";
                        }
                    }
                    pjModel = pjModel + pjFields[i].name + "_pj" + ": 0,";
                    pjFun = pjFun + pjFields[i].name + "_pj" + ",";
                    if (hzModel.indexOf(pjFields[i].name + "_hz") == -1) {
                        hzModel = hzModel + pjFields[i].name + "_hz" + ": 0,";
                        hzFun = hzFun + pjFields[i].name + "_hz" + ",";
                    }
                }
            }
            showFields = showFields + "]";
            usedFields = usedFields + "]";
            tmFields = tmFields + "]";
            hzUsed = hzUsed + "]";
            pjUsed = pjUsed + "]";
            tmFields = Utils.strToJson(tmFields.replace(",]", "]"));
            showFields = Utils.strToJson(showFields.replace(",]", "]"));
            usedFields = Utils.strToJson(usedFields.replace(",]", "]"));
            hzUsed = Utils.strToJson(hzUsed.replace(",]", "]"));
            pjUsed = Utils.strToJson(pjUsed.replace(",]", "]"));
            // 获取Columns
            var showColumns = '[{"xtype": "rownumberer", "width": 30},',
                tmColumns = "[";
            for (var i = 0; i < usedFields.length; i++) {
                for (var j = 0; j < columns.length; j++) {
                    if (usedFields[i].name == columns[j].name) showColumns = showColumns + '{"type": "string", "name":"'+columns[j].name+'", "dataIndex":"' + columns[j].name + '", "text": "' + columns[j].text + '", "width":' + (Utils.isEmpty(columns[j].width) ? 200 : columns[j].width) + "},";
                }
            }
            for (var i = 0; i < tmFields.length; i++) {
                for (var j = 0; j < columns.length; j++) {
                    if (tmFields[i].name == columns[j].name) tmColumns = tmColumns + '{"type": "string", "name":"'+columns[j].name+'", "dataIndex":"' + columns[j].name + '", "text": "' + columns[j].text + '", "width":' + (Utils.isEmpty(columns[j].width) ? 200 : columns[j].width) + "},";
                }
            }
            for (var i = 0; i < hzUsed.length; i++) {
                for (var j = 0; j < columns.length; j++) {
                    if (hzUsed[i].name.startWith(columns[j].name)) showColumns = showColumns + '{"type": "number", "name":"'+columns[j].name+ '_hz", "dataIndex":"' + columns[j].name + '_hz", "text": "' + columns[j].text + '(汇总)", "width":' + (Utils.isEmpty(columns[j].width) ? 200 : columns[j].width) + "},";
                }
            }
            for (var i = 0; i < pjUsed.length; i++) {
                for (var j = 0; j < columns.length; j++) {
                    if (pjUsed[i].name.startWith(columns[j].name)) showColumns = showColumns + '{"type": "number","name":"'+columns[j].name + '_pj", "dataIndex":"' + columns[j].name + '_pj", "text": "' + columns[j].text + '(平均)", "width":' + (Utils.isEmpty(columns[j].width) ? 200 : columns[j].width) + "},";
                }
            }
            showColumns = showColumns + "]";
            tmColumns = tmColumns + "]";
            showColumns = Utils.strToJson(showColumns.replace(",]", "]"));
            tmColumns = Utils.strToJson(tmColumns.replace(",]", "]"));
            // 获取DATA
            var showData = '{"items":[',
                datas = new Array();
            var cls = "{count: 0, " + flModel + hzModel + pjModel;
            // 定义函数
            cls = cls + "add: function(" + flFun + hzFun + pjFun + ") {";
            // 定义函数体
            cls = cls + "this.count = this.count + 1;";
            for (var i = 0; i < flFields.length; i++) {
                if (flFields[i].getValue()) {
                    cls = cls + "this." + flFields[i].name + " = " + flFields[i].name + ";";
                }
            }
            for (var i = 0; i < hzFields.length; i++) {
                if (hzFields[i].getValue()) {
                    cls = cls + "this." + hzFields[i].name + "_hz =  Utils.toFloat(this." + hzFields[i].name + "_hz).add(" + "Utils.toFloat(" + hzFields[i].name + "_hz));";
                }
            }
            for (var i = 0; i < pjFields.length; i++) {
                if (pjFields[i].getValue()) {
                    if (realHz.indexOf(pjFields[i].name + "_hz") == -1) {
                        cls = cls + "this." + pjFields[i].name + "_hz =  Utils.toFloat(this." + pjFields[i].name + "_hz).add(" + "Utils.toFloat(" + pjFields[i].name + "_hz));";
                    }
                    cls = cls + "this." + pjFields[i].name + "_pj =  " + "Utils.toFloat(this." + pjFields[i].name + "_hz).div(this.count);";
                }
            }
            cls = cls + "}, data: function() {";
            cls = cls + "return {linesCount:this.count,";
            for (var i = 0; i < flFields.length; i++) {
                if (flFields[i].getValue()) {
                    cls = cls + flFields[i].name + " : this." + flFields[i].name + ",";
                }
            }
            for (var i = 0; i < hzFields.length; i++) {
                if (hzFields[i].getValue()) {
                    cls = cls + hzFields[i].name + "_hz :  this." + hzFields[i].name + "_hz,";
                }
            }
            for (var i = 0; i < pjFields.length; i++) {
                if (pjFields[i].getValue()) {
                    if (realHz.indexOf(pjFields[i].name + "_hz") == -1) {
                        cls = cls + pjFields[i].name + "_hz : this." + pjFields[i].name + "_hz,";
                    }
                    cls = cls + pjFields[i].name + "_pj : this." + pjFields[i].name + "_pj,";
                }
            }
            cls = cls + "}";
            cls = cls + "}}";
            cls = cls.replace(/,\)/g, ")").replace(/,\}/g, "}");
            var group = "s";
            var fun = '';
            for (var i = 0; i < records.length; i++) {
                for (var j = 0; j < flFields.length; j++) {
                    if (flFields[j].getValue()) {
                        group = group + records[i].get(flFields[j].name);
                        fun = fun + "'" + records[i].get(flFields[j].name) + "',";
                    }
                }
                for (var j = 0; j < hzFields.length; j++) {
                    if (hzFields[j].getValue()) {
                        fun = fun + records[i].get(hzFields[j].name) + ",";
                    }
                }
                for (var j = 0; j < pjFields.length; j++) {
                    if (pjFields[j].getValue()) {
                        fun = fun + records[i].get(pjFields[j].name) + ",";
                    }
                }
                // 截取最后一位的_,
                group = group.replace(/[^A-Z|a-z|0-9|\u4E00-\u9FA5]/g, '');
                fun = fun.substring(0, fun.length - 1);
                try{
                	eval("if (Utils.isEmpty(" + group + ")) {var " + group + " = " + cls + "; datas.push(" + group + ");} " + group + ".add(" + fun + ");");
                } catch (error) {
                	console.log("if (Utils.isEmpty(" + group + ")) {var " + group + " = " + cls + "; datas.push(" + group + ");} " + group + ".add(" + fun + ");");
                }
                group = "s";
                fun = '';
            }
            for (var i = 0; i < datas.length; i++) {
                if (i != datas.length - 1) {
                    showData = showData + Utils.jsonToStr(datas[i].data()) + ",";
                } else {
                    showData = showData + Utils.jsonToStr(datas[i].data());
                }
            }
            showData = showData + "]}";
            result['data'] = showData;
            result['field'] = showFields;
            result['column'] = showColumns;

            result['tmFields'] = tmFields;
            result['tmColumns'] = tmColumns;
            return result;
        },

        // 是否运算符号
        // 说明：判断字符是否为运算符号
        // 参数: chat 字符
        // 返回值 ：boolean
        isCalculationChat: function(chat) {
            if (chat === '=' || chat === '+' || chat === '-' || chat === '*' || chat === '/' || chat === '%') {
                return true;
            } else {
                return false;
            }
        },

        // 延时处理
        // 说明：延迟处理函数，用来做异步操作
        // 参数：
        //      fun 延迟处理的方法
        //      milliseconds 延迟处理的毫秒数
        //      scope  延时处理的对象
        // 返回值：无
        delay: function(fun, milliseconds, scope) {
            new Ext.util.DelayedTask(fun, scope).delay(milliseconds || 500);
        },

        // 循环执行方法
        // 说明：不建议使用，容易造成页面崩溃
        // 参数:
        //      fun 循环执行的方法
        //      milliseconds 循环的毫秒数
        // 返回值 ：无
        loop: function(fun, milliseconds) {
            setInterval(fun, milliseconds);
        },

        // 获取快捷键的数值
        // 说明：将char类型的字符转换
        // 参数:
        //      key char字符串
        // 返回值 ：对应的ascall码
        getKeyNum: function(key) {
            switch (key) {
                case "*":
                    return 106;
                    break;
                case "-":
                    return 109;
                    break;
                case "/":
                    return 111;
                    break;
                case "F1":
                    return 112;
                    break;

                case "F2":
                    return 113;
                    break;

                case "F3":
                    return 114;
                    break;

                case "F4":
                    return 115;
                    break;

                case "F5":
                    return 116;
                    break;

                case "F6":
                    return 117;
                    break;

                case "F7":
                    return 118;
                    break;

                case "F8":
                    return 119;
                    break;

                case "F9":
                    return 120;
                    break;

                case "F10":
                    return 121;
                    break;

                case "F11":
                    return 122;
                    break;

                case "F12":
                    return 123;
                    break;

                case "A":
                    return 65;
                    break;

                case "B":
                    return 66;
                    break;

                case "C":
                    return 67;
                    break;

                case "D":
                    return 68;
                    break;

                case "E":
                    return 69;
                    break;

                case "F":
                    return 70;
                    break;

                case "G":
                    return 71;
                    break;

                case "H":
                    return 72;
                    break;

                case "I":
                    return 73;
                    break;

                case "J":
                    return 74;
                    break;

                case "K":
                    return 75;
                    break;

                case "L":
                    return 76;
                    break;

                case "M":
                    return 77;
                    break;

                case "N":
                    return 78;
                    break;

                case "O":
                    return 79;
                    break;

                case "P":
                    return 80;
                    break;

                case "Q":
                    return 81;
                    break;

                case "R":
                    return 82;
                    break;

                case "S":
                    return 83;
                    break;

                case "T":
                    return 84;
                    break;

                case "U":
                    return 85;
                    break;

                case "V":
                    return 86;
                    break;

                case "W":
                    return 87;
                    break;

                case "X":
                    return 88;
                    break;

                case "Y":
                    return 89;
                    break;

                case "Z":
                    return 90;
                    break;

                case "HOME":
                    return 36;
                    break;

                case "END":
                    return 35;
                    break;

                case "INSERT":
                    return 45;
                    break;

                case "ENTER":
                    return 13;
                    break;

                case "DELETE":
                    return 46;
                    break;

                case "0":
                    return 48;
                    break;

                case "1":
                    return 49;
                    break;

                case "2":
                    return 50;
                    break;

                case "3":
                    return 51;
                    break;

                case "4":
                    return 52;
                    break;

                case "5":
                    return 53;
                    break;

                case "6":
                    return 54;
                    break;

                case "7":
                    return 55;
                    break;

                case "8":
                    return 56;
                    break;

                case "9":
                    return 57;
                    break;
            }
        },

        // 获取当前主键的最上层的页面
        // 采用递归查找
        // 参数：panel
        // 返回值： panel
        loadParentWindow: function(panel) {
            if (panel.getXTypes().indexOf("window") > -1 || Utils.isEmpty(panel.ownerCt) || panel.ownerCt.id == "adminMainFrame" || panel.ownerCt.id == "mainFrame") {
                return panel;
            }
            return Utils.loadParentWindow(panel.ownerCt);
        },

        // 类型转换，转为Float类型
        // 说明：将Object类型的数据转换成Float类型数据
        // 参数：
        //      Object 需要转换的对象
        // 返回值： Float类型数据
        toFloat: function(val) {
            var result = parseFloat(val);
            if (isNaN(result)) {
                return 0;
            }
            return result;
        },

        // 类型转换，转为INT类型
        // 说明：将Object类型的数据转换成int类型数据
        // 参数：
        //      Object 需要转换的对象
        // 返回值： Int类型数据
        toInt: function(val) {
            var result = parseInt(val);
            if (isNaN(result)) {
                return 0;
            }
            return result;
        },

        // 类型转换，转为String类型
        // 说明：将Object类型的数据转换成String类型数据
        // 参数：Object
        // 返回值： String类型数据
        toStr: function(val) {
            if (Utils.isEmpty(val)) {
                return "";
            }
            return val.toString();
        },

        // json字符串格式转换
        // 说明：将json类型对象转换成String类型JSON字符串
        // 参数：
        //      Json数据
        // 返回值：Str字符串
        jsonToStr: function(val) {
            return JSON.stringify(val);
        },

        // json字符串格式转换
        // 说明：将string类型的字符串
        // 参数：Str字符串
        // 返回值：Json数据
        strToJson: function(val) {
            if (Utils.isEmpty(val)) return {};
            return JSON.parse(val);
        },

        // 格式化数据
        // 说明：格式化页面提交的数据
        // 如果是date类型，转换成yyyy-MM-dd 的格式
        // 如果是boolean类型，true转化成“是”，false转换成“否”
        // 如果为字符串 “null”，转换成空字符串
        // 参数：Obj
        // 返回值:Obj
        formatValue: function(obj) {
            if (Utils.isEmpty(obj)) {
                return '';
            } else if (obj instanceof Date) {
                return obj.Format("yyyy-MM-dd");
            } else if (/\d{4}年\d{1,2}月\d{1,2}日/.test(obj.toString)) {
                return obj.Format("yyyy-MM-dd");
            } else if (/^\d+(\.\d+)?$/.test(obj)) {
                return obj;
            } else if (obj.toString == "false") {
                return "否";
            } else if (obj.toString == "true") {
                return "是";
            } else if (obj.toString == "null") {
                return '';
            } else {
                return Ext.String.trim(obj);
            }
        },

        // 判断该对象是否为空或者undefined
        // 说明：如果对象为空，或者对象为undefined，返回false,否则返回true
        // 参数：Obj对象
        // 返回值：Boolean
        isEmpty: function(obj) {
            if (typeof obj != "undefined" && obj != null) {
                return false;
            }
            return true;
        },

        // Utils.Ajax()
        // 通用的ajax方法
        // 说明：执行AJAX方法，如果返回的prompt为error，自动弹出错误提示
        //      如果为success，传递给回调方法
        // 参数：action 执行路径
        //      params 传递的参数
        //      fun 回调方法
        //      fail 错误的回调方法
        //      out 超时时间
        //      mask 遮罩
        //      btn 回调的BTN
        // 返回值: 无
        ajax: function(action, params, fun, fail, btn) {
            Ext.Ajax.request({
                waitMsg: "请稍等,正在加载数据",
                url: action,
                method: btn ? (btn.method || "post") : "post",
                params: params,
                timeout: 600000,
                // default 30000 milliseconds
                success: function(response) {
                    var callback = Ext.decode(response.responseText);
                    if (callback.prompt == 20001) {
                        Ext.MessageBox.toast("长时间未操作，连接已超时...重新登录");
                        location.href = '/';
                    }
                    /*else if (callback.code != 200) {
                        Ext.MessageBox.toast(callback.errMsg, 'error');
                        if (btn)
                        	btn.disabled = false;
                        return;
                    }*/
                    if (fun) fun(callback);
                    else {
                        Ext.MessageBox.toast(callback.root);
                    }
                },
                failure: function(response, options) {
                    if (action != '/print') {
                        Ext.MessageBox.toast("连接服务器超时", "error");
                        if (fail) fail();
                    } else {
                        if (fail) fail();
                    }
                }
            });
        }
    };

/** ************************************************************************************** */
/** *****************************####对象原型模式扩展###********************************** */
/** ************************************************************************************** */

// 判断字符串是否以参数为结尾
// 说明：不同与JAVA在于，JAVA返回int,本函数返回boolean
// 参数：
//      suffix 后缀
// 调用：'aabbcc'.endWith('cc');
// 返回值：boolean
String.prototype.endWith = function(suffix) {
    return new RegExp(suffix + '$', 'i').test(this);
};

// 获取字符创长度
// 如果是中文，或者全角，将会返回2个字节长度
// 参数:
//      字符串
// 调用:
//      'aaaa'.getByteLength
// 返回值: int
String.prototype.getByteLength = function() {
	return this.replace(/[^\x00-\xff]/g,"aa").length;
};

// 判断字符串是否以参数开头
// 说明：不同与JAVA在于，JAVA返回int,本函数返回boolean
// 参数：
//      prefix 前缀
// 调用：
//      'aabbcc'.endWith('aa');
// 返回值：boolean
String.prototype.startWith = function(prefix) {
    var reg = new RegExp("^" + prefix + "\\w+");
    return reg.test(this);
};


// 字符串的trim方法
// 说明：使用方法和JAVA相同
// 参数：
// 调用：
//      'aabbcc'.trim();
// 返回值：String
String.prototype.trim = function() {
    if (Utils.isEmpty(this)) {
        return '';
    } else if (this instanceof Number) {
        return this;
    } else {
        return Ext.String.trim(this.toString());
    }
};

// 队列移除函数
// 说明：可以清除队列里面的内容，等同于Java.removeAll,参数为Array类型
// 调用：Array.removeAll(arg1)
// 返回值：
Array.prototype.removeAll = function(items) {
    for (var i = 0; i < items.length; i++) {
        if (typeof items[i] == 'number') {
            this.remove(items[i] - i);
        } else {
            this.remove(items[i]);
        }
    }
};

// 队列分组函数
// 说明：对其中的某一个或者多个字段进行汇总
// 调用：Array.groupBy(arg1)
// 返回值：
Array.prototype.groupBy = function(keys) {
    var result = [],
        keys = typeof keys == 'string' ? [keys] : keys,

        toEmptyJson = function() {
            var result = {
                children: []
            };
            for (var i = 0; i < keys.length; i++) {
                result[keys[i]] = null;
            }
            return result;
        },
        containKey = function(vars) {
            if (result.length == 0) {
                return null;
            } else {
                var idx = -1,
                    exist;
                for (var i = 0; i < result.length; i++) {
                    exist = true;
                    for (var j = 0; j < keys.length; j++) {
                        if (result[i][keys[j]] != vars[keys[j]]) {
                            exist = false;
                        }
                    }
                    if (exist)
                        idx = i;
                }
                return idx > -1 ? result[idx] : null;
            }
        },
        item;

    for (var i = 0; i < this.length; i++) {
        item = containKey(this[i]);
        if (item) {
            if (item.children) {
                item.children.push(this[i]);
            } else {
                item.children = [];
                item.children.push(this[i]);
            }
        } else {
            item = toEmptyJson();
            for (var j = 0; j < keys.length; j++) {
                item[keys[j]] = this[i][keys[j]];
            }
            item.children.push(this[i]);
            result.push(item);
        }
    }

    return result;
};

// 判断队列时候包含该项
// 说明：从队列中寻找该项目
// 调用Array.contains('aa')
// 返回值: boolean
Array.prototype.contains = function(item) {
    return RegExp("\\b" + item + "\\b").test(this);
};

// 队列移除函数
// 说明：可以清除队列里面的内容，等同于Java.remove,参数为Object类型
// 参数:item
// 调用：Array.remove(arg1)
// 返回值： 0; i < this.length; i++) {
Array.prototype.remove = function(item) {
    if (typeof item == 'number') {
        for (var i = 0, n = 0; i < this.length; i++) {
            if (this[i] != this[item]) {
                this[n++] = this[i];
            }
        }
    } else {
        for (var i = 0, n = 0; i < this.length; i++) {
            if (this[i] != item) {
                this[n++] = this[i];
            }
        }
    }

    if (this.length > 0) this.length -= 1;
};

// 队列增加函数
// 说明：向队列总添加内容，如果不指定标记位，则默认添加到最后
// 调用：Array.add(arg1, i)
// 返回值：
Array.prototype.add = function(items, idx) {
    if (idx) {
        this.splice(idx, 0, items);
    } else {
        this.push(items);
    }
};

// 字符串的批量替换操作
// 说明：可以对字符串类型的参数进行批量替换，等同于JAVA中的replaceAll操作
// 参数:regex 可为正则，也可为普通字符串函数，replaceWith准备替换的值
// 调用：'abcd'.replaceAll('a', 'b')
// 返回值：批量替换后的字符串
String.prototype.replaceAll = function(regex, replaceWith) {
    if (!RegExp.prototype.isPrototypeOf(regex)) {
        return this.replace(new RegExp(regex, "gi"), replaceWith);
    } else {
        return this.replace(regex, replaceWith);
    }
};

// 去掉会影响JSON转义的特殊字符
// 说明：去掉会影响JSON转义的特殊字符
// 调用：'abcd'.replaceHtml()
// 返回值：字符串
String.prototype.replaceHtml = function() {
    //return this.replaceAll(">", "&gt;").replaceAll("<", "&lt;");
	return this;
};

// 数据的除法
// 说明：参照BigDecimal
// 参数:
// arg 被除数
// 调用：new Number(9).div(3)
// 返回值：Number
Number.prototype.div = function(arg) {
    return new Number(new Big(this.toString()).div(new Big(arg.toString())));
};

// 数据的余数
// 说明：参照BigDecimal
// 参数:
// arg 被除数
// 调用：new Number(9).div(3)
// 返回值：Number
Number.prototype.over = function(arg) {
    return this - Math.floor(this.div(arg)).mul(arg);
};

// 数据的乘法
// 说明：参照BigDecimal
// 参数:
// arg 乘数
// 调用：new Number(9).mul(3)
// 返回值：Number
Number.prototype.mul = function(arg) {
    return new Number(new Big(this.toString()).mul(new Big(arg.toString())));
};

// 数据的加法
// 说明：参照BigDecimal
// 参数:
// arg 乘数
// 调用：new Number(9).add(3)
// 返回值：Number
Number.prototype.add = function(arg) {
    return new Number(new Big(this.toString()).add(new Big(arg.toString())));
};

// 数据的减法
// 说明：参照BigDecimal
// 参数:
// arg 被减数
// 调用：new Number(9).add(3)
// 返回值：Number
Number.prototype.sub = function(arg) {
    return new Number(new Big(this.toString()).sub(new Big(arg.toString())));
};

// 将数字转成汉字大写
// 说明：将数字转成汉字大写,不能为负数
// 调用：new Number(12313).toCn()
// 返回值：string
Number.prototype.toCn = function() {
    var me = this;
    var cnNums = new Array("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"), // 汉字的数字
        cnIntRadice = new Array("", "拾", "佰", "仟"), // 基本单位
        cnIntUnits = new Array("", "万", "亿", "兆"), // 对应整数部分扩展单位
        cnDecUnits = new Array("角", "分", "毫", "厘"), // 对应小数部分单位
        cnInteger = "整", // 整数金额时后面跟的字符
        cnIntLast = "元", // 整型完以后的单位
        isMinus = me.toString().substring(0, 1) === '-',
        money = isMinus ? this.toString().substring(1, me.length) : me,
        maxNum = 999999999999999.9999, // 最大处理的数字

        IntegerNum, // 金额整数部分
        DecimalNum, // 金额小数部分
        ChineseStr = "", // 输出的中文金额字符串
        parts; // 分离金额后用的数组，预定义

    if (money == "") {
        return "";
    }

    money = parseFloat(this);
    if (money >= maxNum) {
        $.alert('超出最大处理数字');
        return "";
    }
    if (money == 0) {
        ChineseStr = cnNums[0] + cnIntLast + cnInteger;
        return ChineseStr;
    }
    money = isMinus ? money.toString().substring(1, money.toString().length) : money.toString(); // 转换为字符串
    if (money.indexOf(".") == -1) {
        IntegerNum = money;
        DecimalNum = '';
    } else {
        parts = money.split(".");
        IntegerNum = parts[0];
        DecimalNum = parts[1].substr(0, 4);
    }
    if (parseInt(IntegerNum, 10) != 0) { // 获取整型部分转换
        zeroCount = 0;
        // 查看时候包含负数
        IntLen = IntegerNum.length;
        for (i = 0; i < IntLen; i++) {
            n = IntegerNum.substr(i, 1);
            p = IntLen - i - 1;
            q = p / 4;
            m = p % 4;
            if (n == "0") {
                zeroCount++;
            } else {
                if (zeroCount > 0) {
                    ChineseStr += cnNums[0];
                }
                zeroCount = 0; // 归零
                ChineseStr += cnNums[parseInt(n)] + cnIntRadice[m];
            }
            if (m == 0 && zeroCount < 4) {
                ChineseStr += cnIntUnits[q];
            }
        }
        ChineseStr += cnIntLast;
        // 整型部分处理完毕
    }
    if (DecimalNum != '') { // 小数部分
        decLen = DecimalNum.length;
        for (i = 0; i < decLen; i++) {
            n = DecimalNum.substr(i, 1);
            if (n != '0') {
                ChineseStr += cnNums[Number(n)] + cnDecUnits[i];
            }
        }
    }
    if (ChineseStr == '') {
        ChineseStr += cnNums[0] + cnIntLast + cnInteger;
    } else if (DecimalNum == '') {
        ChineseStr += cnInteger;
    }
    return isMinus ? "负" + ChineseStr : ChineseStr;
};

// 日期格式化函数
// 说明：通过格式化的类型，返回字符串类型的日期,如果formmat参数为空，默认为(yyyy-MM-dd)
// 参数：format 格式化类型
// 调用：new Date().Format('yyyy-MM-dd hh:mm:ss')
// 返回值：字符串类型的日期
Date.prototype.Format = function(format) {
    format = format || "yyyy-MM-dd";

    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        S: this.getMilliseconds()
    };

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(('' + o[k]).length));
        }
    }
    return format;
};

// 验证时间的合法性
// 说明：默认使用的是yyyy-MM-dd格式
// 参数： dateStr时间字符串
//       formatStr正则验证的格式
// 返回值：boolean
Date.prototype.isValiDate = function(dateStr, formatStr) {
    if (!dateStr) {
        return false;
    }
    if (!formatStr) {
        formatStr = "yyyy-MM-dd";
    }
    if (dateStr.length != formatStr.length) {
        return false;
    } else {
        var r1 = /^(((((([02468][048])|([13579][26]))(00))|(d{2}(([02468][48])|([13579][26]))))-((((0[13578])|(1[02]))-(([0-2][0-9])|(3[01])))|(((0[469])|(11))-(([0-2][0-9])|(30)))|(02-([0-2][0-9]))))|(d{2}(([02468][1235679])|([13579][01345789]))-((((0[13578])|(1[02]))-(([0-2][0-9])|(3[01])))|(((0[469])|(11))-(([0-2][0-9])|(30)))|(02-(([0-1][0-9])|(2[0-8]))))))$/;
        return r1.test(dateStr);
    }
    return false;
},

// 日期增加函数
// 说明：通过选择间隔类型、数量来获取日期增加或减少的操作
// 参数：strInterval 间隔类型, Number 数量
// 调用：new Date().DateAdd('d', 10)
// 返回值：返回日期增加/减少后的日期
Date.prototype.DateAdd = function(strInterval, Number) {
    var dtTmp = this;
    switch (strInterval) {
        case "s":
            return new Date(Date.parse(dtTmp) + 1e3 * Number);

        case "n":
            return new Date(Date.parse(dtTmp) + 6e4 * Number);

        case "h":
            return new Date(Date.parse(dtTmp) + 36e5 * Number);

        case "d":
            return new Date(Date.parse(dtTmp) + 864e5 * Number);

        case "w":
            return new Date(Date.parse(dtTmp) + 864e5 * 7 * Number);

        case "q":
            return new Date(dtTmp.getFullYear(), dtTmp.getMonth() + Number * 3, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());

        case "m":
            return new Date(dtTmp.getFullYear(), dtTmp.getMonth() + Number, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());

        case "y":
            return new Date(dtTmp.getFullYear() + Number, dtTmp.getMonth(), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
    }
};

// 时间比较函数
// 说明：通过选择对比日期，以及间隔类型，返回相差的数量
// 参数：strInterval 间隔类型, dtEnd 对比日期
// 调用：new Date().DateDiff('s', new Date());
// 返回值：返回两个时间类型的相差数
Date.prototype.DateBetween = function(strInterval, dtEnd) {
    var dtStart = this;
    if (typeof dtEnd == "string") // 如果是字符串转换为日期型
    {
        dtEnd = StringToDate(dtEnd);
    }
    switch (strInterval) {
        case "s":
            return parseInt((dtEnd - dtStart) / 1e3);

        case "n":
            return parseInt((dtEnd - dtStart) / 6e4);

        case "h":
            return parseInt((dtEnd - dtStart) / 36e5);

        case "d":
            return parseInt((dtEnd - dtStart) / 864e5);

        case "w":
            return parseInt((dtEnd - dtStart) / (864e5 * 7));

        case "m":
            return dtEnd.getMonth() + 1 + (dtEnd.getFullYear() - dtStart.getFullYear()) * 12 - (dtStart.getMonth() + 1);

        case "y":
            return dtEnd.getFullYear() - dtStart.getFullYear();
    }
};

// 获取本月的第1天
// 说明：获取本月的第1天
// 参数：
// 调用：new Date().FirstDayOfMonth();
// 返回值：时间格式,获取本月的第1天
Date.prototype.FirstDayOfMonth = function() {
    this.setDate(1);
    return this;
};

// 获取本月的第最后一天
// 说明：获取本月的第1天
// 参数：
// 调用：new Date().LastDayOfMonth();
// 返回值：时间格式,获取本月的最后一天
Date.prototype.LastDayOfMonth = function() {
    var dt = this;
    dt.setDate(1);
    dt.setMonth(dt.getMonth() + 1);
    return dt.DateAdd('d', -1);
};

// 说明：判断日期格式是否早于被判断日期
// 参数：日期
// 调用：new Date().before();
// 返回值：boolean
Date.prototype.before = function(date) {
    var dt = typeof date == 'string' ? new Date(date) : date;
    return this < dt;
};

// 说明：判断日期格式是否 晚于被判断日期
// 参数：日期
// 调用：new Date().FirstDayOfMonth();
// 返回值：boolean
Date.prototype.after = function(date) {
    var dt = typeof date == 'string' ? new Date(date) : date;
    return this > dt;
};

// 禁止回退函数
// 说明：采用浏览器版本的用户，经常因为点了回退按钮导致页面回退
// 参数：e js事件
// 调用：forbidBackSpace(e)
// 返回值：
function forbidBackSpace(e) {
    var ev = e || window.event;
    // 获取event对象
    var obj = ev.target || ev.srcElement;
    // 获取事件源
    var t = obj.type || obj.getAttribute("type");
    // 获取事件源类型
    // 获取作为判断条件的事件类型
    var vReadOnly = obj.readOnly;
    var vDisabled = obj.disabled;
    // 处理undefined值情况
    vReadOnly = vReadOnly == undefined ? false : vReadOnly;
    vDisabled = vDisabled == undefined ? true : vDisabled;
    // 当敲Backspace键时，事件源类型为密码或单行、多行文本的，
    // 并且readOnly属性为true或disabled属性为true的，则退格键失效
    var flag1 = ev.keyCode == 8 && (t == "password" || t == "text" || t == "textarea") && (vReadOnly == true || vDisabled == true);
    // 当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效
    var flag2 = ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea";
    // 判断
    if (flag2 || flag1) return false;
};

// 初始化吐司
toastr.options = {
    closeButton: false,
    debug: false,
    progressBar: false,
    positionClass: "toast-top-center",
    onclick: null,
    showDuration: "300",
    hideDuration: "200",
    timeOut: "3000",
    extendedTimeOut: "1000",
    showEasing: "swing",
    hideEasing: "linear",
    showMethod: "fadeIn",
    hideMethod: "fadeOut"
};

// 禁止后退键 作用于Firefox、Opera
document.onkeypress = forbidBackSpace;

// 禁止后退键 作用于IE、Chrome
document.onkeydown = forbidBackSpace;


/** ************************************************************************************** */
/** *****************************####EXTJS原生扩建的扩展###******************************* */
/** ************************************************************************************** */

// toast的扩展
// 参数:msg 需要显示的吐司的文字
// type 吐司的类型，默认为success，绿色,error为红色
// 调用：Ext.Msg.toast('保存失败', 'error')
// 返回值：
Ext.override(Ext.window.MessageBox, {
    toast: function(msg, type) {
        if (type == 'error') {
            toastr.error(msg);
        } else {
            toastr.success(msg);
        }
    }
});

// Table的扩展
// 返回值：
Ext.override(Ext.view.Table, {
    getActiveIndex: function() {
        return 0;
    },
    getMaxContentWidth: function(header) {
        var me = this,
            cells = me.el.query(header.getCellInnerSelector()),
            originalWidth = header.getWidth(),
            i = 0,
            ln = cells.length,
            columnSizer = me.body.select(me.getColumnSizerSelector(header)),
            max = Math.max,
            widthAdjust = 0,
            maxWidth;

        if (ln > 0) {
            if (Ext.supports.ScrollWidthInlinePaddingBug) {
                widthAdjust += me.getCellPaddingAfter(cells[0]);
            }
            if (me.columnLines) {
                widthAdjust += Ext.fly(cells[0].parentNode).getBorderWidth('lr');
            }
        }

        // Set column width to 1px so we can detect the content width by
        // measuring scrollWidth
        columnSizer.setWidth(1);

        // We are about to measure the offsetWidth of the textEl to determine
        // how much
        // space the text occupies, but it will not report the correct width if
        // the titleEl
        // has text-overflow:ellipsis. Set text-overflow to 'clip' before
        // proceeding to
        // ensure we get the correct measurement.
        header.titleEl.setStyle('text-overflow', 'clip');

        // Allow for padding round text of header
        maxWidth = header.textEl.dom.offsetWidth + header.titleEl.getPadding('lr');

        // revert to using text-overflow defined by the stylesheet
        header.titleEl.setStyle('text-overflow', '');

        for (; i < ln; i++) {
            maxWidth = max(maxWidth, cells[i].scrollWidth);
        }

        // in some browsers, the "after" padding is not accounted for in the
        // scrollWidth
        maxWidth += widthAdjust;

        // 40 is the minimum column width. TODO: should this be configurable?
        maxWidth = max(maxWidth, 40);

        columnSizer.setWidth(originalWidth);

        return maxWidth;
    },

    afterRender: function() {
        var me = this;

        me.callParent();
        me.mon(me.el, {
            scope: me,
            click: me.handleEvent,
            longpress: me.handleEvent,
            mousedown: me.handleEvent,
            mouseup: me.handleEvent,
            dblclick: me.handleEvent,
            contextmenu: me.handleEvent,
            keydown: me.handleEvent,
            keyup: me.handleEvent,
            keypress: me.handleEvent,
            mouseover: me.handleMouseOver,
            mouseout: me.handleMouseOut
        });
    }
});

// 重写chekboxGroup控件
// 说明：重写了setValue()和getValue()方法
// 调用：
// 返回值：
Ext.override(Ext.form.CheckboxGroup, {
	override: true,
    getValue: function() {
        var me = this,
            result = '',
            values = [],
            boxes = this.getBoxes(),
            b,
            bLen = boxes.length,
            box, name, inputValue, bucket;

        for (b = 0; b < bLen; b++) {
            box = boxes[b];
            name = box.getName();
            inputValue = box.inputValue;

            if (box.getValue()) {
                values.push(inputValue);
            }
        }

        for (var i = 0; i < values.length; i++) {
            result = result + values[i];
            if (i != values.length - 1) {
                result = result + ',';
            }
        }

        return result;
    },
    setValue: function(value) {
        var me = this,
            boxes = me.getBoxes(),
            b,
            bLen = boxes.length,
            box, name,
            values = value.split(','),
            cbValue;

        me.batchChanges(function() {
            Ext.suspendLayouts();
            for (b = 0; b < bLen; b++) {
                box = boxes[b];
                name = box.getName();
                cbValue = false;

                if (value) {
                    if (Ext.isArray(values)) {
                        cbValue = Ext.Array.contains(values, box.inputValue);
                    } else {
                        cbValue = values;
                    }
                }

                box.setValue(cbValue);
            }
            Ext.resumeLayouts(true);
        });
        return me;
    }
});

// 复选框重写
// 说明： 不包含名称的话，是以group的形式存在，不重写值
// 调用：
// 返回值：
Ext.override(Ext.form.field.Checkbox, {
    override: true,

    setValue: function(checked) {
        var me = this,
            boxes, i, len, box;

        if (typeof checked == "undefined") {
            return me;
        }
        // If an array of strings is passed, find all checkboxes in the group
        // with the same name as this
        // one and check all those whose inputValue is in the array, unchecking
        // all the others. This is to
        // facilitate setting values from Ext.form.Basic#setValues, but is not
        // publicly documented as we
        // don't want users depending on this behavior.
        if (Ext.isArray(checked)) {
            boxes = me.getManager().getByName(me.name, me.getFormId()).items;
            len = boxes.length;

            for (i = 0; i < len; ++i) {
                box = boxes[i];
                box.setValue(Ext.Array.contains(checked, box.inputValue));
            }
        } else {
            // The callParent() call ends up trigger setRawValue, we only want
            // to modify
            // the lastValue when setRawValue being called independently.
            me.duringSetValue = true;
            me.callParent(arguments);
            delete me.duringSetValue;
        }
        return me;
    }
});

// 复选框重写
// 说明： 不包含名称的话，是以group的形式存在，不重写值
// 调用：
// 返回值：
Ext.override(Ext.form.field.Radio, {
    override: true,

    initComponent: function() {
        var me = this;
        me.inputValue = me.inputValue || me.boxLabel;
        me.callParent();
    },
    setValue: function(value) {
        var me = this,
            container, active;

        if (Ext.isBoolean(value)) {
        	me.callParent(arguments);
        }
        else if  (value == me.inputValue) {
        	me.setValue(true);
        } else {
            if (value == me.boxLabel) {
                active.setValue(true);
            }
        }
        return me;
    }
});


// 菜单重写
// 说明： 解决动态生成菜单，但是handler无法识别的问题
Ext.override(Ext.menu.Item, {
    initComponent: function() {
        var me = this,
            cls = me.cls ? [me.cls] : [],
            menu;

        // During deprecation period of canActivate config, copy it into
        // focusable config.
        if (me.hasOwnProperty('canActivate')) {
            me.focusable = me.canActivate;
        }

        if (me.plain) {
            cls.push(Ext.baseCSSPrefix + 'menu-item-plain');
        }

        if (cls.length) {
            me.cls = cls.join(' ');
        }

        if (me.menu) {
            menu = me.menu;
            me.menu = null;
            me.setMenu(menu);
        }

        if (me.handler && typeof me.handler === 'string') {
            me.handler = Utils.strToFun(me.handler);
        }
        me.callParent(arguments);
    }
});

// 重写MessageBox
// 说明: 禁用ESC关闭，强制响应OK
Ext.override(Ext.window.MessageBox, {
    onEsc: function() {
        var me = this;

    },
    initComponent: function() {
        this.on('afterrender', function(me) {
            me.header.child('[type=close]').el.dom.children[0].style.display = "none";
            $("#" + me.id + " > div:last").css('border-width', "1px 0 0 0");
            $("#" + me.id + " > div:last").css('border-color', "#999");
            $("#" + me.id + " > div:last").css('width', "250px");
            $("#" + me.id + " > div:last").offset({
                left: 0,
                top: 0
            });
            $("#" + me.id + " > div:last > a").offset({
                left: 0,
                top: 0
            });
        });
        this.callParent(arguments);
    }
});

// 日期选择框重写
// 说明： 将英文的日期名称改为中文
Ext.override(Ext.picker.Date, {
    initComponent: function() {
        this.dayNames = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
        this.callParent();
    }
});

// grid的tableview重写
// 说明：记录
Ext.override(Ext.view.Table, {
    initComponent: function() {
        var me = this;

        me.rowTpl = [
            '{%',
            'var dataRowCls = values.recordIndex === -1 ? "" : " ' + Ext.baseCSSPrefix + 'grid-row";',
            '%}',
            '<tr class="{[values.rowClasses.join(" ")]} {[dataRowCls]}" {rowAttr:attributes} {ariaRowAttr} style="background-color:{rowClassColor};">',
            '<tpl for="columns">' +
            '{%',
            'parent.view.renderCell(values, parent.record, parent.recordIndex, parent.rowIndex, xindex - 1, out, parent)',
            '%}',
            '</tpl>',
            '</tr>', {
                priority: 0
            }
        ];

        me.cellTpl = [
            '<td class="{tdCls}" {tdAttr} {[Ext.aria ? "id=\\"" + Ext.id() + "\\"" : ""]} style="width:{column.cellWidth}px;<tpl if="tdStyle">{tdStyle}</tpl>" tabindex="-1" {ariaCellAttr} data-columnid="{[values.column.getItemId()]}">',
            '<div {unselectableAttr} class="' + Ext.baseCSSPrefix + 'grid-cell-inner {innerCls}" ',
            'style="text-align:{align};<tpl if="style">{style}</tpl>" {ariaCellInnerAttr}>{value}</div>',
            '</td>', {
                priority: 0
            }
        ];

        me.callParent();
    },
    /**
	 * @private Renders the HTML markup string for a single row into the passed
	 *          array as a sequence of strings, or returns the HTML markup for a
	 *          single row.
	 * 
	 * @param {Ext.data.Model}
	 *            record The record to render.
	 * @param {String[]}
	 *            [out] A string array onto which to append the resulting HTML
	 *            string. If omitted, the resulting HTML string is returned.
	 * @return {String} **only when the out parameter is omitted** The resulting
	 *         HTML string.
	 */
    renderRow: function(record, rowIdx, out) {
        var me = this,
            isMetadataRecord = rowIdx === -1,
            selModel = me.selectionModel,
            rowValues = me.rowValues,
            itemClasses = rowValues.itemClasses,
            rowClasses = rowValues.rowClasses,
            itemCls = me.itemCls,
            cls,
            rowTpl = me.rowTpl;

        // Define the rowAttr object now. We don't want to do it in the treeview
        // treeRowTpl because anything
        // this is processed in a deferred callback (such as deferring initial
        // view refresh in gridview) could
        // poke rowAttr that are then shared in tableview.rowTpl. See
        // EXTJSIV-9341.
        //
        // For example, the following shows the shared ref between a treeview's
        // rowTpl nextTpl and the superclass
        // tableview.rowTpl:
        //
        // tree.view.rowTpl.nextTpl === grid.view.rowTpl
        //
        rowValues.rowAttr = {};

        // Set up mandatory properties on rowValues
        rowValues.record = record;
        rowValues.recordId = record.internalId;

        // recordIndex is index in true store (NOT the data source - possibly a
        // GroupStore)
        rowValues.recordIndex = me.store.indexOf(record);

        // rowIndex is the row number in the view.
        rowValues.rowIndex = rowIdx;
        rowValues.rowId = me.getRowId(record);
        rowValues.itemCls = rowValues.rowCls = '';
        if (!rowValues.columns) {
            rowValues.columns = me.ownerCt.getVisibleColumnManager().getColumns();
        }

        itemClasses.length = rowClasses.length = 0;

        // If it's a metadata record such as a summary record.
        // So do not decorate it with the regular CSS.
        // The Feature which renders it must know how to decorate it.
        if (!isMetadataRecord) {
            itemClasses[0] = itemCls;

            if (!me.ownerCt.disableSelection && selModel.isRowSelected) {
                // Selection class goes on the outermost row, so it goes into
                // itemClasses
                if (selModel.isRowSelected(record)) {
                    itemClasses.push(me.selectedItemCls);
                }
            }

            if (me.stripeRows && rowIdx % 2 !== 0) {
                itemClasses.push(me.altRowCls);
            }

            if (me.getRowClass) {
                cls = me.getRowClass(record, rowIdx, null, me.dataSource);
                if (cls) {
                    rowClasses.push(cls);
                }
            }

            var color = record.get('bgcolor');
            if (color) {
                rowValues.rowClassColor = color.length == 6 ? '#' + color : color;
            } else {
                rowValues.rowClassColor = undefined;
            }
        }

        if (out) {
            rowTpl.applyOut(rowValues, out, me.tableValues);
        } else {
            return rowTpl.apply(rowValues, me.tableValues);
        }
    },
    walkCells: function(pos, direction, e, preventWrap, verifierFn, scope) {
        if (!pos) {
            return false;
        }
        var me = this,
            row = typeof pos.row === 'number' ? pos.row : pos.rowIdx,
            column = typeof pos.column === 'number' ? pos.column : pos.colIdx,
            rowCount = me.dataSource.getCount(),
            columns = me.ownerCt.getVisibleColumnManager(),
            firstIndex = columns.getHeaderIndex(columns.getFirst()),
            lastIndex = columns.getHeaderIndex(columns.getLast()),
            newRow = row,
            newColumn = column,
            activeHeader = columns.getHeaderAtIndex(column);

        if (!activeHeader || activeHeader.hidden || !rowCount) {
            return false;
        }

        e = e || {};
        direction = direction.toLowerCase();
        switch (direction) {
            case 'right':

                if (column === lastIndex) {

                    if (preventWrap || row === rowCount - 1) {
                        return false;
                    }
                    if (!e.ctrlKey) {

                        newRow = me.walkRows(row, 1);
                        if (newRow !== row) {
                            newColumn = firstIndex;
                        }
                    }
                } else {
                    if (!e.ctrlKey) {
                        newColumn = columns.getHeaderIndex(columns.getNextSibling(activeHeader));
                    } else {
                        newColumn = lastIndex;
                    }
                };
                break;
            case 'left':

                if (column === firstIndex) {

                    if (preventWrap || row === 0) {
                        return false;
                    }
                    if (!e.ctrlKey) {
                        newRow = me.walkRows(row, -1);
                        if (newRow !== row) {
                            newColumn = lastIndex;
                        }
                    }
                } else {
                    if (!e.ctrlKey) {
                        newColumn = columns.getHeaderIndex(columns.getPreviousSibling(activeHeader));
                    } else {
                        newColumn = firstIndex;
                    }
                };
                break;
            case 'up':
                if (row === 0) {
                    return false;
                } else {
                    if (!e.ctrlKey) {
                        newRow = me.walkRows(row, -1);
                    } else {
                        newRow = me.walkRows(-1, 1);
                    }
                };
                break;
            case 'down':
                if (row === rowCount - 1) {
                    return false;
                } else {
                    if (!e.ctrlKey) {
                        newRow = me.walkRows(row, 1);
                    } else {
                        newRow = me.walkRows(rowCount, -1);
                    }
                };
                break;
        }
        if (verifierFn && verifierFn.call(scope || me, {
            row: newRow,
            column: newColumn
        }) !== true) {
            return false;
        }
        newColumn = columns.getHeaderAtIndex(newColumn);

        return new Ext.grid.CellContext(me).setPosition(newRow, newColumn);
    }
});

// selectmodel重写
// 说明： 记录
Ext.override(Ext.selection.Model, {
    selectPoint: function(rows) {
        var me = this,
            store = me.store,
            toSelect = [];

        for (var i = 0; i <= rows.length; i++) {
            if (!me.isSelected(store.getAt(rows[i]))) {
                toSelect.push(store.getAt(rows[i]));
            }
        }

        me.doMultiSelect(toSelect, true);
    },

    /**
	 * Selects a range of rows if the selection model
	 * {@link #isLocked is not locked}. All rows in between startRow and endRow
	 * are also selected.
	 * 
	 * @param {Ext.data.Model/Number}
	 *            startRow The record or index of the first row in the range
	 * @param {Ext.data.Model/Number}
	 *            endRow The record or index of the last row in the range
	 * @param {Boolean}
	 *            keepExisting (optional) True to retain existing selections
	 */
    selectRange: function(startRow, endRow, keepExisting) {
        var me = this,
            store = me.store,
            selected = me.selected.items,
            result, i, len, toSelect, toDeselect, idx, rec;

        if (me.isLocked()) {
            return;
        }

        result = me.normalizeRowRange(startRow, endRow);
        startRow = result[0];
        endRow = result[1];

        toSelect = [];
        for (i = startRow; i <= endRow; i++) {
            if (!me.isSelected(store.getAt(i))) {
                toSelect.push(store.getAt(i));
            }
        }

        if (!keepExisting) {
            toDeselect = [];
            me.suspendChanges();

            for (i = 0, len = selected.length; i < len; ++i) {
                rec = selected[i];
                idx = store.indexOf(rec);
                if (idx < startRow || idx > endRow) {
                    toDeselect.push(rec);
                }
            }

            for (i = 0, len = toDeselect.length; i < len; ++i) {
                me.doDeselect(toDeselect[i]);
            }
            me.resumeChanges();
        }

        if (toSelect.length) {
            me.doMultiSelect(toSelect, true);
        } else if (toDeselect) {
            me.maybeFireSelectionChange(toDeselect.length > 0);
        }
    }
});

// keymap重写
// 说明： 记录
Ext.override(Ext.util.KeyMap, {
    processKeys: function(keyCode) {
        var processed = false,
            key, keys, keyString, len, i;

        if (keyCode && keyCode.test) {
            return keyCode;
        }

        if (Ext.isString(keyCode)) {
            keys = [];
            keyString = keyCode.toUpperCase();
            for (i = 0, len = keyString.length; i < len; ++i) {
                keys.push(keyString.charCodeAt(i));
            }
            keyCode = keys;
            processed = true;
        }

        if (!Ext.isArray(keyCode)) {
            keyCode = [keyCode];
        }
        if (!processed) {
            for (i = 0, len = keyCode.length; i < len; ++i) {
                key = keyCode[i];
                if (Ext.isString(key)) {
                    keyCode[i] = key.toUpperCase().charCodeAt(0);
                }
            }
        }
        return keyCode;
    },
    processBinding: function(binding, event) {
        if (this.checkModifiers(binding, event)) {
            var key = event.getKey(),
                handler = binding.fn || binding.handler,
                scope = binding.scope || this,
                keyCode = binding.keyCode,
                defaultEventAction = binding.defaultEventAction,
                i, len;
            if (keyCode.test) {
                if (keyCode.test(String.fromCharCode(event.getCharCode()))) {
                    if (handler.call(scope, key, event) !== true && defaultEventAction) {
                        event[defaultEventAction]();
                    }
                }
            } else if (keyCode.length) {
                for (i = 0, len = keyCode.length; i < len; ++i) {
                    if (key === keyCode[i]) {
                        if (!Utils.isEmpty(handler) && handler.call(scope, key, event) !== true && defaultEventAction) {
                            event[defaultEventAction]();
                        }
                        break;
                    }
                }
            }
        }
    }
});

// 重写ColumnHeader的三角形事件
// 说明： 在grid的三角形点击中，加入EXCEL的常用功能；显示全部、前10个 、高级等选项
Ext.override(Ext.grid.header.Container, {
    getMenu: function() {
        var me = this;

        if (!me.menu) {
            me.menu = new Ext.menu.Menu({
                plain: true,
                hideOnParentHide: false,
                items: me.getMenuItems(),
                listeners: {
                    beforeshow: me.beforeMenuShow,
                    hide: me.onMenuHide,
                    scope: me
                }
            });
            me.fireEvent('menucreate', me, me.menu);
        }
        return me.menu;
    },

    getMenuItems: function() {
        return [];
    },

    beforeMenuShow: Ext.emptyFn,

    doFilter: function(dataIndex, text, type) {
        var me = this.grid;
        if (text == '全部') {
            me.store.clearFilter();
            return;
        } else if (text == '(前10个)') {
            //me.store.clearFilter();
            var i = 0;
            me.store.filterBy(function(record, id) {
                i++;
                return i <= 10;
            });
            me.down('clearFilter').add({
            	text: me.getColumn(dataIndex).text,
            	oper: 'top',
            	val: text
            });
        } else if (text == '高级') {
            var items = [];
            if (type == 'int' || type == 'number' || type == 'float' || type == 'double') {
                items = [{
                    xtype: 'textfield',
                    labelWidth: 20,
                    emptyText: '输入数字',
                    vtype: 'negative',
                    fieldLabel: '<=',
                    listeners: {
                        change: function(txt) {
                            me.store.filterBy(function(record, id) {
                                return new Number(record.get(dataIndex)) <= new Number(txt.getValue());
                            });
                            
                            me.down('clearFilter').add({
                            	text: me.getColumn(dataIndex).text,
                            	oper: '<=',
                            	val: '小于等于'+txt.getValue()
                            });
                        }
                    }
                }, {
                    xtype: 'textfield',
                    labelWidth: 20,
                    emptyText: '输入数字',
                    vtype: 'negative',
                    fieldLabel: '>=',
                    listeners: {
                        change: function(txt) {
                            me.store.filterBy(function(record, id) {
                                return new Number(record.get(dataIndex)) >= new Number(txt.getValue());
                            });
                            
                            me.down('clearFilter').add({
                            	text: me.getColumn(dataIndex).text,
                            	oper: '>=',
                            	val: '大于等于'+txt.getValue()
                            });
                        }
                    }
                }, {
                    xtype: 'textfield',
                    labelWidth: 20,
                    emptyText: '输入数字',
                    vtype: 'negative',
                    fieldLabel: '=',
                    listeners: {
                        change: function(txt) {
                            me.store.filterBy(function(record, id) {
                                return new Number(record.get(dataIndex)) == new Number(txt.getValue());
                            });
                            
                            me.down('clearFilter').add({
                            	text: me.getColumn(dataIndex).text,
                            	oper: '=',
                            	val: '等于'+txt.getValue()
                            });
                        }
                    }
                }];
            } else if (type == 'string' || type == 'String') {
                if (/^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)$/.test(me.store.getAt(0).get(dataIndex))) {
                    // 证明是日期格式
                    items = [{
                        xtype: 'datefield',
                        labelWidth: 60,
                        emptyText: '开始时间',
                        fieldLabel: '开始时间',
                        listeners: {
                            change: function(txt) {
                                me.store.filterBy(function(record, id) {
                                    return new Date(record.get(dataIndex)).after(new Date(txt.getValue()));
                                });
                                
                                me.down('clearFilter').add({
                                	text: me.getColumn(dataIndex).text,
                                	oper: '>=',
                                	val: "开始"+txt.getValue()
                                });
                            }
                        }
                    }, {
                        xtype: 'datefield',
                        labelWidth: 60,
                        emptyText: '结束时间',
                        fieldLabel: '结束时间',
                        listeners: {
                            change: function(txt) {
                                me.store.filterBy(function(record, id) {
                                    return new Date(record.get(dataIndex)).before(new Date(txt.getValue()).DateAdd('d', 1));
                                });
                                
                                me.down('clearFilter').add({
                                	text: me.getColumn(dataIndex).text,
                                	oper: '<=',
                                	val: "结束"+txt.getValue()
                                });
                            }
                        }
                    }];
                } else {
                    items = [{
                        xtype: 'textfield',
                        labelWidth: 0,
                        anchor: '100%',
                        emptyText: '输入字符串',
                        labelSeparator: '',
                        listeners: {
                            change: function(txt) {
                                // me.store.clearFilter();
                                var reg = new RegExp(".*" + txt.getValue().toUpperCase() + ".*");
                                me.store.filterBy(function(record, id) {
                                    return reg.test(record.get(dataIndex).toUpperCase());
                                });
                                
                                me.down('clearFilter').add({
                                	text: me.getColumn(dataIndex).text,
                                	oper: '%',
                                	val: '匹配'+txt.getValue()
                                });
                            }
                        }
                    }];
                }
            }

            var popWin = Ext.create('Ext.window.Window', {
                title: '高级搜索',
                width: 230,
                height: items.length * 30 + 48,
                items: [{
                    xtype: 'form',
                    items: items
                }]
            }).show();

            popWin.down('textfield').focus(true);

        } else {
            // me.store.clearFilter();
            me.store.filterBy(function(record, id) {
            	return record.get(dataIndex) == text;
            });
            me.down('clearFilter').add({
            	text: me.getColumn(dataIndex).text,
            	oper: '=',
            	val: text
            });
        }
    }
});

Ext.override(Ext.data.AbstractStore, {
	clearFilter: function(sort) {
        var me = this,
            filters = me.getFilters(false);
        if (!filters || filters.getCount() === 0) {
            return;
        }
        if (!sort && sort != 0)
        	filters.removeAll();
        else
        	filters.splice(sort, 1);                           
        me.suppressNextFilter = false;
    }
});

// 重写HTML编辑器
// 说明: 无
Ext.override(Ext.form.field.HtmlEditor, {
    execCmd: function(cmd, value) {
        var me = this,
            doc = me.getDoc();
        doc.execCommand(cmd, false, (value === undefined ? null : value));
        me.syncValue();
    },
    initEditor: function() {
        var me = this,
            dbody, ss, doc, docEl, fn;

        if (me.destroying || me.isDestroyed) {
            return;
        }

        dbody = me.getEditorBody();
        ss = me.textareaEl.getStyle(['font-size', 'font-family', 'background-image', 'background-repeat', 'background-color', 'color']);

        ss['background-attachment'] = 'fixed'; // w3c
        dbody.bgProperties = 'fixed'; // ie
        Ext.DomHelper.applyStyles(dbody, ss);

        doc = me.getDoc();
        docEl = Ext.get(doc);

        if (docEl) {
            try {
                docEl.clearListeners();
            } catch (e) {}

            fn = me.onEditorEvent.bind(me);
            docEl.on({
                mousedown: fn,
                dblclick: fn,
                click: fn,
                keyup: fn,
                keydown: function(e) {
                    if (e.ctrlKey && e.keyCode == 13) {
                        me.fireEvent('enterKey', me, e);
                    }
                    if (e.ctrlKey && e.keyCode == 86) {
                        me.fireEvent('pasteKey', me, e);
                    }
                },
                delegated: false,
                buffer: 100
            });

            fn = me.onRelayedEvent;
            docEl.on({
                mousedown: fn,
                // menu dismisal (MenuManager) and Window onMouseDown (toFront)
                mousemove: fn,
                // window resize drag detection
                mouseup: fn,
                // window resize termination
                click: fn,
                // not sure, but just to be safe
                dblclick: fn,
                // not sure again
                delegated: false,
                scope: me
            });

            if (Ext.isGecko) {
                docEl.on('keypress', me.applyCommand, me);
            }

            if (me.fixKeys) {
                docEl.on('keydown', me.fixKeys, me);
            }
            if (me.fixKeysAfter) {
                docEl.on('keyup', me.fixKeysAfter, me);
            }

            if (Ext.isIE9) {
                Ext.get(doc.documentElement).on('focus', me.focus, me);
            }

            if (Ext.isIE8) {
                docEl.on('focusout',
                    function() {
                        me.savedSelection = doc.selection.type !== 'None' ? doc.selection.createRange() : null;
                    },
                    me);

                docEl.on('focusin',
                    function() {
                        if (me.savedSelection) {
                            me.savedSelection.select();
                        }
                    },
                    me);
            }

            Ext.getWin().on('beforeunload', me.beforeDestroy, me);
            doc.editorInitialized = true;

            me.initialized = true;
            me.pushValue();
            me.setReadOnly(me.readOnly);
            me.fireEvent('initialize', me);
        }
    }
});

// 通用组件重写
// 说明:
Ext.override(Ext.Component, {
    // 获取顶级
    top: function() {
        var toptip = Utils.loadParentWindow(this);
        return toptip;
    },

    /**
	 * Allows addition of behavior to the hide operation. After calling the
	 * superclass's onHide, the Component will be hidden.
	 * 
	 * Gets passed the same parameters as #hide.
	 * 
	 * @param {String/Ext.dom.Element/Ext.Component}
	 *            [animateTarget]
	 * @param {Function}
	 *            [callback]
	 * @param {Object}
	 *            [scope]
	 * 
	 * @template
	 * @protected
	 */
    onHide: function(animateTarget, cb, scope) {
        var me = this,
            ghostPanel, fromSize, toBox, focusTarget = me.previousFocus;

        me.previousFocus = null;

        // IF this floating component contains focus...
        // Before hiding, restore focus to what was focused when we were shown.
        // IE8 will throw an exception is the target is not focusable
        // Blur the focused element before hiding to force focusLeave
        if (me.floating && focusTarget && me.containsFocus) {
            // Allow the previousFocus target to be an htmlEement or Component
            if (!focusTarget.isComponent) {
                focusTarget = Ext.fly(focusTarget);
            }
            if (Ext.isIE8 || focusTarget.isFocusable()) {
                focusTarget.focus();
            }
        }

        // Default to configured animate target if none passed
        animateTarget = me.getAnimateTarget(animateTarget);

        // Need to be able to ghost the Component
        if (!me.ghost) {
            animateTarget = null;
        }
        // If we're animating, kick off an animation of the ghost down to the
        // target
        if (animateTarget) {
            toBox = {
                x: animateTarget.getX(),
                y: animateTarget.getY(),
                width: animateTarget.dom.offsetWidth,
                height: animateTarget.dom.offsetHeight
            };
            ghostPanel = me.ghost();
            ghostPanel.el.stopAnimation();
            fromSize = me.getSize();
            ghostPanel.el.animate({
                to: toBox,
                listeners: {
                    afteranimate: function() {
                        delete ghostPanel.componentLayout.lastComponentSize;
                        ghostPanel.el.hide();
                        ghostPanel.setHiddenState(true);
                        ghostPanel.el.setSize(fromSize);
                        me.afterHide(cb, scope);
                    }
                }
            });
        }
        me.el.hide();
        if (!animateTarget) {
            me.afterHide(cb, scope);
        }
    },

    /**
	 * Hides this Component, setting it to invisible using the configured
	 * {@link #hideMode}.
	 * 
	 * @param {String/Ext.dom.Element/Ext.Component}
	 *            [animateTarget=null] **only valid for {@link #cfg-floating}
	 *            Components such as {@link Ext.window.Window Window}s or
	 *            {@link Ext.tip.ToolTip ToolTip}s, or regular Components which
	 *            have been configured with `floating: true`.**. The target to
	 *            which the Component should animate while hiding.
	 * @param {Function}
	 *            [callback] A callback function to call after the Component is
	 *            hidden.
	 * @param {Object}
	 *            [scope] The scope (`this` reference) in which the callback is
	 *            executed. Defaults to this Component.
	 * @return {Ext.Component} this
	 */
    hide: function(animateTarget, cb, scope) {
        var me = this,
            continueHide;

        if (me.pendingShow) {
            // If this is a hierarchically hidden floating component with a
            // pending show
            // hide() simply cancels the pending show.
            delete me.pendingShow;
        }

        if (!(me.rendered && !me.isVisible())) {
            continueHide = (me.fireEvent('beforehide', me) !== false);
            if (me.hierarchicallyHidden || continueHide) {
                me.hidden = true;
                me.getInherited().hidden = true;
                if (me.rendered) {
                    // Must deactivate floaters *before* hiding so that they can
                    // detect whether they currently
                    // contain focus, and transfer focus to the previously
                    // focused element.
                    if (me.floating) {
                        me.setActive(false);
                    }
                    me.onHide.apply(me, arguments);
                }
            }
        }
        return me;
    }
});

// 列表的单元格重写
// 说明:无
Ext.override(Ext.grid.CellEditor, {
    onFocusLeave: function(e) {
        this.previousFocus = null;
        this.callParent([e]);
        this.selectSameEditor = false;
    }
});

// 重写GIRD编辑框
// 说明: 1、记录下当前grid的焦点信息
// 2、焦点离开时隐藏编辑框，判断是否存在公式，如果存在就不能隐藏，一直显示
Ext.override(Ext.Editor, {
    completeEdit: function(remainVisible) {
        var me = this,
            field = me.field,
            startValue = me.startValue,
            value;
        if (!me.editing) {
            return;
        }

        // Assert combo values first
        if (field.assertValue) {
            field.assertValue();
        }

        value = me.getValue();
        if (!field.isValid()) {
            if (me.revertInvalid !== false) {
                me.cancelEdit(remainVisible);
            }
            return;
        }

        if (me.ignoreNoChange && !field.didValueChange(value, startValue)) {
            me.onEditComplete(remainVisible);
            return;
        }

        if (me.fireEvent('beforecomplete', me, value, startValue) !== false) {
            // Grab the value again, may have changed in beforecomplete
            value = me.getValue();
            if (me.updateEl && me.boundEl) {
                me.boundEl.setHtml(value);
            }
            me.onEditComplete(remainVisible);
            me.fireEvent('complete', me, value, startValue, field.originalValue);
        }
    },
    onEditComplete: function(remainVisible) {
        this.editing = false;
        if (remainVisible !== true) {
            this.hide();
            this.toggleBoundEl(true);
        }
    },
    startEdit: function(el, value) {
        var me = this,
            field = me.field,
            grid = field.up('grid'),
            dom,
            ownerCt,
            renderTo;
        if (grid) {
            grid.currentPosition = {
                row: grid.plugins[0].context.rowIdx,
                column: grid.plugins[0].context.colIdx
            };

            value = grid.plugins[0].context.column.formula || value;
        }
        me.completeEdit();
        me.boundEl = Ext.get(el);
        dom = me.boundEl.dom;
        value = Ext.isDefined(value) ? value : Ext.String.trim(dom.textContent || dom.innerText || dom.innerHTML);
        if (me.fireEvent("beforestartedit", me, me.boundEl, value) !== false) {
            // If NOT configured with a renderTo, render to the ownerCt's
            // element
            // Being floating, we do not need to use the actual layout's target.
            // Indeed, it's better if we do not so that we do not interfere with
            // layout's child management.
            Ext.suspendLayouts();
            if (!me.rendered) {
                ownerCt = me.ownerCt;
                renderTo = me.renderTo || ownerCt && ownerCt.getEl() || Ext.getBody();
                Ext.fly(renderTo).position();
                me.renderTo = renderTo;
            }
            me.startValue = value;
            me.show();
            me.realign(true);
            // temporarily suspend events on field to prevent the "change" event
            // from firing when resetOriginalValue() and setValue() are called
            field.suspendEvents();
            field.setValue(value);
            field.resetOriginalValue();
            field.resumeEvents();
            // 改为立刻获取焦点
            field.focus(true);
            if (field.autoSize) {
                field.autoSize();
            }
            Ext.resumeLayouts(true);
            me.toggleBoundEl(false);
            me.editing = true;
        }
    },
    onHide: function() {
        var me = this,
            field = me.field,
            grid = me.up('grid');

        if (grid && grid.formula) {
            return;
        }
        if (me.editing) {
            me.completeEdit();
        } else if (field.collapse) {
            field.collapse();
        }
        me.callParent(arguments);
    }
});

// 重写window弹出框
// 说明: 1、如果
// 2、焦点离开时隐藏编辑框，判断是否存在公式，如果存在就不能隐藏，一直显示
Ext.override(Ext.window.Window, {
    doClose: function() {
        var me = this;

        me.fireEvent('close', me);
        if (me.closeAction === "destroy") {
            me.destroy();
            if (me.summary) me.summary.doClose();
        }
    },

    closeAll: function() {
        this.doClose();
    },

    close: function() {
        var me = this;
        me.hide();
        me.callParent();
        if (me.summary) me.summary.close();
        if (me.myMask) me.myMask.hide();
    },

    hide: function() {
        var me = this;
        me.callParent();
        if (me.summary) {
            me.summary.show();
            var grid = me.summary.down("grid"),
                view = grid.getView();
            grid.setFocus();
        }
    },
    show: function() {
        var me = this;
        if (me.summary) me.summary.hide();
        return me.callParent();
    },
    initComponent: function() {
        var me = this;
        me.closeAction = 'destroy';
        this.on("afterrender", function(me) {
            this.buildKeyMapFast();
        });
        me.detail = function(win) {
            return Ext.create(win, {
                summary: me
            });
        };
        me.callParent();
    }
});

// 重写combo
// 说明：默认的combo是不带清除的按钮的，加入了清除按钮
Ext.override(Ext.form.field.ComboBox, {
    initComponent: function() {
        var me = this,
            data;
        if (this.items) {
            data = new Array();
            for (var i = 0; i < this.items.length; i++) {
                data.push({
                    text: this.items[i].text || this.items[i],
                    value: this.items[i].value || this.items[i]
                });
            }

            Ext.apply(this, {
                displayField: 'text',
                valueField: 'value',
                store: Ext.create('Ext.data.Store', {
                    fields: ['text', 'value'],
                    data: data
                })
            });
        } else if (Utils.isEmpty(this.store)) {
            Ext.apply(this, {
                displayField: 'zdz',
                valueField: 'zdz',
                displayTpl: new Ext.XTemplate(
                    '<tpl for=".">' +
                    '{[typeof values === "string" ? values : values["' + 'zdz' + '"]]}' +
                    '<tpl if="xindex < xcount">' + me.delimiter + '</tpl>' +
                    '</tpl>'
                ),
                store: Ext.create('Ext.data.Store', {
                    fields: ['zdz'],
                    proxy: {
                        type: 'ajax',
                        url: '/query',
                        extraParams: {
                            xtype: 'query_zlzdz',
                            zdmch: this.fdname || this.name
                        },
                        actionMethods: {
                            create: 'POST',
                            read: 'get',
                            update: 'put',
                            destroy: 'delete'
                        },
                        reader: {
                            type: 'json',
                            rootProperty: 'root',
                            totalProperty: 'total'
                        }
                    }
                })
            });
        }

        if (!this.editable) {
            this.editable = false;
        }
        if (this.unclear) {
            this.setTriggers({
                picker: {
                    weight: 1,
                    handler: function() {
                        me.onTriggerClick();
                    },
                    scope: "this"
                }
            });
        } else {
            this.setTriggers({
                clear: {
                    cls: Ext.baseCSSPrefix + "form-clear-trigger",
                    handler: function(me) {
                        me.setValue(null);
                        // me.getTrigger("clear").hide();
                    },
                    scope: "this"
                },
                picker: {
                    weight: 1,
                    handler: function() {
                        me.onTriggerClick();
                        me.getTrigger("clear").show();
                    },
                    scope: "this"
                }
            });
        }

        if (this.groupField) {
            var group = this.groupField;

            this.listConfig = {
                tpl: Ext.create('Ext.XTemplate',
                    '<ul><tpl for=".">',
                    '<tpl if="xindex == 1 || this.getGroupStr(parent[xindex - 2]) != this.getGroupStr(values)">',
                    '<li class="x-combo-list-group"><b>{[this.getGroupStr(values)]}</b></li>',
                    '</tpl>',
                    '<li role="option" class="x-boundlist-item" style="padding-left: 20px">{' + this.displayField + '}</li>',
                    '</tpl>' +
                    '</ul>', {
                        getGroupStr: function(values) {
                            return values[group];
                        }
                    }
                )
            };
        }

        this.on('select', function(combo, record, eOpts) {
            var father = combo.ownerCt;
            for (var prop in record.data) {
                if (father.down('*[name=' + prop + ']'))
                    father.down('*[name=' + prop + ']').setValue(record.get(prop));
            }
        });

        this.onValueCollectionEndUpdate = function() {
            var me = this,
                store = me.store,
                selectedRecords = me.valueCollection.getRange(),
                selectedRecord = selectedRecords[0],
                selectionCount = selectedRecords.length,
                filter = true;

            if (me.beforeSelect) {
                filter = me.beforeSelect(me, selectedRecord);
            }

            if (!filter) {
                return;
            }
            if (selectedRecord && selectedRecord.get('disable') == 1) {
                return;
            }

            me.updateBindSelection(me.pickerSelectionModel, selectedRecords);

            if (me.isSelectionUpdating()) {
                return;
            }

            Ext.suspendLayouts();

            me.updateValue();

            // If we have selected a value, and it's not possible to select any
			// more values
            // or, we are configured to hide the picker each time, then collapse
			// the picker.
            if (selectionCount && ((!me.multiSelect && store.contains(selectedRecord)) || me.collapseOnSelect || !store.getCount())) {
                me.updatingValue = true;
                me.collapse();
                me.updatingValue = false;
            }
            Ext.resumeLayouts(true);
            if (selectionCount && !me.suspendCheckChange) {
                if (!me.multiSelect) {
                    selectedRecords = selectedRecord;
                }
                me.fireEvent('select', me, selectedRecords);
            }
        };

        this.callParent(arguments);
    }
});

Ext.override(Ext.view.BoundList, {
    // Clicking on an already selected item collapses the picker
    onItemClick: function(record) {
        // The selection change events won't fire when clicking on the selected
		// element. Detect it here.
        var me = this,
            pickerField = me.pickerField,
            valueField = pickerField.valueField,
            selected = me.getSelectionModel().getSelection();

        if (!pickerField.multiSelect && selected.length) {
            selected = selected[0];
            // Not all pickerField's have a collapse API, i.e.
			// Ext.ux.form.MultiSelect.
            if (selected && pickerField.isEqual(record.get(valueField), selected.get(valueField)) && pickerField.collapse) {
                pickerField.collapse();
            }
        }
    }
});

// 重写Ext.form.field.Base
// 说明： 默认所有的Form下面不为空字段加上红色的*号
Ext.override(Ext.form.field.Base, {
    // 针对form中的基本组件
    initComponent: function() {


        if (this.allowBlank !== undefined && !this.allowBlank) {
            if (this.fieldLabel) {
                this.fieldLabel += '<span style="color:red">*</span>';
            }
        }
        // 加入快捷键
        if (this.key) {
            this.fieldLabel += "(" + this.key + ")";
        }
        this.getValue = function() {
            var me = this,
                type = this.like;

            if (me.xtype == "combo" || me.xtype == "combobox") {
                var me = this,
                    picker = me.picker,
                    rawValue = me.getRawValue(),
                    value = me.value;

                if (me.getDisplayValue() !== rawValue) {
                    me.displayTplData = undefined;
                    if (picker) {
                        me.valueCollection.suspendEvents();
                        picker.getSelectionModel().deselectAll();
                        me.valueCollection.resumeEvents();
                    }
                    if (me.multiSelect || me.forceSelection) {
                        value = me.value = undefined;
                    } else {
                        value = me.value = rawValue;
                    }
                }

                val = me.value = value == null ? null : value;
            } else if (me.xtype == 'checkbox' || me.xtype == 'radio') {
                val = me.checked ? me.inputValue : me.uncheckedValue;
            } else if (me.xtype == 'ueditor') {
                val = me.ue ? Utils.htmlEscape(me.ue.getContent()) : '';
                me.value = val;
            } else {
                val = me.rawToValue(me.processRawValue(me.getRawValue()));
                me.value = val;
            }

            val = Utils.isEmpty(val) ? '' : val;

            if (typeof(val) === 'string') {
                val = val.trim(val.replace(/(^\s*)|(\s*$)/g, ''));
            }
            if (type == 'rlike') {
                val = val + '%';
            } else if (type == 'llike') {
                val = '%' + val;
            } else if (type == 'like') {
                val = '%' + val + '%';
            } else if (typeof val == 'date' || typeof val == 'object') {
                val = Utils.formatValue(me.rawToValue(me.processRawValue(me.getRawValue())));
            }
            return val;
        };

        this.setFormulaValue = function(val, row) {
            if (val == '') {
                return;
            }

            var chat = this.getValue().substring(this.getValue().length - 1);
            if (Utils.isCalculationChat(chat)) {
                this.setValue(this.getValue() + ":" + val + "[" + row + "]");
            } else {
                this.lastIndexOfCalculationReplace(val, row);
            }
            this.focus(true, 5000);
        };

        // 重最后一个运算符开始替换
        this.lastIndexOfCalculationReplace = function(val, row) {
            var sign = 0;

            for (var i = this.getValue().length - 1; i > -1; i--) {
                if (Utils.isCalculationChat(this.getValue().substring(i - 1, i)) && this.getValue().substring(i - 2, i - 1) != '[') {
                    sign = i;
                    break;
                }
            }
            this.setValue(this.getValue().substring(0, sign) + ":" + val + "[" + row + "]");
        };

        this.callParent(arguments);
    }
});

// 重写panel
// 说明:加入了一些常用操作
Ext.override(Ext.panel.Panel, {
    // 是否草稿记录
    draft: false,
    // 针对form中的基本组件
    initComponent: function() {
        var me = this,
            rclickcollection = [];
        if (me.getXTypes().indexOf("grid") == -1) {
            this.bodyStyle = "border-color:white";
        }
        
        if (this.tool) {
        	
        }
        
        // 获取当前页面的参数
        this.getPageData = function() {
            var form = me.getXTypes().indexOf("form") > -1 ? me : me.down("form"),
                grid = me.down('grid'),
                data = {};

            if (form) {
                Utils.apply(data, form.getFormData());
            }

            if (grid) {
                var records = grid.getRecords('all', grid.exception),
                    result = [];

                for (var i = 0; i < records.length; i++) {
                    result.push(records[i].data);
                }
                data['detail'] = result;
            }
            if (this.task)
                return Utils.apply(data, this.task.data);
            else
                return data;
        };

        // 页面初始化
        // 批量添加长短的验证
        this.pageInit = function() {
        	var form = me.down('form'),
        		formItems = form ? me.down('form').getActiveFormItems() : [],
        		grid = me.down('grid'),
    			gridItems = grid ? grid.fields : [],
        		lastPoint = formItems.length - 1,
        		fieldNames = [];
        	
        	// 定义指向
        	for (var i = formItems.length -1; i >= 0; i--) {
        		if (!formItems[i].to && i != formItems.length - 1) {
        			formItems[i].to = formItems[lastPoint].name;
        			lastPoint = i;
        		}
        		if (!fieldNames.contains(formItems[i].name)) {
        			fieldNames.push(formItems[i].name);
        		}
        	}
        	
        	if (gridItems) {
	        	// 定义指向
	        	for (var i = gridItems.length -1; i >= 0; i--) {
	        		if (!fieldNames.contains(gridItems[i].name)) {
	        			fieldNames.push(gridItems[i].name);
	        		}
	        	}
        	}
        };
        
        // 填充数据,append是重新覆盖，还是填充
        this.pageDataFill = function(data, append) {
            if (me.down('button[action=submit][text=修改]') && data.StaffID) {
                me.down('button[action=submit][text=修改]').StaffID
            }
            var form = me.getXTypes().indexOf("form") > -1 ? me : me.down("form"),
                grids = me.getXTypes().indexOf("grid") > -1 ? me : Ext.ComponentQuery.query("grid", me),
                tab = me.getXTypes().indexOf("tabpanel") > -1 ? me : me.down('mytab');

            form.DataFill(data);

            if (tab) {
                for (var i = 0; i < tab.items.length; i++) {
                    if (!tab.items.items[i].rendered) {
                        tab.setActiveTab(i);
                    }
                }

                tab.setActiveItem(0);
            }

            if (grids) {
                for (var i = 0; i < grids.length; i++) {
                    grids[i].DataFill(data[grids[i].ref] || data['detail'], append);
                }
            }
        };

        this.buildKeyMapFast = function() {
            var tbs = this.getDockedItems('toolbar[dock="top"]'),
                bbs = this.getDockedItems('toolbar[dock="bottom"]'),
                form = this.down("form"),
                btn;
            if (tbs && tbs.length > 0) {
                var buttons = tbs[0].items.items;
                for (var i = 0; i < buttons.length; i++) {
                    btn = null;
                    if (buttons[i].key) {
                        btn = buttons[i];
                        this.addKeyMapFast(btn);
                    }
                }
            }
            if (bbs && bbs.length > 0) {
                var buttons = bbs[0].items.items;
                for (var i = 0; i < buttons.length; i++) {
                    btn = null;
                    if (buttons[i].key) {
                        btn = buttons[i];
                        this.addKeyMapFast(btn);
                    }
                }
            }
            if (form) {
                var items = form.getFormItems();
                for (var i = 0; i < items.length; i++) {
                    field = null;
                    if (items[i].key) {
                        field = items[i];
                        this.addKeyMapFast(field);
                    }
                    if (items[i].isFocus) {
                        field = items[i];
                        field.focus(true, 1000);
                    }
                }
            }
        };

        this.addKeyMapFast = function(field, key) {
            var keyMap = this.getKeyMap();
            if (Utils.getKeyNum(field.key) == 13 || Utils.getKeyNum(field.key) == 106 || Utils.getKeyNum(field.key) == 111) {
                keyMap.addBinding({
                    key: Utils.getKeyNum(field.key),
                    alt: false,
                    fn: function() {
                        if (field.getXTypes().indexOf("button") > -1) {
                            if (!field.disabled) field.fireEvent("click", field);
                        }
                    },
                    scope: this
                });
            } else {
                keyMap.addBinding({
                    key: Utils.getKeyNum(field.key),
                    alt: true,
                    fn: function() {
                        if (field.getXTypes().indexOf("button") > -1) {
                            if (!field.disabled) field.fireEvent("click", field);
                        }
                        if (field.getXTypes().indexOf("textfield") > -1) {
                            field.focus(true);
                        }
                    },
                    scope: this
                });
            }
        };
        
        this.pageReset = function() {
            var grids = this.getXTypes().indexOf("grid") > -1 ? [this] : Ext.ComponentQuery.query("grid", this),
                form = this.getXTypes().indexOf("form") > -1 ? this : this.down("form"),
                buttons = Ext.ComponentQuery.query("button[action=submit]", this);
            if (grids && grids.length > 0) {
                for (var i = 0; i < grids.length; i++) {
                    grids[i].reset();
                }
            }
            if (form) {
                form.reset();
            }

            for (var i = 0; i < buttons.length; i++) {
                if (buttons[i].disabled) {
                    buttons[i].setDisabled(false);
                }
            }
            this.task = {};
        };
        
        this.on("afterrender", function(me) {
            if (this.repeat) {
                this.doRepeat();
            }
            
            if (me.getXTypes().indexOf("window") > -1 || Utils.isEmpty(me.ownerCt) || me.ownerCt.id == "adminMainFrame" || me.ownerCt.id == "mainFrame") {
            	me.pageInit();
            }

            this.buildKeyMapFast();
        });
        
        this.callParent(arguments);
    }
});

Ext.override(Ext.toolbar.Toolbar, {
    initComponent: function() {
        var me = this;
        if (me.dock == 'top') {
            me.style = "margin-top:5px";
        }
        me.callParent();
    }
});

/**
 * 重写文件浏览器
 */
Ext.override(Ext.form.field.File, {
    // 针对form中的基本组件
    initComponent: function() {
        this.buttonText = "浏览";
        this.callParent(arguments);
    }
});

Ext.override(Ext.tab.Panel, {
    getActiveIndex: function() {
        var me = this,
            index = me.items.indexOf(me.getActiveTab());
        return index + 1;
    }
});

/**
 * 重写按纽
 */
Ext.override(Ext.button.Button, {
    // 针对form中的基本组件
    initComponent: function() {
        var me = this;
        /*
		 * if (Ext.fly('model').getValue().indexOf(me.model) == -1 && me.model) {
		 * me.hide(); }
		 */
        
        if (me.autoClick) {
        	me.on('afterrender', function(btn) {
        		btn.fireEvent('click', btn);
        	});
        }
        
        if (me.top('pagingtoolbar')) 
        
        if (Ext.ClassManager.getName(me) == 'Ext.tab.Tab') {
            // 改变样式
            this.cls = 'tab-pane fade';
            me.autoEl = {
                tag: 'div',
                html: ''
            };

            this.renderTpl = me.text;
        } else if (Utils.isEmpty(me.itemId) && Utils.isEmpty(me.up('datefield')) && Utils.isEmpty(me.up('filefield'))) {
            me.role = "button";
            me.scale = 'small';
            me.cls = 'btn btn-default btn-xs';
            me.style = me.style || "margin-left: 5px;margin-right: 5px;line-height:" + me.inputHeight / 2 + "px";
            // 通用版的button
            if (this.action) {
                var action = this.action;

                if (action === 'close') {
                    this.text = this.text || '关闭';
                    this.iconCls = 'iconnz icon-remove';
                    this.key = "F4";
                    Ext.apply(this, {
                        listeners: {
                            click: function(btn) {
                                btn.top().close();
                            }
                        }
                    });
                } else if (action === 'submit') {
                    this.text = this.text || '保存';
                    this.iconCls = this.iconCls || 'iconnz icon-save';
                    this.cls = 'btn btn-info btn-xs';
                    this.key = "F10";
                    this.follow = this.follow || "saveAndNew";
                    this.active = this.active || 'new&update';
                    if (Utils.isEmpty(this.events) || Utils.isEmpty(this.events.click)) {
                        Ext.apply(this, {
                            listeners: {
                                click: function(btn) {
                                    var flg = true;
                                    if (!Utils.isEmpty(btn.beforeHandler)) {
                                        flg = btn.beforeHandler(btn);
                                    }
                                    if (!flg) {
                                        return;
                                    }
                                    if (Utils.isEmpty(btn.submitHandler)) {
                                        if (btn.confirm) {
                                            Ext.MessageBox.confirm("提示", btn.confirm || "是否确认此操作", function(btn) {
                                                if (btn == "yes") {
                                                    SqlUtils.doUpdate(btn, btn.vars, btn.afterHandler);
                                                }
                                            });
                                        } else {
                                            SqlUtils.doUpdate(btn, btn.vars, btn.afterHandler);
                                        }
                                    } else {
                                        if (btn.confirm) {
                                            Ext.MessageBox.confirm("提示", btn.confirm || "是否确认此操作", function(btn) {
                                                if (btn == "yes") {
                                                    btn.submitHandler(btn);
                                                }
                                            });
                                        } else {
                                            btn.submitHandler(btn);
                                        }
                                    }
                                }
                            }
                        });
                    }
                } else if (action == 'link') {
                    this.text = this.text || '跳转';
                    this.iconCls = this.iconCls || 'iconnz icon-add';
                    Ext.apply(this, {
                        listeners: {
                            click: function(btn) {
                            	if (btn.win) {
                            		return;
                            	}
                                var link = btn.link,
                                	top = btn.top(),
                                	grid = top.down('grid'),
                                	record = grid ? grid.getSingleSelect() : [];
                                if (btn.text.indexOf('修改') > -1 && record == null) {
                    				Ext.Msg.toast('请选择后进行修改', 'error');
                    				return;
                                }
                             	if (!btn.linkName) {
                             		var flg = true;
                                    if (!Utils.isEmpty(btn.beforeHandler)) {
                                        flg = btn.beforeHandler(btn);
                                    }
                                    if (!flg) {
                                        return;
                                    }
                                    
                             		item = Ext.create(link, {
                                        refer: btn.top(),
                                        style: 'border-width: 1px;border-style: solid;border-color: rgb(207, 207, 207);border-image: initial;background: rgb(255, 255, 255);',
                                        record: record
                                 	});
                             		
                             		btn.win = item;
                             		
                             		(function(field) {
                             			item.on('beforedestroy', function(){
                             				btn.win = null;
                    		            });
                    	            })(this);
                             		
                             		item.show();
                             	} else {
                             		var flg = true;
                                    if (!Utils.isEmpty(btn.beforeHandler)) {
                                        flg = btn.beforeHandler(btn);
                                    }
                                    if (!flg) {
                                        return;
                                    }
                             		var lis = $(".menuTab"),
	                    	    		link = btn.link, 
	                    	    		exists = false,
	                    	    		id;
	                    	    	for (var i = 0; i < lis.length; i++) {
	                    	    		if ($(lis[i]).attr('className') == link) {
	                    	    			var currentId = $(lis[i]).data('id');
	                    	    			id = $(lis[i]).attr('el-id');
	                    	                $('.mainContent .LRADMS_iframe').each(function () {
	                    	                    if ($(this).data('id') == currentId) {
	                    	                        $(this).show().siblings('.LRADMS_iframe').hide();
	                    	                        return false;
	                    	                    }
	                    	                });
	                    	                $(lis[i]).addClass('active').siblings('.menuTab').removeClass('active');
	                    	                
	                    	                var items = $("#content-main > div");
	                    	                for (var i = 0; i < items.length; i++) {
	                    	                	$(items[i]).hide();
	                    	                }
	                    	                
	                    	                if ($(this).data('id') == -1) {
	                    	                	$('.mainContent .LRADMS_iframe').show();
	                    	                } else {
	                    	                	$('.mainContent .LRADMS_iframe').hide();
	                    	                	$("#"+id).show();
	                    	                }
	                    	                
	                    	                $.learuntab.scrollToTab($(lis[i]));
	                    	
	                    	    			exists=true;
	                    	    			break;
	                    	    		}
	                    	    	}
	                    	    	
	                    	    	if (!exists) {
	                    	            var items = $("#content-main > div");
	                    	            for (var i = 0; i < items.length; i++) {
	                    	                $(items[i]).hide();
	                    	            }
	                    	
	                    	            item = Ext.create(link, {
	                    	                // 代办任务列表的情况下，包含流程信息
	                    	                height: $(".mainContent").css('height'),
	                    	                renderTo: 'content-main',
	                    	                record: record,
	                    	                style: 'border-width: 1px;border-style: solid;border-color: rgb(207, 207, 207);border-image: initial;background: rgb(255, 255, 255);'
	                    	            });
	                    	
	                    	            var str = '<a href="javascript:;" class="active menuTab" el-id="' + item.id + '"data-id="' + $('.page-tabs-content').children().length + '">' + btn.linkName + ' <i class="icon icon-remove"></i></a>';
	                    	            $('.menuTab').removeClass('active');
	                    	            $('.mainContent').find('iframe.LRADMS_iframe').hide();
	                    	            $('.mainContent iframe:visible').load(function() {});
	                    	            $('.menuTabs .page-tabs-content').append(str);
	                    	            $.learuntab.scrollToTab($('.menuTab.active'));
	                    	            // item.pageDataFill(record.data);
	                             	}
	                             	if (item.getXTypes().indexOf('window') > -1) {
	                             		item.down('button[action=submit]').follow = 'reflush';
	                             	}
	                            }
                             	if (item.getXTypes().indexOf('window') > -1) {
                             		item.down('button[action=submit]').follow='reflush';
                             	}
                            }
                        }
                    });
                } else if (action === 'query') {
                    this.text = this.text || '';
                    this.key = "F5";
                    this.iconCls = 'iconnz icon-flash';
                    if (Utils.isEmpty(this.events) || Utils.isEmpty(this.events.click)) {
                        Ext.apply(this, {
                            listeners: {
                                click: function(btn) {
                                	let flg = true;
                                	
                                	if (!Utils.isEmpty(btn.beforeHandler)) {
                                		flg = btn.beforeHandler(btn);
                                    }
                                	if (!flg) {
                                		return;
                                	}
                                	
                                    // TAB切换
                                    var toptip = btn.top(),
                                        form = toptip.getXTypes().indexOf('form') > -1 ? toptip : toptip.down('form'),
                                        grid = Ext.getCmp(btn.to) || toptip.down('grid'),
                                        tab = btn.to ? null : toptip.down('tabpanel'),
                                        depends = typeof me.depend == 'string' ? [me.depend] : (me.depend || []),
                                        len = depends.length,
                                        items = Utils.isEmpty(form) ? null : form.getFormItems(),
                                        data = me.vars || {};
                                    if (form) {
                                        for (var i = 0; i < len; i++) {
                                            field = form.down('*[name=' + depends[i] + ']');

                                            if (field.getValue() == '') {
                                                Ext.MessageBox.alert("提示", field.fieldLabel + '不能为空', function(ok) {
                                                    if (ok == "ok") {
                                                        field.focus(false);
                                                        return;
                                                    }
                                                });
                                                return;
                                            }
                                        }
                                        data = Utils.mergeSystemParam(Utils.applyIf(form.getFormData('query'), data));
                                    }

                                    var data = data || {};
                                    data.xtype = this.plan || toptip.xtype;
                                    data.url = this.uri || '/query';
                                    if (tab) {
                                        var index = tab.getActiveIndex();
                                        data.xtype = data.xtype + (index > 0 ? ('-' + index) : '');
                                        var atpanel = tab.getActiveTab();
                                        if (atpanel.xtype == 'grid') {
                                            atpanel.doQuery(data, tab);
                                        } else if (atpanel.down('grid')) {
                                            atpanel.down('grid').doQuery(data);
                                        }
                                    } else {
                                        data.xtype = grid.url || data.xtype;
                                        grid.doQuery(data, null, true);
                                    }
                                }
                            }
                        });
                    }
                } else if (action === 'extra') {
                    this.text = this.text || '提取';
                    this.key = "F8";
                    this.iconCls = 'iconnz icon-extra';
                    this.startEdit = this.startEdit || false;

                    if (Utils.isEmpty(this.events) || Utils.isEmpty(this.events.click)) {
                        Ext.apply(this, {
                            listeners: {
                                click: function(btn, e) {
                                    // 如果存在依赖项,则判断
                                    var toptip = btn.top(),
                                        depends = typeof btn.depend == 'string' ? [btn.depend] : (btn.depend || []),
                                        form = btn.top().down('form'),
                                        formData = form ? form.getFormData('query') : {},
                                        len = depends.length,
                                        searchs = typeof btn.search == 'string' ? [me.search] : me.search,
                                        field, data;
                                    //
                                    if (form) {
                                        for (var i = 0; i < len; i++) {
                                            field = form.down('*[name=' + depends[i] + ']');

                                            if (field.getValue() == '') {
                                                Ext.MessageBox.alert("提示", field.fieldLabel + '不能为空', function(ok) {
                                                    if (ok == "ok") {
                                                        field.focus(false);
                                                        return;
                                                    }
                                                });
                                                return;
                                            }
                                        }
                                    }

                                    data = Utils.mergeSystemParam(formData);

                                    var win = Ext.create(searchs[0], {
                                            refer: btn.top()
                                        }),
                                        grid = win.down('grid');

                                    grid.store.proxy.url = grid.action || '/extra';
                                    data['xtype'] = win.xtype;
                                    grid.doQuery(data);

                                    win.show();

                                    if (searchs.length > 1) {
                                        grid.on('itemdblclick', function(view, record) {
                                            if (!Utils.isEmpty(searchs[1])) {
                                                var mxWin = win.detail(searchs[1]),
                                                    mxGrid = mxWin.down('grid');
                                                mxWin.show();
                                                mxGrid.store.proxy.url = '/extra';
                                                data = record.data;
                                                data['xtype'] = mxWin.xtype;
                                                mxGrid.doQuery(record.data);
                                                mxWin.down('button[text^=确定]').on('click', function() {
                                                    var records = mxGrid.getSelect();
                                                    record.set('detail', records);
                                                    toptip.pageDataFill(record.data, me.append);
                                                    mxWin.closeAll();
                                                    if (me.startEdit)
                                                        e.keyCode = e.ENTER;
                                                    me.top().down('grid').startEditor(e);
                                                });
                                            }
                                        });
                                    } else {
                                        win.down('button[text^=确定]').on('click', function(btn) {
                                            var records = grid.getSelect();
                                            if (records.length == 1) {
                                                var record = records[0],
                                                    data = {};
                                                Utils.apply(data, record.data);
                                                Utils.apply(data, {
                                                    detail: [record]
                                                });

                                                toptip.pageDataFill(data, btn.append);

                                                if (me.startEdit) {
                                                    e.keyCode = e.ENTER;
                                                	me.top().down('grid').startEditor(e);
                                                }
                                            } else {
                                                toptip.pageDataFill({
                                                    detail: records
                                                }, btn.append);

                                            }
                                            win.closeAll();
                                        });
                                    }
                                }
                            }
                        });
                    }
                } else if (action === 'delete') {
                    this.text = this.text || '删除';
                    this.key = this.key || "DELETE";
                    //this.cls = 'btn btn-info btn-xs';
                    //this.iconCls = this.iconCls || 'icon-quxiao';
                    this.active = 'delete';
                    if (Utils.isEmpty(this.events) || Utils.isEmpty(this.events.click)) {
                        Ext.apply(this, {
                            listeners: {
                                click: function(btn) {
                                    if (!Utils.isEmpty(btn.beforeHandler)) {
                                        btn.beforeHandler(btn);
                                    }
                                    if (Utils.isEmpty(btn.handler)) {
                                        SqlUtils.doUpdate(btn, btn.vars, btn.afterHandler);
                                    } else {
                                        btn.handler(btn);
                                    }
                                }
                            }
                        });
                    }
                } else if (action === 'edit') {
                    this.text = '保存修改';
                    // this.formBind = true;
                    this.iconCls = 'iconnz icon-edit';
                    this.active = 'all';
                    this.uri = "doUpdate.do";
                    if (Utils.isEmpty(this.events) || Utils.isEmpty(this.events.click)) {
                        Ext.apply(this, {
                            listeners: {
                                click: function(btn) {
                                    if (Utils.isEmpty(btn.handler)) {
                                        SqlUtils.doUpdate(btn);
                                    } else {
                                        btn.handler(btn);
                                    }
                                }
                            }
                        });
                    }
                } else if (action === 'reset') {
                    this.text = '重置';
                    this.iconCls = 'iconnz icon-reset';
                    if (Utils.isEmpty(this.events) || Utils.isEmpty(this.events.click)) {
                        Ext.apply(this, {
                            listeners: {
                                click: function(btn) {
                                    var toptip = Utils.loadParentWindow(btn);
                                    toptip.pageReset();
                                }
                            }
                        });
                    }
                } else if (action === 'close') {
                    this.text = '关闭';
                    this.iconCls = 'iconnz icon-remove';
                    if (Utils.isEmpty(this.events) || Utils.isEmpty(this.events.click)) {
                        Ext.apply(this, {
                            listeners: {
                                click: function(btn) {
                                    btn.top().close();
                                }
                            }
                        });
                    }
                } else if (action === 'insert') {
                    this.text = '新增';
                    // this.iconCls = 'iconnz icon-add';
                    this.cls = 'btn btn-info btn-xs';
                    if (Utils.isEmpty(this.events) || Utils.isEmpty(this.events.click)) {
                        if (Utils.isEmpty(this.events) || Utils.isEmpty(this.events.click)) {
                            Ext.apply(this, {
                                listeners: {
                                    click: function(btn) {
                                        btn.top().down('grid').doInsert();
                                    }
                                }
                            });
                        }
                    }
                } else if (action === 'print') {
                    this.text = this.text || '打印';
                    this.iconCls = 'iconnz icon-print';
                    if (Utils.isEmpty(this.events) || Utils.isEmpty(this.events.click)) {
                        Ext.apply(this, {
                            listeners: {
                                click: function(btn) {
                                    btn.uri = '/print';
                                    SqlUtils.doUpdate(btn, btn.vars);
                                    btn.disabled=false;
                                }
                            }
                        });
                    }
                } else if (action === 'temp') {
                    // 模板
                    this.iconCls = 'iconnz icon-excel';
                    this.text = this.text || '模板';
                    Ext.apply(this, {
                        listeners: {
                            click: function(btn) {
                                var top = btn.top(),
                                    pop = Ext.create('Ext.window.Window', {
                                        title: '模板管理器',
                                        width: 230,
                                        height: 200,
                                        onUp: function(me) {
                                            Ext.getCmp("temp_new").focus(false);
                                        },
                                        onDown: function(me) {
                                            Ext.getCmp("temp_choose").focus(false);
                                        },
                                        layout: "form",
                                        items: [{
                                            xtype: "button",
                                            text: "创建/修改模板",
                                            cls: 'btn btn-default .btn-lg',
                                            ref: "new",
                                            width: 200,
                                            height: 70,
                                            id: "temp_new",
                                            listeners: {
                                                click: function(me) {
                                                    Ext.create('core.append.ExcelDesign', {
                                                        refer: top
                                                    }).show();
                                                }
                                            }
                                        }, {
                                            xtype: "button",
                                            text: "导入模板",
                                            cls: 'btn btn-default .btn-lg',
                                            margin: '5 0 0 0',
                                            ref: "choose",
                                            width: 200,
                                            height: 70,
                                            id: "temp_choose",
                                            listeners: {
                                                click: function(me) {
                                                    Ext.create('core.append.ExcelImport', {
                                                        refer: top,
                                                        win: me.top(),
                                                        btn: btn,
                                                        append: btn.append
                                                    }).show();
                                                }
                                            }
                                        }]
                                    }).show();

                                pop.down("button[ref=new]").focus(false, 500);
                                var keyMap = pop.getKeyMap();
                                keyMap.on(Ext.event.Event.DOWN, pop.onDown, pop);
                                keyMap.on(Ext.event.Event.UP, pop.onUp, pop);
                            }
                        }
                    });
                }
            }

            me.renderTpl = '<span id="{id}-btnIconEl" role="presentation" class="{iconCls}"></span>' + this.text;

            me.autoEl = {
                tag: 'a'
            };

            if (this.key) this.text = this.text + "(" + this.key + ")";
            if (this.rclick) this.setHidden(true);
        }

        this.callParent(arguments);
    }
});

/**
 * 重日期函数
 */
Ext.override(Ext.form.field.Date, {
    initComponent: function() {
        this.submitFormat = "Y-m-d";
        this.format = "Y-m-d";
        this.callParent(arguments);
    }
});

// 重写Store事件
Ext.override(Ext.data.Store, {
    getSum: function(field) {
        if (Utils.isEmpty(field)) {
            return;
        }

        var records = this.getRange(),
            result = 0;

        for (var i = 0; i < records.length; i++) {
            result = result.add(records[i].get(field) || 0);
        }

        return result;
    }
});

/**
 * 默认选中不是rownumberer的列
 */
Ext.override(Ext.selection.CellModel, {
    // 针对form中的基本组件
    select: function(pos, keepExisting, suppressEvent) {
        var me = this,
            row, oldPos = me.getPosition(),
            store = me.view.store;
        if (pos || pos === 0) {
            if (pos.isModel) {
                row = store.indexOf(pos);
                if (row !== -1) {
                    pos = {
                        row: row,
                        column: oldPos ? oldPos.column : 0
                    };
                } else {
                    pos = null;
                }
            } else if (typeof pos === "number") {
                pos = {
                    row: pos,
                    column: 1
                };
            }
        }
        if (pos) {
            me.selectByPosition(pos, suppressEvent);
        } else {
            me.deselect();
        }
    }
});

// 针对form中的基本组件
Ext.override(Ext.grid.NavigationModel, {
    move: function(dir, keyEvent) {
        var me = this,
            position = me.getPosition();
        if (position && position.record) {
            position = position.view.walkCells(position, dir, null, me.preventWrap);
            if (position && (position.column.xtype == "rownumberer" || position.column.isHidden())) {
                position = position.view.walkCells(position, dir, null, me.preventWrap);
            }
            return position;
        }
        return null;
    }
});

/*
 * 重写BoundListKeyNav 响应回车事件选择后，执行view的next事件
 */
Ext.override(Ext.view.BoundListKeyNav, {
    onKeyEnter: function(e) {
        var view = this.view,
            selModel = view.getSelectionModel(),
            field = view.pickerField,
            count = selModel.getCount();

        // Stop the keydown event so that an ENTER keyup does not get delivered
        // to
        // any element which focus is transferred to in a select handler.
        e.stopEvent();
        this.selectHighlighted(e);

        // Handle the case where the highlighted item is already selected
        // In this case, the change event won't fire, so just collapse
        if (!field.multiSelect && count === selModel.getCount() && field.collapse) {
            field.collapse();
        }

        field.next(e);
    }
});

Ext.override(Ext.container.Container, {
    /**
	 * Reconfigures the initially configured {@link #layout}.
	 * 
	 * NOTE: this method cannot be used to change the "type" of layout after the
	 * component has been rendered to the DOM. After rendering, this method can
	 * only modify the existing layout's configuration properties. The reason
	 * for this restriction is that many container layouts insert special
	 * wrapping elements into the dom, and the framework does not currently
	 * support dynamically changing these elements once rendered.
	 * 
	 * @param {Object}
	 *            configuration object for the layout
	 */
    setLayout: function(layout) {
        var me = this,
            currentLayout = me.layout,
            currentIsLayout = currentLayout && currentLayout.isLayout,
            protoLayout, type;

        if (typeof layout === 'string') {
            layout = {
                type: layout
            };
        }

        type = layout.type;

        if (currentIsLayout && (!type || (type === currentLayout.type))) {
            // no type passed, or same type as existing layout - reconfigure
            // current layout
            delete layout.type;
            currentLayout.setConfig(layout);
        } else {
            // no current layout, or different type passed - create new layout
            if (currentIsLayout) {
                currentLayout.setOwner(null);
            }

            protoLayout = me.self.prototype.layout;

            if (typeof protoLayout === 'string') {
                layout.type = type || protoLayout;
            } else {
                // use protoLayout as default values
                Ext.merge(Ext.merge({}, protoLayout), layout);
            }

            layout = this.layout = Ext.Factory.layout(layout);
            layout.setOwner(this);
        }

        if (me.rendered) {
            me.updateLayout();
        }
    }
});

Ext.override(Ext.panel.Bar, {
    initComponent: function() {
        var me = this,
            vertical = me.vertical;

        me.dock = me.dock || (vertical ? 'left' : 'top');

        me.layout = Ext.apply(vertical ? {
            type: 'vbox',
            align: 'middle',
            alignRoundingMethod: 'ceil'
        } : {
            type: 'hbox',
            align: 'middle',
            alignRoundingMethod: 'floor'
        }, me.layout);

        this.callParent();
    }
});

// 默认text获取焦点时为全选。 如果isFocus为true，则1秒后自动获取焦点
Ext.override(Ext.form.field.Text, {
    // 是否可以覆盖
    override: true,
    
    setSearch: function(search) {
    	var me = this;
    	this.search = search;
    	this.render();
    },
    
    /*
	 * 重写Text,如果首字母是=，表示启动公式
	 */
    onChange: function(newVal, oldVal) {
        this.callParent(arguments);
        this.autoSize();

        if (newVal.toString().getByteLength() > this.maxLength) {
        	this.setValue(newVal.substring(0, this.maxLength));
        	return;
        }
        if ((typeof newVal) != 'string') {
            return;
        }

        var grid = this.up('grid');

        if (grid && newVal && newVal.indexOf('=') == 0) {
            grid.plugins[0].context.column.formula = this;
            grid.formula = this;
        } else if (grid && grid.plugins[0].context && grid.plugins[0].context.column.formula && this.id === grid.plugins[0].context.column.formula.id) {
            grid.clearFormula(this, true);
        }
    },

    // 针对form中的基本组件
    afterRender: function() {
        var me = this;
        me.autoSize();
        me.callParent();
        me.invokeTriggers("afterFieldRender");
        me.inputEl.on("dblclick",
            function(e) {
                if (!Utils.isEmpty(me.up("editor"))) {
                    me.up("editor").completeEdit(false);
                } else {
                    me.eventTrigger(e);
                }
            });
    },
    initComponent: function() {
        var me = this;
        this.labelAlign = "right";
        if (me.isFocus) me.focus(true, 600);
        if (me.zjm) {
            me.on("blur", function(me) {
                comp = this.top().down('*[name=' + me.zjm + ']') || Ext.getCmp(me.to);
                comp.setValue(pinyin.getCamelChars(me.getValue()));
            });
        }

        // 加入过滤事件
        if (me.verify) {
            me.on('blur', function(field) {
                if (Ext.String.trim(field.getValue()) == '') {
                    return;
                }
                var param = {
                    xtype: 'verify' + '_' + field.top().xtype + '_' + me.name
                };
                param[me.name] = me.getValue();

                Utils.ajax('/verify', param, function(callback) {
                    if (callback.msg != '') {
                        Ext.Msg.toast(callback.msg, 'error');
                        me.setValue('');
                    }
                });
            });
        };
        
        // 使用公式
        if (me.formula) {
        	this.setTriggers({
                clear: {
                    weight: 0,
                    cls: Ext.baseCSSPrefix + 'form-clear-trigger',
                    handler: 'onClearClick',
                    scope: 'this'
                },
                expression: {
                    weight: 1,
                    cls: Ext.baseCSSPrefix + 'form-expression-trigger',
                    handler: 'execute',
                    scope: 'this'
                }
            });
        	
        	Ext.apply(me, {
                execute: function(field, e) {
                	if (field.up('grid')) {
                		Ext.create('core.append.FormulaMaker', {refer: {grid: field.up('grid')}}).show();
                	} else {
                		Ext.create('core.append.FormulaMaker', {refer: {field: me}}).show();
                	}
                    return false;
                }
            });
        	
        }
        
        // 弹出检索框
        if (me.search) {
        	this.allowEmpty = true;
            this.setTriggers({
                clear: {
                    weight: 0,
                    cls: Ext.baseCSSPrefix + 'form-clear-trigger',
                    handler: 'onClearClick',
                    scope: 'this'
                },
                search: {
                    weight: 1,
                    cls: Ext.baseCSSPrefix + 'form-search-trigger',
                    handler: 'execute',
                    scope: 'this'
                }
            });

            Ext.apply(me, {
                execute: function(field, e) {
                    field.FieldSearch(field, me.search, e);
                    return false;
                }
            });
        };

        // 弹出检索框
        if (me.xtype === 'textfield' && me.enname) {
            Ext.apply(me, {
                execute: function(field, e) {
                    field.FieldSearch(field, 'core.coreApp.common.search.main.doc.ZlzdSearch', e);
                    return false;
                }
            });
        };

        this.setReadOnly = function(readOnly) {
            var me = this,
                triggers = me.getTriggers(),
                hideTriggers = me.getHideTrigger(),
                trigger,
                id;

            readOnly = !!readOnly;

            if (me.rendered) {
                me.setReadOnlyAttr(readOnly || !me.editable);

                if (triggers) {
                    for (id in triggers) {
                        trigger = triggers[id];
                    }
                }
            }
        };

        /**
		 * 清理所有影响到的数据
		 * 
		 * @param field
		 */
        this.onClearClick = function(field) {
            var form = this.top().getXTypes().indexOf("form") > -1 ? this.top() : this.top().down('form'),
                refers = field.refers,
                len = refers ? refers.length : 0,
                item;
            for (var i = 0; i < len; i++) {
                item = form.down('*[name=' + refers[i] + ']');
                item.setValue('');
                item.setReadOnly(false);
            } 
        	this.setValue('');
        };

        // 检索
        this.FieldSearch = function(field, text, e) {
        	if (field.getValue() == '' && !field.allowEmpty) {
        		return;
        	}
            var form = this.up('form'),
                depends = typeof field.depend == 'string' ? [field.depend] : (field.depend || []),
                len = depends.length,
                formData = Utils.mergeSystemParam(form ? form.getFormData('query', 'search') : {});

            if (form) {
                for (let i = 0; i < len; i++) {
                    field = form.down('*[name=' + depends[i] + ']');
                    if (field.getValue() == '') {
                        Ext.MessageBox.toast(field.fieldLabel + '不能为空', 'error');
                        field.focus(false);
                    }
                }
            }

            // 重复点击的问题
            if (field.win) {
            	if (field.hidden)
            		field.win.show();
            } else {
	            let win = Ext.create(text, {
	                    refer: this,
	                    modal: true,
	                    enname: this.enname
	                }),
	                grid = win.down('grid'),
                    queryBtn = win.down('button[action=query]');
                win.pageDataFill(formData);
                queryBtn.fireEvent('click', queryBtn);
	            field.win = win;

	            (function(field) {
		            win.on('beforedestroy', function(){
		            	field.win = null;
		            });
	            })(this);
	            if (this.enname)
	                formData['enname'] = this.enname;
	            win.show();
	            
	            win.on('beforedestroy', function(){
	            	field.win = null;
	            });

                (function (field) {
                    if (win.down('grid').selType == 'checkboxmodel') {
                        win.down('button[text^=确定]').on('click', function (btn) {
                            let records = grid.getSelect(),
                                record;
                            if (records.length > 1) {
                                return;
                            }
                            record = records[0];

                            if (record.get('bgcolor') != '' && grid[record.get('bgcolor') + 'Text']) {
                                var msg = grid[record.get('bgcolor') + 'Text'];
                                for (var prop in record.data) {
                                    msg = msg.replace('{' + prop + '}', record.get(prop));
                                }
                                if (grid[record.get('bgcolor')] + 'Pass') {
                                    Ext.MessageBox.toast(msg, 'error');
                                    for (var item in record.data) {
                                        if (repeatMappingTo && repeatMappingTo[item]) {
                                            record.data[repeatMappingTo[item]] = record.data[item];
                                            delete record.data[item];
                                        }
                                    }
                                    form.DataFill(record.data, field);
                                    win.close();
                                    field.next(e);
                                    field.getTrigger("clear").show();
                                } else {
                                    Ext.MessageBox.alert("错误", msg, function (ok) {
                                        if (ok == "ok") {
                                            if (!grid[record.get('bgcolor') + 'Pass']) {
                                                return;
                                            } else {
                                                for (var item in record.data) {
                                                    if (repeatMappingTo && repeatMappingTo[item]) {
                                                        record.data[repeatMappingTo[item]] = record.data[item];
                                                        delete record.data[item];
                                                    }
                                                }
                                                form.DataFill(record.data, field);
                                                win.close();
                                                field.next(e);
                                                field.getTrigger("clear").show();
                                            }
                                        }
                                    });

                                    return;
                                }
                            } else {
                                for (var item in record.data) {
                                    if (repeatMappingTo && repeatMappingTo[item]) {
                                        record.data[repeatMappingTo[item]] = record.data[item];
                                        delete record.data[item];
                                    }
                                }
                                form.DataFill(record.data, field);
                                win.close();
                                field.next(e);
                                field.getTrigger("clear").show();
                            }
                            return;
                        });

                        win.down('button[text^=确定]').handlerAop = function (me) {
                            var grid = me.top().down('grid'),
                                records = grid.getSelect(),
                                flg = true,
                                record;
                            for (var i = 0; i < records.length; i++) {
                                record = records[i];
                                if (record.get('bgcolor') != '' && grid[record.get('bgcolor') + 'Text']) {
                                    var msg = grid[record.get('bgcolor') + 'Text'];
                                    for (var prop in record.data) {
                                        msg = msg.replace('{' + prop + '}', record.get(prop));
                                    }
                                    Ext.MessageBox.toast(msg, 'error');
                                    if (!grid[record.get('bgcolor') + 'Pass']) {
                                        flg = false;
                                        break;
                                    }
                                }
                            }
                            return flg;
                        }
                    } else {
                        grid.on('itemdblclick', function (view, record) {
                            if (record.get('bgcolor') != '' && grid[record.get('bgcolor') + 'Text']) {
                                var msg = grid[record.get('bgcolor') + 'Text'];
                                for (var prop in record.data) {
                                    msg = msg.replace('{' + prop + '}', record.get(prop));
                                }
                                if (grid[record.get('bgcolor')] + 'Pass') {
                                    Ext.MessageBox.toast(msg, 'error');
                                    /*for (var item in record.data) {
                                        if (repeatMappingTo && repeatMappingTo[item]) {
                                            record.data[repeatMappingTo[item]] = record.data[item];
                                        }
                                    }*/
                                    form.DataFill(record.data, field);
                                    win.close();
                                    field.next(e);
                                    field.getTrigger("clear").show();
                                } else {
                                    Ext.MessageBox.alert("错误", msg, function (ok) {
                                        if (ok == "ok") {
                                            if (!grid[record.get('bgcolor') + 'Pass']) {
                                                return;
                                            } else {
                                                form.DataFill(record.data, field);
                                                win.close();
                                                field.next(e);
                                                field.getTrigger("clear").show();
                                            }
                                        }
                                    });

                                    return;
                                }
                            } else {
                                /*for (var item in record.data) {
                                    if (repeatMappingTo && repeatMappingTo[item]) {
                                        record.data[repeatMappingTo[item]] = record.data[item];
                                    }
                                }*/
                                form.DataFill(record.data, field);
                                win.close();
                                field.next(e);
                                field.getTrigger("clear").show();
                            }
                            return;
                        });
                    }


                })(this);
            }
        };

        /*
		 * 获取焦点时,全选
		 */
        this.onFocus = function(e) {
            var me = this;
            me.isfouce = true;
            me.selectText();
            me.addCls(me.fieldFocusCls);
            me.triggerWrap.addCls(me.triggerWrapFocusCls);
            me.inputWrap.addCls(me.inputWrapFocusCls);
        };

        this.eventTrigger = function(e) {
            var execute = this.execute;
            if (Utils.isEmpty(execute) || Utils.isEmpty(execute(this, e))) {
                this.next(e);
            }
        };

        this.next = function(e) {
            var me = this,
                top = me.top(),
                comp = top.down('*[name=' + me.to + ']') || Ext.getCmp(me.to),
                grid;

            if (!Utils.isEmpty(comp)) {
                if (comp.xtype == "searchfield") {
                    comp.fireEvent("search", comp, comp.getValue());
                } else if (comp.xtype == "button") {
                    comp.fireEvent("click", comp);
                } else if (comp.getXTypes().indexOf("combo") > -1) {
                	// comp.expand();comp.doQuery(this.allQuery, true);
                	comp.focus();
                } else {
                    comp.focus();
                }
            }
            if (me.startEdit) {
                var toptip = me.top(),
                    grid = toptip.down("grid"),
                    // 吧FORM的状态全部变成不可编辑
                    form = toptip.down("form"),
                    items = form ? form.getFormItems() : [],
                    depends = typeof me.depend == 'string' ? [me.depend] : (me.depend || []),
                    len = depends.length;
                //
                if (form) {
                    for (var i = 0; i < len; i++) {
                        field = form.down('*[name=' + depends[i] + ']');

                        if (field.getValue() == '') {
                            Ext.MessageBox.alert("提示", field.fieldLabel + '不能为空', function(ok) {
                                if (ok == "ok") {
                                    field.focus(false);
                                    return;
                                }
                            });
                            return;
                        }
                    }
                }
                for (var i = 0; i < items.length; i++) {
                    if (items[i].lock) {
                        items[i].setDisabled(true);
                    }
                }
                grid.startEditor(e);
            }
        };
        this.on("specialkey",
            function(field, e) {
                if (e.getKey() === e.ENTER) {
                    this.eventTrigger(e);
                }
            });

        if (this.frame) {
            this.fieldSubTpl = [ // note: {id} here is really {inputId}, but
                // {cmpId} is available
                '<input id="{id}" data-ref="inputEl" type="{type}" role="{role}" {inputAttrTpl}', ' size="1"', // allows
                // inputs
                // to
                // fully
                // respect
                // CSS
                // widths
                // across
                // all
                // browsers
                '<tpl if="name"> name="{name}"</tpl>', '<tpl if="value"> value="{[Ext.util.Format.htmlEncode(values.value)]}"</tpl>', '<tpl if="placeholder"> placeholder="{placeholder}"</tpl>', '{%if (values.maxLength !== undefined){%} maxlength="{maxLength}"{%}%}', '<tpl if="readOnly"> readonly="readonly"</tpl>', '<tpl if="disabled"> disabled="disabled"</tpl>', '<tpl if="tabIdx != null"> tabindex="{tabIdx}"</tpl>', '<tpl if="fieldStyle"> style="{fieldStyle}"</tpl>', ' class="{fieldCls} {typeCls} {editableCls} {inputCls}" autocomplete="off"/>', {
                    disableFormats: true
                }
            ];
        }

        this.callParent(arguments);
    }
});

// form的间距默认为上5px,右5px
Ext.override(Ext.form.Panel, {
    // 针对form中的基本组件
    initComponent: function() {
    	var me = this;
    	
        //this.bodyStyle = this.bodyStyle || "padding:5px 5px 0";

        // 获取Form的所有元素
        this.getFormItems = function() {
            return this.getForm().getFields().items;
        };
        
        // 获取Form的所有元素
        // 可活动的
        this.getActiveFormItems = function() {
            var items = this.getForm().getFields().items,
            	result = [];
            for (var i = 0; i < items.length; i++) {
            	if (!items[i].readOnly && !items[i].hidden) {
            		result.push(items[i]);
            	}
            }
            return result;
        };
        
        if (me.fields) {
        	var items = [],
        		col = me.col;
        	
        	for (var i = 0 ; i < me.fields.length; i++) {
    			if (i % col == 0) {
    				items[i / col] = {
    					layout: 'column',
    					baseCls: 'x-plain',
    					height: 30,
    					defaults: {
    						xtype: 'textfield'
    					},
    					items: []
    				};
    			}
    			
    			items[parseInt(i / col)].items.push(me.fields[i]);
    		}
    		
    		me.items = items;
        }
        
        if (me.fields_a) {// 自己定义的高度
        	var items = [],
        		col = me.col;
        	
        	for (var i = 0 ; i < me.fields_a.length; i++) {
    			if (i % col == 0) {
    				items[i / col] = {
    					layout: 'column',
    					baseCls: 'x-plain',
    					height: 50,
    					defaults: {
    						xtype: 'textfield'
    					},
    					items: []
    				};
    			}
    			
    			items[parseInt(i / col)].items.push(me.fields_a[i]);
    		}
    		
    		me.items = items;
        }
        
        // 获取From的值
        this.getFormData = function(model, type) {
            var items = this.getFormItems(),
                datas = {},
                queryModel;

            for (var i = 0; i < items.length; i++) {
                if (model && items[i].xtype != 'ueditor') {
                    datas[items[i][type+'Name'] || items[i].name] = Utils.toStr(items[i].getValue()).replaceHtml();
                } else {
                	if (!datas[items[i][type+'Name'] || items[i].name])
                		datas[items[i][type+'Name'] || items[i].name] = Utils.toStr(items[i].getValue()).replaceHtml();
                }
                if (model && model == 'query') {
                    queryModel = items[i].queryModel;
                    if (queryModel == 'like')
                        datas[items[i][type+'Name'] || items[i].name] = '%' + items[i].getValue() + '%';
                    if (queryModel == 'rlike')
                        datas[items[i][type+'Name'] || items[i].name] = items[i].getValue() + '%';
                    if (queryModel == 'llike')
                        datas[items[i][type+'Name'] || items[i].name] = '%' + items[i].getValue();
                }
            }

            return datas;
        };

        // 数据填充
        this.DataFill = function(data, source) {
            var items = this.getFormItems(),
                fillData,
                data = data.isModel ? data.data : data;

            if (source)
                source.refers = [];

            for (var i = 0; i < items.length; i++) {
                fillData = data[items[i].mappingTo || items[i].name];
                if (fillData) {
                    fillData = typeof fillData == 'string' ? Ext.String.trim(fillData) : fillData;
                }
                if (items[i].override && !Utils.isEmpty(fillData)) {
                    items[i].setValue(fillData);
                    if (source) {
                        source.refers.push(items[i].getName());
                    }
                    if (!items[i].keepEdit)
                        items[i].setReadOnly(true);
                }

                fillData = null;
            }
        };

        // 重置
        this.reset = function() {
            var items = this.getFormItems();
            for (var i = 0; i < items.length; i++) {
                if (items[i].override && items[i].xtype != 'datefield') {
                    items[i].setValue(items[i].defaultValue || '');
                    items[i].setReadOnly(false);
                }
                if (items[i].override && items[i].getXTypes().indexOf("combo") > -1) {
                    items[i].setValue(null);
                }
                if (items[i].isFocus) {
                    items[i].focus(true);
                }
                if (items[i].disabled) {
                    items[i].setDisabled(false);
                }
            }
        };

        this.callParent(arguments);
    }
});


/*
 * 默认给grid添加键盘事件 @memberOf {TypeName} @return {TypeName}
 */
Ext.override(Ext.grid.Panel, {
    // 公式组件
    formula: false,
    enableKeyNav: true,
    enableDragDrop: true,
    // 可以使用键盘控制上下
    multiSelect: false,
    // 多选
    columnLines: true,
    autoScroll: true,
    filter: true,
    // 展示竖线
    dupl: true,
    rowLines: true,
    mask: true,
    // 是否可以倒出
    isexport: false,
    // 是否导出，默认为false,为true则增加导出按妞
    isbbar: false,
    // 是否主表
    active: true,
    loadMask: true,
    allowInsert: false,
    // 是否可以CellEditor自动加行
    autoInsert: true,
    // 是否排除
    exclude: false,
    // 是否默认插入1条数据
    autoClear: false,
    // 右键菜单是否重画，如果为true则默认系统自带的不存在
    redraw: false,
    // 默认的数据
    dataDefault: {},
    allowDelete: true,
    // 双击打开
    dblclick: true,
    sortable: true,
    // 循环刷新
    loop: false,
    // 默认清楚数据
    // selType: "cellmodel",

    viewConfig: {
        stripeRows: true,
        forceFit: true,
        enableTextSelection: true,

        // 可复制，粘贴
        getRowClass: function(record, rowIndex, rowParams, store) {
            // 禁用数据显示红色
            if (!Utils.isEmpty(record.get("bgcolor"))) {
                return record.get("bgcolor") + "-row";
            } else {
                return '';
            }
        }
    },

    reconfig: function() {
    	let that = this;
    	that.createStore();
    	that.createColumns();
    	that.reconfigure(that.store, that.columns);
    	that.reset();
    },
    
    mergeRecords: function(records){
    	let me = this,
    		localRecords = me.store.getRange(0),
    		len = localRecords.length,
    		remoteLen = records.length;
    
    	for (var i = 0; i < len; i++) {
    		for (var j = 0; j <remoteLen; j++) {
    			if (localRecords[i].get(me.exception.key) == records[j][me.exception.key]) {
    				for (var item in records[j]) {
    					localRecords[i].set(item, records[j][item]);
    				}
    			}
    		}
    	}
    },
    
    /**
	 * 获取查询的数据，根据<code>queryModel</code>来判断是否需要加%
	 */
    getQueryData: function() {
        var record = this.getSingleSelect().data,
            result = {},
            columns = this.columns;

        for (var i = 0; i < columns.length; i++) {
            if (columns[i].queryModel) {
                queryModel = columns[i].queryModel;
                if (queryModel == 'like')
                    result[columns[i].dataIndex] = '%' + record[columns[i].dataIndex] + '%';
                if (queryModel == 'rlike')
                    result[columns[i].dataIndex] = record[columns[i].dataIndex] + '%';
                if (queryModel == 'llike')
                    result[columns[i].dataIndex] = '%' + record[columns[i].dataIndex];
            } else {
                result[columns[i].dataIndex] = record[columns[i].dataIndex];
            }
        }

        return result;
    },

    
    
    // 插入操作
    // 说明：插入一条新的数据到grid总
    // 参数：model 数据模型
    // line 插入到第几行
    // startEditor 是否立刻开始编辑
    doInsert: function(model, line, startEditor) {
        var me = this,
            stroe = me.store,
            dataDefault = me.dataDefault || {},
            count = stroe.getCount(),
            model = model || Ext.create(me.store.model);

        Utils.apply(model.data, dataDefault);
        model.set("sort", stroe.count());
        stroe.insert(line || stroe.count(), model);
        me.getView().refresh();
        if (startEditor) me.plugins[0].startEditByPosition({
            row: me.store.count() - 1,
            column: 1
        });
    },

    // CELL合并，内容相同才合并
    merge: function(cols) {
        var me = this,
            records = me.store.getRange(0),
            mergeRows = [{
                start: 0,
                end: 0
            }],
            isPass = true;

        // 如果第i行等于第i+1行，则记录开始和结束行数
        for (var i = 0; i < records.length; i++) {
            for (var j = 0; j < cols.length; j++) {
                if (i < records.length - 1 && records[i].get(cols[j]) != records[i + 1].get(cols[j])) {
                    isPass = false;
                    mergeRows.push({
                        start: i + 1,
                        end: i + 1
                    });
                    break;
                }
            }

            mergeRows[mergeRows.length - 1].end = (i == records.length - 1 ? records.length - 1 : i + 1);
        }

        // 开始合并行
        var tables = $("#" + me.id + '-body').find('table'),
            trs,
            sub = 0,
            setMergeTrsIntoFirstRow = function(start, end) {
                for (var i = start + 1; i <= end; i++) {
                    $($(tables[start]).find('tbody')).append($(tables[i]).find('tr'));
                }
                // 合并TD
                $($(tables[start]).find('tr:eq(0) td:eq(0)')).attr('rowspan', end - start + 1);
                $($(tables[start]).find('tr:gt(0)').find('td:eq(0)')).remove();
                $($(tables[start]).find('tr:eq(0) td:eq(0)')).css("vertical-align", "middle");

                if (me.selType == 'checkboxmodel') {
                    // 合并TD
                    $($(tables[start]).find('tr:eq(0) td:eq(1)')).attr('rowspan', end - start + 1);
                    $($(tables[start]).find('tr:eq(0) td:eq(1)')).css("vertical-align", "middle");
                    $($(tables[start]).find('tr:gt(0)').find('td:eq(0)')).remove();
                }

                // 将第一行的所有的TD的rowspan修改成正确的
                for (var i = 0; i < cols.length; i++) {
                    colIdx = me.getIdx(cols[i]);
                    $($(tables[start]).find('tr:eq(0) td:eq(' + colIdx + ')')).attr('rowspan', end - start + 1);
                    $($(tables[start]).find('tr:eq(0) td:eq(' + colIdx + ')')).css("vertical-align", "middle");
                    $($(tables[start]).find('tr:gt(0)').find('td:eq(' + (colIdx - i - (me.selType == 'checkboxmodel' ? 2 : 1)) + ')')).remove();
                }
            };
        for (var i = 0; i < mergeRows.length; i++) {
            trs = [];
            for (var j = mergeRows[i].start; j <= mergeRows[i].end; j++) {
                if (j == mergeRows[i].start) {
                    setMergeTrsIntoFirstRow(mergeRows[i].start, mergeRows[i].end);
                } else {
                    $(tables[j]).remove();
                }
            }
        }
    },

    getIdx: function(dataIndex) {
        var me = this,
            idx = -1;

        for (var i = 0, len = me.fields.length; i < len; i++) {
            if (me.fields[i].name == dataIndex) {
                idx = i;
                break;
            }
        }

        return idx + 1 + (me.selType == 'checkboxmodel' ? 1 : 0);
    },
    
    // 获取所有可以被编辑的列
    getActiveFields: function() {
    	var me = this,
    		fields = me.fields,
    		result = [];
    	
    	for (var i = 0; i < fields.length; i++) {
    		if (fields[i].editor) {
    			result.push(fields[i]);
    		}
    	}
    	
    	return result;
    },
    
    // grid重置
    // 说明：
    reset: function() {
        this.store.removeAll();
        if (this.autoInsert) {
            this.doInsert();
        }
    },

    /**
	 * 获取选中的数据
	 * 
	 * @param validate
	 *            根据Grid的Exception来进行验证数据的合法性
	 * @returns
	 */
    getSelect: function(validate) {
        var records = this.getSelectionModel().getSelection(),
            exception = this.exception,
            len = records.length,
            removeRecord = new Array();

        if (validate && exception && exception.key) {
            for (var i = 0; i < len; i++) {
                if (Utils.isEmpty(records[i].get(exception.key)) || records[i].get(exception.key) == '') {
                    removeRecord.push(records[i]);
                }
            }

            records.removeAll(removeRecord);
        }
        return records;
    },
    
    /**
	 * 获取选中的数据
	 * 
	 * @param validate
	 *            根据Grid的Exception来进行验证数据的合法性
	 * @returns
	 */
    getAllData: function(active, validate, submit) {
        var result = [],
        	records = this.getRecords(active, validate, submit);
            
        for (var i = 0; i < records.length; i++) {
        	result.push(records[i].data);
        }
        
        return result;
    },

    // 获取唯一一条选中的数据
    getSingleSelect: function(validate) {
        var records = this.getSelect(validate);
        if (records.length == 0) {
            return null;
        } else {
            return records[0];
        }
    },

    // 全选
    selectAll: function() {
        this.getSelectionModel().selectAll();
    },

    // 获取GRID的数据
    // 说明： 全系统统一使用该函数获取所有，修改的，新增的
    // 参数: active 活动状态 all、select、update、new、delete
    // 		validate 数据校验
    //		submit 是否获取提交数据
    // validate 验证 主要是针对Key
    getRecords: function(active, validate, submit) {
        var records = new Array(),
            grid = this,
            store = grid.store,
            exception = this.exception,
            actives = Utils.isEmpty(active) ? ['all'] : active.split('&'),
            removeRecord = new Array(),
            fields = this.fields,
            submits = [],
            data = {},
            selected;
        
        for (var i = 0; i < fields.length; i++) {
        	if (submit && fields[i].submitName) {
        		data[fields[i].name] = fields[i].submitName;
         		submits.push(data);
         		data = {};
        	}
        }
        for (var i = 0; i < actives.length; i++) {
            if (Utils.isEmpty(actives[i]) || actives[i] == "all") {
                records = records.concat(grid.store.getRange(0));
            } else if (actives[i] == "update") {
                records = records.concat(store.getUpdatedRecords());
            } else if (actives[i] == "new") {
                records = records.concat(store.getNewRecords());
            } else if (actives[i] == "select") {
                records = records.concat(grid.getSelect());
            } else if (actives[i] == "delete") {
                selected = grid.getSelect();
                records = records.concat(selected);
            }
        }

        var len = records.length;

        if (validate && exception && exception.key) {
            for (var i = 0; i < len; i++) {
                if (records[i].get(exception.key) === '') {
                    removeRecord.push(records[i]);
                }
            }

            records.removeAll(removeRecord);
        }
        
        if (submit) {
	        for (var i = 0; i < records.length; i++) {
	        	for (var j = 0; j < submits.length; j++) {
	        		for (var key in submits[j]) {
	        			records[i].set(submits[j][key], records[i].get(key));
	        		}
	        	}
	        }
        }
        
        return records;
    },

    /**
	 * 加载数据
	 * 
	 * @param records
	 */
    loadData: function(records, append) {
        var me = this,
            datas = records instanceof Array ? records : [records],
            len = datas.length,
            newData = [],
            summary = me.summary || [];
        for (var i = 0; i < len; i++) {
            if (datas[i].isModel) {
                newData.push(this.clearDatId(datas[i].data));
            } else {
                newData.push(this.clearDatId(datas[i]));
            }
        }
        this.store.isLoadData = true;
        this.store.loadData(newData, append);
        this.store.isLoadData = false;
        delete this.store.isLoadData;

        for (var i = 0; i < summary.length; i++) {
            var summaryField = Ext.getCmp(me.id + 'summary' + summary[i]),
                currentNum = me.store.getSum(summary[i]);
            summaryField.setText(currentNum);
            if (summary[i].endWith("je")) {
                Ext.getCmp(me.id + 'summary' + summary[i] + 'cn').setText(new Number(currentNum).toCn());
            }
        }

        this.setFocus();
    },

    // 将ID设为空，不然getNewRecord会获取不到
    clearDatId: function(data) {
        data['id'] = null;
        return data;
    },

    /**
	 * 获取列的ＴＥＸＴ值
	 */
    getColumn: function(dataIndex) {
        var me = this,
            column,
            columns = me.fields;
        for (var i = 0; i < columns.length; i++) {
            if (columns[i].name == dataIndex) {
                column = columns[i];
                break;
            }
        }
        return column;
    },

    /**
	 * 获取列的ＴＥＸＴ值
	 */
    getColumnText: function(dataIndex) {
        var me = this,
            text = '',
            columns = me.fields;
        for (var i = 0; i < columns.length; i++) {
            if (columns[i].name == dataIndex) {
                text = columns[i].text;
                break;
            }
        }
        return text;
    },

    /**
	 * 数据填充
	 */
    DataFill: function(record, append) {
    	if (this.excludeFill) {
    		return;
    	}
    	if (!Utils.isEmpty(record)) {
    		if (!this.excludeFields) {
                if (!Ext.fly(this.id)) {
                    (function(grid, record) {
                        grid.on('render', function(grid) {
                            grid.DataFill(record, append);
                        });
                    })(this, record);
                    return;
                }
                if (record instanceof Array) {
                    if (this.store.getCount() == 1 && this.exception && this.exception.key && this.store.getAt(0).get(this.exception.key) == '') {
                        this.store.removeAll();
                    }
                    // 判断是否有数据，如果有数据，就进行合并
                    var exist, newRecords = [];
                    if (this.store.getCount() >= 1) {
                        var gridExitsRecords = this.store.getRange(0);
                        
                        for (var i = 0; i < record.length; i++) {
                            exist = false;
                            for (var j = 0; j < gridExitsRecords.length; j++) {
                                if (this.exception && this.exception.key && gridExitsRecords[j].get(this.exception.key) == record[i].get(this.exception.key)) {
                                	if (gridExitsRecords[j].get('qty'))
                                		gridExitsRecords[j].set('qty', new Number(gridExitsRecords[j].get('qty')).add(new Number(record[i].get('qty'))));
                                	gridExitsRecords[j].set('xgdjbh', gridExitsRecords[j].get('linkNo') + record[i].get('linkNo'));
                                    exist = true;
                                }
                            }
                            if (!exist) {
                                newRecords.push(record[i]);
                            }
                        }

                        if (newRecords.length > 0)
                            this.loadData(newRecords, append);
                    } else {
                        this.loadData(record, append);
                    }
                } else {
                    var current = this.getSingleSelect(),
                        items = current ? current.fields.items : [],
                        len = items.length,
                        fillData;
                    for (var i = 0; i < len; i++) {
                        fillData = record[items[i].name];

                        if ((Utils.isEmpty(this.excludeFields) || this.excludeFields.indexOf(items[i].name) == -1) && !Utils.isEmpty(fillData) && items[i].name != 'id')
                            current.set(items[i].name, record[items[i].name]);

                        fillData = null;
                    }
                }
                this.setFocus();
            }
    	}
    },

    /**
	 * 开始编辑状态
	 * 
	 * @param {Object}
	 *            grid
	 * @param {Object}
	 *            e
	 */
    startEditor: function(e) {
        editingPlugin = this.plugins[0];
        editingPlugin.event = e;
        this.currentPosition = null;
        editingPlugin.context = null;
        editingPlugin.next();
    },
    /**
	 * 选中CELL
	 * 
	 * @param {Object}
	 *            position
	 * @memberOf {TypeName}
	 */
    focusCell: function(position) {
        var cell = new Ext.grid.CellContext(this.getView());
        cell.setPosition(position.row, position.column);
        this.getView().focusCell(cell);
        this.getSelectionModel().select(0);
    },
    // 获取位子的值,cloumn为dataIndex, row为数字
    loadPositionVal: function(val, context) {
        var dataIndex = val.dataIndex,
            rowdiff = new Number(val.rowIndex),
            constant = val.constant,
            result;
        // 当前行，就取当前的数值
        if (constant) result = context.grid.store.getAt(rowdiff).get(dataIndex);
        else if (rowdiff === 0) {
            result = context.record.get(dataIndex);
        } else {
            result = context.grid.store.getAt(context.rowIdx + rowdiff).get(dataIndex);
        }
        return result;
    },
    // 清除公式标记
    clearFormula: function(field, clearColumn) {
        var grid = this;
        grid.plugins[0].clicksToEdit = 1;
        if (clearColumn)
            grid.plugins[0].context.column.formula = null;
        else
            grid.plugins[0].context.column.formula = grid.plugins[0].context.column.formula.getValue();
        grid.formula = null;
        field.up('editor').editing = true;
        field.up('editor').hidden = false;
        grid.plugins[0].activeColumn = grid.plugins[0].context.column;
    },
    // 拆分公式，返回dataIndex数组
    loadSplitFormulaColumns: function(val) {
        var columns = new Array(),
            result = new Array(),
            startSign = 1,
            endSign = 0;
        for (var i = 2; i <= val.length + 1; i++) {
            if ((Utils.isCalculationChat(val.substring(i - 1, i)) && val.substring(i - 2, i - 1) != '[') || (startSign > 0 && i == val.length + 1)) {
                endSign = i - 1;
                columns.push(val.substring(startSign, endSign));
            } else if (val.substring(i - 1, i) === ':' || val.substring(i - 1, i) === '^') {
                startSign = i - 1;
            } else {
                continue;
            }
        }
        for (var i = 0; i < columns.length; i++) {
            for (var j = 0; j < this.columns.length; j++) {
                if (this.columns[j].xtype != 'rownumberer' && columns[i].substring(0, 1) === ':' && this.columns[j].text === columns[i].substring(1, columns[i].indexOf('['))) {
                    result.push({
                        constant: false,
                        dataIndex: this.columns[j].dataIndex,
                        text: this.columns[j].text,
                        rowIndex: columns[i].substring(columns[i].indexOf('[') + 1, columns[i].indexOf(']'))
                    });
                } else if (this.columns[j].xtype != 'rownumberer' && columns[i].substring(0, 1) === ':' && this.columns[j].text === columns[i].substring(1, columns[i].indexOf('['))) {
                    result.push({
                        constant: true,
                        dataIndex: this.columns[j].dataIndex,
                        text: this.columns[j].text,
                        rowIndex: columns[i].substring(columns[i].indexOf('[') + 1, columns[i].indexOf(']'))
                    });
                }
            }
        }
        return result;
    },
    doDelete: function(row) {
    	if (!this.allowDelete) {
    		Ext.Msg.toast('不允许删除行', 'error');
    	}
    	this.store.removeAt(row);
    	if (this.store.getCount() == 0 && this.autoInsert) {
    		this.doInsert();
    	}
    },

    doClear: function() {
    	this.store.removeAll();
    	if (this.autoInsert) {
    		this.doInsert();
    	}
    },
    
    // 重新给最后编辑的cell获取焦点
    reFocusEditor: function(grid) {
        if (Utils.isEmpty(grid)) {
            grid = this;
        }
        grid.plugins[0].startEditByPosition(grid.currentPosition);
    },

    // GRID开始查询
    // 如果grid为tabpanel的时候，点击title会自动触发查询效果，
    // 查询方案，默认已第1个的URL-（index）的形式
    // 说明：params 查询的参数，保存xtype信息
    // tab tabpanel 如果不为空,则代表为tabpanel下的grid
    // withOutAlert: 无数据时，是否弹出alert提示框
    doQuery: function(params, tab, withOutAlert, fun) {
        var me = this;

        if (!Utils.isEmpty(tab)) {
            for (var i = 0; i < tab.items.length; i++) {
                tab.items.items[i].isLoad = false;
            }
            tab.params = params;
            tab.getActiveTab().isLoad = true;
            tab.on("tabchange",
                function(tabPanel, newCard, oldCard, opts) {
                    if (Utils.isEmpty(newCard.isLoad) || newCard.isLoad == false) {
                        var index = tab.getActiveIndex(),
                            xtype = tab.params['xtype'],
                            idx = xtype.lastIndexOf('-');
                        // 去掉最后的数字
                        tab.params['xtype'] = (idx > 0 ? xtype.substring(0, idx) : xtype) + (index > 0 ? ("-" + index) : '');
                        newCard.doQuery(tab.params);
                        newCard.isLoad = true;
                    }
                });
        }

        var me = this,
            grid = me,
            toptip = me.top();

        grid.store.currentPage = 1

        if (grid.store.getProxy().url == ''){
            grid.store.getProxy().url = params['url'];
        }
        grid.store.on('beforeload', function(s) {
     		var extraParams = s.getProxy().extraParams;
     		extraParams['limit'] = grid.pagination ? extraParams['limit'] : 100000000;
           	if (grid.pagination) {
           		extraParams['pagination']=true;
           	}
     		Ext.apply(extraParams,params);
        });
        
        grid.store.load({
            scope: this,
            callback: function(records, operation, success) {
            	if (Ext.decode(operation._response.responseText).prompt == 'UnLogin') {
            		alert('系统长时间未操作，请重新登录！');
            		location.reload() 
            	}
                // 没有数据
                if (records && records.length == 0 && !withOutAlert) {
                    Ext.MessageBox.alert("提示", "数据为空", function(ok) {
                        if (ok == "ok") {
                            if (toptip.getXTypes().indexOf("window") > -1) {
                                toptip.focus(false, 200);
                                toptip.close();
                                if (!Utils.isEmpty(toptip.refer) && !Utils.isEmpty(toptip.refer.plugins)) {
                                    me.reFocusEditor(toptip.refer);
                                } else if (!Utils.isEmpty(toptip.refer) && toptip.refer.xtype == 'textfield') {
                                    toptip.refer.focus();
                                }
                            } else {
                                if (grid.allowInsert)
                                    grid.doInsert();
                            }
                        }
                    });
                    return;
                } else {
                    me.setFocus();
                }
                if (fun) {
                    fun(records);
                }
            }
        });
    },

    setFocus: function(row) {
        var me = this;

        if (me.selType == "cellmodel") {
            cell = new Ext.grid.CellContext(me.getView());
            cell.setPosition(row || 0, 1);
            me.getView().focusCell(cell, 200);
        } else if (me.selType == "checkboxmodel") {
            cell = new Ext.grid.CellContext(me.getView());
            cell.setPosition(row || 0, 2);
            me.getView().focusCell(cell, 200);
        } else {
            me.getView().focusRow(row || 0);
            me.getSelectionModel().select(row || 0);
            if (me.getView().bufferedRenderer && me.getView().getEl())
                me.getView().bufferedRenderer.scrollTo((row || 0));
        }
    },

    // 动态创建store
    // 说明：采用extjs的field字段,默认采用的是query.do，如果有action定义，强制使用action
    createStore: function(datas) {
        var me = this,
            store;

        for (var i = 0; i < me.fields.length; i++) {
            if (me.fields[i].type == 'check') {
                me.fields[i].renderType = 'check';
                me.fields[i].type = 'string';
            }
            if (me.fields[i].type == 'switch') {
                me.fields[i].renderType = 'switch';
                me.fields[i].type = 'string';
            }
            me.fields[i].type = me.fields[i].type || "string";
        }
        store = new Ext.create('Ext.data.Store', {
            fields: me.fields,
            groupField: this.groupField,
            proxy: {
                type: 'ajax',
                actionMethods: {
                    create: 'POST',
                    read: 'get',
                    update: 'put',
                    destroy: 'delete'
                },
                pageSize: 50,
                timeout: me.queryTimeout || 600000000,
                reader: {
                    type: 'json',
                    rootProperty: 'root',
                    totalProperty: 'totalCount'
                }
            }
        });

        if (this.groupField) {
            Utils.apply(this, {
                selType: "rowmodel",
                features: Ext.create("Ext.grid.feature.Grouping", {
                    groupHeaderTpl: "{name}: ({rows.length}比)"
                })
            });
        }

        Ext.apply(me, {
            store: store
        });
    },

    // 将表达式的索引值存放
    // 说明:如果表达式索引不存在，则新建一个队列
    // 参数:indexs: 索引数组, expr: 表达式的值, render:影响到的列
    pushExprIndex: function(indexs, expr, render) {
        var me = this,
            exprIndex = exprs[me.id] || [];
        for (var i = 0; i < indexs.length; i++) {
            if (Utils.isEmpty(exprIndex[indexs[i]])) {
                exprIndex[indexs[i]] = [];
            }
            exprIndex[indexs[i]].push({
                expr: expr,
                indexs: indexs,
                render: render
            });
        }

        exprs[me.id] = exprIndex;
    },

    // 解析每个字段的表达式
    // 说明: 逐个字符对表达式进行解析(a+b)*aa等，只允许出现数值形的常量
    // 参数: expr 表达式; render: 影响到的字段值
    parseExpr: function(expr, render) {
        var me = this,
            len = expr.length,
            elem = [],
            start = 0,
            char, index, indexs = [];
        for (var i = 0; i < len; i++) {
            char = expr.charAt(i);
            if (char !== '+' && char !== '-' && char !== '*' && char !== '/' && char !== '(' && char !== ')') {
                continue;
            } else {
                if (start != i) {
                    index = expr.substring(start, i);
                    if (!/^\d+(\.\d{1,4})?$/.test(index)) {
                        indexs.push(index);
                    }
                }
                start = i + 1;
            }
        }
        if (!/^\d+(\.\d{1,4})?$/.test(expr.substring(start, len)) && start != len) {
            indexs.push(expr.substring(start, len));
        }
        me.pushExprIndex(indexs, expr, render);
    },

    /**
	 * 获取合并的单元格
	 */
    getMergeColumn: function() {
        var me = this,
            fields = me.fields,
            mergeHeader = {},
            idx = 0;
        for (var i = 0; i < fields.length; i++) {
            // 加入单元格配置
            if (fields[i].cell) {
                // 合并单元格
                if (fields[i].cell.merge) {
                    if (fields[i].cell.merge.header) {
                        var headerMergeText = fields[i].cell.merge.header,
                            arr = mergeHeader[headerMergeText] || [];
                        arr.push(i);
                        mergeHeader[headerMergeText] = arr;
                    }
                }
            }
        }

        for (var items in mergeHeader) {
            var columns = [];
            for (var i = 0; i < mergeHeader[items].length; i++) {
                mergeHeader[items][i] = mergeHeader[items][i] - idx;
                columns.push(me.fields[mergeHeader[items][i]]);
            }
            fields.removeAll(mergeHeader[items]);
            fields.add({
                text: items,
                columns: columns
            }, mergeHeader[items][0]);

            idx = mergeHeader[items].length - 1;
        }

        me.fields = fields;
        return me.fields;
    },

    // 动态创建列
    // 说明：采用extjs的field字段，其中dataIndex对应的是field里面的name
    createColumns: function(datas) {
        var me = this,
            fields = me.getMergeColumn(me.fields),
            columns = new Array(),
            details = me.booth || [],
            summary = me.summary || [],
            strBar = [],
            summarySize = 0;
        numericBar = [],
            summaryBar = [],
            summaryStr = "summary",
            cnStr = "cn";

        columns.push({
            xtype: 'rownumberer',
            width: 30
        });
        
        if (me.oper)
	        columns.push({
	        	xtype: 'oper',
	        	width: 60
	        });

        if (Utils.isEmpty(datas)) {
        	if (!me.pagination && me.showTotle) {
        		summaryBar.push({
                    xtype: 'label',
                    text: '条目数：',
                    style: '14px/1.1 "Microsoft Yahei";display:inline-block;padding-left:20px;'
                });
                
                summaryBar.push({
                    xtype: 'label',
                    style: 'display:inline-block;font:italic small-caps bold 14px/1.3em Arial;color:red ',
                    id: me.id + summaryStr + "_tms",
                    text: '0.00'
                });
        	}
            for (var i = 0; i < fields.length; i++) {
            	/*if ((fields[i]['name'] == 'settlementPrice' || fields[i]['name'] == 'settlmentPrice' || fields[i]['name'] == 'settlmentAmount' || fields[i]['name'] == 'settlmentAmount') && Ext.fly('settlementPrice').getValue() != '是') {
            		fields[i].hidden = true;
            	}
            	else if ((fields[i]['name'] == 'retailPrice' || fields[i]['name'] == 'retailAmount') && Ext.fly('retailPrice').getValue() != '是') {
            		fields[i].hidden = true;
            	}
            	else if ((fields[i]['name'] == 'memberPrice' || fields[i]['name'] == 'memberAmount') && Ext.fly('memberPrice').getValue() != '是') {
            		fields[i].hidden = true;
            	}*/
            	/*else if ((fields[i]['name'] == 'dispatchingPrice' || fields[i]['name'] == 'dispatchingAmount') && Ext.fly('dispatchingPrice').getValue() != '是') {
            		fields[i].hidden = true;
            	}
            	else if ((fields[i]['name'] == 'costPrice' || fields[i]['name'] == 'costAmount') && Ext.fly('costPrice').getValue() != '是') {
            		fields[i].hidden = true;
            	}*/
                fields[i]['dataIndex'] = fields[i]['name'];
                fields[i].sortable = me.sortable;
                if (fields[i].columns) {
                    fields[i].height = 45;
                } else {
                    fields[i].height = 30;
                }
                if (fields[i].name === 'billNo') {
                    fields[i].renderer = function(val) {
                        return '<a style="color:black"><strong>' + val + '</strong></a>';
                    }
                }


                // 加入是和否的check
                if (fields[i].renderType == 'check') {
                    (function(field) {
                    	field.renderer = function(value, cellmeta, record, rowIndex, columnIndex, store) {
                            let str;
                        	if (value == '是') {
                                str = '<input class="checkbox" onclick="Utils.changeGridCellRecord(this, \'cell\')" id="' + this.ownerGrid.id + '_' + rowIndex + '_' + columnIndex + '" bb="bb" type="checkbox" checked />';
                            } else {
                                str = '<input class="radio" onclick="Utils.changeGridCellRecord(this, \'cell\')" id="' + this.ownerGrid.id + '_' + rowIndex + '_' + columnIndex + '" aa="aa" type="checkbox" />';
                            }
                        	return str;
                        }
                    })(fields[i]);
                }
                else if (fields[i].renderType == 'switch') {
                    fields[i].renderer = function(value, cellmeta, record, rowIndex, columnIndex, store) {
                        if (value == '是') {
                            var str = '<div class="switch" data-on="danger" data-off="primary"><input type="checkbox" checked /></div>';
                            return str
                        } else {
                            var str = '<div class="switch" data-on="danger" data-off="primary"><input type="checkbox" /></div>';
                            return str
                        }
                    }
                } else if (me.importExcelCheck){
                	fields[i].renderer = function(value, meta, record, rowIndex, columnIndex, store, view) {
                		var field = view.up('grid').fields[columnIndex-1];
                		if (field.regex && !field.regex.test(value)) {
                			meta.tdStyle = "background-color:red;";
            				if (!record.errors) {
            					record.errors = {};
            				}
                			record.errors[field.name]=field.text + ":" + field.regexText;
                		} else {
                			if (record.errors) {
            					delete record.errors[field.name];
            				}
                		}
                		return value;
                    }
                }


                if (me.editable && Utils.isEmpty(fields[i]['editor']) && (typeof fields[i].editable == 'function' || typeof fields[i].editable == 'undefined') && !fields[i].hidden) {
                    fields[i]['editor'] = {
                        xtype: 'textfield'
                    };
                }
                
                if (fields[i].editor) {
                	fields[i].renderer = function(value, meta, record, rowIndex, columnIndex, store, view) {
            			meta.tdStyle = "background-color:#8B2500;";
            			return value;
                    };
                }
                
                // 建立详情
                if (details.contains(fields[i].name)) {
                    if (fields[i].type == 'int' || fields[i].type == 'number') {
                        numericBar.push({
                            xtype: 'label',
                            text: fields[i].text + ':',
                            style: 'display:inline-block; ',
                            width: (fields[i].text.length + 1) * 15
                        });

                        numericBar.push({
                            xtype: 'label',
                            style: 'display:inline-block; ',
                            id: me.id + '-detail-' + fields[i].name,
                            width: 100,
                            text: '0.00'
                        });
                    } else {
                        strBar.push({
                            xtype: 'label',
                            style: 'display:inline-block; padding-left: 20px;',
                            labelAlign: 'right',
                            text: fields[i].text + ':',
                            width: (fields[i].text.length + 1) * 20
                        });

                        strBar.push({
                            xtype: 'label',
                            style: 'display:inline-block;',
                            id: me.id + '-detail-' + fields[i].name,
                            width: fields[i].width
                        });
                    }
                }

                if (summary.contains(fields[i].name)) {
                    summaryBar.push({
                        xtype: 'label',
                        text: fields[i].text + '合计：',
                        style: '14px/1.1 "Microsoft Yahei";display:inline-block;padding-left:20px;'
                    });
                    
                    summaryBar.push({
                        xtype: 'label',
                        style: 'display:inline-block;font:italic small-caps bold 14px/1.3em Arial;color:red ',
                        id: me.id + summaryStr + fields[i].name,
                        text: '0.00'
                    });

                    if (fields[i].name.endWith('je')) {
                        summaryBar.push({
                            xtype: 'label',
                            text: '大写：',
                            style: 'display:inline-block; padding-left:10px',
                            width: 50
                        });
                        summaryBar.push({
                            xtype: 'label',
                            style: 'display:inline-block; ',
                            hidden: summarySize > 2 ? true : false,
                            id: me.id + summaryStr + fields[i].name + cnStr,
                            width: 160
                        });
                    }
                    summarySize++;
                }

                // 加入表达式的计算
                if (fields[i].expr) {
                    me.parseExpr(fields[i].expr, fields[i].name);
                }

                // 加入过滤事件
                if (fields[i].verify) {
                    (function(verify, name) {
                        fields[i]['execute'] = function(editor, context, e) {
                            var data = Utils.mergeSystemParam(context.record.data),
                                toptip = context.grid.top(),
                                form = toptip.getXTypes().indexOf("form") > -1 ? toptip : toptip.down('form'),
                                plan;
                            if (form) {
                                data = Utils.applyIf(data, form.getFormData('query'));
                            }
                            if (typeof verify === 'string') {
                                plan = verify;
                            } else {
                                plan = 'verify' + '_' + context.grid.top().xtype + '_' + name;
                            }

                            data['xtype'] = plan;

                            Utils.ajax('/verify', data, function(callback) {
                                if (callback.msg != '') {
                                    Ext.MessageBox.alert("提示", callback.msg, function(ok) {
                                        if (ok == "ok") {
                                            context.record.set(name, context.originalValue);
                                            context.grid.reFocusEditor();
                                        }
                                    });
                                    return;
                                } else {
                                    editor.next(e);
                                }
                            });

                            return false;
                        };

                    })(fields[i].verify, fields[i].name);
                }

                // 加入对比事件
                if (fields[i].compareTo) {
                    (function(compareTo, name, text) {
                        fields[i]['execute'] = function(editor, context, e) {
                            // (小于等于某个值)
                            if (compareTo.indexOf('<=') > -1) {
                                var toField = compareTo.substring(compareTo.indexOf('<=') + 2, compareTo.length),
                                    sign = compareTo.substring(0, compareTo.indexOf('<=')),
                                    val = context.record.get(toField);

                                if (new Number(context.value).sub(new Number(val)) > 0) {
                                    Ext.MessageBox.toast(text + "应小于等于" + context.grid.getColumnText(toField), 'error');
                                    context.record.set(name, context.originalValue);
                                    context.grid.reFocusEditor();
                                    return false;
                                } else {
                                	return true;
                                }
                            }

                            // (大于等于某个值)
                            else if (compareTo.indexOf('>=') > -1) {
                                var toField = compareTo.substring(compareTo.indexOf('>=') + 2, compareTo.length),
                                    sign = compareTo.substring(0, compareTo.indexOf('>=')),
                                    val = context.record.get(toField);

                                if (new Number(context.value).sub(new Number(val)) < 0) {
                                    Ext.MessageBox.toast(text + "应大于等于" + context.grid.getColumnText(toField), 'error');
                                    context.record.set(name, context.originalValue);
                                    context.grid.reFocusEditor();
                                    return false;
                                } else {
                                	return true;
                                }
                            }

                            // (小于某个值)
                            else if (compareTo.indexOf('<') > -1) {
                                var toField = compareTo.substring(compareTo.indexOf('<') + 1, compareTo.length),
                                    sign = compareTo.substring(0, compareTo.indexOf('<')),
                                    val = context.record.get(toField);

                                if (new Number(context.value).sub(new Number(val)) >= 0) {
                                    Ext.MessageBox.toast(text + "应小于" + context.grid.getColumnText(toField), 'error');
                                    context.record.set(name, context.originalValue);
                                    context.grid.reFocusEditor();
                                    return false;
                                } else {
                                	return true;
                                }
                            }

                            // (大于某个值)
                            else if (compareTo.indexOf('>') > -1) {
                                var toField = compareTo.substring(compareTo.indexOf('>') + 1, compareTo.length),
                                    sign = compareTo.substring(0, compareTo.indexOf('>')),
                                    val = context.record.get(toField);

                                if (new Number(context.value).sub(new Number(val)) <= 0) {
                                    Ext.MessageBox.toast(text + "应大于" + context.grid.getColumnText(toField), 'error');
                                    context.record.set(name, context.originalValue);
                                    context.grid.reFocusEditor();
                                    return false;
                                } else {
                                	return true;
                                }
                            }

                            // (不等于某个值)
                            else if (compareTo.indexOf('!=') > -1) {
                                var toField = compareTo.substring(compareTo.indexOf('!=') + 2, compareTo.length),
                                    sign = compareTo.substring(0, compareTo.indexOf('!=')),
                                    val = context.record.get(toField);

                                if (new Number(context.value).sub(new Number(val)) == 0) {
                                    Ext.MessageBox.toast(text + "应不等于" + context.grid.getColumnText(toField), 'error');
                                    context.record.set(name, context.originalValue);
                                    context.grid.reFocusEditor();
                                    return false;
                                } else {
                                	return true;
                                }
                            }

                            // (等于某个值)
                            else if (compareTo.indexOf('=') > -1) {
                                var toField = compareTo.substring(compareTo.indexOf('=') + 1, compareTo.length),
                                    sign = compareTo.substring(0, compareTo.indexOf('=')),
                                    val = context.record.get(toField);

                                if (new Number(context.value).sub(new Number(val)) != 0) {
                                    Ext.MessageBox.toast(text + "应等于" + context.grid.getColumnText(toField), 'error');
                                    context.record.set(name, context.originalValue);
                                    context.grid.reFocusEditor();
                                    return false;
                                } else {
                                	return true;
                                }
                            }
                            
                         // 在什么之前
                            else if (compareTo.indexOf('before') > -1) {
                                var toField = compareTo.substring(compareTo.indexOf(':') + 1, compareTo.length),
                                    sign = compareTo.substring(0, compareTo.indexOf(':')),
                                    val = toField == 'now' ? new Date() : context.record.get(toField);

                                if (!new Date(context.value).before(val)) {
                                    Ext.MessageBox.toast(text + "应该早于" + val.Format('yyyy-MM-dd'), 'error');
                                    context.record.set(name, context.originalValue);
                                    context.grid.reFocusEditor();
                                    return false;
                                } else {
                                	return true;
                                }
                            }

                            // 在时间之后
                            else if (compareTo.indexOf('after') > -1) {
                                var toField = compareTo.substring(compareTo.indexOf(':') + 1, compareTo.length),
                                    sign = compareTo.substring(0, compareTo.indexOf(':')),
                                    val = toField == 'now' ? new Date() : context.record.get(toField);

                                if (!new Date(context.value).after(val)) {
                                    Ext.MessageBox.toast(text + "应该晚于" + val.Format('yyyy-MM-dd'), 'error');
                                    context.record.set(name, context.originalValue);
                                    context.grid.reFocusEditor();
                                    return false;
                                } else {
                                	return true;
                                }
                            }
                            	
                        };

                    })(fields[i].compareTo, fields[i].name, fields[i].text);
                }

                if (fields[i].search) {
                    (function(field) {
                        field['editor'] = {
                            xtype: 'textfield'
                        };

                        field['execute'] = function(editor, context, e) {
                            var form = context.grid.top().down('form'),
                                depends = typeof field.depend == 'string' ? [field.depend] : (field.depend || []),
                                len = depends.length,
                                formData = form ? form.getFormData() : {},
                                gridData = Utils.mergeSystemParam(context.grid.getQueryData()),
                                data;

                            if (form) {
                                for (var i = 0; i < len; i++) {
                                    formField = form.down('*[name=' + depends[i] + ']');

                                    if (formField.getValue() == '') {
                                        Ext.MessageBox.alert("提示", formField.fieldLabel + '不能为空', function(ok) {
                                            if (ok == "ok") {
                                                formField.focus(false);
                                                return false;
                                            }
                                        });
                                        return false;
                                    }
                                }
                            }

                            var win = Ext.create(field.search, {
                                    refer: context.grid,
                                    record: context.record
                                }),
                                grid = win.down('grid');

                            data = Utils.applyIf(gridData, formData);
                            data['xtype'] = win.xtype;

                            grid.store.getProxy().url = grid.action || '/search';

                            grid.doQuery(data);
                            win.show();

                            win.down('button[text^=确定]').on('click', function() {
                            	if (win.line) {
                            		win.close();
                                    editor.next(e);
                            	}
                                var records = grid.getSelect();

                                if (records.length == 0) {
                                    return;
                                }

                                // 需要进行排重
                                if (!context.grid.dupl) {
                                    var key = context.grid.exception.key,
                                        gridRecords = context.grid.store.getRange(),
                                        result = '',
                                        existsName = '';

                                    for (var i = 0; i < gridRecords.length; i++) {
                                    	for (var j = 0; j < records.length; j++) {
	                                        if (gridRecords[i].get(key) != '' && context.rowIdx != i && gridRecords[i].get(key) == records[j].get(key)) {
	                                        	existsName = gridRecords[i].get(key);
	                                        	break;
	                                        }
	                                    }
                                    }
                                    
                                    if (existsName != '') {
                                    	Ext.MessageBox.toast(existsName+'已存在，不允许重复', 'error');
                                    	return;
                                    }
                                }
                                
                                if (records.length >= 1) {
                                    data = records[0].data;
                                    Utils.applyIf(data, context.grid.dataDefault);
                                    context.grid.DataFill(data);
                                    context.grid.setFocus(context.rowIdx);
                                }

                                
                                if (records.length > 1) {
                                    records.remove(0);
                                    context.grid.loadData(records, true);
                                }
                                win.close();
                                editor.next(e);
                            });

                            win.down('button').handlerAop = function(me) {
                                var grid = me.top().down('grid'),
                                    records = grid.getSelect(),
                                    flg = true,
                                    record;
                                for (var i = 0; i < records.length; i++) {
                                    record = records[i];
                                    if (record.get('bgcolor') != '' && grid[record.get('bgcolor') + 'Text']) {
                                        var msg = grid[record.get('bgcolor') + 'Text'];
                                        for (var prop in record.data) {
                                            msg = msg.replace('{' + prop + '}', record.get(prop));
                                        }
                                        Ext.MessageBox.toast(msg, 'error');
                                        if (!grid[record.get('bgcolor') + 'Pass']) {
                                            flg = false;
                                            break;
                                        }
                                    }
                                }
                                return flg;
                            }


                            grid.on('itemdblclick', function(view, record) {
                                if (record.get('bgcolor') != '' && grid[record.get('bgcolor') + 'Text']) {
                                    var msg = grid[record.get('bgcolor') + 'Text'];
                                    for (var prop in record.data) {
                                        msg = msg.replace('{' + prop + '}', record.get(prop));
                                    }
                                    Ext.MessageBox.alert("错误", msg, function(ok) {
                                        if (ok == "ok") {
                                            win.close();
                                            context.grid.reFocusEditor();
                                        }
                                    });
                                    if (!grid[record.get('bgcolor') + 'Pass']) {
                                        return;
                                    }

                                    return false;
                                }
                                data = record.data;

                                Utils.applyIf(data, context.grid.dataDefault);
                                context.grid.DataFill(data);
                                context.grid.setFocus(context.rowIdx);
                                win.close();
                                editor.next(e);
                            });

                            return false;
                        };
                    })(fields[i]);
                }

                if (fields[i].columns) {
                    for (var x = 0; x < fields[i].columns.length; x++) {
                        fields[i].columns[x]['dataIndex'] = fields[i].columns[x]['name'];
                        if (me.editable && Utils.isEmpty(fields[i].columns[x]['editor']) && (typeof fields[i].columns[x].editable == 'function' || typeof fields[i].columns[x].editable == 'undefined') && !me.hidden) {
                            fields[i].columns[x]['editor'] = {
                                xtype: 'textfield'
                            };
                        }

                        // 建立详情
                        if (details.contains(fields[i].columns[x].name)) {
                            if (fields[i].columns[x].type == 'int' || fields[i].columns[x].type == 'number') {
                                numericBar.push({
                                    xtype: 'label',
                                    text: fields[i].columns[x].text + ':',
                                    style: 'display:inline-block; ',
                                    width: (fields[i].columns[x].text.length + 1) * 15
                                });

                                numericBar.push({
                                    xtype: 'label',
                                    style: 'display:inline-block; ',
                                    id: me.id + '-detail-' + fields[i].columns[x].name,
                                    width: 100,
                                    text: '0.00'
                                });
                            } else {
                                strBar.push({
                                    xtype: 'label',
                                    style: 'display:inline-block; ',
                                    labelAlign: 'right',
                                    text: fields[i].columns[x].text + ':',
                                    width: (fields[i].columns[x].text.length + 1) * 15
                                });

                                strBar.push({
                                    xtype: 'label',
                                    style: 'display:inline-block; ',
                                    id: me.id + '-detail-' + fields[i].columns[x].name,
                                    width: 500
                                });
                            }
                        }

                        if (summary.contains(fields[i].columns[x].name)) {
                            summaryBar.push({
                                xtype: 'label',
                                text: fields[i].columns[x].text + '合计',
                                style: 'display:inline-block; ',
                                width: (fields[i].columns[x].text.length + 2) * 15
                            });
                            summaryBar.push({
                                xtype: 'label',
                                style: 'display:inline-block; ',
                                id: me.id + summaryStr + fields[i].columns[x].name,
                                text: '0.00',
                                width: 75
                            });

                            if (fields[i].columns[x].name.endWith('je')) {
                                summaryBar.push({
                                    xtype: 'label',
                                    hidden: true,
                                    text: fields[i].columns[x].text + '合计(大写)',
                                    style: 'display:inline-block; ',
                                    width: (fields[i].columns[x].text.length + 4) * 15 + 10
                                });
                                summaryBar.push({
                                    xtype: 'label',
                                    hidden: true,
                                    style: 'display:inline-block; ',
                                    id: me.id + summaryStr + fields[i].columns[x].name + cnStr,
                                    width: 200
                                });
                            }
                        }

                        // 加入表达式的计算
                        if (fields[i].columns[x].expr) {
                            me.parseExpr(fields[i].columns[x].expr, fields[i].columns[x].name);
                        }

                        // 加入过滤事件
                        if (fields[i].columns[x].verify) {
                            (function(verify, name) {
                                fields[i].columns[x]['execute'] = function(editor, context, e) {
                                    var data = Utils.mergeSystemParam(context.record.data),
                                        plan;

                                    if (typeof verify === 'string') {
                                        plan = verify;
                                    } else {
                                        plan = 'verify' + '_' + context.grid.top().xtype + '_' + name;
                                    }

                                    data['xtype'] = plan;

                                    Utils.ajax('/verify', data, function(callback) {
                                        if (callback.msg != '') {
                                            Ext.MessageBox.alert("提示", callback.msg, function(ok) {
                                                if (ok == "ok") {
                                                    context.record.set(name, context.originalValue);
                                                    context.grid.reFocusEditor();
                                                }
                                            });
                                            return;
                                        } else {
                                            editor.next(e);
                                        }
                                    });

                                    return false;
                                };

                            })(fields[i].columns[x].verify, fields[i].columns[x].name);
                        }

                        if (fields[i].columns[x].search) {
                            (function(field) {
                                field['execute'] = function(editor, context, e) {
                                    var form = context.grid.top().down('form'),
                                        depends = typeof field.depend == 'string' ? [field.depend] : (field.depend || []),
                                        len = depends.length,
                                        formData = form ? form.getFormData() : {},
                                        gridData = Utils.mergeSystemParam(context.grid.getQueryData()),
                                        data;

                                    if (form) {
                                        for (var i = 0; i < len; i++) {
                                            formField = form.down('*[name=' + depends[x] + ']');

                                            if (formField.getValue() == '') {
                                                Ext.MessageBox.alert("提示", formField.fieldLabel + '不能为空', function(ok) {
                                                    if (ok == "ok") {
                                                        formField.focus(false);
                                                        return false;
                                                    }
                                                });
                                                return false;
                                            }
                                        }
                                    }

                                    var win = Ext.create(field.search, {
                                            refer: context.grid
                                        }),
                                        grid = win.down('grid');

                                    data = Utils.apply(gridData, formData);
                                    data['xtype'] = win.xtype;

                                    grid.store.getProxy().url = grid.action || '/search';

                                    grid.doQuery(data);
                                    win.show();

                                    grid.on('itemdblclick', function(view, record) {
                                        data = record.data;

                                        Utils.applyIf(data, context.grid.dataDefault);
                                        context.grid.DataFill(data);
                                        win.close();
                                        editor.next(e);
                                    });

                                    return false;
                                };
                            })(fields[i].columns[x]);
                        }
                    }
                }
            }
            columns = columns.concat(fields);
        } else {
            var data = datas[0];
            for (var f in data) {
                if (f != 'id') {
                    columns.push({
                        dataIndex: f,
                        text: f
                    });
                }
            }
        }

        Ext.apply(me, {
            columns: columns
        });

        if ((strBar && strBar.length > 0) || (numericBar && numericBar.length > 0) || (summaryBar && summaryBar.length > 0)) {
            Ext.apply(me, {
                bbar: [{
                    xtype: 'container',
                    width: '100%',
                    frame: false,
                    style: 'background:white;border-color: white;',
                    layout: {
                        type: 'vbox',
                        align: 'middel'
                    },
                    items: [{
                        xtype: 'container',
                        height: 25,
                        width: '100%',
                        items: strBar,
                        style: 'background:white',
                        hidden: !(strBar && strBar.length > 0)
                    }, {
                        xtype: 'container',
                        width: '100%',
                        height: 25,
                        style: 'border-top:thin groove black;background:white',
                        items: numericBar,
                        hidden: !(numericBar && numericBar.length > 0)
                    }, {
                        xtype: 'container',
                        height: 25,
                        width: '100%',
                        id: me.id + 'summary',
                        style: 'border-top:thin groove black;background:white',
                        items: summaryBar,
                        hidden: !(summaryBar && summaryBar.length > 0)
                    }]
                }]
            });
        }

        if (numericBar.length > 0 || strBar.length > 0) {
            (function(details) {
                // 添加监听
                me.on('select', function(model, record) {
                    var lab, len = 0;

                    for (var i = 0; i < details.length; i++) {
                        lab = Ext.getCmp(me.id + '-detail-' + details[i]);

                        lab.setText(Utils.toStr(record.get(details[i])));
                    }
                });
            })(details);
        }

        if (summary.length > 0) {
            (function(summary, details) {
                // 重算汇总金额
                me.store.on('datachanged', function(store) {
                    if (me.store.isLoadData) {
                        return;
                    }

                    var records = me.getRecords('new&update');
                    for (var i = 0; i < summary.length; i++) {
                        var summaryField = Ext.getCmp(me.id + 'summary' + summary[i]),
                            currentNum = store.getSum(summary[i]);
                        summaryField.setText(currentNum);

                        if (summary[i].endWith("je")) {
                            Ext.getCmp(me.id + 'summary' + summary[i] + 'cn').setText(new Number(currentNum).toCn());
                        }
                    }
                });

                // 添加监听
                me.store.on('update', function(store, record, operation, modifiedFieldNames, deta, eOpts) {
                    if (me.store.isLoadData) {
                        return;
                    }
                    if (!me.pagination) {
	                    var tmsField = Ext.getCmp(me.id + summaryStr + "_tms");
	                    if (tmsField)
	                    	tmsField.setText(store.getCount());
                    }
                    
                    for (var i = 0; i < modifiedFieldNames.length; i++) {
                        if (me.getColumn(modifiedFieldNames[i]).dataChange) {
                            me.getColumn(modifiedFieldNames[i]).dataChange(me, record);
                        }
                        // 添加汇总
                        if (summary && summary.contains(modifiedFieldNames[i])) {
                            var summaryField = Ext.getCmp(me.id + 'summary' + modifiedFieldNames[i]),
                                currentNum = store.getSum(modifiedFieldNames[i]);
                            summaryField.setText(currentNum);
                            if (modifiedFieldNames[i].endWith('je')) {
                                Ext.getCmp(me.id + 'summary' + modifiedFieldNames[i] + 'cn').setText(new Number(currentNum).toCn());
                            }
                        }

                        // 添加汇总
                        if (details.contains(modifiedFieldNames[i])) {
                            var detailField = Ext.getCmp(me.id + '-detail-' + modifiedFieldNames[i]);
                            detailField.setText(record.get(modifiedFieldNames[i]));
                        }
                    }
                });
            })(summary, details);
        }
        
     // 重算汇总金额
        me.store.on('datachanged', function(store) {
            if (me.store.isLoadData) {
                return;
            }

            var records = me.getRecords('new&update');

            if (!me.pagination) {
	            var tmsField = Ext.getCmp(me.id + summaryStr + "_tms");
	            if (tmsField)
	            	tmsField.setText(store.getCount());
            }
            
            for (var i = 0; i < me.fields.length; i++) {
                for (var j = 0; j < records.length; j++) {
                    // 判断有没有当前修改字段的表达式索引信息
                    if (!Utils.isEmpty(exprs[me.id]) && !Utils.isEmpty(exprs[me.id][me.fields[i].name])) {
                        // 如果有，吧所有的表达式和索引字段信息全部取出来
                        for (var e = 0; e < exprs[me.id][me.fields[i].name].length; e++) {
                            var expr = exprs[me.id][me.fields[i].name][e]['expr'],
                                indexs = exprs[me.id][me.fields[i].name][e]['indexs'],
                                render = exprs[me.id][me.fields[i].name][e]['render'];

                            for (var f = 0; f < indexs.length; f++) {
                                expr = expr.replace(indexs[f], records[j].get(indexs[f]));
                            }
                            records[j].set(render, Utils.eval(expr));
                        }
                    }
                }
            }
        });

        // 添加监听
        me.store.on('update', function(store, record, operation, modifiedFieldNames, deta, eOpts) {
            if (me.store.isLoadData) {
                return;
            }
            for (var i = 0; i < modifiedFieldNames.length; i++) {
                // 判断有没有当前修改字段的表达式索引信息
                if (me.getColumn(modifiedFieldNames[i]) && me.getColumn(modifiedFieldNames[i]).editor && !Utils.isEmpty(exprs[me.id]) && !Utils.isEmpty(exprs[me.id][modifiedFieldNames[i]])) {
                    // 如果有，吧所有的表达式和索引字段信息全部取出来
                    for (var e = 0; e < exprs[me.id][modifiedFieldNames[i]].length; e++) {
                        var expr = exprs[me.id][modifiedFieldNames[i]][e]['expr'],
                            indexs = exprs[me.id][modifiedFieldNames[i]][e]['indexs'],
                            render = exprs[me.id][modifiedFieldNames[i]][e]['render'];

                        for (var f = 0; f < indexs.length; f++) {
                            expr = expr.replace(indexs[f], record.get(indexs[f]));
                        }
                        record.set(render, Utils.eval(expr));
                    }
                }
            }
        });
    },

    getFields: function(name) {
        var fields = this.fields;
        for (var i = 0; i < fields.length; i++) {
            if (fields[i].name == name) {
                return fields[i];
            }
        }
    },

    interval: function() {
        var top = this.top(),
            query = top.down('button[action=query]'),
            query = query || top.down('button[text=查询]');

        query.fireEvent('click', query);
    },

    initComponent: function() {
    	
        var me = this,
            plugins = me.plugins;
        
    	
        if (me.filter) {
        	me.tbar = [{xtype: 'clearFilter'}];
        }
        me.on('itemdblclick', function(view, record) {
        	var djlx = record.get('billType'),
        		djlx = djlx || (record.get('billNo') ? record.get('billNo').substring(0, 3) : '');
        	
            if (me.repeatArgs)
                Utils.repeat(me.repeatArgs[0], me.repeatArgs[1], record);
            if (djlx && me['repeatArgs'+djlx]) {
                Utils.repeat(me['repeatArgs'+djlx][0], me['repeatArgs'+djlx][1], record);
            }
        });
        if (me.selType === 'checkboxmodel')
            me.multiSelect = true;


        if (!Utils.isEmpty(me.datas)) {
            me.on('afterrender', function(){
            	me.store.loadData(me.datas);
            	delete me.datas;
            });
        }

        if (me.loop) {
            me.task = new Ext.util.TaskRunner().start({
                run: me.interval,
                interval: me.loop,
                scope: me
            });
        }
        
        if (me.allowInsert) {
        	me.oper = true;
        }

        if (!Utils.isEmpty(me.fields)) {
            // 构建store和column
            if (Utils.isEmpty(me.store))
                me.createStore();
            if (Utils.isEmpty(me.columns))
                me.createColumns();
            me.on('afterrender', function(grid) {
                if (grid.autoInsert) {
                    grid.store.removeAll(true);
                    this.doInsert();
                }

                if (grid.queryPlan) {
                    var queryParam = Utils.apply({
                        xtype: grid.queryPlan || grid.top().xtype
                    }, grid.queryParam || {});
                    grid.doQuery(queryParam, null, true, function(records){
                    	if ((records == null || records.length) == 0 && grid.autoInsert) {
                    		grid.store.removeAll(true);
                    		grid.doInsert();
                    	}
                    });
                }

                var rclickcollection = [],
                    buttons = Ext.ComponentQuery.query("button", grid);

                for (var i = 0; i < buttons.length; i++) {
                    if (buttons[i].rclick) {
                        rclickcollection.push(buttons[i]);
                    }
                }

                grid.rclickcollection = rclickcollection;
            });

            me.on('beforedestroy', function(me) {
                if (me.task) {
                    new Ext.util.TaskRunner().stop(me.task);
                }
            });
        }
        
        if (me.pagination) {
        	me.bbar = {
                xtype: 'pagingtoolbar',
                store: me.store,
                beforePageText:'当前第',
                afterPageText:'/{0}页',
                refreshText:'刷新',
                firstText : '首页',
                prevText : '上一页',
                nextText : '下一页',
                lastText : '尾页',
                displayInfo:true,
                pageSize: 50,
                displayMsg:'显示:{0}-{1}条,总共:{2}条',
                emptyMsg:'当前查询无记录'
            }
        }

        me.plugins = [Ext.create("core.plugins.CellEditing", {
            clicksToEdit: 1,
            onEscKey: function(e) {
                var window = this.cmp.up("window");
                if (!Utils.isEmpty(window)) {
                    if (window.summary) {
                        window.hide();
                    } else {
                        window.close();
                    }
                }
            },
            onEnterKey: function(e) {
                var grid = this.cmp,
                    window = grid.up("window");
                if (!Utils.isEmpty(window)) {
                    var button = grid.down("button[enterBind=true]");
                    if (Utils.isEmpty(button)) {
                        button = grid.up("window").down("button[enterBind=true]");
                    }
                    grid.fireEvent("itemdblclick", grid, grid.getSelectionModel().getSelection()[0], null, 0, e);
                    if (button) {
                        button.fireEvent("click", button);
                    }
                } else {
                    this.onCellClick(grid.getView(), grid.getView().lastFocused, grid.getView().lastFocused.colIdx, grid.getSelectionModel().getSelection()[0], null, grid.getView().lastFocused.rowIdx, e);
                }
            }
        }), Ext.create("core.plugins.RightMenu"), Ext.create("Ext.grid.plugin.BufferedRenderer", {
            trailingBufferZone: 50,
            leadingBufferZone: 50
        }), Ext.create('core.plugins.HeaderFilter')];

        if (plugins) {
            for (var i = 0; i < plugins.length; i++) {
                me.plugins.push(plugins[i]);
            }
        }

        me.on('cellclick', function(view, td, columnIndex, record, tr, rowIndex, e, eOpts) {
            // 判断是否存在公式,如果存在，则取消celledit的开启状态
            if (me.formula) {
                me.plugins[0].clicksToEdit = -1;
                me.formula.setFormulaValue(me.columns[columnIndex].text, rowIndex - me.currentPosition.row);
            }
        });
        
        this.callParent(arguments);
    },
    afterRender: Ext.Function.createSequence(Ext.grid.GridPanel.prototype.afterRender,
        function() {
            var keyMap;
            keyMap = this.getKeyMap();
            keyMap.on(Ext.event.Event.DELETE, this.onDel, this);
            keyMap.on(Ext.event.Event.INSERT, this.onInsert, this);
            keyMap.on(Ext.event.Event.ENTER, this.onEnter, this);
            var view = this.getView();
            this.tip = new Ext.ToolTip({
                target: view.el,
                delegate: view.cellSelector,
                trackMouse: true,
                renderTo: document.body,
                listeners: {
                    beforeshow: function updateTipBody(tip) {
                    	var trigger = tip.triggerElement,
	                        parent = tip.triggerElement.parentElement,
	                        columnDataIndex = view.getHeaderByCell(trigger).dataIndex;
	                    if (view.getRecord(parent) && view.getRecord(parent).errors && view.getRecord(parent).errors[columnDataIndex]) {
	                    	tip.update('<div style="background-color:red;color:white">'+view.getRecord(parent).errors[columnDataIndex]+'</div>');
	                    } else {
	                    	tip.update(tip.triggerElement.innerText);
	                    }
                    }
                }
            });
        }),
    onEsc: function(k, e) {
        var window = this.up("window");
        if (!Ext.FocusManager || !Ext.FocusManager.enabled || Ext.FocusManager.focusedCmp === window) {
            e.stopEvent();
            window.close();
            if (window.refer) {
                this.plugins[0].startEditByPosition(this.currentPosition);
            }
        }
    },
    onDel: function(grid) {
        var me = this,
            records = me.getSelectionModel().getSelection();
        Ext.MessageBox.confirm("提示", "是否删除",
            function(btn) {
                if (btn == "yes") {
                    Ext.Array.each(records,
                        function(record) {
                            me.store.remove(record);
                        });
                    me.view.refresh();
                }
            });
    },
    onInsert: function(key, e) {
        var me = this;
        me.doInsert();
    }
});

/** ************************************************************************************** */
/** *******************************####系统常规VType验证###******************************* */
/** ************************************************************************************** */

Ext.apply(Ext.form.VTypes, {
    // 确认密码
    confirmPwd: function(val, field) {
        return val == field.up("form").down("textfield[name=" + field.equals + "]").getValue();
    },
    confirmPwdText: "两次输入的密码不一致!",
    // 最大值
    max: function(val, field) {
        if (Number(val) > Number(field.maxValue)) {
            return true;
        }
        return false;
    },
    maxText: '最大值为{0}',
    // 是否整数
    int: function(val) {
        return /^\d+$/.test(val);
    },
    intText: "请输入整数",
    // 正整数
    positive: function(val) {
    	return /^\d+$/.test(val) && val != 0;
    },
    positiveText: "请输入正整数(>0)",
    // 是否整数
    minus: function(val) {
        return /^(-)?\d+$/.test(val);
    },
    minusText: "请输入数字",
    // 是否小数
    decimal: function(val) {
        return /^\d+(\.\d{1,4})?$/.test(val);
    },
    decimalText: "请输入小数",
    // 是否小数(可为负数)
    negative: function(val) {
        return /^(-)?\d+(\.\d{1,4})?$/.test(val);
    },
    negativeText: "请输入小数",
    // 是否非0小数
    unZeroDecimal: function(val) {
        return /^\d+(\.\d{1,2})?$/.test(val) && val != 0;
    },
    unZeroDecimalText: "请输入非零小数",
    // 是否0到1之间的小数
    zeroPoint: function(val) {
        return /^0\.\d{1,2}$/.test(val);
    },
    zeroPointText: "请输入0~0.99之间的小数",
    time: function(val) {
        return /^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)$/.test(val);
    },
    timeText: "请输入合法的日期",
    xiaoqi: function(val) {
        return /^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)$/.test(val) && new Date(val) > new Date();
    },
    xiaoqiText: "请输入合法的效期"
});

/** ************************************************************************************** */
/** ***********************************####自定义组件###********************************** */
/** ************************************************************************************** */
// 公式生成器
Ext.define('core.append.FormulaMaker', {
	extend: 'Ext.window.Window',
	title: '公式生成',
	width: 330,
	height: 250,
	layout: 'form',
	items: [{
		layout: 'column',
		baseCls: 'x-plain',
		items: [{
			xtype: 'combo',
			fieldLabel: '参考类型',
			name: 'table',
			displayField: 'text',
            valueField: 'value',
			store: Ext.create('Ext.data.Store', {
                fields: ['text', 'value'],
                data: [{
                    'text': '商品',
                    'value': 't_doc_Commodity'
                }]
            })
		}]
	}, {
		layout: 'column',
		baseCls: 'x-plain',
		items: [{
			xtype: 'combo',
			fieldLabel: '参考字段',
			name: 'field',
			displayField: 'text',
            valueField: 'value',
			store: Ext.create('Ext.data.Store', {
                fields: ['text', 'value'],
                data: [{
                    text: '商品分类',
                    value: 'ClassifyCode',
                    triggerParam: 'ClassifyName',
                    trigger: 'core.coreApp.search.CommodityClassify'
                }, {
                    text: '品牌',
                    value: 'BrandCode',
                    triggerParam: 'BrandName',
                    trigger: 'core.coreApp.search.CommodityBrand'
                }, {
                	text: '货号',
                	value: 'commodityNo',
                	trigger: 'core.coreApp.search.Commodity'
                }, {
                	text: '货品名称',
                	value: 'CommodityName',
                	trigger: 'core.coreApp.search.Commodity'
                }, {
                    text: '零售价',
                    value: 'retailPrice'
                }, {
                    text: '花色',
                    value: 'flowerColorCode',
                    trigger: 'core.coreApp.search.flowerColor'
                }, {
                    text: '系列',
                    value: 'series'
                }, {
                    text: '年份',
                    value: 'prodYear'
                }, {
                    text: '季节',
                    value: 'season'
                }]
            })
		}]
	}, {
		layout: 'column',
		baseCls: 'x-plain',
		items: [{
			xtype: 'combo',
			fieldLabel: '操作',
			name: 'oper',
			displayField: 'text',
            valueField: 'value',
			store: Ext.create('Ext.data.Store', {
                fields: ['text', 'value'],
                data: [{
                    'text': '以*开头',
                    'value': '~='
                }, {
                    'text': '以*结尾',
                    'value': '=$'
                }, {
                    'text': '等于',
                    'value': '='
                }, {
                    'text': '大于等于',
                    'value': '>='
                }, {
                    'text': '不等于',
                    'value': '!='
                }, {
                    'text': '小于等于',
                    'value': '<='
                }]
            })
		}]
	}, {
		layout: 'column',
		baseCls: 'x-plain',
		items: [{
			xtype: 'combo',
			fieldLabel: '值',
			name: 'val',
			displayField: 'text',
            valueField: 'value',
			store: Ext.create('Ext.data.Store', {
                fields: ['text', 'value'],
                data: [{
                    'text': '零售价',
                    'value': '{retailPrice}'
                }, {
                    'text': '会员价',
                    'value': '{memberPrice}'
                }]
            }),
            execute: function(field, e) {
            	let top = field.top(),
            		combo =  top.down('combo[name=field]'),
            		fieldName = combo.getValue();
            		selectValue = combo.getStore().findRecord("value", combo.getValue()).getData();
            	if (selectValue != null) {
            		let win = Ext.create(selectValue.trigger).show(),
            			grid = win.down('grid'),
            			data = {};
            		
            		grid.store.proxy.url = '/search';
                    
            		data['xtype'] = win.xtype;
            		data[selectValue.triggerParam || fieldName] = '%'+field.getValue()+ '%';
            		Utils.mergeSystemParam(data);
            		grid.doQuery(data);
            		win.down('button[text^=确定]').on('click', function() {
                        var record = grid.getSingleSelect();
                        field.setValue(record.get(fieldName))
                        win.close();
                    });
            	}
            }
		}]
	}, , {
		layout: 'column',
		baseCls: 'x-plain',
		items: [{
			xtype: 'combo',
			fieldLabel: '连接',
			name: 'join',
			items: ['或','与']
		}]
	}],
	buttons: [{
		text: '确定',
		listeners: {
			click: function(btn) {
				var top = btn.top(),
					table = top.down('combo[name=table]').getValue(),
					field = top.down('combo[name=field]').getValue(),
					oper = top.down('combo[name=oper]').getValue(),
					val = top.down('textfield[name=val]').getValue(),
					join = top.down('combo[name=join]').getValue(),
					result = '';
				result = table+'['+field+oper+val+']';
				short = '['+field+oper+val+']';
				if (top.refer.grid)
					if (top.refer.grid.getSingleSelect().get('condition') == '')
						top.refer.grid.getSingleSelect().set('condition', result); 
					else {
						if (join == '或')
							top.refer.grid.getSingleSelect().set('condition', top.refer.grid.getSingleSelect().get('condition')+ '|' + result);
						else {
							top.refer.grid.getSingleSelect().set('condition', top.refer.grid.getSingleSelect().get('condition') + short);
						}
					}
				else if (top.refer.field) {
					if (top.refer.field.getValue() == '')
						top.refer.field.top().DataFill({condition: result}, top.refer.field);
					else {
						top.refer.field.top().DataFill({condition: top.refer.field.getValue() + "|" + result}, top.refer.field);
					}
				}
				top.close();
			}
		}
	}, {
		text: '关闭',
		listeners: {
			click: function(btn) {
				btn.top().close();
			}
		}
	}]
});





Ext.define('core.append.CommodityInfo', {
	extend: 'Ext.button.Button',
	alias: 'widget.spxx',
	text: '参考信息',
	initComponent: function(){
		var me = this;
		me.on('click', function(btn){
			var clientWidth = $(window).width(),
				clientHeight= $(window).height(),
				record = me.top().down('grid').getSingleSelect();
			
			Ext.create('Ext.window.Window', {
				title: '商品参考信息',
				width: 300,
				height: 300,
				layout: 'fit',
				items: [{
					xtype: 'tabpanel',
					items: [{
						title: '库存信息',
						xtype: 'grid',
						summary: ['qty'],
						fields: [{
							name: 'OrganizationCode' ,
							text: '门店编号'
						}, {
							name: 'OrganizationName' ,
							text: '门店名称'
						}, {
							name: 'qty' ,
							text: '库存数量'
						}]
					}, {
						title: '价格信息',
						layout: 'form',
						items: [{
							layout: 'column',
							baseCls: 'x-plain',
							defaults: {xtype: 'label'},
							items: [{
								text: '上次进价：',
								width: '25%'
							}, {
								text: '0.00',
								width: '25%'
							}]
						}, {
							layout: 'column',
							baseCls: 'x-plain',
							defaults: {xtype: 'label'},
							items: [{
								text: '最低进价：',
								width: '25%'
							}, {
								text: '0.00',
								width: '25%'
							}]
						}, {
							layout: 'column',
							baseCls: 'x-plain',
							defaults: {xtype: 'label'},
							items: [{
								text: '最高进价：',
								width: '25%'
							}, {
								text: '0.00',
								width: '25%'
							}]
						}, {
							layout: 'column',
							baseCls: 'x-plain',
							defaults: {xtype: 'label'},
							items: [{
								text: '平均进价：',
								width: '25%'
							}, {
								text: '0.00',
								width: '25%'
							}]
						}]
						
					}, {
						title: '销售信息',
						xtype: 'grid'
					}, {
						title: '采购信息',
						xtype: 'grid'
					}]
				}]
			}).showAt(clientWidth-310, (clientHeight - 300) / 2);
		});
		me.callParent(arguments);
	}
});

// 审核
Ext.define('core.append.Audit', {
    extend: 'Ext.Component',
    alias: 'widget.audit',
    autoEl: {
        tag: 'div',
        id: 'abcdefg',
        style: 'position:absolute;left:1000px;top: 0px;width: 150px; height: 74px; background: url(/images/audit.png) 0 0 no-repeat; position: absolute;' // /设置样式
    }
});

Ext.define('core.append.LabelChoose', {
	extend: 'Ext.window.Window',
	alias: 'widget.labelChoose',
	width: 550,
	height: 500,
	items: [{
		xtype: 'form',
		layout: 'column',
		items: [{
			xtype: 'textfield',
			name: 'commodityCode',
			hidden: true
		}, {
			width: 235,
			height: 440,
			xtype: 'grid',
			ref: 'zuo',
			exclude: true,
			fields: [{
				name: 'LabelCode',
				text: '标签编号'
			}, {
				name: 'LabelName',
				text: '标签名称'
			}]
		}, {
			width: 60,
			items: [{
				xtype: 'button',
				text: '>>',
				margin: '180 0 0 0',
				listeners: {
					click: function(btn) {
						var top = btn.top(),
							record = top.down('grid[ref=zuo]').getSingleSelect(),
							lGrid = top.down('grid[ref=zuo]');
							rGrid = top.down('grid[ref=t_doc_commodity_label]');
						
							lGrid.store.remove(record);
							rGrid.doInsert(record);
						
					}
				}
			}, {
				xtype: 'button',
				text: '<<',
				margin: '20 0 0 0',
				listeners: {
					click: function(btn) {
						var top = btn.top(),
							record = top.down('grid[ref=t_doc_commodity_label]').getSingleSelect(),
							lGrid = top.down('grid[ref=zuo]');
							rGrid = top.down('grid[ref=t_doc_commodity_label]');
						
							rGrid.store.remove(record);
							lGrid.doInsert(record);
						
					}
				}
			}]
		},{
			width: 235,
			xtype: 'grid',
			height: 440,
			ref: 't_doc_commodity_label',
			fields: [{
				name: 'LabelCode',
				text: '标签编号'
			}, {
				name: 'LabelName',
				text: '标签名称'
			}]
		}]
	}],
	buttons: [{
		text: '确定',
		action: 'submit',
		plan: 'add_commodity_label'
	}, {
		text: '取消',
		listeners: {
			click: function() {
				this.top().close();
			}
		}
	}],
	initComponent: function(){
		var me = this;
		
		me.on('afterrender', function(me) {
			me.down('textfield[name=commodityCode]').setValue(me.commodityCode);
			Utils.ajax('/query', {xtype: 'query_commodityLabel', commodityCode: me.commodityCode}, function(callback) {
				var root = callback.root,
					wxz = [],
					yxz = [];
				
				for (var i = 0; i < root.length; i++) {
					if (root[i]['checked']) {
						yxz.push(root[i]);
					} else {
						wxz.push(root[i]);
					}
				}
				me.down('grid[ref=zuo]').loadData(wxz);
				me.down('grid[ref=t_doc_commodity_label]').loadData(yxz);
			});
		});
		me.callParent(arguments);
	}
});

Ext.define('core.append.ListView', {
	extend: 'Ext.Component',
	alias: 'widget.listview',
	record: false,
	autoEl: {
		tag: 'div',
		style: 'border:1px solid #99bce8;padding: 5px'
	},
	tpl: [
       '<tpl for=".">',
	   		'<div class="listview" sn={[xindex]} style="padding-left:20px">{text}</div>',
	   '</tpl>'
	],
	// 这是选择器
    renderSelectors: {
        // 选择li class为title的表达式，在afterrender的时候可以直接this.titleEl来调用
        active: 'a'
    },
	
    onRender: function() {
    	var me = this;
	        el = me.el,
	        items = el.query('div[class=listview]', false);

	    for (var i = 0; i < items.length; i++) {
	    	me.mon(items[i], 'mouseover', me.onMouseover, this);
	    	me.mon(items[i], 'mouseout', me.onMouseout, this);
	    	me.mon(items[i], 'click', me.onClick, this);
    	}
    
        me.callParent();
    },
    
    getSelect: function(){
    	return this.record;
    },
    
    onClick: function(el) {
    	$(el.target).parent().children().each(function(){
    		$(this).removeClass("choose");
    	});
    	
    	$(el.target).addClass("choose");
    	this.record = this.data[Utils.toInt($(el.target).attr('sn')) -1];
    },
    
    onMouseover: function(el) {
    	$(el.target).addClass("mouseover");
    },
    
    onMouseout: function(el) {
    	$(el.target).removeClass("mouseover");
    },
    initComponent: function() {
    	var me = this;
    	this.callParent(arguments);
    }
});

Ext.define('core.append.Choose', {
	extend: 'Ext.Component',
	alias: 'widget.choose',
	autoEl: {
        tag: 'div',
        style: 'height: 100%'
    },
	tpl: [
		'<div class="container-fluid" style="height:100%">',
			'<div class="row" style="height: 100%">',
				'<div class="col-xs-5" style="height: 100%">',
					'<div class="row pre-scrollable" style="height:100%">',
					'<ul class="list-group uncheckview" style="height:100%">',
						'<tpl for=".">',
							'<tpl if="!checked">',
								'<li class="list-group-item" for="unchecked">{text}</li>',
							'</tpl>',
						'</tpl>',
					'</ul>',
					'</div>',
				'</div>',
				'<div class="col-xs-2 text-center"  style="height: 100%">',
					'<div style="height:100%;line-height: 400px;">',
						'<a role="button" class="btn btn-default" style="margin-top:150px" for="next" aria-label="Left Align">',
							'>>',
						'</a>',
						'<a role="button" class="btn btn-default" style="margin-top:20px" for="prev" aria-label="Left Align">',
							'<<',
						'</a>',
					'</div>',
				'</div>',
				'<div class="col-xs-5"   style="height: 100%">',
					'<div class="row pre-scrollable">',
					'<ul class="list-group checkview">',
						'<tpl for=".">',
							'<tpl if="checked">',
								'<li class="list-group-item" for="unchecked">{text}</li>',
							'</tpl>',
						'</tpl>',
					'</ul>',
					'</div>',
				'</div>',
			'</div>',
		'</div>'
    ],
    
    // 这是选择器
    renderSelectors: {
        // 选择li class为title的表达式，在afterrender的时候可以直接this.titleEl来调用
        next: '.next',
        prev: '.prev',
        checked: '.checked',
        unchecked: '.unchecked',
        uncheckview: '.uncheckview',
        checkview: '.checkview'
    },
    
    onRender: function() {
    	var me = this;
	        el = me.el,
	        next = el.query('a[for=next]', false),
	        prev = el.query('a[for=prev]', false),
	        checkview = el.query('ul[class^=checkview]', false),
	        uncheckview = el.query('ul[class^=uncheckview]', false);

        me.mon(next[0], 'click', me.change, this, 'next');
        me.mon(prev[0], 'click', me.change, this, 'prev');
    
        me.callParent();
    },
    
    change: function(item, oper, separator){
    	var me = this;
    	alert(separator);
    },
    
    initComponent: function() {
    	var me = this;
    	this.callParent(arguments);
    }
});

// grid过滤清空全部按钮
Ext.define('core.append.ClearFilter', {
	extend: 'Ext.Component',
    alias: 'widget.clearFilter',
    hidden: true,
    autoEl: {
        tag: 'div',
        style: 'margin: 5px;'
    },
    
    data: {text: '全部清空', items: []},
    
    tpl: [
        '<tpl if="text">',
		'<a for="clearAll" role="button" class="btn btn-warning btn-xs">{text}</a>',
		'<tpl for="items">',
		'<div class="btn-group" style="margin:5px">',
			'<a role="button" class="btn btn-default btn-xs">{text}：{val}</a>',
			'<a for="clear" class="btn btn-default btn-xs" role="button" class="btn btn-default btn-xs">',
				'x',
			'</a>',
		'</div>',
		'</tpl>',
		'</tpl>'
			
    ],
    
    onRender: function() {
    	var me = this,
	        el = me.el,
	        listeners,
	        clearAll = el.query('a[for=clearAll]', false),
	        btns = el.query('a[for=clear]', false);

	    for (var i = 0; i < btns.length; i++) {
	        me.mon(btns[i], 'click', me.remove, this, i);
	    }
	    
	    me.mon(clearAll[0], 'click', me.removeAll, this);
	    me.callParent();
    },
    
    change: function(item, append, separator){
    	var me = this,
    		items = me.data.items,
    		idx = -1;
    	
    	for (var i = 0; i < items.length; i++) {
    		if (items[i].text == item.text && items[i].oper === item.oper) {
    			items[i]['val']=item.val;
    			idx = i;
    			break;
    		}
    	}
    	
    	return idx;
    },
    
    add: function(item) {
    	var me = this,
    		idx = me.change(item);
    	if (idx === -1) {
    		me.data.items.push(item);
    	} else {
    		me.up('grid').store.clearFilter(idx);
    	}
		me.show();
    	me.update(me.data);
    	me.onRender();
    },
    
    removeAll: function(view) {
    	var me = this;
    	me.data.items = [];
    	me.update(me.data);
    	me.up('grid').store.clearFilter();
    	me.hide();
    },
    remove: function(view, e, idx) {
    	var me = this;
    	me.data.items.splice(idx, 1);
    	me.update(me.data);
    	me.up('grid').store.clearFilter(typeof(idx) == 'number' ? idx : 0);
    	me.onRender();
    	if (me.data.items.length == 0) {
    		me.hide();
    	}
    },
    
    initComponent: function() {
    	var me = this;
    	this.callParent(arguments);
    }
});

// grid条件过滤，显示过滤条件按钮
Ext.define('core.append.FilterButton', {
	extend: 'Ext.Component',
    alias: 'widget.filter',
    autoEl: {
        tag: 'div',
        style: 'margin: 5px'
    },
    tpl: [
        '<div class="btn-group">',
		'<a role="button" class="btn btn-default btn-xs">{text}：{val}</a>',
		'<a for="clear" class="btn btn-default btn-xs" role="button" class="btn btn-default btn-xs">',
			'x',
		'</a>',
		'</div>'
    ],
    
    onRender: function() {
    	var me = this,
	        el = me.el,
	        listeners,
	        btns = el.query('a[for=clear]', false);

	    for (var i = 0; i < btns.length; i++) {
	        me.mon(btns[i], 'click', me.remove, this, i);
	    }
	    me.callParent();
    },
    
    remove: function(view, e, idx) {
    	var me = this;
    	me.ownerCt.remove(me);
    },
    
    initComponent: function() {
    	var me = this;
    	this.callParent(arguments);
    }
});

// 开关按钮
Ext.define('core.append.Switch', {
    extend: 'Ext.Component',
    alias: 'widget.switchBtn',
    autoEl: {
        tag: 'div'
    },

    value: null,

    // 添加一个子El，一个EL就是一个<div>，要和下面的{id}-body对应
    childEls: [
        'switch'
    ],

    renderTpl: [
        '<div id="switch-{id}" class="switch" data-on="danger" data-off="primary">',
        '<input type="checkbox" checked />',
        '</div>'
    ],

    setValue: function(val) {
        var me = this;

        if (val) {
            $(me.el.dom).find('input').bootstrapSwitch('setState', true); // true
																			// ||
																			// false
        } else {
            $(me.el.dom).find('input').bootstrapSwitch('setState', false); // true
																			// ||
																			// false
        }
    },

    getValue: function() {
        return me.value;
    },

    initComponent: function(me) {
        var me = this;

        me.on('afterrender', function() {
            $(me.el.dom).find('input').bootstrapSwitch();

            $(me.el.dom).find('div').on('switch-change', function(e, data) {
                var $el = $(data.el);
                me.value = data.value;
            });
        });
        me.callParent();
    }
});

// tab控件，自定义
Ext.define('core.append.TabPanel', {
    extend: 'Ext.Component',
    alias: 'widget.mytab',
    layout: 'fit',
    height: '100%',
    mixins: [
        'Ext.mixin.Queryable'
    ],

    autoEl: {
        tag: 'div',
        background: 'white;color:black'
    },

    /*
	 * @property {Boolean} isContainer `true` in this class to identify an
	 * object as an instantiated Container, or subclass thereof.
	 */
    isContainer: true,
    
    isXType: function(xtype, shallow) {
        return xtype == 'container';
    },
    
    /**
	 * @protected Used by {@link Ext.ComponentQuery ComponentQuery},
	 *            {@link #child} and {@link #down} to retrieve all of the items
	 *            which can potentially be considered a child of this Container.
	 * 
	 * This may be overriden by Components which have ownership of Components
	 * that are not contained in the {@link #property-items} collection.
	 * 
	 * NOTE: IMPORTANT note for maintainers: Items are returned in tree
	 * traversal order. Each item is appended to the result array followed by
	 * the results of that child's getRefItems call. Floating child items are
	 * appended after internal child items.
	 */
    getRefItems: function(deep) {
        var me = this,
            items = me.items.items,
            len = items ? items.length : 0,
            i = 0,
            item,
            result = [];

        for (; i < len; i++) {
            item = items[i];
            result[result.length] = item;
            if (deep && item.getRefItems) {
                result.push.apply(result, item.getRefItems(true));
            }
        }

        // Append floating items to the list.
        items = me.floatingItems ? me.floatingItems.items : null;
        len = items ? items.length : 0;
        for (i = 0; i < len; i++) {
            item = items[i];
            result[result.length] = item;
            if (deep && item.getRefItems) {
                result.push.apply(result, item.getRefItems(true));
            }
        }

        return result;
    },

    // 添加一个子El，一个EL就是一个<div>，要和下面的{id}-body对应
    childEls: [
        'body',
        'content'
    ],

    renderTpl: [
        '<ul id="{id}-body" class="nav nav-tabs">',
        '<tpl for="items">',
        '<li id="li-{num}-{#}" class="{[xindex === 1 ? "active" : ""]}"><a href="#content-{num}-{#}" data-toggle="tab" >{title}</a></li>',
        '</tpl>',
        '</ul>',
        '<div id="{id}-content" class="tab-content" style="height:100%;width:100%">',
        '<tpl for="items">',
        '<div style="height:100%" role="tabpanel" id="content-{num}-{#}" class="tab-pane {[xindex === 1 ? "active" : ""]}"></div>',
        '</tpl>',
        '</div>'
    ],

    onRender: function() {
        var me = this,
            el = me.el,
            listeners,
            btns = el.query('li', false);

        for (var i = 0; i < btns.length; i++) {
            me.mon(btns[i], 'click', me.onTabChange, this);
        }

        me.callParent();
    },

    onTabChange: function() {
        var me = this,
            idx = me.getActiveIndex() - 1,
            item = me.items.items[idx];

        // 将隐藏的显示出来
        item.show();
        this.fireEvent('tabchange', me, item);
    },

    // 获取当前活动panel的索引
    getActiveTab: function() {
        var me = this,
            idx = me.getActiveIndex();
        return me.items.items[idx - 1];
    },

    // 设置选中的tab
    setActiveItem: function(idx) {
        var me = this;

        if (!idx) return;

        $("#" + me.id + " li:eq(" + (idx - 1) + ") a").tab('show');
    },

    // 获取当前活动panel的索引
    getActiveIndex: function() {
        var me = this,
            activeEl = me.el.down('li[class=active]'),
            index = new Number(activeEl.dom.id.split('-')[2]);
        return index;
    },

    // 这是选择器
    renderSelectors: {
        // 选择li class为title的表达式，在afterrender的时候可以直接this.titleEl来调用
        titleEl: 'div.tab-content',
        content: '.tab-content'
    },

    initItems: function() {
        var me = this,
            items = me.items;
        if (!items || !items.isMixedCollection) {

            me.items = new Ext.util.AbstractMixedCollection(false, me.getComponentId);
            me.floatingItems = new Ext.util.MixedCollection(false, me.getComponentId);

            if (items) {
                if (!Ext.isArray(items)) {
                    items = [items];
                }

                me.add(items);
            }
        }
    },

    add: function() {
        var me = this,
            args = Ext.Array.slice(arguments),
            activeEls = me.el.query('div[role=tabpanel]'),
            index = (typeof args[0] === 'number') ? args.shift() : -1,
            needsLayout = false,
            addingArray, items, i, length, item, data, pos, ret, instanced;
        if (args.length === 1 && Ext.isArray(args[0])) {
            items = args[0];
            addingArray = true;
        } else {
            items = args;
        }

        if (me.rendered) {
            Ext.suspendLayouts(); // suspend layouts while adding items...
        }

        length = items.length;


        if (!addingArray && length === 1) { // an array of 1 should still return
											// an array...
            ret = items[0];
        }

        // loop
        for (i = 0; i < length; i++) {

            data = Utils.copy(items[i]);
            delete data.title;
            Ext.apply(data, {
                renderTo: activeEls[i],
                height: me.getHeight() - 43,
                padding: 5,
                hidden: !(activeEls[i].className.indexOf('active') > -1)
            });
            item = Ext.widget(data.xtype || 'panel', data);
            item.initOwnerCt = me;
            item.ownerCt = me;
            pos = (index < 0) ? me.items.length : (index + i);
            instanced = !!item.instancedCmp;
            delete item.instancedCmp;
            // Floating Components are not added into the items collection, but
			// to a separate floatingItems collection
            if (item.floating) {
                me.floatingItems.add(item);
                delete item.initOwnerCt;
                if (me.hasListeners.add) {
                    me.fireEvent('add', me, item, pos);
                }
            } else {
                me.items.insert(pos, item);
                delete item.initOwnerCt;
                needsLayout = true;
                if (me.hasListeners.add) {
                    me.fireEvent('add', me, item, pos);
                }
            }
        }

        if (me.rendered) {
            Ext.resumeLayouts(true);
        }

        return ret;
    },
    initComponent: function() {
        var me = this;

        // 这里要生成随机数ID
        for (var i = 0; i < me.items.length; i++) {
            me.items[i]['num'] = Utils.GetRandomNum(6);
        }

        Ext.apply(this.renderData, {
            items: me.items
        });

        me.items.items = me.items;
        
        me.on('beforerender', function(me) {
            Utils.delay(me.initItems, 10, me);
        });

        this.callParent();
    }
});

Ext.define('core.append.title', {
    alias: 'widget.pagetitle',
    extend: 'Ext.panel.Panel',
    width: '100%',
    layout: 'column',
    baseCls: 'x-plain',
    setText: function(text) {
        var me = this;
        this.down('label').setText(text);
    },
    getText: function() {
        return this.down('label').text;
    },
    initComponent: function() {
        var me = this;
        me.items = [{
            xtype: 'label',
            width: '100%',
            height: 74,
            text: me.text,
            style: 'font: 26px/1.6 "Microsoft Yahei";text-align: center;text-shadow: 1px 1px 1px rgba(0,0,0,0.2);' // /设置样式
        }];
        me.on('afterrender', function(title) {
            if (title.top().audit && title.top().audit == '已审核') {
                title.add({
                    xtype: 'auditor'
                });
            } 
        });
        me.callParent(arguments);
    }
});


Ext.define('App.ux.UEditor', {
    extend: 'Ext.form.field.TextArea',
    alias: ['widget.ueditor'],
    ueditorConfig: {},
    initComponent: function () {
        var me = this;
        me.callParent(arguments);
    },
    validate: function() {
    	let me = this
    	return me.ue || me.ue.getContent() === '';
    }, 
 // 验证是否可用,和formBind绑定
    isValid: function() {
        return this.allowBlank ? this.allowBlank : this.ue && this.ue.getContent() != '';
    },

    validate: function(value, separator, errors) {
        return this.allowBlank ? this.allowBlank : this.ue && this.ue.getContent() != '';
    },
    afterRender: function () {
        var me = this;
        console.log(me.dom);
        me.callParent(arguments);
        console.log(me.dom);
        if (!me.ue) {
            //编辑器各项参数配置，参考UEditor.config.js
            me.ue = UE.getEditor(me.getInputId(), Ext.apply(me.ueditorConfig, {
                //编辑器高度，可在此处修改，但不要在表单配置中修改，否则滚动条出现后工具栏会消失
                initialFrameHeight:320,
                initialFrameWidth: '100%',
                autoHeightEnabled: false,
                enableAutoSave: false,
                saveInterval:0
            }));
            
            me.ue.ready(function () {
                me.UEditorIsReady = true;
            });
            
            //这块 组件的父容器关闭的时候 需要销毁编辑器 否则第二次渲染的时候会出问题 可根据具体布局调整
            var win = me.up('window');
            if (win && win.closeAction == "hide") {
                win.on('beforehide', function () {
                    me.onDestroy();
                });
            } else {
                var panel = me.up('panel');
                if (panel && panel.closeAction == "hide") {
                    panel.on('beforehide', function () {
                        me.onDestroy();
                    });
                }
            }
        } else {
            me.ue.setContent(me.getValue());
        }
    },
    //返回编辑器实例
    getEditor:function(){
        var me=this;
        console.log(me.dom);
        return UE.getEditor(me.getInputId());
    },
    setValue: function (value) {
        var me = this;
        if (!me.ue) {
            me.setRawValue(me.valueToRaw(value));
        } else {
            me.ue.ready(function () {
                me.ue.setContent(value);
            });
        }
        console.log(me.dom);
        me.callParent(arguments);
        return me.mixins.field.setValue.call(me, value);
    },
    getValue: function () {
        var me = this;
        console.log(me.dom);
        return me.getContent();
    },
    getRawValue: function () {
        var me = this;
        if (me.UEditorIsReady) {
            me.ue.sync(me.getInputId());
        }
        console.log(me.dom);
        v = (me.inputEl ? me.inputEl.getValue() : me.rawValue);
        me.rawValue = v;
        return v;
    },
    destroyUEditor: function () {
        var me = this;
        console.log(me.dom);
        if (me.rendered) {
            try {
                me.ue.destroy();
                var dom = document.getElementById(me.id);
                if (dom) {
                    dom.parentNode.removeChild(dom);
                }
                me.ue = null;
            } catch (e) { }
        }
    },
    onDestroy: function () {
        var me = this;
        me.callParent();
        me.destroyUEditor();
    }
});

// Grid头部过滤器
// 说明: 在grid的头部中加入过滤器
Ext.define("core.plugins.HeaderFilter", {
    extend: "Ext.AbstractPlugin",
    alias: "plugin.headerfilter",

    requires: ['Ext.grid.filters.filter.*'],

    /**
	 * @cfg {Boolean} showMenu Defaults to true, including a filter submenu in
	 *      the default header menu.
	 */
    showMenu: true,

    init: function(grid) {
        var me = this,
            store, headerCt;

        me.grid = grid;

        store = grid.store;
        headerCt = grid.headerCt;

        headerCt.on({
            scope: me,
            headertriggerclick: me.headertriggerclick
        });
    },

    headertriggerclick: function(ct, column, e, t, eOpts) {
        var me = this,
            menu = ct.getMenu(),
            grid = column.up('grid'),
            group = me.getColumnRecordsGroup(column),
            model = grid.store.getModel(),
            field = model && model.getField(column.dataIndex),
            locked;
        len = group;

        menu.removeAll();

        menu.add({
            text: '全部',
            handler: function(me) {
                (function(header, dataIndex, text) {
                    header.doFilter(dataIndex, text);
                })(ct, column.dataIndex, me.text);
            }
        });
        menu.add({
            text: '(前10个)',
            handler: function(me) {
                (function(header, dataIndex, text) {
                    header.doFilter(dataIndex, text);
                })(ct, column.dataIndex, me.text);
            }
        });
        menu.add({
            text: '高级',
            handler: function(me) {
                (function(header, dataIndex, text, type) {
                    header.doFilter(dataIndex, text, type);
                })(ct, column.dataIndex, me.text, field.type);
            }
        });
        menu.add('-');
        /*
		 * if (column.locked) { locked = '取消冻结窗口' } else { locked = '冻结窗口' }
		 * menu.add({ text: locked, handler: function(me) { (function(header,
		 * grid, column, dataIndex, text, type) { grid.lock(column, dataIndex);
		 * })(ct, grid, column, column.dataIndex, me.text, field.type); } });
		 * menu.add('-');
		 */
        for (var i = 0; i < (group.length > 50 ? 50 : group.length); i++) {
            menu.add({
                text: group[i],
                handler: function(me) {
                    (function(header, dataIndex, text) {
                        header.doFilter(dataIndex, text);
                    })(ct, column.dataIndex, me.text);
                }
            });
        }
    },

    getColumnRecordsGroup: function(column) {
        var me = this,
            records = me.grid.store.getRange(0),
            group = column.group || [],
            len = records.length,
            dataIndex = column.dataIndex,
            val;

        for (var i = 0; i < len; i++) {
            val = records[i].get(dataIndex);

            if (group.indexOf(val) == -1) {
                group.push(val);
            }
        }

        column.group = group;
        return column.group;
    }
});

/*
 * 带回车进入下个CELL的单元格编辑器
 */
Ext.define("core.plugins.CellEditing", {
    extend: "Ext.grid.plugin.CellEditing",
    alias: "plugin.mycellediting",
    showEditor: function(ed, context, value) {
        var me = this,
            record = context.record,
            view = context.view,
            columnHeader = context.column,
            sm = view.getSelectionModel(),
            selectMode;

        if (columnHeader.editable && !columnHeader.editable(me, context)) {
            return;
        }

        // Context is for another view.
        // This can happen in a lockable grid where there are two grids, each
        // with a separate Editing plugin
        if (!columnHeader.up(me.view.ownerCt)) {
            return me.lockingPartner.showEditor(ed, me.lockingPartner.getEditingContext(record, columnHeader), value);
        }

        me.setEditingContext(context);
        me.setActiveEditor(ed);
        me.setActiveRecord(record);
        me.setActiveColumn(columnHeader);

        selectMode = sm.getSelectionMode();

        // We focus the cell before beginning the edit.
        // In 99% of cases, this will be a no-op because editing will have been
        // triggered
        // by ENTER when a cell is focused, or by clicking a cell which will
        // focus it.
        // But programmatic startEdit calls MUST first focus the Panel,
        // otherwise,
        // the focusenter event caused by focusing the editor field will attempt
        // to delegate focus to a descendant cell, and that will terminate
        // editing.
        // Keep existing records (see EXTJSIV-7897)!
        if (!sm.isCellSelected(view, record, columnHeader) && (selectMode !== 'MULTI' || !sm.getSelection().length || (sm.getSelection().length === 1 && sm.isSelected(record)))) {
            sm.selectByPosition({
                    row: record,
                    column: columnHeader,
                    view: view
                },
                selectMode === 'MULTI');
        }

        // We must ensure the lastFocused is consistent in the View.
        if (!view.cellFocused) {
            view.getNavigationModel().setPosition(context, null, null, null, true);
        }

        // The lastFocused position will be set above,
        // Now we must temporairily clear the current focus position during the
        // edit.
        // Otherwise a refresh during edit will kill the editor by restorinhg
        // focus.
        view.getNavigationModel().setPosition();
        ed.startEdit(view.getCell(record, columnHeader), value, context);

        me.editing = true;
        me.scroll = view.el.getScroll();
    },
    onEditComplete: function(ed, value, startValue, originalValue) {
        var me = this,
            activeColumn = me.getActiveColumn(),
            context = me.context,
            view,
            record;

        if (activeColumn) {
            view = context.view;
            record = context.record;

            me.setActiveEditor(null);
            me.setActiveColumn(null);
            me.setActiveRecord(null);

            context.value = value;
            context.originalvalue = originalValue;
            if (!me.validateEdit()) {
                me.editing = false;
                return;
            }

            // Only update the record if the new value is different than the
            // startValue. When the view refreshes its el will gain focus
            if (!record.isEqual(value, startValue)) {
                record.set(activeColumn.dataIndex, value);
                // Changing the record may impact the position
                context.rowIdx = view.indexOf(record);
            }

            me.fireEvent('edit', me, context);
            me.editing = false;
        }
    },
    /**
	 * @private Shows the grid cell inner element when a cell editor is hidden
	 */
    onHide: function() {
        var me = this,
            context = me.context,
            focusContext;

        me.restoreCell();

        if (context && me.el.contains(Ext.Element.getActiveElement())) {
            focusContext = context.clone();

            // If, after hiding, focus has not been programatically moved to
            // another target
            // (eg, we are in a TAB event listener, and move to edit the next
            // cell)
            // then revert focus back to the corresponding grid cell.
            Ext.on({
                idle: function() {
                    if (Ext.Element.getActiveElement() === document.body) {
                        context.view.getNavigationModel().setPosition(focusContext, null, null, null, true);
                    }
                },
                single: true
            });
            me.context = null;
        }
        me.callParent(arguments);
    },
    // @private
    initEditTriggers: function() {
        var me = this,
            view = me.view;
        // Listen for the edit trigger event.
        if (me.triggerEvent === "cellfocus") {
            me.mon(view, "cellfocus", me.onCellFocus, me);
        } else if (me.triggerEvent === "rowfocus") {
            me.mon(view, "rowfocus", me.onRowFocus, me);
        } else {
            // Prevent the View from processing when the SelectionModel focuses.
            // This is because the SelectionModel processes the mousedown event,
            // and
            // focusing causes a scroll which means that the subsequent mouseup
            // might
            // take place at a different document XY position, and will
            // therefore
            // not trigger a click.
            // This Editor must call the View's focusCell method directly when
            // we recieve a request to edit
            if (view.getSelectionModel().isCellModel) {
                view.onCellFocus = me.beforeViewCellFocus.bind(me);
            }
            // Listen for whichever click event we are configured to use
            me.mon(view, me.triggerEvent || "cell" + (me.clicksToEdit === 1 ? "click" : "dblclick"), me.onCellClick, me);
        }
        // add/remove header event listeners need to be added immediately
        // because
        // columns can be added/removed before render
        me.initAddRemoveHeaderEvents();
        // Attach new bindings to the View's NavigationModel which processes
        // cellkeydown events.
        me.view.getNavigationModel().addKeyBindings({
            enter: me.onEnterKey,
            esc: me.onEscKey,
            scope: me
        });
    },
    // @private Used if we are triggered by a cellclick event
    // *IMPORTANT* Due to V4.0.0 history, the colIdx here is the index within
    // ALL columns, including hidden.
    onCellClick: function(view, cell, colIdx, record, row, rowIdx, e) {
        // Make sure that the column has an editor. In the case of
        // CheckboxModel,
        // calling startEdit doesn't make sense when the checkbox is clicked.
        // Also, cancel editing if the element that was clicked was a tree
        // expander.
        var expanderSelector = view.expanderSelector,
            // Use getColumnManager() in this context because colIdx includes
            // hidden columns.
            columnHeader = view.ownerCt.getColumnManager().getHeaderAtIndex(colIdx),
            editor = columnHeader.getEditor(record);
        // 如果clicksToEdit被改为了-1则说明不让开启编辑状态
        if (this.shouldStartEdit(editor) && this.clicksToEdit != -1 && (!expanderSelector || !e.getTarget(expanderSelector))) {
            this.startEdit(record, columnHeader);
        }
    },
    onSpecialKey: function(ed, field, e) {
        var me = this,
            grid = field.up('grid');

        // 点了后退键,解所first事件
        if (e.getKey() === e.ENTER || e.getKey() === e.TAB) {
            this.event = e;

            if (grid.plugins[0].context.column.formula && field.id === grid.plugins[0].context.column.formula.id && e.getKey() === e.ENTER) {
                grid.clearFormula(field);
            }
        }

        /*
		 * if (grid && grid.plugins[0].context.column.formula && field.id ===
		 * grid.plugins[0].context.column.formula.id && e.getKey() === e.ENTER) {
		 * grid.plugins[0].clicksToEdit = 1;
		 * grid.plugins[0].context.column.formula = null;
		 * console.log(field.up('editor').editing); }
		 */
    },

    next: function(e) {
        var me = this;

        if (!Utils.isEmpty(this.event) && (this.event.getKey() === this.event.ENTER || this.event.getKey() === this.event.TAB)) {
            this.event.stopEvent();
            var editingPlugin = this,
                grid = this.getCmp(),
                sm = grid.getSelectionModel(),
                view = sm.views[0],
                direction = "right",
                gridCols = view.headerCt.getGridColumns(),
                position = grid.currentPosition || {
                    row: 0,
                    column: 0
                },
                store;
            if (grid.store.getRange().length == 0) {
                grid.doInsert();
            }
            if (Utils.isEmpty(this.context) || Utils.isEmpty(this.context.column.to)) {
                do {
                    position = view.walkCells(position, direction, e, sm.preventWrap);
                } while (position && !view.headerCt.getHeaderAtIndex(position.colIdx).getEditor());
            } else {
                // store = view.store;
                for (var i = 0; i < gridCols.length; i++) {
                    if (gridCols[i].dataIndex === this.context.column.to) {
                        position = {
                            row: this.context.rowIdx,
                            column: i
                        };
                    }
                }
                if (position == null) {
                    Ext.MessageBox.alert("提示", "没有找到继续的列");
                }
            }
            if ((Utils.isEmpty(position) || !position) && this.getCmp().allowInsert) {
                grid.doInsert();
                position = {
                    row: grid.store.count() - 1,
                    column: 0
                };
                do {
                    position = view.walkCells(position, direction, e, sm.preventWrap);
                } while (position && !view.headerCt.getHeaderAtIndex(position.colIdx).getEditor());
            }

            if (position && position.rowIdx && !Utils.isEmpty(grid.store.getAt(position.rowIdx).get('break'))) {
                var store = view.store,
                    count = store.count();
                if (position.rowIdx + 1 >= count) {
                    model = Ext.create(store.model);
                    grid.store.insert(count, model);
                }
                position.rowIdx = position.row = position.rowIdx + 1;
                position.record = grid.store.getAt(position.rowIdx);
            }

            var column = typeof position.column === 'number' ? position.column : position.colIdx,
                row = typeof position.row === 'number' ? position.row : position.rowIdx,
                columns = view.ownerCt.getVisibleColumnManager();

            if (!Utils.isEmpty(columns.columns[column]) && ((columns.columns[column].jumpable && columns.columns[column].jumpable(me, (this.context || {
                record: grid.getSingleSelect(),
                grid: grid
            }), e)) || (columns.columns[column].editable && !columns.columns[column].editable(me, this.context, e)))) {
                grid.currentPosition = {
                    row: row,
                    column: columns.columns[column].to ? column + 1 : column
                };
                if (columns.columns[column].to) {
                    columns.columns[column].to = null;
                }
                me.next(e);
                return;
            }
            if (position) this.startEditByPosition(position);
            this.event = null;
        }
    },
    listeners: {
        edit: function(editor, context, e) {
            var cell = new Ext.grid.CellContext(context.grid.getView()),
                execute = context.column.execute;
            cell.setPosition(context.rowIdx, context.colIdx);
            context.grid.getView().lastFocused = cell;

            if (context.value == '' && !context.column.allowEmpty) return;

            // 计算公式
            if (context.value.substring(0, 1) === '=' && context.value.length > 1) {
                context.column.formu = context.value;
                var result = context.grid.loadSplitFormulaColumns(context.value);
                for (var i = 0; i < result.length; i++) {
                    context.value = context.value.replace(new RegExp(':' + result[i]['text'] + '\\[' + result[i].rowIndex + '\\]', 'g'), context.grid.loadPositionVal(result[i], context));
                }

                context.record.set(context.column.dataIndex, eval(context.value.substring(1, context.value.length)));
            }

            if (Utils.isEmpty(execute) || context.column.execute(editor, context, e)) {
                this.next(e);
            }
        }
    }
});


/**
 * GRID右键相应事件
 * 
 * @param {Object}
 *            view
 */
Ext.define("core.plugins.RightMenu", {
    extend: "Ext.AbstractPlugin",
    alias: "plugin.rightMenu",
    init: function(view) {
        var me = view,
            self = this,
            grid = view,
            rclickcollection;


        me.on("cellcontextmenu", function(view, td, cellIndex, record, tr, index, event, options) {
                var toptip = view.top(),
                    rclickcollection = grid.rclickcollection || [];

                event.preventDefault();
                event.stopEvent();

                if (rclickcollection.length > 0) {
                    var menu = Ext.create("Ext.menu.Menu");
                    if (rclickcollection && rclickcollection.length > 0) {
                        for (var i = 0; i < rclickcollection.length; i++) {
                            (function(rclickcollection) {
                                menu.add({
                                    text: rclickcollection.text,
                                    glyph: rclickcollection.glyph,
                                    listeners: {
                                        click: function(btn) {
                                            rclickcollection.fireEvent('click', rclickcollection);
                                        }
                                    }
                                });
                            })(rclickcollection[i]);
                        }
                    }
                    menu.showAt(event.getXY());
                    return;
                }

                var menu = view.contextmenu || Ext.create("Ext.menu.Menu", {
                    width: 100,
                    frame: false,
                    margin: "0 0 10 0",
                    items: [{
                            text: "新增",
                            iconCls: 'iconnz icon-add',
                            handler: function(me) {
                                var me = this;

                                if (!grid.allowInsert) {
                                    Ext.MessageBox.alert("提示", '不允许添加行!');
                                    return;
                                }
                                grid.doInsert();
                                /*
								 * count = grid.store.getCount(), model =
								 * Ext.create(grid.store.model), stroe =
								 * grid.store, sm = grid.getSelectionModel(),
								 * view = sm.views[0], direction =
								 * event.shiftKey ? "left" : "right";
								 * model.set("sort", stroe.count());
								 * grid.store.insert(stroe.count(), model); var
								 * position = { row: stroe.count() - 1, column:
								 * 0 }; do { position = view.walkCells(position,
								 * direction, event, sm.preventWrap); } while
								 * (position &&
								 * !view.headerCt.getHeaderAtIndex(position.colIdx).getEditor());
								 * if (position) {
								 * grid.plugins[0].startEditByPosition(position); }
								 */
                            }
                        }, {
                            text: "当前位置新增",
                            iconCls: 'iconnz icon-add',
                            handler: function(me) {
                                var me = this,
                                    grid = view.up("grid");

                                if (!grid.allowInsert) {
                                    Ext.MessageBox.alert("提示", '不允许添加行!');
                                    return;
                                }
                                var grid = view.up("grid"),
                                    record = grid.getSingleSelect(),
                                    sm = grid.getSelectionModel(),
                                    idx = grid.store.indexOf(record),
                                    direction = event.shiftKey ? "left" : "right",
                                    newModel = Ext.create(grid.store.model);

                                newModel.set("sort", idx + 1);
                                if (newModel.get("shl")) {
                                    newModel.set("shl", 1);
                                }
                                grid.store.insert(idx + 1, newModel);

                                var position = {
                                    row: idx + 1,
                                    column: 0
                                };
                                do {
                                    position = view.walkCells(position, direction, event, sm.preventWrap);
                                } while (position && !view.headerCt.getHeaderAtIndex(position.colIdx).getEditor());
                                if (position) {
                                    grid.plugins[0].startEditByPosition(position);
                                }
                            }
                        }, {
                            text: "复制新增",
                            iconCls: 'iconnz icon-add',
                            handler: function(me) {
                                var me = this,
                                    record = view.up("grid").getSingleSelect(),
                                    grid = view.up("grid");

                                if (!grid.allowInsert) {
                                    Ext.MessageBox.alert("提示", '不允许添加行!');
                                    return;
                                }

                                count = grid.store.getCount(),
                                    stroe = grid.store,
                                    sm = grid.getSelectionModel(),
                                    view = sm.views[0],
                                    direction = event.shiftKey ? "left" : "right";
                                var newModel = record.copy("newModel" + (count + 1));
                                newModel.set("sort", stroe.count());
                                if (newModel.get("shl")) {
                                    newModel.set("shl", 1);
                                }
                                grid.store.insert(stroe.count(), newModel);
                                var position = {
                                    row: stroe.count() - 1,
                                    column: 0
                                };
                                do {
                                    position = view.walkCells(position, direction, event, sm.preventWrap);
                                } while (position && !view.headerCt.getHeaderAtIndex(position.colIdx).getEditor());
                                if (position) {
                                    grid.plugins[0].startEditByPosition(position);
                                }
                                // me.up("menu").close();
                            }
                        },
                        "-", {
                            text: "删除",
                            iconCls: 'iconnz icon-quxiao',
                            handler: function(me) {
                                var record = view.up("grid").getSingleSelect(),
                                    rowIndex = view.dataSource.indexOf(record);
                                Ext.MessageBox.confirm("提示", "是否删除",
                                    function(btn) {
                                        if (btn == "yes") {
                                            var grid = view.up("grid");

                                            grid.store.remove(record);
                                            view.refresh();
                                            grid.fireEvent('dataRemove', view.up("grid"), record, rowIndex);
                                            grid.setFocus(rowIndex);
                                        }
                                    });
                            }
                        }, {
                            text: "清空",
                            iconCls: "iconnz icon-delete",
                            handler: function(me) {
                                var store = grid.store;
                                store.removeAll();
                                var newModel = Ext.create(grid.store.model);
                                newModel.set("sort", store.count());
                                if (newModel.get("shl")) {
                                    newModel.set("shl", 1);
                                }
                                store.insert(store.count(), newModel);

                            }
                        },
                        '-', {
                            text: "显示全部",
                            iconCls: 'iconnz icon-detail',
                            handler: function(btn) {
                                me.store.clearFilter();
                            }
                        }, {
                            text: "记录查找",
                            iconCls: 'iconnz icon-search',
                            handler: function(me) {
                                var me = view.up("grid"),
                                    columns = me.columns,
                                    records = me.store.getRange(),
                                    field = '',
                                    oper = '',
                                    val = '',
                                    fields = me.store.getProxy().getModel().getFields();
                                var window = Ext.create("Ext.window.Window", {
                                    title: "记录查找",
                                    width: 500,
                                    autoScroll: true,
                                    height: 400,
                                    buttonAlign: "center",
                                    layout: "fit",
                                    items: [{
                                        xtype: "form",
                                        frame: true,
                                        autoScroll: true
                                    }],
                                    confirmRecordIsNeed: function(query, record) {
                                        var isRight = true,
                                            fields = query.split("_"),
                                            field = fields[0],
                                            oper = Utils.toInt(fields[1]),
                                            val = '';

                                        for (var i = 2; i < fields.length; i++) {
                                            if (i == 2) val = val + fields[i];
                                            else val = val + '_' + fields[i];
                                        }
                                        switch (oper) {
                                            case 1:
                                                isRight = record.get(field).trim() == val;
                                                break;

                                            case 2:
                                                isRight = record.get(field).indexOf(val) != -1;
                                                break;

                                            case 3:
                                                isRight = record.get(field) >= val;
                                                break;

                                            case 4:
                                                isRight = record.get(field) <= val;
                                                break;

                                            case 5:
                                                isRight = record.get(field) != val;
                                                break;
                                        }
                                        return isRight;
                                    },
                                    buttons: [{
                                        text: "确定",
                                        handler: function(btn) {
                                            var itmes = window.down("form").getFormItems(),
                                                query = new Array(),
                                                records = me.store.getRange();
                                            // 放入要查询的FIELD值
                                            for (var i = 0; i < itmes.length; i++) {
                                                if (itmes[i].ref.indexOf("_tj") != -1 && itmes[i].getValue() != null && itmes[i].getValue() != '' && itmes[i].getValue() != 0) {
                                                    field = itmes[i].ref.split("_")[0];
                                                    query.push(field + "_" + itmes[i].getValue() + "_" + window.down("form").down("textfield[ref=" + field + "_val" + "]").getValue());
                                                }
                                            }
                                            var line = me.store.findBy(function(record, id) {
                                                var isRight = true;
                                                for (var i = 0; i < query.length; i++) {
                                                    if (!btn.up("window").confirmRecordIsNeed(query[i], record)) {
                                                        isRight = false;
                                                    }
                                                }
                                                return isRight;
                                            });
                                            if (line >= 0) {
                                                me.getSelectionModel().select(line);
                                                me.getView().focusRow(line);
                                                btn.up("window").close();
                                            }
                                        }
                                    }],
                                    listeners: {
                                        afterrender: function(me) {
                                            for (var i = 0; i < fields.length; i++) {
                                                for (var j = 0; j < columns.length; j++) {
                                                    if (fields[i].name == columns[j].dataIndex) me.down("form").add(Ext.create("Ext.panel.Panel", {
                                                        layout: "column",
                                                        items: [new Ext.create("Ext.form.ComboBox", {
                                                            fieldLabel: columns[j].text,
                                                            labelWidth: 60,
                                                            width: 200,
                                                            ref: columns[j].dataIndex + "_tj",
                                                            labelAlign: "right",
                                                            store: Ext.create("Ext.data.Store", {
                                                                fields: ["text", "value"],
                                                                data: [{
                                                                    text: '',
                                                                    value: "0"
                                                                }, {
                                                                    text: "等于",
                                                                    value: "1"
                                                                }, {
                                                                    text: "模糊",
                                                                    value: "2"
                                                                }, {
                                                                    text: "大于等于",
                                                                    value: "3"
                                                                }, {
                                                                    text: "小于等于",
                                                                    value: "4"
                                                                }, {
                                                                    text: "不等于",
                                                                    value: "5"
                                                                }]
                                                            }),
                                                            displayField: "text",
                                                            valueField: "value"
                                                        }), Ext.create("Ext.form.field.Text", {
                                                            width: 200,
                                                            ref: columns[j].dataIndex + "_val"
                                                        })]
                                                    }));
                                                }
                                            }
                                        }
                                    }
                                });
                                window.show();
                            }
                        }, {
                            text: "记录过滤",
                            iconCls: 'iconnz icon-filter',
                            handler: function(btn) {
                                var me = view.up("grid"),
                                    columns = me.columns,
                                    records = me.store.getRange(),
                                    field = '',
                                    oper = '',
                                    val = '',
                                    fields = me.store.getProxy().getModel().getFields();
                                var window = Ext.create("Ext.window.Window", {
                                    title: "记录过滤",
                                    width: 500,
                                    height: 400,
                                    autoScroll: true,
                                    bodyStyle: "overflow-x:hidden;",
                                    buttonAlign: "center",
                                    layout: "fit",
                                    items: [{
                                        xtype: "form",
                                        autoScroll: true,
                                        frame: true
                                    }],
                                    confirmRecordIsNeed: function(query, record) {
                                        var isRight = true,
                                            field = query.split("_")[0],
                                            oper = Utils.toInt(query.split("_")[1]),
                                            val = query.split("_")[2];
                                        switch (oper) {
	                                        case 1:
	                                            isRight = record.get(field).trim() == val;
	                                            break;
	
	                                        case 2:
	                                            isRight = record.get(field).indexOf(val) != -1;
	                                            break;
	
	                                        case 3:
	                                            isRight = record.get(field) >= val;
	                                            break;
	
	                                        case 4:
	                                            isRight = record.get(field) <= val;
	                                            break;
	
	                                        case 5:
	                                            isRight = record.get(field) != val;
	                                            break;
                                        }
                                        return isRight;
                                    },
                                    buttons: [{
                                        text: "确定",
                                        handler: function(btn) {
                                            var itmes = window.down("form").getFormItems(),
                                                query = new Array(),
                                                records = me.store.getRange();
                                            // 放入要查询的FIELD值
                                            for (var i = 0; i < itmes.length; i++) {
                                                if (itmes[i].ref.indexOf("_tj") != -1 && itmes[i].getValue() != null && itmes[i].getValue() != '' && itmes[i].getValue() != 0) {
                                                    field = itmes[i].ref.split("_")[0];
                                                    query.push(field + "_" + itmes[i].getValue() + "_" + window.down("form").down("textfield[ref=" + field + "_val" + "]").getValue());
                                                }
                                            }
                                            
                                            var dataIndex,oper,val,description;
                                            
                                            for (var i = 0; i < query.length; i++) {
	                                            dataIndex = query[i].split("_")[0],
	                                            oper = Utils.toInt(query[i].split("_")[1]),
	                                            val = query[i].split("_")[2];
	                                            switch (oper) {
			                                        case 1:
			                                        	oper="=";
			                                        	description="等于";
			                                            break;
			
			                                        case 2:
			                                        	oper="like";
			                                        	description="匹配";
			                                            break;
			
			                                        case 3:
			                                        	oper=">=";
			                                        	description="大于等于";
			                                            break;
			
			                                        case 4:
			                                        	oper="<=";
			                                        	description="小于等于";
			                                            break;
			
			                                        case 5:
			                                        	oper="<>";
			                                        	description="不等于";
			                                            break;
		                                        }
	                                            
	                                            me.down('clearFilter').add({
	                                            	text: me.getColumn(dataIndex).text,
	                                            	oper: oper,
	                                            	val: description+val
	                                            });
                                            }
                                            
                                            var isRight = true
                                            for (var i = 0; i < query.length; i++) {
	                                            me.store.filterBy(function(record, id) {
	                                            	isRight = true;
                                                    if (!Utils.isEmpty(btn.up("window")) && !btn.up("window").confirmRecordIsNeed(query[i], record)) {
                                                        isRight = false;
                                                    }
	                                                return isRight;
	                                            });
                                            }
                                            
                                            btn.up("window").close();
                                        }
                                    }],
                                    listeners: {
                                        afterrender: function(me) {
                                            for (var i = 0; i < fields.length; i++) {
                                                for (var j = 0; j < columns.length; j++) {
                                                    if (fields[i].name == columns[j].dataIndex) me.down("form").add(Ext.create("Ext.panel.Panel", {
                                                        layout: "column",
                                                        items: [new Ext.create("Ext.form.ComboBox", {
                                                            fieldLabel: columns[j].text,
                                                            labelWidth: 60,
                                                            width: 200,
                                                            ref: columns[j].dataIndex + "_tj",
                                                            labelAlign: "right",
                                                            store: Ext.create("Ext.data.Store", {
                                                                fields: ["text", "value"],
                                                                data: [{
                                                                    text: '',
                                                                    value: "0"
                                                                }, {
                                                                    text: "等于",
                                                                    value: "1"
                                                                }, {
                                                                    text: "模糊",
                                                                    value: "2"
                                                                }, {
                                                                    text: "大于等于",
                                                                    value: "3"
                                                                }, {
                                                                    text: "小于等于",
                                                                    value: "4"
                                                                }, {
                                                                    text: "不等于",
                                                                    value: "5"
                                                                }]
                                                            }),
                                                            displayField: "text",
                                                            valueField: "value"
                                                        }), Ext.create("Ext.form.field.Text", {
                                                            width: 200,
                                                            ref: columns[j].dataIndex + "_val"
                                                        })]
                                                    }));
                                                }
                                            }
                                        }
                                    }
                                });
                                window.show();
                            }
                        }, {
                            text: "记录汇总",
                            iconCls: 'iconnz icon-summary',
                            handler: function(btn) {
                            	var me = view.up("grid"),
                                allColumns = me.columns,
                                columns = new Array(),
                                records = me.store.getRange(),
                                fields = allColumns;
                            // 判断是不是有分组
                            for (var i = 0; i < allColumns.length; i++) {
                                if (Utils.isEmpty(allColumns[i].items) || allColumns[i].items.items.length == 0) {
                                    columns.push(allColumns[i]);
                                } else {
                                    for (var j = 0; j < allColumns[i].items.items.length; j++) {
                                        allColumns[i].items.items[j].text = allColumns[i].items.items[j].text.indexOf("(") != -1 ? allColumns[i].items.items[j].text : allColumns[i].items.items[j].text + "(" + allColumns[i].text + ")";
                                        columns.push(allColumns[i].items.items[j]);
                                    }
                                }
                            }
                            var items = "[",
                                items2 = "[";
                            for (var i = 0; i < columns.length; i++) {
                                if (columns[i].xtype != 'rownumberer' && (columns[i].type == "string") ) {
                                   items = items + ',{"xtype": "checkbox", "name": "' + columns[i].dataIndex + '", "boxLabel": "' + columns[i].text + '"}';
                                } else if(columns[i].xtype == 'gridcolumn'){
                                   items2 = items2 + ',{"xtype": "checkbox", "name": "' + columns[i].dataIndex + '", "boxLabel": "' + columns[i].text + '"}';
                                }
                            }
                            items = items + "]";
                            items2 = items2 + "]";
                            items = Utils.strToJson(items.replace("[,", "["));
                            items2 = Utils.strToJson(items2.replace("[,", "["));
                            var window = Ext.create("Ext.window.Window", {
                                title: "记录汇总",
                                width: 410,
                                height: 500,
                                layout: "border",
                                buttonAlign: "center",
                                buttons: [{
                                    xtype: "checkbox",
                                    boxLabel: "统计条目数",
                                    name: "topping"
                                }, {
                                    text: "确定",
                                    handler: function(me) {
                                        // 获取FIELDs
                                        var flFields = me.up("window").down("form[ref=flxz]").getFormItems(),
                                            hzFields = me.up("window").down("form[ref=hzxz]").getFormItems(),
                                            pjFields = me.up("window").down("form[ref=pjxz]").getFormItems(),
                                            isTjtm = me.up("window").down("checkbox[name=topping]").getValue();

                                        var showResult = Utils.calculationTypeAndSummary(fields, columns, records, flFields, hzFields, pjFields);

                                        me.up("window").close();
                                        if (!isTjtm) {
                                            // 生成WINDOW显示
                                            Ext.create("Ext.window.Window", {
                                                title: "数据分类统计",
                                                width: 600,
                                                height: 400,
                                                layout: "fit",
                                                items: [{
                                                    xtype: "grid",
                                                    enableKeyNav: true,
                                                    // 可以使用键盘控制上下
                                                    multiSelect: true,
                                                    // 多选
                                                    columnLines: true,
                                                    // 展示竖线
                                                    rowLines: true,
                                                    autoScroll: true,
                                                    isexport: false,
                                                    // 是否导出，默认为false,为true则增加导出按妞
                                                    isbbar: false,
                                                    plugins: [Ext.create("core.plugins.RightMenu")],
                                                    loadMask: true,
                                                    allowInsert: true,
                                                    // 是否可以CellEditor自动加行
                                                    selType: "cellmodel",
                                                    store: Ext.create("Ext.data.Store", {
                                                        fields: showResult['field'],
                                                        data: Utils.strToJson(showResult['data']),
                                                        proxy: {
                                                            type: "memory",
                                                            reader: {
                                                                type: "json",
                                                                root: "items"
                                                            }
                                                        }
                                                    }),
                                                    columns: showResult['column']
                                                }]
                                            }).show();
                                        } else {
                                            var data = Utils.strToJson(showResult.data);
                                            showResult['column'].add({"dataIndex": "linesCount", "text": "条目数", "type": "int"});
                                            var win = Ext.create("Ext.window.Window", {
                                                    title: "数据分类统计",
                                                    width: 600,
                                                    height: 400,
                                                    layout: "fit",
                                                    items: [{
                                                        xtype: "grid",
                                                        store: Ext.create("Ext.data.Store", {
                                                            fields: showResult['field'],
                                                            data: Utils.strToJson(showResult['data']),
                                                            proxy: {
                                                                type: "memory",
                                                                reader: {
                                                                    type: "json",
                                                                    root: "items"
                                                                }
                                                            }
                                                        }),
                                                        columns: showResult['column']
                                                    }]
                                                }).show();
                                        }
                                    }
                                }, {
                                    text: "取消",
                                    handler: function(me) {
                                        me.up("window").close();
                                    }
                                }],
                                items: [{
                                    xtype: "form",
                                    width: 130,
                                    region: "west",
                                    autoScroll: true,
                                    ref: "flxz",
                                    defaults: {
                                        height: 15
                                    },
                                    items: items,
                                    title: "分类选择"
                                }, {
                                    xtype: "form",
                                    width: 130,
                                    autoScroll: true,
                                    region: "center",
                                    ref: "hzxz",
                                    items: items2,
                                    defaults: {
                                        height: 15
                                    },
                                    title: "汇总选择"
                                }, {
                                    xtype: "form",
                                    width: 130,
                                    autoScroll: true,
                                    ref: "pjxz",
                                    region: "east",
                                    items: items2,
                                    defaults: {
                                        height: 15
                                    },
                                    title: "平均选择"
                                }]
                            });
                            window.show();
                            }
                        },
                        '-', {
                            text: '显示全部列',
                            iconCls: 'iconnz icon-detail',
                            handler: function(me) {
                                var grid = view.up('grid'),
                                    columns = grid.columns;
                                for (var i = 0; i < columns.length; i++) {
                                    columns[i].setHidden(false);
                                }
                            }
                        }, {
                            text: '隐藏列',
                            iconCls: 'iconnz icon-hide',
                            handler: function(me) {
                                var grid = view.up('grid');
                                grid.columns[cellIndex].setHidden(true);
                            }
                        },
                        '-', {
                            text: "数据导出",
                            iconCls: 'iconnz icon-excel',
                            handler: function(btn) {
                            	var top = view.top(),
                            		me = view.up("grid"),
		                            paginition = me.pagination,
		                            records = me.store.getRange(),
		                            allColumns = me.columns,
		                            columns = new Array();
                            	

		                        // 如果有分页，去服务器请求导出
		                        if (paginition && records.length < me.store.getTotalCount()) {
		                            var params = me.store.getProxy().extraParams;
		                            // 一次只允许导出1000条数据
		                            params.page = 1;
		                            params.start = 0;
		                            params.limit = 100000000;
		                            Utils.ajax(me.store.getProxy().url, me.store.getProxy().extraParams, function(callback) {
		                                var records = callback.root,
		                                    data = "[";
		                                // 判断是不是有分组
		                                for (var i = 0; i < allColumns.length; i++) {
		                                    if (Utils.isEmpty(allColumns[i].items) || allColumns[i].items.items.length == 0) {
		                                        columns.push(allColumns[i]);
		                                    } else {
		                                        for (var j = 0; j < allColumns[i].items.items.length; j++) {
		                                            allColumns[i].items.items[j].text = allColumns[i].items.items[j].text.indexOf("(") != -1 ? allColumns[i].items.items[j].text : allColumns[i].items.items[j].text + "(" + allColumns[i].text + ")";
		                                            columns.push(allColumns[i].items.items[j]);
		                                        }
		                                    }
		                                }
		                                data = data + "{";
		                                for (var i = 0; i < columns.length; i++) {
		                                    if (i != columns.length - 1 && columns[i].xtype != "rownumberer") {
		                                        data = data + "'" + columns[i].dataIndex + "':" + "'" + columns[i].text + "',";
		                                    } else if (columns[i].xtype != "rownumberer") {
		                                        data = data + "'" + columns[i].dataIndex + "':" + "'" + columns[i].text + "'";
		                                    }
		                                }
		                                data = data + "}";
		
		                                if (records.length > 0) data = data + ",";
		                                for (var i = 0; i < records.length; i++) {
		                                    if (i != records.length - 1) {
		                                        data = data + "{";
		                                        for (var j = 0; j < columns.length; j++) {
		                                            if (j != columns.length - 1) {
		                                                data = data + "'" + columns[j].dataIndex + "':" + "'" + records[i][columns[j].dataIndex] + "',";
		                                            } else {
		                                                data = data + "'" + columns[j].dataIndex + "':" + "'" + records[i][columns[j].dataIndex] + "'";
		                                            }
		                                        }
		                                        data = data + "},";
		                                    } else {
		                                        data = data + "{";
		                                        for (var j = 0; j < columns.length; j++) {
		                                            if (j != columns.length - 1) {
		                                                data = data + "'" + columns[j].dataIndex + "':" + "'" + records[i][columns[j].dataIndex] + "',";
		                                            } else {
		                                                data = data + "'" + columns[j].dataIndex + "':" + "'" + records[i][columns[j].dataIndex] + "'";
		                                            }
		                                        }
		                                        data = data + "}";
		                                    }
		                                }
		
		                                data = data + "]";
		                                var data = data.replace(/\\/g, "\\\\").replace(/\b/gi, '').replace(/\t/g, '').replace(/\n/g, '').replace(/\f/g, '').replace(/\r/g, '').replace(/\s/g, '');
		                                var download = Ext.create("Ext.window.Window", {
		                                    width: 300,
		                                    height: 200,
		                                    html: '<form action="/admin/export"  method="post" id="exportForm"><input type="hidden" name="datas" value="' + data + '" /></form>',
		                                    title: "下载等待"
		                                });
		                                download.show();
		                                download.hide();
		                                document.getElementById("exportForm").submit();
		                                download.close();
		                            }, null, me.top().down('button[action=query]'));
		                        } else {
		                            // 判断是不是有分组
		                            var data = "[";
		                            for (var i = 0; i < allColumns.length; i++) {
		                                if (Utils.isEmpty(allColumns[i].items) || allColumns[i].items.items.length == 0) {
		                                    columns.push(allColumns[i]);
		                                } else {
		                                    for (var j = 0; j < allColumns[i].items.items.length; j++) {
		                                        allColumns[i].items.items[j].text = allColumns[i].items.items[j].text.indexOf("(") != -1 ? allColumns[i].items.items[j].text : allColumns[i].items.items[j].text + "(" + allColumns[i].text + ")";
		                                        columns.push(allColumns[i].items.items[j]);
		                                    }
		                                }
		                            }
		                            data = data + "{";
		                            for (var i = 0; i < columns.length; i++) {
		                                if (i != columns.length - 1 && columns[i].xtype != "rownumberer") {
		                                    data = data + "'" + columns[i].dataIndex + "':" + "'" + columns[i].text + "',";
		                                } else if (columns[i].xtype != "rownumberer") {
		                                    data = data + "'" + columns[i].dataIndex + "':" + "'" + columns[i].text + "'";
		                                }
		                            }
		                            data = data + "}";
		                            if (records.length > 0) data = data + ",";
		                            for (var i = 0; i < records.length; i++) {
		                                if (i != records.length - 1) {
		                                    data = data + "{";
		                                    for (var j = 0; j < columns.length; j++) {
		                                        if (j != columns.length - 1) {
		                                            data = data + "'" + columns[j].dataIndex + "':" + "'" + records[i].get(columns[j].dataIndex) + "',";
		                                        } else {
		                                            data = data + "'" + columns[j].dataIndex + "':" + "'" + records[i].get(columns[j].dataIndex) + "'";
		                                        }
		                                    }
		                                    data = data + "},";
		                                } else {
		                                    data = data + "{";
		                                    for (var j = 0; j < columns.length; j++) {
		                                        if (j != columns.length - 1) {
		                                            data = data + "'" + columns[j].dataIndex + "':" + "'" + records[i].get(columns[j].dataIndex) + "',";
		                                        } else {
		                                            data = data + "'" + columns[j].dataIndex + "':" + "'" + records[i].get(columns[j].dataIndex) + "'";
		                                        }
		                                    }
		                                    data = data + "}";
		                                }
		                            }
		                            data = data + "]";
		                            var data = data.replace(/\\/g, "\\\\").replace(/\b/gi, '').replace(/\t/g, '').replace(/\n/g, '').replace(/\f/g, '').replace(/\r/g, '').replace(/\s/g, '');
		                            var download = Ext.create("Ext.window.Window", {
		                                width: 300,
		                                height: 200,
		                                html: '<form action="/export"  method="post" id="exportForm"><input type="hidden" name="datas" value="' + data + '" /></form>',
		                                title: "下载等待"
		                            });
		                            download.show();
		                            download.hide();
		                            document.getElementById("exportForm").submit();
		                            download.close();
		                        }	
                            }
                        }
                    ]
                });

                menu.showAt(event.getXY());

                var me = view.up("grid"),
                    record = me.getSingleSelect();

                if (record == null) {
                    Ext.MessageBox.alert("提示", "没有选中的数据");
                    return;
                }
                // 判断有无int 和 number字段的列，如果无，则不需要显示图标分析
                var columns = grid.columns,
                    mobel = grid.store.getProxy().getModel(),
                    haveNumber = false;

                for (var i = 0; i < columns.length; i++) {
                    if (mobel.getField(columns[i].dataIndex) && (mobel.getField(columns[i].dataIndex).type == 'int' || mobel.getField(columns[i].dataIndex).type == 'number') && !columns[i].isHidden()) {
                        haveNumber = true;
                    }
                }

                if (Utils.isEmpty(view.contextmenu) && 　haveNumber) {
                    menu.add('-');

                    menu.add({
                        text: '图表分析',
                        iconCls: 'iconnz icon-chart',
                        listeners: {
                            click: function(btn) {
                                Ext.create('core.coreApp.append.chart', {
                                    grid: grid
                                }).show();
                            }
                        }
                    });
                }
                
                if (Utils.isEmpty(view.contextmenu) && 　record.get("commodityCode")) {
                    menu.add("-");
                    if (record.get("commodityCode")) {
                        menu.add({
                            text: "商品标贴",
                            iconCls: 'iconnz icon-collect',
                            listeners: {
                                click: function(btn) {
                                    Ext.create('core.append.LabelChoose', {commodityCode: record.get('commodityCode')}).show();
                                }
                            }
                        });
                    }
                }

                if (Utils.isEmpty(view.contextmenu) && 　rclickcollection && rclickcollection.length > 0) {
                    menu.add("-");

                    for (var i = 0; i < rclickcollection.length; i++) {
                        (function(rclickcollection) {
                            menu.add({
                                text: rclickcollection.text,
                                glyph: rclickcollection.glyph,
                                listeners: {
                                    click: function(btn) {
                                        rclickcollection.fireEvent('click', rclickcollection);
                                    }
                                }
                            });
                        })(rclickcollection[i]);
                    }
                }

                Ext.apply(view, {
                    contextmenu: menu
                });
            });
    }
});

/** ************************************************************************************** */
/** ***********************************####工具类###************************************** */
/** ************************************************************************************** */

/** ******************************utils************************************** */

/**
 * Created by Hawk on 2016/6/18.
 */
var DescartesUtils = {   
    /**
	 *    * 如果传入的参数只有一个数组，求笛卡尔积结果    *
	 * 
	 * @param arr1
	 *            一维数组    *
	 * @returns {Array}
	 */
      
    descartes1: function(arr1) {     // 返回结果，是一个二维数组
            
        var result = [];    
        var i = 0;    
        for (i = 0; i < arr1.length; i++) {      
            var item1 = arr1[i];      
            result.push([item1]);    
        }    
        return result;  
    },
       
    /**
	 *    * 如果传入的参数只有两个数组，求笛卡尔积结果    *
	 * 
	 * @param arr1
	 *            一维数组    *
	 * @param arr2
	 *            一维数组    *
	 * @returns {Array}
	 */
      descartes2: function(arr1, arr2) {     // 返回结果，是一个二维数组
            
        var result = [];    
        var i = 0,
            j = 0;    
        for (i = 0; i < arr1.length; i++) {      
            var item1 = arr1[i];      
            for (j = 0; j < arr2.length; j++) {        
                var item2 = arr2[j];        
                result.push([item1, item2]);      
            }    
        }    
        return result;  
    },
       
    /**
	 *    *    *
	 * 
	 * @param arr2D
	 *            二维数组    *
	 * @param arr1D
	 *            一维数组    *
	 * @returns {Array}
	 */
      descartes2DAnd1D: function(arr2D, arr1D) {    
        var i = 0,
            j = 0;     // 返回结果，是一个二维数组
            
        var result = [];     
        for (i = 0; i < arr2D.length; i++) {      
            var arrOf2D = arr2D[i];      
            for (j = 0; j < arr1D.length; j++) {        
                var item1D = arr1D[j];        
                result.push(arrOf2D.concat(item1D));      
            }    
        }     
        return result;  
    },
       descartes3: function(list) {    
        var listLength = list.length;    
        var i = 0,
            j = 0;     // 返回结果，是一个二维数组
            
        var result = [];     // 为了便于观察，采用这种顺序
            
        var arr2D = DescartesUtils.descartes2(list[0], list[1]);    
        for (i = 2; i < listLength; i++) {      
            var arrOfList = list[i];      
            arr2D = DescartesUtils.descartes2DAnd1D(arr2D, arrOfList);    
        }    
        return arr2D;  
    },
        // 笛卡儿积组合
      descartes: function(list)   {    
        if (!list) {      
            return [];    
        }    
        if (list.length <= 0) {      
            return [];    
        }    
        if (list.length == 1) {      
            return DescartesUtils.descartes1(list[0]);    
        }    
        if (list.length == 2) {      
            return DescartesUtils.descartes2(list[0], list[1]);    
        }    
        if (list.length >= 3) {      
            return DescartesUtils.descartes3(list);    
        }  
    } 
};

var SqlUtils = {

    /**
	 * 执行操作 1、按钮 2、URL地址 3、提交状态 4、外部参数 5、回调方法
	 */
    doUpdate: function(btn, vars, fun) {
        if (btn && btn.action == 'delete') {
            Ext.MessageBox.confirm("提示", btn.confirmText || "是否删除数据",
                function(btnConfirm) {
                    if (btnConfirm == "yes") {
                        SqlUtils.execute(btn, vars, fun ? fun : function() {
                            btn.top().down('grid').store.remove(btn.top().down('grid').getSelect());
                            btn.disabled = false;
                        });
                    }
                });
        } else {
            SqlUtils.execute(btn, vars, fun);
        }
    },

    // 执行页面操作
    // 说明: 可以根据BTN的Active确定Grid是对(新增、修改、全部)进行操作，
    // Vars为页面需要额外传入的参数,Fun为页面执行完成之后的回调方法
    execute: function(btn, vars, fun) {
        var me = this;

        // 防止重复点击
        btn.disabled = true;
        // Utils.getMask().show();

        // TAB切换
        var toptip = btn.top(),
            xtypes = toptip.getXTypes(),
            grids = Ext.ComponentQuery.query("grid", toptip),
            form = toptip.getXTypes().indexOf("form") > -1 ? toptip : toptip.down('form'),
            items = btn.ignoreForm || !form ? [] : form.getFormItems(),
            isWin = toptip.getXTypes().indexOf("window") > -1,
            task = toptip.task,
            summaryItems = [],
            params = {},
            flg = true,
            grid, formCheck = true,
            rsize = 0;
        
        // form验证,是否有不合格数据
        form.getForm().isValid();
        // 如果有正则不合格的,拦截下来,不要做保存
        if (form.getForm().hasInvalidField()){
        	btn.disabled = false;

        	return;
        }
        if (btn.action == 'delete' && grids) {
            for (var i = 0; i < grids.length; i++) {
                grids[i].exclude = false;
            }
        }

        params = this.fillDataWithVars(params, vars);

        if (task) params = this.fillDataWithVars(params, task.data);

        var formData = Utils.isEmpty(form) ? {} : form.getFormData();

        if (formData) {
            params = this.fillDataWithVars(params, formData);
        }

        var result = null, delKeys = [], delKey = {};;
        for (var z = 0; z < grids.length; z++) {
            result = new Array();
            grid = grids[z];
            if (!Utils.isEmpty(grid) && !grid.exclude) {
                var removeRecord = new Array(),
                    records = grid.getRecords(btn.active, grid.exception, true),
                    columns = grid.columns,
                    rowRemove = false,
                    exception = grid.exception,
                    keys = [],
                    zeros = [],
                    alerts = [],
                    isKey = false,
                    isAlert = false,
                    isZero = false;

                if (!grid.allowEmpty && (Utils.isEmpty(records) || records.length == 0)) {
                    Ext.MessageBox.alert("提示", "数据为空",
                        function(ok) {
                            if (ok == "ok") {
                                btn.disabled = false;

                                // Utils.getMask().hide();
                            }
                        });
                    flg = false;
                    break;
                }

                if (exception) {
                    keys = Utils.isEmpty(exception["key"]) ? null : exception["key"].split(",");
                    zeros = Utils.isEmpty(exception["zero"]) ? null : exception["zero"].split(",");
                    alerts = Utils.isEmpty(exception["alert"]) ? null : exception["alert"].split(",");
                }

                /* 去除KEY为空的数据 */
                if (records != null && records.length > 0) {
                    for (var i = 0; i < records.length; i++) {
                        var isKey = false,
	                        isAlert = false,
	                        isZero = false;
                        if (records[i].errors) {
                        	for (var err in records[i].errors) {
                        		Ext.MessageBox.toast(records[i].errors[err], 'error');
                        		btn.disabled = false;
                        	}
                        	flg = false;
                            break;
                        }
                        if (!isKey) {
                            if (alerts != null && alerts.length > 0) {
                                for (var f = 0; f < alerts.length; f++) {
                                    if (Utils.isEmpty(records[i].get(Ext.String.trim(alerts[f]))) || records[i].get(Ext.String.trim(alerts[f])) == '') {
                                    	Ext.MessageBox.toast(grid.getColumnText(alerts[f]) + "不能为空",'error');
                                    	btn.disabled = false;
                                        flg = false;
                                        break;
                                    }
                                }
                            }
                            if (!isAlert) {
                                if (zeros != null && zeros.length > 0) {
                                    for (var f = 0; f < zeros.length; f++) {
                                        if (Utils.isEmpty(records[i].get(Ext.String.trim(zeros[f]))) || records[i].get(Ext.String.trim(zeros[f])) == 0) {
                                            Ext.MessageBox.toast(grid.getColumnText(zeros[f]) + "不能为0",'error');
                                        	btn.disabled = false;
                                            flg = false;
                                            break;  
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                grid.store.remove(removeRecord);
                records.removeAll(removeRecord);
                if (!grid.allowEmpty && (records == null || records.length <= 0)) {
                    Ext.MessageBox.toast("请选择数据",'error');
                	btn.disabled = false;
                    flg = false;
                    break;
                }

                rsize = records.length;
                params.rsize = rsize;

                for (var i = 0; i < records.length; i++) {
                    result.push(Utils.mergeSystemParam(records[i].data));
                    // delete 有长度限制，所以只取key
                    if (grids[z].exception && grids[z].exception.key) {
                        delKey[grid.exception.key] = records[i].get(grids[z].exception.key);
                    }
                    delKeys.push(delKey);
                }


                if (grid.summary) {
                    summaryItems = Ext.getCmp(grid.id + 'summary').items.items;
                    for (var i = 0; i < summaryItems.length; i++) {
                        if (summaryItems[i].id.indexOf(grid.id + 'summary') > -1) {
                            params[summaryItems[i].id.replace(grid.id + 'summary', '')] = new Number(summaryItems[i].text);
                        }
                    }
                }
            }

            gridData = Utils.isEmpty(grid) ? [] : result;

            if (grid && !grid.exclude && !Utils.isEmpty(gridData)) {
                if (!grid.allowEmpty && (Utils.isEmpty(gridData) || gridData.length == 0)) {
                    Ext.MessageBox.toast("请选择数据",'error');
                    btn.disabled = false;
                    flg = false;
                    break;
                }
                for (var j = 0; j < gridData.length; j++) {
                    Utils.applyIf(gridData[j], formData);
                    Utils.apply(gridData[j], vars);
                }
            } else {
                gridData = [params];
            }

            params[grid.ref || 'detail'] = Utils.jsonToStr(gridData).replaceHtml();
        }

        params.rsize = params.rsize || 1;
        if (grids.length == 0) {
            params['detail'] = Utils.jsonToStr([params]);
        }
        Utils.applyIf(params, {
            action: btn.action
        });

        if (btn.uri != '/print') {
            Utils.mergeSystemParam(params);
        } 

        Utils.applyIf(params, {
            xtype: btn.plan || toptip.xtype
        });

        if (!flg) {
            return;
        }

        if (btn.method == 'delete') {
            uri = btn.uri + '/' +encodeURI(Utils.jsonToStr(delKeys));
        } else {
            uri = btn.uri;
        }

        Utils.ajax(uri || '/doTask', params,
            function(callback) {
                btn.disabled = false;
                if (fun)
                    fun(btn, callback);
                me.callProgramEndPopWindow(btn, callback, isWin, form, grids);
            },
            function() {
                // Utils.getMask().hide();
            }, btn);
    },

    // 填充流程数据
    fillDataWithVars: function(params, vars) {
        if (vars) {
            for (var variable in vars) {
                if (Utils.isEmpty(params[variable]))
                    params[variable] = vars[variable];
            }
        }
        return params;
    },

    // 回调函数
    // 说明：点击完保存按钮后的操作，形式为弹出框
    // 参数：btn 按钮
    // callback 回调函数
    
    
    // form 当前操作页面的form
    // grid 当前操作页面的列表
    callProgramEndPopWindow: function(btn, callback, isWin, form, grids) {
        // 保存并新增
        var top = btn.top();

        btn.disabled = false;

        Ext.MessageBox.toast(callback.root);
        
        if (isWin) {
        	grids = Ext.ComponentQuery.query("grid", top.refer);
        	top.close();
        }
        if (btn.follow == 'saveAndNew') {
            if (top.getXTypes().indexOf("window") > -1) {
                top.close();
            } else {
                top.pageReset();
            }
            btn.disabled = false;
        } else if (btn.follow == 'save') {
            callback.task = {
                data: {

                }
            };
            btn.disabled = false;
            btn.top().down('textfield[name=billNo]').setValue(callback.msg.pk);
        } else if (btn.follow == 'remove') {
            for (var i = 0; i < grids.length; i++) {
                grids[i].store.remove(grids[i].getSelect());

            }
            btn.disabled = false;
        } else if (btn.follow == 'reflush') {
            for (var i = 0; i < grids.length; i++) {
                var reflush = grids[i].top().down('button[action=query]');
                if (reflush)
                    reflush.fireEvent('click', reflush);

            }
            btn.disabled = false;
        } else if (btn.follow == 'commit') {
            for (var i = 0; i < grids.length; i++) {
                grids[i].store.commitChanges();
            }
            btn.disabled = false;
        } else if (btn.follow == 'close') {
            if (top.queryBtn) {
                top.queryBtn.fireEvent('click', top.queryBtn);
            }
            $('.page-tabs-content').find('.active i').trigger("click");
        } else {
            for (var i = 0; i < grids.length; i++) {
                grids[i].store.commitChanges();
            }
        }
    }
};

Ext.define('core.append.AButton', {
	extend: 'Ext.Component',
	alias: 'widget.abtn',
	autoEl: {
		tag: 'a'
	},
    data: {},
	tpl: '<span>{text}</span>',
	// 这是选择器
    renderSelectors: {
        span: 'span',
    },
	afterRender: function() {
		var me = this;
		me.mon(me.span, {
            scope: me,
            click: me.onClick
        });
	},
	onClick: function(){
		this.fireEvent('click', this);
	},
	initComponent: function() {
		var me = this;
		
		Ext.apply(me.data, {
			text: me.text
		});
		me.callParent(arguments);
	}
		
});

// excel自定义导出控件
// 说明:可以根据Grid的列来自定义自己的EXCEL导入公式
// 参数:
// 返回值:
Ext.define('core.append.ExcelDesign', {
    extend: 'Ext.window.Window',
    title: 'EXCEL定制',
    alias: 'widget.excelDesign',
    closeAction: 'destroy',
    width: '50%',
    height: '50%',
    layout: 'fit',
    items: [{
        xtype: 'grid',
        allowInsert: true,
        selType: 'checkboxmodel',
        exception: {
            key: 'dataIndex'
        },
        autoInsert: false,
        fields: [{
            name: 'text',
            text: '文本',
            width: 100,
            editor: {
                xtype: 'textfield'
            }
        }, {
            name: 'dataIndex',
            hidden: true
        }]
    }],
    tbar: [{
        text: '保存',
        handler: function(btn) {
            var win = btn.up('window'),
                xtype = win.refer.xtype,
                object = "[",
                records = win.down('grid').getSelect();

            Ext.Array.each(records, function(record, index) {
                record.set('xtype', xtype);
                if (index == 0) {
                    object = object + Ext.encode(record.data);
                } else {
                    object = object + "," + Ext.encode(record.data);
                }
            });

            object = object + "]";

            Utils.ajax('/exportExcel', {
                datas: object
            }, function(callback) {
                if (callback.prompt == "success") {
                    Ext.MessageBox.alert("提示", callback.msg);
                    win.close();
                }
            });
        }
    }, '-', {
        text: '导出',
        handler: function(btn) {
            var records = btn.up('excelDesign').down('grid').getSelect(),
                data = '[{';0
            for (var i = 0; i < records.length; i++) {
                data = data + "'text" + i + "':'" + records[i].get('text') + "'";

                if (i < records.length - 1) {
                    data = data + ',';
                }
            }
            data = data + '}]';
            var download = Ext.create("Ext.window.Window", {
                width: 300,
                height: 200,
                html: '<form action="/export.do"  method="post" id="exportForm"><input type="hidden" name="datas" value="' + data + '" /></form>',
                title: "下载等待"
            });

            download.show();
            download.hide();
            document.getElementById("exportForm").submit();
            download.close()
        }
    }],
    has: function(dataIndex, callback) {
        var result = false;
        for (var i = 0; i < callback.root.length; i++) {
            if (callback.root[i]['dataIndex'] == dataIndex) {
                result = true;
                break;
            }
        }
        return result;
    },
    initComponent: function() {
        var me = this;

        me.on('afterrender', function() {

            Utils.ajax('/extra', {
                xtype: 'extra_excel',
                pagextype: me.refer.xtype
            }, function(callback) {
                var fields = me.refer.down('grid').fields,
                    sm = me.down('grid').getSelectionModel(),
                    data = [],
                    checked = [];

                for (var i = 0; i < fields.length; i++) {
                    if (!fields[i].hidden) {
                        data.push({
                            text: fields[i].text,
                            dataIndex: fields[i].name
                        });
                        checked.push(me.has(fields[i].name, callback) ? i : -1);
                    }
                }

                me.down('grid').loadData(data);

                for (var i = 0; i < checked.length; i++) {
                    if (checked[i] > -1)
                        sm.select(checked[i], true);
                }
            });

        });

        me.callParent(arguments);
    }
});

// excel导入控件
// 说明:可以根据EXCEL导入生成自定义EXCEL的GRID数据
// 参数:
// 返回值:
Ext.define('core.append.ExcelImport', {
    extend: 'Ext.window.Window',
    title: 'EXCEL导入',
    alias: 'widget.excelImport',
    closeAction: 'destroy',
    width: 250,
    height: 80,
    layout: 'fit',
    items: [{
        xtype: 'form',
        items: [{
            xtype: 'filefield',
            name: 'file',
            width: '99%',
            buttonText: '浏览',
            labelAlign: 'right',
            listeners: {
                change: function(me) {
                    var top = me.up('excelImport'),
                    	refer = top.refer,
                        btn = me.up('excelImport').btn,
                        win = me.up('window').win,
                        append = me.top().append,
                        tempSave = refer.down('button[tempSave=true]'),
                        grid = refer.down('grid'),
                        callback = me.up('window').callback;

                    me.up('window').down('form').getForm().submit({
                        waitMsg: '请稍等,正在提交数据',
                        method: 'post',
                        url: btn.uri || '/importExcel',
                        params: {
                            xtype: refer.xtype
                        },
                        success: function(panel, action) {
                            var callback = Ext.decode(action.response.responseText);
                            if (callback.success && callback.prompt == 'success') {
                            	 if (callback) {
                            		 me.up('window').callback(callback.root,btn);
                            		 return;
                            	 }
                            	 if (btn.uri) {
                                 	Ext.MessageBox.alert(callback.prompt, callback.msg);
                                 } else {
	                                var root = callback.root,
	                                	fields = root.length > 0 ? grid.fields : [],
	                                	mapping = {};
	                                
	                                for (var item in root[0]) {
	                                	for (var i = 0; i < fields.length; i++) {
	                                		if (item == fields[i].text) {
	                                			mapping[item]=fields[i].name;
	                                		}
	                                	}
	                                }
	                                
	                                for (var item in mapping) {
	                                	for (var i = 0; i < root.length; i++) {
	                                		root[i][mapping[item]]=root[i][item];
	                                	}
	                                }
	                                
	                                if (!append) {
	                                	grid.store.removeAll();
	                                	grid.store.loadData(callback.root);
	                                } else {
	                                	grid.mergeRecords(callback.root);
	                                }
	
	                                
	                                me.up('window').close();
	                                if (win)
	                                	win.close();
	                                if (tempSave) {
	                                	tempSave.fireEvent('click', tempSave);
	                                }
	                                if (btn && btn.afterImport) {
	                                    btn.afterImport(btn);
	                                }
	                            }
                            } else {
                                Ext.MessageBox.alert(callback.prompt, callback.msg);
                            }
                        }
                    });
                }
            }
        }]
    }]
});


function validateImageSize(){
    //取控件DOM对象
    var field = document.getElementById('id_fileField');
    //取控件中的input元素
    var inputs = field.getElementsByTagName('input');
    var fileInput = null;
    var il = inputs.length;
    //取出input 类型为file的元素
    for(var i = 0; i < il; i ++){
        if(inputs[i].type == 'file'){
            fileInput = inputs[i];
            break;
        }
    }
    if(fileInput != null){
        var fileSize = getFileSize(fileInput);
        //允许上传不大于20M的文件
        return fileSize > 20480;
    }
    return false;
};
//计算文件大小，返回文件大小值，单位K
function getFileSize(target) {
    var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
    var fs = 0;
    if (isIE && !target.files) {
        var filePath = target.value;
        var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
        var file = fileSystem.GetFile(filePath);
        fs = file.Size;
    } else if (target.files && target.files.length > 0) {
        fs = target.files[0].size;
    } else {
        fs = 0;
    }
    if (fs > 0) {
        fs = fs / 1024;
    }
    return fs;
};
// 编码控件
// 说明 :可以针对关键字高亮，取消原来使用的textarea控件
// 参数:无
// 返回值：
Ext.define('app.coreApp.append.coder', {
    extend: 'Ext.panel.Panel',
    isFormField: true,
    alias: 'widget.coder',
    layout: 'fit',
    getValue: function() {
        var me = this;
        return me.editor.getValue();
    },

    setValue: function(val) {
        var me = this;
        if (val == '') {
            return;
        }
        me.editor.setValue(val);
    },

    getSubmitData: function() {
        var me = this;
        return me.editor.getValue();
    },

    isFileUpload: function() {
        return false;
    },

    // 验证是否可用,和formBind绑定
    isValid: function() {
        return this.allowBlank ? this.allowBlank : this.wasValid;
    },

    validate: function(value, separator, errors) {
        return this.allowBlank ? this.allowBlank : this.wasValid;
    },

    isDirty: function() {
        return false;
    },

    initComponent: function() {
        var me = this;
        me.items = [{
            xtype: 'textarea'
        }];

        me.callParent(arguments);
        me.on('afterrender', function(me) {
            var editor = CodeMirror.fromTextArea(me.down('textarea').inputEl.dom, {
                mode: "text/javascript",
                theme: 'abcdef',
                indentUnit: 4,
                smartIndent: true,
                foldGutter: true,
                fullScreen: true,
                matchBrackets: true,
                showCursorWhenSelecting: true,
                lineNumbers: true
            });
            editor.setOption('lineWrapping', true); // 设置自动换行
            // editor.setSize('auto', 'auto');// 设置自适应高度
            editor.setValue("{}");
            me.value = editor.getValue();
            me.editor = editor;
        });
    }
});

// 图片
Ext.define('app.coreApp.append.column.Img', {
	extend: 'Ext.grid.column.Column',
    alias: 'widget.img',

    text: '图片',
    
    /**
     * @cfg {Number} width
     * The default width in pixels of the row number column.
     */
    width: 30,

    /**
     * @cfg {Boolean} sortable
     * @hide
     */
    sortable: false,

    /**
     * @cfg {Boolean} [draggable=false]
     * False to disable drag-drop reordering of this column.
     */
    draggable: false,

    // Flag to Lockable to move instances of this column to the locked side.
    autoLock: true,

    // May not be moved from its preferred locked side when grid is enableLocking:true
    lockable: false,

    align: 'right',

    /**
     * @cfg {Boolean} producesHTML
     * @inheritdoc
     */
    producesHTML: false,

    constructor: function (config) {
        var me = this;

        // Copy the prototype's default width setting into an instance property to provide
        // a default width which will not be overridden by Container.applyDefaults use of Ext.applyIf
        me.width = me.width;

        me.callParent(arguments);

        // Override any setting from the HeaderContainer's defaults
        me.sortable = false;

        me.scope = me;
    },

    // private
    resizable: false,
    hideable: false,
    menuDisabled: true,
    dataIndex: '',
    cls: Ext.baseCSSPrefix + 'row-numberer',
    tdCls: Ext.baseCSSPrefix + 'grid-cell-row-numberer ' + Ext.baseCSSPrefix + 'grid-cell-special',
    innerCls: Ext.baseCSSPrefix + 'grid-cell-inner-row-numberer',
    rowspan: undefined,

    // private
    defaultRenderer: function(value, metaData, record, rowIdx, colIdx, dataSource, view) {
        if (value== '') {
        	return '<img src="/images/img_small.png" onClick="Utils.showImageBySrc('+value+')"/>';
        } else {
        	return '<img src="/images/img_small.png" onClick="Utils.showImageBySrc('+value+')"/>';
        }
    },

    updater: function(cell, value, record, view, dataSource) {
        cell.firstChild.innerHTML = this.defaultRenderer(value, null, record, null, null, dataSource, view);
    }
});

//图片
Ext.define('app.coreApp.append.column.Oper', {
	extend: 'Ext.grid.column.Column',
    alias: 'widget.oper',
    text: '操作',
    /**
     * @cfg {Number} width
     * The default width in pixels of the row number column.
     */
    width: 60,

    /**
     * @cfg {Boolean} sortable
     * @hide
     */
    sortable: false,

    /**
     * @cfg {Boolean} [draggable=false]
     * False to disable drag-drop reordering of this column.
     */
    draggable: false,

    // Flag to Lockable to move instances of this column to the locked side.
    autoLock: true,

    // May not be moved from its preferred locked side when grid is enableLocking:true
    lockable: false,

    align: 'right',

    /**
     * @cfg {Boolean} producesHTML
     * @inheritdoc
     */
    producesHTML: false,

    constructor: function (config) {
        var me = this;

        // Copy the prototype's default width setting into an instance property to provide
        // a default width which will not be overridden by Container.applyDefaults use of Ext.applyIf
        me.width = me.width;

        me.callParent(arguments);

        // Override any setting from the HeaderContainer's defaults
        me.sortable = false;

        me.scope = me;
    },

    // private
    resizable: false,
    hideable: false,
    menuDisabled: true,
    dataIndex: '',
    cls: Ext.baseCSSPrefix + 'row-numberer',
    tdCls: Ext.baseCSSPrefix + 'grid-cell-row-numberer ' + Ext.baseCSSPrefix + 'grid-cell-special',
    innerCls: Ext.baseCSSPrefix + 'grid-cell-inner-row-numberer',
    rowspan: undefined,

    // private
    defaultRenderer: function(value, metaData, record, rowIdx, colIdx, dataSource, view) {
    	return '<img src="/images/add.png" width="14px" style="cursor:hand;margin-top: 3px;margin-right:5px" height="14px" onClick="Utils.rowOper(\''+view.up('grid').id+'\', \'add\')"/><img src="/images/del.png" style="cursor:hand;margin-top: 3px;margin-right:5px;margin-left:5px" width="14px" height="14px" onClick="Utils.rowOper(\''+view.up('grid').id+'\', \'del\', '+rowIdx+')"/>';
    },

    updater: function(cell, value, record, view, dataSource) {
        cell.firstChild.innerHTML = this.defaultRenderer(value, null, record, null, null, dataSource, view);
    }
});

// 图片控件
// 说明:双击图片控件，会弹出图片选择框，确定后开始上传图片，单击图片，会显示大图
// 参数:
// 返回值：
Ext.define('app.coreApp.append.upload', {
    extend: 'Ext.container.Container',
    isFormField: true,
    alias: 'widget.upload',
    height: 170,
    inputWidth: 150,
    inputHeight: 117,
    width: 190,
    // 封面文字
    text: '',
    // 提交后的名称
    name: '',
    // 使图片居中
    padding: '0 0 0 0',

    value: '',

    // fom验证
    wasValid: false,

    isRender: false,
    // 是否允许为空
    allowBlank: true,
    setReadOnly: function(bol) {

    },

    getValue: function() {
        return this.value;
    },

    getSubmitData: function() {
        return this.value;
    },

    isFileUpload: function() {
        return false;
    },

    // 验证是否可用,和formBind绑定
    isValid: function() {
        return this.allowBlank ? this.allowBlank : this.wasValid;
    },

    validate: function(value, separator, errors) {
        return this.allowBlank ? this.allowBlank : this.wasValid;
    },

    isDirty: function() {
        return false;
    },

    setValue: function(value) {
        var me = this;
        me.wasValid = true;
        this.value = value;
        if (value == '') {
            return;
        }

        if (!Utils.isEmpty(document.getElementById('img_' + me.id))) {
            var img = document.getElementById('img_' + me.id);
            img.parentNode.removeChild(img);
        }

        if (Utils.isEmpty(document.getElementById('img_' + me.id)) && me.isRender) {
            Utils.ajax('/getImg', {
                fileid: me.value
            }, function(callback) {

                var image = callback;

                if (image.error) {
                    Ext.MessageBox.alert("错误", image.error);
                    return;
                }

                var rate = (me.inputWidth / image.width < me.inputHeight / image.height) ? me.inputWidth / image.width : me.inputHeight / image.height;

                me.padding = (image.height >= image.width ? 0 : (me.inputHeight - 1 - (image.height * rate)) / 2) + ' 0 0 0';

                me.setStyle('background', '');
                me.setStyle('text-align', 'center');
                me.setHtml('<div style="padding:' + me.padding + '"><img id="img_' + me.id + '" style="dispaly:block;margin:0 auto;" src="' + image.fileBytes + '" width="' + (image.width * rate - 2) + 'px" height="' + (image.height * rate - 2) + 'px" /></div>');

                document.getElementById('img_' + me.id).addEventListener('click', function() {
                    // console.log('click ' + (new Date()).getTime());
                    Utils.clickTimeout.set(function() {
                        Utils.showFullImage(document.getElementById('img_' + me.id));
                    });
                }, false);
            });
        }
    },

    doClear: function() {
        var me = this;
        me.style = 'text-align:	center;border:1px solid #0c5db7;background: #dadada url(/style/images/corver.png) repeat-y 0 0; height: ' + me.inputHeight + 'px; line-height: ' + me.inputHeight + 'px;cursor: hand;-webkit-user-select:none;-moz-user-select:none;-ms-user-select:none;user-select:none;';
        me.setHtml('<h1 style= "font-size: ' + me.inputHeight / me.text.length + 'px; color: #39689d; margin: 0 auto;width: ' + me.inputHeight / me.text.length + 'px;line-height: ' + me.inputHeight / me.text.length + 'px;font-family: 楷体_GB2312">' + me.text + '</h1>');
    },

    initComponent: function() {
        var me = this;

        this.override = true;

        me.on('afterrender', function(){
        	Ext.fly(me.getEl()).on('dblclick', function() {
                var fileinput = document.createElement('input');

                fileinput.type = 'file';

                fileinput.click();

                fileinput.onchange = function() {
                    if (fileinput.files && fileinput.files[0]) {

                        var progressBar,
                            scale = 0,
                            FileController = "/imageUpload",
                            form = new FormData(),
                            xhr = new XMLHttpRequest();

                        form.append("imageFile", fileinput.files[0]);
                        form.append("fileid", me.value);
                        xhr.onreadystatechange = function() {
                            if (xhr.readyState == 4 && xhr.status == 200) {
                                var b = Utils.strToJson(xhr.responseText);
                                /*if (me.value == '') {
                                    me.ownerCt.add(Ext.widget('upload'));
                                }*/
                                me.value = b.msg;
                                me.wasValid = true;
                                $(fileinput).remove();
                            }
                        };

                        xhr.open("post", FileController, true);

                        xhr.onerror = function(evt) {
                            alert("上传失败！");
                        };


                        xhr.upload.onloadstart = function() { // 上传开始执行方法
                            progressBar = Ext.Msg.show({
                                title: "上传文件",
                                msg: "请稍等，正在上传数据..",
                                progress: true,
                                width: 300
                            });
                        };

                        xhr.upload.onprogress = function(evt) {
                            if (evt.lengthComputable) { //
                                scale = evt.loaded / evt.total;
                                progressBar.updateProgress(scale, scale * 100 + '%');
                            }
                        };

                        xhr.onload = function() {

                            var reader = new FileReader();
                            reader.onload = function(evt) {
                                var image = new Image();

                                image.src = evt.target.result;
                                image.onload = function() {
                                    var rate = (me.inputWidth / image.width < me.inputHeight / image.height) ? me.inputWidth / image.width : me.inputHeight / image.height;

                                    me.padding = (me.height - image.height * rate) / 2 + 'px ' + '0px' + ' ' + '0px' + ' ' + (me.width - image.width * rate) / 2 + 'px';

                                    me.setStyle('background', '');
                                    me.setStyle('text-align', 'center');
                                    progressBar.hide();
                                    me.setHtml('<div style="padding: ' + me.padding + '"><img id="img_' + me.id + '" style="dispaly:block;margin:0 auto;" src="' + evt.target.result + '" width="' + (image.width * rate - 2) + 'px" height="' + (image.height * rate - 2) + 'px" /></div>');
                                    document.getElementById('img_' + me.id).addEventListener('click', function() {
                                        // console.log('click ' + (new
                                        // Date()).getTime());
                                        Utils.clickTimeout.set(function() {
                                            Utils.showFullImage(document.getElementById('img_' + me.id));
                                        });
                                    }, false);
                                    fileinput = null;
                                };
                            };

                            reader.readAsDataURL(fileinput.files[0]);
                        };

                        xhr.send(form);
                    }
                };
            });
        });
        if (me.value == '') {
            me.html = '<div style="margin-left: 20px;text-align:center;cursor:pointer;width:150px;"><div style="float:center;text-align:center;position:relative;text-align:center;color:#8E8E8E;background-color:#F5F5F5;width:150px;height:117px;border:1px solid #ccc;margin:10px 10px 0 0;"><span style="font-size:64px;line-height:115px;font-family:serif;">+</span><div style="position:absolute;bottom:0;left:0;right:0;"><p>' + me.text + '</p></div></div></div>';
            me.on('afterrender', function(me) {
                me.isRender = true;

                if (me.value && me.value != '') {
                    if (Utils.isEmpty(document.getElementById('img_' + me.id))) {
                        Utils.ajax('/getImg', {
                            fileid: me.value
                        }, function(callback) {

                            var image = Utils.strToJson(callback.msg);

                            if (image.error) {
                                Ext.MessageBox.alert("错误", image.error);
                                return;
                            }

                            var rate = (me.inputWidth / image.width < me.inputHeight / image.height) ? me.inputWidth / image.width : me.inputHeight / image.height;

                            me.padding = (image.height >= image.width ? 0 : (me.inputHeight - 1 - (image.height * rate)) / 2) + ' 0 0 0';

                            me.setStyle('background', '');
                            me.setStyle('text-align', 'center');
                            me.setHtml('<div style="padding:' + me.padding + '"><img id="img_' + me.id + '" style="dispaly:block;margin:0 auto;" src="' + image.fileBytes + '" width="' + (image.width * rate - 2) + 'px" height="' + (image.height * rate - 2) + 'px" /></div>');

                            document.getElementById('img_' + me.id).addEventListener('click', function() {
                                // console.log('click ' + (new
                                // Date()).getTime());
                                Utils.clickTimeout.set(function() {
                                    Utils.showFullImage(document.getElementById('img_' + me.id));
                                });
                            }, false);
                        });
                    }
                }
            });
        } else {
        	me.on('afterrender', function(me) {
        		var image = new Image();

                image.src = me.value;
                image.onload = function() {
                	var rate = (me.inputWidth / image.width < me.inputHeight / image.height) ? me.inputWidth / image.width : me.inputHeight / image.height;

                    me.padding = (me.height - image.height * rate) / 2 + 'px ' + '0px' + ' ' + '0px' + ' ' + (me.width - image.width * rate) / 2 + 'px';

                    me.setStyle('background', '');
                    me.setStyle('text-align', 'center');
                    me.setHtml('<div style="padding: ' + me.padding + '"><img id="img_' + me.id + '" style="dispaly:block;margin:0 auto;" src="' + image.src + '" width="' + (image.width * rate - 2) + 'px" height="' + (image.height * rate - 2) + 'px" /></div>');
                    $('#img_' + me.id).click(function() {
                        Utils.clickTimeout.set(function() {
                            Utils.showFullImage(document.getElementById('img_' + me.id));
                        });
                    });
                }
        	});
        }

        me.callParent(arguments);
    }
});

// 报表显示控件
// 说明：可以根据grid的数据自动生成报表
// 参数:Grid
// 返回值:
Ext.define('core.coreApp.append.chart', {
    extend: 'Ext.window.Window',
    title: '图表分析',
    width: 890,
    closeAction: 'destroy',
    height: 530,
    buttons: [{
        text: '退出',
        iconCls: 'iconnz icon-signout',
        listeners: {
            click: function(btn) {
                btn.up('window').close();
            }
        }
    }],
    initComponent: function() {
        var me = this,
            grid = me.grid,
            xfields = new Array(),
            yfields = new Array();

        var columns = grid.columns,
            mobel = grid.store.getProxy().getModel();

        for (var i = 0; i < columns.length; i++) {
            if (mobel.getField(columns[i].dataIndex) && !columns[i].isHidden()) {
                if (mobel.getField(columns[i].dataIndex).type == 'int' || mobel.getField(columns[i].dataIndex).type == 'number') {
                    yfields.push({
                        xtype: "checkbox",
                        name: columns[i].dataIndex,
                        boxLabel: columns[i].text
                    });
                } else {
                    xfields.push({
                        inputValue: columns[i].dataIndex,
                        boxLabel: columns[i].text,
                        name: 'xfield'
                    });
                }
            }
        }
        // 获取X的数据和Y的数据
        this.items = [{
            xtype: 'container',
            layout: 'form',
            items: [{
                xtype: 'container',
                width: 850,
                height: 120,
                layout: 'column',
                items: [{
                    xtype: 'fieldset',
                    title: '参数类型',
                    width: 300,
                    height: 120,
                    items: [{
                        xtype: 'radiogroup',
                        height: 110,
                        vertical: true,
                        autoScroll: true,
                        columns: 2,
                        items: xfields
                    }]
                }, {
                    xtype: 'fieldset',
                    title: '纵坐标显示字段',
                    width: 290,
                    margin: '0 5 0 10',
                    height: 120,
                    items: [{
                        id: 'chartyfield',
                        autoScroll: true,
                        height: 110,
                        xtype: 'container',
                        items: yfields
                    }]
                }, {
                    xtype: 'container',
                    width: 200,
                    height: 100,
                    layout: 'form',
                    items: [{
                        xtype: 'combo',
                        height: 60,
                        fieldLabel: '图表类型',
                        name: 'imgtype',
                        displayField: 'text',
                        valueField: 'value',
                        store: Ext.create('Ext.data.Store', {
                            fields: ['text', 'value'],
                            data: [{
                                'text': '柱状/线状图',
                                'value': '柱状/线状图'
                            }, {
                                'text': '饼状图',
                                'value': '饼状图'
                            }]
                        }),
                        value: '柱状/线状图'
                    }, {
                        xtype: 'container',
                        margin: '25 0 0 0',
                        items: [{
                            xtype: 'button',
                            width: 225,
                            height: 50,
                            text: '开始执行',
                            listeners: {
                                click: function(btn) {
                                    // 开始生成报表
                                    var win = btn.up('window'),
                                        records = win.grid.getRecords(),
                                        fields = win.grid.store.getProxy().getModel().getFields(),
                                        legendLabel = new Array(),
                                        legendIndex = new Array(),
                                        imgType = win.down('combo[name=imgtype]').getValue(),
                                        series = new Array(),
                                        categorys = new Array();

                                    var items = Ext.getCmp('chartyfield').items.items;

                                    for (var i = 0; i < items.length; i++) {
                                        if (items[i].getValue()) {
                                            legendLabel.push(items[i].boxLabel);
                                            legendIndex.push(items[i].name);
                                        }
                                    }

                                    if (imgType === '柱状/线状图') {

                                        for (var i = 0; i < records.length; i++) {
                                            categorys.push(records[i].get(win.down('radiogroup').getValue().xfield));
                                        }

                                        for (var i = 0; i < legendIndex.length; i++) {
                                            series.push({
                                                name: legendLabel[i],
                                                type: 'bar',
                                                barWidth: 40,
                                                data: me.makeSeriesFromLegendRecord(records, legendIndex[i]),
                                                itemStyle: {
                                                    normal: {
                                                        label: {
                                                            show: true,
                                                            position: 'top',
                                                            textStyle: {
                                                                fontSize: '20',
                                                                fontFamily: '微软雅黑',
                                                                fontWeight: 'bold'
                                                            },
                                                            position: 'top'
                                                        }
                                                    }
                                                }
                                            });
                                        }

                                        me.makeBarChart(Ext.getCmp('chartimg'), legendLabel, categorys, series);
                                    } else if (imgType === '饼状图') {
                                        for (var i = 0; i < legendIndex.length; i++) {
                                            series.push({
                                                value: records[0].get(legendIndex[i]),
                                                name: legendLabel[i]
                                            });
                                        }
                                        me.makePieChart(Ext.getCmp('chartimg'), legendLabel, series);
                                    }
                                }
                            }
                        }]
                    }]
                }]
            }, {
                xtype: 'container',
                flex: 1,
                id: 'chartimg'
            }]
        }];

        /**
		 * 
		 */
        me.makeSeriesFromLegendRecord = function(records, legendIndex) {
            var result = new Array();
            for (var i = 0; i < records.length; i++) {
                result.push(records[i].get(legendIndex));
            }
            return result;
        };

        me.isCategoryExists = function(categorys, text) {
            var result = false;
            for (var i = 0; i < categorys.length; i++) {
                if (categorys[i] === text) {
                    result = true;
                    break;
                }
            }
            return result;
        };

        me.makePieChart = function(dom, legend, data) {
            dom.setHtml('<div id="chart" style="height:350px"></div>');
            // 路径配置
            require.config({
                paths: {
                    echarts: '/build/dist'
                }
            });

            // 使用
            require(['echarts', 'echarts/chart/pie', // 使用柱状图就加载bar模块，按需加载
                    'echarts/chart/funnel' // 使用柱状图就加载bar模块，按需加载
                ],
                function(ec) {
                    // 基于准备好的dom，初始化echarts图表
                    var myChart = ec.init(document.getElementById('chart'));

                    option = {
                        tooltip: {
                            trigger: 'item',
                            formatter: "{b} : {c} ({d}%)"
                        },
                        legend: {
                            data: legend
                        },
                        toolbox: {
                            show: true,
                            feature: {
                                dataView: {
                                    show: true,
                                    readOnly: false
                                },
                                magicType: {
                                    show: true,
                                    type: ['pie', 'funnel'],
                                    option: {
                                        funnel: {
                                            x: '25%',
                                            width: '50%',
                                            funnelAlign: 'left',
                                            max: 1548
                                        }
                                    }
                                },
                                restore: {
                                    show: true
                                },
                                saveAsImage: {
                                    show: true
                                }
                            }
                        },
                        calculable: true,
                        series: [{
                            type: 'pie',
                            radius: '55%',
                            center: ['50%', '60%'],
                            data: data
                        }]
                    };
                    // 为echarts对象加载数据
                    myChart.setOption(option);
                });
        };

        me.makeBarChart = function(dom, legend, category, series) {
            dom.setHtml('<div id="chart" style="height:350px"></div>');

            // 路径配置
            require.config({
                paths: {
                    echarts: '/build/dist'
                }
            });

            // 使用
            require(['echarts', 'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                    'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
                ],
                function(ec) {
                    // 基于准备好的dom，初始化echarts图表
                    var myChart = ec.init(document.getElementById('chart'));

                    option = {
                        tooltip: {
                            show: true,
                            trigger: 'item'
                        },
                        legend: {
                            data: legend
                        },
                        toolbox: {
                            show: true,
                            feature: {
                                dataView: {
                                    show: true,
                                    readOnly: false
                                },
                                magicType: {
                                    show: true,
                                    type: ['line', 'bar', 'stack', 'tiled']
                                },
                                restore: {
                                    show: true
                                },
                                saveAsImage: {
                                    show: true
                                }
                            }
                        },
                        calculable: true,
                        xAxis: [{
                            type: 'category',
                            data: category
                        }],
                        yAxis: [{
                            type: 'value'
                        }],
                        series: series
                    };

                    // 为echarts对象加载数据
                    myChart.setOption(option);
                });
        };
        me.callParent(arguments);
    }
});

Ext.onReady(function() {
    Ext.Loader.setConfig({
        enabled: true,
        paths: { // '类名前缀':'所在路径'
            'core': '/static/js/app/core'
        }
    });
});