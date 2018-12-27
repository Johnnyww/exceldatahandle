package com.chenjunxin.exceldatahandle;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

/**
 * Created by chenjunxin on 2018/11/7
 */
public class HelloWorld {

    public static void main(String[] args) {
        String schema = "type Query{hello: String}";

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("hello", new StaticDataFetcher("world")))
                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
        ExecutionResult executionResult = build.execute("{hello}");

        System.out.println(executionResult.getData().toString());
        // Prints: {hello=world}

        LocalDateTime defUnStopDateTime = LocalDateTime.of(0001, 1, 1, 0, 0, 0);
//        Instant instant = defUnStopDateTime.atZone(ZoneId.systemDefault()).toInstant();
//        Date date3 = Date.from(instant);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        String result = sdf.format(date3);
//        System.out.println(result);
        System.out.println(defUnStopDateTime);


        //字符串转时间
        String dateTimeStr = "0001-01-01 00:00:00";
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, df);
        System.out.println(dateTimeStr);


        StringBuffer a = new StringBuffer("123");
        a.append("\r\n");
        a.append("666");
        System.out.println(a.toString());

        StringBuffer b = new StringBuffer();
        System.out.println(b.length());


        // 测试 i++与++i的区别
        int y = 0;
        //注意"="是赋值,"=="才是相等
        y = ++y;// y==0,++y==y+1; 结果y=++y == y+1 == 0+1 ==1
        y = ++y;// y==1,++y==y+1; 结果y=++y == y+1 == 1+1 ==2
        y = ++y;// y==2,++y==y+1; 结果y=++y == y+1 == 2+1 ==3
        y = ++y;// y==3,++y==y+1; 结果y=++y == y+1 == 3+1 ==4
        y = ++y;// y==4,++y==y+1; 结果y=++y == y+1 == 4+1 ==5
        System.out.println("y=" + y);//5
        int i = 0;
        // i==0,i++==0; 结果i=i++ == (记住先赋值后运算)i=i,i=i+1(由于是i++运算这里我们输出的i只取先赋值的结果也就是i=i)
        i = i++;
        i = i++;
        i = i++;
        i = i++;
        i = i++;
        System.out.println("i=" + i);//0
        System.out.println("================");


        int j = 3;
        String p = "3";
        if(p.equals(String.valueOf(j))){
            System.out.println("666666");
        }

        ArrayList<Integer> arrayList = new ArrayList<>();
        for(int w=0;w<9;w++){
            arrayList.add(w);
        }
        arrayList.add(2,11);
        arrayList.forEach(s-> System.out.println(s));
    }
}