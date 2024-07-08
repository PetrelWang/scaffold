package com.bbc.controller;

import com.bbc.common.QwWeiXinUtil;
import com.bbc.dto.ResponseResult;
import com.bbc.dto.WeiXinUserInfoDTO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @Title: TestController
 * @Author WangHaoWei
 * @Package com.bbc.controller
 * @Date 2024/2/6 17:15
 * @description:
 */
@RestController
@Slf4j
public class QWLoginController {
    @Autowired
    private QwWeiXinUtil qwWeiXinUtil;

    /**
     * 加载二维码
     *
     * @return
     */
    @GetMapping("getWecom")
    @ApiOperation("企微登陆获取二维码信息")
    @ResponseBody
    public ResponseResult getErWeiMa() throws UnsupportedEncodingException {
        ResponseResult res = new ResponseResult();
        res.setData(qwWeiXinUtil.loginGetErWeiMa());
        return res;
    }
    @GetMapping("/check")
    public void check(String msg_signature ,String timestamp,String nonce,String echostr){
        System.out.println("开始验证");
    }
    @GetMapping("/")
    @ApiOperation("回调并获取用户信息")
    public ResponseResult getCode(HttpServletRequest request, String code) {
        if(StringUtils.isEmpty(code)){
            ResponseResult.fail("请重新扫码登陆");
        }
        try {
            ResponseResult ajax = ResponseResult.success();
            //todo 此处是前端传入code
            String userId = qwWeiXinUtil.getUserID(code);
            WeiXinUserInfoDTO weiXinUserInfoDTO = qwWeiXinUtil.getUserInfo(userId);
            System.out.println(weiXinUserInfoDTO.toString());
            //获取到手机号并与库中关联并生成令牌
            //String token = loginService.loginByWecom(weiXinUserInfoDTO.getMobile());
            //log.info(token);
            //ajax.setData(token);
            return ajax;
        } catch (Exception e) {
            return ResponseResult.fail(e.getMessage());
        }
    }

}
