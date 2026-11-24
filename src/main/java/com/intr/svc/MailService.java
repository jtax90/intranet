package com.intr.svc;

import java.util.HashMap;

import org.springframework.ui.Model;

public interface MailService {
	// 메일 전송
	String sendMail(Model model, HashMap<String, Object> paramMap) throws Exception;
}
