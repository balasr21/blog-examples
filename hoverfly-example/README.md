# This service is an example for Hoverfly with Java implementation

### Installation Instructions

#### Install [Hoverfly](https://hoverfly.io/) service


```bash
brew install SpectoLabs/tap/hoverfly
```

This will install hoverfly and hoverctl

and hoverfly can be started using below command

```
hoverfly -plain-http-tunneling start
```

This will bring the hoverfly and sets the default mode as [SIMULATE](https://docs.hoverfly.io/en/latest/pages/keyconcepts/modes/simulate.html) in port 8888

```
INFO[2022-12-31T09:03:05Z] Using memory backend                         
INFO[2022-12-31T09:03:05Z] Proxy prepared...                             Destination=. Mode=simulate ProxyPort=8500
INFO[2022-12-31T09:03:05Z] current proxy configuration                   destination=. mode=simulate port=8500
INFO[2022-12-31T09:03:05Z] serving proxy                                
INFO[2022-12-31T09:03:05Z] Admin interface is starting...                AdminPort=8888
```

Alternatively, you can run docker instance

#### Using Docker[https://www.docker.com/]

```
docker run -d -p 8888:8888 -p 8500:8500 spectolabs/hoverfly:latest
```

### How to Run the App in local ?

This app uses maven as a build tool and hence the app can be started by below command

```
mvn spring-boot:run
```

