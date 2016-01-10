import tkinter
class pop_up:

    def __init__(self):
        self.pop_up = tkinter.Toplevel()

        # Titile
        title_label = tkinter.Label(
            self.pop_up, text = 'Please enter the information of the game',
            font = ('Helvetica', 20))

        title_label.grid(
            row = 0, column = 0, columnspan = 2,
            sticky = tkinter.W)

        # Row
        row_label = tkinter.Label(
            self.pop_up, text = '1) Row Number: ',
            font = ('Helvetica', 15))

        row_label.grid(row = 1, column = 0, sticky = tkinter.W)

        self.row_entry = tkinter.Entry(
            self.pop_up, width = 20, font = ('Helvetica', 15))

        self.row_entry.grid(
            row = 1, column = 1,
            sticky = tkinter.W + tkinter.E)

        # Column
        col_label = tkinter.Label(
            self.pop_up, text = '2) Column Number: ',
            font = ('Helvetica', 15))

        col_label.grid(row = 2, column = 0, sticky = tkinter.W)

        self.col_entry = tkinter.Entry(
            self.pop_up, width = 20, font = ('Helvetica', 15))

        self.col_entry.grid(
            row = 2, column = 1,
            sticky = tkinter.W + tkinter.E)

        # Move
        move_label = tkinter.Label(
            self.pop_up, text = '3) Player that move first: ',
            font = ('Helvetica', 15))

        move_label.grid(row = 3, column = 0, sticky = tkinter.W)

        self.Moves = tkinter.StringVar()

        tkinter.Radiobutton(self.pop_up, text = 'Black', variable = self.Moves,
                    value = 'B', command = self.get_moves,
                    font = ('Helvetica', 10)).grid(row = 3, column = 1, sticky = tkinter.W)

        tkinter.Radiobutton(self.pop_up, text = 'White', variable = self.Moves,
                    value = 'W', command = self.get_moves,
                    font = ('Helvetica', 10)).grid(row = 3, column = 1, sticky = tkinter.E)


        # Position
        pos_label = tkinter.Label(
            self.pop_up, text = '4) Color disc on the top-left position: ',
            font = ('Helvetica', 15))

        pos_label.grid(row = 4, column = 0, sticky = tkinter.W)

        self.Positions = tkinter.StringVar()

        tkinter.Radiobutton(self.pop_up, text = 'Black', variable = self.Positions,
                    value = 'B', command = self.get_positions,
                    font = ('Helvetica', 10)).grid(row = 4, column = 1, sticky = tkinter.W)

        tkinter.Radiobutton(self.pop_up, text = 'White', variable = self.Positions,
                    value = 'W', command = self.get_positions,
                    font = ('Helvetica', 10)).grid(row = 4, column = 1, sticky = tkinter.E)
                        

        # Condition
        con_label = tkinter.Label(
            self.pop_up, text = '5) Most discs win or less discs win: ',
            font = ('Helvetica', 15))

        con_label.grid(row = 5, column = 0, sticky = tkinter.W)

        self.Conditions = tkinter.StringVar()

        tkinter.Radiobutton(self.pop_up, text = 'Most', variable = self.Conditions,
                    value = 'M', command = self.get_conditions,
                    font = ('Helvetica', 10)).grid(row = 5, column = 1, sticky = tkinter.W)

        tkinter.Radiobutton(self.pop_up, text = 'Less', variable = self.Conditions,
                    value = 'L', command = self.get_conditions,
                    font = ('Helvetica', 10)).grid(row = 5, column = 1, sticky = tkinter.E)
        
        
        button_frame = tkinter.Frame(self.pop_up)
        button_frame.grid(row = 6, column = 1, sticky = tkinter.E)

        ok_button = tkinter.Button(
            button_frame, text = 'OK', font = ('Helvetica', 15),
            command = self._on_ok)

        ok_button.grid(row = 0, column = 0)

        cancel_button = tkinter.Button(
            button_frame, text = 'Cancel', font = ('Helvetica', 15),
            command = self._on_cancel)

        cancel_button.grid(row = 0, column = 1)

        self.pop_up.columnconfigure(1, weight = 1)

        self._ok_clicked = False
        self.row = 0
        self.col = 0
        self.move = ''
        self.pos = ''
        self.con = ''

    def show(self):
        self.pop_up.grab_set()
        self.pop_up.wait_window()


    def ok_clicked(self):
        return self._ok_clicked


    def rows(self):
        return self.row

    def cols(self):
        return self.col

    def get_moves(self):
        self.move = self.Moves.get()

    def moves(self):
        return self.move

    def get_positions(self):
        self.pos = self.Positions.get()

    def positions(self):
        return self.pos

    def get_conditions(self):
        self.con = self.Conditions.get()

    def conditions(self):
        return self.con
    

    def _on_ok(self):
        self._ok_clicked = True
        self.row = self.row_entry.get()
        self.col = self.col_entry.get()
        self.pop_up.destroy()


    def _on_cancel(self):
        self.pop_up.destroy()
