
~~~ shell
docker build -t jianshao/text-encryptor:0.0.3 .
docker push jianshao/text-encryptor:0.0.3
~~~

~~~ shell
docker run -d --name text-encryptor --rm -p 5000:5000 jianshao/text-encryptor:0.0.3
~~~
