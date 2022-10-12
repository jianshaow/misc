import logging

from flask import Flask, make_response, render_template, request
from jinja2.exceptions import TemplateNotFound

logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)
logger.setLevel(logging.DEBUG)

app = Flask(__name__)


@app.route('/mock-api/<api>', methods=['GET', 'POST', 'PUT', 'DELETE'])
def mock(api):
    body, status = get_resp_body(api, request)
    logger.info('request body -> %s', request.get_data(as_text=True))
    logger.info('return body -> %s', body)
    response = make_response(body, status)
    response.content_type = 'application/json'
    return response


def get_resp_body(api, request):
    status = 200
    resp_body_tmpl = '{}.{}.json'.format(api, request.method)
    try:
        resp_body = render_template(resp_body_tmpl, request=request)
    except TemplateNotFound:
        resp_body = ""
        status = 404
    return resp_body, status


if __name__ == '__main__':
    app.run(debug=True, host="0.0.0.0")
