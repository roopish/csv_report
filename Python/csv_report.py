#!/usr/bin/env python

def main():
    csvfile = open('test.csv', 'r')
    input_dict = {} # dictionary to store names, events and avg time
    mean=0.0
    for line in csvfile.readlines():
        cols = line.split(",")
        names = cols[0].title() #capitalize first letter
        time = cols[1]
        city = cols[2]
        if input_dict.get(names): #name already exists in dictionary
           input_dict[names].append(time)
        else: #new record
            input_dict[names] = list([time])
    print("Name\t"+"Avg_time\t"+"No_Of_events")    
    for key,time in input_dict.items():
        mean = (sum(map(int, time))/len(time))
        print(key+"\t"+str(mean)+"\t\t"+ str(len(time)));

#-------------------------------
if __name__ == "__main__":
    main()
