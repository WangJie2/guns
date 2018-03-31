/**
 * 入库单管理初始化
 */
var Inbound = {
    id: "InboundTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Inbound.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '入库单号', field: 'inboundno', align: 'center', valign: 'middle', sortable: true},
        {title: '负责人', field: 'charger', align: 'center', valign: 'middle', sortable: true},
        {title: '进货经办人', field: 'agent', align: 'center', valign: 'middle', sortable: true},
        {title: '仓库收货人', field: 'receiver', align: 'center', valign: 'middle', sortable: true},
        {title: '入库日期', field: 'inbounddateStr', align: 'center', valign: 'middle', sortable: true},
        {title: '创建日期', field: 'createdate', align: 'center', valign: 'middle', sortable: true},
        {title: '创建人', field: 'createrName', align: 'center', valign: 'middle', sortable: false}
    ];
};

/**
 * 检查是否选中
 */
Inbound.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Inbound.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加入库单管理
 */
Inbound.openAddInbound = function () {
    var index = layer.open({
        type: 2,
        title: '添加入库单',
        area: ['1200px', '460px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/inbound/inbound_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看入库单管理详情
 */
Inbound.openInboundDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '入库单详情',
            area: ['1200px', '460px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/inbound/inbound_update/' + Inbound.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除入库单
 */
Inbound.delete = function () {
    if (this.check()) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/inbound/delete", function (data) {
                Feng.success("删除成功!");
                Inbound.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("inboundId", Inbound.seItem.id);
            ajax.start();
        }
        Feng.confirm("是否刪除该入库单?", operation);
    }
};


/**
 * 导出入库单
 */
$("#export").click(function () {
    var beginTime = $("#beginTime").val();
    var endTime = $("#endTime").val();
    var inboundno = $("#inboundno").val();
    $("#export").attr("href", "/inbound/export?beginTime=" + beginTime + "&endTime=" + endTime + "&inboundno=" + inboundno);
    $("#export").submit();
});

/**
 * 查询入库单列表
 */
Inbound.search = function () {
    var queryData = {};
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    queryData['inboundno'] = $("#inboundno").val();
    Inbound.table.refresh({query: queryData});
};

$(function () {
    init();
    var defaultColunms = Inbound.initColumn();
    var table = new BSTable(Inbound.id, "/inbound/list", defaultColunms);
    table.setPaginationType("server");
    Inbound.table = table.init();
});
function init() {

    var BootstrapTable = $.fn.bootstrapTable.Constructor;
    BootstrapTable.prototype.onSort = function (event) {
        var $this = event.type === "keypress" ? $(event.currentTarget) : $(event.currentTarget).parent(),
            $this_ = this.$header.find('th').eq($this.index()),
            sortName = this.header.sortNames[$this.index()];

        this.$header.add(this.$header_).find('span.order').remove();

        if (this.options.sortName === $this.data('field')) {
            this.options.sortOrder = this.options.sortOrder === 'asc' ? 'desc' : 'asc';
        } else {
            this.options.sortName = sortName || $this.data('field');
            this.options.sortOrder = $this.data('order') === 'asc' ? 'desc' : 'asc';
        }
        this.trigger('sort', this.options.sortName, this.options.sortOrder);

        $this.add($this_).data('order', this.options.sortOrder);

        // Assign the correct sortable arrow
        this.getCaret();

        if (this.options.sidePagination === 'server') {
            this.initServer(this.options.silentSort);
            return;
        }

        this.initSort();
        this.initBody();
    };
    BootstrapTable.prototype.getCaret = function () {
        var that = this;

        $.each(this.$header.find('th'), function (i, th) {
            var sortName = that.header.sortNames[i];
            $(th).find('.sortable').removeClass('desc asc').addClass((sortName || $(th).data('field')) === that.options.sortName ? that.options.sortOrder : 'both');
        });
    };
}
