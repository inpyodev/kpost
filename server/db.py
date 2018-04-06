from flask import Flask
from flask_sqlalchemy import SQLAlchemy
import json

app = Flask(__name__)
# app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///C:\\Workspace\\kpost\\server\\kpost.db' # For Windows
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:////kpost.db' # For Linux
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
db = SQLAlchemy(app)


class KPostRequest(db.Model):
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    request_id = db.Column(db.String(10), nullable=False)
    target_ip = db.Column(db.String(20), nullable=False)
    uuid = db.Column(db.String(60), nullable=False)
    current_screen = db.Column(db.Integer, nullable=False)

    def __repr__(self):
        return json.dumps({'id': self.id, 'request_id': self.request_id,
                           'target_ip': self.target_ip, 'uuid': self.uuid, 'current_screen': self.current_screen})