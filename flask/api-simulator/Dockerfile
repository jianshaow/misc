FROM python:3.12.0a6-alpine3.17

RUN pip install --upgrade pip
RUN pip install flask

COPY www /www

EXPOSE 5000

CMD [ "python", "/www/app.py" ]
