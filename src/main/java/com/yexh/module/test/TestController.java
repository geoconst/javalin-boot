package com.yexh.module.test;

import com.yexh.dao.AccountDao;
import com.yexh.model.Account;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    private AccountDao accountDao = new AccountDao();

    public static void registeRoutes(Javalin app) {
        TestController controller = new TestController();
        app.get("/", controller.indexHandler);
        app.get("/hello", controller.helloHandler);
        app.get("/db", controller.dbHandler);
    }


    public Handler indexHandler = ctx -> {
        Validator<Integer> validator = ctx.queryParamAsClass("age", Integer.class).check((t) -> t != null && t > 0, "年龄错误");
        if (!validator.errors().isEmpty()) {
            ctx.result(validator.errors().get("age").getFirst().getMessage());
            return;
        }

        if (Objects.equals(ctx.queryParam("name"), "hello")) {
            throw new RuntimeException("test name is hello");
        }
        ctx.result("Hello World");
    };

    public Handler helloHandler = ctx -> {
        // Map<String, Object> params = new HashMap<>();
        // params.put("name", "yexuhui");
        // User user = new User("yexuhui", 40);
        Account account = new Account();
        account.setPhone("18705916368");
        ctx.render("hello.jte", Map.of("user", account));
    };

    public Handler dbHandler = ctx -> {
        List<Account> items = Account.dao.findAll();
        ctx.render("db.jte", Map.of("items", items));
    };
}
