'''
Created on Feb 28, 2014

@author: Will
'''
from view.Stats import Stats

class Input():
    
    stats = Stats()
    
    '''  
    A function to execute the 
    translated command and perform a query
    on the graph to retrieve the statistics
    and display them to the usuer.
    @input command the command string
    @input city used for the cityInfo command only
    '''  
    def query(self,command,city=None,citylist=None):
        
        if (command == 'allcities'):      
            list = stats.all_cities()
            for city in list:
                print city
        elif (command == 'city'):
            data = stats.city_info(city)
            if (data == None): return
            for field in data:
                print field + " : " + str(data[field])
        elif (command == 'continents'):     print stats.continent_grouping()
        elif (command == 'hubcities'):      print stats.hub_cities()
        elif (command == 'longestflight'):  print stats.longest_flight()
        elif (command == 'shortestflight'): print stats.shortest_flight()
        elif (command == 'biggestcity'):    print stats.biggest_city()
        elif (command == 'smallestcity'):   print stats.smallest_city()
        elif (command == 'averagesize'):    print stats.average_population()
        elif (command == 'averagedist'):    print stats.average_distance()
        elif (command == 'circlemap'):      stats.display_map()
        elif (command == 'pathinfo'):       
            infostrings = self.stats.path_info(citylist)
            for info in infostrings:
               print info
        
    
    '''  
    A function that will translate raw input
    into function commands in real time
    @input command the command to translate
    '''  
    def translate(self, command):
        self.help_command(command)
        self.extremity_commands(command)
        self.city_info_command(command)
        self.average_commands(command)
        self.grouping_commands(command)
        self.display_command(command)
        self.edit_commands(command)
        self.path_info(command)
    
    
    '''
    A function that translates
    the path info command and executes the query.
    @param command the input user command string
    '''
    def path_info(self,command,raw_input=raw_input):
        if ("check path" in command):
            pathlist = []
            while True:
                pathlist.append(raw_input("Enter a city name: "))
                cont = raw_input("Continue adding? (Type '\g' to finish.)")
                if (cont == "\g"): 
                    self.query('pathinfo',None,pathlist)
                    return
    
                
    
    
    '''
    A helper function that will check the
    input command string for arguments that
    execute commands involving the editing of 
    the graph and its elements
    @param command the input string from the user
    '''
    def edit_commands(self,command):
        if ("-ac" in command):
            index = command.find("-ac") + 4
            location = command[index:]
            self.add_city(location)
        
        if ("-rc" in command):
            index = command.find("-rc") + 4
            cityname = command[index:]
            self.stats.graph.remove_city(cityname)
        
        if ("remove" in command and "route" in command):
            self.remove_route()
            
        if ("add" in command and "route" in command):
            self.add_route()
        
        if ("-edit" in command):
            index = command.find("-edit") + 6
            location = command[index:]
            self.edit_city(location)
    
    
    '''
    A function that allows the user to edit the
    parameters of a given city through a series
    of raw_input user prompts
    @param raw_input used in debugging raw_input functions
    '''
    def edit_city(self,city,raw_input=raw_input):
        node = self.stats.graph.find_city('name',city)
        if (node == None): return
        
        data = node.get_data()
        edits = 5
        while (edits > 0):
            param = raw_input("What parameter should we edit? (Type 'exit' to stop.)\n")
            if ('exit' in param):
                return
            print "Value: " + str(data[param])
            print "New Value: "
            if (param == 'coordinates'):
                data[param] = {}
                longitude = raw_input("N or S? ")
                data[param][longitude] = int(raw_input("Degrees? "))
                latitude = raw_input("E or W? ")
                data[param][latitude] = int(raw_input("Degrees? "))
            elif (param == 'population' or
                  param == 'region' or 
                  param == 'timezone'):
                data[param] = int(raw_input(""))
            else:
                data[param] = raw_input("")
            
            edits -= 1
                
    
    '''        
    Removes the given route from the graph
    Promptes the user several times for input
    about the names of the cities of the route
    @param raw_input used for debugging raw_input prompts
    '''
    def remove_route(self,raw_input=raw_input):
        city1 = raw_input("First city name? ")
        city2 = raw_input("Second city name? ")
        self.stats.graph.remove_route(city1,city2)
    
    '''
    Adds a route to the graph generated from 
    user input prompts
    @param raw_input used for debugging raw_input prompts
    '''
    def add_route(self,raw_input=raw_input):
        city1 = raw_input("First city name? ")
        city2 = raw_input("Second city name? ")
        weight = int(raw_input("Distance? "))
        source = self.stats.graph.find_city('name',city1)
        target = self.stats.graph.find_city('name',city2)
        self.stats.graph.add_edge(source,target,weight)  
        
        
    '''
    Adds the given city to the graph.
    Prompts the user several times for input
    about information about the city.
    @param city the name of the city to add
    @param raw_input used for debugging raw_input prompts
    '''
    def add_city(self,city,raw_input=raw_input):
        city_data = {}
        city_data['name']        = city
        
        print "PLease input city info for " + city_data['name'] + "."
        
        city_data['code']        = raw_input("Code? ")
        city_data['country']     = raw_input("Country? ")
        city_data['continent']   = raw_input("Continent? ")
        city_data['timezone']    = int(eval(raw_input("Time zone? ")))
        
        print "Coordinates? "
        city_data['coordinates'] = {}
        
        longitude = raw_input("N or S? ")
        city_data['coordinates'][longitude] = int(raw_input("Degrees? "))
        
        latitude = raw_input("E or W? ")
        city_data['coordinates'][latitude] = int(raw_input("Degrees? "))
        
        city_data['population']  = int(raw_input("Population? "))
        city_data['region']      = int(raw_input("Region? "))
        
        self.stats.graph.add_node(city_data)
        
    '''  
    A helper function that will check the 
    given command string for arguments that
    indicate the circle map command.
    @input command the input string of commands
    '''  
    def display_command(self,command):
        if ('display' in command or 
            'visual'  in command or
            'map'     in command):
            self.query('circlemap')
    
    '''          
    A helper function that parsese the
    command for those that need a
    grouping of the cities: hub cities,
    continent grouping, and listing all cities.
    @input command the input string of commands
    '''  
    def grouping_commands(self,command):
        if ('hub'    in command and 
            'cities' in command): 
            self.query('hubcities')
        if ('list all'    in command or 
            'all cities'  in command or 
            'list cities' in command): 
            self.query('allcities')
        if (('sort'  in command and 'continents' in command) or
            ('group' in command and 'continents' in command)):
            self.query('continents')
    
    '''          
    A helper function for translate that
    handles only the city information command.
    @input command the command to translate
    '''  
    def city_info_command(self,command):
        if ('-c ' in command):
            index = command.find('-c ') + 3
            location = command[index:]
            self.query('city',location)

    '''  
    A helper function for translate that deals 
    with the simple functions involving largest,
    smallest, longest, and largest values.
    There are "extremities".
    @input command the input string of commands
    '''  
    def extremity_commands(self,command):
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
    
    '''      
    A helper function that parses the command
    for the commands that involve taking 
    averages: distance, and population.
    @input command the input string of commands
    '''  
    def average_commands(self,command):
        if ('average'     in command and 
            ('population' in command or 'size' in command)):
            self.query('averagesize')
        if ('average'     in command and
            ('distance'   in command or 'length' in command)):
            self.query('averagedist')
    
    '''           
    Another helper for translate that deals with
    applying the help file for confused users.
    It simply loads a text file from memory and displays it
    @input command the input string of commands
    '''  
    def help_command(self,command):
        if (command[0] == 'h' and len(command) < 6 or 'help' in command):
            helpMe = open('../../input/help.txt',"r")
            print helpMe.read()

    

if __name__ == '__main__':
    inpt = Input()
    stats = Stats()
    stats.initialize()
    
    print ">> Program started. Waiting for input. (Type 'h' for help)"
    
    while (1):
        input = raw_input('>> ')
        print 'input : ' + input + "\n"
        inpt.translate(input)