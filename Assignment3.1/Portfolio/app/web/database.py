'''
Created on Mar 20, 2014
A file containing almost all of the 
functions used for accessing the database.
@author: Will
'''

from flask import Flask
from flask import g
import sqlite3
import os

app = Flask('__main__')
app.config.from_object('__main__')

DATABASE = 'data/flaskr.db'

'''
Load default config and override 
config from an environment variable
'''
app.config.update(dict(
    DATABASE=os.path.join(app.root_path, 'data/flaskr.db'),
    DEBUG=True,
    SECRET_KEY='development key',
    USERNAME='admin',
    PASSWORD='default'
))

app.config.from_envvar('FLASKR_SETTINGS', silent=True)


"""Connects to the specific database."""
def connect_db():
    rv = sqlite3.connect(DATABASE)
    rv.row_factory = sqlite3.Row
    return rv

"""Opens a new database connection if there is none yet for the
    current application context."""
def get_db():
    if not hasattr(g, 'sqlite_db'):
        g.sqlite_db = connect_db()
    return g.sqlite_db

"""
Performs the given query on the database
@param query the query to perform
"""
def query_db(query, args=(), one=False):
    cur = get_db().execute(query, args)
    rv = cur.fetchall()
    cur.close()
    return (rv[0] if rv else None) if one else rv

"""Initializes the database for the first run"""
def init_db():
    with app.app_context():
        db = get_db()
        with app.open_resource('data/schema.sql', mode='r') as f:
            db.cursor().executescript(f.read())
        db.commit()
        
"""Closes the database again at the end of the request."""
@app.teardown_appcontext
def close_db(error):
    if hasattr(g, 'sqlite_db'):
        g.sqlite_db.close()
        