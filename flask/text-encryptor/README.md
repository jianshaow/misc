
~~~ shell
docker build -t jianshao/text-encryptor:0.0.2 .
docker push jianshao/text-encryptor:0.0.2
~~~

~~~ shell
docker run -d --name text-encryptor --rm -p 5000:5000 jianshao/text-encryptor:0.0.2
~~~
