package com.cesgroup.imp.wotalk.web;

import com.cesgroup.common.web.NonEntityController;
import com.cesgroup.core.annotation.CesLog;
import com.cesgroup.imp.wotalk.service.ImportAuthService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/auth/imp")
public class AuthImportController extends NonEntityController<ImportAuthService>{


	@Override
	@Autowired
	public void setService(ImportAuthService service) {
		super.service = service;
	}

	@Override
	public String getModelName() {
		return "同步wotalk数据";
	}

	@RequestMapping(value = "/importWotalkData")
	@ResponseBody
	@RequiresPermissions(value = "/auth/imp/importWotalkData")
	public boolean importWotalkData(){
		getService().oneKeyImportAuthData();
		return true;
	}
}
