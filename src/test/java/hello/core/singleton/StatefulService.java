package hello.core.singleton;

public class StatefulService {
    // 스프링 빈은 항상 무상태(stateless) 여야 한다.
    private int price; // 공유필트: 상태를 유지하는 필드

    public void order(String name, int price){
        System.out.println("name = " + name + ", price = " + price);
        this.price = price; // 여기가 문제

//        return price; // <- 해결책
    }

    public int getPrice(){
        return price;
    }
}
