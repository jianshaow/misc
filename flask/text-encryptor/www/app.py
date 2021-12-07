from flask import Flask, request, render_template

app = Flask(__name__)


@app.route('/', methods=['GET'])
def index():
    return render_template('index.tmpl')


@app.route('/encode', methods=['POST'])
def encode():
    print(request.json)
    string = request.json['text']
    print(string)
    encoded = []
    for c in string:
        n = chr(ord(c)+10)
        encoded.append(n)
    return ''.join(encoded)


@app.route('/decode', methods=['POST'])
def decode():
    string = request.json['text']
    encoded = []
    for c in string:
        n = chr(ord(c)-10)
        encoded.append(n)
    return ''.join(encoded)


if __name__ == '__main__':
    app.run(debug=True, host="0.0.0.0")
