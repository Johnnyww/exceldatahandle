#基础镜像：仓库是java，tag是8
FROM java:8
MAINTAINER 379271256@qq.com
#解决Docker 容器与宿主机时间不同步直接从全局上修改时区而不从java调用参数中改
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
#将打包好的spring程序拷贝到容器中的指定位置
ADD target/excelhandle-0.0.1-SNAPSHOT.jar /exclehandle.jar
RUN mkdir /test
#容器对外暴露8989端口
#EXPOSE 8989
#容器启动后需要执行的命令
#CMD ["mkdir", "/logs"]
CMD ["java", "-Xmx1024m", "-jar", "/exclehandle.jar"]





##windows docker daemon下执行的脚本
#基础镜像：仓库是java，tag是8
#FROM mcr.microsoft.com/java/jdk:8u181-zulu-windowsservercore
#MAINTAINER 379271256@qq.com
#将打包好的spring程序拷贝到容器中的指定位置
#ADD target/excelhandle-0.0.1-SNAPSHOT.jar /exclehandle.jar
#容器对外暴露8989端口
#EXPOSE 8989
#容器启动后需要执行的命令
#CMD ["java", "-Xmx1024m", "-jar", "/exclehandle.jar"]
