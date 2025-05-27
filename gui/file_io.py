import json
from logic.graph_model import Flowchart, Block, BlockType

def save_flowchart(flowchart, path):
    data = {
        "start_id": flowchart.start_id,
        "blocks": [
            {
                "id": b.block_id,
                "type": b.block_type.name,
                "args": b.args,
                "next": b.next_blocks
            }
            for b in flowchart.blocks.values()
        ]
    }
    with open(path, "w") as f:
        json.dump(data, f, indent=2)

def load_flowchart(path):
    with open(path, "r") as f:
        data = json.load(f)

    fc = Flowchart()
    for b in data["blocks"]:
        block = Block(b["id"], BlockType[b["type"]], b["args"])
        block.next_blocks = b["next"]
        fc.add_block(block)
    fc.start_id = data["start_id"]
    return fc
