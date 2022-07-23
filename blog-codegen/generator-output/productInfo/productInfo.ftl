<#include "/include/macros.ftl">
<@header></@header>
<div class="clearfix"></div>
<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <ol class="breadcrumb">
            <li><a href="/">首页</a></li>
            <li class="active">商品表管理</li>
        </ol>
        <div class="x_panel">
            <div class="x_content">
                <div class="btn-group hidden-xs" id="toolbar">
                    <@shiro.hasPermission name="productInfo:add">
                        <button id="btn_add" type="button" class="btn btn-info" title="新增商品表">
                            <i class="fa fa-plus"></i> 新增
                        </button>
                    </@shiro.hasPermission>
                    <@shiro.hasPermission name="productInfo:batchDelete">
                        <button id="btn_delete_ids" type="button" class="btn btn-danger" title="批量删除">
                            <i class="fa fa-trash-o"></i> 批量删除
                        </button>
                    </@shiro.hasPermission>
                </div>
                <table id="tablelist">
                </table>
            </div>
        </div>
    </div>
</div>
<@addOrUpdateMOdal defaultTitle="添加商品表">
    <input type="hidden" name="id">
    <div class="item form-group">
        <label class="control-label col-md-3 col-sm-3 col-xs-3" for="productId">
            productId
            <span class="required">*</span>
        </label>
        <div class="col-md-7 col-sm-7 col-xs-7">
            <input type="text" class="form-control" name="productId" id="productId" required="required" placeholder="productId"/>
        </div>
    </div>
    <div class="item form-group">
        <label class="control-label col-md-3 col-sm-3 col-xs-3" for="productName">
            productName
            <span class="required">*</span>
        </label>
        <div class="col-md-7 col-sm-7 col-xs-7">
            <input type="text" class="form-control" name="productName" id="productName" required="required" placeholder="productName"/>
        </div>
    </div>
    <div class="item form-group">
        <label class="control-label col-md-3 col-sm-3 col-xs-3" for="productPrice">
            productPrice
            <span class="required">*</span>
        </label>
        <div class="col-md-7 col-sm-7 col-xs-7">
            <input type="text" class="form-control" name="productPrice" id="productPrice" required="required" placeholder="productPrice"/>
        </div>
    </div>
    <div class="item form-group">
        <label class="control-label col-md-3 col-sm-3 col-xs-3" for="productStatus">
            productStatus
            <span class="required">*</span>
        </label>
        <div class="col-md-7 col-sm-7 col-xs-7">
            <input type="text" class="form-control" name="productStatus" id="productStatus" required="required" placeholder="productStatus"/>
        </div>
    </div>
</@addOrUpdateMOdal>
<@footer>
    <script>
        function operateFormatter(code, row, index) {
            var trId = row.id;
            var operateBtn = [
                '<@shiro.hasPermission name="productInfo:edit"><a class="btn btn-xs btn-primary btn-update" data-id="' + trId + '"><i class="fa fa-edit"></i>编辑</a></@shiro.hasPermission>',
                '<@shiro.hasPermission name="productInfo:delete"><a class="btn btn-xs btn-danger btn-remove" data-id="' + trId + '"><i class="fa fa-trash-o"></i>删除</a></@shiro.hasPermission>'
            ];
            return operateBtn.join('');
        }

        $(function () {
            var options = {
                modalName: "商品表",
                url: "/productInfo/list",
                getInfoUrl: "/productInfo/get/{id}",
                updateUrl: "/productInfo/edit",
                removeUrl: "/productInfo/remove",
                createUrl: "/productInfo/add",
                columns: [
                    {
                        checkbox: true
                    },
                    {
                        field: 'productId',
                        title: '',
                        formatter: function (code) {
                            return code ? code : '-';
                        }
                    },
                    {
                        field: 'productName',
                        title: '',
                        formatter: function (code) {
                            return code ? code : '-';
                        }
                    },
                    {
                        field: 'productPrice',
                        title: '',
                        formatter: function (code) {
                            return code ? code : '-';
                        }
                    },
                    {
                        field: 'productStatus',
                        title: '',
                        formatter: function (code) {
                            return code ? code : '-';
                        }
                    },
                    {
                        field: 'operate',
                        title: '操作',
                        width: '130px',
                        formatter: operateFormatter //自定义方法，添加操作按钮
                    }
                ]
            };
            // 初始化table组件
            var table = new Table(options);
            table.init();
        });
    </script>
</@footer>
