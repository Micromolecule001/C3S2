#!/bin/bash

# Define available projects and their main classes
declare -A PROJECTS=(
    ["TextAnalyzer"]="com.textanalyzer.Launcher"
    ["UnitConverter"]="com.unitconverter.Launcher"
)

echo "Available projects:"
for project in "${!PROJECTS[@]}"; do
    echo " - $project"
done

# Prompt user for project selection
read -p "Enter project name to build and run: " PROJECT

# Check if the project exists
if [[ -z "${PROJECTS[$PROJECT]}" ]]; then
    echo "Error: Project '$PROJECT' not found!"
    exit 1
fi

# Define directories
SRC_DIR="apps/$PROJECT/src"
OUT_DIR="apps/$PROJECT/out"
MAIN_CLASS="${PROJECTS[$PROJECT]}"

# Compile the project
echo "Compiling $PROJECT..."
find "$SRC_DIR" -name "*.java" | xargs javac -d "$OUT_DIR"

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo "Running $PROJECT..."
    java -cp "$OUT_DIR" "$MAIN_CLASS"
else
    echo "Compilation failed!"
    exit 1
fi

