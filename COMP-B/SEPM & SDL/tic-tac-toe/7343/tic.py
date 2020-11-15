daBoard = {'7': ' ' , '8': ' ' , '9': ' ' ,
            '4': ' ' , '5': ' ' , '6': ' ' ,
            '1': ' ' , '2': ' ' , '3': ' ' }

board_keys = []

for key in daBoard:
    board_keys.append(key)

def printBoard(board):
    print(board['7'] + '|' + board['8'] + '|' + board['9'])
    print('-+-+-')
    print(board['4'] + '|' + board['5'] + '|' + board['6'])
    print('-+-+-')
    print(board['1'] + '|' + board['2'] + '|' + board['3'])

def game():

    turn = 'X'
    count = 0

    for i in range(10):
        printBoard(daBoard)
        print("It's your turn, " + turn + ". Move to which place?")

        move = input()

        if daBoard[move] == ' ':
            daBoard[move] = turn
            count += 1
        else:
            print("That place is already filled.\nMove to which place?")
            continue

        if count >= 5:
            if daBoard['7'] == daBoard['8'] == daBoard['9'] != ' ': # across the top
                printBoard(daBoard)
                print("\nGame Over.\n")
                print(" **** " + turn + " won. ****")
                break
            elif daBoard['4'] == daBoard['5'] == daBoard['6'] != ' ': # across the middle
                printBoard(daBoard)
                print("\nGame Over.\n")
                print(" **** " + turn + " won. ****")
                break
            elif daBoard['1'] == daBoard['2'] == daBoard['3'] != ' ': # across the bottom
                printBoard(daBoard)
                print("\nGame Over.\n")
                print(" **** " + turn + " won. ****")
                break
            elif daBoard['1'] == daBoard['4'] == daBoard['7'] != ' ': # down the left side
                printBoard(daBoard)
                print("\nGame Over.\n")
                print(" **** " + turn + " won. ****")
                break
            elif daBoard['2'] == daBoard['5'] == daBoard['8'] != ' ': # down the middle
                printBoard(daBoard)
                print("\nGame Over.\n")
                print(" **** " + turn + " won. ****")
                break
            elif daBoard['3'] == daBoard['6'] == daBoard['9'] != ' ': # down the right side
                printBoard(daBoard)
                print("\nGame Over.\n")
                print(" **** " + turn + " won. ****")
                break
            elif daBoard['7'] == daBoard['5'] == daBoard['3'] != ' ': # diagonal
                printBoard(daBoard)
                print("\nGame Over.\n")
                print(" **** " + turn + " won. ****")
                break
            elif daBoard['1'] == daBoard['5'] == daBoard['9'] != ' ': # diagonal
                printBoard(daBoard)
                print("\nGame Over.\n")
                print(" **** " + turn + " won. ****")
                break

        if count == 9:
            print("\nGame Over.\n")
            print("It's a Tie!!")

        if turn =='X':
            turn = 'O'
        else:
            turn = 'X'

    restart = input("Do want to play Again?(y/n)")
    if restart == "y" or restart == "Y":
        for key in board_keys:
            daBoard[key] = " "

        game()

if __name__ == "__main__":
    game()
