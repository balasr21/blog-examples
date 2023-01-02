# This service is an example for Hoverfly with Spring webflux implementation

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

This app serves as a demo for Hoverfly

An order processing service that exposes a POST /order endpoint with the list of items to order along with the shipment postcode. This ordering service interacts with the dependent driver service in order to retrieve the driver details based on the postcode (using GET driver/{postCode})

![HoverflyExampleFlow](./Hoverfly example.jpg?title=Hoverfly)

### How to Run the App in local ?

This app uses maven as a build tool and hence the app can be started by below command

```
mvn spring-boot:run
```

Please refer to the tests #ProxyServerOrderControllerTest and #WebServerOrderControllerTest which runs hoverfly as proxy and web server 


