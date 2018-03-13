$(function () {
    $('#log-out').click(function () {
        // 清除session
        $.ajax({
            url: '/o2o/local/logout',
            type: 'post',
            async: false,
            cache: false,
            dataType: 'json',
            success: (data) => {
                console.log(data)
                if (data.success) {
                    const usertype = $('#log-out').attr('usertype')
                    // 清除成功后退出到登录界面
                    window.location.href = '/o2o/local/login?usertype=' + usertype
                }
            },
            error: (data, error) => {
                alert(error);
            }
        })
    })
})