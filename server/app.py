from flask import Flask, request
from db import db, Request
import random
import json
import uuid

app = Flask(__name__)
db.create_all()


@app.route('/')
def hello_world():
    return 'Hello World!'


@app.route('/register', methods=['GET', 'POST'])
def register():
    request_ip = request.args.get('request_ip')
    request_number = str(random.randint(0, 100000))
    req = Request(target_ip=request_ip, request_id=request_number, uuid='')
    db.session.add(req)
    db.session.commit()
    return req.__repr__()


@app.route('/connect', methods=['GET', "POST"])
def connect():
    connect_id = request.args.get('connect_id')
    req: Request = Request.query\
        .filter_by(request_id=connect_id)\
        .filter_by(uuid='')\
        .first()

    req.uuid = str(uuid.uuid4())
    db.session.commit()

    return req.__repr__()


@app.route('/send', methods=['GET', 'POST'])
def send():
    uuid = request.args.get('uuid')
    message = request.args.get('message')
    req: Request = Request.query\
        .filter_by(uuid=uuid)\
        .first()

    return req.__repr__()

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=7777)
