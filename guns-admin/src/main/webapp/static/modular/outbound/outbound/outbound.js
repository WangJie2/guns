/**
 * 出库单管理初始化
 */
var Outbound = {
    id: "OutboundTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Outbound.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '出库单号', field: 'outboundno', align: 'center', valign: 'middle', sortable: true},
        {title: '客户单位名称', field: 'customername', align: 'center', valign: 'middle', sortable: true},
        {title: '客户提货', field: 'customerreceive', align: 'center', valign: 'middle', sortable: true},
        {title: '仓库发货', field: 'warehousedelivery', align: 'center', valign: 'middle', sortable: true},
        {title: '出库日期', field: 'outbounddateStr', align: 'center', valign: 'middle', sortable: true},
        {title: '创建日期', field: 'createdate', align: 'center', valign: 'middle', sortable: true},
        {title: '创建人', field: 'createrName', align: 'center', valign: 'middle', sortable: false}
    ];
};

/**
 * 检查是否选中
 */
Outbound.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Outbound.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加出库单管理
 */
Outbound.openAddOutbound = function () {
    var index = layer.open({
        type: 2,
        title: '添加出库单',
        area: ['1200px', '460px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/outbound/outbound_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看出库单管理详情
 */
Outbound.openOutboundDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '出库单详情',
            area: ['1200px', '460px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/outbound/outbound_update/' + Outbound.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除出库单
 */
Outbound.delete = function () {
    if (this.check()) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/outbound/delete", function (data) {
                Feng.success("删除成功!");
                Outbound.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("outboundId", Outbound.seItem.id);
            ajax.start();
        }
        Feng.confirm("是否刪除该出库单?", operation);
    }
};

/**
 * 导出入库单
 */
$("#export").click(function () {
    var beginTime = $("#beginTime").val();
    var endTime = $("#endTime").val();
    var outboundno = $("#outboundno").val();
    $("#export").attr("href", "/outbound/export?beginTime=" + beginTime + "&endTime=" + endTime + "&outboundno=" + outboundno);
    $("#export").submit();
});

/**
 * 查询出库单列表
 */
Outbound.search = function () {
    var queryData = {};
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    queryData['outboundno'] = $("#outboundno").val();
    Outbound.table.refresh({query: queryData});
};

$(function () {
    init();
    var defaultColunms = Outbound.initColumn();
    var table = new BSTable(Outbound.id, "/outbound/list", defaultColunms);
    table.setPaginationType("server");
    Outbound.table = table.init();
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
