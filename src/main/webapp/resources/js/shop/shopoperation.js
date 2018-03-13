/*
*
* */
$(function () {
    const shopId = getQueryString('shopId')
    const isEdit = shopId ? true : false

    const initUrl = '/o2o/shopadmin/getshopinitinfo'
    const registerShopUrl = '/o2o/shopadmin/registershop'
    const shopInfoUrl = '/o2o/shopadmin/getshopbyid?shopId=' + shopId
    const editShopUrl = '/o2o/shopadmin/modifyshop'

    if (isEdit) {
        getShopInfo(shopId)
    } else {
        getShopInitInfo()
    }

    function getShopInfo(shopId) {
        axios.get(shopInfoUrl)
            .then(function (res) {
                const data = res.data
                if (data.success) {
                    const shop = data.shop
                    $('#shop-name').val(shop.shopName)
                    $('#shop-addr').val(shop.shopAddr)
                    $('#shop-phone').val(shop.phone)
                    $('#shop-desc').val(shop.shopDesc)

                    const shopCategory = '<option data-id="'
                        + shop.shopCategory.shopCategoryId + '" selected>'
                        + shop.shopCategory.shopCategoryName + '</option>'
                    let tempAreaHtml = ''
                    data.areaList.map(function (item, index) {
                        tempAreaHtml += '<option data-id="' + item.areaId + '">'
                            + item.areaName + '</option>'
                    })
                    $('#shop-category').html(shopCategory)
                    $('#shop-category').attr('disabled', 'disabled')
                    $('#area').html(tempAreaHtml)
                    $('#area').attr('data-id', shop.areaId)
                }
            })
    }

    function getShopInitInfo() {
        axios.get(initUrl)
            .then(function (res) {
                const data = res.data
                if (data.success) {
                    let tempHtml = ''
                    let tempAreaHtml = ''
                    data.shopCategoryList.map((item, index) => {
                        tempHtml += '<option data-id="' + item.shopCategoryId + '">'
                            + item.shopCategoryName + '</option>'
                    })
                    data.areaList.map((item, index) => {
                        tempAreaHtml += '<option data-id="' + item.areaId + '">'
                            + item.areaName + '</option>'
                    })
                    $("#shop-category").html(tempHtml)
                    $("#area").html(tempAreaHtml)
                }
            })
    }

    $("#submit").click(() => {
        const shop = {}
        if (isEdit) {
            shop.shopId = shopId
        }
        shop.shopName = $('#shop-name').val()
        shop.shopAddr = $('#shop-addr').val()
        shop.phone = $('#shop-phone').val()
        shop.shopDesc = $('#shop-desc').val()
        shop.shopCategory = {
            shopCategoryId: $('#shop-category').find('option').not(function () {
                return !this.selected
            }).data('id')
        }
        shop.area = {
            areaId: $('#area').find('option').not(function () {
                return !this.selected
            }).data('id')
        }
        const shopImg = $('#shop-img')[0].files[0]
        const formData = new FormData()
        // 商铺图片
        formData.append('shopImg', shopImg)
        // 商铺信息
        formData.append('shopStr', JSON.stringify(shop))
        const verifyCodeActual = $('#j_captcha').val()
        if (!verifyCodeActual) {
            $.toast('请输入验证码！')
            return
        }
        // 验证码信息
        formData.append('verifyCodeActual', verifyCodeActual)

        $.ajax({
            url: (isEdit ? editShopUrl : registerShopUrl),
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: (data) => {
                if (data.success) {
                    $.toast('提交成功！')
                } else {
                    $.toast('提交失败！' + data.errMsg)
                }
                // 提交后，无论提交结果是否成功，都需要刷新验证码。
                $('#captcha_img').click()
            }
        })
    })
})