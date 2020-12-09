package com.shay.loginandregistermodule.data.model;

//固定参数请到baselibrary查看apiutils
public class AliSmsRequestParam {
    public String getPhoneNumbers() {
        return PhoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        PhoneNumbers = phoneNumbers;
    }

    public String getSignName() {
        return SignName;
    }

    public void setSignName(String signName) {
        SignName = signName;
    }

    public String getTemplateCode() {
        return TemplateCode;
    }

    public void setTemplateCode(String templateCode) {
        TemplateCode = templateCode;
    }

    public String getTemplateParam() {
        return TemplateParam;
    }

    public void setTemplateParam(String templateParam) {
        TemplateParam = templateParam;
    }

    //接受的手机号码
    String PhoneNumbers;
    //申请的签名，在ali控制台
    String SignName;
    //模板编号，在ali控制台
    String TemplateCode;
    //模板对应自定义参数值${code},自定义验证码
    String TemplateParam;
}
