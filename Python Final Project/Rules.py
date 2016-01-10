### Basic rules for the game ###

def rule(board, rows, columns) -> list:
    col = int(columns) 
    row = int(rows) 

    
    for n in range(len(board[0])): 
        if board[row-1][n] == board[row-1][col]:

            
            # Check horizontally if there are any pieces to flip
            if board[row-1][n] == 'B':
                if col > n:
                    board = check_horizontal(board,row-1,n+1,col,'W','B')
                elif col < n:
                    board = check_horizontal(board,row-1,col+1,n,'W','B')
            
                
            elif board[row-1][n] == 'W':
                if col > n:
                    board = check_horizontal(board,row-1,n+1,col,'B','W')
                elif col < n:
                    board = check_horizontal(board,row-1,col+1,n,'B','W')


    for n in range(len(board)):
        if board[n][col] == board[row-1][col]:

            # Check vertically if there are any pieces to flip
            if board[n][col] == 'B':
                if row-1 > n:     
                    board = check_vertical(board,n+1,row-1,col,'W','B')
 
                elif row-1 < n:
                    board = check_vertical(board,row,n,col,'W','B')


            elif board[n][col] == 'W':
                if row-1 > n:
                    board = check_vertical(board,n+1,row-1,col,'B','W')

                elif row-1 < n:
                    board = check_vertical(board,row,n,col,'B','W')


    u_list = check_diagonal(board, row-1, col, 'up')
    
    for n in range(len(u_list)):

        if board[u_list[n][0]][u_list[n][1]] == board[row-1][col]:

            # Check up-slope if there are any pieces to flip
            if board[u_list[n][0]][u_list[n][1]] == 'B':
                if row-1 > u_list[n][0] and col < u_list[n][1]:      
                    board = check_up_diagonal(board,row-1,col,u_list[n][1],'W','B')
                                              
                elif row-1 < u_list[n][0] and col > u_list[n][1]:
                    board = check_up_diagonal(board,u_list[n][0],u_list[n][1],col,'W','B')
                
            elif board[u_list[n][0]][u_list[n][1]] == 'W':
                if row-1 > u_list[n][0] and col < u_list[n][1]:      
                    board = check_up_diagonal(board,row-1,col,u_list[n][1],'B','W')
                                              
                elif row-1 < u_list[n][0] and col > u_list[n][1]:
                    board = check_up_diagonal(board,u_list[n][0],u_list[n][1],col,'B','W')
                      
    d_list = check_diagonal(board, row-1, col, 'down')

    for n in range(len(d_list)):

        if board[d_list[n][0]][d_list[n][1]] == board[row-1][col]:

            # Check down-slope if there are any pieces to flip       
            if board[d_list[n][0]][d_list[n][1]] == 'B':
                if row-1 < d_list[n][0] and col < d_list[n][1]:      
                    board = check_down_diagonal(board,row-1,col,u_list[n][1],'W','B')

                elif row-1 > d_list[n][0] and col > d_list[n][1]:      
                    board = check_down_diagonal(board,d_list[n][0],d_list[n][1],col,'W','B')

            elif board[d_list[n][0]][d_list[n][1]] == 'W':
                if row-1 < d_list[n][0] and col < d_list[n][1]:      
                    board = check_down_diagonal(board,row-1,col,u_list[n][1],'B','W')

                elif row-1 > d_list[n][0] and col > d_list[n][1]:      
                    board = check_down_diagonal(board,d_list[n][0],d_list[n][1],col,'B','W')

      
    return board


### Check the same color disk horizontally, if necessary, fill the opposite color disk in between ###
            
def check_horizontal(board, row, col1, col2, color1, color2) -> list:
    
    c_list = []
    for x in range(col1, col2):
        if board[row][x] == color1:
            c_list.append(0)
        else:
            c_list.append(1)

    if sum(c_list) == 0:
        for x in range(col1, col2):
            board[row][x] = color2
       
    return board


### Check the same color disk vertically, if necessary, fill the opposite color disk in between ###
        
def check_vertical(board, row1, row2, col, color1, color2) -> list:

    c_list = []
    for x in range(row1, row2):
        if board[x][col] == color1:
            c_list.append(0)
        else:
            c_list.append(1)

    if sum(c_list) == 0:
        for x in range(row1, row2):
            board[x][col] = color2

    return board


### Create a list of all the disks that are diagonal to the chosen one ###

def check_diagonal(board, row, col, up_down) -> list:
    position = []
    row1 = row
    col1 = col
    if up_down == 'up':
        while True:
            try:
                row1 -= 1
                col1 += 1
                board[row1][col1]
                position.append((row1,col1))
            except:
                row1 = row
                col1 = col
                break
            
        while True:
            try:
                row1 += 1
                col1 -= 1
                board[row1][col1]
                position.append((row1,col1))
            except:
                row1 = row
                col1 = col
                break
    elif up_down == 'down':
        while True:
            try:
                row1 += 1
                col1 += 1
                board[row1][col1]
                position.append((row1,col1))
            except:
                row1 = row
                col1 = col
                break
        while True:
            try:
                row1 -= 1
                col1 -= 1
                board[row1][col1]
                position.append((row1,col1))
            except:
                row1 = row
                col1 = col
                break
    return position

        
### Check the same color disc diagonally (up-slope), if necessary, fill the opposite color disc in between ###            

def check_up_diagonal(board, row, col1, col2, color1, color2) -> list:

    c_list = []
    row1 = row
    if row < 0 or col1 < 0 or col2 < 0:
        return board
    
    for x in range(col1+1, col2):
        row1 -= 1
        if board[row1][x] == color1:
            c_list.append(0)
        else:
            c_list.append(1)

    if sum(c_list) == 0:
        for x in range(col1+1, col2):
            row -= 1
            if row >= 0:
                board[row][x] = color2

    return board


### Check the same color disc diagonally (down-slope), if necessary, fill the opposite color disc in between ###        
       
def check_down_diagonal(board, row, col1, col2, color1, color2) -> list:

    c_list = []
    row1 = row
    if row < 0 or col1 < 0 or col2 < 0:
        return board
    
    for x in range(col1+1, col2):
        row1 += 1
        if board[row1][x] == color1:
            c_list.append(0)
        else:
            c_list.append(1)

    if sum(c_list) == 0:
        for x in range(col1+1, col2):
            row += 1
            board[row][x] = color2

    return board                                          
    



    
