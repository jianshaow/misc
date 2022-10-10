
~~~ shell
docker build -t jianshao/api-simulator:0.0.1 .
docker push jianshao/api-simulator:0.0.1
~~~

~~~ shell
docker run -d --name api-simulator --rm -p 5000:5000 -v $PWD/resp-body:/data/resp-body jianshao/api-simulator:0.0.1
~~~

~~~ shell
kubectl -nsimulator create cm resp-body --from-file=resp-body/
~~~
