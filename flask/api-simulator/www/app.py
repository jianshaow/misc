import json
import logging
import os
import sys

from flask import Flask, request

logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)
logger.setLevel(logging.DEBUG)

app = Flask(__name__)

resp_body_dir = '/data/resp-body'


@app.route('/mock-api/<api>', methods=['GET', 'POST', 'PUT', 'DELETE'])
def mock(api):
    body = get_resp_body(api, request.method)
    logger.info('request body -> %s', request.get_data(as_text=True))
    logger.info('return body -> %s', body)
    return body


def get_resp_body(api, method):
    resp_body = os.path.join(resp_body_dir, '{}.{}.json'.format(api, method))
    with open(resp_body, 'r') as resp_body_json:
        return json.load(resp_body_json)


if __name__ == '__main__':
    resp_body_dir = (len(sys.argv) == 2 and sys.argv[1] or resp_body_dir)
    app.run(debug=True, host="0.0.0.0")
