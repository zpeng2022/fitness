var topMenuHtml = '';

//递归获取导航栏
var getChild22 = function (item, ht) {
    ht += '<dl class="layui-nav-child layui-anim layui-anim-upbit">';
    $.each(item, function (index, child) {
        if (child.children != null && child.children.length > 0) {//如果还有子孩子
            ht += '<a>' + child.title + '</a>';
            ht += getChild2(child.children, "");
        } else {
            ht += '<dd><a href="javascript:;" data-url="' + child.url + '" data-title="' + child.title + '" data-id="' + child.id + '" class="menuNvaBar">';
            ht += child.title + '</a></dd>';
        }
    });
    ht += "</dl>"
    return ht;
};

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
            //直接用唯一的children生成菜单
            var child=item.children;
            topMenuHtml += '<li class="layui-nav-item layui-hide-xs">';
            topMenuHtml += '<a href="javascript:;" data-url="'+child.url+'" data-title="'+child.title
                +'" data-id="'+child.id+'" class="menuNvaBar">';
            topMenuHtml += child.title;
            topMenuHtml += '</a></li>';
        }
    });
}