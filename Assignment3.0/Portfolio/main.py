'''
Created on Mar 13, 2014

@author: Will
'''
from web.Parser import Parser
from flask import render_template
from flask import Flask
app = Flask(__name__)

p = Parser()
assignments = p.parse_log("data/svn_log.xml","data/svn_list.xml")
entries = p.parse_list("data/svn_list.xml")

#assignments = p.parse_log("data/test_log.xml","data/test_list.xml")
#entries = p.parse_list("data/test_list.xml")

@app.route('/')
def home_page():
    return render_template('index.html',
                           assignments=assignments)

@app.route('/<name>')
def assignment_page(name=None):
    return render_template('assign.html',
                           assignments=assignments,
                           name=name)
    
@app.route('/<name>/<filename>')
def file_page(name=None,filename=None):
    _file = p.find_file(assignments,filename)
    commits = p.get_commits(assignments,entries,name,filename)
    return render_template('file.html',
                           name=name,
                           filename=filename,
                           url=_file['url'],
                           commits=commits)

if __name__ == '__main__':
    app.run(debug=True)