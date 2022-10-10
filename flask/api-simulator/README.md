
~~~ shell
docker build -t jianshao/api-simulator:0.0.2 .
docker push jianshao/api-simulator:0.0.2
~~~

~~~ shell
docker run -d --name api-simulator --rm -p 5000:5000 -v $PWD/resp-body:/data/resp-body jianshao/api-simulator:0.0.2
~~~

~~~ shell
kubectl -nsimulator create cm resp-body --from-file=resp-body/
~~~
