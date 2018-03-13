/*
*
* */
$(function () {
    getList()

    function getList(e) {
        axios.get('/o2o/shopadmin/getshoplist')
            .then(function (res) {
                const data = res.data
                if (data.success) {
                    handleList(data.shopList)
                    handleUser(data.user)
                }
            })
    }

    function handleList(shopList) {
        let tempHtml = ''
        shopList.map(function (item, index) {
            tempHtml += '<div class="row row-shop"><div class="col-40">'
                + item.shopName + '</div><div class="col-40">'
                + shopStatus(item.enableStatus)
                + '</div><div class="col-20">'
                + goShop(item.enableStatus, item.shopId)
                + '</div></div>'
        })
        $('.shop-wrap').html(tempHtml)
    }

    function handleUser(user) {
        $('#user-name').text(user.name)
    }

    function shopStatus(status) {
        if (status === 0) {
            return '审核中'
        } else if (status === -1) {
            return '店铺非法'
        } else if (status === 1) {
            return '审核通过'
        }
    }
    
    function goShop(status, id) {
        if (status === 1) {
            return '<a href="/o2o/shopadmin/shopmanage?shopId=' + id + '">进入</a>'
        } else {
            return ''
        }
    }
})