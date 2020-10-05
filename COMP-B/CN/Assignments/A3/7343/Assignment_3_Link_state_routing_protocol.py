'''
@author: Sehej Bakshi
'''
import csv
import sys
import os

router_matrix = []
matrix_set = 0
nodes = []
distances = {}
unvisited = {}
previous = {}
visited = {}
interface = {}
path = []
start = 0
end = 0


def print_choices():
    print("######################################################")
    print("\nCS542 Link State Routing Simulator\n")
    print("(1) Input Network Topology File")
    print("(2) Build a Connection Table")
    print("(3) Shortest Path to Destination Router")
    print("(4) Exit")
    print("\n######################################################\n")

    pass


def check_choices(command):
    if not command.isdigit():
        print("Please enter a number as command from given choices..")

        return -1
    else:
        command = int(command)

        if command > 4 or command < 1:
            print("Please enter a valid command from given choices..")

            return -1
        else:
            return command


def process_file(fname):
    global matrix_set
    global router_matrix
    matrix_set = 0
    router_matrix = []

    with open(fname) as f:
        router_matrix = [list(map(int, x.split(" "))) for x in f]
    matrix_set = 1

    print("\nReview original topology matrix:\n")
    for line in router_matrix:
        for item in line:
            print(item)
        print()
    print()

    set_distances(
        router_matrix)


def set_distances(router_matrix):
    global distances
    global nodes

    distances = {}
    nodes = []

    num_nodes = len(router_matrix)

    for i in range(num_nodes):
        tempdict = {}
        for j in range(num_nodes):
            if i != j and router_matrix[i][j] != -1:
                tempdict[j + 1] = router_matrix[i][j]
        distances[i + 1] = tempdict
        nodes.append(i + 1)


def dijkstra(start):
    global distances
    global nodes
    global unvisited
    global previous
    global visited
    global interface

    unvisited = {node: None for node in nodes}
    previous = {node: None for node in nodes}
    interface = {node: None for node in nodes}
    visited = {node: None for node in nodes}

    current = int(start)
    currentDist = 0
    unvisited[current] = currentDist

    while True:
        for next, distance in distances[current].items():

            if next not in unvisited:
                continue
            newDist = currentDist + distance

            if not unvisited[next] or unvisited[next] > newDist:
                unvisited[next] = newDist
                previous[next] = current

                if not interface[current]:
                    interface[next] = next
                else:
                    interface[next] = interface[current]

        visited[current] = currentDist
        del unvisited[current]

        done = 1
        for x in unvisited:
            if unvisited[x]:
                done = 0
                break
        if not unvisited or done:
            break

        elements = [node for node in unvisited.items() if node[1]]

        current, currentDist = sorted(elements, key=lambda x: x[1])[0]


def shortest_path(start, end):
    global path

    path = []
    dest = int(end)
    src = int(start)
    path.append(dest)

    while dest != src:
        path.append(previous[dest])
        dest = previous[dest]

    path.reverse()


print_choices()

command = 0

while command != 4:

    command = check_choices(input("\nCommand : "))

    if command == 1:

        if matrix_set == 1:
            answer = input("\nThe network topology is already uploaded. Do you want to overwrite? (Y/N) :")

        if matrix_set == 0 or answer == 'Y' or answer == 'y':
            filename = input(
                "\nInput original network topology matrix data file[ NxN distance matrix. (value : -1 for no link, "
                "0 for self loop) : ")

            if os.path.isfile(filename):
                process_file(filename)
                start = 0
                end = 0
            else:
                print("\nThe file does not exist. Please try again..")

    elif command == 2:

        if matrix_set == 1:

            start = input("\nSelect a source router : ")

            if (start.isdigit()) and (int(start) > 0) and (int(start) <= len(router_matrix)):
                dijkstra(start)
                print("\nDestination\tInterface")

                for key in interface:
                    print(key, "\t\t", interface[key])

            else:
                start = 0
                print("\nPlease enter a valid source router.")

        else:
            print("\nNo network topology matrix exist. Please upload the data file first.. ")

    elif command == 3:

        if matrix_set == 1:

            end = input("\nSelect a destination router : ")

            if (end.isdigit()) and (int(end) > 0) and (int(end) <= len(router_matrix)):
                if int(start) == 0:
                    print("\nNo source router selected yet. Please select a source router using choice : 2.")

                elif int(start) == int(end):
                    print("\nSource and Destination routers are same. Please select a different destination router.")

                elif not previous[int(end)]:
                    print("\nThere does not exist any route from Source : %s to Destination : %s. \nPlease select a "
                          "different destination router. " % (
                              start, end))

                else:
                    shortest_path(start, end)
                    print("\nThe shortest path from router %s to router %s : " % (start, end))

                    for item in path:
                        print
                        str(item) + '  ',
                    print()
                    cost = 0
                    if visited[int(end)]:
                        cost = visited[int(end)]
                    print("\nThe total cost is : ", cost)

            else:
                print("\nPlease enter a valid destination router.")

            pass
        else:
            print("\nNo network topology matrix exist. Please upload the data file first.. ")

# Exit if command is 4.

print()

"\nExiting. Good Bye!\n"
