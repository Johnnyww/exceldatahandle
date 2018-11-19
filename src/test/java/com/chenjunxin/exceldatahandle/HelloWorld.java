package com.chenjunxin.exceldatahandle;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
    }
}