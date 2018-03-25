/**
 * 初始化字典详情对话框
 */
var InboundInfoDlg = {
    count: $("#itemSize").val(),
    itemTemplate: $("#itemTemplate").html(),
    // validateFields: {
    //     inbounddate: {
    //         validators: {
    //             notEmpty: {
    //                 message: '入库日期不能为空'
    //             }
    //         }
    //     }
    // }
};

/**
 * item获取新的id
 */
InboundInfoDlg.newId = function () {
    if (this.count == undefined) {
        this.count = 0;
    }
    this.count = this.count + 1;
    return "inboundItem" + this.count;
};

/**
 * 关闭此对话框
 */
InboundInfoDlg.close = function () {
    parent.layer.close(window.parent.Inbound.layerIndex);
};

/**
 * 添加条目
 */
InboundInfoDlg.addItem = function () {
    $("#itemsArea").append(this.itemTemplate);
    $("#inboundItem").attr("id", this.newId());
};

/**
 * 删除item
 */
InboundInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
};

/**
 * 清除为空的item Dom
 */
InboundInfoDlg.clearNullDom = function () {
    /* $("[name='inboundItem']").each(function () {
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
InboundInfoDlg.collectData = function () {
    this.clearNullDom();
    var detail = [];
    $("[name='inboundItem']").each(function () {
        var item = {};
        item.materialno = $(this).find("[name='materialno']").val();
        item.materialname = $(this).find("[name='materialname']").val();
        item.unit = $(this).find("[name='unit']").val();
        item.number = $(this).find("[name='number']").val();
        item.pinumber = $(this).find("[name='pinumber']").val();
        item.jiannumber = $(this).find("[name='jiannumber']").val();
        detail.push(item);
    });
    this.charger = $("#charger").val();
    this.agent = $("#agent").val();
    this.receiver = $("#receiver").val();
    this.inbounddate = $("#inbounddate").val();
    this.detail = detail;
};

/**
 * 验证数据是否为空
 */
InboundInfoDlg.validate = function () {
    $('#inboundInfoForm').data("bootstrapValidator").resetForm();
    $('#inboundInfoForm').bootstrapValidator('validate');
    return $("#inboundInfoForm").data('bootstrapValidator').isValid();
}


/**
 * 提交添加字典
 */
InboundInfoDlg.addSubmit = function () {
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/inbound/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Inbound.table.refresh();
        InboundInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    var inbound = {};
    inbound.charger = this.charger;
    inbound.agent = this.agent;
    inbound.receiver = this.receiver;
    inbound.inbounddate = this.inbounddate;
    ajax.set('inboundStr', JSON.stringify(inbound));
    ajax.set('detailStr', JSON.stringify(this.detail));
    ajax.start();
};

/**
 * 提交修改
 */
InboundInfoDlg.editSubmit = function () {
    this.collectData();
    if (!this.validate()) {
        return;
    }
    var ajax = new $ax(Feng.ctxPath + "/inbound/update", function (data) {
        Feng.success("修改成功!");
        window.parent.Inbound.table.refresh();
        InboundInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    var inbound = {};
    inbound.id = $("#id").val();
    inbound.charger = this.charger;
    inbound.agent = this.agent;
    inbound.receiver = this.receiver;
    inbound.inbounddate = this.inbounddate;
    ajax.set('inboundStr', JSON.stringify(inbound));
    ajax.set('detailStr', JSON.stringify(this.detail));
    ajax.start();
};
$(function () {
    Feng.initValidator("inboundInfoForm", InboundInfoDlg.validateFields);
});

