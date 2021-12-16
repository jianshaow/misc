
~~~ shell
docker build -t jianshao/text-encryptor:0.0.4 .
docker push jianshao/text-encryptor:0.0.4
~~~

~~~ shell
docker run -d --name text-encryptor --rm -p 5000:5000 jianshao/text-encryptor:0.0.4
~~~

~~~ shell
kubectl -nencryptor create cm image-cm --from-file=www/static/angle.gif
~~~
