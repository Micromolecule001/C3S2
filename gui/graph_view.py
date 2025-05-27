import tkinter as tk
from tkinter import simpledialog
from logic.graph_model import Flowchart, Block, BlockType

class GraphCanvas(tk.Canvas):
    def __init__(self, parent):
        super().__init__(parent, bg="white")
        self.flowchart = Flowchart()
        self.block_widgets = {}
        self.connect_mode = False
        self.connect_from = None
        self.drag_data = {"item": None, "x": 0, "y": 0}
        self.bind("<Button-1>", self.on_click)
        self.bind("<B1-Motion>", self.on_drag_motion)
        self.bind("<ButtonRelease-1>", self.on_drag_release)

    def add_block_dialog(self):
        block_id = simpledialog.askstring("ID блоку", "Введіть ID блоку:")
        block_type = simpledialog.askstring("Тип блоку", "Введіть тип блоку (ASSIGN_VAR, ASSIGN_CONST, INPUT, PRINT, CONDITION_EQ, CONDITION_LT):")
        args = simpledialog.askstring("Аргументи", "Введіть аргументи (через пробіл):").split()

        block = Block(block_id, BlockType[block_type], args)
        self.flowchart.add_block(block)

        x, y = 100 + len(self.block_widgets)*20, 100 + len(self.block_widgets)*40
        widget = self.create_rectangle(x, y, x+120, y+40, fill="lightblue")
        text = self.create_text(x+60, y+20, text=f"{block_id}\n{block_type}")
        self.block_widgets[block_id] = (widget, text, x, y)

    def start_connection_mode(self):
        self.connect_mode = True
        self.connect_from = None
        self.bind("<Button-1>", self.on_connect_click)

    def on_connect_click(self, event):
        clicked_id = self.get_block_at(event.x, event.y)
        if not clicked_id:
            return
        if not self.connect_from:
            self.connect_from = clicked_id
        else:
            self.flowchart.connect_blocks(self.connect_from, clicked_id)
            x1, y1 = self.block_widgets[self.connect_from][2:]
            x2, y2 = self.block_widgets[clicked_id][2:]
            self.create_line(x1+60, y1+40, x2+60, y2, arrow=tk.LAST)
            self.connect_from = None
            self.connect_mode = False
            self.bind("<Button-1>", self.on_click)

    def get_block_at(self, x, y):
        for block_id, (rect, text, bx, by) in self.block_widgets.items():
            if bx <= x <= bx+120 and by <= y <= by+40:
                return block_id
        return None

    def on_click(self, event):
        clicked_id = self.get_block_at(event.x, event.y)
        if clicked_id:
            rect, text, bx, by = self.block_widgets[clicked_id]
            self.drag_data["item"] = clicked_id
            self.drag_data["x"] = event.x
            self.drag_data["y"] = event.y

    def on_drag_motion(self, event):
        item_id = self.drag_data["item"]
        if item_id:
            dx = event.x - self.drag_data["x"]
            dy = event.y - self.drag_data["y"]
            rect, text, bx, by = self.block_widgets[item_id]
            self.move(rect, dx, dy)
            self.move(text, dx, dy)
            self.block_widgets[item_id] = (rect, text, bx + dx, by + dy)
            self.drag_data["x"] = event.x
            self.drag_data["y"] = event.y

    def on_drag_release(self, event):
        self.drag_data["item"] = None

    def set_flowchart(self, flowchart):
        self.delete("all")
        self.flowchart = flowchart
        self.block_widgets = {}
        for i, block in enumerate(flowchart.blocks.values()):
            x, y = 100 + i * 20, 100 + i * 40
            widget = self.create_rectangle(x, y, x + 120, y + 40, fill="lightblue")
            text = self.create_text(x + 60, y + 20, text=f"{block.block_id}\n{block.block_type.name}")
            self.block_widgets[block.block_id] = (widget, text, x, y)
            for next_id in block.next_blocks:
                nx, ny = x + 60, y + 40
                nx2, ny2 = nx + 60, ny + 40
                self.create_line(nx, ny, nx2, ny2, arrow=tk.LAST)
