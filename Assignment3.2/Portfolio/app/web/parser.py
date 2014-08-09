'''
Created on Mar 13, 2014

@author: Will
'''
import xml.etree.ElementTree as et

class Parser():
    
    def parse_log(self,svn_log,svn_list):
        '''
        A function that parses an XML file
        in the format given by the "svn log"
        command. Assumes that a file named 
        filename exists.
        @param svn_log the "svn log" file
        @param svn_list the "svn list" file
        '''
        assignments = {}
        entries = self.parse_list(svn_list)
        
        xmltext = open(svn_log,"r").read()
        tree = et.fromstring(xmltext)
        
        self.init_assignments(assignments,tree)
        
        for logentry in tree.iter('logentry'):
            for path_el in logentry.iter('path'):
                project_name = self.get_project_name(path_el)
                if (project_name != None):
                    self.set_assignment_attribs(assignments,project_name,logentry)
                    self.set_project_file(assignments,entries,path_el,project_name)
            for msg in logentry.iter('msg'):
                if project_name != None:
                    for _file in assignments[project_name]['files']:
                        _file['message'] = msg.text
        
        return assignments
    
    def parse_list(self,filename):
        '''
        A function that parses an XML file
        in the format gien by "svn list"
        command. Assumes that the filename exists.
        '''
        entries = {}
        
        xmltext = open(filename,"r").read()
        tree = et.fromstring(xmltext)
        
        self.init_entries(entries, tree)
        
        for entry in tree.iter('entry'):
            commit = {}
            self.get_commit(commit,entry)
            for name in entry.iter('name'):
                file_name = name
            if commit['size'] != -100:
                entries[file_name.text].append(commit)
        
        return entries
    
    def init_entries(self,entries,tree):
        '''
        Initializes the entries dictionary.
        This is necessary so that we can append new files
        and add new dictionary elements without 
        reinitializing each time and possibly erasing data.
        @param entries the entries to initialize
        @param tree the parsed xml file representation
        '''
        for entry in tree.iter('entry'):
            for name in entry.iter('name'):
                file_name = name
            
            entries[file_name.text] = []

    def get_commit(self,commit,entry):
        '''
        A helper function that gets the
        commit information given an entry
        of the svn list file. We set the
        value to -100 to make sure that the
        entry actually has a size.
        @param commit the commit to initialize
        @param entry the svn list entry
        '''
        commit['message'] = None
        commit['size'] = -100
        for size in entry.iter('size'):
            commit['size'] = size.text
        
        for addition in entry.iter('commit'):
            commit['revision'] = addition.attrib['revision']
            for author in addition.iter('author'):
                commit['author'] = author.text
            for date in addition.iter('date'):
                commit['date'] = date.text

        for msg in entry.iter('msg'):
            commit['message'] = msg.text
             
    def init_assignments(self,assignments,tree):
        '''
        Initializes the assignments dictionary.
        This is necessary so that we can append new files
        and add new dictionary elements without 
        reinitializing each time and possibly erasing data.
        @param assignments the assignments to initialize
        @param tree the parsed xml file representation
        '''
        for logentry in tree.iter('logentry'):
            for path_el in logentry.iter('path'):
                project_name = self.get_project_name(path_el)
                if (project_name != None):
                    assignments[project_name] = {}
                    assignments[project_name]['files'] = [] 
                    
    def get_project_name(self,path_el):
        '''
        Gets the project name given the
        entire path description. This will allow us
        to set multiple files to the same project 
        object.
        @param path_el the path element
        @return the project_name corresponding to the path
        '''    
        project_name = path_el.text[len('/hempy2/'):]
                
        forward_slash = project_name.find('/')
        if (forward_slash != -1): 
            project_name = project_name[:forward_slash]
        else:
            project_name = None
             
        return project_name
    
    def set_project_file(self,assignments,entries,path_el,project_name):
        '''
        Sets the current file in the path element
        to the assignments structure. This file description
        will include the type of the file, its size, path
        to svn files, and version, the author, the revision number,
        author, commit message, and commit date.
        @param path_el the path element to add
        @param project_name the name of the current project
        '''
        file_name = path_el.text
        forward_slash = file_name.find('/')
        while (forward_slash != -1):
            file_name = file_name[forward_slash+1:]
            forward_slash = file_name.find('/')
        
        extension = self.get_file_extension(file_name)
        if (extension == None): return
                   
        _file = {}
        _file['name'] = file_name
        _file['type'] = extension
        _file['url'] = "https://subversion.ews.illinois.edu/svn/sp14-cs242"
        _file['url'] += path_el.text
        _file['version'] = assignments[project_name]['version']
        _file['size'] = self.get_size(path_el.text,entries)
     
        file_list = assignments[project_name]['files']
        duplicate = self.duplicate_exists(file_list,file_name)
        
        if (not duplicate):
            assignments[project_name]['files'].append(_file)

    def get_size(self,path_el,entries):
        '''
        Gets the size of a file matching
        the path element given in the svn list
        @param path_el the path element
        @param the list of entries
        @return the matching file entry
        '''
        path = path_el[len('/hempy2/'):]
       
        for commitpath in entries:
            if commitpath == path:
                for commit in entries[commitpath]:
                    return commit['size']
        
    def duplicate_exists(self,file_list,file_name):
        '''
        Checks if the given file name already
        exists in the array. A helper function
        for adding files to the assignment list.
        @param file_list the list of files
        @param filename the filename to search for
        @return true if there is a duplicate,
                false otherwise
        '''
        for _file in file_list:
            if (_file['name'] == file_name):
                return True
        return False
    
    def get_file_extension(self,file_name):
        '''
        Gets the file extension for 
        the given file name. If no file
        extension exists, then it is not 
        a file
        @param file_name the name of the file
        @return the extension of the file
                None if no extension exists
        '''
        dot = file_name.find('.')
        extension = file_name
        if (dot != -1):
            extension = extension[dot+1:]
        else:
            extension = None
        
        return extension

    def set_assignment_attribs(self,assignments,project_name,logentry):
        '''
        Sets simple attributes for the given
        project in the assignments structure.
        This includes the summary, version,
        date, and file list.
        @param assignments the assignment storage
        @param project_name the name of the project
        @param logentry the entry in the xml file
        '''              
        for date in logentry.iter('date'):
            assignments[project_name]['date'] = date.text
            assignments[project_name]['version'] = logentry.attrib['revision']
        for summary in logentry.iter('msg'):
            assignments[project_name]['summary'] = summary.text    
    
    def find_file(self,assignments,file_name):
        '''
        Checks through the given list of
        assignments to find the file pertaining 
        to the given filename. If none is found, 
        then we return None.
        @param assignments the list of assignments
        @param filename the filename to match
        @return the matching file
                None otherwise
        '''
        if file_name == None: return None

        for project_name in assignments:
            for _file in assignments[project_name]['files']:
                if _file['name'] == file_name:
                    return _file
        
        return None
    
    def get_commits(self,assignments,entries,name,file_name):
        '''
        A function that will gather a list of 
        commits made for a certain file so that they
        can easily listed one after another.
        @param assignments the list of course asssignments
        @param entries the list of commits
        @param name the the project name
        @param filename the name of the specific file
        '''
        remove_url = "https://subversion.ews.illinois.edu/svn/sp14-cs242/hempy2/"
        _file = self.find_file(assignments,file_name)
        path = None
        
        if _file == None: 
            for entry in entries:
                if (name in entry and file_name in entry):
                    path = entry
        else:
            path = _file['url'][len(remove_url):]
        
        commits = []
        if path != None and path in entries:
            for commit in entries[path]:
                commits.append(commit)
                
        return commits
        
if __name__ == '__main__':
    p = Parser()
    p.parse_log("../data/svn_log.xml","../data/svn_list.xml")