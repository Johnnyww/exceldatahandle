#基础镜像：仓库是java，tag是8
FROM java:8
MAINTAINER 379271256@qq.com
#将打包好的spring程序拷贝到容器中的指定位置
ADD target/excelhandle-0.0.1-SNAPSHOT.jar /home/app/exclehandle.jar
#容器对外暴露8989端口
#EXPOSE 8989
#容器启动后需要执行的命令
CMD ["java", "-Xmx1024m", "-jar", "/home/app/exclehandle.jar"]
