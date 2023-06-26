package controller;
// ksb 테스트
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dto.Mem;
import exception.OpenerException;
import exception.ShopException;
import service.ShopService;
import util.CipherUtil;

@Controller
@RequestMapping("mem")
public class MemController {
	@Autowired
	private ShopService service;
	@Autowired
	private CipherUtil cipher;
	
	private String passwordHash(String password) throws Exception {
		return cipher.makehash(password, "SHA-512");
	}
	
	@GetMapping("*")
	public ModelAndView all() {
		ModelAndView mav = new ModelAndView();
		mav.addObject(new Mem()); 
		return mav;
	}
	
	@PostMapping("join")
	public ModelAndView join(Mem mem, String email1, String email2) {
		int maxMemNum = service.maxMemNum();
		mem.setMem_number(maxMemNum+1);
		mem.setMem_id(email1 + "@" + email2);
		try {
			mem.setMem_pw(passwordHash(mem.getMem_pw()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(service.userInsert(mem)) {			
			service.pointInsert(mem.getMem_id());
			throw new ShopException("반갑습니다. " + mem.getMem_name() + "님 :)", "login");			
		} else {
			throw new ShopException("죄송합니다. 회원가입 시 오류가 발생했습니다.", "mem/join");
		}
	}
	
	@RequestMapping("emailForm")
	public ModelAndView emailForm(@RequestParam(value="email", required=false)String email, HttpSession ses) {
		ModelAndView mav = new ModelAndView();
		if(service.getMemEmail(email) != null) {
			throw new OpenerException("이미 가입되어있는 이메일 입니다.", "login");
		}  else if(email != null) {
			//인증번호 랜덤 생성
		    String randomkey = authCodeMaker();
		    // 발신자 정보
			String sender = "zxc2289@naver.com";
			String password = "slfflflakaqh";
			// 메일 받을 주소
			String recipient = email;
			System.out.println("inputedEmail : " + email);
			Properties prop = new Properties();
			try {
				FileInputStream fis = new FileInputStream("D:\\twerkinghamtori\\workspace\\spring\\second_prj\\src\\main\\resources\\mail.properties"); 
				// 진규 경로 : D:\java_gdu_workspace\second_prj\src\main\resources\mail.properties
				// 수빈 경로 : D:\\springstudy\\second_prj\\src\\main\\resources\mail.properties
				prop.load(fis);
				prop.put("mail.smtp.user", sender);
				System.out.println(prop);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(sender, password);
				}
			});
			MimeMessage msg = new MimeMessage(session);
			// email 전송
			try {
				try {
					msg.setFrom(new InternetAddress(sender, "HOMIEGYM 인증센터", "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

				// 메일 제목
				msg.setSubject("이메일 인증");
				// 메일 내용
				msg.setText(randomkey);
				Transport.send(msg);
				System.out.println("이메일 전송 : " + randomkey);

			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			ses.setAttribute("randomkey", randomkey);
		}		
		return mav;
	}
	
	@PostMapping("emailPwForm")
	public ModelAndView emailForm2(@RequestParam(value="email", required=false)String email, HttpSession ses) {
		ModelAndView mav = new ModelAndView();
		System.out.println(email);
		if(service.getMemEmail(email) == null) {
			throw new OpenerException("가입되지 않은 이메일입니다.", "login");
		}  else if(email != null) {
			//인증번호 랜덤 생성
		    String randomkey = authCodeMaker();
		    // 발신자 정보
			String sender = "zxc2289@naver.com";
			String password = "slfflflakaqh";
			// 메일 받을 주소
			String recipient = email;
			System.out.println("inputedEmail : " + email);
			Properties prop = new Properties();
			try {
				FileInputStream fis = new FileInputStream("D:\\twerkinghamtori\\workspace\\spring\\second_prj\\src\\main\\resources\\mail.properties"); 
				// 진규 경로 : D:\java_gdu_workspace\second_prj\src\main\resources\mail.properties
				// 수빈 경로 : D:\\springstudy\\second_prj\\src\\main\\resources\mail.properties
				prop.load(fis);
				prop.put("mail.smtp.user", sender);
				System.out.println(prop);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(sender, password);
				}
			});
			MimeMessage msg = new MimeMessage(session);
			// email 전송
			try {
				try {
					msg.setFrom(new InternetAddress(sender, "HOMIEGYM 인증센터", "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

				// 메일 제목
				msg.setSubject("이메일 인증");
				// 메일 내용
				msg.setText(randomkey);
				Transport.send(msg);
				System.out.println("이메일 전송 : " + randomkey);

			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			ses.setAttribute("randomkey", randomkey);
		}	
		mav.addObject("email",email);
		return mav;
	}
	//인증번호 확인
	@RequestMapping("emailFormchk")
	public ModelAndView emailFormchk(String authNum, @RequestParam(value="pwchg", required=false)String pwchg, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String randomkey = (String) session.getAttribute("randomkey");
		boolean able = true;
		if(!authNum.equals(randomkey)) {
			throw new ShopException("인증번호가 틀립니다.","emailForm");
		} else {
			able=true;
			mav.addObject("able", able);
			mav.setViewName("mem/emailForm");
			return mav;
		}
	}
	// 인증번호 확인
	@PostMapping("emailPwchk")
	public ModelAndView emailPwchk(String authNum, @RequestParam(value = "pwchg", required = false) String pwchg, String email, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String randomkey = (String) session.getAttribute("randomkey");
		boolean able = true;
		if (!authNum.equals(randomkey)) {
			throw new ShopException("인증번호가 틀립니다.", "emailPwForm");
		} else {
			able = true;
			mav.addObject("able", able);
			mav.addObject("email",email);
			mav.setViewName("redirect:pwChgForm");
			return mav;
		}
	}
	@GetMapping("pwChgForm")
	public ModelAndView pwChgForm(@RequestParam(value = "email", required = false) String email) {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
	@PostMapping("password1")
	public String password1(String email, String mem_pw, String mem_pw2) {
		System.out.println(email);
		System.out.println(mem_pw);
		if(!service.updatePw(email, mem_pw)) {
			throw new OpenerException("죄송합니다. 비밀번호 변경 중 오류가 발생했습니다.", "login");
		} else {
			throw new OpenerException("비밀번호가 변경되었습니다. 새로운 비밀번호로 로그인하세요.", "login");
		}
	}
	//인증번호 생성 함수
	private String authCodeMaker() {
		String authCode = null;
		
		StringBuffer temp = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			int rIndex = random.nextInt(3);
			switch (rIndex) {
			case 0:
				// a-z
				temp.append((char) ((int) (random.nextInt(26)) + 97));
				break;
			case 1:
				// A-Z
				temp.append((char) ((int) (random.nextInt(26)) + 65));
				break;
			case 2:
				// 0-9
				temp.append((random.nextInt(10)));
				break;
			}
		}
		
		authCode = temp.toString();
		System.out.println("인증번호 : " + authCode);		
		return authCode;
	}	
	
	@GetMapping("login")
	public ModelAndView loginGet (HttpSession session) {
		ModelAndView mav = new ModelAndView();
		//apiURL을 생성하여 view로 전달
		String clientId = "9wCxroKdKrQrcz_Kpy9q";
		String redirectURI = null;
		try {
			redirectURI = URLEncoder.encode("http://localhost:8080/second_prj/mem/naverLogin","UTF-8");
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		SecureRandom random = new SecureRandom(); //난수발생기
		String state = new BigInteger(130,random).toString();
		String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
		apiURL += "&client_id=" + clientId;
		apiURL += "&redirect_uri=" + redirectURI;
		apiURL += "&state=" + state; 
		mav.addObject("apiURL",apiURL);
		mav.addObject(new Mem());
		session.getServletContext().setAttribute("state", state);
		return mav;
	}
	
	@PostMapping("login")
	public ModelAndView loginPost(Mem mem, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Mem dbMem = service.getMemEmail(mem.getMem_id());
		if(dbMem == null) {
			throw new ShopException("가입되지 않은 회원입니다.", "login");
		}
		String hashPass = "";
		try {
			hashPass = passwordHash(mem.getMem_pw());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(dbMem.getMem_channel() == null) {
			if(dbMem.getMem_pw().equals(hashPass)) {
				session.setAttribute("loginMem", dbMem);
				mav.addObject("userid",dbMem.getMem_id());
				mav.setViewName("redirect:/mypage/myInfo?mem_id=" + dbMem.getMem_id());
			} else {
				throw new ShopException("아이디 또는 비밀번호를 확인하세요.", "login");
			}
		} else {
			throw new ShopException("소셜로그인을 통해 가입한 회원입니다.", "login");
		}		
		
		return mav;
	}
	
	@RequestMapping("naverLogin")
	public String naverLogin(String code, String state, HttpSession session) throws Exception {
		String clientId = "9wCxroKdKrQrcz_Kpy9q";
		String clientSecret = "6jS_AQDDhx";
		String redirectURI = URLEncoder.encode("YOUR_CALLBACK_URL", "UTF-8");
		String apiURL;
		apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
		apiURL += "client_id=" + clientId;
		apiURL += "&client_secret=" + clientSecret;
		apiURL += "&redirect_uri=" + redirectURI;
		apiURL += "&code=" + code;
		apiURL += "&state=" + state;
		String access_token = "";
		String refresh_token = ""; // 아마도 재로그인할 때 쓸듯..
		StringBuffer sb = new StringBuffer();
		System.out.println("apiURL=" + apiURL);
		try {
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			BufferedReader br;
			System.out.println("responseCode=" + responseCode);
			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8"));
			}
			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				sb.append(inputLine);
			}
			br.close();
			if (responseCode == 200) {
				System.out.println("\n===========첫번째 요청에 대한 응답메세지 (sb 1):");
				System.out.println("sb : " + sb.toString());
			}
			// sb(=response (네이버 응답 메세지) ) : JSON형태의 문자열
			// {"access_token":"AAAANxVDVPVQ-O....}
		} catch (Exception e) {
			System.out.println(e);
		}
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(sb.toString()); // 문자열 -> JSON객체
		String token = (String) json.get("access_token"); // 토큰
		System.out.println("\n========token : " + token);
		String header = "Bearer " + token;
		try {
			apiURL = "https://openapi.naver.com/v1/nid/me"; // 두번째 요청. 프로필 정보 조회(토큰값을 이용해서)
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", header); // 토큰 전달. 인증정보
			int responseCode = con.getResponseCode();
			BufferedReader br;
			sb = new StringBuffer();
			if (responseCode == 200) {
				System.out.println("로그인 정보 정상 수신");
				br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			} else {
				System.out.println("로그인 정보 수신 오류");
				br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8"));
			}
			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				sb.append(inputLine);
			}
			br.close();
			System.out.println("sb.toString()");
		} catch (Exception e) {
			System.out.println(e);
		}
		json = (JSONObject) parser.parse(sb.toString());
		System.out.println(json);
		JSONObject jsondetail = (JSONObject) json.get("response");
		System.out.println(jsondetail.get("id"));
		System.out.println(jsondetail.get("name"));
		System.out.println(jsondetail.get("email"));
		String mem_name = jsondetail.get("name").toString();
		String mem_email = jsondetail.get("email").toString();
		String mem_phoneno = jsondetail.get("mobile").toString().replace("-", "");
		Mem mem = service.getMemEmail(mem_email);	
		if(mem != null) {
			session.setAttribute("loginMem", mem);
			throw new ShopException("반갑습니다. " + mem.getMem_name() + "님 :)", "/second_prj/mypage/myInfo?mem_id=" + mem.getMem_id());
		} else {
			mem = new Mem();
			mem.setMem_id(mem_email);
			mem.setMem_name(mem_name);
			mem.setMem_phoneno(mem_phoneno);
			mem.setMem_channel("naver");
			int maxMemNum = service.maxMemNum();
			mem.setMem_number(maxMemNum+1);
			if(service.userInsert(mem)) {
				throw new ShopException("반갑습니다. " + mem.getMem_name() + "님 :)", "mypage?mem_id=" + mem.getMem_id());
			} else {
				throw new ShopException("죄송합니다. 소셜 로그인 과정 중 오류가 발생했습니다.", "mem/login");
			}
		}
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("loginMem");
		return "redirect:login";
	}
}
