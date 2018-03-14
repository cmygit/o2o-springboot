$(function() {
    const loginUrl = '/o2o/local/logincheck';
    const registerUrl = '/o2o/local/bindlocalauth';
    // 从地址栏的url里获取usertype
	// usertype=1则为顾客，其余为店家
	const usertype = getQueryString('usertype')
	let loginCount = 0;

	// 登录事件
	$('#submit').click(function() {
		// 获取用户名
		var userName = $('#username').val();
        // 获取密码
        var password = $('#psw').val();
        // 获取验证码信息
		var verifyCodeActual = $('#j_captcha').val();
		// 是否需要验证码验证，默认为false
		var needVerify = false;
		if (loginCount >= 3) {
            // 如果登录3次都失败，
            if (!verifyCodeActual) {
				$.toast('请输入验证码！');
				return;
			} else {
            	// 西药进行验证码校验
                needVerify = true;
			}
		}
		// 访问后台进行登录验证
		$.ajax({
			url : loginUrl,
			async : false,
			cache : false,
			type : "post",
			dataType : 'json',
			data : {
				userName : userName,
				password : password,
				verifyCodeActual : verifyCodeActual,
				needVerify : needVerify
			},
			success : function(data) {
				if (data.success) {
					$.toast('登录成功！');
					if (usertype == 1) {
                        window.location.href = '/o2o/frontend/index';
                    } else {
                        window.location.href = '/o2o/shopadmin/shoplist';
                    }
				} else {
					$.toast('登录失败！');
					loginCount++;
					if (loginCount >= 3) {
                        // 如果登录3次都失败，就进行验证码校验
                        $('#verifyPart').show();
					}
				}
			}
		});
	});


	// 注册事件
    $('#register').click(function() {
        // 获取处输入的帐号
        const userName = $('#username').val();
        // 获取输入的密码
        const password = $('#psw').val();

        $.ajax({
            url : registerUrl,
            async : false,
            cache : false,
            type : "post",
            dataType : 'json',
            data : {
                userName : userName,
                password : password,
                registerFlag: 'true',
				userType: usertype,
                needVerify: 'false'
            },
            success : function(data) {
                if (data.success) {
                    $.toast('注册成功！');
                    if (usertype == 1) {
                        window.location.href = '/o2o/frontend/index';
                    } else {
                        window.location.href = '/o2o/shopadmin/shoplist';
                    }
                } else {
                    $.toast('注册失败！');
                }
            }
        });
    });
});