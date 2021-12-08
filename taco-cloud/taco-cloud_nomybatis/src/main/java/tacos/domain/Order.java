package tacos.domain;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data

public class Order implements Serializable {
    // Serializable接口是启用其序列化功能的接口。
    // 实现java.io.Serializable 接口的类是可序列化的。
    // 没有实现此接口的类将不能使它们的任意状态被序列化或逆序列化。

    // 序列化的过程，就是一个“freeze”的过程，它将一个对象freeze（冷冻）住，然后进行存储，
    // 等到再次需要的时候，再将这个对象de-freeze就可以立即使用。
    // 序列化操作并不是由我们实现，是在声明的各个不同变量的时候，由具体的数据类型帮助我们实现了序列化操作。
    // 当我们让实体类实现Serializable接口时，其实是在告诉JVM此类可被序列化，可被默认的序列化机制序列化。

    // 序列化的作用：
    // 序列化是将对象状态转换为可保持或传输的格式的过程。
    // 与序列化相对的是反序列化，它将流转换为对象。
    // 这两个过程结合起来，可以轻松地存储和传输数据。

    private static final long serialVersionUID = 1L;
    // serialVersionUID作用： 序列化时为了保持版本的兼容性，即在版本升级时反序列化仍保持对象的唯一性。
    // 有两种生成方式：
    // 一个是默认的1L，
    // 比如：private static final long serialVersionUID = 1L;
    // 一个是根据类名、接口名、成员方法及属性等来生成一个64位的哈希字段，
    // 比如：private static final long serialVersionUID = -8940196742313994740L;

    private Long id;

    private Date placedAt;

    private User user;

    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Street is required")
    private String street;
    @NotBlank(message = "city is required")
    private String city;
    @NotBlank(message = "state is required")
    private String state;
    @NotBlank(message = "zip is required")
    private String zip;
    @CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber;
    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$", message = "Must be formatted MM/YY")
    private String ccExpiration;
    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String ccCVV;

    private List<Taco> tacos = new ArrayList<>();

    public void addDesign(Taco design) {
        this.tacos.add(design);
    }

}





//
