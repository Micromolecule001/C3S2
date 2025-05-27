import subprocess
import tempfile
from logic.translator import translate_flowchart_set

def run_test(flowcharts, input_data: str, expected_output: str, verbose=True) -> bool:
    code = translate_flowchart_set(flowcharts)

    with tempfile.NamedTemporaryFile(mode="w+", suffix=".py", delete=False) as temp:
        temp.write(code)
        temp.flush()

        try:
            result = subprocess.run(
                ["python3", temp.name],
                input=input_data.encode(),
                capture_output=True,
                timeout=5
            )
            actual_output = result.stdout.decode().strip()
            if verbose:
                print("Expected:", expected_output.strip())
                print("Actual  :", actual_output)
            return actual_output == expected_output.strip()
        except subprocess.TimeoutExpired:
            print("❌ Timeout during execution.")
            return False
