<html>
    <#include "../common/header.ftl">
    <body>

        <div id="wrapper" class="toggled">
            <#-- 侧边栏sidebar -->
            <#include "../common/nav.ftl">

            <#-- 主要内容content -->
            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div class="row clearfix">

                    <#--订单列表-->
                        <div class="col-md-12 column">
                            <table class="table table-bordered table-hover table-condensed">
                                <thead>
                                <tr>
                                    <th>订单id</th>
                                    <th>姓名</th>
                                    <th>手机号</th>
                                    <th>地址</th>
                                    <th>金额</th>
                                    <th>订单状态</th>
                                    <th>支付状态</th>
                                    <th>创建时间</th>
                                    <th colspan="2">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                        <#list orderMasterDTOPage.content as  orderMasterDTO>
                        <tr>
                            <td>${orderMasterDTO.orderId}</td>
                            <td>${orderMasterDTO.buyerName}</td>
                            <td>${orderMasterDTO.buyerPhone}</td>
                            <td>${orderMasterDTO.buyerAddress}</td>
                            <td>${orderMasterDTO.orderAmount}</td>
                            <td>${orderMasterDTO.getOrderStatusEnum().msg}</td>
                            <td>${orderMasterDTO.getPayStatusEnum().msg}</td>
                            <td>${orderMasterDTO.createTimestamp}</td>
                            <td>
                                <a href="/sell/seller/order/detail?orderId=${orderMasterDTO.orderId}">详情</a>
                            </td>
                            <td>
                                <#if orderMasterDTO.getOrderStatusEnum().msg == "新订单">
                                    <a href="/sell/seller/order/cancel?orderId=${orderMasterDTO.orderId}">取消</a>
                                </#if>
                            </td>
                        </tr>
                        </#list>
                                </tbody>
                            </table>
                        </div>

                    <#-- 分页 -->
                        <div class="col-md-12 column">
                            <ul class="pagination pull-right">
                        <#if currentPage lte 1>
                            <li class="disabled"><a href="#">上一页</a></li>
                        <#else>
                            <li><a href="/sell/seller/order/list?page=${currentPage-1}&limit=${limit}">上一页</a></li>
                        </#if>


                        <#list 1..orderMasterDTOPage.getTotalPages() as index>
                            <#if currentPage == index>
                                <li class="disabled"><a href="#">${index}</a></li>
                            <#else>
                                <li><a href="/sell/seller/order/list?page=${index}&limit=${limit}">${index}</a></li>
                            </#if>
                        </#list>

                        <#if currentPage gte orderMasterDTOPage.getTotalPages()>
                            <li class="disabled"><a href="#">下一页</a></li>
                        <#else>
                            <li><a href="/sell/seller/order/list?page=${currentPage+1}&limit=${limit}">下一页</a></li>
                        </#if>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
