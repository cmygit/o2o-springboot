$(function () {
    var loading = false;
    // 分页允许返回的最大条数
    var maxItems = 20;
    // 一页返回的最大条数
    var pageSize = 2;
    // 获取商品列表的url
    var listUrl = '/o2o/frontend/listproductsbyshop';
    // 页码
    var pageNum = 1;
    // 从地址栏url里获取shopId
    var shopId = getQueryString('shopId');
    // 商品类别id
    var productCategoryId = '';
    // 商品名称模糊查询
    var productName = '';
    // 获取商店详情信息的url
    var searchDivUrl = '/o2o/frontend/listshopdetailpageinfo?shopId='
        + shopId;
    // 渲染出商店详情信息以及商品类别列表以供搜索
    getSearchDivData();
    // 预先加载20条商品信息
    addItems(pageSize, pageNum);

    // 给兑换礼品的a标签赋值兑换礼品的URL
    // $('#exchangelist').attr('href', '/o2o/frontend/awardlist?shopId=' + shopId)

    function getSearchDivData() {
        // 获取本店铺信息以及商品类别信息列表
        var url = searchDivUrl;
        $.getJSON(
            url,
            function (data) {
                if (data.success) {
                    var shop = data.shop;
                    $('#shop-cover-pic').attr('src', getContextPath() + shop.shopImg);
                    $('#shop-update-time').html(
                        new Date(shop.lastEditTime)
                            .Format("yyyy-MM-dd"));
                    $('#shop-name').html(shop.shopName);
                    $('#shop-desc').html(shop.shopDesc);
                    $('#shop-addr').html(shop.shopAddr);
                    $('#shop-phone').html(shop.phone);

                    var productCategoryList = data.productCategoryList;
                    var html = '';
                    productCategoryList
                        .map(function (item, index) {
                            html += '<a href="#" class="button" data-product-search-id='
                                + item.productCategoryId
                                + '>'
                                + item.productCategoryName
                                + '</a>';
                        });
                    $('#shopdetail-button-div').html(html);
                }
            });
    }

    function addItems(pageSize, pageIndex) {
        // 生成新条目的HTML
        var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
            + pageSize + '&productCategoryId=' + productCategoryId
            + '&productName=' + productName + '&shopId=' + shopId;
        // 设定加载符，若还在后台取数据则不能再次访问后台，避免多次重复加载
        loading = true;
        // 获取当前查询条件下的商品总数
        $.getJSON(url, function (data) {
            if (data.success) {
                // 获取当前查询条件下的商品总数
                maxItems = data.count;
                var html = '';
                // 遍历商品列表，拼接出卡片集合
                data.productList.map(function (item, index) {
                    html += '' + '<div class="card" data-product-id='
                        + item.productId + '>'
                        + '<div class="card-header">' + item.productName
                        + '</div>' + '<div class="card-content">'
                        + '<div class="list-block media-list">' + '<ul>'
                        + '<li class="item-content">'
                        + '<div class="item-media">' + '<img src="'
                        + getContextPath() + item.imgAddr + '" width="44">' + '</div>'
                        + '<div class="item-inner">'
                        + '<div class="item-subtitle">' + item.productDesc
                        + '</div>' + '</div>' + '</li>' + '</ul>'
                        + '</div>' + '</div>' + '<div class="card-footer">'
                        + '<p class="color-gray">'
                        + new Date(item.lastEditTime).Format("yyyy-MM-dd")
                        + '更新</p>' + '<span>点击查看</span>' + '</div>'
                        + '</div>';
                });
                // 将卡片集合添加到目标HTML组件里
                $('.list-div').append(html);
                // 获取目前为止已显示的卡片总数，包含之前已经加载的
                var total = $('.list-div .card').length;
                if (total >= maxItems) {
                    // 若总数达到跟按照此查询条件列出来的总数一致，则隐藏加载提示符
                    $('.infinite-scroll-preloader').hide();
                } else {
                    $('.infinite-scroll-preloader').show();
                }
                // 否则页码加1，继续load出新的店铺
                pageNum += 1;
                // 加载结束，可以再次加载了
                loading = false;
                // 刷新页面，显示新加载的店铺
                $.refreshScroller();
            }
        });
    }

    // 下滑屏幕自动进行分页搜索
    $(document).on('infinite', '.infinite-scroll-bottom', function () {
        if (loading)  {
            return;
        }
        addItems(pageSize, pageNum);
    });

    // 选择新的商品类别之后，重置页码，清空原先的商品列表，按照新的名字去查询
    $('#shopdetail-button-div').on(
        'click',
        '.button',
        function (e) {
            productCategoryId = e.target.dataset.productSearchId;
            if (productCategoryId) {
                if ($(e.target).hasClass('button-fill')) {
                    $(e.target).removeClass('button-fill');
                    productCategoryId = '';
                } else {
                    $(e.target).addClass('button-fill').siblings()
                        .removeClass('button-fill');
                }
                $('.list-div').empty();
                pageNum = 1;
                addItems(pageSize, pageNum);
            }
        });

    // 点击商品的卡片进入该商品的详情页面
    $('.list-div')
        .on(
            'click',
            '.card',
            function (e) {
                var productId = e.currentTarget.dataset.productId;
                // window.location.href = '/o2o/frontend/productdetail?productId=' + productId;
            });

    $('#search').on('change', function (e) {
        productName = e.target.value;
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    $('#me').click(function () {
        $.openPanel('#panel-left-demo');
    });
    $.init();
});
