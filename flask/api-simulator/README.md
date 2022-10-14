
~~~ shell
docker build -t jianshao/api-simulator:0.0.2 .
docker push jianshao/api-simulator:0.0.2
~~~

~~~ shell
docker run -d --name api-simulator --rm -p 5000:5000 jianshao/api-simulator:0.0.2
curl http://localhost:5000/mock-api/test
curl http://localhost:5000/mock-api/test -XPOST
curl http://localhost:5000/mock-api/test -H 'Accept: application/xml'
docker stop
docker run -d --name api-simulator --rm -p 5000:5000 -v $PWD/resp-body:/www/templates jianshao/api-simulator:0.0.2
curl http://localhost:5000/mock-api/test
~~~

~~~ shell
kubectl -nsimulator create cm resp-body --from-file=resp-body/
~~~
