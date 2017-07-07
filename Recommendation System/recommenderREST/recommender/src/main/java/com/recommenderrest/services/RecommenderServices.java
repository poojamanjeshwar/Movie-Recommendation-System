package com.recommenderrest.services;

import org.springframework.stereotype.Component;

@Component
public class RecommenderServices {
/*
	@Autowired
	private Environment env;
	
	@Autowired
	private IRegistrationSL registrationSL;
	
	private Logger logger = LogManager.getLogger(ProfileRestService.class.getName());
	
	public UserProfile validateCredentialService(String userName, String password) throws CooeRestException{
		logger.info("inside loginService");
		UserProfile profile = null;
		LoginCooeApplicationReqTo loginCooeApplicationReqTo = new LoginCooeApplicationReqTo();
		loginCooeApplicationReqTo.setUserName(userName);
		loginCooeApplicationReqTo.setPassword(password);
		try{
			LoginCooeApplicationResTo loginCooeApplicationResTo = registrationSL.login(loginCooeApplicationReqTo);
			profile = loginCooeApplicationResTo.getUserProfile();
		}catch(CooeSLException cse){
			logger.error("Received Exception from SL");
			if(StringUtils.equalsIgnoreCase(cse.getErrorCode(),env.getProperty("registration.sl.000"))){
				throw new CooeRestException("validateCredentialService", env.getProperty("cooe.000") , env.getProperty("cooe.000.msg"));
			}else if(StringUtils.equalsIgnoreCase(cse.getErrorCode(),env.getProperty("registration.sl.001"))){
				throw new CooeRestException("validateCredentialService", env.getProperty("cooe.001") , env.getProperty("cooe.001.msg"));
			}else if(StringUtils.equalsIgnoreCase(cse.getErrorCode(),env.getProperty("registration.sl.010"))){
				throw new CooeRestException("validateCredentialService", env.getProperty("cooe.010") , env.getProperty("cooe.010.msg"));
			}else if(StringUtils.equalsIgnoreCase(cse.getErrorCode(),env.getProperty("registration.sl.011"))){
				throw new CooeRestException("validateCredentialService", env.getProperty("cooe.011") , env.getProperty("cooe.011.msg"));
			}else if(StringUtils.equalsIgnoreCase(cse.getErrorCode(),env.getProperty("registration.sl.012"))){
				throw new CooeRestException("validateCredentialService", env.getProperty("cooe.012") , env.getProperty("cooe.012.msg"));
			}
		}catch(Exception e){
			logger.error("Unexpected Exception ::" + e.getMessage());
			throw new CooeRestException("validateCredentialService", env.getProperty("cooe.001") , env.getProperty("cooe.001.msg"));
		}
		return profile;
	}
	
	*/
	
}
