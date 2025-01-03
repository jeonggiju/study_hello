package hello.core;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Service;

/**
 * @ComponentScan:
 * - Spring이 지정된 패키지 및 하위 패키지에서 컴포넌트를 자동으로 스캔하고 스프링 컨테이너에 빈으로 등록하도록 한다.
 * - 기본적으로 이 어노테이션이 선언된 클래스의 패키지를 기준으로 스캔한다.
 *
 * "excludeFilters":
 * * 특정 클래스나 어노테이션이 적용된 컴포넌트를 스캔에서 제외하도록 설정한다.
 * * type = FilterType.ANNOTATION:
 * *** 필터 조건을 어노테이션 유형으로 지정한다.
 * *** Configuration.class 어노테이션이 붙은 클래스는 스캔 대상에서 제외된다.
 * * classes = Configuration.class:
 * *** @Configuration 어노테이션이 붙은 클래스들은 스캔하지 않도록 설정
 * *** 이는 앞서 설정한 testAppConfig, AppConfig 따위로 충돌되지 않도록 하기 위함이다
 *
 * "basePackages"
 * * 탐색할 패키지의 시작 위치를 지정, 이 패키지를 포함해서 하위 패키지를 모두 탐색,
 * "basePackageClasses"
 * * 지정한 클래스의 패키지를 탐색 시작 위치로 지정
 *
 * * 만일 basePackages, basePackageClasses 를 지정하지 않으면 @ComponentScan 이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다.
 *    여기서는 "hello.core" 가 된다.
 * */
@Service
@Configuration
@ComponentScan(
//        basePackages = {"hello.core.member", "hello.core.order"},
//        basePackageClasses = {AutoAppConfig.class},
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes= Configuration.class)
)
public class AutoAppConfig {
}
