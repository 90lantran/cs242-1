'''
Created on Feb 28, 2014

@author: Will
'''
from model.Graph import Graph
from view.Stats import Stats

FILE = Graph.FILE
stats = Stats()

class Input():
    
    # A function to execute the 
    # translated command and perform a query
    # on the graph to retrieve the statistics
    # and display them to the usuer.
    #
    # @input command the command string
    # @input city used for the cityInfo command only
    def query(self,command,city=None):
        if (command == 'allcities'):      stats.allCities()
        if (command == 'city'):           stats.cityInfo(city)
        if (command == 'longestflight'):  print stats.longestFlight()
        if (command == 'shortestflight'): print stats.shortestFlight()
        if (command == 'biggestcity'):    print stats.biggestCity()
        if (command == 'smallestcity'):   print stats.smallestCity()
        if (command == 'averagesize'):    stats.averagePopulation()
        if (command == 'continents'):     stats.continentGrouping()
        if (command == 'hubcities'):      stats.hubCities()
    
    # A function that will translate raw input
    # into function commands in real time
    #
    # @input command the command to translate
    def translate(self, command):
        self.helpCommand(command)
        self.extremityCommands(command)
        self.cityInfoCommand(command);
        
        if ('hub'    in command and 
            'cities' in command): 
            self.query('hubcities')
        if ('list all'    in command or 
            'all cities'  in command or 
            'list cities' in command): 
            self.query('allcities')
        if ('average'     in command and 
            ('population' in command or 'size' in command)):
            self.query('averagesize')
        if (('sort'  in command and 'continents' in command) or
            ('group' in command and 'continents' in command)):
            self.query('continents')
    
    # A helper function for translate that
    # handles only the city information command.
    #
    # @input command the command to translate
    def cityInfoCommand(self,command):
        if ('-c ' in command):
            index = command.find('-c ') + 3
            location = command[index:]
            self.query('city',location)

    # A helper function for translate that deals 
    # with the simple functions involving largest,
    # smallest, longest, and largest values
    def extremityCommands(self,command):
        if ('biggest' in command and 
            'city'    in command): 
            self.query('biggestcity')
            return
        if ('smallest' in command and 
            'city'     in command): 
            self.query('smallestcity')
            return 
        if ('longest' in command and 
            'flight'  in command): 
            self.query('longestflight')
            return
        if ('smallest' in command and 
            'flight'   in command): 
            self.query('smallestflight')
            return
    
    # Another helper for translate that deals with
    # applying the help file for confused users.
    # It simply loads a text file from memory and displays it
    def helpCommand(self,command):
        if (command[0] == 'h' and len(command) < 6):
            helpMe = open('../../input/help.txt',"r")
            print helpMe.read()


if __name__ == '__main__':
    inpt = Input()
    stats = Stats()
    stats.initialize()
    
    print ">> Program started. Waiting for input."
    
    while (1):
        command = raw_input('>> ')
        print 'input: ' + command
        inpt.translate(command)