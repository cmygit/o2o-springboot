$(function() {
    // 定义访问后台，获取头条列表以及一级类别列表的url
    const url = '/o2o/frontend/listmainpageinfo';

    // 访问后台，获取头条列表以及以及类别列表
    $.getJSON(url, function (data) {
        if (data.success) {
            // 获取后台传递过来的头条列表
            const headLineList = data.headLineList;
            let swiperHtml = '';
            // 遍历头条列表，并拼接出轮播图组
            headLineList.map(function (item, index) {
                swiperHtml += ''
                            + '<div class="swiper-slide img-wrap">'
                            +      '<img class="banner-img" src="'+ getContextPath() + item.lineImg +'" alt="'+ item.lineName +'">'
                            + '</div>';
            });
            // 将轮播图组赋值给前端HTML控件
            $('.swiper-wrapper').html(swiperHtml);
            // 设定轮播图轮换时间为1.5s
            $(".swiper-container").swiper({
                autoplay: 1500,
                // 用户对轮播图操作时，是否自动停止autoplay
                autoplayDisableOnInteraction: false
            });
            // 获取后台传递过来的大类列表
            const shopCategoryList = data.shopCategoryList;
            let categoryHtml = '';
            // 遍历大类列表，拼接出俩俩（col-50）一行的类别
            shopCategoryList.map(function (item, index) {
                categoryHtml += ''
                             +  '<div class="col-50 shop-classify" data-category='+ item.shopCategoryId +'>'
                             +      '<div class="word">'
                             +          '<p class="shop-title">'+ item.shopCategoryName +'</p>'
                             +          '<p class="shop-desc">'+ item.shopCategoryDesc +'</p>'
                             +      '</div>'
                             +      '<div class="shop-classify-img-warp">'
                             +          '<img class="shop-img" src="'+ getContextPath() + item.shopCategoryImg +'">'
                             +      '</div>'
                             +  '</div>';
            });
            // 将拼接好的类别赋值给前端HTML控件进行展示
            $('.row').html(categoryHtml);
        }
    });

    // 若点击“我的”，则显示侧栏
    $('#me').click(function () {
        $.openPanel('#panel-left-demo');
    });

    $('.row').on('click', '.shop-classify', function (e) {
        const shopCategoryId = e.currentTarget.dataset.category;
        const newUrl = '/o2o/frontend/shoplist?parentId=' + shopCategoryId;
        window.location.href = newUrl;
    });

});
