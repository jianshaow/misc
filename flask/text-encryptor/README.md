
~~~ shell
docker build -t jianshao/text-encryptor:0.0.1 .
docker push ~~~
~~~

~~~ shell
docker run -d --name text-encryptor --rm -p 5000:5000 jianshao/text-encryptor:0.0.1
~~~
