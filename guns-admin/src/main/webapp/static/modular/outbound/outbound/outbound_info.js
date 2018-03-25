/**
 * 初始化字典详情对话框
 */
var OutboundInfoDlg = {
    count: $("#itemSize").val(),
    itemTemplate: $("#itemTemplate").html(),
    validateFields: {
        customername: {
            validators: {
                notEmpty: {
                    message: '客户单位名称不能为空'
                }
            }
        },
        outbounddate: {
            validators: {
                notEmpty: {
                    message: '出库日期不能为空'
                }
            }
        }
    }
};

/**
 * item获取新的id
 */
OutboundInfoDlg.newId = function () {
    if (this.count == undefined) {
        this.count = 0;
    }
    this.count = this.count + 1;
    return "outboundItem" + this.count;
};

/**
 * 关闭此对话框
 */
OutboundInfoDlg.close = function () {
    parent.layer.close(window.parent.Outbound.layerIndex);
};

/**
 * 添加条目
 */
OutboundInfoDlg.addItem = function () {
    $("#itemsArea").append(this.itemTemplate);
    $("#outboundItem").attr("id", this.newId());
};

/**
 * 删除item
 */
OutboundInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
};

/**
 * 清除为空的item Dom
 */
OutboundInfoDlg.clearNullDom = function () {
    /* $("[name='outboundItem']").each(function () {
     var num = $(this).find("[name='itemNum']").val();
     var name = $(this).find("[name='itemName']").val();
     if (num == '' || name == '') {
     $(this).remove();
     }
     });*/
};

/**
 * 收集添加字典的数据
 */
OutboundInfoDlg.collectData = function () {
    this.clearNullDom();
    var detail = [];
    $("[name='outboundItem']").each(function () {
        var item = {};
        item.materialno = $(this).find("[name='materialno']").val();
        item.materialname = $(this).find("[name='materialname']").val();
        item.unit = $(this).find("[name='unit']").val();
        item.number = $(this).find("[name='number']").val();
        item.pinumber = $(this).find("[name='pinumber']").val();
        item.jiannumber = $(this).find("[name='jiannumber']").val();
        detail.push(item);
    });
    this.customername = $("#customername").val();
    this.customerreceive = $("#customerreceive").val();
    this.warehousedelivery = $("#warehousedelivery").val();
    this.outbounddate = $("#outbounddate").val();
    this.detail = detail;
};


/**
 * 提交添加
 */
OutboundInfoDlg.addSubmit = function () {
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/outbound/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Outbound.table.refresh();
        OutboundInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    var outbound = {};
    outbound.customername = this.customername;
    outbound.customerreceive = this.customerreceive;
    outbound.warehousedelivery = this.warehousedelivery;
    outbound.outbounddate = this.outbounddate;
    ajax.set('outboundStr', JSON.stringify(outbound));
    ajax.set('detailStr', JSON.stringify(this.detail));
    ajax.start();
};

/**
 * 提交修改
 */
OutboundInfoDlg.editSubmit = function () {
    this.collectData();
    if (!this.validate()) {
        return;
    }
    var ajax = new $ax(Feng.ctxPath + "/outbound/update", function (data) {
        Feng.success("修改成功!");
        window.parent.Outbound.table.refresh();
        OutboundInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    var outbound = {};
    outbound.id = $("#id").val();
    outbound.customername = this.customername;
    outbound.customerreceive = this.customerreceive;
    outbound.warehousedelivery = this.warehousedelivery;
    outbound.outbounddate = this.outbounddate;
    ajax.set('outboundStr', JSON.stringify(outbound));
    ajax.set('detailStr', JSON.stringify(this.detail));
    ajax.start();
};
