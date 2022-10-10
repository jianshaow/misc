from flask import Flask, request, render_template
import logging

logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)
logger.setLevel(logging.DEBUG)
his_logger = logging.getLogger('history')
his_logger.setLevel(logging.INFO)

app = Flask(__name__)

offset = 0x1000

@app.route('/', methods=['GET'])
def index():
    return render_template('index.tmpl')


@app.route('/encode', methods=['POST'])
def encode():
    string = request.json['text']
    encoded = []
    for c in string:
        n = c
        order = ord(c)
        if 0x4e00 <= order <= 0x9fa5:
            n = chr(order - offset)
        encoded.append(n)
        logger.debug('%s(%d) to %s(%d)', c, ord(c), n, ord(n))
    result = ''.join(encoded)
    his_logger.info('%s -> %s', string, result)
    return result


@app.route('/decode', methods=['POST'])
def decode():
    string = request.json['text']
    encoded = []
    for c in string:
        n = c
        order = ord(c)
        if 0x4e00 - offset <= order <= 0x9fa5 - offset:
            n = chr(order + offset)
        encoded.append(n)
        logger.debug('%s(%d) to %s(%d)', c, ord(c), n, ord(n))
    result = ''.join(encoded)
    his_logger.info('%s -> %s', string, result)
    return result


if __name__ == '__main__':
    app.run(debug=True, host="0.0.0.0")
