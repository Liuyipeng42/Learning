<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>JSP - Hello World</title>
    </head>
    <body>
        <h1><%= "Hello World!" %></h1>

        <br/>
        <a href="hello-servlet">Hello Servlet</a>

        <br/>
        <a href="ResponseMessage">ResponseMessage</a>

        <br/>
        <a href="UseLoadOnStartup">UseLoadOnStartup</a>

        <br/>
        <a href="Redirect">Redirect</a>

        <br/>
        <a href="ReadRequest">ReadRequest</a>

        <br/>
        <a href="GetParam?a=1&b=2&c=3">GetParam</a>

        <br/><br/>

        <form action="GetParam" method="get">
            <label>
                <input type="text" name="getParam"><br/>
                <input type="submit" value="get请求">
            </label>
        </form>
        <form action="GetParam" method="post">
            <label>
                <input type="text" name="postParam"><br/>
                <input type="submit" value="post请求">
            </label>
        </form>

    </body>
</html>