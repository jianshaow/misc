from flask import Flask, abort, request, jsonify

app = Flask(__name__)

@app.route('/pre-get-claims', methods=['POST'])
def pre_get_claims():
    if not request.json or 'id' not in request.json or 'info' not in request.json:
        abort(400)
    return jsonify({'result': 'success'})

if __name__ == '__main__':
    app.run(debug=True)