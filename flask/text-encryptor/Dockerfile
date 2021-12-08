FROM python:3.10-alpine

RUN pip install flask

COPY www /www

EXPOSE 5000

CMD [ "python", "/www/app.py" ]
