var layer = layui.layer;
var $ = jQuery = layui.jquery;
var form = layui.form;
var element = layui.element;
var uusername;
$(function () {
    FrameWH();
    //递归获取导航栏
    var getChild=function (item,ulHtml) {
        ulHtml += '<dl class="layui-nav-child">';
        $.each(item,function (index,child) {
            if(child.children !=null&& child.children.length>0){
                ulHtml +='<a>'+child.title+'</a>';
                ulHtml +=getChild(child.children,"");
            }else {
                ulHtml += '<dd><a href="javascript:;" data-url="'+child.url+'" data-title="'+child.title+'" data-id="'+child.id+'" class="menuNvaBar">';
                ulHtml += '<cite>'+child.title+'</cite></a></dd>';
            }
        });
        ulHtml += "</dl>"
        return ulHtml;
    };
    var getChild2 = function (item, ht) {
        ht += '<dl class="layui-nav-child layui-anim layui-anim-upbit">';
        $.each(item, function (index, child) {
            // console.log("子菜单："+index+"-"+JSON.stringify(child)+"-"+child.title)
            if (child.children != null && child.children.length > 0) {//如果还有子孩子
                // console.log("子菜单还存在子孩子")
                ht += '<a>' + child.title + '</a>';
                ht += getChild2(child.children, "");
            } else {
                // console.log("子菜单属性:"+child.url+child.title)
                ht += '<dd><a href="javascript:;" data-url="' + child.url + '" data-title="' + child.title + '" data-id="' + child.id + '" class="menuNvaBar">';
                ht += child.title + '</a></dd>';
            }
        });
        ht += "</dl>"
        // console.log("子菜单:"+ht)
        return ht;
    };
    var uusername1;
    CoreUtil.sendAjax("/sys/home/",null,null,null,null,function (res) {
        $("#deptName").html("所属部门："+res.data.userInfo.deptName);
        $("#nickName").html(res.data.userInfo.realName);
        uusername1 = res.data.userInfo.username
        var data=res.data.menus;
        if(data!= "" && data.length>0){
            /*左侧菜单内容生成*/
            //添加最外层
            //左侧导航栏取消生成
           /* var ulHtml = '<ul class="layui-nav layui-nav-tree layui-left-nav">';
            if(data!= null&&data.length > 0){//遍历菜单数据
                $.each(data,function(index,item){
                    // console.log("菜单生成："+index+"-"+JSON.stringify(item))
                    if(index == 0){//索引为0，
                        ulHtml += '<li class="layui-nav-item layui-nav-itemed">';
                    }else{
                        ulHtml += '<li class="layui-nav-item">';
                    }
                    ulHtml += '<a href="javascript:;">';
                    if(item.icon != undefined && item.icon != ''){
                        ulHtml += '<i class="layui-icon '+item.icon+'" style="padding-right: 8px; font-size: 16px" "'+
                            item.icon+'"></i>';
                    }
                    ulHtml += '<cite style="font-size: 16px">'+item.title+'</cite>';
                    ulHtml += '</a>'
                    if(item.children != null && item.children.length > 0){
                        ulHtml += '<dl class="layui-nav-child">';
                        $.each(item.children,function(index,child){
                            if(child.children !=null&& child.children.length>0){
                                ulHtml +='<a style="font-size: 16px">'+child.title+'</a>';
                                if(child.icon != undefined && child.icon != ''){
                                    ulHtml += '<i class="layui-icon '+child.icon+'" style="padding-right: 8px; font-size: 16px" "'
                                        +child.icon+'"></i>';
                                }
                                ulHtml +=getChild(child.children,"");
                            }else {
                                ulHtml += '<dd><a href="javascript:;" data-url="'+child.url+'" data-title="'+child.title
                                    +'" data-id="'+child.id+'" class="menuNvaBar">';
                                if(child.icon != undefined && child.icon != ''){
                                    ulHtml += '<i class="layui-icon '+child.icon+'" style="padding-right: 8px; font-size: 16px" "'
                                        +child.icon+'"></i>';
                                }
                                ulHtml += '<cite style="font-size: 16px">'+child.title+'</cite></a></dd>';
                            }
                        });
                        ulHtml += "</dl>"
                    }
                    ulHtml += '</li>'
                });
            }
            ulHtml += '</ul>';
            $(".navBar").html(ulHtml);*/

           //生成顶部导航栏
            var topMenuHtml = '';
            //递归获取导航栏
            if (data != null && data.length > 0) {//遍历菜单数据
                $.each(data, function (index, item) {
                    if (item.children != null && item.children.length > 1) {//子菜单数目＞1时才用列表
                        //生成目录
                        topMenuHtml += '<li class="layui-nav-item"><a href="javascript:;">';
                        topMenuHtml += item.title;
                        topMenuHtml += '<i class="layui-icon layui-icon-down layui-nav-more"></i></a>';
                        //获取子菜单
                        topMenuHtml +=getChild2(item.children,"");
                        topMenuHtml +='</li>'
                    } else if(item.children != null){
                        // console.log("唯一子菜单："+JSON.stringify(item.children))
                        //直接用唯一的children生成菜单
                        var child=item.children[0];//这里不用.child去获取item的孩子，这里item就是child
                        topMenuHtml += '<li class="layui-nav-item layui-hide-xs">';
                        topMenuHtml += '<a href="javascript:;" data-url="'+child.url+'" data-title="'+child.title
                            +'" data-id="'+child.id+'" class="menuNvaBar">';
                        topMenuHtml += child.title;
                        topMenuHtml += '</a></li>';
                    }
                });
            }
            $(".top-menu").html(topMenuHtml);

            element.init();  //初始化页面元素
        }else{
            $("#navBarId").empty();
        }
        top.layer.closeAll();
    },"GET",false);
    uusername = uusername1;
    $(document).on('click','.menuNvaBar',function () {
        var dataid = $(this);
        if ($(".layui-tab-title li[lay-id]").length <= 0) {
            active.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"), dataid.attr("data-title"), dataid.attr("data-icon"));
        } else {
            var isData = false;
            $.each($(".layui-tab-title li[lay-id]"), function () {
                if ($(this).attr("lay-id") == dataid.attr("data-id")) {
                    isData = true;
                }
            })
            if (isData == false) {
                active.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"), dataid.attr("data-title"), dataid.attr("data-icon"));
            }
        }
        active.tabChange(dataid.attr("data-id"));
    });

    $(".hideMenu").click(function(){
        $(".layui-layout-admin").toggleClass("showMenu");
        //渲染顶部窗口
        //tab.tabMove();
    })
    //退出登录
    $("#logout").click(function () {
        logoutDialog();
    });

});
//删除前确认对话框
var logoutDialog=function () {
    layer.open({
        content: '确定要退出登录么？',
        yes: function(index, layero){
            layer.close(index); //如果设定了yes回调，需进行手工关闭
            CoreUtil.sendAjax("/sys/user/logout",null,function (res) {
                top.window.location.href="/index/login";
            },"GET",true);
        }
    });
}
//触发事件
var active={
    tabAdd : function (url,id,title,icon) {
        if(url!=""&&url!=null&&url!=undefined){
            element.tabAdd('tab',{
                title: title,
                icon:icon,
                content: '<iframe data-frameid="' + id + '" frameborder="0" name="content" width="100%" src="' + url + '"></iframe>',
                id: id
            })
        }
        FrameWH();//计算框架高度
    },
    tabChange: function (id) {
        //切换到指定Tab项
        element.tabChange('tab', id); //切换到：用户管理
        $("iframe[data-frameid='" + id + "']").attr("src", $("iframe[data-frameid='" + id + "']").attr("src"))//切换后刷新框架
    },
    tabDelete: function (id) {
        element.tabDelete("tab", id);//删除
    }
};
function FrameWH() {
    console.log("iframe更新高度--------------------------------------------------------")
    var h1=document.documentElement.clientHeight-60;//60是导航栏的高度

    var h = $(window).height() - 41 - 10 - 35 - 10 - 44 - 10;
    $("iframe").css("height", h1 + "px");
};