todolist
========

Todolist example using Maven Java Spring REST HTML Backbone RequireJS (TODO: OAuth2)

eclipse setup:

mvn eclipse:clean eclipse:eclipse

Setup BD:
todolist-parent\todolist-web\src\main\webapp\META-INF\context.xml

<xml>
<Context antiJARLocking="true" path="">   

    <Environment name="todolist/url" value="http://localhost:8080/todolist" type="java.lang.String" override="false" />
    <Environment name="db/hbmDialect" value="org.hibernate.dialect.MySQL5InnoDBDialect" type="java.lang.String" override="false" />
    <Environment name="db/type" value="MYSQL" type="java.lang.String" override="false" />
   	<Environment name="db/showSql" value="true" type="java.lang.Boolean" override="false" />
    <Environment name="db/jdbcDriver" value="com.mysql.jdbc.Driver" type="java.lang.String" override="false" />
    <Environment name="db/url" value="jdbc:mysql://localhost:3306/todolist" type="java.lang.String" override="false" />
    <Environment name="db/user" value="todolist" type="java.lang.String" override="false" />
    <Environment name="db/password" value="todo" type="java.lang.String" override="false" />
	
	<Environment name="mail/emailFrom" value="noreply@ourdoughmain.com" type="java.lang.String" override="false" />
    <Environment name="mail/serverUrl" value="smtp.gmail.com" type="java.lang.String" override="false" />
    <Environment name="mail/serverPort" value="587" type="java.lang.String" override="false" />
    <Environment name="mail/serverUser" value="cursosgloballogic@gmail.com" type="java.lang.String" override="false" />
    <Environment name="mail/serverPassword" value="cursosdegloballogic3" type="java.lang.String" override="false" />

</Context>
</xml>
