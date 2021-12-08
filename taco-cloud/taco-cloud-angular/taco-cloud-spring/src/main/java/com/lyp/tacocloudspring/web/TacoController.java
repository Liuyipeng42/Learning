package com.lyp.tacocloudspring.web;


import com.lyp.tacocloudspring.entity.Ingredient;
import com.lyp.tacocloudspring.entity.Taco;
import com.lyp.tacocloudspring.service.TacoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
// @RestController 注解会告诉 Spring，控制器中的所有处理器方法的返回值都要直接写入响应体中，
// 而不是将值放到模型中并传递给一个视图以便于进行渲染。
@RequestMapping(path = "/design", produces = "application/json")
// produces = "application/json"表明所有处理器方法只会处理 客户端发送的 Accept头中包含“application/json”的请求。
// 有json类型数据的请求就有json类型的相应，所以会限制 API 只会生成 JSON 结果。
// 同时还允许其他的控制器处理具有相同路径的请求，只要这些请求不要求 JSON 格式的输出就可以。
@CrossOrigin(origins = "*")
// 只要 协议，域名，端口有任何一个的不同，就被当作是跨域
// 因为考虑到安全问题，浏览器要限制跨域访问
// @CrossOrigin 可以允许跨域请求
public class TacoController {

    private final TacoService tacoService;

    @Autowired
    public TacoController(TacoService tacoService) {
        this.tacoService = tacoService;
    }

//    @GetMapping("/recent")
//    public CollectionModel<EntityModel<Taco>> recentTacos() {
//        List<Taco> tacoList = tacoService.getTacosDesc12();
//
//        CollectionModel<EntityModel<Taco>> recentResources = CollectionModel.wrap(tacoList);
//        recentResources.add(
//                linkTo(methodOn(DesignTacoController.class).recentTacos())
//                        .withRel("recents"));
//
//        return recentResources;
//    }

    @GetMapping("/recent")
    public List<Taco> recentTacos() {
        return tacoService.getTacosDesc12();
    }

    @PostMapping(consumes="application/json")
    // 只处理 Content-Type为 consume指定类型的数据类型的请求
    @ResponseStatus(HttpStatus.CREATED)
    // 若请求成功，则会将 HTTP 状态码 由 200 改为 201 (CREATED)，使 HTTP 状态更具有描述性。
    // 它会告诉客户端，请求不仅成功了，还创建了一个资源。
    public Taco postTaco(@RequestBody Taco taco) {
        // @RequestBody 注解表明请求应该被转换为一个 Taco 对象并绑定到该参数上。
        // @RequestBody 注解能够确保请求体中的 JSON 会被绑定到 Taco 对象上。
        return tacoService.saveTaco(taco);
    }

    @GetMapping("/{id}")
    // 路径的“{id}”部分是占位符。请求中的实际值将会传递给 id 参数，
    // 可以通过@PathVariable 注解与{id}占位符进行匹配，给一个变量赋值。
    public ResponseEntity<List<Taco>> tacoByOrderId(@PathVariable("id") String id) {
        List<Taco> tacoList = tacoService.getTacosByOrderId(id);
        if (tacoList != null) {
            return new ResponseEntity<>(tacoList, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}

// Http报头分为通用报头，请求报头，响应报头和实体报头。
// 请求方的http报头结构：通用报头|请求报头|实体报头
// 响应方的http报头结构：通用报头|响应报头|实体报头
// Accept属于请求头，代表发送端（客户端）希望接受的数据类型
// Content-Type属于实体头，代表发送端（客户端|服务器）发送的实体数据的数据类型
