import json
import logging
import os
import sys

from flask import Flask, request

logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)
logger.setLevel(logging.DEBUG)
his_logger = logging.getLogger('history')
his_logger.setLevel(logging.INFO)

app = Flask(__name__)

resp_body_dir = '/data/resp-body'


@app.route('/api-simulate')
def simulate():
    api = request.args['api']

    result = get_resp_body(api)
    his_logger.info('%s -> %s', result, result)
    return result


def get_resp_body(api):
    resp_body = os.path.join(resp_body_dir, '%s.json' % api)
    with open(resp_body, 'r') as resp_body_json:
        return json.load(resp_body_json)


if __name__ == '__main__':
    resp_body_dir = (len(sys.argv) == 2 and sys.argv[1] or resp_body_dir)
    app.run(debug=True, host="0.0.0.0")
