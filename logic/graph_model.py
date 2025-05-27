from enum import Enum
from typing import Dict, List, Union

class BlockType(Enum):
    ASSIGN_VAR = "ASSIGN_VAR"
    ASSIGN_CONST = "ASSIGN_CONST"
    INPUT = "INPUT"
    PRINT = "PRINT"
    CONDITION_EQ = "CONDITION_EQ"
    CONDITION_LT = "CONDITION_LT"

class Block:
    def __init__(self, block_id: str, block_type: BlockType, args: List[str]):
        self.block_id = block_id
        self.block_type = block_type
        self.args = args  # залежно від типу може бути V1, V2 або V, C
        self.next_blocks = []  # IDs of next blocks

    def add_next(self, next_id: str):
        self.next_blocks.append(next_id)

class Flowchart:
    def __init__(self):
        self.blocks: Dict[str, Block] = {}
        self.start_id: Union[str, None] = None

    def add_block(self, block: Block):
        if self.start_id is None:
            self.start_id = block.block_id
        self.blocks[block.block_id] = block

    def connect_blocks(self, from_id: str, to_id: str):
        self.blocks[from_id].add_next(to_id)
