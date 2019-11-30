package com.diabin.fastec.example.generators;

import com.diabin.latte.annotations.AppRegisterGenerator;
import com.diabin.latte.core.wechat.templates.AppRegisterTemplate;

@AppRegisterGenerator(
        packageName = "com.diabin.fastec.example",
        registerTemplate = AppRegisterTemplate.class
)
public interface AppRegister {
}
