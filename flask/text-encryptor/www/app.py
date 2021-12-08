from flask import Flask, request, render_template
import logging

logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger('history')
logger.setLevel(logging.INFO)

app = Flask(__name__)


@app.route('/', methods=['GET'])
def index():
    return render_template('index.tmpl')


@app.route('/encode', methods=['POST'])
def encode():
    string = request.json['text']
    encoded = []
    for c in string:
        n = chr(ord(c) + 10)
        encoded.append(n)
    result = ''.join(encoded)
    logger.info(string + ' -> ' + result)
    return result


@app.route('/decode', methods=['POST'])
def decode():
    string = request.json['text']
    encoded = []
    for c in string:
        n = chr(ord(c) - 10)
        encoded.append(n)
    result = ''.join(encoded)
    logger.info(string + ' -> ' + result)
    return result


if __name__ == '__main__':
    app.run(debug=True, host="0.0.0.0")
