# logic/interpreter.py

import threading

def run_flowchart(flowchart, shared_memory, input_queue, output_queue):
    current_id = flowchart.start_id
    visited = set()

    while current_id:
        block = flowchart.blocks[current_id]
        if block.block_type == BlockType.ASSIGN_VAR:
            shared_memory[block.args[0]] = shared_memory[block.args[1]]
        elif block.block_type == BlockType.ASSIGN_CONST:
            shared_memory[block.args[0]] = int(block.args[1])
        elif block.block_type == BlockType.INPUT:
            shared_memory[block.args[0]] = int(input_queue.get())
        elif block.block_type == BlockType.PRINT:
            output_queue.append(shared_memory[block.args[0]])
        elif block.block_type == BlockType.CONDITION_EQ:
            if shared_memory[block.args[0]] == int(block.args[1]):
                current_id = block.next_blocks[0]
                continue
            else:
                current_id = block.next_blocks[1]
                continue
        elif block.block_type == BlockType.CONDITION_LT:
            if shared_memory[block.args[0]] < int(block.args[1]):
                current_id = block.next_blocks[0]
                continue
            else:
                current_id = block.next_blocks[1]
                continue

        if block.next_blocks:
            current_id = block.next_blocks[0]
        else:
            current_id = None
