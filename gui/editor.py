# gui/editor.py
import tkinter as tk
from tkinter import simpledialog, filedialog, messagebox
from gui.graph_view import GraphCanvas
from gui.file_io import save_flowchart, load_flowchart

class FlowchartEditor(tk.Tk):
    def __init__(self):
        super().__init__()
        self.title("Редактор блок-схем потоків")
        self.geometry("1000x600")
        self.canvas_area = GraphCanvas(self)
        self.canvas_area.pack(fill=tk.BOTH, expand=True)

        self._init_toolbar()

    def _init_toolbar(self):
        toolbar = tk.Frame(self)
        toolbar.pack(side=tk.TOP, fill=tk.X)

        tk.Button(toolbar, text="Додати блок", command=self.canvas_area.add_block_dialog).pack(side=tk.LEFT)
        tk.Button(toolbar, text="З'єднати", command=self.canvas_area.start_connection_mode).pack(side=tk.LEFT)
        tk.Button(toolbar, text="Зберегти", command=self.save).pack(side=tk.LEFT)
        tk.Button(toolbar, text="Завантажити", command=self.load).pack(side=tk.LEFT)

    def save(self):
        path = filedialog.asksaveasfilename(defaultextension=".json")
        if path:
            save_flowchart(self.canvas_area.flowchart, path)

    def load(self):
        path = filedialog.askopenfilename(filetypes=[("JSON Files", "*.json")])
        if path:
            flowchart = load_flowchart(path)
            self.canvas_area.set_flowchart(flowchart)

if __name__ == "__main__":
    app = FlowchartEditor()
    app.mainloop()

