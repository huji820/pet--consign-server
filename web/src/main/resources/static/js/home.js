(function ($) {
	$.loading = function() {
		$(".fakeloader").fakeLoader({
			timeToHide:1200, //加载效果的持续时间
			zIndex:"999",//
			spinner:"spinner2",//可选值 'spinner1', 'spinner2', 'spinner3', 'spinner4', 'spinner5', 'spinner6', 'spinner7' 对应有7种效果
			bgColor:"white" //加载时的背景颜色
			//imagePath:"yourPath/customizedImage.gif" //自定义的加载图片，见demo8.html
		});
	};
	
	$.main = {
	    id: -1
	}
	$.index = function() {
		var lis = $(".menuTab"),
     	link = "core.coreApp.Welcome",
     	items = $("#content-main > div");
          
       	var item = Ext.create(link, {height: $(".mainContent").css('height'),renderTo: 'content-main', style: 'border-width: 1px;border-style: solid;border-color: rgb(207, 207, 207);border-image: initial;background: rgb(255, 255, 255);'});
       	
        $('.menuTab').removeClass('active');
        $('.mainContent').find('iframe.LRADMS_iframe').hide();
        $('.mainContent iframe:visible').load(function () {
        });
        $.learuntab.scrollToTab($('.menuTab.active'));
        $.main.id = item.id;
	}
	
    $.learuntab = {
        requestFullScreen: function () {
            var de = document.documentElement;
            if (de.requestFullscreen) {
                de.requestFullscreen();
            } else if (de.mozRequestFullScreen) {
                de.mozRequestFullScreen();
            } else if (de.webkitRequestFullScreen) {
                de.webkitRequestFullScreen();
            }
        },
        exitFullscreen: function () {
            var de = document;
            if (de.exitFullscreen) {
                de.exitFullscreen();
            } else if (de.mozCancelFullScreen) {
                de.mozCancelFullScreen();
            } else if (de.webkitCancelFullScreen) {
                de.webkitCancelFullScreen();
            }
        },
        refreshTab: function () {
            location.href=window.location.href;
        },
        activeTab: function () {
            var currentId = $(this).data('id');
            if (!$(this).hasClass('active')) {
                /*$('.mainContent .LRADMS_iframe').each(function () {
                    if ($(this).data('id') == 'welcome') {
                        $(this).show().siblings('.LRADMS_iframe').hide();
                        return false;
                    }
                });*/
                $(this).addClass('active').siblings('.menuTab').removeClass('active');
                
                var items = $("#content-main > div");
                for (var i = 0; i < items.length; i++) {
                	$(items[i]).hide();
                }
                
                if ($(this).data('id') == -1) {
                	//$('.mainContent .LRADMS_iframe').show();
                	$('.mainContent .LRADMS_iframe').show();
                } else {
                	$('.mainContent .LRADMS_iframe').hide();
                	$("#"+$(this).attr('el-id')).show();
                }
                
                $.learuntab.scrollToTab(this);
            }
        },
        
        lock: function() {
            Ext.create("Ext.window.Window", {
                width: 500,
                height: 200,
                title: "解锁",
                pwd: "",
                closable: false,
                modal:true,
                items: [{
                    margin: "40 0 10 55",
                    xtype: "textfield",
                    inputType: "password",
                    name: "pwd",
                    fieldLabel: "请输入锁定密码"
                }, {
                    margin: "0 0 10 70",
                    xtype: "label",
                    hidden: true,
                    html: '<span style="color:red">密码错误，请重新输入</span>'
                }],
                buttons: [{
                    text: "确定",
                    key: "ENTER",
                    listeners: {
                        click: function(btn) {
                            var window = btn.up("window"),
                                input = window.down("textfield[name=pwd]"),
                                label = window.down("label");
                            if (window.pwd) {
                                if (input.getValue() == window.pwd) {
                                    window.close();
                                } else {
                                    label.show();
                                }
                            } else {
                                window.pwd = input.getValue();
                                input.setFieldLabel('请输入解锁密码');
                                input.setValue("");
                            }
                        }
                    }
                }]
            }).show();
        },
        goHome: function() {
            var tabElement = $(".menuTab:first"),
            	currentId = $(tabElement).data('id');
            if (!$(tabElement).hasClass('active')) {
                $('.mainContent .LRADMS_iframe').each(function () {
                    if ($(tabElement).data('id') == currentId) {
                        $(tabElement).show().siblings('.LRADMS_iframe').hide();
                        return false;
                    }
                });
                $(tabElement).addClass('active').siblings('.menuTab').removeClass('active');
                
                var items = $("#content-main > div");
                for (var i = 0; i < items.length; i++) {
                	$(items[i]).hide();
                }
                
                if ($(tabElement).data('id') == -1) {
                	$('.mainContent .LRADMS_iframe').show();
                } else {
                	$('.mainContent .LRADMS_iframe').hide();
                	$("#"+$(tabElement).attr('el-id')).show();
                }
                
                $.learuntab.scrollToTab(tabElement);
            }
        },
        closeOtherTabs: function () {
        	//$.learuntab.goHome();
        	$.learuntab.scrollToTab($(".menuTab:first"));
            $('.page-tabs-content').children("[data-id]").find('.icon-remove').parents('a').not(".active").each(function () {
                $(this).remove();
                $("#"+$(this).attr('el-id')).remove();
            });
            $('.page-tabs-content').css("margin-", "0");
        },
        closeTab: function () {
            var closeTabId = $(this).parents('.menuTab').data('id');
            var currentWidth = $(this).parents('.menuTab').width();
            // 当前如果是选中的
            if ($(this).parents('.menuTab').hasClass('active')) {
            	// 如果还有下一个title的话，下一个高亮
                if ($(this).parents('.menuTab').next('.menuTab').size()) {
                    var activeId = $(this).parents('.menuTab').next('.menuTab:eq(0)').data('id');
                    $(this).parents('.menuTab').next('.menuTab:eq(0)').addClass('active');
                    // 将其他的先隐藏起来
                    var items = $("#content-main > div");
                    for (var i = 0; i < items.length; i++) {
                    	$(items[i]).hide();
                    }
                    
                    $("#"+$(this).parents('.menuTab').next('.menuTab:eq(0)').attr('el-id')).show();
                    var marginLeftVal = parseInt($('.page-tabs-content').css('margin-'));
                    if (marginLeftVal < 0) {
                        $('.page-tabs-content').animate({
                            marginLeft: (marginLeftVal + currentWidth) + 'px'
                        }, "fast");
                    }
                    $(this).parents('.menuTab').remove();
                    $('.mainContent .LRADMS_iframe').each(function () {
                        if ($(this).data('id') == closeTabId) {
                            $(this).remove();
                            return false;
                        }
                    });
                }
                // 如果前面还有控件
                if ($(this).parents('.menuTab').prev('.menuTab').size()) {
                    var activeId = $(this).parents('.menuTab').prev('.menuTab:last').data('id');
                    $(this).parents('.menuTab').prev('.menuTab:last').addClass('active');
                    /*$('.mainContent .LRADMS_iframe').each(function () {
                        if ($(this).data('id') == activeId) {
                            $(this).show().siblings('.LRADMS_iframe').hide();
                            return false;
                        }
                    });*/
                    
                    var items = $("#content-main > div");
                    for (var i = 0; i < items.length; i++) {
                    	$(items[i]).hide();
                    }
                    
                    $("#"+$(this).parents('.menuTab').prev('.menuTab:eq(0)').attr('el-id')).show();
                    
                    $(this).parents('.menuTab').remove();
                    if (activeId == -1) {
                    	$('.mainContent .LRADMS_iframe').show();
                    }
                } 
                
                $("#"+$(this).parents('.menuTab').attr('el-id')).remove();
            }
            else {
                $(this).parents('.menuTab').remove();
                /*$('.mainContent .LRADMS_iframe').each(function () {
                    if ($(this).data('id') == closeTabId) {
                        $(this).remove();
                        return false;
                    }
                });*/
                $.learuntab.scrollToTab($('.menuTab.active'));
            }
            return false;
        },
        addNote: function() {
        	var lis = $(".menuTab"),
	    		link = $(this).attr('page'), 
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
	            
	            if ($(this).attr('page')) {
		        	var link = $(this).attr('page'), 
		        		item = Ext.create(link, {ggid: $(this).attr('ggid'), height: $(".mainContent").css('height'),renderTo: 'content-main', style: 'border-width: 1px;border-style: solid;border-color: rgb(207, 207, 207);border-image: initial;background: rgb(255, 255, 255);'});
		        	
		            var str = '<a href="javascript:;" class="active menuTab" el-id="'+item.id+'"data-id="' + $('.page-tabs-content').children().length + '">' + '系统公告' + ' <i class="icon icon-remove"></i></a>';
		            $('.menuTab').removeClass('active');
		            $('.mainContent').find('iframe.LRADMS_iframe').hide();
		            $('.mainContent iframe:visible').load(function () {
		            });
		            $('.menuTabs .page-tabs-content').append(str);
		            $.learuntab.scrollToTab($('.menuTab.active'));
	            }
	    	}
        },
        addCard: function() {
        	var lis = $(".menuTab"),
        		link = $(this).attr('page'), 
	    		exists = false,
	    		id;
        	
	    	if (!link) {
	    		return;
	    	}
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
	            
	            if ($(this).attr('page')) {
		        	var item = Ext.create(link, {height: $(".mainContent").css('height'),renderTo: 'content-main', style: 'border-width: 1px;border-style: solid;border-color: rgb(207, 207, 207);border-image: initial;background: rgb(255, 255, 255);'});
		        	
		            var str = '<a href="javascript:;" className="'+link+'" class="active menuTab" el-id="'+item.id+'"data-id="' + $('.page-tabs-content').children().length + '">' + $(this).find('h5').html() + ' <i class="icon icon-remove"></i></a>';
		            $('.menuTab').removeClass('active');
		            $('.mainContent').find('iframe.LRADMS_iframe').hide();
		            $('.mainContent iframe:visible').load(function () {
		            });
		            $('.menuTabs .page-tabs-content').append(str);
		            $.learuntab.scrollToTab($('.menuTab.active'));
	            }
	    	}
        },
        addTab: function () {
        	var lis = $(".menuTab"),
        		exists = false,
        		id;
        	
        	for (var i = 0; i < lis.length; i++) {
        		if ($(lis[i]).attr('className') == $(this).attr('xtype')) {
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
        		$(".navbar-custom-menu>ul>li.open").removeClass("open");
                var dataId = $(this).attr('data-id'),
                	edit = parseInt($(this).attr('edit')),
                	del = parseInt($(this).attr('delete')),
                	audit = parseInt($(this).attr('audit')),
                	print = parseInt($(this).attr('print')),
                	exp = parseInt($(this).attr('export')),
                	end = parseInt($(this).attr('end'));
                
                if (dataId != "") {
                    //top.$.cookie('nfine_currentmoduleid', dataId, { path: "/" });
                }
                var dataUrl = $(this).attr('xtype');
                var menuName = $.trim($(this).text());
                var flag = true;
                if (dataUrl == undefined || $.trim(dataUrl).length == 0) {
                    return false;
                }
                
                // 记录当前是第几个页面
                var items = $("#content-main > div");
                for (var i = 0; i < items.length; i++) {
                	$(items[i]).hide();
                }
                var item = Ext.create(dataUrl, {height: $(".mainContent").css('height'),renderTo: 'content-main', style: 'border-width: 1px;border-style: solid;border-color: rgb(207, 207, 207);border-image: initial;    background: rgb(255, 255, 255);'});
                let btns = Ext.ComponentQuery.query("button", item);
                
                if (!exp) {
            		item.notBeExport = true;
            	}
            	
                for (var i = 0; i < btns.length; i++) {
                	if (!edit && btns[i].text && (btns[i].text.indexOf('新增') > -1 || btns[i].text.indexOf('修改') > -1)) {
            			btns[i].setDisabled(true);
                	}
                	if (!del && btns[i].text && btns[i].text.indexOf('删除') > -1) {
                		btns[i].setDisabled(true);
                	}
                	if (!audit && btns[i].text && btns[i].text.indexOf('审核') > -1) {
                		btns[i].setDisabled(true);
                	}
                	if (!print && btns[i].text && btns[i].text.indexOf('打印') > -1) {
                		btns[i].setDisabled(true);
                	}
                	else if (!end && btns[i].text && btns[i].text.indexOf('终止') > -1) {
                		btns[i].setDisabled(true);
                	}
                }
            	
                if (flag) {
                    var str = '<a href="javascript:;" class="active menuTab" className="'+dataUrl+'" el-id="'+item.id+'"data-id="' + $('.page-tabs-content').children().length + '">' + menuName + ' <i class="icon icon-remove"></i></a>';
                    $('.menuTab').removeClass('active');
                    var str1 = '<iframe class="LRADMS_iframe" id="iframe' + dataId + '" name="iframe' + dataId + '"  width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" seamless></iframe>';
                    $('.mainContent').find('iframe.LRADMS_iframe').hide();
                    //$('.mainContent').append(str1);
                    //$.loading(true);
                    $('.mainContent iframe:visible').load(function () {
                        //$.loading(false);
                    });
                    $('.menuTabs .page-tabs-content').append(str);
                    $.learuntab.scrollToTab($('.menuTab.active'));
                }
                return false;
        	}
        },
        scrollTabRight: function () {
            var marginLeftVal = Math.abs(parseInt($('.page-tabs-content').css('margin-')));
            var tabOuterWidth = $.learuntab.calSumWidth($(".content-tabs").children().not(".menuTabs"));
            var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
            var scrollVal = 0;
            if ($(".page-tabs-content").width() < visibleWidth) {
                return false;
            } else {
                var tabElement = $(".menuTab:first");
                var offsetVal = 0;
                while ((offsetVal + $(tabElement).outerWidth(true)) <= marginLeftVal) {
                    offsetVal += $(tabElement).outerWidth(true);
                    tabElement = $(tabElement).next();
                }
                offsetVal = 0;
                while ((offsetVal + $(tabElement).outerWidth(true)) < (visibleWidth) && tabElement.length > 0) {
                    offsetVal += $(tabElement).outerWidth(true);
                    tabElement = $(tabElement).next();
                }
                scrollVal = $.learuntab.calSumWidth($(tabElement).prevAll());
                if (scrollVal > 0) {
                    $('.page-tabs-content').animate({
                        marginLeft: 0 - scrollVal + 'px'
                    }, "fast");
                }
            }
        },
        scrollTabLeft: function () {
            var marginLeftVal = Math.abs(parseInt($('.page-tabs-content').css('margin-')));
            var tabOuterWidth = $.learuntab.calSumWidth($(".content-tabs").children().not(".menuTabs"));
            var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
            var scrollVal = 0;
            if ($(".page-tabs-content").width() < visibleWidth) {
                return false;
            } else {
                var tabElement = $(".menuTab:first");
                var offsetVal = 0;
                while ((offsetVal + $(tabElement).outerWidth(true)) <= marginLeftVal) {
                    offsetVal += $(tabElement).outerWidth(true);
                    tabElement = $(tabElement).next();
                }
                offsetVal = 0;
                if ($.learuntab.calSumWidth($(tabElement).prevAll()) > visibleWidth) {
                    while ((offsetVal + $(tabElement).outerWidth(true)) < (visibleWidth) && tabElement.length > 0) {
                        offsetVal += $(tabElement).outerWidth(true);
                        tabElement = $(tabElement).prev();
                    }
                    scrollVal = $.learuntab.calSumWidth($(tabElement).prevAll());
                }
            }
            $('.page-tabs-content').animate({
                marginLeft: 0 - scrollVal + 'px'
            }, "fast");
        },
        scrollToTab: function (element) {
            var marginLeftVal = $.learuntab.calSumWidth($(element).prevAll()), marginRightVal = $.learuntab.calSumWidth($(element).nextAll());
            var tabOuterWidth = $.learuntab.calSumWidth($(".content-tabs").children().not(".menuTabs"));
            var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
            var scrollVal = 0;
            if ($(".page-tabs-content").outerWidth() < visibleWidth) {
                scrollVal = 0;
            } else if (marginRightVal <= (visibleWidth - $(element).outerWidth(true) - $(element).next().outerWidth(true))) {
                if ((visibleWidth - $(element).next().outerWidth(true)) > marginRightVal) {
                    scrollVal = marginLeftVal;
                    var tabElement = element;
                    while ((scrollVal - $(tabElement).outerWidth()) > ($(".page-tabs-content").outerWidth() - visibleWidth)) {
                        scrollVal -= $(tabElement).prev().outerWidth();
                        tabElement = $(tabElement).prev();
                    }
                }
            } else if (marginLeftVal > (visibleWidth - $(element).outerWidth(true) - $(element).prev().outerWidth(true))) {
                scrollVal = marginLeftVal - $(element).prev().outerWidth(true);
            }
            $('.page-tabs-content').animate({
                marginLeft: 0 - scrollVal + 'px'
            }, "fast");
        },
        calSumWidth: function (element) {
            var width = 0;
            $(element).each(function () {
                width += $(this).outerWidth(true);
            });
            return width;
        },
        init: function () {
            $('.menuItem').on('click', $.learuntab.addTab);
            $('#gohome').on('click', $.learuntab.goHome);
            $('#lock').on('click', $.learuntab.lock);
            $('.menuTabs').on('click', '.menuTab i', $.learuntab.closeTab);
            $('.menuTabs').on('click', '.menuTab', $.learuntab.activeTab);
            $('.tabLeft').on('click', $.learuntab.scrollTabLeft);
            $('.tabRight').on('click', $.learuntab.scrollTabRight);
            $('.tabReload').on('click', $.learuntab.refreshTab);
            
            $('.tabCloseCurrent').on('click', function () {
                $('.page-tabs-content').find('.active i').trigger("click");
            });
            $('.tabCloseAll').on('click', function () {
            	$.learuntab.goHome();
            	
                $('.page-tabs-content').children("[data-id]").find('.icon-remove').each(function () {
                    //$('.LRADMS_iframe[data-id="' + $(this).data('id') + '"]').remove();
                	$("#content-main > div:gt(0)").remove();
                	$(this).parents('a').remove();
                	$("#"+$(this).attr('el-id')).remove();
                });
                /*$('.page-tabs-content').children("[data-id]:first").each(function () {
                    $('.LRADMS_iframe[data-id="' + $(this).data('id') + '"]').show();
                    $(this).addClass("active");
                });*/
                $('.mainContent .LRADMS_iframe').show();
                $('.page-tabs-content').css("margin-", "0");
            });
            $('.tabCloseOther').on('click', $.learuntab.closeOtherTabs);
            $('.fullscreen').on('click', function () {
                if (!$(this).attr('fullscreen')) {
                    $(this).attr('fullscreen', 'true');
                    $.learuntab.requestFullScreen();
                } else {
                    $(this).removeAttr('fullscreen')
                    $.learuntab.exitFullscreen();
                }
            });
        }
    };
    $.learunindex = {
        load: function () {
            $("body").removeClass("hold-transition")
            $("#content-wrapper").find('.mainContent').height($(window).height() - 100);
            $(window).resize(function (e) {
                $("#content-wrapper").find('.mainContent').height($(window).height() - 100);
            });
            $(".sidebar-toggle").click(function () {
                if (!$("body").hasClass("sidebar-collapse")) {
                    $("body").addClass("sidebar-collapse");
                } else {
                    $("body").removeClass("sidebar-collapse");
                }
            })
            $(window).load(function () {
                window.setTimeout(function () {
                    $('#ajax-loader').fadeOut();
                }, 300);
            });
        },
        jsonWhere: function (data, action) {
            if (action == null) return;
            var reval = new Array();
            $(data).each(function (i, v) {
                if (action(v)) {
                    reval.push(v);
                }
            })
            return reval;
        },
        loadMenu: function () {
			Utils.ajax('/admin/menu', {model: Ext.fly('model').getValue(), UserControlCode: Ext.fly('UserControlCode').getValue()} , function(callback) {
				var _html = "";
				var data = callback.root;
				debugger;
				$.each(data, function (i) {
	                var row = data[i];
	                // 获取最外面一层
	                if (row.menuid.length == 1) {
	                    _html += '<li class="treeview">';
	                    _html += '<a href="#">'
	                    _html += '<i class="' + row.icon + '"></i><span>' + row.text + '</span>'
	                    _html += '</a>'
	                    // 获取所有的子集,包括为0的和为1的
	                    var childNodes = $.learunindex.jsonWhere(data, function (v) { 
	                    	return v.menuid.startWith(row.menuid);
	                    });
	                    //子菜单大于0,加载子菜单	//判定是否有关联子级
	                    if (row.sn > 0){
						    _html +='<DIV class=sub-menu>'
							_html +='<TABLE>'
							_html +='<TBODY>'
							_html +='<TR>'
							for (var i = 1; i <= row.sn; i++ ) {
								_html +='<TD vAlign=top';
								
								var idx = 0;
								
								if (i > 1) {
									_html = _html + ' style="border-left:1px dashed #bbb;"';
								}
								_html  += '>';
								for (var j = 0; j < childNodes.length; j++) {
									var subrow = childNodes[j];
									if (subrow.sn == i) {
										if (idx == 0) {
											_html +='<UL>'+'<h5>'+subrow.title+'</h5>';
										}
										idx++;
										_html += '<li>';
										_html += '<a>';
										_html += '<div class="'+ (subrow.open ? "menuItem" : "disableItem") +'" data-id="' + subrow.menuid + '" edit= "'+subrow.edit+'"  delete= "'+subrow.delete+'"  audit= "'+subrow.audit+'" print = "'+subrow.print+'" export = "'+subrow.export+'"  end = "'+subrow.end+'" xtype="' + subrow.page + '"><i class="' + subrow.IconCls + '"></i>' + subrow.text+'</div>';
										_html +=  '</a>';
										_html += '</li>';
									}
								}							   
								_html +='</UL>'
								_html +='</TD>'
							}
							_html +='</TR>'
							_html +='</TBODY>'
							_html +='</TABLE>'
							_html +='</DIV> '
						}
	                    _html += '</li>'					
	                }
	            });
				
				$("#sidebar-menu").append(_html);
				
				$.learuntab.init();
			});
	  }
    };
    $(function () {
        $.learunindex.load();
        $.learunindex.loadMenu();
    });
})(jQuery);