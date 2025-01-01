package hello.core.singleton;


/**
 * 잘 설계한 객체는 컴파일 오류만으로 웬만한 것들이 다 잡히도록 설계한 것이다
 *
 * Spring Container 를 쓰면 Spring Container 가 기본적으로 객체를 다 싱글톤으로 만들어서 관리해 준다.
 *
 * */
public class SingletonService {

    /**
     * 자기 자신을 내부의 프라이빗으로 하나 가지고 있다.
     * static 으로 하면 클래스 레벨에 올라가기에 딱 하나만 존재하게 된다.
     * */
    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    private SingletonService(){}

    public void logic(){
        System.out.println("적당한 로직");
    }

//    public static void main(String[] args) {
//        /**
//         * 이렇게 되면 instance 가 두 번 호출된다.
//         * singleton 은 instance 가 하나만 생성되는 것을 의미한다.
//         * 이를 방지하기 위해 생성자를 private로 막아둔다.
//        */
//
//        SingletonService instance = new SingletonService();
//    }
}
