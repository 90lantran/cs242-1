'''
Created on Mar 14, 2014

@author: Will
'''
import unittest
from app.web.parser import Parser

class ParserTest(unittest.TestCase):
    parser = Parser()
    assignments = parser.parse_log("../data/test_log.xml","../data/test_list.xml")
    entries = parser.parse_list("../data/test_list.xml")
    
    def testParseLog(self):
        self.assignments = self.parser.parse_log("../data/svn_log.xml","../data/svn_list.xml")
        self.assertEquals(self.assignments['Assignment2.0']['files'][0], {'name': 'Input.py', 
        'url': 'https://subversion.ews.illinois.edu/svn/sp14-cs242/hempy2/Assignment2.0/CSAir/src/control/Input.py', 
        'version': '7105', 'message': None, 'type': 'py', 'size': '3707'})

    def testParseList(self):
        self.entries = self.parser.parse_list("../data/svn_list.xml")
        self.assertEquals(self.entries['Assignment1.2/Chess/src/model/Chess.java'], [{'date': '2014-02-21T14:20:28.754343Z',
                                                                                      'revision': '6022', 
                                                                                      'author': 'hempy2', 
                                                                                      'size': '3114',
                                                                                      'message': None}])
           
    def testMultipleRevisionsSameFile(self):
        commits = self.parser.get_commits(self.assignments,self.entries,"Assignment2.1","Input.py")
        self.assertEquals(commits,[{'date': '2014-03-07T18:41:34.047768Z', 
                                    'message': None, 
                                    'revision': '7785', 
                                    'author': 'hempy2', 'size': '10869'}, 
                                   {'date': '2016-02-03T18:41:34.047768Z', 
                                    'message': None, 'revision': '7725', 
                                    'author': 'hempy2', 'size': '10869'}])
    
    
    '''
    If we edit multiple files, we should
    see that the number of commits is greater
    than one for each.
    '''
    def testMultipleRevisionsMultipleFiles(self):
        commits = self.parser.get_commits(self.assignments,self.entries,"Assignment2.1","__init__.py")
        self.assertEquals(commits,[{'date': '2014-03-07T15:36:32.156583Z', 
                                    'message': None, 'revision': '7748', 
                                    'author': 'hempy2', 'size': '0'}, 
                                   {'date': '2014-01-0T15:36:32.156583Z', 
                                    'message': None, 'revision': '428', 
                                    'author': 'hempy2', 'size': '29'}])
        
        commits = self.parser.get_commits(self.assignments,self.entries,"Assignment2.1","Input.py")
        self.assertEquals(commits,[{'date': '2014-03-07T18:41:34.047768Z', 
                                    'message': None, 
                                    'revision': '7785', 
                                    'author': 'hempy2', 'size': '10869'}, 
                                   {'date': '2016-02-03T18:41:34.047768Z', 
                                    'message': None, 'revision': '7725', 
                                    'author': 'hempy2', 'size': '10869'}])
        
    '''
    When deletions happen, there will be missing data
    in the log, but not in the list, because a commit
    occurred that removed the file.
    '''
    def testDeletions(self):
        commits = self.parser.get_commits(self.assignments,self.entries,"shopping","list2.txt")
        self.assertEquals(commits,[{'date': '2014-01-29T21:31:24.604801Z', 
                                    'message': None, 'revision': '975', 
                                    'author': 'hempy2', 'size': '82'}])
        _file = self.parser.find_file(self.assignments,"list2.txt")
        self.assertEquals(_file,None)
    
    
    def testFindFile(self):
        _file = self.parser.find_file(self.assignments,"GraphTest.py")
        self.assertEquals(_file,{'name': 'GraphTest.py', 
                                'url': 'https://subversion.ews.illinois.edu/svn/sp14-cs242/hempy2/Assignment2.1/CSAir/src/tests/GraphTest.py', 
                                'version': '7785', 'message': None, 'type': 'py', 'size': '3574'})

if __name__ == "__main__":
    unittest.main()