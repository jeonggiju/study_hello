package hello.core.web;


import hello.core.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final MyLogger myLogger;

    /**
     * 1. @RequestMapping("log-demo")
     * - 이 메서드는 '/log-demo' 라는 URL 경로로 들어오는 HTTP 요청을 처리한다.(e.g. http://localhost:8080:/log-demo)
     * - 요청 방식(HTTP Method)은 명시되지 있지 않으므로, 기본적으로 GET, POST 등 모든 HTTP 메서드를 처리할 수 있다.
     * 2. @ResponseBody
     * - 메서드의 반환 값인 "OK"를 HTTP 응답 본문(Body)에 직접 넣는다.
     * 3. HttpServletRequest request
     * - HTTP 요청에 대한 정보를 담고 있는 객체이다.
     * - 이 객체를 통해 클라이언트 요청의 URL, 헤더, 파라미터 등을 가져올 수 있다.
     * */
    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        String requestURL = request.getRequestURI();
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }

}
