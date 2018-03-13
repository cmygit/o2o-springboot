$(function () {
    let shopId = getQueryString('shopId')
    const shopInfoUrl = '/o2o/shopadmin/getshopmanagementinfo?shopId=' + shopId
    axios.get(shopInfoUrl)
        .then(function (res) {
            const data = res.data
            if (data.redirect) {
                window.location.href = data.url
            } else {
                if (data.shopId) {
                    shopId = data.shopId
                }
                // $('#shopInfo').attr('href', '/o2o/shopadmin/shopoperation?shopId=' + shopId)
            }

        })
})