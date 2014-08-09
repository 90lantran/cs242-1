'''
Created on Mar 20, 2014

@author: Will
'''
from flask import Flask
from app.web.database import get_db
import unittest
import urllib

app = Flask(__name__)
app.config.from_object(__name__)

class MainTest(unittest.TestCase):
    
    def testSQLInjection(self):
        params = urllib.urlencode({'title': "title;DROP TABLE entries", 
                                   'text': "text;DROP TABLE entries", 
                                   'reply':None})
        urllib.urlopen("http://localhost:5000/Assignment2.0/Input.py/comments", params)
        
        with app.app_context():
            db = get_db()
            cur = db.execute('select * from entries')
            entrylist = cur.fetchall()
            self.assertNotEquals(len(entrylist),0)
            
    def testCommentInput(self):
        params = urllib.urlencode({'title': "title", 'text': "text", 'reply':None})
        urllib.urlopen("http://localhost:5000/Assignment2.0/Input.py/comments", params)
        
        with app.app_context():
            db = get_db()
            cur = db.execute('select text from entries where (text = ?)',["text"])
            entrylist = cur.fetchall()
            for entry in entrylist:
                self.assertEquals(entry[0],"text")
    
    def testReplyInput(self):
        params = urllib.urlencode({'title': "title", 'text': "reply", 'reply':1})
        urllib.urlopen("http://localhost:5000/Assignment2.0/Input.py/comments", params)
        
        with app.app_context():
            db = get_db()
            cur = db.execute('select text from replies where (text = ?)',["reply"])
            replylist = cur.fetchall()
            for reply in replylist:
                self.assertEquals(reply[0],"reply")
    
    def testSanitizeInput(self):
        obscene = "fuck your shit, Marty; you're a dick"
        correct_string = "enjoy your chocolate ice cream, worst TA ever; you're a fuzzy panda"
        params = urllib.urlencode({'title': "test", 'text': obscene, 'reply':None})
        urllib.urlopen("http://localhost:5000/Assignment2.0/Input.py/comments", params)
        
        with app.app_context():
            db = get_db()
            cur = db.execute('select text from entries where (text = ?) order by id desc',[correct_string])
            entrylist = cur.fetchall()
            for entry in entrylist:
                self.assertEquals(entry[0],correct_string)
        

if __name__ == "__main__":
    unittest.main()
        