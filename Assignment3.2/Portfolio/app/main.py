'''
Created on Mar 13, 2014
Code for configuration of app
copied from:

http://flask.pocoo.org/docs/patterns/sqlite3/
http://flask.pocoo.org/docs/tutorial/setup/#tutorial-setup

@author: Will
'''
import os
from web.parser import Parser
from flask import Flask,request, \
     render_template, flash, jsonify
from web.database import get_db,init_db

app = Flask(__name__)
app.config.from_object(__name__)

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

p = Parser()
assignments = p.parse_log("data/svn_log.xml","data/svn_list.xml")
entries = p.parse_list("data/svn_list.xml")

@app.route('/')
def home_page():
    """The home page for the website"""
    return render_template('index.html',
                           assignments=assignments)

@app.route('/<name>')
def assignment_page(name=None):
    """The template page for each assignment (file collection)"""
    return render_template('assign.html',
                           assignments=assignments,
                           name=name)
 
@app.route('/<name>/<filename>')
def file_page(name=None,filename=None):
    """The template for displaying individual assignment files""" 
    _file = p.find_file(assignments,filename)
    commits = p.get_commits(assignments,entries,name,filename)
    
    url = None
    if _file != None:
        url = _file['url']
        
    return render_template('file.html',
                           name=name,
                           filename=filename,
                           url=url,
                           commits=commits)
    


"""A function for handling assignment file comments by users"""
@app.route('/<name>/<filename>/comments',methods=['GET','POST'])
def comment(name=None,filename=None):
    if (request.method == 'GET'):
        db = get_db()
        cur = db.execute('select title, text from entries order by id desc')
        entrylist = cur.fetchall()
        return render_template('show_entries.html', 
                               name=name,
                               filename=filename,
                               entrylist=entrylist)
    else:
        if (request.form['reply'] == "None"):
            return add_comment(request,name,filename)
        else:
            return reply_comment(request,name,filename)

def add_comment(request,name=None,filename=None):
    """
    Adds a comment to the html page
    for a given filename as part of a 
    project
    @param name the assignment name
    @param filename the name of the file
    @param the request data
    """ 
    db = get_db()
    title = sanitize(request.form['title'])
    text = sanitize(request.form['text'])
   
    db.execute('insert into entries (title, text) values (?, ?)',
                 [title,text])  
    db.commit()
    flash('New entry was successfully posted')

    cur = db.execute('select * from entries order by id desc')
    entrylist = cur.fetchall()
    
    _file = p.find_file(assignments,filename)

    data = {'name': name,
            'file': _file,
            'filename': filename,
            'entrylist': entrylist}
    return "test"


def reply_comment(request,name=None,filename=None):
    """
    Replies to a given comment and adds the
    data to the database.
    @param name the name of the assignment 
    @param filename the filename of the file
    @param request the request data from the user
    """
    db = get_db()
    title = sanitize(request.form['title'])
    text = sanitize(request.form['text'])
    db.execute('insert into replies (title,text,reply) values (?, ?, ?)',
               [title,text,request.form['reply']])
    db.commit()
    flash('New reply was successfully posted')
    
    cur = db.execute('select * from entries order by id desc')
    entrylist = cur.fetchall()
    
    cur = db.execute('select title,text,reply from replies order by id desc')
    replylist = cur.fetchall()
    
    _file = p.find_file(assignments,filename)
    
    return render_template('show_entries.html',
                           name=name,
                           file=_file,
                           filename=filename,
                           entrylist=entrylist,
                           replylist=replylist)

def sanitize(text):
    """
    Removes words that exist in the bad_words
    table from the text and replaces
    them with better words.
    @param text the comment text
    @return the sanitized text
    """
    db = get_db()
    cur = db.execute('select * from bad_words')
    bad_words = cur.fetchall()

    for pairing in bad_words:
        if (pairing[0] in text):
            text = text.replace(pairing[0],pairing[1])
     
    return text

def refresh_log_files():
    """
    Renews the svn_log and svn_list files in the data files
    if they do not already exist on disk.
    """
    if not os.path.isfile('data/svn_log.xml'):
        os.system('svn log --verbose --xml https://subversion.ews.illinois.edu/svn/sp14-cs242/hempy2/ > data/svn_log.xml')
    if not os.path.isfile('data/svn_list.xml'):
        os.system('svn list --xml --recursive https://subversion.ews.illinois.edu/svn/sp14-cs242/hempy2 > data/svn_list.xml')

def load_svn_files():
    """
    Checks out the subversion folder if it does not already
    exist on disk for loading files asynchronously.
    """
    if not os.path.exists('../hempy2/'):
        os.system('svn co https://subversion.ews.illinois.edu/svn/sp14-cs242/hempy2/ ../')
      
if __name__ == '__main__':
    refresh_log_files()
    load_svn_files()
    init_db()
    app.run(debug=True,host="0.0.0.0")
    