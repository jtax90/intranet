package com.intr.svcImpl;

import java.util.HashMap;
import java.util.Random;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.intr.dao.CoreDao;
import com.intr.dao.EmpDao;
import com.intr.dao.QueryDao;
import com.intr.dao.UtilDao;
import com.intr.svc.MailService;

@Service
@Transactional(rollbackFor = Exception.class)
public class MailServiceImpl implements MailService {
	//
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	UtilDao utilDao;

	@Autowired
	CoreDao coreDao;
	
	@Autowired
	EmpDao empDao;
	
	@Autowired
	QueryDao queryDao;
	
    @Autowired
    JavaMailSenderImpl javaMailSender;
    
	// 메일 전송
    public String sendMail(Model model, HashMap<String, Object> paramMap) throws Exception {
    	//
    	String joinCode = "";
    	HashMap<String, Object> defaultInfo = empDao.empInqyDao1020(paramMap);

    	if (defaultInfo != null) {
    		//--------------------------------------------------------------------------------------------
			// 인증코드 생성
			//--------------------------------------------------------------------------------------------
    		joinCode = String.valueOf(new Random().nextInt(10000) + 100000);

    		//--------------------------------------------------------------------------------------------
			// 제목 및 내용
			//--------------------------------------------------------------------------------------------
    		String subject = "[인트라넷] 정보 찾기 인증코드 발급 안내";
    		StringBuilder sb = new StringBuilder();
    		sb.append("[INTR] 정보 찾기 인증코드 발급 안내\n");
    		sb.append("인증 코드는 ").append(joinCode).append("입니다.\n");
    		sb.append("\n");
    		sb.append("인증 코드를 입력해주세요.");

    		//--------------------------------------------------------------------------------------------
			// 발신자, 수신자
			//--------------------------------------------------------------------------------------------
    		defaultInfo = empDao.empInqyDao1030();
    		String sender = String.valueOf(defaultInfo.get("email"));
    		String receiver = String.valueOf(paramMap.get("findEmail"));

    		//--------------------------------------------------------------------------------------------
			// 메일 구성
			//--------------------------------------------------------------------------------------------
    		MimeMessage message = javaMailSender.createMimeMessage();
    		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
    		helper.setSubject(subject);
    		helper.setText(sb.toString());
    		helper.setFrom(sender);
    		helper.setTo(receiver);

    		//--------------------------------------------------------------------------------------------
			// 메일 전송
			//--------------------------------------------------------------------------------------------
    		javaMailSender.send(message);
    	}

    	return String.format("[{'joinCode':'%s'}]", joinCode);
    }
}
