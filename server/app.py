import threading

from flask import Flask, request
from db import db, Request
import random
import json
import uuid
from socket_server import KPostServer, ConnectionManager

app = Flask(__name__)
db.create_all()
kpserver = KPostServer()
server_thread = threading.Thread(target=kpserver.run_server)
server_thread.daemon = True
server_thread.start()


@app.route('/')
def hello_world():
    return 'Hello World!'


@app.route('/register', methods=['POST'])
def register():
    request_ip = request.form['request_ip']
    request_number = str(random.randint(0, 100000))
    uuid_num = str(uuid.uuid4())

    req = Request(target_ip=request_ip, request_id=request_number, uuid=uuid_num)
    db.session.add(req)
    db.session.commit()

    return req.__repr__()


@app.route('/connect', methods=["POST"])
def connect():
    connect_id = request.form['connect_id']
    req = Request.query \
        .filter_by(request_id=connect_id) \
        .first()

    req.request_id = ''
    db.session.commit()

    return req.__repr__()


@app.route('/send', methods=['POST'])
def send():
    uuid = request.form['uuid']
    message = request.form['message']
    req = Request.query\
        .filter_by(uuid=uuid) \
        .first()

    conn = ConnectionManager()
    conn.send_message(uuid, message)

    return req.__repr__() + " Message : %s" % message


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=7777)
