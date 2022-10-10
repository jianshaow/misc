import logging

from flask import Flask, make_response, render_template, request

logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)
logger.setLevel(logging.DEBUG)

app = Flask(__name__)


@app.route('/mock-api/<api>', methods=['GET', 'POST', 'PUT', 'DELETE'])
def mock(api):
    body = get_resp_body(api, request)
    logger.info('request body -> %s', request.get_data(as_text=True))
    logger.info('return body -> %s', body)
    response = make_response(body)
    response.content_type = 'application/json'
    return response


def get_resp_body(api, request):
    resp_body_templ = '{}.{}.json'.format(api, request.method)
    return render_template(resp_body_templ, args=request.args)


if __name__ == '__main__':
    app.run(debug=True, host="0.0.0.0")
