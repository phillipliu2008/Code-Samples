import Rules


### Raise exception when move is invalid ###
    
class InvalidOthelloMoveError(Exception):
    pass

    
### Create a new Play Board ###

def create_board(row, column) -> list:

    board = []
    for x in range(row):
        board.append([])
        for y in range(column):
            board[x].append('-')
            
    return board


### Counting the scores of each side ###

def score(board) -> list:
    white = 0
    black = 0
    score =[]
    for n in board:
        white += n.count('W')
        black += n.count('B')
    score.append(white)
    score.append(black)
    return score



### Game ends if the board is filled up ###

def game_end(board) -> bool:
    score1 = score(board)
    if sum(score1) == len(board[0]) * len(board):
        return True

    return False


### Make the move from user ###

def make_move(board, row, column, turn) -> list:
        
    if board[int(row)-1][int(column)] == 'W' or board[int(row)-1][int(column)] == 'B':
        raise InvalidOthelloMoveError()
    else:
        board[int(row)-1][int(column)] = turn

    board = Rules.rule(board, row, column)

    return board


### Check if there are available moves ###

def check_move(board, turn) -> bool:
    
    counter1,counter2,counter3 = [],[],[]
    g_board = copy_list(board)
    counter1 = score(g_board)

    for row in range(len(g_board)):
        for col in range(len(g_board[0])):

            if g_board[row][col] == '-':
                g_board[row][col] = turn[0]
                g_board = Rules.rule(g_board, str(row+1), str(col))
                counter2 = score(g_board)

                if counter1[0] == counter2[0] or counter1[1] == counter2[1]:
                    counter3.append(0)
                else:
                    counter3.append(1)
                    
                g_board = copy_list(board)

    if sum(counter3)==0:
        return False
    
    return True

        
### Copy a list ###       
        
def copy_list(old_list) -> list:
    new_list = []
    for row in range(len(old_list)):
        new_list.append([])
        for col in range(len(old_list[0])):  
            new_list[row].append(old_list[row][col])

    return new_list
    


### Check who is the winner ###

def winner(score, option):
    if option == 'M':
        if score[0] > score[1]:
            return 'Winner is WHITE !'
        elif score[0] < score[1]:
            return 'Winner is BLACK !'
        else:
            return 'Tie Game !'
    elif option == 'L':
        if score[0] < score[1]:
            return 'Winner is WHITE !'
        elif score[0] > score[1]:
            return 'Winner is BLACK !'
        else:
            return 'Tie Game !'

        

    



