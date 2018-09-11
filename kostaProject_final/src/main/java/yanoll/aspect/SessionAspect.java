package yanoll.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;



	@Aspect //Aspect 어노테이션으로 aop처리 (기존처럼 xml기반이아니라)
	public class SessionAspect {
	      @Around("execution(public * kosta.controller.*.*exe(..))")
	      //aop의 around
	      //컨트롤러 패키지에서 exe로 끝나는 메소드에 Aspect가 적용되도록 aop처리
	            //kosta.controller패키지 아래 모든파일 중 ~~~exe로 끝나는 파일 (파라미터값은 신경안씀)
	      public String sessionCheck(ProceedingJoinPoint joinPoint)throws Throwable{
	            
	            Object[] obj = joinPoint.getArgs(); 
	           
	            //josinPoint객체안에 들어있는 파라미터들을 몽땅 가져옴
	            //배열로 넘어옴
	            //joinPoint(컨트롤러의 핵심관심사항메소드 _exe 의 파라미터값을 가져오는데
	            //우리가 넣어준건 0번쨰에 세션객체 1개밖에 없음
	            HttpServletRequest request = (HttpServletRequest)obj[0];
	            //SessionController에서  session_exe메소드에  파라미터를 통해 던져준 세션객체를 받아옴
	            //파라미터순서대로 가져오기때문에 1번째꺼인 obj[0]을 다시 request에 가져온다
	            

	            //request를 세션에 담아! jsp에서는 session을 지원해줬는데 여기서는 안해줘서 방법이것뿐~
	            //
	            HttpSession session = request.getSession();
	            String name = (String)session.getAttribute("name");   //세션에 있는 이름가져옴
	            
	            String view = "session/session_fail";
	            //세션문제가 있을떄 가야할 뷰
	            
	            try {
	                  if(name == null){  //이름이 없으면 예외 발생시킴
	                        throw new Exception("no session");
	                  }
	                  
	                  //아이디가 있으면 controller의 return값인 success.jsp로 간다
	                  view = (String)joinPoint.proceed();       //핵심관심사항
	                  //joinPoint객체를 발생하여 .proceed()메소드를 넣어주면 
	                  //원래 호출하려는 비지니스 로직인 session_exe메소드를 호출함 
	                  //메소드가 exe로 호출하면 공통관심사인(세션체크)와 핵심관심사인 ( session_exe)를 호출하지만
	                  //.proceed()로 호출하면 핵심관심사인 session_exe만 호출 
	                  //session_exesms session/session_success 뷰를 리턴해줌
	            } catch (Exception e) {
	                  return view; //예외가 발생하면 catch에 걸려서 view로 반환하여 fail로감
	            }
	            return view; //널값이 아니라서 예외가 발생하지않으면 suceess뷰 리턴해줌
	      }
	}
	
	
