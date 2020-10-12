from flask import Flask, abort, request, jsonify

app = Flask(__name__)

@app.route('/post-get-claims', methods=['POST'])
def post_get_claims():
    if not request.json or 'userId' not in request.json:
        abort(400)
    user_id = request.json['userId']

    credit_score = 0
    if user_id == '1':
        credit_score = 10
    if user_id == '2':
        credit_score = 20

    return jsonify({'credit-score': credit_score})

if __name__ == '__main__':
    app.run(debug=True)