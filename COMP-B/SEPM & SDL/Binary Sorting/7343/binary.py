from numpy import *

def sequentialSearch(arr, element):
    pos = 0
    for pos in range(len(arr)):
        if arr[pos] == element:
            break
    if pos == len(arr):
        print("Element not found")
    else:
        print("Element found at index:", pos)


def binarySearch(arr, low, high, element):
    if high >= low:
        mid = (low + high) // 2
        if arr[mid] == element:
            return mid
        elif arr[mid] > element:
            return binarySearch(arr, low, mid - 1, element)
        else:
            return binarySearch(arr, mid + 1, high, element)

    else:
        return -1


a = []
n = int(input("Enter the number of elements: "))

print('Enter elements:')

for i in range(n):
    a.append(int(input()))

arr = array(a)

element = int(input("Enter element to search: "))

choice = int(input("1. Sequential Search \n2. Binary Search \n"))

if choice == 1:
    sequentialSearch(arr, element)
elif choice == 2:
    arr1 = sort(arr)
    print(arr1)
    result = binarySearch(arr1, 0, len(arr), element)
    if result == -1:
        print("Element not found")
    else:
        print("Element found at pos:", result)
else:
    print("Incorrect choice")
