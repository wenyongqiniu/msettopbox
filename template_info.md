# 源码自动生成模板 empty

### 概述

* 模板: empty
* 模板使用时间: 2020-05-12 20:52:03

### Docker
* Image: registry.cn-beijing.aliyuncs.com/rdc-template/repo
* Tag: 0817
* SHA256: 5556cb0b0d1605f84cca2b4118fe3d65e50314e905b66b8d926f4b1de90c432d

### 用户输入参数
* repoUrl: "git@code.aliyun.com:snail-out-front/msettopbox.git" 
* needDockerfile: "N" 
* appName: "msettopbox" 
* operator: "aliyun_653304" 
* appReleaseContent: "# 
* 请参考: 请参考 
* https://help.aliyun.com/document_detail/59293.html: https://help.aliyun.com/document_detail/59293.html 
* 了解更多关于release文件的编写方式: 了解更多关于release文件的编写方式 
* [NEWLINE][NEWLINE]#: [NEWLINE][NEWLINE]# 
* 构建源码语言类型[NEWLINE]code.language: scripts[NEWLINE][NEWLINE]# 
* 应用部署脚本[NEWLINE]deploy.appctl.path: deploy.sh" 

### 上下文参数
* appName: msettopbox
* operator: aliyun_653304
* gitUrl: git@code.aliyun.com:snail-out-front/msettopbox.git
* branch: master


### 命令行
	sudo docker run --rm -v `pwd`:/workspace -e repoUrl="git@code.aliyun.com:snail-out-front/msettopbox.git" -e needDockerfile="N" -e appName="msettopbox" -e operator="aliyun_653304" -e appReleaseContent="# 请参考 https://help.aliyun.com/document_detail/59293.html 了解更多关于release文件的编写方式 [NEWLINE][NEWLINE]# 构建源码语言类型[NEWLINE]code.language=scripts[NEWLINE][NEWLINE]# 应用部署脚本[NEWLINE]deploy.appctl.path=deploy.sh"  registry.cn-beijing.aliyuncs.com/rdc-template/repo:0817

