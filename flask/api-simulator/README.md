
~~~ shell
export image_ver=0.0.5
docker build -t jianshao/api-simulator:$image_ver .
docker push jianshao/api-simulator:$image_ver

docker run -d --name api-simulator --rm -p 5000:5000 jianshao/api-simulator:$image_ver
curl http://localhost:5000/mock-api/test
curl http://localhost:5000/mock-api/test -XPOST
curl http://localhost:5000/mock-api/test -H 'Accept: application/xml'

docker stop api-simulator
docker run -d --name api-simulator --rm -p 5000:5000 -v $PWD/resp-body:/www/templates jianshao/api-simulator:$image_ver
curl http://localhost:5000/test
curl http://localhost:5000/test/mockit

kubectl -nsimulator delete cm root-resp-body
kubectl -nsimulator create cm root-resp-body --from-file=resp-body/

kubectl apply -f manifests/deploy.yaml
~~~
