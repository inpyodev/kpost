import json
import socketserver
import threading
from db import db, KPostRequest

HOST = '0.0.0.0'
PORT = 7778
lock = threading.Lock()  # syncronized 동기화 진행하는 스레드 생성
connections = {}

def update_screen(param):
    pass


class ConnectionManager:
    def add_connection(self, uuid, conn):
        if uuid in connections:
            return None

        lock.acquire()
        connections[uuid] = conn
        lock.release()

    def remove_connection(self, uuid):
        if uuid not in connections:
            return

        lock.acquire()
        del connections[uuid]
        lock.release()

    def send_message(self, uuid, msg):
        print('Guide {} to UUID {}'.format(msg, uuid))
        connections[uuid].send(msg.encode())

    def handle_message(self, msg, conn):
        try:
            unpack_msg = json.loads(msg)
            msg_type = unpack_msg['msg_type']
            uuid = unpack_msg['uuid']

            if msg_type == 'login':
                print('Logged {}'.format(uuid))
                self.add_connection(uuid, conn)
            elif msg_type == 'logout':
                print('Logout {}'.format(uuid))
                self.remove_connection(uuid)
            elif msg_type == 'screen_update':
                print('Update {}'.format(unpack_msg['screen_update']))
                update_screen(unpack_msg['screen_update'])

        except Exception as e:
            print("Error" + e)
        print(msg)


class TcpHandler(socketserver.BaseRequestHandler):
    connman = ConnectionManager()

    def handle(self):
        print('[%s] 연결됨' % self.client_address[0])

        try:
            msg = self.request.recv(1024)
            while msg:
                if self.connman.handle_message(msg.decode(), self.request) == -1:
                    self.request.close()
                    break

                msg = self.request.recv(1024)

        except Exception as e:
            print(e)

        print('[%s] 접속종료' % self.client_address[0])


def run_server():
    server = socketserver.TCPServer((HOST, PORT), TcpHandler)
    server.serve_forever()


