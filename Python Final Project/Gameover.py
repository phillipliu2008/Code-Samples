import tkinter

### Window poped up when game is over ###

class game_over:

    def __init__(self, txt):
        self.over = tkinter.Toplevel()
        self.txt = txt
        
        msg_label = tkinter.Label(
            self.over, text = txt,
            font = ('Helvetica', 15))

        msg_label.grid(
            row = 0, column = 0, sticky = tkinter.N + tkinter.S + tkinter.E + tkinter.W)

        button_frame = tkinter.Frame(self.over)
        button_frame.grid(row = 1, column = 0, padx = 80, pady = 10, sticky = tkinter.E)

        ok_button = tkinter.Button(
            button_frame, text = 'OK', font = ('Helvetica', 15),
            command = self._on_ok)

        ok_button.grid(row = 0, column = 0)

        self._ok_clicked = False

    def ok_clicked(self):
        return self._ok_clicked

    def show(self):
        self.pop_up.grab_set()
        self.pop_up.wait_window()

    def _on_ok(self):
        self._ok_clicked = True
        self.over.destroy()
