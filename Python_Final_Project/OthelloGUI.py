import tkinter, Pop_up, Rules, Display, Gameover

class OthelloGUI:
    
    def __init__(self):
        self.root_window = tkinter.Tk()
        self.root_window.title('Othello')
        self.turn = ''
        self.score = [0,0]
        self.row = 6
        self.col = 6
        self.gameboard = []
        self.v_counter = 0
        

        self.canvas = tkinter.Canvas(
            master=self.root_window, width=500, height=500,
            background='#009900')

        self.canvas.grid(
            row = 2, column = 0, padx = 10, pady = 10, columnspan = 2,
            sticky = tkinter.N + tkinter.S + tkinter.E + tkinter.W)
        
        self.canvas.bind('<Button-1>', self.mouse_clicked)
        self.canvas.bind('<Configure>', self.canvas_resize)

        ### Start Button display ###        
        self.start_button =tkinter.Button(
            self.root_window, text = 'Start Game', font =('Helvetica', 20),
            command = self.user_input)
        
        self.start_button.grid(
            row = 0, column = 0, padx = 60, pady = 10, columnspan = 2,
            sticky = tkinter.W + tkinter.E)

        ### Turn display ###
        self.turn_update()

        ### Score display ###
        self.score_update()

        self.root_window.rowconfigure(2, weight = 1)
        self.root_window.columnconfigure(0, weight = 1)
        self.root_window.columnconfigure(1, weight = 1)

    ### Main Loop ###
        
    def start(self):
        self.root_window.mainloop()
        
    ### Get user input from Pop-up window ###

    def user_input(self):
        pop_up = Pop_up.pop_up()
        pop_up.show()

        if pop_up.ok_clicked():

            try:
                assert(int(pop_up.rows()) % 2 == 0)
                assert(int(pop_up.rows()) > 2 )
                assert(int(pop_up.rows()) < 18)
                    
                assert(int(pop_up.cols()) % 2 == 0)
                assert(int(pop_up.cols()) > 2)
                assert(int(pop_up.cols()) < 18)
                
                self.row = int(pop_up.rows()) 
                self.col = int(pop_up.cols())

                    
                self.move = pop_up.moves()
                if self.move == 'B':
                    self.turn = 'BLACK'
                elif self.move == 'W':
                    self.turn = 'WHITE'
                self.position = pop_up.positions()

                self.gameboard = []
                self.score = [0,0]
                self.gameboard = list(Display.create_board(self.row, self.col))
            
                self.condition = pop_up.conditions()
                self.draw_board()
                self.initial_board()
                
            except:
                pass


    ### Draw the game board using current height/width ###

    def draw_board(self):
        self.canvas.delete(tkinter.ALL)
        width = self.canvas.winfo_width()
        height = self.canvas.winfo_height()
        row_temp = 0
        col_temp = 0
        self.turn_update()
        self.score_update()

        row_space = height / self.row
        row_temp = row_space
        for row in range(self.row):
            self.canvas.create_line(0, row_temp, width, row_temp, fill='black')
            row_temp = row_space + row_temp

        col_space = width / self.col
        col_temp = col_space
        for col in range(self.col):
            self.canvas.create_line(col_temp, 0, col_temp, height, fill='black')
            col_temp = col_space + col_temp

        self.draw_discs()

    ### Handle window resize ###    
            
    def canvas_resize(self, event):
        self.draw_board()

    ### When mouse is clicked, drop discs ###

    def mouse_clicked(self, event):
        width = self.canvas.winfo_width()
        height = self.canvas.winfo_height()
        
        row_space = height / self.row
        row_temp = row_space
        col_space = width / self.col
        col_temp = col_space

        brx = 0
        bry = 0

        board_row = 0
        board_col = 0
        
        for row in range(self.row):
            if event.y > (row_temp - row_space):
                if event.y < row_temp:
                    bry = row_temp
                    board_row = row
            row_temp = row_temp + row_space

        for col in range(self.col):
            if event.x > (col_temp - col_space):
                if event.x < col_temp:
                    brx = col_temp
                    board_col = col
            col_temp = col_temp + col_space
            
        tlx = brx - col_space
        tly = bry - row_space

        # Check if there are available moves 
        if Display.check_move(self.gameboard, self.turn):

            counter1 = list(Display.score(self.gameboard))

            if self.turn == 'BLACK':
                try:
                    self.gameboard = list(Display.make_move(self.gameboard, str(board_row+1), str(board_col), 'B'))
                    counter2 = list(Display.score(self.gameboard))

                     # if user choose an empty square, raise exception
                    if counter1[0] == counter2[0] or counter1[1] == counter2[1]:
                        self.gameboard[board_row][board_col] = '-'
                        raise Display.InvalidOthelloMoveError()

                    else:
                        self.canvas.create_oval(tlx+5, tly+5, brx-5, bry-5, outline='black', fill='black')
                        self.gameboard[board_row][board_col] = 'B'
                        self.change_turn()
                        self.v_counter = 0
                except:
                    pass
                
            
            elif self.turn == 'WHITE':
                try:
                    self.gameboard = list(Display.make_move(self.gameboard, str(board_row+1), str(board_col), 'W'))
                    counter2 = list(Display.score(self.gameboard))

                    # if user choose an empty square, raise exception
                    if counter1[0] == counter2[0] or counter1[1] == counter2[1]:
                        self.gameboard[board_row][board_col] = '-'
                        raise Display.InvalidOthelloMoveError()
                
                    else:
                        self.canvas.create_oval(tlx+5, tly+5, brx-5, bry-5, outline='white', fill='white')
                        self.gameboard[board_row][board_col] = 'W'
                        self.change_turn()
                        self.v_counter = 0
                except:
                    pass

        else:
            if sum(self.score) == 0:
                pass
            else:
                self.v_counter += 1
                self.change_turn()

        self.draw_discs()
        self.score = list(Display.score(self.gameboard))
        self.score_update()
        self.turn_update()

        try:
            
            if self.v_counter == 2 or Display.game_end(self.gameboard):
                self.score = list(Display.score(self.gameboard))
                msg = Display.winner(self.score, self.condition)
                #game_over = Gameover.game_over(msg)
                #game_over.show()
                tkinter.messagebox.showinfo(title = 'Game Over', message = msg)

        except:
            pass


    ### Display turn on the main window ###

    def turn_update(self):
        self.turn_dis = tkinter.StringVar()
        self.turn_dis.set('TURN: ' + self.turn)
        turn_label = tkinter.Label(
            self.root_window, textvariable = self.turn_dis,
            font = ('Helvetica', 15))
        turn_label.grid(row = 1, column = 0, padx = 10, sticky = tkinter.W)

    ### Display score in the main window ###

    def score_update(self):
        self.score_dis = tkinter.StringVar()
        self.score_dis.set('{:7}{:2}{:2} -- {:2}{:2}'.format(
            'SCORE:','W',str(self.score[0]),'B',str(self.score[1])))
        score_label = tkinter.Label(
            self.root_window, textvariable = self.score_dis,
            font = ('Helvetica', 15))
        score_label.grid(row = 1, column = 1, padx = 10, sticky = tkinter.E)

    ### Change turn ###
        
    def change_turn(self):
        if self.turn == 'BLACK':
            self.turn = 'WHITE'
        elif self.turn == 'WHITE':
            self.turn = 'BLACK'

    ### Create initial board with user desired discs position ###

    def initial_board(self):
        width = self.canvas.winfo_width()
        height = self.canvas.winfo_height()
        middlex = width / 2
        middley = height / 2
        row_space = height / self.row
        col_space = width / self.col
        half_row = int(self.row / 2)
        half_col = int(self.col / 2)
        
        if self.position == 'W':
            
            self.canvas.create_oval(middlex + 5, middley-row_space + 5,
                                    middlex+col_space - 5, middley - 5, outline='black', fill='black')
            self.gameboard[half_row][half_col-1] = 'B'
            
            self.canvas.create_oval(middlex-col_space + 5, middley + 5,
                                    middlex - 5, middley+row_space - 5, outline='black', fill='black')
            self.gameboard[half_row-1][half_col] = 'B'
            
            self.canvas.create_oval(middlex-col_space + 5, middley-row_space + 5,
                                    middlex - 5, middley - 5, outline='white', fill='white')
            self.gameboard[half_row-1][half_col-1] = 'W'
            
            self.canvas.create_oval(middlex + 5, middley + 5,
                                    middlex+col_space - 5, middley+row_space - 5, outline='white', fill='white')
            self.gameboard[half_row][half_col] = 'W'
            
        elif self.position == 'B':
            
            self.canvas.create_oval(middlex + 5, middley-row_space + 5,
                                    middlex+col_space - 5, middley - 5, outline='white', fill='white')
            self.gameboard[half_row][half_col-1] = 'W'
            
            self.canvas.create_oval(middlex-col_space + 5, middley + 5,
                                    middlex - 5, middley+row_space - 5, outline='white', fill='white')
            self.gameboard[half_row-1][half_col] = 'W'
            
            self.canvas.create_oval(middlex-col_space + 5, middley-row_space + 5,
                                    middlex - 5, middley - 5, outline='black', fill='black')
            self.gameboard[half_row-1][half_col-1] = 'B'
            
            self.canvas.create_oval(middlex + 5, middley + 5,
                                    middlex+col_space - 5, middley+row_space - 5, outline='black', fill='black')
            self.gameboard[half_row][half_col] = 'B'

        self.score = list(Display.score(self.gameboard))
        self.score_update()

    ### Re-draw discs after the window is resized ###

    def draw_discs(self):
        width = self.canvas.winfo_width()
        height = self.canvas.winfo_height()
        row_space = height / self.row
        col_space = width / self.col
        row_temp = row_space
        col_temp = col_space
        tlx, tly, brx, bry = 0, 0, 0, 0
        
        
        for row in range(len(self.gameboard)):
            for col in range(len(self.gameboard[0])):
                if self.gameboard[row][col] == 'B':
                    bry = row_space * row + row_temp
                    brx = col_space * col + col_temp
                    tly = bry - row_space
                    tlx = brx - col_space
                    self.canvas.create_oval(tlx+5, tly+5, brx-5, bry-5, outline='black', fill='black')

                elif self.gameboard[row][col] == 'W':
                    bry = row_space * row + row_temp
                    brx = col_space * col + col_temp
                    tly = bry - row_space
                    tlx = brx - col_space
                    self.canvas.create_oval(tlx+5, tly+5, brx-5, bry-5, outline='white', fill='white')
                    
                    
            
if __name__ == '__main__':
    OthelloGUI().start()




    
