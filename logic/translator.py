# logic/translator.py
from logic.graph_model import Flowchart, BlockType
import textwrap

def translate_flowchart_set(flowcharts: list[Flowchart]) -> str:
    code = textwrap.dedent("""\
        import threading

        shared_vars = [0] * 100
        input_data = []
        input_index = 0
        input_lock = threading.Lock()

        def get_input():
            global input_index
            with input_lock:
                val = input_data[input_index]
                input_index += 1
                return val

    """)

    for i, fc in enumerate(flowcharts):
        thread_code = generate_thread_function(fc, i)
        code += thread_code + "\n\n"

    # Створення та запуск потоків
    code += textwrap.dedent("""\
        if __name__ == "__main__":
            import sys
            input_data = list(map(int, sys.stdin.read().split()))
            threads = []
    """)

    for i in range(len(flowcharts)):
        code += f"    threads.append(threading.Thread(target=thread_{i}))\n"

    code += textwrap.dedent("""\
            for t in threads:
                t.start()
            for t in threads:
                t.join()
    """)
    return code


def generate_thread_function(fc: Flowchart, index: int) -> str:
    lines = [f"def thread_{index}():"]
    visited = set()

    def dfs(block_id):
        if block_id in visited:
            return
        visited.add(block_id)
        block = fc.blocks[block_id]

        match block.block_type:
            case BlockType.ASSIGN_VAR:
                lines.append(f"    shared_vars[{block.args[0]}] = shared_vars[{block.args[1]}]")
            case BlockType.ASSIGN_CONST:
                lines.append(f"    shared_vars[{block.args[0]}] = {block.args[1]}")
            case BlockType.INPUT:
                lines.append(f"    shared_vars[{block.args[0]}] = get_input()")
            case BlockType.PRINT:
                lines.append(f"    print(shared_vars[{block.args[0]}])")
            case BlockType.CONDITION_EQ | BlockType.CONDITION_LT:
                cond = "==" if block.block_type == BlockType.CONDITION_EQ else "<"
                if len(block.next_blocks) < 2:
                    return  # недійсний блок
                lines.append(f"    if shared_vars[{block.args[0]}] {cond} {block.args[1]}:")
                lines.append(f"        # if-branch")
                lines.append(f"        block_id = '{block.next_blocks[0]}'")
                lines.append(f"        thread_{index}__dispatch(block_id)")
                lines.append(f"        return")
                lines.append(f"    else:")
                lines.append(f"        block_id = '{block.next_blocks[1]}'")
                lines.append(f"        thread_{index}__dispatch(block_id)")
                lines.append(f"        return")
                return

        if block.next_blocks:
            for next_id in block.next_blocks:
                dfs(next_id)

    # Dispatch function
    lines.append(f"    thread_{index}__dispatch('{fc.start_id}')")

    lines.append(f"\ndef thread_{index}__dispatch(block_id):")
    lines.append(f"    while True:")
    lines.append(f"        if block_id == '': break")
    for block_id in fc.blocks:
        lines.append(f"        if block_id == '{block_id}':")
        lines.append(f"            # Block logic for {block_id}")
        block = fc.blocks[block_id]
        match block.block_type:
            case BlockType.ASSIGN_VAR:
                lines.append(f"            shared_vars[{block.args[0]}] = shared_vars[{block.args[1]}]")
            case BlockType.ASSIGN_CONST:
                lines.append(f"            shared_vars[{block.args[0]}] = {block.args[1]}")
            case BlockType.INPUT:
                lines.append(f"            shared_vars[{block.args[0]}] = get_input()")
            case BlockType.PRINT:
                lines.append(f"            print(shared_vars[{block.args[0]}])")
            case BlockType.CONDITION_EQ | BlockType.CONDITION_LT:
                cond = "==" if block.block_type == BlockType.CONDITION_EQ else "<"
                lines.append(f"            if shared_vars[{block.args[0]}] {cond} {block.args[1]}:")
                lines.append(f"                block_id = '{block.next_blocks[0]}'")
                lines.append(f"            else:")
                lines.append(f"                block_id = '{block.next_blocks[1]}'")
                lines.append(f"            continue")
        if block.next_blocks:
            lines.append(f"            block_id = '{block.next_blocks[0]}'")
            lines.append(f"            continue")
        else:
            lines.append(f"            break")
    return "\n".join(lines)
